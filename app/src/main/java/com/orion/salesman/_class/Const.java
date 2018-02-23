package com.orion.salesman._class;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._interface.IF_GET_IP;
import com.orion.salesman._object.API_100;
import com.orion.salesman._object.API_103;
import com.orion.salesman._object.API_104;
import com.orion.salesman._object.API_110;
import com.orion.salesman._object.API_112;
import com.orion.salesman._object.API_130;
import com.orion.salesman._object.API_131;
import com.orion.salesman._object.API_303;
import com.orion.salesman._object.NewShopPromotionList;
import com.orion.salesman._object.OrderBuying;
import com.orion.salesman._object.PMPRODUCTLIST;
import com.orion.salesman._offline.OfflineObject;
import com.orion.salesman._result._object.RouteShopOffline;
import com.orion.salesman._route._object.Snack;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by maidinh on 3/8/2016.
 */
public class Const {

    public static String TAG = "Const";
    public static int sumDownload = 22;
    public static final String rootURL = "http://snsv.orionvina.vn/dataservice.asmx";
    public static final String ORION_FAMILY = "/CpnInfo_ListT";

    //    public static final String serverUrl = "http://oriontest.dazone.co.kr";
    public static final String serverUrl = "http://snsv.orionvina.vn";
    public static final String FileUpload = "/FileUpload.aspx";
    public static final String PDA_SHOP_IMAGE = "PDA_SHOP_IMAGE";
    public static final String PDA_ORDER_IMAGE = "PDA_ORDER_IMAGE";


    public static final String UpLoadImage = serverUrl + FileUpload;
    public static final String IMAGE_JPG = ".jpg";
    public static final String DATE_FORMAT_PICTURE = "yyyyMMdd_HHmmss";

