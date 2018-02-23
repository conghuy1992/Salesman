package com.orion.salesman._receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._sqlite.DatabaseHandler;

/**
 * Created by maidinh on 18/10/2016.
 */
public class LogoutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new PrefManager(context).setLogout(false);
        DatabaseHandler db = new DatabaseHandler(context);
        db.deleteGPS();
        Log.d("LogoutReceiver","Logout");
    }
}