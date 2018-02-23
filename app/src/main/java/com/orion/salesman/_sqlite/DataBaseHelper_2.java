package com.orion.salesman._sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.orion.salesman._class.Const;
import com.orion.salesman._object.AddressSQLite;
import com.orion.salesman._object.AddressSQLiteStreets;
import com.orion.salesman._object.ProductInfor;
import com.orion.salesman._object.TeamDisplay;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 18/8/2016.
 */
public class DataBaseHelper_2 extends SQLiteOpenHelper {
    String TAG = "DataBaseHelper";
    private Context context;
    private String DB_PATH;
    private static String DB_NAME = Const.DATABASE_SQL_2;
    private SQLiteDatabase db;
    private String TB_NAME = "PRODUCT_B";
    private String TEAM_DISPLAY = "TEAM_DISPLAY";

    public DataBaseHelper_2(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = context.getFilesDir().getPath() + "/";
    }

    public void openDB() {
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    public List<TeamDisplay> getListDisplay() {
        List<TeamDisplay> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TEAM_DISPLAY;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TeamDisplay contact = new TeamDisplay();
                contact.setIDX(cursor.getString(0));
                contact.setTEAMCD(cursor.getString(1));
                contact.setTEAMNM(cursor.getString(2));
                contact.setTOOLCD(cursor.getString(3));
                contact.setTOOLNM(cursor.getString(4));
                contact.setBRAND(cursor.getString(5));
                contact.setCHECKTOOKQTY(cursor.getString(6));
                contact.setCHECKSHOPQTY(cursor.getString(7));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public List<ProductInfor> getListProduct() {
        List<ProductInfor> contactList = new ArrayList<>();
//        String selectQuery = "SELECT * FROM " + TB_NAME;
        String selectQuery = "SELECT * FROM PRODUCT_B ORDER BY PRDCLS1,TPRDCD ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ProductInfor contact = new ProductInfor();
                contact.setIDX(cursor.getString(0));
                contact.setTEAM(cursor.getString(1));
                contact.setPRDKIND(cursor.getString(2));
                contact.setPRDNM(cursor.getString(3));
                contact.setPRDCLS1(cursor.getString(4));
                contact.setBRANDNM(cursor.getString(5));
                contact.setTPRDCD(cursor.getString(6));
                contact.setTPRDDSC(cursor.getString(7));
                contact.setPRDCD(cursor.getString(8));
                contact.setPRDDSC(cursor.getString(9));
                contact.setCSEEAQTY(cursor.getString(10));
                contact.setBXCSEQTY(cursor.getString(11));
                contact.setBXEAQTY(cursor.getString(12));
                contact.setSTOREPRICE(cursor.getString(13));
                contact.setSTORECASEPRICE(cursor.getString(14));
                contact.setSTOREEAPRICE(cursor.getString(15));
                contact.setBOXSALESYN(cursor.getString(16));
                contact.setCASESALESYN(cursor.getString(17));
                contact.setEASALESYN(cursor.getString(18));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e(TAG, "database does't exist yet.");
        }
        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        Log.e(TAG, "start copyDataBase");
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
