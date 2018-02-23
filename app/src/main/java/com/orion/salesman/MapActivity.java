package com.orion.salesman;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.orion.salesman._adaper.CustomAdapterNewShop;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomDialog;
import com.orion.salesman._class.CustomDialogTryAgain;
import com.orion.salesman._class.GpsManager;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._class.LocationAddress;
import com.orion.salesman._class.LocationTracker;
import com.orion.salesman._interface.GetAddressCallback;
import com.orion.salesman._interface.IF_100;
import com.orion.salesman._interface.IF_7;
import com.orion.salesman._interface.LocationChangeCallback;
import com.orion.salesman._interface.OnClickDialog;
import com.orion.salesman._object.AddressSQLite;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.LISTPOSITION;
import com.orion.salesman._object.NearShop;
import com.orion.salesman._object.PositionAPI;
import com.orion.salesman._object.RESEND_116;
import com.orion.salesman._object.ResultAddMap;
import com.orion.salesman._object.SavePositionLocaiton;
import com.orion.salesman._object.SendParamNearShop;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._fragment.EditFragment;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._sqlite.DataBaseHelper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.orion.salesman._sqlite.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by maidinh on 18/8/2016.
 */
public class MapActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MapActivity";
    private ImageView btnBack;
    private boolean flag = false;
    //    private GoogleMap mMap;
    private LatLng myPosition;
    //    private static Geocoder geocoder = null;

    private boolean isRunning = false;
    private Button imgGPS, imgMyShop, imgNear;
    RelativeLayout btnSave;
    private int typeMap = 0;
    private Spinner spProvince, spDist, spStreet;
    private String CUSTCD = "";
    private int KIND = 0;
    String StrAD = "";
    String StrLon = "";
    String StrLat = "";
    //    private int FROM_POSITION;
    private InforShopDetails inforShopDetails;
    private ProgressBar progressbar;
    private LinearLayout lnParent;
    private TextView tvAddress;
    Location mLocation;
    WebView wv;
    String ADD_1 = "";
    String ADD_2 = "";
    String ADD_3 = "";

    void getAdmincodeFromMyPosition(final double LON, final double LAT) {

        String getGPSFromWeb = "http://snsv.orionvina.vn:8080/GeoServer/FindAddress?layer=admin_4301&crs=4301&lon=" +
                LON +
                "&lat=" +
                LAT +
                "&names=admin_id1,admin_id2,admin_id3,name1,name2,name3";
        Log.d(TAG, "getGPSFromWeb:" + getGPSFromWeb);
        StringRequest request = new StringRequest(Request.Method.GET, getGPSFromWeb,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "response:" + response);

                        if (response != null && response.length() > 0) {

                            try {
                                InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
                                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                                Document doc = dBuilder.parse(is);
                                Element element = doc.getDocumentElement();
                                element.normalize();
                                NodeList nList = doc.getElementsByTagName("data");
                                for (int i = 0; i < nList.getLength(); i++) {
                                    Node node = nList.item(i);
                                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                                        Element element2 = (Element) node;
                                        ADD_1 = Const.getValue("admin_id1", element2).trim();
                                        ADD_2 = Const.getValue("admin_id2", element2).trim();
                                        ADD_3 = Const.getValue("admin_id3", element2).trim();

                                        String ad = ADD_1 + ADD_2 + ADD_3;
                                        if (ad.length() == 10) {
                                            ADDR1 = ADD_1;
                                            ADDR2 = ADD_2;
                                            ADDR3 = ADD_3;
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                ADD_1 = "";
                                ADD_2 = "";
                                ADD_3 = "";
                                e.printStackTrace();
                            }


                            if (ADD_1.length() > 0 && ADD_2.length() > 0 && ADD_3.length() > 0) {
                                btnSave.setEnabled(false);
                                for (int i = 0; i < listProvince.size(); i++) {
                                    AddressSQLite addressSQLite = listProvince.get(i);
                                    if (addressSQLite.getADDR1().equals(ADD_1)) {
                                        spProvince.setSelection(i);
                                        break;
                                    }
                                }
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (int i = 0; i < listDist.size(); i++) {
                                            AddressSQLite ob = listDist.get(i);
                                            if (ob.getADDR2().equals(ADD_2)) {
                                                spDist.setSelection(i);
                                                break;
                                            }
                                        }
                                    }
                                }, 1000);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        for (int i = 0; i < listStreet.size(); i++) {
                                            AddressSQLite ob = listStreet.get(i);
                                            if (ob.getADDR3().equals(ADD_3)) {
                                                spStreet.setSelection(i);
                                                btnSave.setEnabled(true);
                                                Log.d(TAG, "setSelection");
                                                break;
                                            }
                                        }
                                    }
                                }, 2000);
                            }
                        } else {
                            ADD_1 = "";
                            ADD_2 = "";
                            ADD_3 = "";
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // handle error response
                        ADD_1 = "";
                        ADD_2 = "";
                        ADD_3 = "";
                    }
                }
        );
        MyApplication.getInstance().addToRequestQueue(request);
    }

    LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        locationTracker = new LocationTracker();
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        lnParent = (LinearLayout) findViewById(R.id.lnParent);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        spProvince = (Spinner) findViewById(R.id.sp1);
        spDist = (Spinner) findViewById(R.id.sp2);
        spStreet = (Spinner) findViewById(R.id.sp3);
        btnBack = (ImageView) findViewById(R.id.btnBack);
        btnSave = (RelativeLayout) findViewById(R.id.btnSave);
        btnSave.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnSave.setEnabled(true);
            }
        }, 5000);
        imgGPS = (Button) findViewById(R.id.imgGPS);
        imgNear = (Button) findViewById(R.id.imgNear);
        imgMyShop = (Button) findViewById(R.id.imgMyShop);
        imgMyShop.setOnClickListener(this);
        imgNear.setOnClickListener(this);
        imgGPS.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        if (MainActivity.checkNewOrEditShop == 0) {
            imgNear.setVisibility(View.VISIBLE);
        } else {
            imgNear.setVisibility(View.GONE);
        }

        locationTracker.registerLocationService(MapActivity.this, new LocationChangeCallback() {
            @Override
            public void onLocationChangeCallBack(Location location) {
//                Log.d(TAG, "onLocationChangeCallBack");
                mLocation = location;
            }
        });
        KIND = getIntent().getIntExtra(Const.CHECK_KIND_GOTO_MAP, 0);

        StrAD = getIntent().getStringExtra(Const.STRING_AD);
        StrLon = getIntent().getStringExtra(Const.STRING_LON);
        StrLat = getIntent().getStringExtra(Const.STRING_LAT);

