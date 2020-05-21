package ir.batna.provider.ui;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.net.InetAddress;
import java.net.UnknownHostException;

import ir.batna.provider.R;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Dev_am1 on 5/6/2020
 */
enum ConnectionType {WIFI, MOBILE_DATA, NONE}

public class Fragment_wifi extends Fragment {
    private static final String TAG = "Fragment_wifi";
    private TextView status;
    private TextView dataType;
    private TextView networkType;
    private TextView ipAddress;
    private TextView macAddress;
    private TextView ssid;
    private TextView linkSpeed;
    private Context mContext;
    boolean isWifi = false;
    static ConnectionType connectionType = ConnectionType.NONE;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_wifi, container, false);
        status = v.findViewById(R.id.textViewStatus);
        dataType = v.findViewById(R.id.textViewDataType);
        networkType = v.findViewById(R.id.textViewNetworkType);
        ipAddress = v.findViewById(R.id.textViewIpAddress);
        macAddress = v.findViewById(R.id.textViewMacAddress);
        ssid = v.findViewById(R.id.textViewSsid);
        linkSpeed = v.findViewById(R.id.textViewLinkSpeed);
        mContext = getActivity().getApplicationContext();

        if (checkConnection(mContext)) {
            status.setText("شما به اینترنت وصل می باشید.");
        } else {
            status.setText("شما به اینترنت وصل نمی باشید!");
        }
        dataType.setText(getNetworkClass(mContext));
        if (connectionType == ConnectionType.WIFI) {
            networkType.setText("اتصال به WiFi");
            isWifi = true;
        } else if (connectionType == ConnectionType.MOBILE_DATA) {
            networkType.setText("اتصال به داده همراه");
        }
        ipAddress.setText(getIpAddress(mContext));

        WifiManager wm = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        macAddress.setText(wm.getConnectionInfo().getMacAddress());

        WifiInfo wifiInfo = wm.getConnectionInfo();
        ssid.setText(wifiInfo.getSSID());
        if (isWifi) {
            int speedMbps = wifiInfo.getLinkSpeed();
            linkSpeed.setText(speedMbps + "");
        } else {
            linkSpeed.setText("0");
        }
        return v;
    }

    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    private static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    connectionType = ConnectionType.WIFI;
                    return true;
                } else {
                    connectionType = ConnectionType.MOBILE_DATA;
                    return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
                }
            }
        }
        return false;
    }

    private static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:     // api< 8: replace by 11
                case TelephonyManager.NETWORK_TYPE_GSM:      // api<25: replace by 16
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:   // api< 9: replace by 12
                case TelephonyManager.NETWORK_TYPE_EHRPD:    // api<11: replace by 14
                case TelephonyManager.NETWORK_TYPE_HSPAP:    // api<13: replace by 15
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA: // api<25: replace by 17
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:      // api<11: replace by 13
                case TelephonyManager.NETWORK_TYPE_IWLAN:    // api<25: replace by 18
                case 19: // LTE_CA
                    return "4G";
                case TelephonyManager.NETWORK_TYPE_NR:       // api<29: replace by 20
                    return "5G";
                default:
                    return "?";
            }
        }
        return "?";
    }

    private static String getIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                .getSystemService(WIFI_SERVICE);

        String ipAddress = intToInetAddress(wifiManager.getDhcpInfo().ipAddress).toString();

        ipAddress = ipAddress.substring(1);

        return ipAddress;
    }

    private static InetAddress intToInetAddress(int hostAddress) {
        byte[] addressBytes = {(byte) (0xff & hostAddress),
                (byte) (0xff & (hostAddress >> 8)),
                (byte) (0xff & (hostAddress >> 16)),
                (byte) (0xff & (hostAddress >> 24))};

        try {
            return InetAddress.getByAddress(addressBytes);
        } catch (UnknownHostException e) {
            throw new AssertionError();
        }
    }


}
