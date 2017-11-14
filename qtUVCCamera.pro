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
    libuvc/ctrl.c \
    libuvc/device.c \
    libuvc/diag.c \
    libuvc/frame.c \
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
    libuvc/defines.h \
    libuvc/localdefines.h \
    libuvc/utilbase.h \
    libuvc/utlist.h \
    libuvc/libuvc/libuvc.h \
    libuvc/libuvc/libuvc_config.h \
    libuvc/libuvc/libuvc_internal.h \
    libuvc/libuvc/libuvc_original.h \
    libusb/os/poll_posix.h \
    libusb/os/threads_posix.h \
    libusb/config.h \
    libusb/hotplug.h \
    libusb/libusb.h \
    libusb/libusbi.h \
    libusb/utilbase.h \
    libusb/version.h \
    libusb/os/android_usbfs.h

CONFIG += mobility
MOBILITY = 
INCLUDEPATH += $$PWD/libuvc
INCLUDEPATH += $$PWD/libusb

DISTFILES += \
    android/AndroidManifest.xml \
    android/gradle/wrapper/gradle-wrapper.jar \
    android/gradlew \
    android/res/values/libs.xml \
    android/build.gradle \
    android/gradle/wrapper/gradle-wrapper.properties \
    android/gradlew.bat

ANDROID_PACKAGE_SOURCE_DIR = $$PWD/android
