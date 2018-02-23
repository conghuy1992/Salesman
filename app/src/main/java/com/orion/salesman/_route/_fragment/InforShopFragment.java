package com.orion.salesman._route._fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman.ShopInfoActivity;
import com.orion.salesman.ThreeMonthHistory;
import com.orion.salesman.ZoomImage;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.AddressSQLite;
import com.orion.salesman._object.AddressSQLiteStreets;
import com.orion.salesman._route._adapter.DisplayInfoAdapter;
import com.orion.salesman._route._adapter.OrderInformationAdapter;
import com.orion.salesman._route._object.DisplayInfo;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._route._object.OrderInformation;
import com.orion.salesman._sqlite.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 4/8/2016.
 */
public class InforShopFragment extends Fragment {
    private RelativeLayout btnEdit, btnTrade;
    private String TAG = "InforShopFragment";
    private List<OrderInformation> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderInformationAdapter mAdapter;
    private List<DisplayInfo> infoArrayList = new ArrayList<>();
    private RecyclerView recyclerViewDisplayInfo;
    private DisplayInfoAdapter adapterDisplayInfo;
    private InforShopDetails shopDetailsList;
    private TextView tvName, tvGrade, tvAddress, tvOwner, tvTel, tvShopType;
    private ImageView img_1, img_2;
    private String SEQ = "";

    void gotoZoomIMG(int pos) {
        Intent intent = new Intent(getActivity(), ZoomImage.class);
        intent.putExtra(Const.POSITION_IMAGE, pos);
        intent.putExtra(Const.SEND_URL, URL_1);
        intent.putExtra(Const.SEND_URL_2, URL_2);
        getActivity().startActivity(intent);
    }

    String URL_1 = "";
    String URL_2 = "";

