package com.example.unique.presencetracking;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class Main4Activity extends AppCompatActivity {
    public boolean Pflag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

//        dbHelper dbh=new dbHelper(this,"pt",null,1);
//
//
//        if(Pflag==true)
//        {
//            dbh.update();
//        }

       final int periodicity = (int) TimeUnit.HOURS.toSeconds(24); // Every 12 hours periodicity expressed as seconds
        final int toleranceInterval = (int) TimeUnit.MINUTES.toSeconds(1); // a small(ish) window of time when triggering is OK
//
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService.class) // the JobService that will be called
//                .setTag("my-unique-tag")        // uniquely identifies the job
//                .setLifetime(Lifetime.FOREVER)
//                .setTrigger(Trigger.executionWindow(periodicity, periodicity + toleranceInterval))
//                .build();
//
//
//
//
//        dispatcher.mustSchedule(myJob);



        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString("some_key", "some_value");

        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(MyJobService.class)
                // uniquely identifies the job
                .setTag("my-unique-tag")
                // one-off job
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(periodicity, periodicity + toleranceInterval))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setExtras(myExtrasBundle)
                .build();

        dispatcher.mustSchedule(myJob);


    }
}
