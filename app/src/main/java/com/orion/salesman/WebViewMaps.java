package com.orion.salesman;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.orion.salesman._class.Const;

public class WebViewMaps extends Activity {

    WebView wv = null;
    LinearLayout lnTitle;
    ImageView onBackShop;
    ProgressBar progressbar;
    String LISTPOSITION = "";
    String TAG = "WebViewMaps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        LISTPOSITION = getIntent().getStringExtra(Const.LISTPOSITION);
//        Log.d(TAG, "LISTPOSITION:" + LISTPOSITION);
        if (!Const.checkInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.cannotconnect), Toast.LENGTH_SHORT).show();
        }
        wv = (WebView) findViewById(R.id.wv);
        lnTitle = (LinearLayout) findViewById(R.id.lnTitle);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        lnTitle.setPadding(MainActivity.widthTabLayout, 0, 0, 0);
        onBackShop = (ImageView) findViewById(R.id.onBackShop);
        onBackShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        wv.clearHistory();
//        wv.clearCache(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setSupportZoom(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setDisplayZoomControls(false);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setAllowContentAccess(true);
        wv.getSettings().setAppCacheEnabled(true);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wv.getSettings().setAllowFileAccessFromFileURLs(true);
        wv.getSettings().setAllowFileAccess(true);
        wv.getSettings().setAllowContentAccess(true);

        /******Add Code : WebView Size Fit************/
        wv.setWebContentsDebuggingEnabled(true);
        wv.getSettings().setLoadWithOverviewMode(true);
        wv.getSettings().setUseWideViewPort(true);
        /********************************************/

        /***************** Edit Code : Load Url **********************/
        wv.loadUrl(Const.MAPS_HTTP);

        /*************************************************************/
        wv.addJavascriptInterface(new JsObject(this), "Android");

        wv.setWebViewClient(new WebViewExtend());

        /***************************** Call Function : DrawRoute *********************************************************/
            /*wv.loadUrl("javascript:DrawRoute([{lon:106.658546, lat:10.820476, shop:\"1\", name:\"shop1\"}," +
                    "        {lon:106.668556, lat:10.850496, shop:\"1\", name:\"shop2\"}," +
                    "        {lon:106.683566, lat:10.845496, shop:\"1\", name:\"shop2\"}," +
                    "        {lon:106.688576, lat:10.830456, shop:\"1\", name:\"shop2\"}," +
                    "        {lon:106.698586, lat:10.860516, shop:\"1\", name:\"shop2\"}]);");*/

        /***************************** Call Function : MarkerAdd ********************************************************/
        //wv.loadUrl("javascript:MarkerAdd([{lon:106.658546, lat:10.820476, shop:\"1\", name:\"shop1\"}]");

    }

    private class WebViewExtend extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            progressbar.setVisibility(View.GONE);
            // 페이지 로딩시 호출된다.
            super.onPageFinished(view, url);

            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }


//            view.loadUrl("javascript:DrawRoute([{ lon: 106.658546, lat: 10.820476, shop: \"1\", name: \"shop1\" },\n" +
//                    "\t\t\t   { lon: 106.668556, lat: 10.850496, shop: \"2\", name: \"shop2\" },\n" +
//                    "\t\t\t   { lon: 106.688576, lat: 10.830456, shop: \"4\", name: \"shop4\" },\n" +
//                    "\t\t\t   { lon: 106.678576, lat: 10.830456, shop: \"4\", name: \"shop5\" },\n" +
//                    "\t\t\t   { lon: 106.678576, lat: 10.800456, shop: \"3\", name: \"shop6\" },\n" +
//                    "\t\t\t   { lon: 106.688576, lat: 10.810456, shop: \"2\", name: \"shop7\" },\n" +
//                    "\t\t\t   { lon: 106.698576, lat: 10.8150456, shop: \"1\", name: \"shop8\" },\n" +
//                    "\t\t\t   { lon: 106.688576, lat: 10.850456, shop: \"1\", name: \"shop9\" },\n" +
//                    "\t\t\t   { lon: 106.698586, lat: 10.860516, shop: \"1\", name: \"shop10\"}]);");
            view.loadUrl("javascript:DrawRoute(" + LISTPOSITION + ");");
        }
    }

    public class JsObject {
        private Context context = null;

        public JsObject(Context context) {
            this.context = context;

        }

        @JavascriptInterface
        public void toastShort(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}
