package com.example.unique.presencetracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Saiyed Farhan on 17-Mar-18.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver{

    public static boolean flags=false;
    @Override
    public void onReceive(Context context, Intent intent) {

       /*Reverification*/

        long mlseconds=(long)intent.getLongExtra("mili",0);
        Toast.makeText(context,"Miliseconds"+mlseconds,Toast.LENGTH_LONG).show();
        if((Calendar.getInstance().get(Calendar.MILLISECOND)-mlseconds)<=300000){

            Intent i =new Intent(context,Main3Activity.class);
            context.startActivity(i);
            Toast.makeText(context,"Alarm Rings....."+(Calendar.getInstance().get(Calendar.MILLISECOND)-mlseconds),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(context,"Alarm more than 5 mins old",Toast.LENGTH_LONG).show();

        }


//
//


        /*Reverification Ends*/
        //on authentication success
//        flags=true;
//
//        if(flags){
//
//        }

    }
}
