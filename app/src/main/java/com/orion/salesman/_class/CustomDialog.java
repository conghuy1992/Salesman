package com.orion.salesman._class;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._interface.OnClickDialog;

/**
 * Created by maidinh on 9/9/2016.
 */
public class CustomDialog extends Dialog {
    public OnClickDialog callback;
    public Button btnOK, btnCancel;
    public String msg;

    public CustomDialog(Context context, String msg, OnClickDialog callback) {
        super(context);
        this.msg = msg;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.custom_dialog);
        TextView tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvMsg.setText(msg);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.btnOK();
            }
        });
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.btnCancel();
            }
        });
    }
}
