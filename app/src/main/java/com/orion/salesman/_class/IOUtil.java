package com.orion.salesman._class;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.orion.salesman.LoginActivity;
import com.orion.salesman._interface.DownloadDB;
import com.orion.salesman._pref.PrefManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * Created by Dat on 8/15/2016.
 */
public class IOUtil {
    public static String TAG = "IOUtil";

    public static void saveFile(Context context, String fileName, byte[] response, String time, int i, DownloadDB callback) {
        try {

//            String root = Environment.getExternalStorageDirectory().toString();
//            File myDir = new File(root);
//            myDir.mkdirs();
//            File file = new File(myDir, fileName);
//            FileOutputStream outputStream = new FileOutputStream(file);

            FileOutputStream outputStream;
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            outputStream.write(response);
            outputStream.close();
//            Toast.makeText(context, "Download complete.", Toast.LENGTH_LONG).show();
            new PrefManager(context).setTimeSaveDb(time, i);
            Log.d(TAG, "Download complete");
            LoginActivity.check++;
            if (LoginActivity.check > 1) {
                callback.onSuccess();
            }
        } catch (Exception e) {
            Log.e("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
            e.printStackTrace();
        }
    }

    public static byte[] readFile(String file) throws IOException {
        return readFile(new File(file));
    }

    public static byte[] readFile(File file) throws IOException {
        // Open file
        RandomAccessFile f = new RandomAccessFile(file, "r");
        try {
            // Get and check length
            long longlength = f.length();
            int length = (int) longlength;
            if (length != longlength)
                throw new IOException("File size >= 2 GB");
            // Read file and return data
            byte[] data = new byte[length];
            f.readFully(data);
            return data;
        } finally {
            f.close();
        }
    }
}
