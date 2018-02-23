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
import com.orion.salesman._object.CodeH;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 22/8/2016.
 */
public class DataBaseCodeH extends SQLiteOpenHelper {
    String TAG = "DataBaseHelper";
    private Context context;
    private String DB_PATH;

    private static String DB_NAME = Const.DATABASE_SQL;
    private SQLiteDatabase db;
    private String TB_NAME = "CODE_H";

    public DataBaseCodeH(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        DB_PATH = context.getFilesDir().getPath() + "/";
    }

    public void openDB() {
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);
    }

    public List<CodeH> getData() {
        List<CodeH> contactList = new ArrayList<CodeH>();
        String selectQuery = "SELECT * FROM " + TB_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CodeH contact = new CodeH();
                contact.setIDX(cursor.getString(0));
                contact.setCODEGROUP(cursor.getString(1));
                contact.setCODEKEY(cursor.getString(2));
                contact.setCODEDESC(cursor.getString(3));
                contact.setCODEVALUE(cursor.getString(4));
                contact.setCODEVALUE2(cursor.getString(5));
                contact.setCODEVALUE3(cursor.getString(6));
                contact.setCODEVALUE4(cursor.getString(7));
                contact.setCODEVALUE5(cursor.getString(8));
                contact.setCODEVALUE6(cursor.getString(9));
                contact.setGUBUN(cursor.getString(10));
                contact.setBIGO(cursor.getString(11));
                contact.setPLANTCD(cursor.getString(12));
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
