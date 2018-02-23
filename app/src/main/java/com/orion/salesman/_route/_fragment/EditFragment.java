package com.orion.salesman._route._fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.MapActivity;
import com.orion.salesman.R;
import com.orion.salesman.ShopInfoActivity;
import com.orion.salesman._adaper.CustomAdapterCodeHTable;
import com.orion.salesman._adaper.CustomAdapterNewShop;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomDialog;
import com.orion.salesman._class.CustomDialogTryAgain;
import com.orion.salesman._class.GpsManager;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._interface.IF_117;
import com.orion.salesman._interface.IF_DELETE_IMAGE;
import com.orion.salesman._interface.OnClickDialog;
import com.orion.salesman._object.AddNewShop;
import com.orion.salesman._object.AddressSQLite;
import com.orion.salesman._object.AddressSQLiteStreets;
import com.orion.salesman._object.CodeH;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.RESENDNEWSHOP;
import com.orion.salesman._object.ResultAddShop;
import com.orion.salesman._object.UpPicture;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._route._object.DisplayInfo;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._sqlite.DataBaseCodeH;
import com.orion.salesman._sqlite.DataBaseHelper;
import com.orion.salesman._sqlite.DatabaseHandler;
import com.orion.salesman._summary._adapter.DataSummarySales;
import com.orion.salesman._summary._object.SummaryDelivery;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 4/8/2016.
 */
public class EditFragment extends Fragment implements TextWatcher {
    private String TAG = "EditFragment";
    private InforShopDetails shopDetailsList;
    private String SEQ;
    public static EditFragment instance = null;
    private CustomDialog customDialog;
    String StrAD = "";
    String StrLon = "";
    String StrLat = "";


    public void updateAdminCode(String admincode) {
        StrAD = admincode;
        ADDR1 = admincode.substring(0, 2);
        ADDR2 = admincode.substring(2, 5);
        ADDR3 = admincode.substring(5, 10);
        Log.d(TAG, "ADDR1:" + ADDR1 + " ADDR2:" + ADDR2 + " ADDR3:" + ADDR3);
        updateSpinner(admincode);
        if (shopDetailsList != null) {
            shopDetailsList.setV4(ADDR1);
            shopDetailsList.setV5(ADDR2);
            shopDetailsList.setV29(admincode);
        }
    }

    public void updateLocation(String s) {
        Type listType = new TypeToken<List<Double>>() {
        }.getType();
        List<Double> arList = new Gson().fromJson(s, listType);
        LON = (long) (arList.get(0) * Const.GPS_WGS84);
        LAT = (long) (arList.get(1) * Const.GPS_WGS84);
        StrLon = "" + LON;
        StrLat = "" + LAT;
        Log.d(TAG, "LON:" + LON + " LAT:" + LAT);
        if (shopDetailsList != null) {
            shopDetailsList.setV14("" + LON);
            shopDetailsList.setV15("" + LAT);
        }


//        Log.d(TAG,""+arList.get(0));
//        Log.d(TAG,""+arList.get(1));
        tvLocation.setText("lon: " + arList.get(0) + "\nlat: " + arList.get(1));
    }

    @SuppressLint("ValidFragment")
    public EditFragment(InforShopDetails shopDetailsList, String SEQ) {
        this.shopDetailsList = shopDetailsList;
        this.SEQ = SEQ;
    }

    public EditFragment() {

    }


    protected ShopInfoActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (ShopInfoActivity) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;

    }


    int isScale = 1;
    public static File file;
    private static int fileSize;
    private AutoCompleteTextView singleComplete;
    private Button btnGPS;
    private EditText edShopName, edHouseNo, edOwner, edMobile;
    private TextView edSEQ;
    private ImageView btnSave;
    private int c_year;
    private int c_month;
    private int c_date;
    private int checDist = 0;
    private int checWard = 0;
    private TextView tvDate, tvCloseDate, edDelivery, tvRoute;
    private List<AddressSQLite> addresses = new ArrayList<>();
    private List<AddressSQLiteStreets> addressStreet = new ArrayList<>();
    private ArrayList<AddressSQLite> listProvince = new ArrayList<>();
    private ArrayList<AddressSQLite> listDist = new ArrayList<>();
    private ArrayList<String> listGrade = new ArrayList<>();
    private List<CodeH> listRoute = new ArrayList<>();
    private List<CodeH> listChannel = new ArrayList<>();
    private Button btnAbove;
    private ArrayList<String> listSeq = new ArrayList<>();
    private List<CodeH> codeHList = new ArrayList<>();
    private List<CodeH> listReason = new ArrayList<>();
    private Spinner spProvince, spDist, spGrade, spDelivery, spRoute, spSeq, spChannel, spReason, spWard;
    private TableRow trReason, trCloseDate, trSEQ, trChannel;

    boolean checkAddList(ArrayList<AddressSQLite> listDist, String s) {
        for (int i = 0; i < listDist.size(); i++) {
            if (s.equals(listDist.get(i).getADDR5()))
                return false;
        }
        return true;
    }

    class getPDACOM extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            getDB();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public void getDB() {
        try {
            DataBaseHelper db = new DataBaseHelper(mActivity);
            db.openDB();
            addresses.clear();
            addressStreet.clear();
            addresses = db.getAddress();
            addressStreet = db.getAddressStreet();

            DataBaseCodeH dataBaseCodeH = new DataBaseCodeH(mActivity);
            dataBaseCodeH.openDB();
            codeHList = dataBaseCodeH.getData();
            Log.d(TAG, "s1:" + addresses.size() + " s2:" + addressStreet.size());
        } catch (Exception e) {
//            MyApplication.getInstance().showToast(getResources().getString(R.string.errordb));
            e.printStackTrace();
        }
    }

    public boolean checkContains(ArrayList<AddressSQLite> arrayList, String s) {
        for (AddressSQLite str : arrayList) {
            if (str.getADDR1().equals(s))
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

    CustomAdapterNewShop adapterDist, adapterWard;
    CustomAdapterNewShop gradeDelivery;
    private ArrayList<AddressSQLite> listStreet = new ArrayList<>();

    public void ShowDateDialog(final int check) {
        //0: createdate, 1:closedate
        DatePickerDialog dpd = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                c_year = year;
                c_month = monthOfYear + 1;
                c_date = dayOfMonth;
                if (check == 0) {
                    DATE = year + Const.formatFullDate(c_month) + Const.formatFullDate(c_date);
                    tvDate.setText(year + "." + Const.formatFullDate(c_month) + "." + Const.formatFullDate(c_date));
                } else if (check == 1) {
                    CLOSEDATE = year + Const.formatFullDate(c_month) + Const.formatFullDate(c_date);
                    tvCloseDate.setText(year + "." + Const.formatFullDate(c_month) + "." + Const.formatFullDate(c_date));
                }


            }
        }, c_year, c_month - 1, c_date);
        dpd.show();
    }

    String CDATETIME = "", ROUTSEQ = "", CUSTCD = "";


    String data117 = "";

    void initDB117() {
        ADDRSTR = getCodeStreets(singleComplete.getText().toString().trim());
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);

        CDATETIME = year + Const.formatFullDate(month) + Const.formatFullDate(date) + Const.formatFullDate(hour) + Const.formatFullDate(minute) + Const.formatFullDate(second);
        String MNGEMPID = dataLogin.getMNGEMPID();
        /*
        * MODE: 1=New Shop, 2=Stop Shop Trade, 3=Update Shop
        * */
        String MODE = "1";

        String TEAMCD = dataLogin.getTEAM();
        AddNewShop ob = new AddNewShop();
        if (MainActivity.checkNewOrEditShop == 0) {
            CLOSEDATE = "";
            REASON = "";
            MODE = "1";
        } else if (MainActivity.checkNewOrEditShop == 1) {
            CLOSEDATE = "";
            REASON = "";
            MODE = "3";
        } else {
            MODE = "2";
        }
        if (MainActivity.checkNewOrEditShop != 0) {
            ROUTSEQ = MainActivity.SEQ;
            CUSTCD = MainActivity.CUSTCD;
        }

        ob.setCUSTCD(CUSTCD);
        ob.setROUTSEQ(ROUTSEQ);
        ob.setMODE(MODE);
        ob.setCDATETIME(CDATETIME);
        ob.setROUTNO(ROUTNO);
        ob.setTEAMCD(TEAMCD);
        ob.setCUSTNM(edShopName.getText().toString().trim());
        ob.setADDR1(ADDR1);
        ob.setADDR2(ADDR2);
