package com.example.terry.temperaturetool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/*
*Created by Terry on 10/3/2016.
* The main body of the app
* This app is designed to showing the current ambient temperature by phone's sensor
* and auto generate a list of random temperature for 5 days
*
* It features degree Fahrenheit and Celsius transformation by a simple interactive-UI
*
* */
public class MainActivity extends AppCompatActivity {

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    protected double[] weatherList;//store the temperature data for weekdays
    protected curTemperature mCurTemp;
    protected boolean format;//True for Celsius, False for Fahrenheit
    protected ListView weekDayIndex;
    protected Button FormatChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //todo: initialize the content
        format = true;//initial format is Celsius
        try {
            mCurTemp = new curTemperature(this);
            mCurTemp.setFormat(format);
            weatherList = new WeatherGenerator().getWeatherList();

        }catch (Exception e){
            Toast error = new Toast(this);
            error.makeText(this,"fail to loading temperature",Toast.LENGTH_SHORT);
            error.show();
        };


        // todo:linking the widget id

        weekDayIndex = (ListView) findViewById(R.id.WeekDayList);
        FormatChanger = (Button) findViewById(R.id.button);
        setBottomActivity();
        setTemperature(weatherList,weekDayIndex);


    }

    public void setBottomActivity(){
        // TODO: setup onclick listener for format change botton
        FormatChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo: format switch
                format = format? false:true;
                weatherList = new DegreeConverter().convert(weatherList,format);
                setTemperature(weatherList,weekDayIndex);
                //button display changes relatively
                FormatChanger.setText((format)?"°C":"°F");
                mCurTemp.setFormat(format);
            }
        });
    }

    public void setTemperature(double[] weatherList, ListView Index){
        // TODO: setting or updating the display values

        long[] tempInt = new long[5];
        for(int i = 0;i<5;i++){
            tempInt[i] = Math.round(weatherList[i]);
        }

        String[] items = {
                        "Monday\n"+ tempInt[0] +"°",
                        "Tuesday\n"+ tempInt[1]+"°",
                        "Wednesday\n"+ tempInt[2]+"°",
                        "Thursday\n"+ tempInt[3]+"°",
                        "Friday\n"+ tempInt[4]+"°"
                        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        Index.setAdapter(adapter);


    }

    protected void onResume() {
        super.onResume();
        mCurTemp.onResume();
    }

    protected void onPause() {
        super.onPause();
        mCurTemp.onPause();

    }
}
