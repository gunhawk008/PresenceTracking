package com.example.unique.presencetracking;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
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
import com.google.gson.Gson;

import static android.app.Activity.RESULT_OK;
import static com.example.unique.presencetracking.constants.REQUEST_CHECK_SETTINGS;

public class ForegroundService extends Service {

    /*Code edited for location*/
    LocationCallback mCallback;
    LocationRequest mLocationRequestHighAccuracy;
    LocationRequest mLocationRequestBalancedPowerAccuracy;
    FusedLocationProviderClient fusedLocationProviderClient;
    String lat,lon;
    /*code ends*/
    private NotificationManager notificationManager;
//    private ThreadGroup basicServiceThreads = new ThreadGroup("BasicServiceGroup");
    private boolean isShowingForegroundNotification;
    private Thread thread;

    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind Called", Toast.LENGTH_SHORT).show();
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(thread!=null){
            thread.stop();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "onCreate Called", Toast.LENGTH_SHORT).show();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        /*Code Edited for Location*/

        /*code starts*/
        mLocationRequestHighAccuracy = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(constants.locationInterval);
        mLocationRequestBalancedPowerAccuracy = new LocationRequest().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY).setInterval(constants.locationInterval);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequestHighAccuracy)
                .addLocationRequest(mLocationRequestBalancedPowerAccuracy);

        SettingsClient client = LocationServices.getSettingsClient(this);

        mCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                //get location updates
                for(Location location:locationResult.getLocations()){
                    Toast.makeText(ForegroundService.this,"From Service -> Latitude : "+location.getLatitude()+" Longitude: "+location.getLongitude(),Toast.LENGTH_SHORT).show();
                    lat=String.valueOf(location.getLatitude());
                    lon=String.valueOf(location.getLongitude());
                }
            }
        };

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//
//            //fusedLocationProviderClient.requestLocationUpdates(mLocationRequestHighAccuracy, mCallback);
//            ActivityCompat.requestPermissions(fusedact.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, constants.REQUEST_location_permission);
//
//        } else {
//            fusedLocationProviderClient.requestLocationUpdates(mLocationRequestBalancedPowerAccuracy, mCallback, null);
//            fusedLocationProviderClient.requestLocationUpdates(mLocationRequestHighAccuracy, mCallback, null);
//        }

        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                fusedLocationProviderClient.requestLocationUpdates(mLocationRequestBalancedPowerAccuracy, mCallback, null);
                fusedLocationProviderClient.requestLocationUpdates(mLocationRequestHighAccuracy,mCallback,null);
            }
        });


        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                if (e instanceof ResolvableApiException) {
//                    // Location settings are not satisfied, but this can be fixed
//                    // by showing the user a dialog.
//                    try {
//                        // Show the dialog by calling startResolutionForResult(),
//                        // and check the result in onActivityResult().
//                        ResolvableApiException resolvable = (ResolvableApiException) e;
//                        resolvable.startResolutionForResult(fusedact.this,
//                                constants.REQUEST_CHECK_SETTINGS);
//                    } catch (IntentSender.SendIntentException sendEx) {
//                        // Ignore the error.
//                    }
//                }

                /*Send notification about "disabled location"*/
            }
        });
        /*code ends here*/

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
       Toast.makeText(this, "onStartCommand Called", Toast.LENGTH_SHORT).show();
//        if (intent.hasExtra("foreground")) {
            //Foreground Service Demo
            if (isShowingForegroundNotification) {
                stopImportantJob();
                stopSelf();//you have to stop it still, it is not enough with stopforeground
            } else
                doImportantJob();

//        } else {
//            //Not foreground Service Demo
//            String message = intent.getExtras().getString("extra");
//            Log.v("BASICSERVICE", "onStartCommand() called, message = " + message + ", startId = " + startId);
//            displayNotificationMessage("something is happening, it is not a foreground service");
//            thread = new Thread(new RunnableWorker(message), "BackgroundThread-1");
//            thread.start();
//        }
        return START_NOT_STICKY;
    }



    private void displayNotificationMessage(String message) {

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(message)
                .setContentText("Touch to turn off service")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Starting up!!!")
                .setContentIntent(contentIntent)
                .setOngoing(false) //by default false
                .build();

        notificationManager.notify(0, notification);
    }

    void doImportantJob() {
        //...  perform important job
        //make this service a foreground service, so it will be as important as the Activity
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0,
                        new Intent(this, MainActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This notification is from a foreground service")
                .setContentText("Touch to open activity handling this service")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Starting up!!!")
                .setContentIntent(contentIntent)
                .setOngoing(false) //Always true in startForeground
                .build();
        startForeground(1992, notification); //notification can not be dismissed until detached,// or stopped service or stopForeground()
        thread = new Thread(new Runnable() {
        public void run(){
            while(true){
                try{
                    String url=constants.PASS_LAT+lat+constants.PASS_LON+lon+constants.PASS_LUID+preferencemgr.getValue(ForegroundService.this,constants.UID);
                    Log.v("URL",url);
                    String result= network.callGet(url);
                    Log.v("Result=",result);

                    Thread.sleep(constants.THREAD_SLEEP);

                } catch (InterruptedException e) {
                    Log.v("THREAD", "... sleep interrupted");
                } catch (General_Exception e) {
                    e.printStackTrace();
                }
            }


        }

        });
        thread.start();
        isShowingForegroundNotification = true;

    }

    private void stopImportantJob() {
        //... Stop your work
        //notificationManager.cancel(1992); Will not work in the notification started with startForeground
        //notificationManager.cancelAll(); neither
        stopForeground(true);
        isShowingForegroundNotification = false;
        if (false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stopForeground(STOP_FOREGROUND_DETACH); //now you can dismiss the notification
                stopForeground(STOP_FOREGROUND_REMOVE);
            }
        }
    }

//    class RunnableWorker implements Runnable {
////        private String counter;
////
////        public RunnableWorker(String msg) {
////            this.counter = msg;
////        }
//
//        public void run() {
//            final String TAG2 = "RunnableWorker:";
//            // do background processing here...
//            try {
//
////                Gson gson=new Gson();
////            Pojo1_location p=new Pojo1_location();
////            p.setUid(uid1);
////            p.setLat(lat1);
////            p.setLon(lon1);
////
////            result=gson.toJson(p,Pojo1_location.class);
//                String url=constants.PASS_LAT+lat+constants.PASS_LON+lon+constants.PASS_LUID+preferencemgr.getValue(ForegroundService.this,constants.UID);
//                String result= network.callGet(url);
//                Toast.makeText(ForegroundService.this,"result:"+result,Toast.LENGTH_LONG).show();
//                Log.v("Result=",result);
//                Log.v(TAG2, "sleeping for 10 seconds. counter = " + counter);
//                Thread.sleep(constants.THREAD_SLEEP);
//                Log.v(TAG2, "... waking up");
//            } catch (InterruptedException e) {
//                Log.v(TAG2, "... sleep interrupted");
//            } catch (General_Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}