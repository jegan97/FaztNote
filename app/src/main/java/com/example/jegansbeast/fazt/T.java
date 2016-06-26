package com.example.jegansbeast.fazt;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by JEGAN'S BEAST on 6/25/2016.
 */
public class T {

    public static void toastShort(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void logDatabase(String msg){
        Log.d("faztdatabase",msg);
    }

}