//        intent.putExtra(Const.STRING_AD, StrAD);
//        intent.putExtra(Const.STRING_LON, StrLon);
//        intent.putExtra(Const.STRING_LAT, StrLat);


        if (KIND == 0) {
            CUSTCD = getIntent().getStringExtra(Const.CUSTCD);
//            FROM_POSITION = getIntent().getIntExtra(Const.FROM_POSITION, 0);
        } else if (KIND == 1) {

        }
        inforShopDetails = new Gson().fromJson(getIntent().getStringExtra(Const.SHOP_INFO_DETAIL), InforShopDetails.class);

        if (inforShopDetails == null) {

            Log.d(TAG, "inforShopDetails = null");
        } else {
            Log.d(TAG, "ADMINCODE:" + inforShopDetails.getV29());
            Log.d(TAG, "inforShopDetails not null");
        }

        resumeMap();
        initWebView();
        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
        }
        init();
        if (!Const.checkInternetConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                init();
//            }
//        }, 500);


    }

    void initWebView() {
        wv = (WebView) findViewById(R.id.wv);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setAllowContentAccess(true);
        wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv.getSettings().setAllowFileAccessFromFileURLs(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setAllowContentAccess(true);

        wv.setWebContentsDebuggingEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.loadUrl(Const.MAPS_HTTP);
        wv.addJavascriptInterface(new JsObject(this), "Android");
        wv.setWebViewClient(new WebViewExtend());
    }

    boolean flagFirst = true;

    private class WebViewExtend extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            // ??? ??? ????.
            super.onPageFinished(view, url);

            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }

            view.loadUrl("javascript:SetOnMapClick(true)");

            if (MainActivity.checkNewOrEditShop == 0 && flagFirst) {
                flagFirst = false;
                String routeLon = "";
                String routeLat = "";
                if (MainActivity.LON != 0 && MainActivity.LAT != 0) {
                    long S1 = (long) (MainActivity.LON * Const.GPS_WGS84);
                    long S2 = (long) (MainActivity.LAT * Const.GPS_WGS84);
                    routeLon = "" + S1;
                    routeLat = "" + S2;
                }

                if (StrLon.length() > 0 && StrLat.length() > 0) {

                    LON = (double) Integer.parseInt(StrLon) / Const.GPS_WGS84;
                    LAT = (double) Integer.parseInt(StrLat) / Const.GPS_WGS84;
                    if (LON != 0 && LAT != 0) {
                        getAddressLocation(LAT, LON, "");
                    }
                } else if (routeLon.length() > 0 && routeLat.length() > 0) {
                    LON = (double) Integer.parseInt(routeLon) / Const.GPS_WGS84;
                    LAT = (double) Integer.parseInt(routeLat) / Const.GPS_WGS84;
                    if (LON != 0 && LAT != 0) {
                        getAddressLocation(LAT, LON, "");
                    }
                } else {
                    if (mLocation != null) {
                        LAT = mLocation.getLatitude();
                        LON = mLocation.getLongitude();
                        getAddressLocation(LAT, LON, "");
                    } else {
                        if (MainActivity.LON != 0 && MainActivity.LAT != 0) {
                            LON = MainActivity.LON;
                            LAT = MainActivity.LAT;
                            getAddressLocation(LAT, LON, "");
                        }
                    }
                }
            } else if (MainActivity.checkNewOrEditShop != 0 && flagFirst) {
                flagFirst = false;
                if (DATA_SHOP != null && DATA_SHOP.length() > 0) {
                    getAddressLocation(TEMP_LAT, TEMP_LON, "");

                } else {

                    if (mLocation != null) {
                        LAT = mLocation.getLatitude();
                        LON = mLocation.getLongitude();
                        getAddressLocation(LAT, LON, "");
                    } else {
                        if (MainActivity.LON != 0 && MainActivity.LAT != 0) {
                            LON = MainActivity.LON;
                            LAT = MainActivity.LAT;
                            getAddressLocation(LAT, LON, "");
                        }
                    }
                }
            }
        }
    }

    String getJSON(double lon, double lat, String shopId, String shopName) {
        String s = "";
        LISTPOSITION ob = new LISTPOSITION(lon, lat, shopId, shopName, "0");
        s = new Gson().toJson(ob);
        return s;
    }


    void getAddressLocation(double lat, double lon, String title) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Runnable");
                progressbar.setVisibility(View.VISIBLE);
            }
        });
        new LocationAddress(lat, lon, new GetAddressCallback() {
            @Override
            public void onSuccess(String s) {

                final String address = s;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        String data = getJSON(LON, LAT, "", getResources().getString(R.string.myHere) + address);
                        String data = getJSON(LON, LAT, "", address);
                        wv.loadUrl("javascript:CaptionMarkerAdd([" + data + "]);");
                        progressbar.setVisibility(View.GONE);
                    }
                });
            }
        }).execute();
    }

    Handler handler = new Handler();

    public class JsObject {
        private Context context = null;

        public JsObject(Context context) {
            this.context = context;
            Log.d(TAG, "context");
        }

        @JavascriptInterface
        public void GetTouchPosition(String position) {
            Log.d(TAG, "position:" + position);
            if (position.length() > 0 && position.contains(":")) {
                btnSave.post(new Runnable() {
                    @Override
                    public void run() {
                        btnSave.setEnabled(false);
                    }
                });


                LON = Double.parseDouble(position.split(":")[0]);
                LAT = Double.parseDouble(position.split(":")[1]);

                if (position.split(":")[2].trim().length() == 2 && position.split(":")[3].trim().length() == 3 && position.split(":")[4].trim().length() == 5) {
                    final String add_1 = Integer.parseInt(position.split(":")[2].trim()) + "";
                    final String add_2 = Integer.parseInt(position.split(":")[3].trim()) + "";
                    final String add_3 = Integer.parseInt(position.split(":")[4].trim()) + "";
                    ADDR1 = add_1;
                    ADDR2 = add_2;
                    ADDR3 = add_3;


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < listProvince.size(); i++) {
                                AddressSQLite addressSQLite = listProvince.get(i);
                                if (addressSQLite.getADDR1().equals(add_1)) {
                                    spProvince.setSelection(i);
                                    break;
                                }
                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < listDist.size(); i++) {
                                        AddressSQLite ob = listDist.get(i);
                                        if (ob.getADDR2().equals(add_2)) {
                                            spDist.setSelection(i);
                                            break;
                                        }
                                    }
                                }
                            }, 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    for (int i = 0; i < listStreet.size(); i++) {
                                        AddressSQLite ob = listStreet.get(i);
                                        if (ob.getADDR3().equals(add_3)) {
                                            spStreet.setSelection(i);
                                            Log.d(TAG, "setSelection");
                                            btnSave.setEnabled(true);
                                            break;
                                        }
                                    }
                                }
                            }, 2000);
                        }
                    });
                }
                if (LON < 0 || LAT < 0) {
                    Toast.makeText(getApplicationContext(), "Data problems, try again", Toast.LENGTH_SHORT).show();
                } else {
                    getAddressLocation(LAT, LON, title);
                }
            } else {
                Toast.makeText(getApplicationContext(), "Can not get GPS", Toast.LENGTH_SHORT).show();
            }
            // position : lon:lat
            // code
        }
    }

    void init() {
        new loadData().execute();

    }

    protected Location getLocation() {
        return locationTracker.getLocation();
    }

    class loadData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            initDB();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            updateUI();
            lnParent.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }
    }

    private List<AddressSQLite> addresses = new ArrayList<>();
    private ArrayList<AddressSQLite> listProvince = new ArrayList<>();
    private ArrayList<AddressSQLite> listDist = new ArrayList<>();
    private ArrayList<AddressSQLite> listStreet = new ArrayList<>();
    int checkWard = 0;

    public boolean checkContainsStreet(ArrayList<AddressSQLite> arrayList, String s) {
        for (AddressSQLite str : arrayList) {
            if (str.getADDR3().equals(s))
                return true;
        }
        return false;
    }

    public boolean checkContainsDist(ArrayList<AddressSQLite> arrayList, String s) {
        for (AddressSQLite str : arrayList) {
            if (str.getADDR2().equals(s))
                return true;
        }
        return false;
    }

    boolean checkContains(ArrayList<AddressSQLite> arrayList, String s) {
        for (AddressSQLite str : arrayList) {
            if (str.getADDR1().equals(s))
                return true;
        }
        return false;
    }

    private String ADDR1 = "", ADDR2 = "", ADDR3 = "";
    private CustomAdapterNewShop adapterDist, adapterStreet;
    private int checDist = 0;
    private String tinh = "", quan = "", phuong = "";

    boolean isSave(String s1, String s2, String s3) {
        for (AddressSQLite s : addresses) {
            if (s.getADDR1().trim().equals(s1) && s.getADDR2().trim().equals(s2) && s.getADDR3().trim().equals(s3))
                return true;
        }
        return false;
    }


    void initDB() {
        addresses.clear();
        DataBaseHelper db = new DataBaseHelper(this);
        db.openDB();
        addresses = db.getAddress();
        for (AddressSQLite s : addresses) {
            if (!checkContains(listProvince, s.getADDR1())) {
                listProvince.add(s);
            }
        }
        Collections.sort(listProvince, new Comparator<AddressSQLite>() {
            @Override
            public int compare(AddressSQLite addressSQLite, AddressSQLite t1) {
                return addressSQLite.getADDR4().compareTo(t1.getADDR4());
            }
        });
    }

    void updateUI() {
        adapterStreet = new CustomAdapterNewShop(this, listStreet, 3);
        spStreet.setAdapter(adapterStreet);
        spStreet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d(TAG, "listStreet:" + listStreet.get(i).getADDR3() + ":" + listStreet.get(i).getADDR6());
                ADDR3 = listStreet.get(i).getADDR3();
                Log.d(TAG, "1 ADDR3:" + ADDR3);
                phuong = listStreet.get(i).getADDR6();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterDist = new CustomAdapterNewShop(this, listDist, 1);
        spDist.setAdapter(adapterDist);
        spDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checDist = i;
                ADDR2 = listDist.get(i).getADDR2();
                Log.d(TAG, "spDist ADDR2:" + ADDR2);
                quan = listDist.get(i).getADDR5();
//                Log.d(TAG, "ADDR2 1:" + ADDR2);
                listStreet.clear();
                for (int k = 0; k < addresses.size(); k++) {
                    AddressSQLite s = addresses.get(k);
                    if (s.getADDR1().equals(ADDR1) && s.getADDR2().equals(ADDR2)) {
//                        Log.d(TAG, "S2:" + s.getADDR2() + " S6:" + s.getADDR6());
                        if (!checkContainsStreet(listStreet, s.getADDR3()))
                            listStreet.add(s);
                    }
                }
                Collections.sort(listStreet, new Comparator<AddressSQLite>() {
                    @Override
                    public int compare(AddressSQLite addressSQLite, AddressSQLite t1) {
                        return addressSQLite.getADDR6().compareTo(t1.getADDR6());
                    }
                });
                adapterStreet.notifyDataSetChanged();
                if (checkWard == 0)
                    checkWard = 1;
                else checkWard = 0;
                Log.d(TAG, "spStreet.setSelection(checkWard);" + checkWard);
                spStreet.setSelection(checkWard);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomAdapterNewShop dataAdapter = new CustomAdapterNewShop(this, listProvince, 0);
        spProvince.setAdapter(dataAdapter);
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d(TAG, "listProvince:" + listProvince.get(i).getADDR4() + ":" + listProvince.get(i).getADDR1());
                ADDR1 = listProvince.get(i).getADDR1();
                tinh = listProvince.get(i).getADDR4();
                listDist.clear();
                for (int k = 0; k < addresses.size(); k++) {
                    AddressSQLite s = addresses.get(k);
                    if (s.getADDR1().equals(ADDR1)) {
                        if (!checkContainsDist(listDist, s.getADDR2()))
                            listDist.add(s);
                    }
                }
                Collections.sort(listDist, new Comparator<AddressSQLite>() {
                    @Override
                    public int compare(AddressSQLite addressSQLite, AddressSQLite t1) {
                        return addressSQLite.getADDR5().compareTo(t1.getADDR5());
                    }
                });
//                Log.d(TAG, "1 listDist:" + listDist.size());
                adapterDist.notifyDataSetChanged();
//                Log.e(TAG, "1 set");
                if (checDist == 0)
                    spDist.setSelection(1);
                else spDist.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (inforShopDetails != null) {
            tvAddress.setText(MainActivity.MY_ADDRESS);
            String s1 = inforShopDetails.getV4();
            final String s2 = inforShopDetails.getV5();
            for (int i = 0; i < listProvince.size(); i++) {
                AddressSQLite addressSQLite = listProvince.get(i);
                if (addressSQLite.getADDR1().equals(s1)) {
                    spProvince.setSelection(i);
                    break;
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    Log.e(TAG, "listDist:" + listDist.size());
                    for (int i = 0; i < listDist.size(); i++) {
                        AddressSQLite ob = listDist.get(i);
                        if (ob.getADDR2().equals(s2)) {
                            spDist.setSelection(i);
                            break;
                        }
                    }
                }
            }, 1000);
            if (inforShopDetails.getV29() != null && inforShopDetails.getV29().length() == 10) {

                final String id_3 = inforShopDetails.getV29().substring(5, 10);
                Log.d(TAG, "id_3:" + id_3);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "listStreet:" + new Gson().toJson(listStreet));
                        for (int i = 0; i < listStreet.size(); i++) {
                            AddressSQLite ob = listStreet.get(i);
                            if (ob.getADDR3().equals(id_3)) {
                                spStreet.setSelection(i);
                                Log.d(TAG, "setSelection");
                                break;
                            }
                        }
                    }
                }, 2000);
            } else {
                Log.d(TAG, "getV29 fail");
            }
        } else if (StrAD.length() == 10) {
            String s1 = StrAD.substring(0, 2);
            for (int i = 0; i < listProvince.size(); i++) {
                AddressSQLite addressSQLite = listProvince.get(i);
                if (addressSQLite.getADDR1().equals(s1)) {
                    spProvince.setSelection(i);
                    break;
                }
            }
            final String s2 = StrAD.substring(2, 5);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < listDist.size(); i++) {
                        AddressSQLite ob = listDist.get(i);
                        if (ob.getADDR2().equals(s2)) {
                            spDist.setSelection(i);
                            break;
                        }
                    }
                }
            }, 1000);
            final String id_3 = StrAD.substring(5, 10);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "listStreet:" + new Gson().toJson(listStreet));
                    for (int i = 0; i < listStreet.size(); i++) {
                        AddressSQLite ob = listStreet.get(i);
                        if (ob.getADDR3().equals(id_3)) {
                            spStreet.setSelection(i);
                            Log.d(TAG, "setSelection");
                            break;
                        }
                    }
                }
            }, 2000);

        }
        if (MainActivity.checkNewOrEditShop == 0) {
            tvAddress.setVisibility(View.GONE);
            if (StrAD.length() == 10) {

                try {
                    updateSpinner(StrAD);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (RouteFragment.ADMINCODE.length() == 10) {

                try {
                    updateSpinner(RouteFragment.ADMINCODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void updateSpinner(final String s) {
//        final String s = RouteFragment.ADMINCODE;
        ADDR1 = s.substring(0, 2);
        for (int i = 0; i < listProvince.size(); i++) {
            AddressSQLite addressSQLite = listProvince.get(i);
            if (addressSQLite.getADDR1().equals(ADDR1)) {
                spProvince.setSelection(i);
                break;
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ADDR2 = s.substring(2, 5);

                for (int i = 0; i < listDist.size(); i++) {
                    AddressSQLite ob = listDist.get(i);
                    if (ob.getADDR2().equals(ADDR2)) {
//                        Log.d(TAG, "2 ADDR2:" + ADDR2 + " [pos]" + i);
                        spDist.setSelection(i);
                        break;
                    }
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ADDR3 = s.substring(5, 10);
                        Log.d(TAG, "2 ADDR3:" + ADDR3);
                        for (int i = 0; i < listStreet.size(); i++) {
                            AddressSQLite ob = listStreet.get(i);
                            if (ob.getADDR3().equals(ADDR3)) {
                                spStreet.setSelection(i);
                                break;
                            }
                        }
                    }
                }, 1000);

            }
        }, 1000);
    }

    boolean firsttime = false;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTracker.stopLocationUpdates();
    }

    double TEMP_LON = 0;
    double TEMP_LAT = 0;
    String DATA_SHOP = "";

    void resumeMap() {
        if (inforShopDetails != null) {
            Log.d(TAG, "inforShopDetails != null");
            if (inforShopDetails.getV14() != null && inforShopDetails.getV14().length() > 0
                    && inforShopDetails.getV15() != null && inforShopDetails.getV15().length() > 0) {
                TEMP_LON = (double) Integer.parseInt(inforShopDetails.getV14()) / Const.GPS_WGS84;
                TEMP_LAT = (double) Integer.parseInt(inforShopDetails.getV15()) / Const.GPS_WGS84;
                Log.d(TAG, "SHOP LON:" + inforShopDetails.getV14());
                Log.d(TAG, "SHOP LAT:" + inforShopDetails.getV15());
                LON = TEMP_LON;
                LAT = TEMP_LAT;

                title = MainActivity.SHOP_NAME;
                LISTPOSITION ob = new LISTPOSITION(TEMP_LON, TEMP_LAT, "", title, MainActivity.SEQ);
                DATA_SHOP = new Gson().toJson(ob);
            } else {
            }
        } else {

        }
        if (TEMP_LON == 0 && TEMP_LAT == 0) {
            imgMyShop.setVisibility(View.GONE);
        } else {
            imgMyShop.setVisibility(View.VISIBLE);
        }
    }

    String city = "";
    String state = "";
    String zip = "";
    String country = "";
    private double LON = 0.0, LAT = 0.0;


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.notiGPS));
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    float currentZoom = 17;


    private String ADMINCODE = "";
    long L1 = 0;
    long L2 = 0;
    int check = 0;
    String data116 = "";

    void initDB116() {
        if (LON == 0 && LAT == 0) {
            Log.d(TAG, "LON == 0");
            if (mLocation != null) {
                LON = mLocation.getLongitude();
                LAT = mLocation.getLatitude();
                L1 = (long) (LON * Const.GPS_WGS84);
                L2 = (long) (LAT * Const.GPS_WGS84);
                ADMINCODE = ADDR1 + ADDR2 + ADDR3;
                Map<String, String> map = new HashMap<>();
                map.put("CUSTCD", CUSTCD);
                map.put("LON", "" + L1);
                map.put("LAT", "" + L2);
                map.put("ADMINCODE", ADMINCODE);
                data116 = new Gson().toJson(map);
                Log.d(TAG, data116);
            }
        } else {
            Log.d(TAG, "LON != 0  LON:" + LON + "   LAT:" + LAT);
            L1 = (long) (LON * Const.GPS_WGS84);
            L2 = (long) (LAT * Const.GPS_WGS84);
            ADMINCODE = ADDR1 + ADDR2 + ADDR3;
            Map<String, String> map = new HashMap<>();
            map.put("CUSTCD", CUSTCD);
            map.put("LON", "" + L1);
            map.put("LAT", "" + L2);
            map.put("ADMINCODE", ADMINCODE);
            data116 = new Gson().toJson(map);
            Log.d(TAG, data116);
        }
    }

    void backtonewshop() {
        Intent intent = getIntent();
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(LON);
        arrayList.add(LAT);
        intent.putExtra(Const.UPDATE_POSITION, new Gson().toJson(arrayList));
        String ad = ADDR1 + ADDR2 + ADDR3;
        intent.putExtra(Const.UPDATE_ADMINCODE, ad);
        setResult(Const.REQUEST_CODE_INFO, intent);
        finish();
    }

    private CustomDialog customDialog;

    void API_116() {
        if ((L1 == 0 && L2 == 0) || (ADMINCODE.trim().length() != 10)) {
            if (L1 == 0 && L2 == 0)
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cannotgetgps), Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cannotgetadmincode), Toast.LENGTH_SHORT).show();
        } else {

            String str1 = ADMINCODE.substring(0, 2);
            String str2 = ADMINCODE.substring(2, 5);
            String str3 = ADMINCODE.substring(5, 10);
            if (isSave(str1, str2, str3)) {
                progressbar.setVisibility(View.VISIBLE);
                new HttpRequest(this).new API_116(MainActivity.dataLogin, data116, new IF_100() {
                    @Override
                    public void onSuccess() {
                        SavePositionLocaiton savePositionLocaiton = new SavePositionLocaiton(MainActivity.SHOP_POSITION, L1, L2, ADDR1, ADDR2, ADDR3);
                        Intent intent = getIntent();
                        intent.putExtra(Const.UPDATE_POSITION, new Gson().toJson(savePositionLocaiton));
                        setResult(Const.REQUEST_CODE, intent);
                        finish();
                    }

                    @Override
                    public void onFail() {
                        dialogTryAgain();
                    }
                }).execute();
            } else {
                Toast.makeText(getApplicationContext(), "ADMINCODE incorrect, try again", Toast.LENGTH_SHORT).show();
            }


        }
    }


    CustomDialogTryAgain customDialogTryAgain;

    void dialogTryAgain() {
        customDialogTryAgain = new CustomDialogTryAgain(MapActivity.this, getResources().getString(R.string.problemupload)
                , getResources().getString(R.string.tryagain), getResources().getString(R.string.exit), new OnClickDialog() {
            @Override
            public void btnOK() {
                if (data116.length() > 0 && L1 != 0 && L2 != 0 && ADMINCODE.trim().length() == 10) {
                    DatabaseHandler db = new DatabaseHandler(MapActivity.this);
                    RESEND_116 ob = new RESEND_116(data116);
                    db.add_116(ob);
                }
                SavePositionLocaiton savePositionLocaiton = new SavePositionLocaiton(MainActivity.SHOP_POSITION, L1, L2, ADDR1, ADDR2, ADDR3);
                Intent intent = getIntent();
                intent.putExtra(Const.UPDATE_POSITION, new Gson().toJson(savePositionLocaiton));
                setResult(Const.REQUEST_CODE, intent);
                finish();
                customDialogTryAgain.dismiss();

            }

            @Override
            public void btnCancel() {
                customDialogTryAgain.dismiss();
                API_116();



            }
        });
        customDialogTryAgain.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        if (v == btnBack) {
            finish();
        } else if (v == imgGPS) {
            if (mLocation != null) {
                LON = mLocation.getLongitude();
                LAT = mLocation.getLatitude();
                getAddressLocation(LAT, LON, "");
                getAdmincodeFromMyPosition(LON, LAT);


            } else {
                Toast.makeText(getApplicationContext(), "can not get GPS", Toast.LENGTH_SHORT).show();
            }

        } else if (v == btnSave) {
            if (LON < 0 || LAT < 0) {
                Toast.makeText(getApplicationContext(), "GPS is incorrect, choose again", Toast.LENGTH_SHORT).show();
            } else {
                if (KIND == 0) {

                    customDialog = new CustomDialog(this, getResources().getString(R.string.adddata), new OnClickDialog() {
                        @Override
                        public void btnOK() {
                            customDialog.dismiss();
                            initDB116();
                            API_116();
                        }

                        @Override
                        public void btnCancel() {
                            customDialog.dismiss();
                        }
                    });
                    customDialog.show();
                } else if (KIND == 1) {
                    if (LON == 0 && LAT == 0) {
                        Toast.makeText(getApplicationContext(), "Can not get GPS", Toast.LENGTH_SHORT).show();
                    } else {
                        backtonewshop();
                    }
                }
            }
        } else if (v == imgMyShop) {
            if (DATA_SHOP != null && DATA_SHOP.length() > 0) {
                LON = TEMP_LON;
                LAT = TEMP_LAT;
//                wv.loadUrl("javascript:CaptionMarkerAdd([" + DATA_SHOP + "]);");
                getAddressLocation(LAT, LON, "");
                getAdmincodeFromMyPosition(LON, LAT);
            }
        } else if (v == imgNear) {
            String ad = ADDR1 + ADDR2 + ADDR3;
            if (LON != 0 && LAT != 0 && ad.length() == 10) {
                getListNearShop(ad, LON, LAT);
            } else {
                Toast.makeText(getApplicationContext(), "Can not get near shop", Toast.LENGTH_SHORT).show();
            }

        }
    }


    String title = "";

    void getListNearSuccess(List<NearShop> LIST) {
        List<SendParamNearShop> lst = new ArrayList<>();
        for (NearShop obj : LIST) {

            double lon = 0;
            double lat = 0;

            if (obj.getV3().trim().length() > 0) {
                lon = (double) Integer.parseInt(obj.getV3().trim()) / Const.GPS_WGS84;
            }
            if (obj.getV4().trim().length() > 0) {
                lat = (double) Integer.parseInt(obj.getV4().trim()) / Const.GPS_WGS84;
            }
            String shop = "";
            String name = obj.getV2().trim();
            String time = "";
            String code = obj.getV1().trim();
            String address = obj.getV5().trim();
            SendParamNearShop ob = new SendParamNearShop(lon, lat, shop, name, time, code, address);
            lst.add(ob);
        }


        String data = new Gson().toJson(lst);
        wv.loadUrl("javascript:SetNearbyShop(" + data + ");");
//        wv.loadUrl("javascript:ShowNearbyShop();");
    }

    void getListNearFail() {

    }


    void getListNearShop(String ADMINCODE, double LON, double LAT) {
        progressbar.setVisibility(View.VISIBLE);

        long numOne = (long) (LON * Const.GPS_WGS84);
        long numTwo = (long) (LAT * Const.GPS_WGS84);
        String strLON = "" + numOne;
        String strLAT = "" + numTwo;
//        String strLON = "38373830";
//        String strLAT = "3846246";
//        ADMINCODE = "7978527595";
        new HttpRequest(this).new API_7(MainActivity.dataLogin, ADMINCODE, strLON, strLAT, new IF_7() {
            @Override
            public void onSuccess(List<NearShop> LIST) {
                getListNearSuccess(LIST);
                progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onNoInternet() {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
                getListNearFail();
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
                progressbar.setVisibility(View.GONE);
                getListNearFail();
            }

            @Override
            public void onError(String s) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                getListNearFail();
            }
        }).execute();
    }
}
