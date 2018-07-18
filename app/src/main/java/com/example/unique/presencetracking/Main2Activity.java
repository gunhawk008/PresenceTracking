package com.example.unique.presencetracking;

import android.*;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Handler;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.example.unique.presencetracking.constants.REQUEST_CHECK_SETTINGS;

public class Main2Activity extends AppCompatActivity {
    Button ForgroundServicebtn;
    boolean locationAvailable=false;
    String sf;
    /*Code edited for location*/

    LocationRequest mLocationRequestHighAccuracy;
    LocationRequest mLocationRequestBalancedPowerAccuracy;
    /*code ends*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*code start here for location*/
        mLocationRequestHighAccuracy = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(constants.locationInterval);
        mLocationRequestBalancedPowerAccuracy = new LocationRequest().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).setInterval(constants.locationInterval);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequestHighAccuracy)
                .addLocationRequest(mLocationRequestBalancedPowerAccuracy);
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...

                // write code for starting the service
                locationAvailable=true;
            }
        });


        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(Main2Activity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
        /*code ends here*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, constants.REQUEST_location_permission);
        }
        ForgroundServicebtn = (Button) findViewById(R.id.logoutbtn);
        if (isMyServiceRunning(com.example.unique.presencetracking.ForegroundService.class)) {
            //Toast.makeText(this, "service running", Toast.LENGTH_LONG).show();

            ForgroundServicebtn.setText("Log Out");

        } else {
            //Toast.makeText(this, "service not running", Toast.LENGTH_SHORT).show();

            ForgroundServicebtn.setText("Log In");
        }


        /*Code commented by karna*/

        //Location
        /*ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        final Thread thread1 = new Thread()
        {
            @Override
            public void run() {
                try {
                    while(true) {
//                        Toast.makeText(getBaseContext(), "Running Thread...", Toast.LENGTH_LONG).show();
                        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                        Calendar calobj = Calendar.getInstance();
                        //System.out.println(df.format(calobj.getTime()));
                        Log.v("Running thread",df.format(calobj.getTime()));
//                        getLocation();
                        sleep(10000);


                    }
                } catch (InterruptedException e) {
//                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
                    Log.v("thread","exception");
                }
            }
        };
        */
        /*Code commented by karna*/
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                thread1.start();
//
//            }
//        });


//        request_perm();
//        String imei;
//        Toast.makeText(this, getIMEI(this), Toast.LENGTH_LONG);
//        if (android.os.Build.VERSION.SDK_INT >= 26) {
//            imei = getIMEI().toString();
//            Toast.makeText(this,imei+"im26",Toast.LENGTH_LONG).show();
//        } else {
//            imei = getIMEI(this).toString();
//            Toast.makeText(this,imei+"im23",Toast.LENGTH_LONG).show();
//        }
//        new async_task().execute(constants.PASS_IMEI+imei);
//
//        String[] flags = new String[]{constants.UID};
//
//        for (String flag : flags) {
//            sf = preferencemgr.getValue(this, flag);
//            if (sf != null) {
//                Toast.makeText(this,"User ID from Preference Manager: "+sf,Toast.LENGTH_LONG).show();
//
//            } else {
//                Toast.makeText(this,"No User Id",Toast.LENGTH_LONG).show();
//
//            }
//        }

///* Below code to save preference */
//        HashMap< String,String> data=new HashMap< String,String>();
//        data.put(preferencemgr.UID,"120");
//        preferencemgr.setValue(this, data);
//
//
///*below code to access stored preference*/
//        Toast.makeText(this,preferencemgr.getValue(this,preferencemgr.UID),Toast.LENGTH_LONG).show();

//        Bundle bundle = getIntent().getExtras();
//        String message = bundle.getString("remoteMessage");
//        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();



        /*database message store starts*/
        boolean Pflag=true;
        dbHelper dbh=new dbHelper(this,"pt",null,1);

        String json="[{\"timerid\":\"151515\",\"time\":\"51551\"},{\"timerid\":\"151215\",\"time\":\"51121\"}]";
        Gson gson=new Gson();
        pojoTimer[] arr_timer=gson.fromJson(json,pojoTimer[].class);


        //        List<pojoTimer> _arrTimer=new ArrayList<>();
//        pojoTimer p1=new pojoTimer();
//        p1.setTime("1554554");
//        p1.setTimerid("123");
//        _arrTimer.add(p1); // object added to array list
//        pojoTimer p2=new pojoTimer();
//        p2.setTime("155455456");
//        p2.setTimerid("1234");
//        _arrTimer.add(p2);

        dbh.insertSalesmen(arr_timer);
//        if(Pflag==true)
//        {
//            dbh.update();
//        }
//        else
//        {
//            dbh.update1();
//        }
        for(pojoTimer pj:arr_timer){

            // Set Alarm
            pj.getTime();
            pj.getTimerid();
        }
        /*Receive Data Message FCM*/
//        FirebaseInstanceId fd=new FirebaseInstanceId();
        Toast.makeText(this,"Token :"+ FirebaseInstanceId.getInstance().getToken(),Toast.LENGTH_LONG).show();
        TextView textView = (TextView) findViewById(R.id.textView);
        if(getIntent().getExtras() != null)
        {

            for(String key : getIntent().getExtras().keySet())
            {
                if(key.equals("timer"))
                {
                    textView.setText(getIntent().getExtras().getString(key));
                }
            }
        }
        /*Receive Data Message FCM Ends*/

//https://www.youtube.com/watch?v=PGMt11DK2uo
        /*database message store ends*/




        /*Alarm manager starts*/



        String _date="2018-03-21 17:20:30";

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = (Date)formatter.parse(_date);
            date.getTime();
            startAlarm(date.getTime(),10);
            //startAlarm(date.getTime()+30000,11);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Calendar calendar = Calendar.getInstance();
//
//        if(Build.VERSION.SDK_INT >= 23){
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH),
//            //Hours
//            //Minutes
//            0
//        };

        /*set Alarm Ends*/

    }


















    @Override
    protected void onResume() {
        super.onResume();
        if(isMyServiceRunning(com.example.unique.presencetracking.ForegroundService.class)){
           Toast.makeText(this,"serviec running",Toast.LENGTH_LONG).show();

            ForgroundServicebtn. setText("Log Out");

        }
        else{
           Toast.makeText(this,"serviec not running",Toast.LENGTH_SHORT).show();

            ForgroundServicebtn.setText("Log In");
        }
    }

    public void foregroundService(View view) {

        if(((Button)view.findViewById(R.id.logoutbtn)).getText().toString().equals("Log Out")){
            //startForegroundService(new Intent(getApplicationContext(),ForegroundService.class));
            stopService(new Intent(getApplicationContext(), com.example.unique.presencetracking.ForegroundService.class));
            if(isMyServiceRunning(com.example.unique.presencetracking.ForegroundService.class)){
               Toast.makeText(this,"service running",Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(this,"service not running",Toast.LENGTH_SHORT).show();
            }
            ((Button)view.findViewById(R.id.logoutbtn)).setText("Log In");
        }else {
            if(locationAvailable){
                startService(new Intent(this, com.example.unique.presencetracking.ForegroundService.class));
                if(isMyServiceRunning(com.example.unique.presencetracking.ForegroundService.class)){
                    Toast.makeText(this,"service running",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(this,"serviec not running",Toast.LENGTH_SHORT).show();
                }
                ((Button)view.findViewById(R.id.logoutbtn)).setText("Log Out");
            }

        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    // LOCATION
    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your Location")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    @SuppressLint("MissingPermission")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case constants.REQUEST_CHECK_SETTINGS:
                if (resultCode == RESULT_OK) {
                    locationAvailable=true;
                    // write code to start the service



//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        ActivityCompat.requestPermissions(fusedact.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, constants.REQUEST_location_permission);
//                    }else{
//                    fusedLocationProviderClient.requestLocationUpdates(mLocationRequestBalancedPowerAccuracy, mCallback, null);
//                    fusedLocationProviderClient.requestLocationUpdates(mLocationRequestHighAccuracy,mCallback,null);
//                    }

                }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case constants.REQUEST_location_permission :
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                    Toast.makeText(this , "permission granted",Toast.LENGTH_SHORT).show();


                    // write code here to start the service



//                    fusedLocationProviderClient.requestLocationUpdates(mLocationRequestBalancedPowerAccuracy, mCallback, null);
//                    fusedLocationProviderClient.requestLocationUpdates(mLocationRequestHighAccuracy,mCallback,null);
                } else {
                    Toast.makeText(this , "Permission not available",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }
    }

    private void startAlarm(long timeInMillis,int rcode) {
        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;
        intent = new Intent(Main2Activity.this,AlarmNotificationReceiver.class);
        intent.putExtra("mili",timeInMillis);
        Toast.makeText(this,"timeinmillis"+intent.getLongExtra("mili",0),Toast.LENGTH_LONG).show();

        pendingIntent = PendingIntent.getBroadcast(this,rcode,intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis,pendingIntent);
    }


}