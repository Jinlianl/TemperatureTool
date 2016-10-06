#include <jni.h>
#include <string>

extern "C"
/*
 * the native C/C++ code to doing Fahrenheit and Celsius conversion,
 * pass a whole list of temperature between Java and C through JNI
 * */
JNIEXPORT jint JNICALL
Java_com_example_terry_temperaturetool_DegreeConverter_convert_1JNI(JNIEnv *env, jobject instance,
                                                                    jdoubleArray list_, jint format) {
    jdouble *c_list = env->GetDoubleArrayElements(list_, NULL);

    jdouble temp = 0.0;

    //get the length of the list and transfer to a C array
    int length = env->GetArrayLength(list_);


    //exception checking
    if (c_list == NULL) {
        return 1; /* exception occurred */
    }
    //todo:degree conversion
    for(int i = 0; i<length;i++){
        temp = c_list[i];
        if(format == 1){        //F° to C°
            c_list[i] = (jfloat)((temp - 32.0)/1.8);
        } else{                 //C° to F°
            c_list[i] = (jfloat)(temp*1.8) + 32.0;
        }

    }
    //sending the list back to Java
    env->ReleaseDoubleArrayElements(list_,c_list,0);


    return 0;
}
