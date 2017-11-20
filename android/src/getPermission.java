//package com.cermate.hmi;

import org.qtproject.qt5.android.bindings.QtActivity;
import org.qtproject.qt5.android.QtNative;

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
import android.app.FragmentManager;
import android.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.app.ActivityCompat;

public class GetPermission extends QtActivity
{
    private static GetPermission m_instance;
    private static UsbAccessory accessory;
    private static String TAG = "TAG";
    private static final String ACTION_USB_PERMISSION = "org.qtproject.example.USB_PERMISSION";
    private static PendingIntent mPermissionIntent;
//    private static UsbManager m_usbManager;
    private static UsbDeviceConnection connection;
//    private static HashMap<Integer, Integer> connectedDevices;
    private static HashMap<String, Integer> deviceCache = new HashMap<String, Integer>();
    private static int fd = 0;
    private static boolean res = false;
    private static Activity mActivityInstance;
    private static UsbDevice camdev = null;

    public GetPermission()
    {
        Log.d(TAG, "jin constructor");


        m_instance = this;

        Log.d(TAG, "chu constructor");
//        connectedDevices = new HashMap<Integer, Integer>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d(TAG, "jin onCreate");
//        super.onCreate(savedInstanceState);
//        if(m_usbManager == null){
//            m_usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
//        }


////        registerReceiver(usbManagerBroadcastReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED));
////        registerReceiver(usbManagerBroadcastReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_DETACHED));
//        registerReceiver(mUsbReceiver, new IntentFilter(ACTION_USB_PERMISSION));

//        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);

//        final Handler handler = new Handler();

//        handler.postDelayed(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                checkForDevices();
//            }
//        }, 1000);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
//private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
//    public void onReceive(Context context, Intent intent) {
//        res = true;
//        Log.d("RET", "in receive callback");
//        String action = intent.getAction();
//        if (ACTION_USB_PERMISSION.equals(action)) {
//            synchronized (this) {
//                UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//                if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                    if(device != null){
//                        m_instance.openDevice(device);
//                   }
//                }
//            }
//        }
//    }
//};
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

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
//                            m_usbManager.requestPermission(device, mPermissionIntent);
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

//    private int connectToDevice(UsbDevice device)
//    {
//        connection = m_usbManager.openDevice(device);
//        // if we make this, kernel driver will be disconnected
//        connection.claimInterface(device.getInterface(0), true);

//        Log.d(TAG, "inserting device with id: " + device.getDeviceId() + " and file descriptor: " + connection.getFileDescriptor());
//        connectedDevices.put(device.getDeviceId(), connection.getFileDescriptor());

//        return connection.getFileDescriptor();
//    }
    public static void openDevice(UsbManager m_usbManager,UsbDevice device)
    {
        try {
            Log.e("try","jinlailema");
//            if (!res) return;
            UsbDeviceConnection devConn = m_usbManager.openDevice(device);
            Log.d("devConn = ",devConn.toString());
            fd = devConn.getFileDescriptor();
            Log.d("TAG","fd = " + fd);

//            deviceCache.put(device.getDeviceName(), fd);

        }
        catch (Exception e) {
            Log.e("try","catched");
            e.printStackTrace();
            return;
        }
    }
//    public static int newClass(Activity activity){
//        Log.d("TT","in new class");
//        Log.d("ACTIVITY",activity.toString());
//        Looper.prepare();
//       GetPermission m_instance1 = new GetPermission();
//       m_instance = m_instance1;
//       mActivityInstance = activity;
//       //Looper.loop();
//       Log.d("TT","out new class");
//       return 1;
//    }

//    static public int checkForDevices(int vid, int pid)
//    {
////        if (deviceCache.containsKey(devPath)) {
////            int fd = deviceCache.get(devPath);
////            return fd;
////        }


//        Log.d(TAG, "in checkForDevices" + fd);
//        Activity mother = QtNative.activity();
//       Intent intent = new Intent(mother, GetPermission.class);

//       Log.d(TAG, "after new Intent");
//        Log.d(TAG, intent.toString());
//        Log.d(TAG, mother.toString());
//        Log.d(TAG, GetPermission.class.getName());
//       mother.startActivity(intent);

//        Log.d(TAG, "in checkForDevices" + fd);

//        if(fd > 0)
//            return fd;
////        deviceCache.put(devPath, -1);

//        Log.d(TAG, "before get device list");
//        HashMap<String, UsbDevice> deviceList = m_usbManager.getDeviceList();
//        Log.d(TAG, "after get device list");
//        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

////        Looper.loop();
//        while(deviceIterator.hasNext())
//        {
//            UsbDevice device = deviceIterator.next();

//            if (device.getVendorId()== vid && device.getProductId()== pid)
//            {
//                Log.d(TAG, "Found a device: " + device);
//                res = false;
//                m_usbManager.requestPermission(device, mPermissionIntent);
//                return 0;
//            }
//        }
//        return -1;
//    }
    public static int checkPermission(UsbManager m_usbManager){
        boolean permissionCheck = m_usbManager.hasPermission(camdev);
        Log.d("permissionCheck = ", permissionCheck?"yes":"no");
        if(permissionCheck) openDevice(m_usbManager,camdev);
        return permissionCheck?fd:0;
    }
    public static int checkDevices(UsbManager m_usbManager, int vid, int pid)
    {
        Log.d("TRACE", "in checkDevices");
        Activity currentActivity = QtNative.activity();
        Log.d("currentActivity", currentActivity.toString());

        HashMap<String, UsbDevice> deviceList = m_usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
//        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        while(deviceIterator.hasNext())
        {
            UsbDevice device = deviceIterator.next();
            if (device.getVendorId()== 1133 && device.getProductId()== 2093)
            {
                Log.e("TRACE", "Found a device: " + device.getDeviceName());
                camdev = device;
                m_usbManager.requestPermission(device, mPermissionIntent);
                break;
            }
        }
        if(camdev == null){
            return 0;
        }
//        Looper.prepare();
//        final FragmentManager fragmentManager = currentActivity.getFragmentManager();
//        final Fragment request = new Fragment() {
//            void Fragment(){
//                Log.wtf("AA","in constractor");
//            }

//            @Override public void onStart()
//            {
//                Log.wtf("AA","in onstart");
//                super.onStart();
//                Log.wtf("AA","in onstart");
//            }

//        };


//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(0, request).commit();
//        Log.wtf("TRACE",request.toString());
        return 1;
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