//            ADDR3 = getIdStreet(singleComplete.getText().toString().trim());

        ob.setADDR3(STREETCODE);
        ob.setADDRSTR(singleComplete.getText().toString().trim());
//            ADMINCODE = "1212312345";
        ADMINCODE = ADDR1 + ADDR2 + ADDR3;
        ob.setADMINCODE(ADMINCODE);
        ob.setHOUSENO(edHouseNo.getText().toString().trim());
        ob.setGRADE(GRADE);
        ob.setOWNERNM(edOwner.getText().toString().trim());
        ob.setHPNO(edMobile.getText().toString().trim());
        ob.setCREATEDATE(CREATEDATE);
        ob.setDEALERID(DEALERID);
        ob.setDEALERNM(DEALERNM);
        ob.setCHANNEL(CHANNEL);
        ob.setLON("" + LON);
        ob.setLAT("" + LAT);

        IMG1URL = listPictureSend.get(0);
        IMG2URL = listPictureSend.get(1);

        ob.setIMG1URL(IMG1URL);
        ob.setIMG2URL(IMG2URL);
        Log.d(TAG, "IMG1URL:" + IMG1URL);
        Log.d(TAG, "IMG2URL:" + IMG2URL);
        ob.setENDDATE(CLOSEDATE);
        ob.setREASON(REASON);
        data117 = new Gson().toJson(ob);
        Log.d(TAG, "data117:" + data117);
    }

    void sendAPI(DataLogin dataLogin) {
        progressbar.setVisibility(View.VISIBLE);
        new HttpRequest(mActivity).new API_117(dataLogin, data117, new IF_117() {
            @Override
            public void onSuccess(String ID) {
                progressbar.setVisibility(View.GONE);

                if (MainActivity.checkNewOrEditShop == 1) {

                    for (int i = 0; i < listPictureDelete.size(); i++) {
                        if (listPictureDelete.get(i).trim().length() > 0) {
                            if (!listPictureDelete.get(i).trim().equals(listPictureSend.get(i).trim())) {
                                String data[] = listPictureDelete.get(i).trim().split("/");
                                String IMG_NAME = data[data.length - 1].trim();
                                new HttpRequest(getActivity()).new DELETE_IMAGE(IMG_NAME, new IF_DELETE_IMAGE() {
                                    @Override
                                    public void onComplete(String result) {

                                    }
                                }).execute();
                            }
                        }
                    }

                }


                Log.d(TAG, "API 117 OK");
                String CUSTCD = ID;
                if (shopDetailsList == null)
                    shopDetailsList = new InforShopDetails();
                shopDetailsList.setV14("" + LON);
                shopDetailsList.setV15("" + LAT);
                shopDetailsList.setV3("" + edShopName.getText().toString().trim());
                shopDetailsList.setV4("" + ADDR1);
                shopDetailsList.setV5("" + ADDR2);
                shopDetailsList.setV6("" + STREETCODE);
                shopDetailsList.setV7("" + edHouseNo.getText().toString().trim());
                shopDetailsList.setV26("" + edOwner.getText().toString().trim());
                shopDetailsList.setV16("" + IMG1URL);
                shopDetailsList.setV17("" + IMG2URL);
                if (MainActivity.TEAM.equals(Const.PIE_TEAM))
                    shopDetailsList.setV8(GRADE);
                else if (MainActivity.TEAM.equals(Const.SNACK_TEAM))
                    shopDetailsList.setV9(GRADE);
                else shopDetailsList.setV10(GRADE);
                shopDetailsList.setV28("" + edMobile.getText().toString().trim());
                shopDetailsList.setV18("" + DEALERID);
                shopDetailsList.setV13("" + REASON);
                shopDetailsList.setV29(ADMINCODE);
                if (MainActivity.checkNewOrEditShop == 0) {
                    shopDetailsList.setV1(CUSTCD);
                    shopDetailsList.setV11(CREATEDATE);
                    Intent intent = mActivity.getIntent();
                    intent.putExtra(Const.UPDATE_POSITION, new Gson().toJson(shopDetailsList));
                    mActivity.setResult(Const.REQUEST_NEW_SHOP, intent);
                    mActivity.finish();
                } else {
                    if (mActivity != null) {
                        Intent intent = mActivity.getIntent();
                        intent.putExtra(Const.UPDATE_POSITION, new Gson().toJson(shopDetailsList));
                        mActivity.setResult(Const.REQUEST_UPDATE_SHOP, intent);
                        mActivity.finish();
                    }
                }
            }

            @Override
            public void onFail() {
                progressbar.setVisibility(View.GONE);
                Log.e(TAG, "API 117 Fail...");
                if (MainActivity.checkNewOrEditShop == 0) {
                    tryAgainNewShop();
                } else {
                    tryAgainEditShop();
                }
            }
        }).execute();

    }

    CustomDialogTryAgain customDialogTryAgain, dialogEdit;

    void tryAgainEditShop() {
        dialogEdit = new CustomDialogTryAgain(mActivity, getResources().getString(R.string.problemupload)
                , getResources().getString(R.string.tryagain), getResources().getString(R.string.exit), new OnClickDialog() {
            @Override
            public void btnOK() {
//                sendAPI(dataLogin);
                progressbar.setVisibility(View.VISIBLE);
                saveDataReSend();
                dialogEdit.dismiss();
            }

            @Override
            public void btnCancel() {
                if (data117.length() > 0) {
                    DatabaseHandler db = new DatabaseHandler(mActivity);
                    RESENDNEWSHOP OB = new RESENDNEWSHOP(data117);
                    db.add_NEWSHOP(OB);
                }
                if (MainActivity.checkNewOrEditShop == 1) {
                    Intent intent = ((Activity) mActivity).getIntent();
                    intent.putExtra(Const.UPDATE_POSITION, new Gson().toJson(shopDetailsList));
                    ((Activity) mActivity).setResult(Const.REQUEST_UPDATE_SHOP, intent);
                    ((Activity) mActivity).finish();
                } else {
                    ((Activity) mActivity).finish();
                }
                dialogEdit.dismiss();

            }
        });
        dialogEdit.show();
    }

    void tryAgainNewShop() {
        customDialogTryAgain = new CustomDialogTryAgain(mActivity, getResources().getString(R.string.newshopfail)
                , getResources().getString(R.string.tryagain), getResources().getString(R.string.exit), new OnClickDialog() {
            @Override
            public void btnOK() {
//                sendAPI(dataLogin);
                progressbar.setVisibility(View.VISIBLE);
                saveDataReSend();
                customDialogTryAgain.dismiss();
            }

            @Override
            public void btnCancel() {
                customDialogTryAgain.dismiss();
                mActivity.finish();
            }
        });
        customDialogTryAgain.show();
    }

    private DataLogin dataLogin;
    private String DATE = "", CLOSEDATE = "";
    private String ROUTNO = "", ADDR1 = "", ADDR2 = "", ADDR3 = "", ADDRSTR = "", ADMINCODE = "",
            GRADE = "", DEALERID = "", DEALERNM = "", CHANNEL = "", IMG1URL = "",
            IMG2URL = "", REASON = "", CREATEDATE = "", STREETCODE = "";

    void createGPS() {
        GpsManager.getInstance(mActivity).getLocation();
    }

    public static long LON = 0, LAT = 0;

