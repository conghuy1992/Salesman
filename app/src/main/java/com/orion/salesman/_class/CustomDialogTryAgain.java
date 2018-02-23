package com.orion.salesman._class;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._interface.OnClickDialog;

/**
 * Created by maidinh on 12/10/2016.
 */
public class CustomDialogTryAgain extends Dialog {
    public OnClickDialog callback;
    public Button btnOK, btnCancel;
    public String msg;
    public String msgOK;
    public String msgCancel;

    public CustomDialogTryAgain(Context context, String msg, String msgOK, String msgCancel, OnClickDialog callback) {
        super(context);
        this.msg = msg;
        this.msgOK = msgOK;
        this.msgCancel = msgCancel;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);
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
