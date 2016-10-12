package com.example.contactbookdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.example.contactbookdemo.R;
import com.example.contactbookdemo.fragment.ContactBookFragment;


public class MainActivityContactBook extends FragmentActivity {

    public static final int PERMISSION_ALL = 1;
    String[] PERMISSION = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contact_book);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            if(!hasPermissions(this, PERMISSION)){
                ActivityCompat.requestPermissions(this, PERMISSION, PERMISSION_ALL);
            }else{
                //call fragment
                ContactBookFragment contactBookFragment = new ContactBookFragment();
                FragmentTransaction trans =getSupportFragmentManager().beginTransaction();
                trans.replace(R.id.fragment_container,contactBookFragment).commitAllowingStateLoss();
            }

        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ALL:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED) {
                    ContactBookFragment contactBookFragment = new ContactBookFragment();
                    FragmentTransaction trans =getSupportFragmentManager().beginTransaction();
                    trans.replace(R.id.fragment_container,contactBookFragment).commitAllowingStateLoss();

                } else {
                    //System.out.println("Contacts permission denied");
                    // permission denied,Disable the
                    // functionality that depends on this permission.
                }
                return;

        }
    }
}