//    void getLocal() {
//        createGPS();
//        ArrayList<Location> loc = GpsManager.getInstance().getGpsData();
//        if (loc != null) {
//            if (loc.size() != 0) {
//                int s = 360000;
//                Location ob = loc.get(0);
//                LON = (long) (ob.getLongitude() * s);
//                LAT = (long) (ob.getLatitude() * s);
//                Log.d(TAG, "LON:" + LON + ": LAT" + LAT);
//            }
//            MyApplication.getInstance().showToast("get GPS ok");
//        } else {
//            MyApplication.getInstance().showToast(getResources().getString(R.string.getGPSFail));
//        }
//    }

    private boolean flagDist = false, flagDealer = false, flagGrade = false, flagChannel = false;
    private List<AddressSQLiteStreets> getAddressStreet = new ArrayList<>();
    private List<String> streetsName = new ArrayList<>();

    String getCodeStreets(String text) {
        String s = "";
        for (AddressSQLiteStreets a : getAddressStreet) {
            if (text.equals(a.getSTREETNM()))
                s = a.getSTREETCD();
        }
        return s;
    }

    String getNameStreet(String code) {
        String s = "";
        for (AddressSQLiteStreets a : getAddressStreet) {
            if (code.equals(a.getSTREETCD()))
                s = a.getSTREETNM();
        }
        return s;
    }

    public boolean checkContainsStreet(ArrayList<AddressSQLite> arrayList, String s) {
        for (AddressSQLite str : arrayList) {
            if (str.getADDR3().equals(s))
                return true;
        }
        return false;
    }


    private ImageView img_1, img_2;
    private ProgressDialog progressDialog;
    private FrameLayout btnRemove, btnRemove2;

    public String dateToString(Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    private ArrayList<String> listPicture = new ArrayList<>();
    private ArrayList<String> listPictureSend = new ArrayList<>();
    private ArrayList<String> listPictureDelete = new ArrayList<>();
    private ProgressBar progressbar;

    String getIdStreet(String name) {
        String id = "";
        for (AddressSQLiteStreets s : getAddressStreet) {
            if (s.getSTREETNM().trim().equals(name)) {
                id = s.getSTREETCD();
                break;
            }
        }
        return id;
    }

    class loadData extends AsyncTask<String, String, String> {
        View v;

        public loadData(View v) {
            this.v = v;
        }

        @Override
        protected String doInBackground(String... params) {
            initDB(v);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            init(v);
            progressbar.setVisibility(View.GONE);
            ShopInfoActivity.checkClick = true;
        }
    }

    String[] arr;

    void initDB(View v) {
        getAddressStreet.clear();
        DataBaseHelper db = new DataBaseHelper(mActivity);
        db.openDB();
        getAddressStreet = db.getAddressStreet();
        for (AddressSQLiteStreets s : getAddressStreet) {
            streetsName.add(s.getSTREETNM().trim());
        }
        arr = new String[streetsName.size()];
        arr = streetsName.toArray(arr);

    }

    TextView tvLocation;

    public void init(View v) {
        instance = this;
        btnRemove = (FrameLayout) v.findViewById(R.id.btnRemove);
        btnRemove2 = (FrameLayout) v.findViewById(R.id.btnRemove2);

        img_1 = (ImageView) v.findViewById(R.id.img_1);
        img_2 = (ImageView) v.findViewById(R.id.img_2);
        listPicture.clear();
        listPictureDelete.add("");
        listPictureDelete.add("");
        listPictureSend.add("");
        listPictureSend.add("");
        listPicture.add("");
        listPicture.add("");
        tvLocation = (TextView) v.findViewById(R.id.tvLocation);
        singleComplete = (AutoCompleteTextView) v.findViewById(R.id.editauto);
        singleComplete.addTextChangedListener(this);
        singleComplete.setAdapter(new ArrayAdapter<String>(mActivity, android.R.layout.simple_list_item_1, arr));
        if (shopDetailsList != null) {
            String str = "errorLink";
            String str2 = "errorLink";
            if (shopDetailsList.getV16().trim().length() > 0) {
                str = shopDetailsList.getV16();
                listPictureSend.set(0, str);
                listPictureDelete.set(0, str);

                btnRemove.setVisibility(View.VISIBLE);
            } else {
                btnRemove.setVisibility(View.GONE);
                img_1.setImageResource(R.drawable.take_a_picture);
            }
            if (shopDetailsList.getV17().trim().length() > 0) {
                str2 = shopDetailsList.getV17();
                listPictureSend.set(1, str2);
                listPictureDelete.set(1, str2);

                btnRemove2.setVisibility(View.VISIBLE);
            } else {
                btnRemove2.setVisibility(View.GONE);
                img_2.setImageResource(R.drawable.take_a_picture);
            }
            if (shopDetailsList.getV14().trim().length() != 0) {
                LON = Long.parseLong(shopDetailsList.getV14());
                tvLocation.setText("lon: " + (float) Integer.parseInt(shopDetailsList.getV14()) / Const.GPS_WGS84
                        + "\nlat: "
                        + (float) Integer.parseInt(shopDetailsList.getV15()) / Const.GPS_WGS84);
            }
            if (shopDetailsList.getV15().trim().length() != 0) {
                LAT = Long.parseLong(shopDetailsList.getV15());
            }
        }


        if (MainActivity.checkNewOrEditShop != 0) {
            Glide.with(mActivity)
                    .load(shopDetailsList.getV16())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new GlideDrawableImageViewTarget(img_1) {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                            img_1.setImageResource(R.drawable.warning);
                        }
                    });

            Glide.with(mActivity)
                    .load(shopDetailsList.getV17())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new GlideDrawableImageViewTarget(img_2) {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                            img_2.setImageResource(R.drawable.warning);
                        }
                    });
        }

        trReason = (TableRow) v.findViewById(R.id.trReason);
        trCloseDate = (TableRow) v.findViewById(R.id.trCloseDate);
        btnAbove = (Button) v.findViewById(R.id.btnAbove);
        trSEQ = (TableRow) v.findViewById(R.id.trSEQ);
        trChannel = (TableRow) v.findViewById(R.id.trChannel);
        if (MainActivity.checkNewOrEditShop == 0) {
            trReason.setVisibility(View.GONE);
            trCloseDate.setVisibility(View.GONE);
            trSEQ.setVisibility(View.GONE);
            btnAbove.setVisibility(View.INVISIBLE);
        } else if (MainActivity.checkNewOrEditShop == 1) {
            trCloseDate.setVisibility(View.GONE);
            trReason.setVisibility(View.GONE);
        } else {

        }

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        CREATEDATE = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        DATE = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        CLOSEDATE = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        String jsondata = new PrefManager(mActivity).getInfoLogin();
        dataLogin = new Gson().fromJson(jsondata, DataLogin.class);
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCanceledOnTouchOutside(false);
        btnSave = (ImageView) v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        edDelivery = (TextView) v.findViewById(R.id.edDelivery);
        tvRoute = (TextView) v.findViewById(R.id.tvRoute);
