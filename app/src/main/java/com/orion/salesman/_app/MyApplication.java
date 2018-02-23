package com.orion.salesman._app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.orion.salesman.BuildConfig;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._class.GMailSender;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._fragment.ResultSalesFragment;
import com.orion.salesman._result._fragment.ResultStockFragment;
import com.orion.salesman._result._fragment.RouteNonShopFragment;
import com.orion.salesman._result._fragment.RouteSalesShopFragment;
import com.orion.salesman._summary._fragment.DeliveryFragment;
import com.orion.salesman._summary._fragment.DisplayFragment;
import com.orion.salesman._summary._fragment.SalesReportFragment;
import com.orion.salesman._summary._fragment.SalesShopFragment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by maidinh on 3/8/2016.
 */
public class MyApplication extends Application {
    private String TAG = "MyApplication";
    public static MyApplication instance = null;
    private RequestQueue mRequestQueue;
    private Thread.UncaughtExceptionHandler androidDefaultUEH;

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
//    String getToday(int day) {
//        String s = "";
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, day);
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH) + 1;
//        int date = cal.get(Calendar.DATE);
//        s = year + Const.setFullDate(month) + Const.setFullDate(date);
//        return s;
//    }

    public void update(int pos, String time) {
        String s = new PrefManager(this).getInfoLogin();
        DataLogin dataLogin = new Gson().fromJson(s, DataLogin.class);
        if (pos == 0) {
            SalesReportFragment.instance.send_100(dataLogin, time);
            SalesReportFragment.instance.reload();
        } else if (pos == 1) {
            SalesShopFragment.instance.send(time);
            SalesShopFragment.instance.reload();
        } else if (pos == 2) {
            DisplayFragment.instance.send(dataLogin, time);
            DisplayFragment.instance.reload();
        } else if (pos == 3) {
            DeliveryFragment.instance.send(dataLogin, time);
            DeliveryFragment.instance.reload();
        }
//        else if (pos == 5) {
//            ResultSalesFragment.instance.send(time);
//        } else if (pos == 6) {
//            RouteSalesShopFragment.instance.send(time);
//        } else if (pos == 7) {
//            RouteNonShopFragment.instance.send(time);
//        } else if (pos == 8) {
//            ResultStockFragment.instance.send_303(time);
//        }
    }

    public void showDialogDate(final Context context, final TextView tv, final int position) {
        String YESTERDAY = "";
        String TODAY = "";
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int date = cal.get(Calendar.DATE);
        //Sunday = 1, Monday = 2,...

        if (dayOfWeek == 2) {
            YESTERDAY = Const.getToday(-3);
            TODAY = Const.getToday(-2);
        } else if (dayOfWeek == 3) {
            YESTERDAY = Const.getToday(-3);
            TODAY = Const.getToday(-1);
        } else {
            YESTERDAY = Const.getToday(-2);
            TODAY = Const.getToday(-1);
        }
//        Log.e(TAG, "dayOfWeek:" + dayOfWeek);
        final String finalYESTERDAY = YESTERDAY;
        final String finalTODAY = TODAY;
//        Log.d(TAG,"YESTERDAY:"+YESTERDAY);
//        Log.d(TAG,"TODAY:"+TODAY);

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setTitleColor(Color.RED);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_date);
        dialog.setCanceledOnTouchOutside(false);

        TextView tvMonth1 = (TextView) dialog.findViewById(R.id.tvMonth1);
        TextView tvMonth2 = (TextView) dialog.findViewById(R.id.tvMonth2);
        tvMonth1.setText("" + YESTERDAY.substring(0, 4) + "-" + YESTERDAY.substring(4, 6));
        tvMonth2.setText("" + TODAY.substring(0, 4) + "-" + TODAY.substring(4, 6));
        TextView tvpreDate = (TextView) dialog.findViewById(R.id.tvpreDate);
        tvpreDate.setText("" + YESTERDAY.substring(6, 8));
        TextView tvToday = (TextView) dialog.findViewById(R.id.tvToday);
        tvToday.setText("" + TODAY.substring(6, 8));
        TextView tvClose = (TextView) dialog.findViewById(R.id.tvClose);
        LinearLayout lnyesterday = (LinearLayout) dialog.findViewById(R.id.lnyesterday);
        LinearLayout lntoday = (LinearLayout) dialog.findViewById(R.id.lntoday);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        lnyesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!MainActivity.DATE_SYNC.equals(finalYESTERDAY)) {
                    MainActivity.DATE_SYNC = finalYESTERDAY;
                    tv.setText(Const.formatDate(MainActivity.DATE_SYNC));
                    update(position, MainActivity.DATE_SYNC);
                }
            }
        });
        lntoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!MainActivity.DATE_SYNC.equals(finalTODAY)) {
                    MainActivity.DATE_SYNC = finalTODAY;
                    tv.setText(Const.formatDate(MainActivity.DATE_SYNC));
                    update(position, MainActivity.DATE_SYNC);
                }
            }
        });
        FrameLayout frCalendar = (FrameLayout) dialog.findViewById(R.id.frCalendar);
        frCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ShowDateDialog(context, tv, position);
            }
        });
        dialog.show();
    }

    public void ShowDateDialog(Context context, final TextView tv, final int position) {
        try {
            MainActivity.YEAR = Integer.parseInt(MainActivity.DATE_SYNC.substring(0, 4));
            MainActivity.MONTH = Integer.parseInt(MainActivity.DATE_SYNC.substring(4, 6));
            MainActivity.DAYOFMONTH = Integer.parseInt(MainActivity.DATE_SYNC.substring(6, 8));
        } catch (Exception e) {
            Calendar calendar = Calendar.getInstance();
            MainActivity.YEAR = calendar.get(Calendar.YEAR);
            MainActivity.MONTH = calendar.get(Calendar.MONTH) + 1;
            MainActivity.DAYOFMONTH = calendar.get(Calendar.DATE);
            e.printStackTrace();
        }

        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int y, int monthOfYear, int dayOfMonth) {
                MainActivity.YEAR = y;
                MainActivity.MONTH = monthOfYear + 1;
                MainActivity.DAYOFMONTH = dayOfMonth;
                tv.setText(MainActivity.YEAR + "-" + Const.setFullDate(MainActivity.MONTH) + "-" + Const.setFullDate(MainActivity.DAYOFMONTH));
                MainActivity.DATE_SYNC = MainActivity.YEAR + Const.setFullDate(MainActivity.MONTH) + Const.setFullDate(MainActivity.DAYOFMONTH);
                Log.d(TAG, MainActivity.DATE_SYNC);
                update(position, MainActivity.DATE_SYNC);
            }
        }, MainActivity.YEAR, MainActivity.MONTH - 1, MainActivity.DAYOFMONTH);
        dpd.show();
    }

    public int getDimens(Context context, int id) {
        return (int) context.getResources().getDimension(id);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        androidDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, final Throwable ex) {
//            Log.e(TAG, "Uncaught exception is: " + ex);
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            ex.printStackTrace(pw);
//            final String BUG = sw.toString();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        GMailSender sender = new GMailSender(Const.S1, Const.S2);
//                        sender.sendMail("Bug from SalesMan App: SM ID:" + MainActivity.dataLogin.getUSERID() + " pass:" + MainActivity.dataLogin.getPASSWORD()
//                                        + " ver:" + BuildConfig.VERSION_NAME + " InforDevice" + Const.InforDevice(),
//                                BUG,
//                                Const.S1,
//                                Const.S3);
//                    } catch (Exception e) {
//                        Log.e("SendMail", e.getMessage(), e);
//                    }
//                }
//            }).start();



//            Const.WriteFileLog(MainActivity.dataLogin.getUSERID(),0, BUG);
//            System.exit(0);

            androidDefaultUEH.uncaughtException(thread, ex);

        }
    };

    public void setChooseTab(String TEAM, CustomViewPager viewPager, TabLayout tabLayout) {
        int n = Integer.parseInt(TEAM) - 1;
        if (!TEAM.equals(Const.MIX_TEAM))
            viewPager.setCurrentItem(n);
        viewPager.setPagingEnabled(false);
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        if (TEAM.equalsIgnoreCase(Const.MIX_TEAM)) {
        } else {
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                if (n == 0) {
                    if (i == 1) {
                        tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                } else {
                    if (i != n) {
                        tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                return true;
                            }
                        });
                    }
                }

            }
        }

    }

    public void showToast(String title) {
        Toast.makeText(getApplicationContext(), title, Toast.LENGTH_SHORT).show();
    }

    public void CustomDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
//    public void shortcut(int badgeCount) {
//        ShortcutBadger.applyCount(this, badgeCount);
//    }
}
