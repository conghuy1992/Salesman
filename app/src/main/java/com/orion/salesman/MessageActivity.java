package com.orion.salesman;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._infor._fragment._fragment.fragment_three;
import com.orion.salesman._infor._fragment._object.ObjA0004;
import com.orion.salesman._interface.IF_2;
import com.orion.salesman._object.A0004;
import com.orion.salesman._object.RESULTOBJECT;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._sqlite.DatabaseHandler;

/**
 * Created by maidinh on 28/10/2016.
 */
public class MessageActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "MessageActivity";
    ImageView onBackShop;
    LinearLayout lnTitle;
    TextView tvTime, tvName, tvContents;
    Button btnSend;
    ObjA0004 objA0004;
    EditText edMsg;
    int position = 0;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        prefManager = new PrefManager(getApplicationContext());
        position = getIntent().getIntExtra(Const.posMsg, 0);
        String data = getIntent().getStringExtra(Const.MSG);

        objA0004 = new Gson().fromJson(data, ObjA0004.class);

        if (fragment_three.POSITION != 1) {
            if (!objA0004.getIsRead().equals("1")) {
                prefManager.setUpdateListMsg(true);
                prefManager.setUpdateAtPosition(position);
            }

        } else {

        }


        tvTime = (TextView) findViewById(R.id.tvTime);
        tvName = (TextView) findViewById(R.id.tvName);
        edMsg = (EditText) findViewById(R.id.edMsg);
        tvContents = (TextView) findViewById(R.id.tvContents);
        String date = objA0004.getV1();
        String time = objA0004.getV2();
        if (date.length() == 8) {
            date = Const.formatDate(date);
        }
        if (time.length() == 6) {
            time = Const.formatHour(time);
        }
        tvTime.setText(date + "  " + time);
        tvName.setText(objA0004.getV4());
        tvContents.setText(objA0004.getV5());
        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        onBackShop.setOnClickListener(this);
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        lnTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);
        new HttpRequest(getApplicationContext()).new API_5(MainActivity.dataLogin, objA0004.getV1(), objA0004.getV2(), objA0004.getV3(), new IF_2() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "response:" + s);
            }
        }).execute();
    }

    @Override
    public void onClick(View view) {
        if (view == onBackShop) {
            finish();
        } else if (view == btnSend) {
            A0006();
        }
    }

    void A0006() {
        if (!Const.checkInternetConnection(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
        } else {
            final String msg = edMsg.getText().toString().trim();
            if (msg.length() > 0) {
                btnSend.setEnabled(false);
                new HttpRequest(this).new API_6(MainActivity.dataLogin, objA0004.getV3(), msg, "", "", new IF_2() {
                    @Override
                    public void onSuccess(String s) {
                        Log.d(TAG, "onSuccess:" + s);
                        if (s != null && s.length() > 0) {
                            RESULTOBJECT ob = new Gson().fromJson(s, RESULTOBJECT.class);
                            if (ob.getRESULT() == 0) {
                                Toast.makeText(getApplicationContext(), "send msg ok", Toast.LENGTH_SHORT).show();
                                edMsg.setText("");
                                btnSend.setEnabled(true);

                                DatabaseHandler db = new DatabaseHandler(MessageActivity.this);
                                A0004 a0004 = new A0004(objA0004.getV1(), objA0004.getV2(), objA0004.getV3(), objA0004.getV4(), msg, "1", "1", Const.getToday());
                                db.ADD_TABLE_A0004(a0004);
                                new PrefManager(getApplicationContext()).setOutBox(true);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), "send msg fail", Toast.LENGTH_SHORT).show();
                                btnSend.setEnabled(true);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "send msg fail", Toast.LENGTH_SHORT).show();
                            btnSend.setEnabled(true);
                        }
                    }
                }).execute();
            } else {
                Toast.makeText(getApplicationContext(), "You must input message", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