//        edDelivery.setEnabled(false);
        edSEQ = (TextView) v.findViewById(R.id.edSEQ);
        edMobile = (EditText) v.findViewById(R.id.edMobile);
        edOwner = (EditText) v.findViewById(R.id.edOwner);
        edHouseNo = (EditText) v.findViewById(R.id.edHouseNo);
        edShopName = (EditText) v.findViewById(R.id.edShopName);
        btnGPS = (Button) v.findViewById(R.id.btnGPS);
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getLocal();
                Intent intent = new Intent(mActivity, MapActivity.class);
                intent.putExtra(Const.CHECK_KIND_GOTO_MAP, 1);
                intent.putExtra(Const.SHOP_INFO_DETAIL, new Gson().toJson(shopDetailsList));


                intent.putExtra(Const.STRING_AD, StrAD);
                intent.putExtra(Const.STRING_LON, StrLon);
                intent.putExtra(Const.STRING_LAT, StrLat);

                ((Activity) mActivity).startActivityForResult(intent, Const.REQUEST_CODE_INFO);
            }
        });
        Calendar calendar = Calendar.getInstance();
        c_year = calendar.get(Calendar.YEAR);
        c_month = calendar.get(Calendar.MONTH) + 1;
        c_date = calendar.get(Calendar.DATE);
//        new getPDACOM().execute();
        getDB();
//        spDelivery = (Spinner) v.findViewById(R.id.spDelivery);
        spWard = (Spinner) v.findViewById(R.id.spWard);
        spDist = (Spinner) v.findViewById(R.id.spDist);
        spProvince = (Spinner) v.findViewById(R.id.spProvince);

        spRoute = (Spinner) v.findViewById(R.id.spRoute);
        spGrade = (Spinner) v.findViewById(R.id.spGrade);
