
//import org.qtproject.qt5.android.bindings.QtActivity;
import org.qtproject.qt5.android.QtNative;

import java.lang.String;
//import android.content.Intent;
import android.util.Log;
import android.app.Activity;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.app.PendingIntent;
import android.hardware.usb.UsbDeviceConnection;
import java.util.HashMap;
//import 	android.os.Bundle;
import java.util.Iterator;

public class GetPermission extends QtNative
{

    protected static UsbManager usbManager;
    private static UsbDevice camdev = null;

//    public GetPermission()
//    {
//        m_instance = this;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//    }

//    @Override
//    public void onDestroy()
//    {
//        super.onDestroy();
//    }

    public static int testfunc() {
        Log.d("GetPermission/testfunc", "in testfunc, return value should be 1");
        return 1;
    }

    public static int requestDevicePermission(UsbManager m_usbManager, int vid, int pid)
    {
        usbManager = m_usbManager;
        Activity currentActivity = QtNative.activity();
        HashMap<String, UsbDevice> deviceList = m_usbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while(deviceIterator.hasNext())
        {
            UsbDevice device = deviceIterator.next();
            if (device.getVendorId()== vid && device.getProductId()== pid)
            {
                Log.e("GetPermission/requestDevicePermission", "Find the device: " + device.getDeviceName());
                camdev = device;
                boolean permissionCheck = m_usbManager.hasPermission(camdev);
                PendingIntent mPermissionIntent = PendingIntent.getActivity(QtNative.activity(),0,QtNative.activity().getIntent(),0);
                if(!permissionCheck)m_usbManager.requestPermission(device, mPermissionIntent);
                break;
            }
        }
        if(camdev == null){
            return 0;
        }
        return 1;
    }

    public static int checkPermission(){
        boolean checkResult = usbManager.hasPermission(camdev);
        Log.d("GetPermission/checkPermission", checkResult?"yes":"no");
        int ret = 0;
        if(checkResult)
            ret = openDevice();
        return ret;
    }

    private static int openDevice()
    {
        try {
            UsbDeviceConnection devConn = usbManager.openDevice(camdev);
            int fd = devConn.getFileDescriptor();
            Log.e("GetPermission/openDevice","USB device FileDescriptor = " + fd);
            return fd;
        }
        catch (Exception e) {
            Log.wtf("GetPermission/openDevice","Exception catched when open device");
            e.printStackTrace();
            return 0;
        }
    }

}
