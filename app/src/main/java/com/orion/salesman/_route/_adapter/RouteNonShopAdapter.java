package com.orion.salesman._route._adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman.ShopInfoActivity;
import com.orion.salesman._class.Const;
import com.orion.salesman._result._object.RouteNonShop;
import com.orion.salesman._route._fragment.RouteFragment;
import com.orion.salesman._route._object.InforShopDetails;
import com.orion.salesman._route._object.getSMWorkingDayObject;

import java.util.List;

/**
 * Created by maidinh on 5/8/2016.
 */
public class RouteNonShopAdapter extends RecyclerView.Adapter<RouteNonShopAdapter.MyViewHolder> {
    private List<getSMWorkingDayObject> listWorkingDay;
    private List<RouteNonShop> moviesList;
    private Context context;
    private List<InforShopDetails> listAllShopInfor;
    String TAG = "RouteNonShopAdapter";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo;
        public RelativeLayout btnDetails;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            btnDetails = (RelativeLayout) view.findViewById(R.id.btnDetails);
        }
    }

    public void updateList(List<RouteNonShop> moviesList) {
        this.moviesList = moviesList;
        this.notifyDataSetChanged();
    }

    public RouteNonShopAdapter(Context context, List<RouteNonShop> moviesList, List<InforShopDetails> listAllShopInfor, List<getSMWorkingDayObject> listWorkingDay) {
        this.context = context;
        this.moviesList = moviesList;
        this.listAllShopInfor = listAllShopInfor;
        this.listWorkingDay = listWorkingDay;
    }

    String getSEQ(String CUSTCD) {
        String SEQ = "";
        if (listWorkingDay.size() == 0) {

        } else {
            for (getSMWorkingDayObject obj : listWorkingDay) {
                if (CUSTCD.equals(obj.getV3())) {
                    SEQ = obj.getV1();
                    break;
                }
            }
        }
        return SEQ;
    }

    InforShopDetails getDetail(String CUSTCD) {
        InforShopDetails isd = null;
        for (InforShopDetails obj : listAllShopInfor) {
            if (CUSTCD.equals(obj.getV1())) {
                isd = obj;
                break;
            }
        }
        return isd;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_nonshop, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RouteNonShop movie = moviesList.get(position);
        holder.tvOne.setText(movie.getV2());
        holder.tvTwo.setText(movie.getV3());
        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"position:"+position,Toast.LENGTH_SHORT).show();
                InforShopDetails obj = getDetail(movie.getV1());
                String SEQ = getSEQ(movie.getV1());

                if (obj != null && SEQ.length() > 0) {
                    MainActivity.display_icon_update_stop_shop = 1;
                    MainActivity.checkNewOrEditShop = 1;
                    Intent intent = new Intent(context, ShopInfoActivity.class);
                    intent.putExtra(Const.LIST_INFO_SHOP, RouteFragment.instance.getListInfo(obj.getV1()));
                    intent.putExtra(Const.LIST_DETAILS, new Gson().toJson(obj));
                    intent.putExtra(Const.SEQ, SEQ);
                    ((Activity) context).startActivityForResult(intent, Const.REQUEST_UPDATE_SHOP);
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.can_not_get_data), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}