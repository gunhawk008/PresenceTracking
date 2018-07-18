package com.example.unique.presencetracking;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by salma on 13-03-2018.
 */

public class async_task extends AsyncTask<String,Void,Void> {
    String result;
    Context mContext;
    public async_task(Context context){
    mContext=context;
    }

    @Override
    protected Void doInBackground(String... strings) {
        if (strings[0]!=null){
            Log.v("error","param not null"+strings[0]);
        }else{
            Log.v("error","param null");
        }
        try{
            result= network.callGet(strings[0]);
            Gson gson=new Gson();
            Pojo1 p=gson.fromJson(result,Pojo1.class);
            //Toast.makeText(this,"result:"+p.getResult()+" Uid:"+p.getUid(),Toast.LENGTH_LONG).show();
            Log.v("Pojo Value",p.getUserid());
            if(p.getResult().equals("success")){
                HashMap< String,String> data=new HashMap< String,String>();
                data.put(constants.UID,p.getUserid());
                data.put(constants.SETUP_FLAG,constants.SETUP_OK);
                preferencemgr.setValue(mContext, data);

            }else {
                //Toast.makeText(mContext,"UnAuthorized Device",Toast.LENGTH_LONG).show();
            }


        }catch(Exception e){
            Log.v("error",e.getMessage().toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.v("result",result);
        Intent i=new Intent(mContext,Main2Activity.class);
        Toast.makeText(mContext,"First Time UID received"+preferencemgr.getValue(mContext, constants.UID),Toast.LENGTH_LONG).show();
        mContext.startActivity(i);
    }
}
