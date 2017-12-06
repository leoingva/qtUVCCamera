#ifndef WIDGET_H
#define WIDGET_H

#include<QImage>
#include <QMutex>
#include <QRect>

namespace UVC {
    extern QImage currentCameraFrame;
    extern QMutex mutexCameraFrame;
    extern bool frameReady;
    extern QRect frameRect;

    int testJNI();
    int findUVCDevice(int &vid, int &pid);
    int requestPermission(int vid, int pid);
    int checkPermission();
    int startUVC(int fd,int width, int height, int fps);
    int stopUVC();
}



#if !defined(BUILDING_LIBRARY)
#include <QWidget>
#include <QTimer>

class UVCPlayer : public QWidget
{
    Q_OBJECT

public:
    UVCPlayer(QWidget *parent = 0);
    ~UVCPlayer();
    void paintEvent ( QPaintEvent * );
    QTimer *timer;
    bool isStreaming = false;
private slots:

    void timerCallback();
};
#endif

#endif // WIDGET_H
