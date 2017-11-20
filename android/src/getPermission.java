
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
    protected static UsbManager usbManager;
    private static UsbDeviceConnection connection;
    private static HashMap<String, Integer> deviceCache = new HashMap<String, Integer>();
    private static int fd = 0;
    private static boolean res = false;
    private static Activity mActivityInstance;
    private static UsbDevice camdev = null;

    public GetPermission()
    {
        m_instance = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    public static void openDevice(UsbManager m_usbManager,UsbDevice device)
    {
        try {
            UsbDeviceConnection devConn = m_usbManager.openDevice(device);
            fd = devConn.getFileDescriptor();
        }
        catch (Exception e) {
            Log.e("try","catched");
            e.printStackTrace();
            return;
        }
    }

    public static int checkPermission(UsbManager m_usbManager){
        usbManager = m_usbManager;
        boolean permissionCheck = m_usbManager.hasPermission(camdev);
        Log.d("permissionCheck = ", permissionCheck?"yes":"no");
        if(permissionCheck) openDevice(m_usbManager,camdev);
        return permissionCheck?fd:0;
    }

    public static int findDevice(UsbManager m_usbManager, int vid, int pid)
    {
        Activity currentActivity = QtNative.activity();
        HashMap<String, UsbDevice> deviceList = m_usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while(deviceIterator.hasNext())
        {
            UsbDevice device = deviceIterator.next();
            if (device.getVendorId()== vid && device.getProductId()== pid)
            {
                Log.e("TRACE", "Found a device: " + device.getDeviceName());
                camdev = device;
                boolean permissionCheck = m_usbManager.hasPermission(camdev);
                if(!permissionCheck)m_usbManager.requestPermission(device, mPermissionIntent);
                break;
            }
        }
        if(camdev == null){
            return 0;
        }
        return 1;
    }

    public static int testfunc() {
        Log.d("TAG", "in testfunc");
        return 1;
    }

}
