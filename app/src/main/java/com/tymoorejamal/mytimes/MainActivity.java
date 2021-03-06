package com.tymoorejamal.mytimes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static final int fineLocationPermission = 1;
    static final int courseLocationPermission = 2;
    static final int externalStortagePermission = 3;
    static final int multiplePermissions = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button switchButton1 = findViewById(R.id.b_viewgoodtimes);
        switchButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewGoodTimes.class);
                startActivity(intent);

            }
        });

        Button switchButton2 = findViewById(R.id.b_addgoodtime);
        switchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGoodTime.class);
                startActivity(intent);
            }
        });

    }

    private void requestPermissions(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    multiplePermissions);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    fineLocationPermission);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    courseLocationPermission);
        }
        else{
            ((GlobalVariables) this.getApplication()).setCanUseLocation(true);
            getLocation();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},externalStortagePermission
                    );
        }
        else{
            ((GlobalVariables) this.getApplication()).setCanUseExternalStorage(true);
        }
    }


    private void getLocation(){
        LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location==null){
            Log.d("testGeneral", "null location");
            return;
        }
        ((GlobalVariables) this.getApplication()).setLatitude(location.getLatitude());
        ((GlobalVariables) this.getApplication()).setLongitude(location.getLongitude());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case fineLocationPermission: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ((GlobalVariables) this.getApplication()).setCanUseLocation(true);
                    getLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    ((GlobalVariables) this.getApplication()).setCanUseLocation(false);
                }
                return;
            }
            case courseLocationPermission: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ((GlobalVariables) this.getApplication()).setCanUseLocation(true);
                    getLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    ((GlobalVariables) this.getApplication()).setCanUseLocation(false);
                }
                return;
            }
            case externalStortagePermission: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ((GlobalVariables) this.getApplication()).setCanUseExternalStorage(true);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    ((GlobalVariables) this.getApplication()).setCanUseExternalStorage(false);
                }
                return;
            }

            case multiplePermissions: {
                // If request is cancelled, the result arrays are empty.

                boolean goodToGo = true;

                for (int i = 0; i<grantResults.length; i++){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        goodToGo = false;
                    }
                }

                if (goodToGo) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    ((GlobalVariables) this.getApplication()).setCanUseExternalStorage(true);
                    ((GlobalVariables) this.getApplication()).setCanUseLocation(true);
                    getLocation();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    ((GlobalVariables) this.getApplication()).setCanUseExternalStorage(false);
                    ((GlobalVariables) this.getApplication()).setCanUseLocation(false);
                }
                return;
            }

        }
    }


}
