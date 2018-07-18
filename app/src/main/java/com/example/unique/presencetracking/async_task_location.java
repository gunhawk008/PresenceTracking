package com.example.unique.presencetracking;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by salma on 13-03-2018.
 */

public class async_task_location extends AsyncTask<String,Void,Void> {
    String result;
    Context mContext;
    public async_task_location(Context context){
        mContext=context;
    }
//String lat1, lon1, uid1;
//    public void async_task_location(String uid, String lat, String lon){
//        lat1=lat;
//        lon1=lon;
//        uid1=uid;
//    }
    @Override
    protected Void doInBackground(String... strings) {
        if (strings[0]!=null){
            Log.v("error","param not null"+strings[0]);
        }else{
            Log.v("error","param null");
        }
        try{
             Gson gson=new Gson();
//            Pojo1_location p=new Pojo1_location();
//            p.setUid(uid1);
//            p.setLat(lat1);
//            p.setLon(lon1);
//
//            result=gson.toJson(p,Pojo1_location.class);
            result= network.callGet(strings[0]);
            Toast.makeText(mContext,"result:"+result,Toast.LENGTH_LONG).show();
            Log.v("Result=",result);
//            if(p1.getResult().equals("success")){
//                HashMap< String,String> data=new HashMap< String,String>();
//                data.put(preferencemgr.UID,p1.getUid());
//                preferencemgr.setValue(mContext, data);
//
//            }else {
//                Toast.makeText(mContext,"UnAuthorized Device",Toast.LENGTH_LONG).show();
//            }


        }catch(Exception e){
            Log.v("error",e.getMessage().toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.v("result",result);
    }
}
