package com.orion.salesman._route._adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._class.CustomDialog;
import com.orion.salesman._interface.OnClickDialog;
import com.orion.salesman._route._fragment.RouteConfirmFragment;
import com.orion.salesman._route._object.Pie;
import com.orion.salesman._route._object.RouteConfirm;
import com.orion.salesman._route._object.Snack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 16/8/2016.
 */
public class RouteConfirmFragmentAdapter extends RecyclerView.Adapter<RouteConfirmFragmentAdapter.MyViewHolder> {
    private List<RouteConfirm> moviesList;
    private Context context;
    private RouteConfirmFragment confirmFragment;

    public List<RouteConfirm> getList() {
        if (moviesList == null)
            moviesList = new ArrayList<>();
        return moviesList;
    }

    public void updateListPM(List<String> list) {
        if (list != null && list.size() > 0) {
            for (RouteConfirm confirm : moviesList) {
                boolean flag = true;
                String PRDCD = confirm.getPRDCD();
                for (String s : list) {
                    if (PRDCD.trim().equals(s.trim())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    confirm.setV6(false);
                }
            }
        } else {
            for (RouteConfirm confirm : moviesList) {
                confirm.setV6(false);
            }
        }
        this.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix, tvSeven, tvCancelClick, tvProductCode;
        public ImageView imgPMT, imgCancel;
        public FrameLayout lnRoot;

        public MyViewHolder(View view) {
            super(view);
            tvProductCode = (TextView) view.findViewById(R.id.tvProductCode);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            tvSix = (TextView) view.findViewById(R.id.tvSix);
            tvSeven = (TextView) view.findViewById(R.id.tvSeven);
            tvCancelClick = (TextView) view.findViewById(R.id.tvCancelClick);
            imgPMT = (ImageView) view.findViewById(R.id.imgPMT);
            imgCancel = (ImageView) view.findViewById(R.id.imgCancel);
            lnRoot = (FrameLayout) view.findViewById(R.id.lnRoot);
        }
    }

    public RouteConfirmFragmentAdapter(Context context, List<RouteConfirm> moviesList, RouteConfirmFragment confirmFragment) {
        this.context = context;
        this.moviesList = moviesList;
        this.confirmFragment = confirmFragment;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_order_product_no_line, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setTextColorBlack(TextView v) {
        v.setTextColor(Color.BLACK);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final RouteConfirm movie = moviesList.get(position);
        holder.tvOne.setText(movie.getV1());
        holder.tvProductCode.setText(movie.getPRDCD());
        String tv_2 = movie.getV2();
        if (tv_2.length() == 0)
            tv_2 = "0";
        if (!movie.isCheck()) {
            holder.tvTwo.setText(tv_2);
            holder.tvThree.setText(Const.formatAMT((long) Double.parseDouble(movie.getV3())));
            holder.tvFour.setText("");
            holder.tvFive.setText("");
        } else {
            holder.tvTwo.setText("");
            holder.tvThree.setText("");
            holder.tvFour.setText(tv_2);
            holder.tvFive.setText(Const.formatAMT((long) Double.parseDouble(movie.getV3())));
        }
        holder.imgCancel.setVisibility(tv_2.equals("0") ? View.GONE : View.VISIBLE);
        holder.tvCancelClick.setEnabled(tv_2.equals("0") ? false : true);
        if (tv_2.equals("0")) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 0);
            holder.lnRoot.setLayoutParams(params);
        } else {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.rowHeight));
            holder.lnRoot.setLayoutParams(params);
        }

//        holder.imgCancel.setVisibility(View.VISIBLE);
        holder.imgPMT.setVisibility(movie.isV6() ? View.VISIBLE : View.GONE);
        holder.tvSix.setText("");
        holder.tvSeven.setText("");
        holder.tvCancelClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelPMT(position);
            }
        });
    }

    long tv2 = 0;
    long tv4 = 0;
    long tv3 = 0;
    long tv5 = 0;

    void UpdateText() {
        tv2 = 0;
        tv4 = 0;
        tv3 = 0;
        tv5 = 0;
        for (int i = 0; i < moviesList.size(); i++) {
            if (!moviesList.get(i).isCheck()) {
                if (moviesList.get(i).getV2() != null)
                    tv2 += Integer.parseInt(moviesList.get(i).getV2());
                if (moviesList.get(i).getV3() != null)
                    tv3 += (long) Double.parseDouble(moviesList.get(i).getV3());
            } else {
                if (moviesList.get(i).getV2() != null)
                    tv4 += Integer.parseInt(moviesList.get(i).getV2());
                if (moviesList.get(i).getV3() != null)
                    tv5 += (long) Double.parseDouble(moviesList.get(i).getV3());
            }
        }
    }

    void CancelPMT(final int pos) {
        boolean flag = moviesList.get(pos).isV6();
        String msg = "";
        if (flag)
            msg = context.getResources().getString(R.string.notidele_promotion);
        else
            msg = context.getResources().getString(R.string.notidele);
        customDialog = new CustomDialog(context, msg, new OnClickDialog() {
            @Override
            public void btnOK() {
//                if (moviesList.get(pos).isSave()) {
//                    boolean isPMT = moviesList.get(pos).isV6();
//                    String TPRDCD = moviesList.get(pos).getTPRDCD();
//                    String PRDCD = moviesList.get(pos).getPRDCD();
//                    moviesList.get(pos).setV2("0");
//                    moviesList.get(pos).setV3("0");
//                    confirmFragment.updateListPromotion(TPRDCD, PRDCD, isPMT);
//                    notifyItemChanged(pos);
//                    UpdateText();
//                    if (moviesList.size() > 0)
//                        confirmFragment.UpdateTextSum(tv2, tv3, tv4, tv5);
//                    else confirmFragment.UpdateTextSum(0, 0, 0, 0);
//                } else {
                boolean isPMT = moviesList.get(pos).isV6();
                String TPRDCD = moviesList.get(pos).getTPRDCD();
                String PRDCD = moviesList.get(pos).getPRDCD();
                moviesList.remove(pos);
                confirmFragment.updateListPromotion(TPRDCD, PRDCD, isPMT, moviesList.size());
                notifyDataSetChanged();
                UpdateText();
                if (moviesList.size() > 0)
                    confirmFragment.UpdateTextSum(tv2, tv3, tv4, tv5);
                else confirmFragment.UpdateTextSum(0, 0, 0, 0);
//                }
                customDialog.dismiss();
            }

            @Override
            public void btnCancel() {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    CustomDialog customDialog;

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
