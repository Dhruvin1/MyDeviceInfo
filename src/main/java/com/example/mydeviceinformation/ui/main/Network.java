package com.example.mydeviceinformation.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telephony.CellInfo;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydeviceinformation.R;

import java.util.Locale;
import java.util.Objects;

import static android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_NR;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Network#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Network extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int PERMISSION_REQUEST_CODE = 45;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Network() {
        // Required empty public constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                }  else {
                    Toast.makeText(this.getContext(),"Issue with permission",Toast.LENGTH_LONG).show();
                }
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{
                Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
    }

    public String[] get_network_info(TelephonyManager telephonyManager) {
        String[] sig = new String[4];

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions();
        }
        else {
            switch (telephonyManager.getDataNetworkType()) {
                case NETWORK_TYPE_EDGE:
                    sig[0] = "2G";
                    sig[1] = "EDGE";
                    break;
                case NETWORK_TYPE_GPRS:
                    sig[0] = "2G";
                    sig[1] = "GPRS";
                    break;
                case NETWORK_TYPE_CDMA:
                    sig[0] = "2G";
                    sig[1] = "CDMA";
                    break;
                case NETWORK_TYPE_IDEN:
                    sig[0] = "2G";
                    sig[1] = "IDEN";
                    break;
                case NETWORK_TYPE_1xRTT:
                    sig[0] = "2G";
                    sig[1] = "1xRTT";
                    break;
                case NETWORK_TYPE_UMTS:
                    sig[0] = "3G";
                    sig[1] = "UMTS";
                    break;
                case NETWORK_TYPE_HSDPA:
                    sig[0] = "3G";
                    sig[1] = "HSDPA";
                    break;
                case NETWORK_TYPE_HSPA:
                    sig[0] = "3G";
                    sig[1] = "HSPA";
                    break;
                case NETWORK_TYPE_HSPAP:
                    sig[0] = "3G";
                    sig[1] = "HSPAP";
                    break;
                case NETWORK_TYPE_EVDO_0:
                    sig[0] = "3G";
                    sig[1] = "EVDO_0";
                    break;
                case NETWORK_TYPE_EVDO_A:
                    sig[0] = "3G";
                    sig[1] = "EVDO_A";
                    break;
                case NETWORK_TYPE_EVDO_B:
                    sig[0] = "3G";
                    sig[1] = "EVDO_B";
                    break;
                case NETWORK_TYPE_LTE:
                    sig[0] = "4G";
                    sig[1] = "LTE";
                    break;
                case NETWORK_TYPE_NR:
                    sig[0] = "5G";
                    sig[1] = "NR";
                    break;
                default:
                    sig[0] = "Unknown";
                    sig[1] = "Unknown";
            }
            CellInfo cellInfo = telephonyManager.getAllCellInfo().get(0);
            if (cellInfo.isRegistered()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    sig[3] = String.valueOf(cellInfo.getCellSignalStrength().getDbm());
                    int strength = cellInfo.getCellSignalStrength().getLevel();
                    switch (strength) {
                        case 0:
                            sig[2] = "None";
                            break;
                        case 1:
                            sig[2] = "Poor";
                            break;
                        case 2:
                            sig[2] = "Moderate";
                            break;
                        case 3:
                            sig[2] = "Good";
                            break;
                        case 4:
                            sig[2] = "Excellent";
                            break;
                    }

                } else {
                    sig[3] = "Feature Not Supported";
                    sig[2] = "Feature Not Supported";
                }
            } else {
                sig[3] = "Not Applicable";
                sig[2] = "No Signal";
            }
        }
        return sig;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Network.
     */
    // TODO: Rename and change types and number of parameters
    public static Network newInstance(String param1, String param2) {
        Network fragment = new Network();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_network, container, false);
        TextView network_type = (TextView) v.findViewById(R.id.network_type_output);
        TextView network_technology = (TextView) v.findViewById(R.id.network_technology_output);
        TextView signal_strength = (TextView) v.findViewById(R.id.signal_strength_output);
        TextView signal_strength_dbm = (TextView) v.findViewById(R.id.signal_strength_dbm_output);
        TextView data_enabled = (TextView) v.findViewById(R.id.data_enabled_output);
        TextView connectivity_type = (TextView) v.findViewById(R.id.connectivity_type_output);
        TextView upload_speed = (TextView) v.findViewById(R.id.upload_speed_output);
        TextView download_speed = (TextView) v.findViewById(R.id.download_speed_output);
        TelephonyManager telephonyManager = (TelephonyManager) this.getContext().getSystemService(Context.TELEPHONY_SERVICE);

        String[] signal = get_network_info(telephonyManager);
        if (signal[0] == null && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            signal = get_network_info(telephonyManager);
        }
        network_type.setText(signal[0]);
        network_technology.setText(signal[1]);
        signal_strength.setText(signal[2]);
        signal_strength_dbm.setText(signal[3]);

        ConnectivityManager connectivityManager = (ConnectivityManager)this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        if (telephonyManager.getDataState() == 2){
            data_enabled.setText("True");
            connectivity_type.setText("Mobile");
        }
        else if(isWiFi){
            data_enabled.setText("False");
            connectivity_type.setText("WiFi");
        }
        else{
            data_enabled.setText("False");
            connectivity_type.setText("None");
        }
        NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        int downSpeed = nc.getLinkDownstreamBandwidthKbps();
        int upSpeed = nc.getLinkUpstreamBandwidthKbps();
        download_speed.setText(String.format(Locale.getDefault(),"%d Kbps",downSpeed));
        upload_speed.setText(String.format(Locale.getDefault(),"%d Kbps",upSpeed));
        return v;
    }

}