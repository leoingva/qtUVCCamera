//package com.cermate.hmi;

import org.qtproject.qt5.android.bindings.QtActivity;

import java.lang.String;
import java.io.File;
import android.content.Intent;
import android.util.Log;
import android.net.Uri;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.app.Activity;
import android.content.pm.PackageInfo;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbAccessory;
import 	android.content.IntentFilter;
import android.app.PendingIntent;
import android.hardware.usb.UsbDeviceConnection;
import java.util.HashMap;
import 	android.os.Bundle;
import 	android.content.BroadcastReceiver;
import 	android.os.Handler;
import java.util.Iterator;
import 	android.os.Looper;

public class GetPermission extends QtActivity
{
    private static GetPermission m_instance;
    private UsbAccessory accessory;
    private String TAG = "TAG";
    private static final String ACTION_USB_PERMISSION = "org.qtproject.example.USB_PERMISSION";
    private PendingIntent mPermissionIntent;
    private UsbManager manager;
    private UsbDeviceConnection connection;
    private HashMap<Integer, Integer> connectedDevices;

    public GetPermission()
    {
        m_instance = this;

        connectedDevices = new HashMap<Integer, Integer>();
        Log.d(TAG, "in GetPermission");
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "in onCreate");
        super.onCreate(savedInstanceState);

        manager = (UsbManager) getSystemService(Context.USB_SERVICE);

//        registerReceiver(usbManagerBroadcastReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED));
//        registerReceiver(usbManagerBroadcastReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED));
//        registerReceiver(usbManagerBroadcastReceiver, new IntentFilter(ACTION_USB_PERMISSION));

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

        Looper.prepare();

        final Handler handler = new Handler();

        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                checkForDevices();
                Looper.myLooper().quit();
            }
        }, 1000);
        Looper.loop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

//    private static native void notifyDeviceAttached(int fd);
//    private static native void notifyDeviceDetached(int fd);

//    private final BroadcastReceiver usbManagerBroadcastReceiver = new BroadcastReceiver()
//    {
//        public void onReceive(Context context, Intent intent)
//        {
//            try
//            {
//                String action = intent.getAction();

//                Log.d(TAG, "INTENT ACTION: " + action);

//                if (ACTION_USB_PERMISSION.equals(action))
//                {
//                    Log.d(TAG, "onUsbPermission");

//                    synchronized (this)
//                    {
//                        UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

//                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false))
//                        {
//                            if(device != null)
//                            {
//                                int fd = connectToDevice(device);
//                                Log.d(TAG,"device file descriptor: " + fd);
//                                notifyDeviceAttached(fd);
//                            }
//                        }
//                        else
//                        {
//                            Log.d(TAG, "permission denied for device " + device);
//                        }
//                    }
//                }

//                if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action))
//                {
//                    Log.d(TAG, "onDeviceConnected");

//                    synchronized(this)
//                    {
//                        UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

//                        if (device != null)
//                        {
//                            manager.requestPermission(device, mPermissionIntent);
//                        }
//                    }
//                }

//                if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action))
//                {
//                    Log.d(TAG, "onDeviceDisconnected");

//                    synchronized(this)
//                    {
//                        UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

//                        int fd = connectedDevices.get(device.getDeviceId());

//                        Log.d(TAG, "device: " + device.getDeviceId() + " disconnected. fd: " + fd);

//                        notifyDeviceDetached(fd);

//                        connectedDevices.remove(device.getDeviceId());
//                    }
//                }
//            }
//            catch(Exception e)
//            {
//                Log.d(TAG, "Exception: " + e);
//            }
//        }
//    };

    private int connectToDevice(UsbDevice device)
    {
        connection = manager.openDevice(device);
        // if we make this, kernel driver will be disconnected
        connection.claimInterface(device.getInterface(0), true);

        Log.d(TAG, "inserting device with id: " + device.getDeviceId() + " and file descriptor: " + connection.getFileDescriptor());
        connectedDevices.put(device.getDeviceId(), connection.getFileDescriptor());

        return connection.getFileDescriptor();
    }

    public int checkForDevices()
    {
         Log.d(TAG, "in checkForDevices");
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        while(deviceIterator.hasNext())
        {
            UsbDevice device = deviceIterator.next();

            if (device.getVendorId()== 0 && device.getProductId()== 0)
            {
                Log.d(TAG, "Found a device: " + device);
                manager.requestPermission(device, mPermissionIntent);
                return 1;
            }
        }
        return 0;
    }
public static int checkDevices(UsbManager manager, Context context )
{
     Log.d("TAG", "in checkDevices");

    HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
    Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
    PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
    while(deviceIterator.hasNext())
    {

        UsbDevice device = deviceIterator.next();
//        Log.d("TAG", "in while cur device: " + device);

        if (device.getVendorId()== 1133 && device.getProductId()== 2093)
        {
            Log.d("TAG", "Found a device: " + device);
            manager.requestPermission(device, mPermissionIntent);
            return 1;
        }
    }
    return 0;
}
//}

//public class GetPermission
//{
//    protected GetPermission()
//    {
//    }

    public static int installApp(String appPackageName) {
        Log.d("TAG", "in installApp");
//        if (QtActivity.activity() == null)
//            return -1;
        return 1;
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile(new File(appPackageName)),
//                                               "application/vnd.android.package-archive");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            QtNative.activity().startActivity(intent);
//            return 0;
//        } catch (android.content.ActivityNotFoundException anfe) {
//            return -3;
//        }
    }

//    //public String getVersionName(Context context) {
////    public String getVersionName() {
////        String myversionName = "";
////        try {
////            //QtNative.activity().
////            // get the package info
////            //PackageManager pm = Activity.getApplication().getPackageManager();
////            PackageManager pm = QtNative.activity().getPackageManager();
////            PackageInfo pi = pm.getPackageInfo(QtNative.activity().getPackageName(), 0);
////            myversionName = pi.versionName;
////        } catch (Exception e) {
////            Log.e("VersionInfo", "Exception", e);
////        }
////        return myversionName;
////    }

}
