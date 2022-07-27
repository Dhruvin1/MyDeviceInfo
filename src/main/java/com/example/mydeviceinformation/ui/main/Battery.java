package com.example.mydeviceinformation.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydeviceinformation.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Battery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Battery extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Battery() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Battery.
     */
    // TODO: Rename and change types and number of parameters
    public static Battery newInstance(String param1, String param2) {
        Battery fragment = new Battery();
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
        View v = inflater.inflate(R.layout.fragment_battery, container, false);
        TextView battery_percentage = (TextView) v.findViewById(R.id.battery_percentage_output);
        TextView battery_capacity = (TextView) v.findViewById(R.id.battery_capacity_output);
        TextView battery_state = (TextView) v.findViewById(R.id.battery_state_output);
        TextView battery_temperature = (TextView) v.findViewById(R.id.battery_temperature_output);
        TextView battery_technology = (TextView) v.findViewById(R.id.battery_technology_output);
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.getContext().registerReceiver(null, ifilter);
        try{

            int temperature = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int technology = batteryStatus.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, -1);

            battery_temperature.setText(String.format(Locale.getDefault(),"%d F",temperature));
            battery_technology.setText(String.format(Locale.getDefault(),"%d",technology));
        }
        catch (Exception e) {
            System.out.println(e.toString());
            Toast.makeText(this.getContext().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        try{
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            battery_state.setText(String.format(Locale.getDefault(),"%d",status));
        }
        catch (Exception e){
            System.out.println(e.toString());
            Toast.makeText(this.getContext().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        try{
            BatteryManager mBatteryManager = (BatteryManager) this.getContext().getSystemService(Context.BATTERY_SERVICE);
            int chargeCounter = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            int capacity = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            float capacity_mah = 0;
            if(capacity != 0) {
                capacity_mah = (chargeCounter / capacity) * 100;
            }
            battery_capacity.setText(String.format(Locale.getDefault(),"%.0f mAh",capacity_mah));
        }
        catch (Exception e){
            System.out.println(e.toString());
            Toast.makeText(this.getContext().getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
        try{
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float)scale;
            battery_percentage.setText(String.format(Locale.getDefault(),"%.0f",batteryPct));
        }
        catch (Exception e){
            System.out.println(e.toString());
            Toast.makeText(this.getContext().getApplicationContext(), "Error in Battery " +
                    "Calculation"+e.toString(), Toast.LENGTH_LONG).show();

        }
        return v;
    }
}