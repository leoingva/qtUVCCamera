#include "UVC.h"

#include "libuvc/libuvc.h";

#include <QDebug>
#define puts qDebug
#include <QPainter>


#include <QAndroidJniObject>
#include <QAndroidJniEnvironment>
#include <QString>
#include "jni.h"
#include <QDir>
#include <QProcess>
#include <QtAndroid>
#include <QStandardPaths>
#include <QAndroidJniObject>
#include <QString>
#include <QTime>
#include <QCoreApplication>
#include <QRect>

#include<QImage>
#include <QMutex>
QImage UVC::currentCameraFrame;
QMutex UVC::mutexCameraFrame;
bool UVC::frameReady = false;
QRect UVC::frameRect;

uvc_device_t *dev;
uvc_context_t *ctx;
uvc_error_t res;
uvc_device_handle_t *devh;

const static char* MY_JAVA_CLASS = "GetPermission";

int UVC::testJNI() {
        return QAndroidJniObject::callStaticMethod<jint>(MY_JAVA_CLASS,
                                           "testfunc",
                                           "()I");
}
int UVC::checkPermission(){
    int ret = 0;
    ret = QAndroidJniObject::callStaticMethod<jint>(MY_JAVA_CLASS, "checkPermission","()I");
    return ret;
}
int  UVC::requestPermission(int vid, int pid) {
    int ret = 0;
        QAndroidJniObject activity = QtAndroid::androidActivity();
        if(!activity.isValid()) return 0;

        QAndroidJniObject serviceName = QAndroidJniObject::getStaticObjectField<jstring>("android/content/Context","USB_SERVICE");
        QAndroidJniObject usbManager = activity.callObjectMethod("getSystemService", "(Ljava/lang/String;)Ljava/lang/Object;", serviceName.object<jobject>());

        ret = QAndroidJniObject::callStaticMethod<jint>(MY_JAVA_CLASS, "requestDevicePermission",
                                                  "(Landroid/hardware/usb/UsbManager;II)I",
                                                    usbManager.object<jobject>(),vid,pid);

    return ret;
}



/* This callback function runs once per frame. Use it to perform any
 * quick processing you need, or have it put the frame into your application's
 * input queue. If this function takes too long, you'll start losing frames. */
void callbackfunc(uvc_frame_t *frame, void *ptr) {
    uvc_frame_t *rgb;
    uvc_error_t ret;
    /* We'll convert the image from YUV/JPEG to RGB, so allocate space */
    rgb = uvc_allocate_frame(frame->width * frame->height * 3);
    if (!rgb) {
        printf("unable to allocate rgb frame!");
        return;
    }
    /* Do the RGB conversion */
    ret = uvc_any2rgb(frame,rgb);
    if (ret) {
        uvc_perror(ret, "uvc_any2rgb");
        uvc_free_frame(rgb);
        return;
    }
    QImage tmp((uchar*)rgb->data,rgb->width,rgb->height,rgb->width*3,QImage::Format_RGB888);
    UVC::mutexCameraFrame.lock();
    UVC::currentCameraFrame=tmp.copy();
    UVC::mutexCameraFrame.unlock();

    uvc_free_frame(rgb);
    UVC::frameReady = true;
}


