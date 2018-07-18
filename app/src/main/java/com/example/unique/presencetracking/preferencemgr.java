package com.example.unique.presencetracking;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;

public class preferencemgr {
    public static final String APP_SETTINGS = "APP_SETTINGS";
//    public static final String UID = "userid";


    public static void deleteValue(Context paramContext, String paramString) {
        SharedPreferences.Editor localEditor = getSharedPreferences(paramContext).edit();
        localEditor.remove(paramString);
        localEditor.commit();
    }

    private static SharedPreferences getSharedPreferences(Context paramContext) {
        return paramContext.getSharedPreferences(APP_SETTINGS, 0);
    }

    public static String getValue(Context paramContext, String paramString) {
        return getSharedPreferences(paramContext).getString(paramString, null);
    }

    public static void setValue(Context paramContext, Map<String, String> paramMap) {
        SharedPreferences.Editor localEditor = getSharedPreferences(paramContext).edit();
        if (paramMap == null) {
            Log.v("Data", "NULL");
            return;
        }

        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            localEditor.putString(entry.getKey(), entry.getValue());
        }
        localEditor.commit();
    }

}
