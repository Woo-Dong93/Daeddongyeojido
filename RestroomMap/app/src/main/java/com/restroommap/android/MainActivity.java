package com.restroommap.android;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mmap; // 구글 맵 객체
    private Button btn1 = null;
    private Dialog optionDialog;
    private Dialog progressDialog;

    XmlResourceParser xrp; // xml 파서객체
    private ArrayList<DataDto> mDataList = null; // 배열
    private DataDto mDataDto = null;
    private boolean dataResult1 = false;
    private boolean dataResult2 = false;
    private boolean dataResult3 = false;
    private boolean dataResult4 = false;
    private boolean dataResult5 = false;
    private boolean dataResult6 = false;
    private boolean dataResult7 = false;
    private boolean dataResult8 = false;
    private boolean dataResult9 = false;
    private boolean dataResult10 = false;
    private boolean dataResult11 = false;

    private double latPoint;
    private double lngPoint;

    private double myLat = 0;
    private double myLng = 0;

    private String myJuso = null;

    private boolean kedault = false;
    private boolean adault = false;
    private boolean mAllways = true;
    private int mDistance = 100;

    private boolean matSetting = false;

    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 구글 맵 연결
        MapFragment mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 다이얼로그 생성
                optionDialog = new Dialog(MainActivity.this);
                optionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                optionDialog.setContentView(R.layout.custom_dialog_option);

                TextView cancleText = (TextView) optionDialog.findViewById(R.id.cancleText);
                TextView okText = (TextView) optionDialog.findViewById(R.id.okText);

                CheckBox audlt_btn = (CheckBox) optionDialog.findViewById(R.id.audlt_btn);
                CheckBox kiaudlt_btn = (CheckBox) optionDialog.findViewById(R.id.kiaudlt_btn);

                RadioGroup radioGroup = (RadioGroup) optionDialog.findViewById(R.id.radioGroup);
                RadioButton public_btn = (RadioButton) optionDialog.findViewById(R.id.public_btn);
                RadioButton share_btn = (RadioButton) optionDialog.findViewById(R.id.share_btn);

                RadioGroup radioGroup2 = (RadioGroup) optionDialog.findViewById(R.id.radioGroup2);
                RadioButton mi_100 = (RadioButton) optionDialog.findViewById(R.id.mi_100);
                RadioButton mi_500 = (RadioButton) optionDialog.findViewById(R.id.mi_500);
                RadioButton mi_1000 = (RadioButton) optionDialog.findViewById(R.id.mi_1000);

                //두번째 옵션 선택시 변경
                if (kedault) {
                    kiaudlt_btn.setChecked(true);
                }
                if (adault) {
                    audlt_btn.setChecked(true);
                }
                if (mDistance == 100) {
                    mi_100.setChecked(true);
                } else if (mDistance == 500) {
                    mi_500.setChecked(true);
                } else if (mDistance == 1000) {
                    mi_1000.setChecked(true);
                }
                if (mAllways == true) {
                    public_btn.setChecked(true);
                } else {
                    share_btn.setChecked(true);
                }
                // 공용 선택시 옵션 변경
                audlt_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            adault = true;
                        } else {
                            adault = false;
                        }
                    }
                });
                // 공용 선택시 옵션 변경
                kiaudlt_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            kedault = true;
                        } else {
                            kedault = false;
                        }
                    }
                });
                // 공용 선택시 옵션 변경
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.public_btn:
                                mAllways = true;
                                break;
                            case R.id.share_btn:
                                mAllways = false;
                                break;
                        }
                    }
                });
                // 공용 선택시 옵션 변경
                radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.mi_100:
                                mDistance = 100;
                                break;
                            case R.id.mi_500:
                                mDistance = 500;
                                break;
                            case R.id.mi_1000:
                                mDistance = 1000;
                                break;
                        }
                    }
                });


                cancleText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionDialog.dismiss();

                    }
                });
                okText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        optionDialog.dismiss();
                        setUpMap();

                    }
                });

                optionDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                optionDialog.show();
            }
        });



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 지도가 싱크되면 맵을 준비
        mmap = googleMap;

        // 맵 타입
        mmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //내 위치 허용
        mmap.setMyLocationEnabled(true);
        mmap.setTrafficEnabled(true);
        mmap.setIndoorEnabled(true);
        mmap.setBuildingsEnabled(true);
        mmap.getUiSettings().setZoomControlsEnabled(true);

        //내 위치 가져올지 , 고정 위치 가져올지 startactivity서 startAcitivity 할때 , putExtra로 값을 넘기는 객체 확인
        if (getIntent().getStringExtra("ALL") != null) {
            if (getIntent().getStringExtra("ALL").equals("0")) {

                // 내 지도 가져올려면 시간이 걸려 , 프로그래스바로 체크
                progressDialog = new Dialog(MainActivity.this);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.setContentView(R.layout.custom_progress);

                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                progressDialog.show();

                // 로케이션 매니져로 내 위치가져오기
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        myLat = location.getLatitude();
                        myLng = location.getLongitude();

                        final LatLng myLocation = new LatLng(myLat, myLng);

                        drawMarker(myLocation);

                        if(!matSetting ) {
                            GetLocations();
                            matSetting = true;
                        }
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };

                // Register the listener with the Location Manager to receive location updates
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                // 수동으로 위치 구하기
                String locationProvider = LocationManager.GPS_PROVIDER;
                Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
                if (lastKnownLocation != null) {

                    myLat = lastKnownLocation.getLatitude();
                    myLng = lastKnownLocation.getLongitude();





                }



            } else {
                Resources res = getResources(); //res 폴더 관리자(Resources 객체) 얻어오기
                xrp = res.getXml(R.xml.chungcheongnamdo_cheonan); //XmlResourcesParser 얻어오기
                getXmlData(); //아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기



            }
        }

    }

    private void setUpMap() {
        // 맵을 다시 보이려면 한번 제거후 다시생성
        mmap.clear();

        //카메라 업데이트
        CameraUpdate update;
        if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
            update = CameraUpdateFactory.newLatLng(new LatLng(myLat, myLng));

        } else {
            update = CameraUpdateFactory.newLatLng(new LatLng(MyApplication.lat, MyApplication.lng));
        }

        // 카메라이동
        mmap.moveCamera(update);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mmap.animateCamera(zoom);





        //데이터가 있으면 데이터를 비교하여 마커표시
        if (mDataList != null && mDataList.size() > 0) {
            for (int i = 0; i < mDataList.size(); i++) {
                // 마커 표시하기 : 위치지정, 풍선말 설정
                if (!mDataList.get(i).getData6().contains("-") && !mDataList.get(i).getData5().contains("-")) {
                    if (mAllways == true) {
                        //분리 화장실인지 , 공용 화장실인지
                        if (mDataList.get(i).getData6().equals("Y")) {
                            if (kedault == true && adault == true) {
                                //장애인인지  , 아동인지 체크
                                if ((Integer.parseInt(mDataList.get(i).getData1()) + Integer.parseInt(mDataList.get(i).getData3()) + Integer.parseInt(mDataList.get(i).getData10())) > 0 && (Integer.parseInt(mDataList.get(i).getData2()) + Integer.parseInt(mDataList.get(i).getData4()) + Integer.parseInt(mDataList.get(i).getData11())) > 0) {
                                    double sLat;
                                    double sLng;
                                    if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
                                        sLat = Double.valueOf(myLat);
                                        sLng = Double.valueOf(myLng);
                                    } else {
                                        sLat = Double.valueOf(MyApplication.lat);
                                        sLng = Double.valueOf(MyApplication.lng);
                                    }

                                    double eLat = Double.valueOf(mDataList.get(i).getData6());
                                    double eLng = Double.valueOf(mDataList.get(i).getData5());
                                    //좌표간 거리비교
                                    String distance = calcDistance(sLat, sLng, eLat, eLng);

                                    //지정위치보다 안에만 마커찍어주기
                                    if (Integer.parseInt(distance) < mDistance) {
                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(new LatLng(Double.valueOf(mDataList.get(i).getData6()), Double.valueOf(mDataList.get(i).getData5())))
                                                .title(mDataList.get(i).getData8())
                                                .snippet(mDataList.get(i).getData9());

                                        mmap.addMarker(markerOptions).showInfoWindow();
                                    }

                                }
                                //장애인만 표시
                            } else if (kedault == true && adault == false) {
                                if ((Integer.parseInt(mDataList.get(i).getData1()) + Integer.parseInt(mDataList.get(i).getData3()) + Integer.parseInt(mDataList.get(i).getData10())) > 0) {
                                    double sLat;
                                    double sLng;
                                    if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
                                        sLat = Double.valueOf(myLat);
                                        sLng = Double.valueOf(myLng);
                                    } else {
                                        sLat = Double.valueOf(MyApplication.lat);
                                        sLng = Double.valueOf(MyApplication.lng);
                                    }

                                    double eLat = Double.valueOf(mDataList.get(i).getData6());
                                    double eLng = Double.valueOf(mDataList.get(i).getData5());
                                    String distance = calcDistance(sLat, sLng, eLat, eLng);

                                    if (Integer.parseInt(distance) < mDistance) {
                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(new LatLng(Double.valueOf(mDataList.get(i).getData6()), Double.valueOf(mDataList.get(i).getData5())))
                                                .title(mDataList.get(i).getData8())
                                                .snippet(mDataList.get(i).getData9());

                                        mmap.addMarker(markerOptions).showInfoWindow();
                                    }
                                }
                                //아동만 표시
                            } else if (kedault == false && adault == true) {
                                if ((Integer.parseInt(mDataList.get(i).getData2()) + Integer.parseInt(mDataList.get(i).getData4()) + Integer.parseInt(mDataList.get(i).getData11())) > 0) {
                                    double sLat;
                                    double sLng;
                                    if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
                                        sLat = Double.valueOf(myLat);
                                        sLng = Double.valueOf(myLng);
                                    } else {
                                        sLat = Double.valueOf(MyApplication.lat);
                                        sLng = Double.valueOf(MyApplication.lng);
                                    }

                                    double eLat = Double.valueOf(mDataList.get(i).getData6());
                                    double eLng = Double.valueOf(mDataList.get(i).getData5());
                                    String distance = calcDistance(sLat, sLng, eLat, eLng);

                                    if (Integer.parseInt(distance) < mDistance) {
                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(new LatLng(Double.valueOf(mDataList.get(i).getData6()), Double.valueOf(mDataList.get(i).getData5())))
                                                .title(mDataList.get(i).getData8())
                                                .snippet(mDataList.get(i).getData9());

                                        mmap.addMarker(markerOptions).showInfoWindow();
                                    }
                                }
                                //공용 , 분리 , 거리에 따라표시
                            } else {
                                double sLat;
                                double sLng;
                                if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
                                    sLat = Double.valueOf(myLat);
                                    sLng = Double.valueOf(myLng);
                                } else {
                                    sLat = Double.valueOf(MyApplication.lat);
                                    sLng = Double.valueOf(MyApplication.lng);
                                }

                                double eLat = Double.valueOf(mDataList.get(i).getData6());
                                double eLng = Double.valueOf(mDataList.get(i).getData5());
                                String distance = calcDistance(sLat, sLng, eLat, eLng);

                                if (Integer.parseInt(distance) < mDistance) {
                                    MarkerOptions markerOptions = new MarkerOptions()
                                            .position(new LatLng(Double.valueOf(mDataList.get(i).getData6()), Double.valueOf(mDataList.get(i).getData5())))
                                            .title(mDataList.get(i).getData8())
                                            .snippet(mDataList.get(i).getData9());

                                    mmap.addMarker(markerOptions).showInfoWindow();
                                }
                            }
                            //분리화장실일떄
                        } else {
                            if (kedault == true && adault == true) {
                                if ((Integer.parseInt(mDataList.get(i).getData1()) + Integer.parseInt(mDataList.get(i).getData3()) + Integer.parseInt(mDataList.get(i).getData10())) > 0 && (Integer.parseInt(mDataList.get(i).getData2()) + Integer.parseInt(mDataList.get(i).getData4()) + Integer.parseInt(mDataList.get(i).getData11())) > 0) {
                                    double sLat;
                                    double sLng;
                                    if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
                                        sLat = Double.valueOf(myLat);
                                        sLng = Double.valueOf(myLng);
                                    } else {
                                        sLat = Double.valueOf(MyApplication.lat);
                                        sLng = Double.valueOf(MyApplication.lng);
                                    }

                                    double eLat = Double.valueOf(mDataList.get(i).getData6());
                                    double eLng = Double.valueOf(mDataList.get(i).getData5());
                                    String distance = calcDistance(sLat, sLng, eLat, eLng);

                                    if (Integer.parseInt(distance) < mDistance) {
                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(new LatLng(Double.valueOf(mDataList.get(i).getData6()), Double.valueOf(mDataList.get(i).getData5())))
                                                .title(mDataList.get(i).getData8())
                                                .snippet(mDataList.get(i).getData9());

                                        mmap.addMarker(markerOptions).showInfoWindow();
                                    }
                                }
                            } else if (kedault == true && adault == false) {
                                if ((Integer.parseInt(mDataList.get(i).getData1()) + Integer.parseInt(mDataList.get(i).getData3()) + Integer.parseInt(mDataList.get(i).getData10())) > 0) {
                                    double sLat;
                                    double sLng;
                                    if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
                                        sLat = Double.valueOf(myLat);
                                        sLng = Double.valueOf(myLng);
                                    } else {
                                        sLat = Double.valueOf(MyApplication.lat);
                                        sLng = Double.valueOf(MyApplication.lng);
                                    }

                                    double eLat = Double.valueOf(mDataList.get(i).getData6());
                                    double eLng = Double.valueOf(mDataList.get(i).getData5());
                                    String distance = calcDistance(sLat, sLng, eLat, eLng);

                                    if (Integer.parseInt(distance) < mDistance) {
                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(new LatLng(Double.valueOf(mDataList.get(i).getData6()), Double.valueOf(mDataList.get(i).getData5())))
                                                .title(mDataList.get(i).getData8())
                                                .snippet(mDataList.get(i).getData9());

                                        mmap.addMarker(markerOptions).showInfoWindow();
                                    }
                                }

                            } else if (kedault == false && adault == true) {
                                if ((Integer.parseInt(mDataList.get(i).getData2()) + Integer.parseInt(mDataList.get(i).getData4()) + Integer.parseInt(mDataList.get(i).getData11())) > 0) {
                                    double sLat;
                                    double sLng;
                                    if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
                                        sLat = Double.valueOf(myLat);
                                        sLng = Double.valueOf(myLng);
                                    } else {
                                        sLat = Double.valueOf(MyApplication.lat);
                                        sLng = Double.valueOf(MyApplication.lng);
                                    }

                                    double eLat = Double.valueOf(mDataList.get(i).getData6());
                                    double eLng = Double.valueOf(mDataList.get(i).getData5());
                                    String distance = calcDistance(sLat, sLng, eLat, eLng);

                                    if (Integer.parseInt(distance) < mDistance) {
                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(new LatLng(Double.valueOf(mDataList.get(i).getData6()), Double.valueOf(mDataList.get(i).getData5())))
                                                .title(mDataList.get(i).getData8())
                                                .snippet(mDataList.get(i).getData9());

                                        mmap.addMarker(markerOptions).showInfoWindow();
                                    }
                                }
                            } else {
                                double sLat;
                                double sLng;
                                if (myLat > 0 && myLng > 0 && getIntent().getStringExtra("ALL") != null && getIntent().getStringExtra("ALL").equals("0")) {
                                    sLat = Double.valueOf(myLat);
                                    sLng = Double.valueOf(myLng);
                                } else {
                                    sLat = Double.valueOf(MyApplication.lat);
                                    sLng = Double.valueOf(MyApplication.lng);
                                }

                                double eLat = Double.valueOf(mDataList.get(i).getData6());
                                double eLng = Double.valueOf(mDataList.get(i).getData5());
                                String distance = calcDistance(sLat, sLng, eLat, eLng);

                                if (Integer.parseInt(distance) < mDistance) {
                                    MarkerOptions markerOptions = new MarkerOptions()
                                            .position(new LatLng(Double.valueOf(mDataList.get(i).getData6()), Double.valueOf(mDataList.get(i).getData5())))
                                            .title(mDataList.get(i).getData8())
                                            .snippet(mDataList.get(i).getData9());

                                    mmap.addMarker(markerOptions).showInfoWindow();
                                }

                            }
                        }
                    }
                }
            }
        }
    }

    // xml 데이터 파서
    private void getXmlData() {

        StringBuffer buffer = new StringBuffer();
        String name;

        try {
            xrp.next();
            int eventType = xrp.getEventType(); //XML의 이벤트 타입 얻어오기

            while (eventType != XmlResourceParser.END_DOCUMENT) {
                // XmlResourceParser의 5가지 이벤트 타입
                switch (eventType) {
                    //문서시작
                    case XmlResourceParser.START_DOCUMENT:
                        mDataList = new ArrayList<DataDto>();
                        break;
                    //태그 시작
                    case XmlResourceParser.START_TAG:
                        name = xrp.getName();
                        if (name.equals("Row")) {
                            mDataDto = new DataDto();
                        }
                        if (name.equals("여성용-어린이용대변기수"))
                            dataResult1 = true;
                        if (name.equals("남성용-장애인용소변기수"))
                            dataResult2 = true;
                        if (name.equals("남성용-어린이용소변기수"))
                            dataResult3 = true;
                        if (name.equals("여성용-장애인용대변기수"))
                            dataResult4 = true;
                        if (name.equals("경도_WGS84좌표_"))
                            dataResult5 = true;
                        if (name.equals("위도_WGS84좌표_"))
                            dataResult6 = true;
                        if (name.equals("남녀공용화장실여부"))
                            dataResult7 = true;
                        if (name.equals("화장실명"))
                            dataResult8 = true;
                        if (name.equals("소재지도로명주소"))
                            dataResult9 = true;
                        if (name.equals("남성용-어린이용대변기수"))
                            dataResult10 = true;
                        if (name.equals("남성용-장애인용대변기수"))
                            dataResult11 = true;
                        break;
                    //태그 내용맞으면 데이터저장
                    case XmlResourceParser.TEXT:
                        name = xrp.getName();
                        if (dataResult1) {
                            mDataDto.setData1(xrp.getText());
                            dataResult1 = false;
                        }
                        if (dataResult2) {
                            mDataDto.setData2(xrp.getText());
                            dataResult2 = false;
                        }
                        if (dataResult3) {
                            mDataDto.setData3(xrp.getText());
                            dataResult3 = false;
                        }
                        if (dataResult4) {
                            mDataDto.setData4(xrp.getText());
                            dataResult4 = false;
                        }
                        if (dataResult5) {
                            mDataDto.setData5(xrp.getText());
                            dataResult5 = false;
                        }
                        if (dataResult6) {
                            mDataDto.setData6(xrp.getText());
                            dataResult6 = false;
                        }
                        if (dataResult7) {
                            mDataDto.setData7(xrp.getText());
                            dataResult7 = false;
                        }
                        if (dataResult8) {
                            mDataDto.setData8(xrp.getText());
                            dataResult8 = false;
                        }
                        if (dataResult9) {
                            mDataDto.setData9(xrp.getText());
                            dataResult9 = false;
                        }
                        if (dataResult10) {
                            mDataDto.setData10(xrp.getText());
                            dataResult10 = false;
                        }
                        if (dataResult11) {
                            mDataDto.setData11(xrp.getText());
                            dataResult11 = false;
                        }
                        break;
                    //태그끝나면 리스트에 데이터 저장
                    case XmlResourceParser.END_TAG:
                        name = xrp.getName();
                        if (name.equalsIgnoreCase("Row") && mDataDto != null) {
                            mDataList.add(mDataDto);
                        }
                        break;
                    case XmlResourceParser.END_DOCUMENT:

                }
                eventType = xrp.next(); //다음 이벤트타입 얻어오기
            }

            //맵셋팅
            setUpMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //좌표값으로 거리계산
    public static String calcDistance(double lat1, double lon1, double lat2, double lon2) {
        double EARTH_R, Rad, radLat1, radLat2, radDist;
        double distance, ret;
        EARTH_R = 6371000.0;
        Rad = Math.PI / 180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lon1 - lon2);

        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);

        return String.valueOf(Math.round(ret));
    }

    // 내위치가져오기
    public void GetLocations() {
        StringBuffer juso = new StringBuffer();
        latPoint = myLat;
        lngPoint = myLng;
        try {
            // 위도,경도를 이용하여 현재 위치의 주소를 가져온다.
            List<Address> addresses;
            final Geocoder geocoder = new Geocoder(this);
            addresses = geocoder.getFromLocation(latPoint, lngPoint, 1);
            for (Address addr : addresses) {
                int index = addr.getMaxAddressLineIndex();
                for (int i = 0; i <= index; i++) {
                    juso.append(addr.getAddressLine(i));
                    juso.append(" ");
                }
                juso.append("\n");
                myJuso = juso.toString();
                progressDialog.dismiss();

                if (myLat > 0 && myLng > 0) {
                    Resources res = getResources();
                    // 이부분에서 파싱이 재대로 안이루어 지면 , 데이터가 안뜸
                    //이부분에서 공공데이터와 주소를 정확히 판단해서 xrp 분기처리하기
                    if (myJuso.contains("천안시")) {
                        xrp = res.getXml(R.xml.chungcheongnamdo_cheonan);
                    }

                    getXmlData();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //marker 추가
    public void drawMarker(final LatLng location) {
//        mmap.clear();
        if(marker!= null)
        {
            marker.remove();
        }
        mmap.moveCamera(CameraUpdateFactory.newLatLng(location));
        marker = mmap.addMarker(new MarkerOptions()
                .position(location)
                .title("화장실 등록하기") // 말풍선 내용
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.marker,100,100)))); // 이미지 가로, 세로 크기 조절
        markerClick();
    }

    public void markerClick() {
        if (mmap != null) {
            mmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                public boolean onMarkerClick(Marker marker) {
                    marker.showInfoWindow(); // 말풍선 보여주기
                    infoClick(marker);
                    return false;
                }
            });
        }
    }

    // 말풍선 클릭
    public void infoClick(final Marker marker) {
        if (marker.isInfoWindowShown()) {
            mmap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(final Marker marker) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                    // 제목셋팅
                    alertDialogBuilder.setTitle("화장실 등록하기");

                    // AlertDialog 셋팅
                    alertDialogBuilder
                            .setMessage("자기 위치의 주소로 해당 위치를 등록하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("네",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                                            intent.putExtra("location", marker.getPosition().toString());
                                            startActivity(intent);
                                        }
                                    })
                            .setNegativeButton("아니오",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });

                    // 다이얼로그 생성
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // 다이얼로그 보여주기
                    alertDialog.show();
                }
            });
        }
    }
    //marker 크기 resize
    public Bitmap resizeMapIcons(int resId,int width, int height){
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(resId);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, width, height, false);
        return resizedBitmap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
