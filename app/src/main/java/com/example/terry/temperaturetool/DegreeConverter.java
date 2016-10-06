package com.example.terry.temperaturetool;

/**
 * Created by Terry on 10/3/2016.
 * this class is a tool to make the format conversion
 */

public class DegreeConverter {


    public DegreeConverter(){
        //todo:
    }

    private native int convert_JNI(double[] list, int format);

    public double[] convert(double[] tempList, boolean format){
        //TODO: implement native method of format converter
        //indicate the current format, 1 for °C , 0 for °F
        int isCelsius = format? 1:0;
        if( convert_JNI(tempList,isCelsius) != 0){
            //array loading error
            return null;
        }

        return tempList;
    }


}
