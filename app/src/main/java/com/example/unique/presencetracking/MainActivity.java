package com.example.unique.presencetracking;

        import android.annotation.SuppressLint;
        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.app.KeyguardManager;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.hardware.fingerprint.FingerprintManager;
        import android.Manifest;
        import android.os.Build;
        import android.os.Bundle;
        import android.security.keystore.KeyGenParameterSpec;
        import android.security.keystore.KeyPermanentlyInvalidatedException;
        import android.security.keystore.KeyProperties;
        import android.support.annotation.NonNull;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v4.app.ActivityCompat;
        import android.telephony.TelephonyManager;
        import android.util.Log;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.iid.FirebaseInstanceId;

        import java.io.IOException;
        import java.security.InvalidAlgorithmParameterException;
        import java.security.InvalidKeyException;
        import java.security.KeyStore;
        import java.security.KeyStoreException;
        import java.security.NoSuchAlgorithmException;
        import java.security.NoSuchProviderException;
        import java.security.UnrecoverableKeyException;
        import java.security.cert.CertificateException;
        import javax.crypto.Cipher;
        import javax.crypto.KeyGenerator;
        import javax.crypto.NoSuchPaddingException;
        import javax.crypto.SecretKey;

        import static com.google.android.gms.internal.zzbfq.NULL;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Token"+ FirebaseInstanceId.getInstance().getToken());
        if (preferencemgr.getValue(this, constants.SETUP_FLAG) != null) {
            Intent i = new Intent(this, Main2Activity.class);
            Toast.makeText(this, "SetUp Already Done before.. " + preferencemgr.getValue(this, constants.SETUP_FLAG), Toast.LENGTH_LONG).show();
            startActivity(i);
        } else {
//            if (android.os.Build.VERSION.SDK_INT >= 26) {
//                Toast.makeText(this, getIMEI(this), Toast.LENGTH_LONG).show();
//                new async_task(this).execute(constants.PASS_IMEI + getIMEI(this));
//
//            } else {
//                Toast.makeText(this, getIMEI(), Toast.LENGTH_LONG).show();
//                new async_task(this).execute(constants.PASS_IMEI + getIMEI());
//            }
            check_perm();
        }


    }


    void check_perm(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Toast.makeText(this , "Not Granted",Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_PHONE_STATE},constants.REQUEST_CODE_IMEI);
        }else {
            Toast.makeText(this , "Permission already granted",Toast.LENGTH_SHORT).show();
            if(android.os.Build.VERSION.SDK_INT >= 26)
            {
                Toast.makeText(this,getIMEI(this),Toast.LENGTH_LONG).show();
                new async_task(this).execute(constants.PASS_IMEI+getIMEI(this));
                //                        imei.setText(getIMEI(this));

            }
            else{
                Toast.makeText(this,getIMEI(),Toast.LENGTH_LONG).show();
                new async_task(this).execute(constants.PASS_IMEI+getIMEI());
                //                        imei.setText(getIMEI());
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case constants.REQUEST_CODE_IMEI: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this , "permission granted",Toast.LENGTH_SHORT).show();
                    // permission was granted, do the IMEI code.
                    if(android.os.Build.VERSION.SDK_INT >= 26)
                    {
                        Toast.makeText(this,getIMEI(this),Toast.LENGTH_LONG).show();
                        new async_task(this).execute(constants.PASS_IMEI+getIMEI(this));
                        //                        imei.setText(getIMEI(this));

                    }
                    else{
                        Toast.makeText(this,getIMEI(),Toast.LENGTH_LONG).show();
                        new async_task(this).execute(constants.PASS_IMEI+getIMEI());
                        //                        imei.setText(getIMEI());
                    }



                } else {
//                    Toast.makeText(this , "Permission not available",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }

    @TargetApi(26)
    public String getIMEI(Activity activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    PackageManager.PERMISSION_GRANTED);
        }

        return telephonyManager.getImei();

    }

    @TargetApi(23)
    public String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE},
                    PackageManager.PERMISSION_GRANTED);
        }

        return telephonyManager.getDeviceId();

    }




}