int UVC::findUVCDevice(int &vid, int &pid){
    /* Initialize a UVC service context. Libuvc will set up its own libusb
    * context. Replace NULL with a libusb_context pointer to run libuvc
    * from an existing libusb context. */
    res = uvc_init(&ctx, NULL);
    if (res < 0) {
        uvc_perror(res, "uvc_init");
        return res;
    }
    puts("UVC initialized");
    /* Locates the first attached UVC device, stores in dev */
    res = uvc_find_device(ctx, &dev, 0, 0, NULL); /* filter devices: vendor_id, product_id, "serial_num" */
    if (res < 0) {
        uvc_perror(res, "uvc_find_device"); /* no devices found */
        uvc_exit(ctx);
        puts("UVC exited");
        return res;
    }
    puts("Device found");
    uvc_device_descriptor_t *desc;
    if (uvc_get_device_descriptor(dev, &desc) == UVC_SUCCESS)
    {
        qDebug()<<desc->idVendor;
        qDebug()<<desc->idProduct;
        vid = desc->idVendor;
        pid = desc->idProduct;
        uvc_free_device_descriptor(desc);


    }
}
extern int maxAvailableWidth, maxAvailableHeight;
int UVC::startUVC(int fd,int width, int height, int fps){

    uvc_stream_ctrl_t ctrl;
    uvc_get_device_with_fd(ctx,&dev,0,0,0,fd,0,0);
    /* Try to open the device: requires exclusive access */
    res = uvc_open(dev, &devh);

    if (res < 0) {
      uvc_perror(res, "uvc_open"); /* unable to open device */
      devh = NULL;
      return 0;
    }
    puts("Device opened");
    /* Print out a message containing all the information that libuvc
     * knows about the device */

//    uvc_print_diag(devh, stderr);
    /* Try to negotiate a 640x480 30 fps YUYV stream profile */
    res = uvc_get_stream_ctrl_format_size(
        devh, &ctrl, /* result stored in ctrl */
//                      UVC_FRAME_FORMAT_RGB565,
        UVC_FRAME_FORMAT_YUYV, /* YUV 422, aka YUV 4:2:2. try _COMPRESSED */
        width, height, fps /* width, height, fps */
    );
    if(res == -51){
        uvc_perror(res, "get_mode"); /* device doesn't provide a matching stream */
        qDebug()<<maxAvailableWidth<<maxAvailableHeight;
        res = uvc_get_stream_ctrl_format_size(
            devh, &ctrl, /* result stored in ctrl */
    //                      UVC_FRAME_FORMAT_RGB565,
            UVC_FRAME_FORMAT_YUYV, /* YUV 422, aka YUV 4:2:2. try _COMPRESSED */
            maxAvailableWidth, maxAvailableHeight, fps /* width, height, fps */
        );
    }
    /* Print out the result */
    uvc_print_stream_ctrl(&ctrl, stderr);
    if (res < 0) {
        uvc_perror(res, "get_mode"); /* device doesn't provide a matching stream */
        uvc_close(devh);
        devh = NULL;
        puts("Device closed");
        return res;
    }
    /* Start the video stream. The library will call user function cb:
    *   cb(frame, (void*) 12345)
    */
    res = uvc_start_streaming(devh, &ctrl, callbackfunc, nullptr, 0);
    if (res < 0) {
        uvc_perror(res, "start_streaming"); /* unable to start stream */
        uvc_close(devh);
        devh = NULL;
        puts("Device closed");
        return res;
    }
    puts("Streaming...");
    uvc_set_ae_mode(devh, 2); /* e.g., turn on auto exposure */
//  sleep(10); /* stream for 10 seconds */
    return 1;

}
int UVC::stopUVC(){
    if(devh != NULL){
        /* End the stream. Blocks until last callback is serviced */
        uvc_stop_streaming(devh);
        puts("Done streaming.");
        /* Release our handle on the device */
        uvc_close(devh);
        puts("Device closed");
        devh = NULL;
    }
    return 1;
}

#ifndef BUILDING_LIBRARY
// this is an example of using
UVCPlayer::UVCPlayer(QWidget *parent)
    : QWidget(parent)
{
    timer = new QTimer(this);
    connect(this->timer, SIGNAL(timeout()), this, SLOT(timerCallback()));

    int ret = UVC::testJNI();
    qDebug()<<"test ret:" << ret;
    int vid = 0, pid = 0;
    UVC::findUVCDevice(vid,pid);

    ret = 0;
    ret = UVC::requestPermission(vid,pid);
    qDebug()<<"getPermission return:" << ret;
    if(ret ){
        this->timer->start(100);
    }
}


void UVCPlayer::timerCallback(){
    if(isStreaming){
        this->update();
    }else{
        int fd = UVC::checkPermission();
        qDebug()<<"FD ="<<fd;
        if(fd){
    //        timer->stop();
            UVC::startUVC(fd,640,480,20);
            isStreaming = true;
        }
    }
}
void UVCPlayer::paintEvent ( QPaintEvent * ){
    QPainter p(this);
    UVC::mutexCameraFrame.lock();
    p.drawImage(0,0,UVC::currentCameraFrame);
   UVC:: mutexCameraFrame.unlock();
}

UVCPlayer::~UVCPlayer()
{
    UVC::stopUVC();
}
#endif