    public static void setBackground0(View v) {
        v.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public static void setBackground1(View v) {
        v.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    public static String MAPS_HTTP = "http://snsv.orionvina.vn/orionmap/mobile/mapinfo.html";
    public static String keyEncrypt = "keyEncrypt";
    public static String SHOP_NAME = "SHOP_NAME";
    public static String SHOP_ADDRESS = "SHOP_ADDRESS";
    public static int TIME_OUT_UPLOAD_IMAGE = 30000;
    public static int REQUEST_CODE = 1;
    public static int REQUEST_CODE_INFO = 2;
    public static int REQUEST_IMAGE = 3;
    public static int REQUEST_IMAGE_2 = 4;
    public static int REQUEST_NEW_SHOP = 5;
    public static int REQUEST_UPDATE_SHOP = 6;
    public static int REQUEST_CONFIRM_SHOP = 7;
    public static int GPS_WGS84 = 360000;
    public static String VI = "vi";
    public static String EN = "en";
    public static String CUSTCD = "CUSTCD";
    public static String SHOP_INFO_DETAIL = "SHOP_INFO_DETAIL";
    public static String CHA_WKDAY = "";


    public static String STRING_AD = "STRING_AD";
    public static String STRING_LON = "STRING_LON";
    public static String STRING_LAT = "STRING_LAT";


    public static String OK = "OK";
    public static String FAIL = "FAIL";
    public static String MSG = "MSG";
    public static String posMsg = "posMsg";
    public static String inforSE = "";
    public static String smName = "";

    public static String CHECK_KIND_GOTO_MAP = "CHECK_KIND_GOTO_MAP";//0:gps button in route; 1:GPS button in newshop,edit,...
    public static String FROM_POSITION = "FROM_POSITION";
    public static String UPDATE_POSITION = "UPDATE_POSITION";
    public static String UPDATE_ADMINCODE = "UPDATE_ADMINCODE";
    public static String DS = "DS";
    public static String DATA_LOGIN = "DATA_LOGIN";

    public static String compareStringOne = "compareStringOne";
    public static String compareStringTwo = "compareStringTwo";

    public static String DOMAIN = "app.orionvina.vn";
    public static String IP = "210.211.119.74";
    //    public static String IP = "";
    public static int PORT = 23001;
    public static String SE = "SE";
    public static String GPSINTERVAL = "GPSINTERVAL";
    public static String SENDINTERVAL = "SENDINTERVAL";
    public static String OneNew = "OneNew";
    public static String NO_INTERNET = "NO_INTERNET";
    public static String S1 = "huyencrazy2015@gmail.com";
    public static String S2 = "themakodoanra1";
    public static String S3 = "langtumotthoi1992@gmail.com";
    public static String PHONENUMBER = "0000000";
    public static String LISTPOSITION = "LISTPOSITION";
    public static int timeReload = 60000;
    public static int A0008 = 8;
    public static int A0140 = 140;
    public static int A0129 = 129;
    public static int A0128 = 128;
    public static int A0127 = 127;
    public static int A0007 = 7;
    public static int A0006 = 6;
    public static int A0005 = 5;
    public static int A0004 = 4;
    public static int A0003 = 3;
    public static int API_2 = 9;
    public static int Login = 1;
    public static int SendPosition = 1000;
    public static int SummarySales = 100;
    public static int A0133 = 133;
    public static int SummarySalesShop = 101;
    public static int SummaryDelivery = 104;
    public static int SummaryDisplay = 103;
    public static int Route = 110;
    public static int getRouteScheduleShop = 111;
    public static int getRouteShop = 112;
    public static int DownloadList = 10;
    public static int GetRouteShopStock = 113;
    public static int GetSMWorkDay = 114;
    public static int VisitShop = 115;
    public static int shopLocaltionInput = 116;
    public static int NewShop = 117;
    public static int StockInput = 118;
    public static int DisplayInput = 119;
    public static int GetOrderNonShopList = 120;
    public static int Get_Promotion_List = 121;
    public static int GetNonShopSKUPromotionList = 122;
    public static int GetNewShopPromotionList = 123;
    public static int InputOrder = 124;
    public static int InputHistoryOrder = 125;
    public static int ResetPromotionHistory = 126;
    public static int API_130 = 130;
    public static int API_1311 = 131;
    public static int API_132 = 132;
    public static int API_303 = 303;
    public static int API_304 = 304;
    public static String POSITION_IMAGE = "POSITION_IMAGE";
    public static String SEND_URL = "SEND_URL";
    public static String SEND_URL_2 = "SEND_URL_2";
    public static String INFOR_SHOP = "INFOR_SHOP";
    public static String HUY = "HUY";
    public static String NAME_ROUTE = "NAME_ROUTE";
    public static String KEY_ROUTE = "KEY_ROUTE";
    public static String DATABASE_SQL = "PDACOM.DB";
    public static String DATABASE_SQL_2 = "PDACOM2.DB";
    public static String TABLE_ADDRESS_M = "ADDRESS_M";
    public static String LIST_INFO_SHOP = "LIST_INFO_SHOP";
    public static String LIST_DETAILS = "LIST_DETAILS";
    public static String SEQ = "SEQ";
    public static String RESULT = "RESULT";
    public static String ROW_0 = "#ffffff";
    public static String ROW_1 = "#ffffff";
    public static String ROW_SUM = "#e94545";
    public static String ROW_SUM_TEXT = "#fff000";
    public static final String PATH_IMG = "/storage/emulated/0/";
    public static String NO_PIC = "nopic";
    public static String SUMMARY_1 = "SUMMARY_1";
    public static String SUMMARY_2 = "SUMMARY_2";
    public static String SUMMARY_3 = "SUMMARY_3";
    public static String SUMMARY_4 = "SUMMARY_4";
    public static String PIE_TEAM = "01";
    public static String SNACK_TEAM = "02";
    public static String GUM_TEAM = "03";
    public static String MIX_TEAM = "04";
    public static String PTEAM = "PTEAM";
    public static String STEAM = "STEAM";
    public static String GTEAM = "GTEAM";
    public static String EA = "EA";
//    public static String Cs = "Cs";
//    public static String Bx = "Bx";

    public static String Box = "Box";
    public static String CASE = "CASE";
//    public static String EA = "EA";

    public static int setStatusProgressbar(int pos, int Sum) {
        return pos * 100 / Sum;
    }

    public static void setBackgroundButtonRed(Button v) {
        v.setBackgroundResource(R.drawable.bg_small_button_red);
    }

    public static void setBackgroundButtonGray(Button v) {
        v.setBackgroundResource(R.drawable.bg_small_button_gray);
    }

    public static void setBackGroundChild(TextView v) {
        v.setBackgroundResource(R.drawable.border_dot);
    }

    public static void setBackGroundChild_2(View v) {
        v.setBackgroundResource(R.drawable.border_dot_2);
    }

    public static void setBackGroundTextOne(TextView v) {
        v.setBackgroundColor(Color.parseColor("#e6b9b8"));
    }

    public static void setColorYellow(TextView v) {
        v.setBackgroundColor(Color.parseColor("#ccff66"));
    }

    public static void setColorRowChild(TextView tv) {
        tv.setBackgroundColor(Color.parseColor("#ebf5fc"));
    }

    public static void setTextBlack(TextView v) {
        v.setTextColor(Color.BLACK);
    }

    public static void setBold(TextView v) {
        v.setTypeface(null, Typeface.BOLD);
    }

    public static void setColorSum(TextView tv) {
        tv.setBackgroundColor(Color.parseColor(ROW_SUM));
    }

    public static void setTextColorSum(TextView tv) {
        tv.setTextColor(Color.parseColor(ROW_SUM_TEXT));
    }

    public static void setColor_0(View tv) {
        tv.setBackgroundColor(Color.parseColor(ROW_0));
    }

    public static void setTextSize(Context c, int id, TextView tv) {
        tv.setTextSize(MyApplication.getInstance().getDimens(c, id));
    }

    //    public static void setBackgroundChild(TextView tv) {
//        tv.setBackgroundResource(R.drawable.border_dot);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tv.getLayoutParams();
//        params.setMargins(0,MyApplication.getInstance().getDimens(),0,0);
//        tv.setLayoutParams(params);
//    }
    public static void setGravityRight(TextView v) {
        v.setGravity(Gravity.CENTER | Gravity.RIGHT);
    }

    public static void setColor_1(TextView tv) {
        tv.setBackgroundColor(Color.parseColor(ROW_1));
    }

    public static void longInfo(String TAG, String str) {
        if (str.length() > 4000) {
            Log.d(TAG, str.substring(0, 4000));
            longInfo(TAG, str.substring(4000));
        } else
            Log.d(TAG, str);
    }

    public static String formatFullDate(int date) {
        String s = "";
        if (date < 10)
            s = "0" + date;
        else s = "" + date;
        return s;
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getWKDAY() {
        String s = "";
        Calendar calendar = Calendar.getInstance();
        int DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
        if (DAY_OF_WEEK == 1)
            s = "SUN";
        else if (DAY_OF_WEEK == 2)
            s = "MON";
        else if (DAY_OF_WEEK == 3)
            s = "TUE";
        else if (DAY_OF_WEEK == 4)
            s = "WED";
        else if (DAY_OF_WEEK == 5)
            s = "THU";
        else if (DAY_OF_WEEK == 6)
            s = "FRI";
        else
            s = "SAT";
        return s;
    }

    public static String RoundOneNumber(String str) {
        String s = "";
        double kilobytes = Double.parseDouble(str);
        double newKB = Math.round(kilobytes * 10.0) / 10.0;
        s = "" + newKB;
        return s;
    }

    public static String getPartInt(String n) {
        String s = "";
        double a = Double.parseDouble(n);
        s = "" + Math.round(a);
        return s;
    }

    public static String formatAMT(long value) {
        String pattern = "###,###";
        String s = "";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        s = myFormatter.format(value) + "";
        s = s.replace(".", ",");
        return s;
    }

    public static String formatAMTFloat(float value) {
        String s = "";
        String pattern = "###,###.##";
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        s = myFormatter.format(value) + "";
        return s;
    }

    public static String RoundNumber(String str) {
        String s = "";
        try {
            double kilobytes = Double.parseDouble(str);
            double newKB = Math.round(kilobytes * 10.0) / 10.0;
            s = "" + newKB;
        } catch (Exception e) {
            s = str;
            e.printStackTrace();
        }
        return s;
    }

    public static String setFullDate(int n) {
        String s = "";
        if (n < 10) s = "0" + n;
        else s = "" + n;
        return s;
    }

    public static boolean checkDate(List<OfflineObject> arr, String s) {
        // true : data off
        // false: data online
        for (OfflineObject p : arr) {
            if (p.getDATE().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public static boolean checkDateRouteShopOffline(List<RouteShopOffline> arr, String s, String w) {
        // true : data off
        // false: data online
        for (RouteShopOffline p : arr) {
            if (p.getDATE().equalsIgnoreCase(s) && p.getWEEK().equals(w))
                return true;
        }
        return false;
    }

    public static boolean API_110_contains(List<API_110> arr, String s, String w) {
        // true : data off
        // false: data online
        for (API_110 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s) && p.getKEY_ROUTE().equals(w))
                return true;
        }
        return false;
    }

    public static boolean API_112_contains(List<API_112> arr, String s, String w) {
        // true : data off
        // false: data online
        for (API_112 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s) && p.getWEEK().equals(w))
                return true;
        }
        return false;
    }

    public static boolean API_130_contains(List<API_130> arr, String s) {
        // true : data off
        // false: data online
        for (API_130 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public static boolean API_131_contains(List<API_131> arr, String s) {
        // true : data off
        // false: data online
        for (API_131 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public static boolean API_100_contains(List<API_100> arr, String s) {
        // true : data off
        // false: data online
        for (API_100 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public static boolean API_104_contains(List<API_104> arr, String s) {
        // true : data off
        // false: data online
        for (API_104 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public static boolean API_303_contains(List<com.orion.salesman._object.API_303> arr, String s) {
        // true : data off
        // false: data online
        for (API_303 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public static String getToday(int day) {
        String s = "";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        s = year + Const.setFullDate(month) + Const.setFullDate(date);
        return s;
    }

    public static boolean API_103_contains(List<API_103> arr, String s) {
        // true : data off
        // false: data online
        for (API_103 p : arr) {
            if (p.getDATE().equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public static String formatDate(String day) {
        String s = "";
        try {
            s = day.substring(0, 4) + "-" + day.substring(4, 6) + "-" + day.substring(6, 8);
        } catch (Exception e) {
            s = day;
            e.printStackTrace();
        }
        return s;
    }

    public static String formatHour(String day) {
        String s = "";
        try {
            s = day.substring(0, 2) + ":" + day.substring(2, 4) + ":" + day.substring(4, 6);
        } catch (Exception e) {
            s = day;
            e.printStackTrace();
        }
        return s;
    }

    public static void fixMargin(View v) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        v.setLayoutParams(layoutParams);
    }

    public static void fixMarginFrame(View v) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        v.setLayoutParams(layoutParams);
    }

    public static Bitmap createScaleBitmap(String pathFile, int reqSize) {
        try {
            // Get the dimensions of the View
            int targetW = reqSize;
            int targetH = reqSize;

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathFile, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

            Bitmap bmResult = BitmapFactory.decodeFile(pathFile, bmOptions);
            ExifInterface exifReader = new ExifInterface(pathFile);
            int exifOrientation = exifReader.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            int exifToDegrees = exifToDegrees(exifOrientation);

            return bitMapRotate(exifToDegrees, bmResult);
        } catch (Exception e) {
            return null;
        }
    }

    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public static Bitmap bitMapRotate(int exifToDegrees, Bitmap bmPhotoResult) {
        //Create object of new Matrix.
        Matrix matrix = new Matrix();
        //set image rotation value to 90 degrees in matrix.
        matrix.postRotate(exifToDegrees);
//        matrix.postScale(0.5f, 0.5f);
        int newWidth = bmPhotoResult.getWidth();
        int newHeight = bmPhotoResult.getHeight();
        //Create bitmap with new values.
        Bitmap bMapRotate = Bitmap.createBitmap(bmPhotoResult, 0, 0, newWidth, newHeight, matrix, true);
        return bMapRotate;
    }

    public static boolean checkContainSnack(List<Snack> arList, String s) {
        for (Snack p : arList) {
            if (s.equals(p.getColumnsOne()))
                return true;
        }
        return false;
    }

    public static boolean getDBNewShop(List<NewShopPromotionList> LIST, String CUSTCD) {
        if (LIST != null && LIST.size() > 0) {
            for (int i = 0; i < LIST.size(); i++) {
                if (CUSTCD.equalsIgnoreCase(LIST.get(i).getV1()))
                    return true;
            }
        }
        return false;
    }

    public static boolean checkListString(List<String> arrStrings, String s) {
        for (int i = 0; i < arrStrings.size(); i++) {
            if (arrStrings.get(i).equals(s))
                return true;
        }
        return false;
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    public static long getDistanceBetweenTwoPoints(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        final int RADIUS_EARTH = 6371;

        double dLat = getRad(latitude2 - latitude1);
        double dLong = getRad(longitude2 - longitude1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(getRad(latitude1)) * Math.cos(getRad(latitude2)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (long) ((RADIUS_EARTH * c) * 1000);
    }

    public static Double getRad(Double x) {
        return x * Math.PI / 180;
    }

    public static String inputFormat = "HH:mm";
    public static SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);

    public static Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

    public static void StyleTextBuyGift(final Context context, final TextView tv, int kind) {
        // kind : 0 -> BUY
        // kind : 1 -> GIFT
        tv.setTextColor(context.getResources().getColor(R.color.black));
        if (kind == 0)
            tv.setBackgroundColor(context.getResources().getColor(R.color.backgroundbuy));
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.post(new Runnable() {
            @Override
            public void run() {
                if (tv.getLineCount() == 1) {
                    tv.setHeight((int) context.getResources().getDimension(R.dimen.tv_1_line));
                }
            }
        });
    }

    public static boolean checkContains(List<String> list, String str) {
        if (list.size() > 0) {
            for (String s : list) {
                if (s.equals(str))
                    return true;
            }
            return false;
        } else {
            return false;
        }
    }


    public static String nowTime() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        String time = year + ":" + Const.setFullDate(month) + ":" + Const.setFullDate(date) + ":" + Const.setFullDate(hour) + ":" + Const.setFullDate(minute) + ":" + Const.setFullDate(second);
        return time;
    }

    public static String folder = "/SMLogFile";

    public static void WriteFileLog(int UserId, int API, String json) {
        String strData = "ID: " + UserId + " [" + Const.nowTime() + "]" + " API:" + API + " JSON:" + json;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + folder;
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File yourFile = new File(dir, Const.getToday() + ".txt");
        try {
            FileWriter fw = new FileWriter(yourFile, true); //the true will append the new data
            fw.write("\n" + strData);//appends the string to the file
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public static void WriteFileLog_2(int UserId, int API, String json, String timer) {
        String strData = "Time Send" + timer + " ID: " + UserId + " [" + Const.nowTime() + "]" + " API:" + API + " JSON:" + json;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + folder;
        File dir = new File(file_path);
        if (!dir.exists())
            dir.mkdirs();
        File yourFile = new File(dir, Const.getToday() + ".txt");
        try {
            FileWriter fw = new FileWriter(yourFile, true); //the true will append the new data
            fw.write("\n" + strData);//appends the string to the file
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    public static String getToday() {
        String TODAY = "";
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        TODAY = year + Const.formatFullDate(month) + Const.formatFullDate(date);
        return TODAY;
    }

    public final static long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;

    public static long calculateDeltaInDays(Calendar a, Calendar b) {

        // Optional: avoid cloning objects if it is the same day
        if (a.get(Calendar.ERA) == b.get(Calendar.ERA)
                && a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
                && a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR)) {
            return 0;
        }
        Calendar a2 = (Calendar) a.clone();
        Calendar b2 = (Calendar) b.clone();
        a2.set(Calendar.HOUR_OF_DAY, 0);
        a2.set(Calendar.MINUTE, 0);
        a2.set(Calendar.SECOND, 0);
        a2.set(Calendar.MILLISECOND, 0);
        b2.set(Calendar.HOUR_OF_DAY, 0);
        b2.set(Calendar.MINUTE, 0);
        b2.set(Calendar.SECOND, 0);
        b2.set(Calendar.MILLISECOND, 0);
        long diff = a2.getTimeInMillis() - b2.getTimeInMillis();
        long days = diff / MILLISECS_PER_DAY;
        return Math.abs(days);
    }

    public static String getNowTime() {
        Calendar now = Calendar.getInstance();
        int HOUR_OF_DAY_INTERVAL = now.get(Calendar.HOUR_OF_DAY);
        int MINUTE_INTERVAL = now.get(Calendar.MINUTE);
        int SECOND_INTERVAL = now.get(Calendar.SECOND);
        String TIME = formatFullDate(HOUR_OF_DAY_INTERVAL) + formatFullDate(MINUTE_INTERVAL) + formatFullDate(SECOND_INTERVAL);
        return TIME;
    }

    public static String InforDevice() {
        String reqString = Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
        return reqString;
    }

//    public static float GetBoxQty(int a_nOrderBox, int a_nOrderCase, int a_nOrderEA, int SAMPLE_BXEAQTY, int SAMPLE_BXCSEQTY) {
//        float Qty = a_nOrderBox;
//        Qty += (SAMPLE_BXEAQTY == 0 ? 0 : a_nOrderCase * 1.0 / SAMPLE_BXCSEQTY);
//        Qty += (SAMPLE_BXEAQTY == 0 ? 0 : a_nOrderEA * 1.0 / SAMPLE_BXEAQTY);
//        return Qty;
//    }

    public static float GetBoxQty(int a_nOrderBox, int a_nOrderCase, int a_nOrderEA, int nCaseBox, int nEABox) {
        float Qty = a_nOrderBox;
        Qty += (nCaseBox == 0 ? 0 : a_nOrderCase * 1.0 / nCaseBox);
        Qty += (nEABox == 0 ? 0 : a_nOrderEA * 1.0 / nEABox);
        return Qty;
    }

    public static Float routeTGT(String str) {
        float a = 0;
        try {
            a = (float) Math.round(Float.parseFloat(str) * 10) / 10;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }


    public static class getIpFromDomain extends AsyncTask<String, String, String> {

        IF_GET_IP callback;

        public getIpFromDomain(IF_GET_IP callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String ip = "";
            InetAddress address = null;
            try {
                address = InetAddress.getByName(new URL("http://" + Const.DOMAIN).getHost());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (address != null) {
                ip = address.getHostAddress();
            }
            return ip;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.onSuccess(s);

        }
    }


    public static String getPreMonth() {
        String s = "";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1); // 1 month pre
        int MONTH = cal.get(Calendar.MONTH) + 1;
        int YEAR = cal.get(Calendar.YEAR);
        s = YEAR + Const.formatFullDate(MONTH);
        return s;
    }

    public static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }


    public static List<PMPRODUCTLIST> SortListPM(List<PMPRODUCTLIST> LIST_PROMOTION) {

        /**
         * // KM 20170411
         // sort level
         for (int i = 0; i < LIST_PROMOTION.size(); i++) {
         for (int j = 0; j <= i; j++) {
         if (Float.parseFloat(LIST_PROMOTION.get(i).getV4()) < Float.parseFloat(LIST_PROMOTION.get(j).getV4())) {
         PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
         LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
         LIST_PROMOTION.set(j, pp);
         }
         }
         }

         // sort GIFTAMTPERCENT
         for (int i = 0; i < LIST_PROMOTION.size(); i++) {
         for (int j = 0; j <= i; j++) {
         if (Float.parseFloat(LIST_PROMOTION.get(i).getV17()) > Float.parseFloat(LIST_PROMOTION.get(j).getV17())
         && Float.parseFloat(LIST_PROMOTION.get(i).getV4()) == Float.parseFloat(LIST_PROMOTION.get(j).getV4())) {
         PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
         LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
         LIST_PROMOTION.set(j, pp);
         }
         }
         }

         // sort PRICE
         for (int i = 0; i < LIST_PROMOTION.size(); i++) {
         for (int j = 0; j <= i; j++) {
         if (LIST_PROMOTION.get(i).getPRICE() > LIST_PROMOTION.get(j).getPRICE()
         && Float.parseFloat(LIST_PROMOTION.get(i).getV17()) == Float.parseFloat(LIST_PROMOTION.get(j).getV17())) {
         PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
         LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
         LIST_PROMOTION.set(j, pp);
         }
         }
         }

         // sort id
         for (int i = 0; i < LIST_PROMOTION.size(); i++) {
         int ID_1 = Integer.parseInt(LIST_PROMOTION.get(i).getV1().split("-")[1]);
         for (int j = 0; j <= i; j++) {
         int ID_2 = Integer.parseInt(LIST_PROMOTION.get(j).getV1().split("-")[1]);
         if (ID_1 < ID_2
         && LIST_PROMOTION.get(i).getPRICE() == LIST_PROMOTION.get(j).getPRICE()) {
         PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
         LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
         LIST_PROMOTION.set(j, pp);
         }
         }
         }

         Log.d(TAG, "sort id");
         for (PMPRODUCTLIST obj : LIST_PROMOTION) {
         Log.d(TAG, obj.getV4() + "-" + obj.getV17() + "-" + obj.getPRICE() + "-" + obj.getV1());
         }
         */


        // sort level
        for (int i = 0; i < LIST_PROMOTION.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (Float.parseFloat(LIST_PROMOTION.get(i).getV4()) < Float.parseFloat(LIST_PROMOTION.get(j).getV4())) {
                    PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
                    LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
                    LIST_PROMOTION.set(j, pp);
                }
            }
        }

        // sort GIFTAMTPERCENT
        for (int i = 0; i < LIST_PROMOTION.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (Float.parseFloat(LIST_PROMOTION.get(i).getV17()) > Float.parseFloat(LIST_PROMOTION.get(j).getV17())
                        && Float.parseFloat(LIST_PROMOTION.get(i).getV4()) == Float.parseFloat(LIST_PROMOTION.get(j).getV4())) {
                    PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
                    LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
                    LIST_PROMOTION.set(j, pp);
                }
            }
        }

        // sort QTY
        for (int i = 0; i < LIST_PROMOTION.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (LIST_PROMOTION.get(i).getSumQty() > LIST_PROMOTION.get(j).getSumQty()
                        && Float.parseFloat(LIST_PROMOTION.get(i).getV17()) == Float.parseFloat(LIST_PROMOTION.get(j).getV17())) {
                    PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
                    LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
                    LIST_PROMOTION.set(j, pp);
                }
            }
        }


        // sort high gift
        for (int i = 0; i < LIST_PROMOTION.size(); i++) {
            for (int j = 0; j <= i; j++) {
                if (LIST_PROMOTION.get(i).getHightGift() > LIST_PROMOTION.get(j).getHightGift()
                        && LIST_PROMOTION.get(i).getSumQty() == LIST_PROMOTION.get(j).getSumQty()) {
                    PMPRODUCTLIST pp = LIST_PROMOTION.get(i);
                    LIST_PROMOTION.set(i, LIST_PROMOTION.get(j));
                    LIST_PROMOTION.set(j, pp);
                }
            }
        }


        for (PMPRODUCTLIST obj : LIST_PROMOTION) {
            Log.d(TAG, obj.getV1() + " - " + obj.getV4() + " - " + obj.getV17() + " - " + obj.getSumQty() + " - " + obj.getHightGift());
        }


        return LIST_PROMOTION;
    }
}
