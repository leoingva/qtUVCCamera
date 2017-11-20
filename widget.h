#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QTimer>


class Widget : public QWidget
{
    Q_OBJECT

public:
    Widget(QWidget *parent = 0);
    ~Widget();
//    void cb(uvc_frame_t *frame, void *ptr);
    void paintEvent ( QPaintEvent * );
    QTimer *timer;
private slots:

    // Method to verify the permission state
    void verifyPermissionState();
};

#endif // WIDGET_H
