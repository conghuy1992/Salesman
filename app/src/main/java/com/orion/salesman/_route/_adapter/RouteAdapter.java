package com.orion.salesman._route._adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.MapActivity;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman.ShopInfoActivity;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.AddressConverter;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomDialog;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._interface.IF_2;
import com.orion.salesman._interface.OnClickDialog;
import com.orion.salesman._object.AddressSQLite;
import com.orion.salesman._object.AddressSQLiteStreets;
import com.orion.salesman._object.CalculatorDistance;
import com.orion.salesman._object.DataLogin;
import com.orion.salesman._object.OrderNonShopList;
import com.orion.salesman._object.SaveInputData;
import com.orion.salesman._object.SavePositionLocaiton;
import com.orion.salesman._object.VisitShop;
import com.orion.salesman._pref.PrefManager;
import com.orion.salesman._result._object.RouteShopOffline;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._route._object.Route;
import com.orion.salesman._route._object.RouteObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.eksys.nativelib.EksysNetworkException;
import kr.co.eksys.nativelib.NetworkManager;

/**
 * Created by maidinh on 4/8/2016.
 */
public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {
    private String TAG = "RouteAdapter";
    private Context context;
    private List<RouteObject> LIST;

    public List<RouteObject> getLIST() {
        return LIST;
    }

    public void updateGPS(int pos, boolean flag) {
        if (LIST.size() > pos) {
            LIST.get(pos).setTouch(flag);
            notifyItemChanged(pos);
        }
    }


    public void updateAfterEdit(int pos, InforShopDetails isd, String address) {
        LIST.get(pos).setInforShopDetails(isd);
        LIST.get(pos).setADDRESS(address);
        if (pos != 0) {
            int n = pos + 2;
            for (int i = pos; i < n; i++) {
                if (LIST.get(i).getInforShopDetails().getV14() != null
                        && LIST.get(i).getInforShopDetails().getV14().length() > 0
                        && !LIST.get(i).getInforShopDetails().getV14().equals("0")
                        && !LIST.get(i).getInforShopDetails().getV14().equals("0.0")
                        && LIST.get(i).getInforShopDetails().getV15() != null
                        && LIST.get(i).getInforShopDetails().getV15().length() > 0
                        && !LIST.get(i).getInforShopDetails().getV15().equals("0")
                        && !LIST.get(i).getInforShopDetails().getV15().equals("0.0")) {

                    if (LIST.get(i - 1).getInforShopDetails().getV14() != null
                            && LIST.get(i - 1).getInforShopDetails().getV14().length() > 0
                            && !LIST.get(i - 1).getInforShopDetails().getV14().equals("0")
                            && !LIST.get(i - 1).getInforShopDetails().getV14().equals("0.0")
                            && LIST.get(i - 1).getInforShopDetails().getV15() != null
                            && LIST.get(i - 1).getInforShopDetails().getV15().length() > 0
                            && !LIST.get(i - 1).getInforShopDetails().getV15().equals("0")
                            && !LIST.get(i - 1).getInforShopDetails().getV15().equals("0.0")) {
                        double LAT1 = (double) Integer.parseInt(LIST.get(i - 1).getInforShopDetails().getV15()) / Const.GPS_WGS84;
                        double LON1 = (double) Integer.parseInt(LIST.get(i - 1).getInforShopDetails().getV14()) / Const.GPS_WGS84;
                        double LAT2 = (double) Integer.parseInt(LIST.get(i).getInforShopDetails().getV15()) / Const.GPS_WGS84;
                        double LON2 = (double) Integer.parseInt(LIST.get(i).getInforShopDetails().getV14()) / Const.GPS_WGS84;
                        long DISTANCE = Const.getDistanceBetweenTwoPoints(LAT1, LON1, LAT2, LON2);
                        if (DISTANCE < 0)
                            DISTANCE = 0;
                        LIST.get(i).setDISTANCE("" + DISTANCE);
                    } else {
                        LIST.get(i).setDISTANCE("-1");
                    }
                } else {
                    LIST.get(i).setDISTANCE("");
                }
                notifyItemChanged(i);
            }
        } else {
            notifyItemChanged(0);
            for (int i = 1; i < 2; i++) {
                if (LIST.get(i).getInforShopDetails().getV14() != null
                        && LIST.get(i).getInforShopDetails().getV14().length() > 0
                        && !LIST.get(i).getInforShopDetails().getV14().equals("0")
                        && !LIST.get(i).getInforShopDetails().getV14().equals("0.0")
                        && LIST.get(i).getInforShopDetails().getV15() != null
                        && LIST.get(i).getInforShopDetails().getV15().length() > 0
                        && !LIST.get(i).getInforShopDetails().getV15().equals("0")
                        && !LIST.get(i).getInforShopDetails().getV15().equals("0.0")) {

                    if (LIST.get(i - 1).getInforShopDetails().getV14() != null
                            && LIST.get(i - 1).getInforShopDetails().getV14().length() > 0
                            && !LIST.get(i - 1).getInforShopDetails().getV14().equals("0")
                            && !LIST.get(i - 1).getInforShopDetails().getV14().equals("0.0")
                            && LIST.get(i - 1).getInforShopDetails().getV15() != null
                            && LIST.get(i - 1).getInforShopDetails().getV15().length() > 0
                            && !LIST.get(i - 1).getInforShopDetails().getV15().equals("0")
                            && !LIST.get(i - 1).getInforShopDetails().getV15().equals("0.0")) {
                        double LAT1 = (double) Integer.parseInt(LIST.get(i - 1).getInforShopDetails().getV15()) / Const.GPS_WGS84;
                        double LON1 = (double) Integer.parseInt(LIST.get(i - 1).getInforShopDetails().getV14()) / Const.GPS_WGS84;
                        double LAT2 = (double) Integer.parseInt(LIST.get(i).getInforShopDetails().getV15()) / Const.GPS_WGS84;
                        double LON2 = (double) Integer.parseInt(LIST.get(i).getInforShopDetails().getV14()) / Const.GPS_WGS84;
                        long DISTANCE = Const.getDistanceBetweenTwoPoints(LAT1, LON1, LAT2, LON2);
                        if (DISTANCE < 0)
                            DISTANCE = 0;
                        LIST.get(i).setDISTANCE("" + DISTANCE);
                    } else {
                        LIST.get(i).setDISTANCE("-1");
                    }
                } else {
                    LIST.get(i).setDISTANCE("");
                }
                notifyItemChanged(i);
            }
        }
    }

    public void updateLocation(String s, String DATE, String KEY, String address) {
        Log.d(TAG, "updateLocation");
        SavePositionLocaiton savePositionLocaiton = new Gson().fromJson(s, SavePositionLocaiton.class);
        int pos = savePositionLocaiton.getPosition();
        long LON = savePositionLocaiton.getLON();
        long LAT = savePositionLocaiton.getLAT();
        String V4 = savePositionLocaiton.getADDR1();
        String V5 = savePositionLocaiton.getADDR2();
        String V6 = savePositionLocaiton.getADDR3();
        LIST.get(pos).setLON("" + LON);
        LIST.get(pos).setLAT("" + LAT);

        LIST.get(pos).getInforShopDetails().setV14("" + LON);
        LIST.get(pos).getInforShopDetails().setV15("" + LAT);

        LIST.get(pos).getInforShopDetails().setV4("" + V4);
        LIST.get(pos).getInforShopDetails().setV5("" + V5);
        LIST.get(pos).getInforShopDetails().setV29(V4 + V5 + V6);
        LIST.get(pos).setADDRESS(address);

        if (pos != 0) {
            int n = pos + 2;
            for (int i = pos; i < n; i++) {
                if (LIST.get(i).getInforShopDetails().getV14() != null
                        && LIST.get(i).getInforShopDetails().getV14().length() > 0
                        && !LIST.get(i).getInforShopDetails().getV14().equals("0")
                        && !LIST.get(i).getInforShopDetails().getV14().equals("0.0")
                        && LIST.get(i).getInforShopDetails().getV15() != null
                        && LIST.get(i).getInforShopDetails().getV15().length() > 0
                        && !LIST.get(i).getInforShopDetails().getV15().equals("0")
                        && !LIST.get(i).getInforShopDetails().getV15().equals("0.0")) {

                    if (LIST.get(i - 1).getInforShopDetails().getV14() != null
                            && LIST.get(i - 1).getInforShopDetails().getV14().length() > 0
                            && !LIST.get(i - 1).getInforShopDetails().getV14().equals("0")
                            && !LIST.get(i - 1).getInforShopDetails().getV14().equals("0.0")
                            && LIST.get(i - 1).getInforShopDetails().getV15() != null
                            && LIST.get(i - 1).getInforShopDetails().getV15().length() > 0
                            && !LIST.get(i - 1).getInforShopDetails().getV15().equals("0")
                            && !LIST.get(i - 1).getInforShopDetails().getV15().equals("0.0")) {
                        double LAT1 = (double) Integer.parseInt(LIST.get(i - 1).getInforShopDetails().getV15()) / Const.GPS_WGS84;
                        double LON1 = (double) Integer.parseInt(LIST.get(i - 1).getInforShopDetails().getV14()) / Const.GPS_WGS84;
                        double LAT2 = (double) Integer.parseInt(LIST.get(i).getInforShopDetails().getV15()) / Const.GPS_WGS84;
                        double LON2 = (double) Integer.parseInt(LIST.get(i).getInforShopDetails().getV14()) / Const.GPS_WGS84;
                        long DISTANCE = Const.getDistanceBetweenTwoPoints(LAT1, LON1, LAT2, LON2);
                        if (DISTANCE < 0)
                            DISTANCE = 0;
                        LIST.get(i).setDISTANCE("" + DISTANCE);
                    } else {
                        LIST.get(i).setDISTANCE("-1");
                    }
                } else {
                    LIST.get(i).setDISTANCE("");
                }
                notifyItemChanged(i);
            }
        } else {
            for (int i = 1; i < 2; i++) {
                if (LIST.get(i).getInforShopDetails().getV14() != null
                        && LIST.get(i).getInforShopDetails().getV14().length() > 0
                        && !LIST.get(i).getInforShopDetails().getV14().equals("0")
                        && !LIST.get(i).getInforShopDetails().getV14().equals("0.0")
                        && LIST.get(i).getInforShopDetails().getV15() != null
                        && LIST.get(i).getInforShopDetails().getV15().length() > 0
                        && !LIST.get(i).getInforShopDetails().getV15().equals("0")
                        && !LIST.get(i).getInforShopDetails().getV15().equals("0.0")) {

                    if (LIST.get(i - 1).getInforShopDetails().getV14() != null
                            && LIST.get(i - 1).getInforShopDetails().getV14().length() > 0
                            && !LIST.get(i - 1).getInforShopDetails().getV14().equals("0")
                            && !LIST.get(i - 1).getInforShopDetails().getV14().equals("0.0")
                            && LIST.get(i - 1).getInforShopDetails().getV15() != null
                            && LIST.get(i - 1).getInforShopDetails().getV15().length() > 0
                            && !LIST.get(i - 1).getInforShopDetails().getV15().equals("0")
                            && !LIST.get(i - 1).getInforShopDetails().getV15().equals("0.0")) {
                        double LAT1 = (double) Integer.parseInt(LIST.get(i - 1).getInforShopDetails().getV15()) / Const.GPS_WGS84;
                        double LON1 = (double) Integer.parseInt(LIST.get(i - 1).getInforShopDetails().getV14()) / Const.GPS_WGS84;
                        double LAT2 = (double) Integer.parseInt(LIST.get(i).getInforShopDetails().getV15()) / Const.GPS_WGS84;
                        double LON2 = (double) Integer.parseInt(LIST.get(i).getInforShopDetails().getV14()) / Const.GPS_WGS84;
                        long DISTANCE = Const.getDistanceBetweenTwoPoints(LAT1, LON1, LAT2, LON2);
                        if (DISTANCE < 0)
                            DISTANCE = 0;
                        LIST.get(i).setDISTANCE("" + DISTANCE);
                    } else {
                        LIST.get(i).setDISTANCE("-1");
                    }
                } else {
                    LIST.get(i).setDISTANCE("");
                }
                notifyItemChanged(i);
            }
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven, TV_DISTANCE;
        public TextView tvName, tvAddress, tvOwner, tvTel, tvLocation,
                tvGrade, tvShopType, tvCloseShop;
        public LinearLayout layoutRoute;
        public FrameLayout layoutRouteDetail;
        public Button btnGps, btnInfo, btnOrder, btnClose;
        public ImageView imgType;

        public MyViewHolder(View view) {
            super(view);
            layoutRoute = (LinearLayout) view.findViewById(R.id.layoutRoute);
            layoutRouteDetail = (FrameLayout) view.findViewById(R.id.layoutRouteDetail);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            tvSix = (TextView) view.findViewById(R.id.tvSix);
            tvSeven = (TextView) view.findViewById(R.id.tvSeven);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvAddress = (TextView) view.findViewById(R.id.tvAddress);
            tvOwner = (TextView) view.findViewById(R.id.tvOwner);
            tvTel = (TextView) view.findViewById(R.id.tvTel);
            tvLocation = (TextView) view.findViewById(R.id.tvLocation);
            tvGrade = (TextView) view.findViewById(R.id.tvGrade);
            tvCloseShop = (TextView) view.findViewById(R.id.tvCloseShop);
            tvShopType = (TextView) view.findViewById(R.id.tvShopType);
            imgType = (ImageView) view.findViewById(R.id.imgType);
            btnGps = (Button) view.findViewById(R.id.btnGps);
            btnInfo = (Button) view.findViewById(R.id.btnInfo);
            btnOrder = (Button) view.findViewById(R.id.btnOrder);
            btnClose = (Button) view.findViewById(R.id.btnClose);
            TV_DISTANCE = (TextView) view.findViewById(R.id.TV_DISTANCE);
        }
    }

    public RouteAdapter(Context context, List<RouteObject> LIST) {
        this.context = context;
        this.LIST = LIST;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_route, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        int P = position + 1;
//        final String SEQ = "" + P;

        final RouteObject routeObject = LIST.get(position);
        final String SEQ = routeObject.getSEQ();

        final InforShopDetails inforShopDetails = LIST.get(position).getInforShopDetails();
        holder.tvCloseShop.setVisibility(routeObject.isClose() ? View.VISIBLE : View.GONE);
        holder.btnClose.setEnabled(routeObject.isClose() ? false : true);

        String S = routeObject.getDISTANCE();
        if (S.length() == 0) {
            holder.TV_DISTANCE.setText("");
        } else {
            if (S.equals("-1")) {
                holder.TV_DISTANCE.setText("");
            } else {
                holder.TV_DISTANCE.setText(S + " m");
            }
        }

        if (routeObject.isTouch())
            Const.setBackgroundButtonRed(holder.btnOrder);
        else
            Const.setBackgroundButtonGray(holder.btnOrder);

        holder.layoutRouteDetail.setVisibility(routeObject.isShow() ? View.VISIBLE : View.GONE);

        holder.tvOne.setText(SEQ);
        holder.tvTwo.setText(routeObject.getSHOPNAME() + "\n" + routeObject.getADDRESS());
        holder.tvThree.setText(Const.RoundNumber(routeObject.getMAG()));
        holder.tvFour.setText(Const.RoundNumber(routeObject.getMTD()));


        if (routeObject.isVisit()) {
//            holder.tvFive.setText(routeObject.getOMI().equals("1") ? "Y" : "N");
            holder.tvFive.setText(routeObject.getOMI());
            holder.tvSix.setText(Const.RoundNumber(routeObject.getORDER()));

        } else {
            holder.tvSix.setText("");
            holder.tvFive.setText("");
        }

        if (routeObject.getNONSHOP().length() > 0) {
            holder.tvSeven.setVisibility(View.VISIBLE);
            holder.tvSeven.setText(routeObject.getNONSHOP());
            if (routeObject.getNONSHOP().contains("1"))
                holder.tvSeven.setBackgroundResource(R.drawable.circle_text_nonshop_lv_1);
            else if (routeObject.getNONSHOP().contains("2"))
                holder.tvSeven.setBackgroundResource(R.drawable.circle_text_nonshop_lv_2);
            else holder.tvSeven.setBackgroundResource(R.drawable.circle_text_nonshop_lv_3);
        } else {
            holder.tvSeven.setVisibility(View.GONE);
            holder.tvSeven.setText("");
        }

        holder.layoutRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"SEQ:"+SEQ,Toast.LENGTH_SHORT).show();
                MainActivity.display_icon_update_stop_shop = 0;
                MainActivity.SHOP_POSITION_UPDATE_GPS = position;
                MainActivity.CUSTCD = routeObject.getSHOPCODE();
                MainActivity.AREA = inforShopDetails.getV31();
                MainActivity.ADDRESS = routeObject.getADDRESS();
                MainActivity.V7 = inforShopDetails.getV7();
                MainActivity.V6 = inforShopDetails.getV6();
                MainActivity.SHOP_POSITION = position;
                MainActivity.SEQ = SEQ;
                MainActivity.CHANNEL = inforShopDetails.getV30();
                MainActivity.SHOP_CODE = routeObject.getSHOPCODE();
                if (routeObject.isVisit()) {
                    MainActivity.STATUS_ORDER = true;
                } else {
                    MainActivity.STATUS_ORDER = false;
                }

                for (int i = 0; i < LIST.size(); i++) {
                    RouteObject r = LIST.get(i);
                    if (r.isShow() && i != position) {
                        r.setShow(false);
                        notifyItemChanged(i);
                        break;
                    }
                }
                routeObject.setShow(routeObject.isShow() ? false : true);
                holder.layoutRouteDetail.setVisibility(routeObject.isShow() ? View.VISIBLE : View.GONE);
                if (holder.layoutRouteDetail.isShown()) {
                    MainActivity.UPDATE_GPS_LON = inforShopDetails.getV14();
                    MainActivity.UPDATE_GPS_LAT = inforShopDetails.getV15();
                    MainActivity.instance.updateAdapter();

                    if (routeObject.getLON() != null && routeObject.getLON().length() > 0
                            && routeObject.getLAT() != null && routeObject.getLAT().length() > 0) {
                        MainActivity.instance.removeUpdateGPS();
                        MainActivity.instance.updateGPS();
                    } else {
                        MainActivity.instance.removeUpdateGPS();
                    }
                    if (position == LIST.size() - 1)
                        RouteFragment.instance.ScrollEndList(LIST.size());
                } else {
                    MainActivity.instance.removeUpdateGPS();
                }

            }
        });
        holder.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.checkNewOrEditShop = 1;
