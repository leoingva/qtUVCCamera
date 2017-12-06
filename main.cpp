#include "UVC.h"
#include <QApplication>







int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    UVCPlayer w;
    w.showFullScreen();

    return a.exec();
}
