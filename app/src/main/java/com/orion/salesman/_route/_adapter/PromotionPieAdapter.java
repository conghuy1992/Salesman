package com.orion.salesman._route._adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._route._object.PromotionInfor;
import com.orion.salesman._route._object.PromotionPieObject;

import java.util.List;

/**
 * Created by maidinh on 18/8/2016.
 */
public class PromotionPieAdapter extends RecyclerView.Adapter<PromotionPieAdapter.MyViewHolder> {
    String TAG = "PromotionPieAdapter";
    private List<PromotionPieObject> moviesList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvZero, tvOne, tvTwo, tvThree, tvFour;
        public FrameLayout frRoot;
        public LinearLayout lnPromotion;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvZero = (TextView) view.findViewById(R.id.tvZero);
            frRoot = (FrameLayout) view.findViewById(R.id.frRoot);
            lnPromotion = (LinearLayout) view.findViewById(R.id.lnPromotion);
        }
    }

    public PromotionPieAdapter(Context context, List<PromotionPieObject> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_promotion_pie_fragment_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PromotionPieObject movie = moviesList.get(position);
        String tv1 = movie.getV1();
        holder.tvOne.setText(tv1.substring(0, 4) + "-" + tv1.substring(4, 6) + "-" + tv1.substring(6, 8));
        String tv2 = movie.getV2();
        holder.tvTwo.setText(tv2.substring(0, 4) + "-" + tv2.substring(4, 6) + "-" + tv2.substring(6, 8));
//        holder.tvThree.setText(movie.getV3());
        holder.tvFour.setText(movie.getV4());
//        Log.d(TAG,"movie.getV4():"+movie.getV4());
        holder.tvZero.setVisibility(View.GONE);
//        Log.d(TAG,holder.tvThree.getText().toString());

        String S3 = movie.getV3();
        String S4[] = S3.split("=>");
        String S5[] = S4[0].split(" or ");
        String S6[] = S4[1].split(" or ");
        holder.lnPromotion.removeAllViews();
        for (int i = 0; i < S5.length; i++) {
            final TextView tv = new TextView(context);
//            tv.setTextColor(context.getResources().getColor(R.color.black));
//            tv.setBackgroundColor(context.getResources().getColor(R.color.backgroundbuy));
//            tv.setGravity(Gravity.CENTER_VERTICAL);
            if (S5.length > 1) {
                if (i == S5.length - 1) {
                    tv.setText("BUY " + S5[i]);
                } else {
                    tv.setText("BUY " + S5[i] + " or");
                }
            } else {
                tv.setText("BUY " + S5[i]);
            }
            Const.StyleTextBuyGift(context, tv, 0);
            holder.lnPromotion.addView(tv);

//            if (i != S5.length - 1) {
            TextView line = new TextView(context);
            line.setBackgroundColor(context.getResources().getColor(R.color.teamColor));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.tv_2));
            line.setLayoutParams(params);
            holder.lnPromotion.addView(line);
//            }
        }
        for (int i = 0; i < S6.length; i++) {
            TextView tv = new TextView(context);
//            tv.setTextColor(context.getResources().getColor(R.color.black));


            if (S6.length > 1) {
                if (i == S6.length - 1) {
                    tv.setText("GIFT " + S6[i]);
                } else {
                    tv.setText("GIFT " + S6[i] + " or");
                }
            } else {
                tv.setText("GIFT " + S6[i]);
            }

            Const.StyleTextBuyGift(context, tv, 1);

            holder.lnPromotion.addView(tv);
            if (i != S6.length - 1) {
                TextView line = new TextView(context);
                line.setBackgroundColor(context.getResources().getColor(R.color.teamColor));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.tv_2));
                line.setLayoutParams(params);
                holder.lnPromotion.addView(line);
            }
        }

        if (holder.lnPromotion.getChildCount() > 2) {
            FrameLayout.LayoutParams params =
                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT);
            holder.frRoot.setLayoutParams(params);
        }

//        holder.tvThree.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "count:" + holder.tvThree.getLineCount());
//                if (holder.tvThree.getLineCount() > 3) {
//                    FrameLayout.LayoutParams params =
//                            new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                                    FrameLayout.LayoutParams.WRAP_CONTENT);
//                    holder.frRoot.setLayoutParams(params);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}