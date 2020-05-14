package com.restroommap.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IntroAcitivty extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    final private int GPS_OK = 135;
    private LocationManager locationManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        //마시멜로이상 권한체크
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        }


    }

    @TargetApi(23)
    @SuppressLint("NewApi")
    private void checkPermission() {
        List<String> permissionsNeeded = new ArrayList<String>();

        // 퍼미션 체크
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add("location");

        // 퍼미션 요청
        if (permissionsList.size() > 0) {
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        } else {
            getGpsAble();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    getGpsAble();
                } else {
                    checkPermission();
                }

            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @TargetApi(23)
    @SuppressLint("NewApi")
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    private void getGpsAble() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //GPS 설정화면으로 이동
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IntroAcitivty.this);

            // 제목셋팅
            alertDialogBuilder.setTitle("알림");

            // AlertDialog 셋팅
            alertDialogBuilder
                    .setMessage("위치파악을 위해 GPS를 켜시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("켜기",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    startActivityForResult(intent, GPS_OK);
                                    dialog.cancel();


                                }
                            })
                    .setNegativeButton("고정위치",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    MyApplication.mGpsNotAble = false;
                                    dialog.cancel();

                                    new Handler().postDelayed(
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    Intent intent = new Intent(IntroAcitivty.this, MenuActivity.class);
                                                    intent.putExtra("ALL","1");
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }, 1000);

                                }
                            });

            // 다이얼로그 생성
            AlertDialog alertDialog = alertDialogBuilder.create();
            // 다이얼로그 보여주기
            alertDialog.show();
        } else

        {
            MyApplication.mGpsNotAble = true;
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(IntroAcitivty.this, MenuActivity.class);
                            intent.putExtra("ALL","0");
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case GPS_OK:
                getGpsAble();
        }
    }
}
