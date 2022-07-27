package com.example.mydeviceinformation.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.app.ActivityManager;
//import android.content.Context;

import androidx.fragment.app.Fragment;
//import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydeviceinformation.R;

import java.util.Locale;

public class Device extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Device() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Device.
     */
    // TODO: Rename and change types and number of parameters
    public static Device newInstance(String param1, String param2) {
        Device fragment = new Device();
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
        View v = inflater.inflate(R.layout.fragment_device, container, false);
        TextView os_version = (TextView) v.findViewById(R.id.os_version_output);
        TextView api_level = (TextView) v.findViewById(R.id.api_level_output);
        TextView device = (TextView) v.findViewById(R.id.device_output);
        TextView model = (TextView) v.findViewById(R.id.model_output);
        TextView manufacturer = (TextView) v.findViewById(R.id.manufacturer_output);
        TextView hardware = (TextView) v.findViewById(R.id.hardware_output);
        TextView serial_number = (TextView) v.findViewById(R.id.serial_number_output);
        TextView screen_size = (TextView) v.findViewById(R.id.screen_size_output);
        TextView screen_resolution = (TextView) v.findViewById(R.id.screen_resolution_output);
        TextView screen_density = (TextView) v.findViewById(R.id.screen_density_output);
        TextView total_ram = (TextView) v.findViewById(R.id.total_ram_output);
        TextView available_ram = (TextView) v.findViewById(R.id.available_ram_output);
        TextView internal_storage = (TextView) v.findViewById(R.id.internal_storage_output);
        TextView available_storage = (TextView) v.findViewById(R.id.available_storage_output);
        TextView os_security_patch = (TextView) v.findViewById(R.id.os_security_patch_output);
        TextView os_release_codename = (TextView) v.findViewById(R.id.os_release_codename_output);
        TextView device_uuid = (TextView) v.findViewById(R.id.device_uuid_output);


        try {
            //  Block of code to try
            os_version.setText(Build.VERSION.BASE_OS);
            api_level.setText((Build.VERSION.SDK));
            device.setText(Build.DEVICE);
            model.setText(Build.MODEL);
            manufacturer.setText(Build.MANUFACTURER);
            hardware.setText(Build.HARDWARE);
            // getting error in the below code
            //serial_number.setText(Build.getSerial());
            serial_number.setText(R.string.NA);
            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;
            int densityDpi = (int)(metrics.density * 160f);
            double wi=(double)width/(double)metrics.xdpi;
            double hi=(double)height/(double)metrics.ydpi;
            double x = Math.pow(wi,2);
            double y = Math.pow(hi,2);
            double screenInches = Math.sqrt(x+y);
            screen_size.setText(String.format(Locale.getDefault(),"%.2f inches",screenInches));
            screen_resolution.setText(String.format(Locale.getDefault(),"%d x %d pixels",height,width));
            screen_density.setText(String.format(Locale.getDefault(),"%d dpi",densityDpi));

            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) this.getActivity().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            double availableMegs = mi.availMem/1048576.0;
            double totalMeg = mi.totalMem/1048576.0;

            total_ram.setText(String.format(Locale.getDefault(),"%.2f MB",totalMeg));
            available_ram.setText(String.format(Locale.getDefault(),"%.2f MB",availableMegs));

            StatFs internalStatFs = new StatFs( Environment.getRootDirectory().getAbsolutePath() );
            double internalTotal = ( internalStatFs.getBlockCountLong() * internalStatFs.getBlockSizeLong() ) /1048576.0;
            double internalFree = ( internalStatFs.getAvailableBlocksLong() * internalStatFs.getBlockSizeLong() ) /1048576.0;

            internal_storage.setText(String.format(Locale.getDefault(),"%.2f MB",internalTotal));
            available_storage.setText(String.format(Locale.getDefault(),"%.2f MB",internalFree));

            os_security_patch.setText(Build.VERSION.SECURITY_PATCH);
            os_release_codename.setText(Build.VERSION.CODENAME);

            TelephonyManager telephonyManager = (TelephonyManager) this.getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            String imei = telephonyManager.getImei();

            device_uuid.setText(imei);
            //device_uuid.setText(TelephonyManager.getDeviceId());
        }
        catch(Exception e){
            System.out.println(e.toString());
            Toast.makeText(this.getContext().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        return v;
    }
}