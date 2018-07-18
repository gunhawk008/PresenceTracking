package com.example.unique.presencetracking;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by salma on 24-03-2018.
 */

public class MyJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        // Do some work here
        Toast.makeText(this,"Job dispatcher",Toast.LENGTH_LONG).show();
        Log.v("Job","Working");

        new Thread(new Runnable() {
            @Override
            public void run() {



                dbHelper _dbHelper = new dbHelper(getApplicationContext(),"",null,12);
//                SQLiteDatabase _db1 = _dbHelper.getWritableDatabase();
//                String _insert = "insert into " + dbHelper.tbl_Salesman + "(" +dbHelper.salesman_code + "," +
//                       dbHelper.salesman_name + "," + dbHelper.salesman_status + ")values ('123','2018-02-21 12:12','Success')";
//
//                SQLiteStatement localSQLiteStatement = _db1.compileStatement(_insert);
//                _db1.beginTransaction();
//                long l = localSQLiteStatement.executeInsert();
//                String S  = String.valueOf(l);
//                Log.v("data",S);
//                _db1.setTransactionSuccessful();
//                _db1.endTransaction();

//
//                SQLiteDatabase _db2 = _dbHelper.getWritableDatabase();
//                String _insert2 = "insert into " + dbHelper.tbl_Salesman + "(" +dbHelper.salesman_code + "," +
//                        dbHelper.salesman_name +","+ dbHelper.salesman_status + ","+ dbHelper.salesman_upload_status +")values ('13','2018-02-21 12:12','Success','')";
//
//                SQLiteStatement localSQLiteStatement2 = _db2.compileStatement(_insert2);
//                _db2.beginTransaction();
//                long l2 = localSQLiteStatement2.executeInsert();
//                String S2  = String.valueOf(l2);
//                Log.v("data",S2);
//                _db2.setTransactionSuccessful();
//                _db2.endTransaction();
//                _db2.close();












                SQLiteDatabase _db = _dbHelper.getReadableDatabase();

                String query = "select " + dbHelper.salesman_code+ " from " + dbHelper.tbl_Salesman + " where " + dbHelper.salesman_status+ " = " + "'Success' AND "+dbHelper.salesman_upload_status+" = ''";
                int i=0;
                String timerid="";
                Cursor _storeName = _db.rawQuery(query, null);

                while (_storeName.moveToNext()) {
                    i=i+1;
                    if(i==1){
                        timerid=_storeName.getString(0);
                    }else{
                        timerid = timerid+","+_storeName.getString(0);
                    }

                }
                String upload="{\"timer\":[" + timerid+"]}";

                _storeName.close();
                _db.close();
                Log.v("UPLOAD STRING ->",upload);


            }
        }).start();



        return false; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Toast.makeText(this,"Job dispatcher stopped",Toast.LENGTH_LONG).show();
        return false; // Answers the question: "Should this job be retried?"
    }
}
