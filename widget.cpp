#include "widget.h"

#include <QDebug>
#define puts qDebug
#include "libuvc/libuvc.h"
#include<QImage>
#include <QMutex>
#include <QPainter>


#include <QAndroidJniObject>
#include <QAndroidJniEnvironment>
#include <QDebug>
#include <QString>
#include "jni.h"
#include <QDir>
#include <QProcess>
#include <QtAndroid>
#include <QStandardPaths>
#include <QAndroidJniObject>
#include <QString>
const static char* MY_JAVA_CLASS = "GetPermission";
int InstallApp(const QString &appPackageName) {
        QAndroidJniObject jsText = QAndroidJniObject::fromString(appPackageName);
        return QAndroidJniObject::callStaticMethod<jint>(MY_JAVA_CLASS,
                                           "installApp",
                                           "(Ljava/lang/String;)I",
                                           jsText.object<jstring>());
}
int  getPermission(int vid, int pid) {
    int ret = 0;
        QAndroidJniObject activity = QtAndroid::androidActivity();
        QAndroidJniObject serviceName = QAndroidJniObject::getStaticObjectField<jstring>("android/content/Context","USB_SERVICE");
        QAndroidJniObject usbManager = activity.callObjectMethod("getSystemService", "(Ljava/lang/String;)Ljava/lang/Object;", serviceName.object<jobject>());
        if ( usbManager.isValid() ){
            qDebug()<<"usb manager got";
            ret = QAndroidJniObject::callStaticMethod<jint>(MY_JAVA_CLASS,
                                                      "checkDevices",
                                                      "(Landroid/hardware/usb/UsbManager;Landroid/content/Context;)I",
                                                      usbManager.object<jobject>(),activity.object());
        }else
        {
            qDebug()<<"usb manager error";
            ret = -1;
        }
//    QAndroidJniObject activity = QAndroidJniObject(MY_JAVA_CLASS,
//                                                   "()V");
//    if (activity.isValid()) {
//        return activity.callMethod<jint>("checkForDevices","()I");
//    }
    return ret;
}


QImage img;
QMutex mutex;
QWidget *w;
/* This callback function runs once per frame. Use it to perform any
 * quick processing you need, or have it put the frame into your application's
 * input queue. If this function takes too long, you'll start losing frames. */
void cb(uvc_frame_t *frame, void *ptr) {
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

//  qDebug()<<frame->actual_bytes;

//  QPixmap pix;
//  pix.loadFromData((const uchar*)frame->data,frame->actual_bytes,"JPEG");
//  pix.save("file:///sotrage/emulated/0/Download/3.jpg","JPG",100);


  QImage tmp((uchar*)rgb->data,rgb->width,rgb->height,rgb->width*3,QImage::Format_RGB888);
//mutex.lock();
img=tmp.copy();
//mutex.unlock();
w->update();
//  static int cnt = 1;
//  img.save(QString("/sdcard/Download/c/%1.jpg").arg(cnt++));

  uvc_free_frame(rgb);
}
uvc_error_t uvc_start(){
      uvc_context_t *ctx;
      uvc_device_t *dev;
      uvc_device_handle_t *devh;
      uvc_stream_ctrl_t ctrl;
      uvc_error_t res;
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
      } else {
        puts("Device found");
        uvc_device_descriptor_t *desc;
        if (uvc_get_device_descriptor(dev, &desc) == UVC_SUCCESS)
        {
            qDebug()<<desc->idVendor;
                    qDebug()<<desc->idProduct;
getPermission(desc->idVendor,desc->idProduct);
        }

        /* Try to open the device: requires exclusive access */
        res = uvc_open(dev, &devh);
        if (res < 0) {
          uvc_perror(res, "uvc_open"); /* unable to open device */
        } else {
          puts("Device opened");
          /* Print out a message containing all the information that libuvc
           * knows about the device */
          uvc_print_diag(devh, stderr);
          /* Try to negotiate a 640x480 30 fps YUYV stream profile */
          res = uvc_get_stream_ctrl_format_size(
              devh, &ctrl, /* result stored in ctrl */
//                      UVC_FRAME_FORMAT_UYVY,
              UVC_FRAME_FORMAT_YUYV, /* YUV 422, aka YUV 4:2:2. try _COMPRESSED */
              640, 480, 20 /* width, height, fps */
          );
          /* Print out the result */
          uvc_print_stream_ctrl(&ctrl, stderr);
          if (res < 0) {
            uvc_perror(res, "get_mode"); /* device doesn't provide a matching stream */
          } else {
            /* Start the video stream. The library will call user function cb:
             *   cb(frame, (void*) 12345)
             */
            res = uvc_start_streaming(devh, &ctrl, cb, nullptr, 0);
            if (res < 0) {
              uvc_perror(res, "start_streaming"); /* unable to start stream */
            } else {
              puts("Streaming...");
              uvc_set_ae_mode(devh, 2); /* e.g., turn on auto exposure */
//              sleep(10); /* stream for 10 seconds */
              /* End the stream. Blocks until last callback is serviced */
//              uvc_stop_streaming(devh);
//              puts("Done streaming.");
            }
          }
          /* Release our handle on the device */
//          uvc_close(devh);
//          puts("Device closed");
        }
        /* Release the device descriptor */
//        uvc_unref_device(dev);
      }
      /* Close the UVC context. This closes and cleans up any existing device handles,
       * and it closes the libusb context if one was not provided. */
//      uvc_exit(ctx);
//      puts("UVC exited");
}

Widget::Widget(QWidget *parent)
    : QWidget(parent)
{


    int ret = InstallApp("asdf");
    qDebug()<<ret;
//    ret = getPermission();
    qDebug()<<ret;

    //    w = this;
    uvc_start();
}

Widget::~Widget()
{
}

void Widget::paintEvent ( QPaintEvent * ){
    QPainter p(this);
//    mutex.lock();
    p.drawImage(0,0,img);
//    mutex.unlock();
}
