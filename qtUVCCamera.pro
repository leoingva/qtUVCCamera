#-------------------------------------------------
#
# Project created by QtCreator 2017-11-08T09:16:53
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = qtUVCCamera
TEMPLATE = app

# The following define makes your compiler emit warnings if you use
# any feature of Qt which has been marked as deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS ACCESS_RAW_DESCRIPTORS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0


SOURCES += \
        main.cpp \
        widget.cpp \
    libjpeg/cdjpeg.c \
    libjpeg/cjpeg.c \
    libjpeg/djpeg.c \
    libjpeg/jaricom.c \
    libjpeg/jcapimin.c \
    libjpeg/jcapistd.c \
    libjpeg/jcarith.c \
    libjpeg/jccoefct.c \
    libjpeg/jccolext.c \
    libjpeg/jccolor.c \
    libjpeg/jcdctmgr.c \
    libjpeg/jchuff.c \
    libjpeg/jcinit.c \
    libjpeg/jcmainct.c \
    libjpeg/jcmarker.c \
    libjpeg/jcmaster.c \
    libjpeg/jcomapi.c \
    libjpeg/jcparam.c \
    libjpeg/jcphuff.c \
    libjpeg/jcprepct.c \
    libjpeg/jcsample.c \
    libjpeg/jctrans.c \
    libjpeg/jdapimin.c \
    libjpeg/jdapistd.c \
    libjpeg/jdarith.c \
    libjpeg/jdatadst.c \
    libjpeg/jdatadst-tj.c \
    libjpeg/jdatasrc.c \
    libjpeg/jdatasrc-tj.c \
    libjpeg/jdcoefct.c \
    libjpeg/jdcol565.c \
    libjpeg/jdcolext.c \
    libjpeg/jdcolor.c \
    libjpeg/jddctmgr.c \
    libjpeg/jdhuff.c \
    libjpeg/jdinput.c \
    libjpeg/jdmainct.c \
    libjpeg/jdmarker.c \
    libjpeg/jdmaster.c \
    libjpeg/jdmerge.c \
    libjpeg/jdmrg565.c \
    libjpeg/jdmrgext.c \
    libjpeg/jdphuff.c \
    libjpeg/jdpostct.c \
    libjpeg/jdsample.c \
    libjpeg/jdtrans.c \
    libjpeg/jerror.c \
    libjpeg/jfdctflt.c \
    libjpeg/jfdctfst.c \
    libjpeg/jfdctint.c \
    libjpeg/jidctflt.c \
    libjpeg/jidctfst.c \
    libjpeg/jidctint.c \
    libjpeg/jidctred.c \
    libjpeg/jmemmgr.c \
    libjpeg/jmemnobs.c \
    libjpeg/jpegtran.c \
    libjpeg/jquant1.c \
    libjpeg/jquant2.c \
    libjpeg/jsimd_none.c \
    libjpeg/jstdhuff.c \
    libjpeg/jutils.c \
    libjpeg/rdbmp.c \
    libjpeg/rdcolmap.c \
    libjpeg/rdgif.c \
    libjpeg/rdjpgcom.c \
    libjpeg/rdppm.c \
    libjpeg/rdrle.c \
    libjpeg/rdswitch.c \
    libjpeg/rdtarga.c \
    libjpeg/tjutil.c \
    libjpeg/transupp.c \
    libjpeg/turbojpeg.c \
    libjpeg/wrbmp.c \
    libjpeg/wrgif.c \
    libjpeg/wrjpgcom.c \
    libjpeg/wrppm.c \
    libjpeg/wrrle.c \
    libjpeg/wrtarga.c \
    libuvc/ctrl.c \
    libuvc/device.c \
    libuvc/diag.c \
    libuvc/frame.c \
    libuvc/frame-mjpeg.c \
    libuvc/init.c \
    libuvc/misc.c \
    libuvc/stream.c \
    libusb/os/poll_posix.c \
    libusb/os/threads_posix.c \
    libusb/descriptor.c \
    libusb/hotplug.c \
    libusb/io.c \
    libusb/strerror.c \
    libusb/sync.c \
    libusb/os/android_netlink.c \
    libusb/os/android_usbfs.c \
    libusb/core.c

HEADERS += \
        widget.h \
    libjpeg/bmp.h \
    libjpeg/cderror.h \
    libjpeg/cdjpeg.h \
    libjpeg/jchuff.h \
    libjpeg/jconfig.h \
    libjpeg/jdcoefct.h \
    libjpeg/jdct.h \
    libjpeg/jdhuff.h \
    libjpeg/jdmainct.h \
    libjpeg/jdmaster.h \
    libjpeg/jdsample.h \
    libjpeg/jerror.h \
    libjpeg/jinclude.h \
    libjpeg/jmemsys.h \
    libjpeg/jmorecfg.h \
    libjpeg/jpeg_nbits_table.h \
    libjpeg/jpegcomp.h \
    libjpeg/jpegint.h \
    libjpeg/jpeglib.h \
    libjpeg/jsimd.h \
    libjpeg/jsimddct.h \
    libjpeg/jversion.h \
    libjpeg/tjutil.h \
    libjpeg/transupp.h \
    libjpeg/turbojpeg.h \
    libjpeg/wrppm.h \
    libuvc/defines.h \
    libuvc/localdefines.h \
    libuvc/utilbase.h \
    libuvc/utlist.h \
    libuvc/libuvc/libuvc.h \
    libuvc/libuvc/libuvc_config.h \
    libuvc/libuvc/libuvc_config.h.in \
    libuvc/libuvc/libuvc_internal.h \
    libuvc/libuvc/libuvc_internal_original.h \
    libuvc/libuvc/libuvc_original.h \
    libusb/os/poll_posix.h \
    libusb/os/threads_posix.h \
    libusb/config.h \
    libusb/hotplug.h \
    libusb/libusb.h \
    libusb/libusb_original.h \
    libusb/libusbi.h \
    libusb/libusbi_original.h \
    libusb/localdefines.h \
    libusb/utilbase.h \
    libusb/version.h \
    libusb/version_nano.h \
    libusb/os/android_usbfs.h

CONFIG += mobility
MOBILITY = 
INCLUDEPATH += $$PWD/libuvc
INCLUDEPATH += $$PWD/libusb
INCLUDEPATH += $$PWD/libjpeg
