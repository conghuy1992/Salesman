package com.orion.salesman._sqlite;

/**
 * Created by Huy on 11/9/2016.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.orion.salesman._object.A0004;
import com.orion.salesman._object.API_100;
import com.orion.salesman._object.API_103;
import com.orion.salesman._object.API_104;
import com.orion.salesman._object.API_110;
import com.orion.salesman._object.API_111;
import com.orion.salesman._object.API_112;
import com.orion.salesman._object.API_120;
import com.orion.salesman._object.API_121;
import com.orion.salesman._object.API_122;
import com.orion.salesman._object.API_123;
import com.orion.salesman._object.API_130;
import com.orion.salesman._object.API_131;
import com.orion.salesman._object.API_132;
import com.orion.salesman._object.API_303;
import com.orion.salesman._object.API_304;
import com.orion.salesman._object.DataBase128;
import com.orion.salesman._object.DataBase129;
import com.orion.salesman._object.RESENDNEWSHOP;
import com.orion.salesman._object.RESEND_115;
import com.orion.salesman._object.RESEND_116;
import com.orion.salesman._object.RESEND_125;
import com.orion.salesman._object.SaveInputData;
import com.orion.salesman._object.SendPosition;
import com.orion.salesman._object.TABLE_INPUT;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 15;

    // Database Name
    private static final String DATABASE_NAME = "DATA_OFFLINE";
    // Contacts table name
    private static final String TABLE_API_112 = "TABLE_API_112";
    // Contacts Table Columns names
    private static final String KEY_ID = "KEY_ID";
    private static final String KEY_DATE = "KEY_DATE";
    private static final String KEY_WEEK = "KEY_WEEK";
    private static final String KEY_DATA = "KEY_DATA";

    private static final String TABLE_API_132 = "TABLE_API_132";
    private static final String KEY_ID_132 = "KEY_ID_132";
    private static final String KEY_PRDCLS1_132 = "KEY_PRDCLS1_132";
    private static final String KEY_DATE_132 = "KEY_DATE_132";
    private static final String KEY_DATA_132 = "KEY_DATA_132";

    private static final String TABLE_API_131 = "TABLE_API_131";
    private static final String KEY_ID_131 = "KEY_ID_131";
    private static final String KEY_DATE_131 = "KEY_DATE_131";
    private static final String KEY_DATA_131 = "KEY_DATA_131";

    private static final String TABLE_API_130 = "TABLE_API_130";
    private static final String KEY_ID_130 = "KEY_ID_130";
    private static final String KEY_DATE_130 = "KEY_DATE_130";
    private static final String KEY_DATA_130 = "KEY_DATA_130";

    private static final String TABLE_API_123 = "TABLE_API_123";
    private static final String KEY_ID_123 = "KEY_ID_123";
    private static final String KEY_DATE_123 = "KEY_DATE_123";
    private static final String KEY_DATA_123 = "KEY_DATA_123";

    private static final String TABLE_API_122 = "TABLE_API_122";
    private static final String KEY_ID_122 = "KEY_ID_122";
    private static final String KEY_DATE_122 = "KEY_DATE_122";
    private static final String KEY_DATA_122 = "KEY_DATA_122";

    private static final String TABLE_API_121 = "TABLE_API_121";
    private static final String KEY_ID_121 = "KEY_ID_121";
    private static final String KEY_DATE_121 = "KEY_DATE_121";
    private static final String KEY_DATA_121 = "KEY_DATA_121";

    private static final String TABLE_API_120 = "TABLE_API_120";
    private static final String KEY_ID_120 = "KEY_ID_120";
    private static final String KEY_DATE_120 = "KEY_DATE_120";
    private static final String KEY_DATA_120 = "KEY_DATA_120";

    private static final String TABLE_API_110 = "TABLE_API_110";
    private static final String KEY_ID_110 = "KEY_ID_110";
    private static final String KEY_DATE_110 = "KEY_DATE_110";
    private static final String KEY_ROUTE_110 = "KEY_ROUTE_110";
    private static final String KEY_DATA_110 = "KEY_DATA_110";

    private static final String TABLE_API_111 = "TABLE_API_111";
    private static final String KEY_ID_111 = "KEY_ID_111";
    private static final String KEY_DATE_111 = "KEY_DATE_111";
    private static final String KEY_DATA_111 = "KEY_DATA_111";
    private static final String KEY_WKDAY_111 = "KEY_WKDAY_111";

    private static final String TABLE_API_100 = "TABLE_API_100";
    private static final String KEY_ID_100 = "KEY_ID_100";
    private static final String KEY_DATE_100 = "KEY_DATE_100";
    private static final String KEY_DATA_100 = "KEY_DATA_100";

    private static final String TABLE_API_104 = "TABLE_API_104";
    private static final String KEY_ID_104 = "KEY_ID_104";
    private static final String KEY_DATE_104 = "KEY_DATE_104";
    private static final String KEY_DATA_104 = "KEY_DATA_104";

    private static final String TABLE_API_103 = "TABLE_API_103";
    private static final String KEY_ID_103 = "KEY_ID_103";
    private static final String KEY_DATE_103 = "KEY_DATE_103";
    private static final String KEY_DATA_103 = "KEY_DATA_103";

    private static final String TABLE_API_304 = "TABLE_API_304";
    private static final String KEY_ID_304 = "KEY_ID_304";
    private static final String KEY_DATE_304 = "KEY_DATE_304";
    private static final String HIDEPTCD_304 = "HIDEPTCD_304";
    private static final String DEALERID_304 = "DEALERID_304";
    private static final String KEY_DATA_304 = "KEY_DATA_304";

    private static final String TABLE_API_303 = "TABLE_API_303";
    private static final String KEY_ID_303 = "KEY_ID_303";
    private static final String KEY_DATE_303 = "KEY_DATE_303";
    private static final String KEY_DATA_303 = "KEY_DATA_303";

    private static final String TABLE_INPUT_DATA = "TABLE_INPUT_DATA";
    private static final String TABLE_INPUT_KEY = "TABLE_INPUT_KEY";
    private static final String TABLE_INPUT_DATE = "TABLE_INPUT_DATE";
    private static final String TABLE_INPUT_SHOP_ID = "TABLE_INPUT_SHOP_ID";
    private static final String TABLE_INPUT_STOCK_PIE = "TABLE_INPUT_STOCK_PIE";
    private static final String TABLE_INPUT_STOCK_SNACK = "TABLE_INPUT_STOCK_SNACK";
    private static final String TABLE_INPUT_STOCK_GUM = "TABLE_INPUT_STOCK_GUM";
    private static final String TABLE_INPUT_DISPLAY_PIE = "TABLE_INPUT_DISPLAY_PIE";
    private static final String TABLE_INPUT_DISPLAY_SNACK = "TABLE_INPUT_DISPLAY_SNACK";
    private static final String TABLE_INPUT_ORDER_PIE = "TABLE_INPUT_ORDER_PIE";
    private static final String TABLE_INPUT_ORDER_SNACK = "TABLE_INPUT_ORDER_SNACK";
    private static final String TABLE_INPUT_ORDER_GUM = "TABLE_INPUT_ORDER_GUM";
    private static final String TABLE_INPUT_OMI = "TABLE_INPUT_OMI";
    private static final String TABLE_INPUT_ORDER = "TABLE_INPUT_ORDER";
    private static final String TABLE_INPUT_JSON_ORDER = "TABLE_INPUT_JSON_ORDER";

    private static final String TABLE_ORDER = "TABLE_ORDER";
    private static final String KEY_ORDER = "KEY_ORDER";
    private static final String KEY_DATE_ORDER = "KEY_DATE_ORDER";
    private static final String SHOPID = "SHOPID";
    private static final String STOCK_PIE = "STOCK_PIE";
    private static final String STOCK_SNACK = "STOCK_SNACK";
    private static final String STOCK_GUM = "STOCK_GUM";
    private static final String DISPLAY_PIE = "DISPLAY_PIE";
    private static final String DISPLAY_SNACK = "DISPLAY_SNACK";
    private static final String ORDER_PIE = "ORDER_PIE";
    private static final String ORDER_SNACK = "ORDER_SNACK";
    private static final String ORDER_GUM = "ORDER_GUM";
    private static final String URL_IMG = "URL_IMG";
    private static final String IS_124 = "IS_124"; // 0 ;not yet, 1-> saved
    private static final String COUNT_124 = "COUNT_124"; // ex: 4 product, update ok 3 product -> still 1 produtct :COUNT_UPDATE = 3; next time post to API product start 3 in list
    private static final String DATA_124 = "DATA_124";
    private static final String FILE_PATH_IMG = "FILE_PATH_IMG";
    private static final String IS_118 = "IS_118"; //0 : 1;
    private static final String DATA_118 = "DATA_118";
    private static final String IS_115 = "IS_115";
    private static final String DATA_115 = "DATA_115";
    private static final String IS_119 = "IS_119";
    private static final String DATA_119 = "DATA_119";

    private static final String TABLE_NEWSHOP = "TABLE_NEWSHOP";
    private static final String TABLE_NEWSHOP_ID = "TABLE_NEWSHOP_ID";
    private static final String TABLE_NEWSHOP_DATA = "TABLE_NEWSHOP_DATA";

    private static final String TABLE_116 = "TABLE_116";
    private static final String TABLE_ID_116 = "TABLE_ID_116";
    private static final String TABLE_DATA_116 = "TABLE_DATA_116";


    private static final String TABLE_A0004 = "TABLE_A0004";
    private static final String TABLE_ID_A0004 = "TABLE_ID_A0004";
    private static final String A0004_CDATE = "A0004_CDATE";
    private static final String A0004_CTIME = "A0004_CTIME";
    private static final String A0004_AUTHOR = "A0004_AUTHOR";
    private static final String A0004_AUTHORNM = "A0004_AUTHORNM";
    private static final String A0004_MSGBODY = "A0004_MSGBODY";
    private static final String A0004_ISREAD = "A0004_ISREAD";
    private static final String A0004_OUT_BOX = "A0004_OUT_BOX";
    private static final String A0004_DATE_ADD = "A0004_DATE_ADD";

    private static final String TABLE_115 = "TABLE_115";
    private static final String TABLE_ID_115 = "TABLE_ID_115";
    private static final String TABLE_DATA_115 = "TABLE_DATA_115";

    private static final String TABLE_125 = "TABLE_125";
    private static final String TABLE_ID_125 = "TABLE_ID_125";
    private static final String TABLE_DATA_125 = "TABLE_DATA_125";
    private static final String TABLE_SHOPID_125 = "TABLE_SHOPID_125";
    private static final String TABLE_DATE_125 = "TABLE_DATE_125";

    private static final String TABLE_128 = "TABLE_128";
    private static final String TABLE_ID_128 = "TABLE_ID_128";
    private static final String CUSTCD_128 = "CUSTCD_128";
    private static final String OMI_128 = "OMI_128";
    private static final String ORDERQTY_128 = "ORDERQTY_128";
    private static final String CDATE_128 = "CDATE_128";

    private static final String TABLE_129 = "TABLE_129";
    private static final String TABLE_ID_129 = "TABLE_ID_129";
    private static final String DATA_129 = "DATA_129";
    private static final String CDATE_129 = "CDATE_129";


    private static final String GPS_TABLE = "GPS_TABLE";
    private static final String GPS_ID = "GPS_ID";
    private static final String GPS_DATE = "GPS_DATE";
    private static final String GPS_TIME = "GPS_TIME";
    private static final String GPS_LON = "GPS_LON";
    private static final String GPS_LAT = "GPS_LAT";
    private static final String GPS_SPEED = "GPS_SPEED";
    private static final String GPS_ANGLE = "GPS_ANGLE";
    private static final String GPS_SEID = "GPS_SEID";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GPS_TABLE = "CREATE TABLE "
                + GPS_TABLE
                + "("
                + GPS_ID + " INTEGER PRIMARY KEY,"
                + GPS_DATE + " TEXT,"
                + GPS_TIME + " TEXT,"
                + GPS_LON + " TEXT,"
                + GPS_LAT + " TEXT,"
                + GPS_SPEED + " TEXT,"
                + GPS_ANGLE + " TEXT,"
                + GPS_SEID + " TEXT"
                + ")";

        String CREATE_TABLE_INPUT_DATA = "CREATE TABLE "
                + TABLE_INPUT_DATA
                + "("
                + TABLE_INPUT_KEY + " INTEGER PRIMARY KEY,"
                + TABLE_INPUT_DATE + " TEXT,"
                + TABLE_INPUT_SHOP_ID + " TEXT,"
                + TABLE_INPUT_STOCK_PIE + " TEXT,"
                + TABLE_INPUT_STOCK_SNACK + " TEXT,"
                + TABLE_INPUT_STOCK_GUM + " TEXT,"
                + TABLE_INPUT_DISPLAY_PIE + " TEXT,"
                + TABLE_INPUT_DISPLAY_SNACK + " TEXT,"
                + TABLE_INPUT_ORDER_PIE + " TEXT,"
                + TABLE_INPUT_ORDER_SNACK + " TEXT,"
                + TABLE_INPUT_ORDER_GUM + " TEXT,"
                + TABLE_INPUT_OMI + " TEXT,"
                + TABLE_INPUT_ORDER + " TEXT,"
                + TABLE_INPUT_JSON_ORDER + " TEXT"
                + ")";

        String CREATE_RESEND_125 = "CREATE TABLE "
                + TABLE_125
                + "("
                + TABLE_ID_125 + " INTEGER PRIMARY KEY,"
                + TABLE_DATA_125 + " TEXT,"
                + TABLE_SHOPID_125 + " TEXT,"
                + TABLE_DATE_125 + " TEXT"
                + ")";

        String CREATE_RESEND_115 = "CREATE TABLE "
                + TABLE_115
                + "("
                + TABLE_ID_115 + " INTEGER PRIMARY KEY,"
                + TABLE_DATA_115 + " TEXT"
                + ")";
        String CREATE_RESEND_116 = "CREATE TABLE "
                + TABLE_116
                + "("
                + TABLE_ID_116 + " INTEGER PRIMARY KEY,"
                + TABLE_DATA_116 + " TEXT"
                + ")";

        String CREATE_RESEND_NEWSHOP = "CREATE TABLE "
                + TABLE_NEWSHOP
                + "("
                + TABLE_NEWSHOP_ID + " INTEGER PRIMARY KEY,"
                + TABLE_NEWSHOP_DATA + " TEXT"
                + ")";

        String CREATE_ORDER_TABLE = "CREATE TABLE "
                + TABLE_ORDER
                + "("
                + KEY_ORDER + " INTEGER PRIMARY KEY,"
                + KEY_DATE_ORDER + " TEXT,"
                + SHOPID + " TEXT,"
                + STOCK_PIE + " TEXT,"
                + STOCK_SNACK + " TEXT,"
                + STOCK_GUM + " TEXT,"
                + DISPLAY_PIE + " TEXT,"
                + DISPLAY_SNACK + " TEXT,"
                + ORDER_PIE + " TEXT,"
                + ORDER_SNACK + " TEXT,"
                + ORDER_GUM + " TEXT,"
                + URL_IMG + " TEXT,"
                + IS_124 + " TEXT,"
                + COUNT_124 + " TEXT,"
                + DATA_124 + " TEXT,"
                + FILE_PATH_IMG + " TEXT,"
                + IS_118 + " TEXT,"
                + DATA_118 + " TEXT,"
                + IS_115 + " TEXT,"
                + DATA_115 + " TEXT,"
                + IS_119 + " TEXT,"
                + DATA_119 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + TABLE_API_112
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DATE + " TEXT,"
                + KEY_WEEK + " TEXT,"
                + KEY_DATA + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_110 = "CREATE TABLE "
                + TABLE_API_110
                + "("
                + KEY_ID_110 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_110 + " TEXT,"
                + KEY_ROUTE_110 + " TEXT,"
                + KEY_DATA_110 + " TEXT"
                + ")";
        String CREATE_CONTACTS_TABLE_111 = "CREATE TABLE "
                + TABLE_API_111
                + "("
                + KEY_ID_111 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_111 + " TEXT,"
                + KEY_DATA_111 + " TEXT,"
                + KEY_WKDAY_111 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_120 = "CREATE TABLE "
                + TABLE_API_120
                + "("
                + KEY_ID_120 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_120 + " TEXT,"
                + KEY_DATA_120 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_121 = "CREATE TABLE "
                + TABLE_API_121
                + "("
                + KEY_ID_121 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_121 + " TEXT,"
                + KEY_DATA_121 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_122 = "CREATE TABLE "
                + TABLE_API_122
                + "("
                + KEY_ID_122 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_122 + " TEXT,"
                + KEY_DATA_122 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_123 = "CREATE TABLE "
                + TABLE_API_123
                + "("
                + KEY_ID_123 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_123 + " TEXT,"
                + KEY_DATA_123 + " TEXT"
                + ")";
        String CREATE_CONTACTS_TABLE_130 = "CREATE TABLE "
                + TABLE_API_130
                + "("
                + KEY_ID_130 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_130 + " TEXT,"
                + KEY_DATA_130 + " TEXT"
                + ")";
        String CREATE_CONTACTS_TABLE_131 = "CREATE TABLE "
                + TABLE_API_131
                + "("
                + KEY_ID_131 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_131 + " TEXT,"
                + KEY_DATA_131 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_132 = "CREATE TABLE "
                + TABLE_API_132
                + "("
                + KEY_ID_132 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_132 + " TEXT,"
                + KEY_PRDCLS1_132 + " TEXT,"
                + KEY_DATA_132 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_100 = "CREATE TABLE "
                + TABLE_API_100
                + "("
                + KEY_ID_100 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_100 + " TEXT,"
                + KEY_DATA_100 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_104 = "CREATE TABLE "
                + TABLE_API_104
                + "("
                + KEY_ID_104 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_104 + " TEXT,"
                + KEY_DATA_104 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_103 = "CREATE TABLE "
                + TABLE_API_103
                + "("
                + KEY_ID_103 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_103 + " TEXT,"
                + KEY_DATA_103 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_304 = "CREATE TABLE "
                + TABLE_API_304
                + "("
                + KEY_ID_304 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_304 + " TEXT,"
                + HIDEPTCD_304 + " TEXT,"
                + DEALERID_304 + " TEXT,"
                + KEY_DATA_304 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_303 = "CREATE TABLE "
                + TABLE_API_303
                + "("
                + KEY_ID_303 + " INTEGER PRIMARY KEY,"
                + KEY_DATE_303 + " TEXT,"
                + KEY_DATA_303 + " TEXT"
                + ")";

        String CREATE_TABLE_A0004 = "CREATE TABLE "
                + TABLE_A0004
                + "("
                + TABLE_ID_A0004 + " INTEGER PRIMARY KEY,"
                + A0004_CDATE + " TEXT,"
                + A0004_CTIME + " TEXT,"
                + A0004_AUTHOR + " TEXT,"
                + A0004_AUTHORNM + " TEXT,"
                + A0004_MSGBODY + " TEXT,"
                + A0004_ISREAD + " TEXT,"
                + A0004_OUT_BOX + " TEXT,"
                + A0004_DATE_ADD + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_128 = "CREATE TABLE "
                + TABLE_128
                + "("
                + TABLE_ID_128 + " INTEGER PRIMARY KEY,"
                + CUSTCD_128 + " TEXT,"
                + OMI_128 + " TEXT,"
                + ORDERQTY_128 + " TEXT,"
                + CDATE_128 + " TEXT"
                + ")";

        String CREATE_CONTACTS_TABLE_129 = "CREATE TABLE "
                + TABLE_129
                + "("
                + TABLE_ID_129 + " INTEGER PRIMARY KEY,"
                + DATA_129 + " TEXT,"
                + CDATE_129 + " TEXT"
                + ")";


        db.execSQL(CREATE_GPS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE_129);
        db.execSQL(CREATE_CONTACTS_TABLE_128);
        db.execSQL(CREATE_TABLE_A0004);
        db.execSQL(CREATE_TABLE_INPUT_DATA);
        db.execSQL(CREATE_RESEND_125);
        db.execSQL(CREATE_RESEND_115);
        db.execSQL(CREATE_RESEND_116);
        db.execSQL(CREATE_RESEND_NEWSHOP);
        db.execSQL(CREATE_ORDER_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE_100);
        db.execSQL(CREATE_CONTACTS_TABLE_103);
        db.execSQL(CREATE_CONTACTS_TABLE_104);
        db.execSQL(CREATE_CONTACTS_TABLE_110);
        db.execSQL(CREATE_CONTACTS_TABLE_111);
        db.execSQL(CREATE_CONTACTS_TABLE_120);
        db.execSQL(CREATE_CONTACTS_TABLE_121);
        db.execSQL(CREATE_CONTACTS_TABLE_122);
        db.execSQL(CREATE_CONTACTS_TABLE_123);
        db.execSQL(CREATE_CONTACTS_TABLE_130);
        db.execSQL(CREATE_CONTACTS_TABLE_131);
        db.execSQL(CREATE_CONTACTS_TABLE_132);
        db.execSQL(CREATE_CONTACTS_TABLE_303);
        db.execSQL(CREATE_CONTACTS_TABLE_304);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + GPS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_129);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_128);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_A0004);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INPUT_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_125);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_115);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_116);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWSHOP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_100);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_103);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_104);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_110);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_111);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_112);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_120);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_121);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_122);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_123);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_130);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_131);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_132);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_303);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_API_304);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void addGPS(SendPosition obj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(GPS_DATE, obj.getDATE());
        values.put(GPS_TIME, obj.getTIME());
        values.put(GPS_LON, obj.getLON());
        values.put(GPS_LAT, obj.getLAT());
        values.put(GPS_SPEED, obj.getSPEED());
        values.put(GPS_ANGLE, obj.getANGLE());
        values.put(GPS_SEID, obj.getSEID());

        db.insert(GPS_TABLE, null, values);
        db.close(); // Closing database connection
    }

    public void ADD_INPUT_DATA(TABLE_INPUT contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TABLE_INPUT_DATE, contact.getTABLE_INPUT_DATE());
        values.put(TABLE_INPUT_SHOP_ID, contact.getTABLE_INPUT_SHOP_ID());
        values.put(TABLE_INPUT_STOCK_PIE, contact.getTABLE_INPUT_STOCK_PIE());
        values.put(TABLE_INPUT_STOCK_SNACK, contact.getTABLE_INPUT_STOCK_SNACK());
        values.put(TABLE_INPUT_STOCK_GUM, contact.getTABLE_INPUT_STOCK_GUM());
        values.put(TABLE_INPUT_DISPLAY_PIE, contact.getTABLE_INPUT_DISPLAY_PIE());
        values.put(TABLE_INPUT_DISPLAY_SNACK, contact.getTABLE_INPUT_DISPLAY_SNACK());
        values.put(TABLE_INPUT_ORDER_PIE, contact.getTABLE_INPUT_ORDER_PIE());
        values.put(TABLE_INPUT_ORDER_SNACK, contact.getTABLE_INPUT_ORDER_SNACK());
        values.put(TABLE_INPUT_ORDER_GUM, contact.getTABLE_INPUT_ORDER_GUM());
        values.put(TABLE_INPUT_OMI, contact.getTABLE_INPUT_OMI());
        values.put(TABLE_INPUT_ORDER, contact.getTABLE_INPUT_ORDER());
        values.put(TABLE_INPUT_JSON_ORDER, contact.getTABLE_INPUT_JSON_ORDER());

        db.insert(TABLE_INPUT_DATA, null, values);
        db.close(); // Closing database connection
    }

    public void ADD_TABLE_A0004(A0004 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(A0004_CDATE, contact.getCDATE());
        values.put(A0004_CTIME, contact.getCTIME());
        values.put(A0004_AUTHOR, contact.getAUTHOR());
        values.put(A0004_AUTHORNM, contact.getAUTHORNM());
        values.put(A0004_MSGBODY, contact.getMSGBODY());
        values.put(A0004_ISREAD, contact.getISREAD());
        values.put(A0004_OUT_BOX, contact.getOUTBOX());
        values.put(A0004_DATE_ADD, contact.getDATEADD());


        db.insert(TABLE_A0004, null, values);
        db.close(); // Closing database connection
    }

    public void add_116(RESEND_116 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_DATA_116, contact.getDATA());
        db.insert(TABLE_116, null, values);
        db.close(); // Closing database connection
    }

    public void add_129(DataBase129 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATA_129, contact.getDATA());
        values.put(CDATE_129, contact.getCDATE());
        db.insert(TABLE_129, null, values);
        db.close(); // Closing database connection
    }

    public void add_128(DataBase128 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTCD_128, contact.getCUSTCD_128());
        values.put(OMI_128, contact.getOMI_128());
        values.put(ORDERQTY_128, contact.getORDERQTY_128());
        values.put(CDATE_128, contact.getCDATE());
        db.insert(TABLE_128, null, values);
        db.close(); // Closing database connection
    }

    public void add_125(RESEND_125 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_DATA_125, contact.getDATA());
        values.put(TABLE_SHOPID_125, contact.getSHOPID());
        values.put(TABLE_DATE_125, contact.getDATE());
        db.insert(TABLE_125, null, values);
        db.close(); // Closing database connection
    }

    public void add_115(RESEND_115 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_DATA_115, contact.getDATA());
        db.insert(TABLE_115, null, values);
        db.close(); // Closing database connection
    }


    public void add_NEWSHOP(RESENDNEWSHOP contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_NEWSHOP_DATA, contact.getDATA());
        db.insert(TABLE_NEWSHOP, null, values);
        db.close(); // Closing database connection
    }

    public void addTABLEORDER(SaveInputData contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_ORDER, contact.getDATE());
        values.put(SHOPID, contact.getSHOPID());
        values.put(STOCK_PIE, contact.getSTOCK_PIE());
        values.put(STOCK_SNACK, contact.getSTOCK_SNACK());
        values.put(STOCK_GUM, contact.getSTOCK_GUM());
        values.put(DISPLAY_PIE, contact.getDISPLAY_PIE());
        values.put(DISPLAY_SNACK, contact.getDISPLAY_SNACK());
        values.put(ORDER_PIE, contact.getORDER_PIE());
        values.put(ORDER_SNACK, contact.getORDER_SNACK());
        values.put(ORDER_GUM, contact.getORDER_GUM());
        values.put(URL_IMG, contact.getURL_IMG());
        values.put(IS_124, contact.getIS_124());
        values.put(COUNT_124, contact.getCOUNT_124());
        values.put(DATA_124, contact.getDATA_124());
        values.put(FILE_PATH_IMG, contact.getFILE_PATH_IMG());
        values.put(IS_118, contact.getIS_118());
        values.put(DATA_118, contact.getDATA_118());
        values.put(IS_115, contact.getIS_115());
        values.put(DATA_115, contact.getDATA_115());
        values.put(IS_119, contact.getIS_119());
        values.put(DATA_119, contact.getDATA_119());
        db.insert(TABLE_ORDER, null, values);
        db.close(); // Closing database connection
    }

    public void addContact_303(API_303 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_303, contact.getDATE());
        values.put(KEY_DATA_303, contact.getDATA());
        db.insert(TABLE_API_303, null, values);
        db.close(); // Closing database connection
    }

    public void addContact_304(API_304 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_304, contact.getDATE());
        values.put(HIDEPTCD_304, contact.getHIDEPTCD());
        values.put(DEALERID_304, contact.getDEALERID());
        values.put(KEY_DATA_304, contact.getDATA());
        db.insert(TABLE_API_304, null, values);
        db.close(); // Closing database connection
    }

    public void addContact_103(API_103 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_103, contact.getDATE());
        values.put(KEY_DATA_103, contact.getDATA());
        db.insert(TABLE_API_103, null, values);
        db.close(); // Closing database connection
    }

    public void addContact_104(API_104 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_104, contact.getDATE());
        values.put(KEY_DATA_104, contact.getDATA());
        db.insert(TABLE_API_104, null, values);
        db.close(); // Closing database connection
    }

    public void addContact_100(API_100 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_100, contact.getDATE());
        values.put(KEY_DATA_100, contact.getDATA());
        db.insert(TABLE_API_100, null, values);
        db.close(); // Closing database connection
    }

    // Adding new contact
    public void addContact(API_112 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, contact.getDATE());
        values.put(KEY_WEEK, contact.getWEEK());
        values.put(KEY_DATA, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_112, null, values);
        Log.e("112", "add ok ");
        db.close(); // Closing database connection
    }

    // Adding new contact API_130
    public void addContact_130(API_130 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_130, contact.getDATE());
        values.put(KEY_DATA_130, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_130, null, values);
        db.close(); // Closing database connection
    }

    public void addContact_131(API_131 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_131, contact.getDATE());
        values.put(KEY_DATA_131, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_131, null, values);
        db.close(); // Closing database connection
    }

    // Adding new contact API_123
    public void addContact_123(API_123 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_123, contact.getDATE());
        values.put(KEY_DATA_123, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_123, null, values);
        db.close(); // Closing database connection
    }

    // Adding new contact API_122
    public void addContact_122(API_122 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_122, contact.getDATE());
        values.put(KEY_DATA_122, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_122, null, values);
        db.close(); // Closing database connection
    }

    // Adding new contact API_121
    public void addContact_121(API_121 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_121, contact.getDATE());
        values.put(KEY_DATA_121, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_121, null, values);
        db.close(); // Closing database connection
    }

    // Adding new contact API_120
    public void addContact_120(API_120 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_120, contact.getDATE());
        values.put(KEY_DATA_120, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_120, null, values);
        db.close(); // Closing database connection
    }

    // Adding new contact API_110
    public void addContact_110(API_110 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_110, contact.getDATE());
        values.put(KEY_ROUTE_110, contact.getKEY_ROUTE());
        values.put(KEY_DATA_110, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_110, null, values);
        db.close(); // Closing database connection
    }

    public void addContact_111(API_111 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_111, contact.getDATE());
        values.put(KEY_DATA_111, contact.getDATA());
        values.put(KEY_WKDAY_111, contact.getWKDAY());
        // Inserting Row
        db.insert(TABLE_API_111, null, values);
        db.close(); // Closing database connection
    }

    public void addContact_132(API_132 contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE_132, contact.getDATE());
        values.put(KEY_PRDCLS1_132, contact.getPRDCLS1());
        values.put(KEY_DATA_132, contact.getDATA());
        // Inserting Row
        db.insert(TABLE_API_132, null, values);
        db.close(); // Closing database connection
    }
    public List<SendPosition> getListGPS() {
        List<SendPosition> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + GPS_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SendPosition contact = new SendPosition();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setTIME(cursor.getString(2));
                contact.setLON(cursor.getString(3));
                contact.setLAT(cursor.getString(4));
                contact.setSPEED(cursor.getString(5));
                contact.setANGLE(cursor.getString(6));
                contact.setSEID(cursor.getString(7));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<SaveInputData> getListOrder() {
        List<SaveInputData> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SaveInputData contact = new SaveInputData();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setSHOPID(cursor.getString(2));
                contact.setSTOCK_PIE(cursor.getString(3));
                contact.setSTOCK_SNACK(cursor.getString(4));
                contact.setSTOCK_GUM(cursor.getString(5));
                contact.setDISPLAY_PIE(cursor.getString(6));
                contact.setDISPLAY_SNACK(cursor.getString(7));
                contact.setORDER_PIE(cursor.getString(8));
                contact.setORDER_SNACK(cursor.getString(9));
                contact.setORDER_GUM(cursor.getString(10));
                contact.setURL_IMG(cursor.getString(11));
                contact.setIS_124(cursor.getString(12));
                contact.setCOUNT_124(cursor.getString(13));
                contact.setDATA_124(cursor.getString(14));
                contact.setFILE_PATH_IMG(cursor.getString(15));
                contact.setIS_118(cursor.getString(16));
                contact.setDATA_118(cursor.getString(17));
                contact.setIS_115(cursor.getString(18));
                contact.setDATA_115(cursor.getString(19));
                contact.setIS_119(cursor.getString(20));
                contact.setDATA_119(cursor.getString(21));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<TABLE_INPUT> GET_LIST_INPUT() {
        List<TABLE_INPUT> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_INPUT_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TABLE_INPUT contact = new TABLE_INPUT();
                contact.setTABLE_INPUT_KEY(Integer.parseInt(cursor.getString(0)));
                contact.setTABLE_INPUT_DATE(cursor.getString(1));
                contact.setTABLE_INPUT_SHOP_ID(cursor.getString(2));
                contact.setTABLE_INPUT_STOCK_PIE(cursor.getString(3));
                contact.setTABLE_INPUT_STOCK_SNACK(cursor.getString(4));
                contact.setTABLE_INPUT_STOCK_GUM(cursor.getString(5));
                contact.setTABLE_INPUT_DISPLAY_PIE(cursor.getString(6));
                contact.setTABLE_INPUT_DISPLAY_SNACK(cursor.getString(7));
                contact.setTABLE_INPUT_ORDER_PIE(cursor.getString(8));
                contact.setTABLE_INPUT_ORDER_SNACK(cursor.getString(9));
                contact.setTABLE_INPUT_ORDER_GUM(cursor.getString(10));
                contact.setTABLE_INPUT_OMI(cursor.getString(11));
                contact.setTABLE_INPUT_ORDER(cursor.getString(12));
                contact.setTABLE_INPUT_JSON_ORDER(cursor.getString(13));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<DataBase129> getList129() {
        List<DataBase129> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_129;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DataBase129 contact = new DataBase129();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATA(cursor.getString(1));
                contact.setCDATE(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<DataBase128> getList128() {
        List<DataBase128> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_128;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DataBase128 contact = new DataBase128();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setCUSTCD_128(cursor.getString(1));
                contact.setOMI_128(cursor.getString(2));
                contact.setORDERQTY_128(cursor.getString(3));
                contact.setCDATE(cursor.getString(4));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<RESEND_125> getList125() {
        List<RESEND_125> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_125;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RESEND_125 contact = new RESEND_125();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setDATA(cursor.getString(1));
                contact.setSHOPID(cursor.getString(2));
                contact.setDATE(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<RESEND_115> getList115() {
        List<RESEND_115> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_115;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RESEND_115 contact = new RESEND_115();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setDATA(cursor.getString(1));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<A0004> getTABLE_A0004() {
        List<A0004> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_A0004;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                A0004 contact = new A0004();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setCDATE(cursor.getString(1));
                contact.setCTIME(cursor.getString(2));
                contact.setAUTHOR(cursor.getString(3));
                contact.setAUTHORNM(cursor.getString(4));
                contact.setMSGBODY(cursor.getString(5));
                contact.setISREAD(cursor.getString(6));
                contact.setOUTBOX(cursor.getString(7));
                contact.setDATEADD(cursor.getString(8));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<RESEND_116> getList116() {
        List<RESEND_116> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_116;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RESEND_116 contact = new RESEND_116();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setDATA(cursor.getString(1));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<RESENDNEWSHOP> getLISTNEWSHOP() {
        List<RESENDNEWSHOP> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NEWSHOP;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RESENDNEWSHOP contact = new RESENDNEWSHOP();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setDATA(cursor.getString(1));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_303> getListAPI_303() {
        List<API_303> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_303;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_303 contact = new API_303();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_304> getListAPI_304() {
        List<API_304> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_304;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_304 contact = new API_304();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setHIDEPTCD(cursor.getString(2));
                contact.setDEALERID(cursor.getString(3));
                contact.setDATA(cursor.getString(4));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_103> getListAPI_103() {
        List<API_103> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_103;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_103 contact = new API_103();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_104> getListAPI_104() {
        List<API_104> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_104;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_104 contact = new API_104();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_110> getListAPI_110() {
        List<API_110> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_110;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_110 contact = new API_110();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setKEY_ROUTE(cursor.getString(2));
                contact.setDATA(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_111> getListAPI_111() {
        List<API_111> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_111;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_111 contact = new API_111();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contact.setWKDAY(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    // Getting All Contacts
    public List<API_112> getAllContacts() {
        List<API_112> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_112;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_112 contact = new API_112();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setWEEK(cursor.getString(2));
                contact.setDATA(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    // Getting All API_123
    public List<API_123> getListAPI_123() {
        List<API_123> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_123;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_123 contact = new API_123();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_122> getListAPI_122() {
        List<API_122> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_122;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_122 contact = new API_122();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_121> getListAPI_121() {
        List<API_121> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_121;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_121 contact = new API_121();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_120> getListAPI_120() {
        List<API_120> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_120;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_120 contact = new API_120();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_130> getListAPI_130() {
        List<API_130> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_130;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_130 contact = new API_130();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_131> getListAPI_131() {
        List<API_131> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_131;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_131 contact = new API_131();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_132> getListAPI_132() {
        List<API_132> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_132;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_132 contact = new API_132();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setPRDCLS1(cursor.getString(2));
                contact.setDATA(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<API_100> getListAPI_100() {
        List<API_100> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_API_100;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                API_100 contact = new API_100();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setDATE(cursor.getString(1));
                contact.setDATA(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    // Updating single contact
    public void updateIsRead(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(A0004_ISREAD, data);
        db.update(TABLE_A0004, values, TABLE_ID_A0004 + "=" + id, null);
    }


    public void update_128_Order(String omi, String order, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OMI_128, omi);
        values.put(ORDERQTY_128, order);
        db.update(TABLE_128, values, TABLE_ID_128 + "=" + id, null);
    }

    public void updateContact(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATA, data);
        db.update(TABLE_API_112, values, KEY_ID + "=" + id, null);
    }

    public void update_110(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATA_110, data);
        db.update(TABLE_API_110, values, KEY_ID_110 + "=" + id, null);
    }

    public void delete_110(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_110, KEY_ID_110 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DELETE_129_ID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_129, TABLE_ID_129 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DELETE_128_ID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_128, TABLE_ID_128 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DELETE_125_ID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_125, TABLE_ID_125 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DELETE_115_ID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_115, TABLE_ID_115 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DELETE_116_ID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_116, TABLE_ID_116 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DELETE_117_ID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWSHOP, TABLE_NEWSHOP_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_112, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // delete all record
    public void delete_All_API_112() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_112);
        db.close();
    }

    public void delete_All_API_100() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_100);
        db.close();
    }

    public void deleteGPS() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + GPS_TABLE);
        db.close();
    }
    public void DELETE_ALL_TABLE_INPUT_DATA() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_INPUT_DATA);
        db.close();
    }

    public void delete_All_API_103() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_103);
        db.close();
    }

    public void delete_All_API_104() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_104);
        db.close();
    }

    public void delete_All_API_110() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_110);
        db.close();
    }

    public void delete_All_API_111() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_111);
        db.close();
    }

    public void delete_All_API_120() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_120);
        db.close();
    }

    public void delete_All_API_121() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_121);
        db.close();
    }

    public void delete_All_API_122() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_122);
        db.close();
    }

    public void delete_All_API_123() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_123);
        db.close();
    }

    public void delete_All_API_130() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_130);
        db.close();
    }

    public void delete_All_API_131() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_131);
        db.close();
    }

    public void delete_All_API_132() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_132);
        db.close();
    }

    public void delete_All_API_304() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_304);
        db.close();
    }

    public void delete_All_API_303() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_API_303);
        db.close();
    }

    public void DELETE_129_ALL() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_129);
        db.close();
    }

    public void DELETE_128_ALL() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_128);
        db.close();
    }

    public void DELETE_125_ALL() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_125);
        db.close();
    }

    public void DELETE_115_ALL() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_115);
        db.close();
    }

    public void DELETE_TABLE_A0004_ROW(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_A0004, TABLE_ID_A0004 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DELETE_TABLE_A0004() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_A0004);
        db.close();
    }

    public void DELETE_116_ALL() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_116);
        db.close();
    }

    public void DELETE_117_ALL() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NEWSHOP);
        db.close();
    }

    public void delete_All_ORDER() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ORDER);
        db.close();
    }

    public void delete_API_121(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_121, KEY_ID_121 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_API_100(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_100, KEY_ID_100 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_API_110(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_110, KEY_ID_110 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_API_103(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_103, KEY_ID_103 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_API_104(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_104, KEY_ID_104 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_API_130(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_130, KEY_ID_130 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_API_131(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_131, KEY_ID_131 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_API_303(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_303, KEY_ID_303 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void delete_API_123(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_API_123, KEY_ID_123 + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }


    public void DELETE_TABLE_INPUT_DATA(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INPUT_DATA, TABLE_INPUT_KEY + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public void DELETE_TABLE_ORDER(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER, KEY_ORDER + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
    public void GPS_DELETE_ID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(GPS_TABLE, GPS_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }



    public void updateOrderIMG(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(URL_IMG, data);
        db.update(TABLE_ORDER, values, KEY_ORDER + "=" + id, null);
    }



    public void updateOrder124(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IS_124, data);
        db.update(TABLE_ORDER, values, KEY_ORDER + "=" + id, null);
    }


    public void updateOrder115(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IS_115, data);
        db.update(TABLE_ORDER, values, KEY_ORDER + "=" + id, null);
    }

    public void updateOrder118(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IS_118, data);
        db.update(TABLE_ORDER, values, KEY_ORDER + "=" + id, null);
    }

    public void updateOrder119(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IS_119, data);
        db.update(TABLE_ORDER, values, KEY_ORDER + "=" + id, null);
    }

    public void updateOrderCount(String data, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COUNT_124, data);
        db.update(TABLE_ORDER, values, KEY_ORDER + "=" + id, null);
    }

}