    void initText(View v) {
        img_1 = (ImageView) v.findViewById(R.id.img_1);
        img_2 = (ImageView) v.findViewById(R.id.img_2);
        img_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoZoomIMG(0);
            }
        });
        img_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoZoomIMG(1);
            }
        });
        if (shopDetailsList != null) {
            URL_1 = shopDetailsList.getV16();
            URL_2 = shopDetailsList.getV17();
            Log.d(TAG,"URL_1:"+URL_1);
            Log.d(TAG,"URL_2:"+URL_2);
            Glide.with(getActivity())
                    .load(shopDetailsList.getV16())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new GlideDrawableImageViewTarget(img_1) {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            img_1.setImageResource(R.drawable.warning);

                        }
                    });

            Glide.with(getActivity())
                    .load(shopDetailsList.getV17())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new GlideDrawableImageViewTarget(img_2) {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            img_2.setImageResource(R.drawable.warning);
                        }
                    });
        }
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvGrade = (TextView) v.findViewById(R.id.tvGrade);
        tvAddress = (TextView) v.findViewById(R.id.tvAddress);
        tvOwner = (TextView) v.findViewById(R.id.tvOwner);
        tvTel = (TextView) v.findViewById(R.id.tvTel);
        tvShopType = (TextView) v.findViewById(R.id.tvShopType);

        tvName.setText(shopDetailsList.getV1() + " - " + shopDetailsList.getV3());
        String grade = "";
        if (MainActivity.TEAM.equals(Const.PIE_TEAM))
            grade = shopDetailsList.getV8();
        else if (MainActivity.TEAM.equals(Const.SNACK_TEAM))
            grade = shopDetailsList.getV9();
        else grade = shopDetailsList.getV10();
        tvGrade.setText(getResources().getString(R.string.grade) + ": " + grade);
        tvAddress.setText(getResources().getString(R.string.address) + ": " + MainActivity.ADDRESS);
        tvOwner.setText(getResources().getString(R.string.owner) + ": " + shopDetailsList.getV26());
        tvTel.setText(getResources().getString(R.string.tel) + ": " + shopDetailsList.getV28());
        String type = "";
        if (shopDetailsList.getV2().equals("1")) {
            type = "DS";
        } else {
            type = "PS";
        }
        tvShopType.setText(getResources().getString(R.string.shoptype) + ": " + type);
    }

    public void createRC_2(View v) {
        if (infoArrayList == null || infoArrayList.size() == 0) {
            tvNodata.setVisibility(View.VISIBLE);
        } else {
            tvNodata.setVisibility(View.GONE);
            adapterDisplayInfo = new DisplayInfoAdapter(infoArrayList);
            recyclerViewDisplayInfo = (RecyclerView) v.findViewById(R.id.rcDisplayInfo);
            recyclerViewDisplayInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerViewDisplayInfo.setAdapter(adapterDisplayInfo);
        }

    }

    public void createRC_1(View v) {
        OrderInformation ob = new OrderInformation(shopDetailsList.getV20(), shopDetailsList.getV21(), shopDetailsList.getV22(),
                shopDetailsList.getV23(), shopDetailsList.getV24(), shopDetailsList.getV25());
        arrayList.clear();
        arrayList.add(ob);
        if (arrayList == null || arrayList.size() == 0) {
            tvNodata2.setVisibility(View.VISIBLE);
        } else {
            tvNodata2.setVisibility(View.GONE);
            mAdapter = new OrderInformationAdapter(arrayList);
            recyclerView = (RecyclerView) v.findViewById(R.id.rcOrderInfo);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }

    }

    @SuppressLint("ValidFragment")
    public InforShopFragment(List<DisplayInfo> infoArrayList, InforShopDetails shopDetailsList, String SEQ) {
        this.infoArrayList = infoArrayList;
        this.shopDetailsList = shopDetailsList;
        this.SEQ = SEQ;
    }

    public InforShopFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    List<AddressSQLiteStreets> getAddressStreet = new ArrayList<>();
    List<AddressSQLite> getAddress = new ArrayList<>();

    void initDB() {
        getAddressStreet.clear();
        getAddress.clear();
        DataBaseHelper db = new DataBaseHelper(getActivity());
        db.openDB();
        getAddressStreet = db.getAddressStreet();
        getAddress = db.getAddress();
    }

    String getCity(String s1, String s2) {
        String s = "";
        for (AddressSQLite as : getAddress) {
            if (s1.equals(as.getADDR1()) && s2.equals(as.getADDR2())) {
                s = as.getADDR5() + ", " + as.getADDR4();
            }
        }
        return s;
    }

    String getStreet(String code) {
        String s = "";
        for (AddressSQLiteStreets str : getAddressStreet)
            if (code.equals(str.getSTREETCD()))
                s = str.getSTREETNM().trim();
        return s;
    }

    private ProgressBar progressbar;
    private TextView tvNodata, tvNodata2;

    class loadData extends AsyncTask<String, String, String> {
        View v;

        public loadData(View v) {
            this.v = v;
        }

        @Override
        protected String doInBackground(String... params) {
            initDB();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            initView(v);
            progressbar.setVisibility(View.GONE);
            svParent.setVisibility(View.VISIBLE);
            ShopInfoActivity.checkClick = true;
        }
    }

    ScrollView svParent;
    Button btnHistory;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.info_shop_fragment, container, false);
        ShopInfoActivity.checkClick = false;
        tvNodata = (TextView) v.findViewById(R.id.tvNodata);
        tvNodata2 = (TextView) v.findViewById(R.id.tvNodata2);
        progressbar = (ProgressBar) v.findViewById(R.id.progressbar);
        svParent = (ScrollView) v.findViewById(R.id.svParent);
        btnHistory = (Button) v.findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CUSTCD = "";
                if (shopDetailsList != null) {
                    CUSTCD = shopDetailsList.getV1().trim();
                }

                Intent intent = new Intent(getActivity(), ThreeMonthHistory.class);
                intent.putExtra(Const.CUSTCD, CUSTCD);
                startActivity(intent);
            }
        });
        new loadData(v).execute();
        return v;
    }

    void initView(View v) {
        createRC_1(v);
        createRC_2(v);
        initText(v);
        btnTrade = (RelativeLayout) v.findViewById(R.id.btnTrade);
        btnTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.checkNewOrEditShop = 2;
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_left_exit,
                                R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_right_exit)
                        .replace(R.id.flShopInfo, new EditFragment(shopDetailsList, SEQ))
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnEdit = (RelativeLayout) v.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.checkNewOrEditShop = 1;
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.fragment_slide_right_enter,
                                R.anim.fragment_slide_left_exit,
                                R.anim.fragment_slide_left_enter,
                                R.anim.fragment_slide_right_exit)
                        .replace(R.id.flShopInfo, new EditFragment(shopDetailsList, SEQ))
                        .addToBackStack(null)
                        .commit();
            }
        });
        if (MainActivity.display_icon_update_stop_shop == 1) {
            btnTrade.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        } else {
            btnTrade.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ShopInfoActivity.flag = true;
        ShopInfoActivity.getInstance().setTitleForText(getResources().getString(R.string.shopInfo));
    }

    @Override
    public void onPause() {
        super.onPause();
        ShopInfoActivity.flag = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}