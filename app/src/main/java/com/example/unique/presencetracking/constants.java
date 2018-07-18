package com.example.unique.presencetracking;

/**
 * Created by salma on 15-03-2018.
 */

public class constants {

    public static final String PASS_IMEI="http://www.mellowbytes.com/sp/checkimei.php?imei=";
    public static final String PASS_LAT="http://www.mellowbytes.com/sp/senddata.php?lat=";
    public static final String PASS_LON="&long=";
    public static final String PASS_LUID="&uid=";
    public static final long locationInterval=30000;
    public static final int REQUEST_CHECK_SETTINGS=1;
    public static final int REQUEST_location_permission=2;
    public static final int REQUEST_CODE_IMEI=10;
    public static final String UID="UID";
    public static final long THREAD_SLEEP=40000;
    public static final String SETUP_FLAG="setup_flag";
    public static final String SETUP_OK="setup_ok";
}