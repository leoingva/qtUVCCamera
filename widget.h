#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>



class Widget : public QWidget
{
    Q_OBJECT

public:
    Widget(QWidget *parent = 0);
    ~Widget();
//    void cb(uvc_frame_t *frame, void *ptr);
    void paintEvent ( QPaintEvent * );
};

#endif // WIDGET_H
