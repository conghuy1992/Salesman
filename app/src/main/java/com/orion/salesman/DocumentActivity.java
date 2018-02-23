package com.orion.salesman;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman._class.Const;
import com.orion.salesman._infor._fragment._object.OneNew;
import com.orion.salesman._pref.PrefManager;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 19/10/2016.
 */
public class DocumentActivity extends AppCompatActivity {
    String TAG = "DocumentActivity";
    OneNew oneNew;
    TextView tvTitle, tvContent;
    WebView wv;
    ImageView onBackShop;
    LinearLayout lnTitle;
    TextView TV1, TV2, TV3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.document_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.color_bar));
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        wv = (WebView) findViewById(R.id.wv);
        TV1 = (TextView) findViewById(R.id.TV1);
        TV2 = (TextView) findViewById(R.id.TV2);
        TV3 = (TextView) findViewById(R.id.TV3);
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        lnTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);
        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        onBackShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        oneNew = new Gson().fromJson(getIntent().getStringExtra(Const.OneNew), OneNew.class);


        PrefManager pref = new PrefManager(DocumentActivity.this);
        pref.setUPDATE_NEWS(true);
        List<String> listIdNews = new ArrayList<>();
        String data = pref.getID_NEWS();
        if (data.length() > 0) {
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            listIdNews = new Gson().fromJson(data, listType);
            listIdNews.add(oneNew.getV3().trim());
        } else {
            listIdNews.add(oneNew.getV3().trim());
        }
        if (listIdNews.size() > 0) {
            String jsonString = new Gson().toJson(listIdNews);
            pref.setID_NEWS(jsonString);
        }

        TV3.setText("Write: " + oneNew.getV11());
        String date = oneNew.getV9();
        if (date.length() == 8) {
            date = Const.formatDate(date);
        }
        TV2.setText("Date: " + date);
        TV1.setText("Category: " + getKind(oneNew.getV1()));
        tvTitle.setText(oneNew.getV4());
//        Const.longInfo(TAG, oneNew.getV5());

//        String stringFromBase = new String(Base64.decode(oneNew.getV5(), Base64.DEFAULT));

        String link = oneNew.getV5().trim().replaceAll("\\\\\"", "");

        wv.loadData(link, "text/html; charset=UTF-8", null);


    }


    String getKind(String S) {
        String kind = getResources().getString(R.string.News);
        if (S.equals("01"))
            kind = getResources().getString(R.string.News);
        else if (S.equals("02"))
            kind = getResources().getString(R.string.promotion);
        else if (S.equals("03"))
            kind = getResources().getString(R.string.Contest);
        else if (S.equals("04"))
            kind = getResources().getString(R.string.Display);
        else if (S.equals("05"))
            kind = getResources().getString(R.string.Product);
        return kind;
    }
}