//                Log.d(TAG, "url1" + inforShopDetails.getV16());
//                Log.d(TAG, "url2" + inforShopDetails.getV17());
                Intent intent = new Intent(context, ShopInfoActivity.class);
                intent.putExtra(Const.LIST_INFO_SHOP, RouteFragment.instance.getListInfo(inforShopDetails.getV1()));
                intent.putExtra(Const.LIST_DETAILS, new Gson().toJson(inforShopDetails));
                intent.putExtra(Const.SEQ, SEQ);
                ((Activity) context).startActivityForResult(intent, Const.REQUEST_UPDATE_SHOP);
            }
        });
        holder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position != 0) {
                    RouteObject obj = LIST.get(position - 1);
                    if (!obj.isClose()) {
                        if (!obj.isVisit()) {
                            String msg = MainActivity.dataLogin.getPERSHORTDSC() + " SM (" + obj.getSHOPCODE() + " / " + obj.getSHOPNAME() + ") Shop can not visit, check please";
//                                    (huy) SM (test shop) Shop Can’t Visit. Check SM
                            new HttpRequest(context).new API_6(MainActivity.dataLogin, MainActivity.dataLogin.getMNGEMPID(), msg, "", "", new IF_2() {
                                @Override
                                public void onSuccess(String s) {

                                }
                            }).execute();
                        }
                    }
                }

                MainActivity.SALESMANTYPE = routeObject.getSHOPTYPE();
                MainActivity.SHOP_ID = routeObject.getSHOPCODE();
                MainActivity.DEALERCD = inforShopDetails.getV18();
                Intent intent = new Intent(context, OrderActivity.class);
                List<String> arrStrings = new ArrayList<String>();
                arrStrings.add(inforShopDetails.getV1());
                arrStrings.add(inforShopDetails.getV3());
                arrStrings.add(inforShopDetails.getV6());
                intent.putExtra(Const.CUSTCD, inforShopDetails.getV1());
                intent.putExtra(Const.INFOR_SHOP, new Gson().toJson(arrStrings));
                intent.putExtra(Const.LIST_DETAILS, new Gson().toJson(inforShopDetails));
                context.startActivity(intent);
                String grade = "";
                if (MainActivity.TEAM.equals(Const.GUM_TEAM))
                    grade = inforShopDetails.getV10();
                else if (MainActivity.TEAM.equals(Const.SNACK_TEAM))
                    grade = inforShopDetails.getV9();
                else grade = inforShopDetails.getV8();
                MainActivity.SHOP_GRADE = grade;


            }
        });
        holder.btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.MY_ADDRESS = routeObject.getADDRESS();
                MainActivity.SHOP_NAME = inforShopDetails.getV3();
                MainActivity.checkNewOrEditShop = 4;
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra(Const.CHECK_KIND_GOTO_MAP, 0);
                intent.putExtra(Const.FROM_POSITION, position);
                intent.putExtra(Const.CUSTCD, inforShopDetails.getV1());
                intent.putExtra(Const.SHOP_INFO_DETAIL, new Gson().toJson(inforShopDetails));
                ((Activity) context).startActivityForResult(intent, Const.REQUEST_CODE);
            }
        });
        holder.tvName.setText(inforShopDetails.getV1() + " - " + inforShopDetails.getV3());
        holder.tvAddress.setText(context.getResources().getString(R.string.address) + ": " + routeObject.getADDRESS());
        holder.tvOwner.setText(context.getResources().getString(R.string.owner) + ": " + inforShopDetails.getV26());
        holder.tvTel.setText(context.getResources().getString(R.string.tel) + ": " + inforShopDetails.getV28());

        if (inforShopDetails.getV14() != null && inforShopDetails.getV14().length() > 0
                && inforShopDetails.getV15() != null && inforShopDetails.getV15().length() > 0) {
            holder.tvLocation.setText(context.getResources().getString(R.string.localtion) + ": "
                    + (float) Integer.parseInt(inforShopDetails.getV14()) / Const.GPS_WGS84 + " - "
                    + (float) Integer.parseInt(inforShopDetails.getV15()) / Const.GPS_WGS84);
            holder.btnGps.setText(context.getResources().getString(R.string.gotoShop));
        } else {
            holder.btnGps.setText(context.getResources().getString(R.string.gps));
            holder.tvLocation.setText(context.getResources().getString(R.string.localtion) + ":");
        }
        String grade = "";
        if (MainActivity.TEAM.equals(Const.GUM_TEAM))
            grade = inforShopDetails.getV10();
        else if (MainActivity.TEAM.equals(Const.SNACK_TEAM))
            grade = inforShopDetails.getV9();
        else grade = inforShopDetails.getV8();

        holder.tvGrade.setText(context.getResources().getString(R.string.grade) + ": " + grade);
        String type = "";
        if (inforShopDetails.getV2().equals("1")) {
            type = "DS";
            holder.imgType.setVisibility(View.VISIBLE);
        } else {
            type = "PS";
            holder.imgType.setVisibility(View.GONE);
        }
        holder.tvShopType.setText(context.getResources().getString(R.string.shoptype) + ": " + type);
        holder.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogClose(routeObject, position, SEQ);

            }
        });
    }

    CustomDialog customDialog;


    public void updateOrderList(int pos, SaveInputData obSave) {
//        if (obSave.getDATA_124().length() > 10)
        LIST.get(pos).setORDER(MainActivity.valueOrder);
//        if (obSave.getDATA_119().length() > 0)
//            LIST.get(pos).setOMI("1");
        LIST.get(pos).setOMI(MainActivity.valueOMI);
        LIST.get(pos).setVisit(true);
        notifyItemChanged(pos);
    }

    public void updateOrderInfor(int pos, String V20, String V21, String V22, String V23, String V24, String V25) {
        LIST.get(pos).getInforShopDetails().setV20(V20);
        LIST.get(pos).getInforShopDetails().setV21(V21);
        LIST.get(pos).getInforShopDetails().setV22(V22);
        LIST.get(pos).getInforShopDetails().setV23(V23);
        LIST.get(pos).getInforShopDetails().setV24(V24);
        LIST.get(pos).getInforShopDetails().setV25(V25);
        notifyItemChanged(pos);
    }

    void showDialogClose(final RouteObject route, final int pos, final String SEQ) {
        customDialog = new CustomDialog(context, context.getResources().getString(R.string.noticloseshop), new OnClickDialog() {
            @Override
            public void btnOK() {
                customDialog.dismiss();
                if (Const.checkInternetConnection(context)) {
                    route.setClose(true);
                    notifyItemChanged(pos);
                    RouteFragment.instance.updateCloseShop(route.getSHOPCODE());
                    Log.d(TAG, "POSITION:" + pos);

                    new sendAPI(SEQ, route.getSHOPCODE()).execute();
                }
            }

            @Override
            public void btnCancel() {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    class sendAPI extends AsyncTask<String, String, String> {
        String SEQ;
        String CUSTCD;

        public sendAPI(String SEQ, String CUSTCD) {
            this.SEQ = SEQ;
            this.CUSTCD = CUSTCD;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            RouteFragment.instance.showPR();
        }

        @Override
        protected String doInBackground(String... strings) {
            callAPICloseShop(SEQ, CUSTCD);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            RouteFragment.instance.hidePR();
            if (connect == 1) {
//                MyApplication.getInstance().showToast("API 115 ok");
            } else {
                MyApplication.getInstance().showToast("API 115 fail");
            }
        }
    }

    int connect = 0;

    void callAPICloseShop(String SEQ, String CUSTCD) {
        /*
        * type:1 ->goto shop  - 2: out shop
        * */

        if (RouteFragment.KEY_ROUTE.length() > 0 && SEQ.length() > 0 && CUSTCD.length() > 0) {
            int type = 1;
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            String TIME = Const.setFullDate(hour) + Const.setFullDate(minute) + Const.setFullDate(second);
            String _DATE = year + Const.setFullDate(month) + Const.setFullDate(date);
            DataLogin dataLogin = new Gson().fromJson(new PrefManager(context).getInfoLogin(), DataLogin.class);

            String contents = "";
            Map<String, String> map = new HashMap<>();
            map.put("MODE", "" + type);
            map.put("DATE", "" + Const.getToday());
            map.put("EMPID", "" + dataLogin.getUSERID());
            map.put("WKDAY", "" + RouteFragment.KEY_ROUTE);
            map.put("CUSTCD", CUSTCD);
            map.put("SEQ", SEQ);
            if (type == 1) {
                map.put("INTIME", TIME);
                map.put("OUTTIME", "");
            } else if (type == 2) {
                map.put("INTIME", "");
                map.put("OUTTIME", TIME);
            }
            map.put("ISOPEN", "Y");
            String ISINSHOP = "N";
            if (MainActivity.DISTANCE <= MainActivity.CHECKSHOPDISTANCE)
                ISINSHOP = "Y";
            map.put("ISINSHOP", ISINSHOP);
            Log.d(TAG, new Gson().toJson(map));
            String idLogin = dataLogin.getUSERID() + "";
            if ((Const.checkInternetConnection(context)
                    && Const.PHONENUMBER.length() > 0
                    && idLogin.trim().length() > 0
                    && dataLogin.getDEPTCD().trim().length() > 0
                    && dataLogin.getJOBCODE().trim().length() > 0)
                    || (Const.checkInternetConnection(context) && dataLogin.getJOBGRADE().trim().equals(Const.SE))) {
                NetworkManager nm = new NetworkManager(Const.IP, Const.PORT);
                nm.setTerminalInfo(Const.PHONENUMBER, idLogin, dataLogin.getDEPTCD(), dataLogin.getJOBCODE());
                try {
                    nm.setTR('A', Const.VisitShop);
                    Log.d(TAG, "VisitShop ROUTE ADAPTER");
                    nm.connect(10);          // set timeout
                    nm.send(new Gson().toJson(map));// send body
                    contents = nm.receive();
                    VisitShop summarySales_api = new Gson().fromJson(contents, VisitShop.class);
                    if (summarySales_api.getRESULT() == 0) {
                        Log.d(TAG, "SalesMan VisitShop success");
                        connect = 1;
                    } else {
                        Log.d(TAG, "VisitShop fail");
                    }
                    Log.d(TAG, "VisitShop: " + contents);
                } catch (EksysNetworkException e) {
                    contents = "VisitShop Error(" + e.getErrorCode() + ")";
                    Log.e(TAG, "VisitShop Error(" + e.getErrorCode() + "): " + e.toString());
                } finally {
                    nm.close();
                }
            }
        }


    }

    @Override
    public int getItemCount() {
        return LIST.size();
    }
}