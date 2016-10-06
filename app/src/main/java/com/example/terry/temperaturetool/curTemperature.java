package com.example.terry.temperaturetool;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;


/**
 * Created by Terry on 10/5/2016.
 * Features getting the ambient temperature by the phone it-self through SensorManager
 */

public class curTemperature implements SensorEventListener {

    private final Sensor mAmbrient;
    private final SensorManager mSensorManager;
    private final Activity ma;
    private float curTemp;// current temperature
    private boolean format;//format indicator, 0 for °F, 1 for °C
    private TextView TempView;//view display for current temp

    public curTemperature(Activity ma){
        //initialization
        this.ma = ma;
        mSensorManager = (SensorManager)ma.getSystemService(ma.SENSOR_SERVICE);
        mAmbrient = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        TempView = (TextView) ma.findViewById(R.id.CurrentTemp);
        curTemp = 0;
        //checked whether the ambient sensor are available
        if(mAmbrient == null){
            TempView.setText("Device do not support ambient sensor");
        }


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO: updates only greater than 1 degree, saves resource
        if(Math.abs(this.curTemp - event.values[0])> 1.0){
            this.curTemp = event.values[0];
            // display the sensor monitored data.
            long displayValue = Math.round(curTemp);
            if(!format){
                double[] tp = {curTemp};
                new DegreeConverter().convert(tp, false);
                displayValue = Math.round(tp[0]);
            }
            TempView.setText(displayValue + "°");
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    protected void onResume() {
        mSensorManager.registerListener(this, mAmbrient, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        mSensorManager.unregisterListener(this);
    }

    public void setFormat(boolean format){
        // TODO: setting the display format
        this.format = format;

    }
}