//        spSeq = (Spinner) v.findViewById(R.id.spSeq);
        spChannel = (Spinner) v.findViewById(R.id.spChannel);
        spReason = (Spinner) v.findViewById(R.id.spReason);

        adapterWard = new CustomAdapterNewShop(mActivity, listStreet, 3);
        spWard.setAdapter(adapterWard);
        spWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "listStreet:" + listStreet.get(i).getADDR3() + ":" + listStreet.get(i).getADDR6());
                ADDR3 = listStreet.get(i).getADDR3();
                Log.d(TAG, "ADDR3:" + ADDR3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapterDist = new CustomAdapterNewShop(mActivity, listDist, 1);
        spDist.setAdapter(adapterDist);
        spDist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checDist = i;
                listStreet.clear();
                Log.d(TAG, "listDist:" + listDist.get(i).getADDR2() + ":" + listDist.get(i).getADDR5());
                ADDR2 = listDist.get(i).getADDR2();
                for (int k = 0; k < addresses.size(); k++) {
                    if (addresses.get(k).getADDR1().equals(ADDR1) && addresses.get(k).getADDR2().equals(ADDR2)) {
                        DEALERID = addresses.get(k).getADDR7();
                        DEALERNM = addresses.get(k).getADDR9();
//                        Log.e(TAG, DEALERID + ":" + DEALERID);
                        edDelivery.setText(DEALERNM);
                    }
                    AddressSQLite s = addresses.get(k);
                    if (s.getADDR1().equals(ADDR1) && s.getADDR2().equals(ADDR2)) {
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
                if (shopDetailsList != null && !flagDist) {
                    flagDist = true;
                    for (int k = 0; k < listDist.size(); k++) {
                        if (shopDetailsList.getV5().equals(listDist.get(k).getADDR2())) {
                            spDist.setSelection(k);
                            break;
                        }
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapterWard.notifyDataSetChanged();
                        if (shopDetailsList != null) {
                            if (shopDetailsList.getV29() != null && shopDetailsList.getV29().length() == 10) {
                                Log.d(TAG, "getV29");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (int i = 0; i < listStreet.size(); i++) {
                                            AddressSQLite ob = listStreet.get(i);
                                            if (ob.getADDR3().equals(shopDetailsList.getV29().substring(5, 10))) {
                                                spWard.setSelection(i);
                                                break;
                                            }
                                        }
                                    }
                                }, 1000);
                            } else {
                                if (checWard == 0)
                                    checWard = 1;
                                else checWard = 0;
                                spWard.setSelection(checWard);
                            }
                        } else {
                            if (checWard == 0)
                                checWard = 1;
                            else checWard = 0;
                            spWard.setSelection(checWard);
                        }

                    }
                }, 500);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        listProvince.clear();
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
        CustomAdapterNewShop dataAdapter = new CustomAdapterNewShop(mActivity, listProvince, 0);
        spProvince.setAdapter(dataAdapter);
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "listProvince:" + listProvince.get(i).getADDR4() + ":" + listProvince.get(i).getADDR1());
                ADDR1 = listProvince.get(i).getADDR1();
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapterDist.notifyDataSetChanged();
                        if (checDist == 0)
                            spDist.setSelection(1);
                        else spDist.setSelection(0);
                    }
                }, 500);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listRoute.clear();
        listGrade.clear();
        for (CodeH s : codeHList) {
            if (s.getCODEGROUP().equalsIgnoreCase("RouteWeek"))
                listRoute.add(s);
            if (s.getCODEGROUP().equalsIgnoreCase("SHOPGRADE"))
                listGrade.add(s.getCODEKEY());
            if (s.getCODEGROUP().equalsIgnoreCase("CUSCHN1"))
                listChannel.add(s);
            if (s.getCODEGROUP().equalsIgnoreCase("REASON"))
                listReason.add(s);
        }

        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(mActivity, R.layout.row_spinner_add, listGrade);
        gradeAdapter.setDropDownViewResource(R.layout.row_simple_spinner_dropdown_item);
        spGrade.setAdapter(gradeAdapter);
        spGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                GRADE = listGrade.get(i);
                Log.d(TAG, "listGrade:" + GRADE);
                if (shopDetailsList != null && !flagGrade) {
                    String _Gra = "";
                    if (MainActivity.TEAM.equals(Const.PIE_TEAM))
                        _Gra = shopDetailsList.getV8();
                    else if (MainActivity.TEAM.equals(Const.SNACK_TEAM))
                        _Gra = shopDetailsList.getV9();
                    else _Gra = shopDetailsList.getV10();
                    for (int k = 0; k < listGrade.size(); k++) {
                        if (_Gra.equals(listGrade.get(k))) {
                            flagGrade = true;
                            spGrade.setSelection(k);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomAdapterCodeHTable gradeRoute = new CustomAdapterCodeHTable(mActivity, listRoute, 0);
        spRoute.setAdapter(gradeRoute);
        spRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "listRoute:" + listRoute.get(i).getCODEKEY() + ":" + listRoute.get(i).getCODEDESC());
                ROUTNO = listRoute.get(i).getCODEKEY();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        tvCloseDate = (TextView) v.findViewById(R.id.tvCloseDate);
        tvCloseDate.setText(year + "." + Const.formatFullDate(month) + "." + Const.formatFullDate(date));

        tvDate = (TextView) v.findViewById(R.id.tvDate);
        tvDate.setText(c_year + "." + Const.formatFullDate(c_month) + "." + Const.formatFullDate(c_date));

        CustomAdapterCodeHTable gradeChannel = new CustomAdapterCodeHTable(mActivity, listChannel, 1);
        spChannel.setAdapter(gradeChannel);
        spChannel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CHANNEL = listChannel.get(i).getCODEKEY();
                Log.d(TAG, "CHANNEL:" + CHANNEL);
                if (shopDetailsList != null && !flagChannel) {
                    flagChannel = true;
                    for (int k = 0; k < listChannel.size(); k++) {
                        if (shopDetailsList.getV30().equals(listChannel.get(k).getCODEKEY())) {
                            spChannel.setSelection(k);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        CustomAdapterCodeHTable ReasonAdapter = new CustomAdapterCodeHTable(mActivity, listReason, 2);
        spReason.setAdapter(ReasonAdapter);
        spReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                REASON = listReason.get(i).getCODEKEY();
                Log.d(TAG, "REASON:" + REASON);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (shopDetailsList != null) {
            edHouseNo.setText(shopDetailsList.getV7());
            edShopName.setText(shopDetailsList.getV3());
            edOwner.setText(shopDetailsList.getV26());
            edMobile.setText(shopDetailsList.getV28());
            edSEQ.setText(SEQ);
//            DEALERID = "", DEALERNM
            DEALERID = shopDetailsList.getV18();
            DEALERNM = shopDetailsList.getV19();
            edDelivery.setText(DEALERNM);
            ADDR1 = shopDetailsList.getV4();
            ADDR2 = shopDetailsList.getV5();
//            Log.e(TAG, DEALERID + ":" + DEALERNM + ":" + ADDR2 + ":" + ADDR1);
            for (int i = 0; i < listProvince.size(); i++) {
                if (ADDR1.equals(listProvince.get(i).getADDR1())) {
                    spProvince.setSelection(i);
                    break;
                }
            }
        }
        if (MainActivity.checkNewOrEditShop == 1) {
            spRoute.setVisibility(View.INVISIBLE);
            tvRoute.setVisibility(View.VISIBLE);
            tvRoute.setText(RouteFragment.NAME_ROUTE);
            ROUTNO = RouteFragment.KEY_ROUTE;
            String OPEN = shopDetailsList.getV11();
            Log.d(TAG, "OPEN:" + OPEN);
            CREATEDATE = OPEN;
            String s = OPEN.substring(0, 4) + "." + OPEN.substring(4, 6) + "." + OPEN.substring(6, 8);
            tvDate.setText(s);
            singleComplete.setText(getNameStreet(shopDetailsList.getV6()));
        } else if (MainActivity.checkNewOrEditShop == 2) {
            String OPEN = shopDetailsList.getV11();
            CREATEDATE = OPEN;
            String s = OPEN.substring(0, 4) + "." + OPEN.substring(4, 6) + "." + OPEN.substring(6, 8);
            tvDate.setText(s);

            singleComplete.setText(getNameStreet(shopDetailsList.getV6()));
            disableSpinner(spProvince);
            disableSpinner(spDist);
            disableSpinner(spRoute);
            disableSpinner(spGrade);
            disableSpinner(spChannel);
            disableEdit(edShopName);
            disableEdit(singleComplete);
            disableEdit(edOwner);
            disableEdit(edHouseNo);
//            disableEdit(edSEQ);
            disableEdit(edMobile);
        }

        if (MainActivity.checkNewOrEditShop == 0) {
            img_1.setImageResource(R.drawable.take_a_picture);
            img_2.setImageResource(R.drawable.take_a_picture);
        } else {

        }

        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra("android.intent.extras.CAMERA_FACING", 0);
                String name = dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss");
                destination = new File(Environment.getExternalStorageDirectory(), name + ".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, Const.REQUEST_IMAGE);
            }
        });
        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String name = dateToString(new Date(), "yyyy-MM-dd-hh-mm-ss");
                destination = new File(Environment.getExternalStorageDirectory(), name + ".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
                startActivityForResult(intent, Const.REQUEST_IMAGE_2);
            }
        });
        frParent.setVisibility(View.VISIBLE);


        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listPictureSend.set(0, "");
                listPicture.set(0, "");
                btnRemove.setVisibility(View.GONE);
                img_1.setImageResource(R.drawable.take_a_picture);


//                listPicture
            }
        });
        btnRemove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listPictureSend.set(1, "");
                listPicture.set(1, "");
                btnRemove2.setVisibility(View.GONE);
                img_2.setImageResource(R.drawable.take_a_picture);
            }
        });


        if (MainActivity.checkNewOrEditShop == 0) {
//            if (RouteFragment.ADMINCODE.length() == 10) {
//                try {
//                    updateSpinner(RouteFragment.ADMINCODE);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            if (MainActivity.LON != 0 && MainActivity.LAT != 0) {
                Log.d(TAG, "getAdmincodeFromMyPosition");
                btnGPS.setEnabled(true);
                getAdmincodeFromMyPosition(MainActivity.LON, MainActivity.LAT);
            } else {
                btnGPS.setEnabled(false);
                Log.d(TAG, "GPS not working");
            }

        }

    }

    void updateSpinner(final String s) {
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
                        for (int i = 0; i < listStreet.size(); i++) {
                            AddressSQLite ob = listStreet.get(i);
                            if (ob.getADDR3().equals(ADDR3)) {
                                spWard.setSelection(i);
                                break;
                            }
                        }
                    }
                }, 1000);

            }
        }, 1000);
    }

    File destination;
    String imagePath;

    boolean isSave(String s1, String s2, String s3) {
        for (AddressSQLite s : addresses) {
            if (s.getADDR1().trim().equals(s1) && s.getADDR2().trim().equals(s2) && s.getADDR3().trim().equals(s3))
                return true;
        }
        return false;
    }

    void saveData() {
        STREETCODE = getIdStreet(singleComplete.getText().toString().trim());
        ADMINCODE = ADDR1 + ADDR2 + ADDR3;
        Log.d(TAG, "ADMINCODE:" + ADMINCODE);
        if (STREETCODE.length() == 0 || ADMINCODE.trim().length() != 10) {
            if (STREETCODE.length() == 0)
                Toast.makeText(mActivity, getResources().getString(R.string.cannotgetidstreet), Toast.LENGTH_SHORT).show();
            else if (ADMINCODE.trim().length() != 10)
                Toast.makeText(mActivity, getResources().getString(R.string.cannotgetadmincode), Toast.LENGTH_SHORT).show();
        } else {
            initDB117();
            String str1 = ADMINCODE.substring(0, 2);
            String str2 = ADMINCODE.substring(2, 5);
            String str3 = ADMINCODE.substring(5, 10);
            if (isSave(str1, str2, str3)) {
                if (MainActivity.checkNewOrEditShop == 2) {
                    Trade();
                } else if (MainActivity.checkNewOrEditShop == 1) {
                    Edit();
                } else {
                    editAndNew();
                }
            } else {
                Toast.makeText(getActivity(), "ADMINCODE incorrect, try again", Toast.LENGTH_SHORT).show();
            }


        }
    }

    void saveDataReSend() {
        STREETCODE = getIdStreet(singleComplete.getText().toString().trim());
        ADMINCODE = ADDR1 + ADDR2 + ADDR3;
        if (STREETCODE.length() == 0 || ADMINCODE.trim().length() != 10) {
            if (STREETCODE.length() == 0)
                Toast.makeText(mActivity, getResources().getString(R.string.cannotgetidstreet), Toast.LENGTH_SHORT).show();
            else if (ADMINCODE.trim().length() != 10)
                Toast.makeText(mActivity, getResources().getString(R.string.cannotgetadmincode), Toast.LENGTH_SHORT).show();
        } else {
            initDB117();
            if (MainActivity.checkNewOrEditShop == 2) {
                sendAPI(dataLogin);
            } else if (MainActivity.checkNewOrEditShop == 1) {
                if (listPicture.get(0).trim().length() == 0 && listPicture.get(1).trim().length() == 0) {
                    sendAPI(dataLogin);

                } else {

                    for (int i = 0; i < listPicture.size(); i++) {
                        if (listPicture.get(i).trim().length() > 0) {
                            String s = listPicture.get(i);

                            int seq = i + 1;
                            new UploadFileToServer(s, seq).execute();
                        }

                    }
                }
            } else {
                if (listPicture.get(0).trim().length() == 0 && listPicture.get(1).trim().length() == 0) {
                    sendAPI(dataLogin);
                } else {

                    for (int i = 0; i < listPicture.size(); i++) {
                        if (listPicture.get(i).trim().length() > 0) {
                            String s = listPicture.get(i);
                            Log.d(TAG, s);
                            int seq = i + 1;
                            new UploadFileToServer(s, seq).execute();
                        }

                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                FileInputStream in = new FileInputStream(destination);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                imagePath = destination.getAbsolutePath();
                listPicture.set(0, imagePath);

                btnRemove.setVisibility(View.VISIBLE);

//                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
                Bitmap bmp = Const.createScaleBitmap(imagePath, 100);
                img_1.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == Const.REQUEST_IMAGE_2 && resultCode == Activity.RESULT_OK) {
            try {
                FileInputStream in = new FileInputStream(destination);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                imagePath = destination.getAbsolutePath();
                listPicture.set(1, imagePath);
                btnRemove2.setVisibility(View.VISIBLE);

//                Bitmap bmp = BitmapFactory.decodeStream(in, null, options);
                Bitmap bmp = Const.createScaleBitmap(imagePath, 100);
                img_2.setImageBitmap(bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.e(TAG, "Request cancelled");
        }
    }

    void disableEdit(EditText e) {
        e.setEnabled(false);
        e.setEnabled(false);
        e.setTextColor(Color.BLACK);
        Drawable drawable = e.getBackground();
        drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP); // change the drawable color
        if (Build.VERSION.SDK_INT > 16) {
            e.setBackground(drawable); // set the new drawable to EditText
        } else {
            e.setBackgroundDrawable(drawable); // use setBackgroundDrawable because setBackground required API 16
        }
    }

    void disableSpinner(Spinner s) {
        s.setBackgroundColor(Color.WHITE);
        s.setClickable(false);
        s.setEnabled(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FrameLayout frParent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.edit_fragment, container, false);
        LON = 0;
        LAT = 0;

        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        frParent = (FrameLayout) v.findViewById(R.id.frParent);
//        init(v);

        ShopInfoActivity.checkClick = false;
        new loadData(v).execute();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ShopInfoActivity.checkClick = false;
//                new loadData(v).execute();
//            }
//        }, 1000);
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.checkNewOrEditShop == 1)
            ShopInfoActivity.getInstance().setTitleForText(getResources().getString(R.string.editShop));
        else if (MainActivity.checkNewOrEditShop == 0)
            ShopInfoActivity.getInstance().setTitleForText(getResources().getString(R.string.newShop));
        else
            ShopInfoActivity.getInstance().setTitleForText(getResources().getString(R.string.trade));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public static String uploadFileToServer(String filename, String targetUrl, int Scale, int seq) {
        String response = "error";
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;

        String pathToOurFile = filename;
        String responseString = null;
        String urlServer = targetUrl;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024;
        FileInputStream fileInputStream = null;
        try {
            if (Scale == 0) {
                fileInputStream = new FileInputStream(new File(
                        pathToOurFile));
            } else {
                Bitmap bitmap = Const.createScaleBitmap(pathToOurFile, 200);
                String timeStamp = new SimpleDateFormat(Const.DATE_FORMAT_PICTURE,
                        Locale.getDefault()).format(new Date());
                String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/Scale";
                File dir = new File(file_path);
                if (!dir.exists())
                    dir.mkdirs();
                file = new File(dir, "scale" + timeStamp + Const.IMAGE_JPG);
                FileOutputStream fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                fileInputStream = new FileInputStream(file);
            }

            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();
            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setChunkedStreamingMode(1024);
            // Enable POST method
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(Const.TIME_OUT_UPLOAD_IMAGE);
            connection.setReadTimeout(Const.TIME_OUT_UPLOAD_IMAGE);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);

            connection.setRequestProperty("filename", Const.PDA_SHOP_IMAGE);
            String id = MainActivity.CUSTCD;
            if (MainActivity.checkNewOrEditShop == 0) {
                id = MainActivity.dataLogin.getUSERID() + "_" + seq;
            } else {
                id = MainActivity.CUSTCD + "_" + seq;
            }
            connection.setRequestProperty("id", id);
//            connection.setRequestProperty("id", "DELETE"); // delete
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String token = "anyvalye";
            outputStream.writeBytes("Content-Disposition: form-data; name=\"Token\"" + lineEnd);
            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
            outputStream.writeBytes("Content-Length: " + token.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(token + lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String taskId = "anyvalue";
            outputStream.writeBytes("Content-Disposition: form-data; name=\"TaskID\"" + lineEnd);
            outputStream.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
            outputStream.writeBytes("Content-Length: " + taskId.length() + lineEnd);
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(taskId + lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String connstr = null;
            connstr = "Content-Disposition: form-data; name=\"UploadFile\";filename=\""
                    + new URI(pathToOurFile).toASCIIString() + "\"" + lineEnd;
            outputStream.writeBytes(connstr);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            System.out.println("Image length " + bytesAvailable + "");
            fileSize = bytesAvailable;
            try {
                while (bytesRead > 0) {
                    try {
                        outputStream.write(buffer, 0, bufferSize);
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                        response = "outofmemoryerror";
                        return response;
                    }
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "error";
                return response;
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();
            System.out.println("Server Response Code " + " " + serverResponseCode);
            System.out.println("Server Response Message " + serverResponseMessage);

            if (serverResponseCode == 200) {
                response = "true";
            } else {
                response = "false";
            }
            fileInputStream.close();
            outputStream.flush();

            connection.getInputStream();
            //for android InputStream is = connection.getInputStream();
            java.io.InputStream is = connection.getInputStream();

            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }

            responseString = b.toString();
            System.out.println("response string is" + responseString); //Here is the actual output

            //Util.deleteFile(file);
            outputStream.close();
        } catch (Exception ex) {
            // Exception handling
            response = "error";
            System.out.println("Send file Exception" + ex.getMessage() + "");
            ex.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (outputStream != null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        if (response.equalsIgnoreCase("true"))
            return responseString;
        else {
            return "error ";
        }
    }

    private int countUploadImage = 0;

    public class UploadFileToServer extends AsyncTask<String, Integer, String> {
        String filePath;
        int seq;

        public UploadFileToServer(String filePath, int seq) {
            this.filePath = filePath;
            this.seq = seq;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        protected String doInBackground(String... params) {
            return handleBackground(params);
        }

        protected String handleBackground(String... params) {
//            String filePath = params[0];
//            String serverUrl = params[1];
//            int isScale = Integer.parseInt(params[2]);
            return uploadFileToServer(filePath, Const.UpLoadImage, isScale, seq);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            if (result.startsWith("Error")) {
//                countUploadImage = 0;
//            } else {
            if (result.startsWith("Error") || result.startsWith("error")) {
                progressbar.setVisibility(View.GONE);
                Log.e(TAG, "upload image fail");
                if (MainActivity.checkNewOrEditShop == 0) {
                    if (customDialogTryAgain != null) {
                        if (customDialogTryAgain.isShowing()) {

                        } else {
                            tryAgainNewShop();
                        }
                    } else {
                        tryAgainNewShop();
                    }
                } else {
                    if (dialogEdit != null) {
                        if (dialogEdit.isShowing()) {

                        } else {
                            tryAgainEditShop();
                        }
                    } else {
                        tryAgainEditShop();
                    }
                }
            } else {
                Log.d(TAG, "filePath:" + filePath);
                String URL = filePath;
                File file = new File(URL);
                if (file.exists()) {
                    file.delete();
                }

                countUploadImage++;
                Log.e(TAG, "result:" + result);
                result = result.substring(1, result.length() - 1);
                if (listPicture.get(0).length() > 0 && listPicture.get(1).length() > 0) {
                    listPictureSend.set(seq - 1, Const.serverUrl + result);
                    if (countUploadImage == 2) {
                        countUploadImage = 0;
                        initDB117();
                        sendAPI(dataLogin);
                    }
                } else {
                    listPictureSend.set(seq - 1, Const.serverUrl + result);
                    if (countUploadImage == 1) {
                        countUploadImage = 0;
                        initDB117();
                        sendAPI(dataLogin);
                    }
                }
            }
        }
    }

    void Edit() {
        if (edHouseNo.getText().toString().trim().length() == 0
                || edShopName.getText().toString().trim().length() == 0
                || singleComplete.getText().toString().trim().length() == 0
                || LON == 0
                || LAT == 0) {
            MyApplication.getInstance().showToast("You must input full information and GPS");
        } else {
            customDialog = new CustomDialog(mActivity, getResources().getString(R.string.adddata), new OnClickDialog() {
                @Override
                public void btnOK() {
                    progressbar.setVisibility(View.VISIBLE);
                    customDialog.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (listPicture.get(0).trim().length() == 0 && listPicture.get(1).trim().length() == 0) {
                                sendAPI(dataLogin);

                            } else {
                                for (int i = 0; i < listPicture.size(); i++) {
                                    if (listPicture.get(i).trim().length() > 0) {
                                        String s = listPicture.get(i);
                                        Log.d(TAG, s);
                                        int seq = i + 1;
                                        new UploadFileToServer(s, seq).execute();
                                    }

                                }
                            }
                        }
                    }, 1000);

                }

                @Override
                public void btnCancel() {
                    customDialog.dismiss();
                }
            });
            customDialog.show();
        }
    }

    void editAndNew() {
        if (edHouseNo.getText().toString().trim().length() == 0
                || edMobile.getText().toString().trim().length() == 0
                || edShopName.getText().toString().trim().length() == 0
                || edOwner.getText().toString().trim().length() == 0
                || singleComplete.getText().toString().trim().length() == 0
                || LON == 0
                || LAT == 0) {
            MyApplication.getInstance().showToast("You must input full information and GPS");
        } else {
            customDialog = new CustomDialog(mActivity, getResources().getString(R.string.adddata), new OnClickDialog() {
                @Override
                public void btnOK() {
                    progressbar.setVisibility(View.VISIBLE);
                    customDialog.dismiss();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (listPicture.get(0).trim().length() == 0 && listPicture.get(1).trim().length() == 0) {
                                sendAPI(dataLogin);
                            } else {
                                for (int i = 0; i < listPicture.size(); i++) {
                                    if (listPicture.get(i).trim().length() > 0) {
                                        String s = listPicture.get(i);
                                        Log.d(TAG, s);
                                        int seq = i + 1;
                                        new UploadFileToServer(s, seq).execute();
                                    }

                                }
                            }
                        }
                    }, 1000);
//                            sendAPI(dataLogin);

                }

                @Override
                public void btnCancel() {
                    customDialog.dismiss();
                }
            });
            customDialog.show();
        }
    }

    void Trade() {
        customDialog = new CustomDialog(mActivity, getResources().getString(R.string.adddata), new OnClickDialog() {
            @Override
            public void btnOK() {
//                progressbar.setVisibility(View.VISIBLE);
                customDialog.dismiss();
                sendAPI(dataLogin);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 1000);

            }

            @Override
            public void btnCancel() {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

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
                                            StrAD = ad;
                                            ADDR1 = ADD_1;
                                            ADDR2 = ADD_2;
                                            ADDR3 = ADD_3;
                                            updateSpinner(ad);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), "Can not get ADMINCODE from API", Toast.LENGTH_SHORT).show();
                                ADD_1 = "";
                                ADD_2 = "";
                                ADD_3 = "";
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Can not get ADMINCODE from API", Toast.LENGTH_SHORT).show();
                            ADD_1 = "";
                            ADD_2 = "";
                            ADD_3 = "";
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Can not get ADMINCODE from API", Toast.LENGTH_SHORT).show();
                        // handle error response
                        ADD_1 = "";
                        ADD_2 = "";
                        ADD_3 = "";
                    }
                }
        );
        MyApplication.getInstance().addToRequestQueue(request);
    }


}