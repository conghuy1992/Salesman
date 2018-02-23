package com.orion.salesman._route._adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._route._object.PromotionInfor;

import java.util.List;

/**
 * Created by maidinh on 16/8/2016.
 */
public class PromotionInforAdapter extends RecyclerView.Adapter<PromotionInforAdapter.MyViewHolder> {
    private List<PromotionInfor> moviesList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour;
        public LinearLayout lnRoot, lnPromotion;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvZero);
            lnRoot = (LinearLayout) view.findViewById(R.id.lnRoot);
            lnPromotion = (LinearLayout) view.findViewById(R.id.lnPromotion);

        }
    }

    public PromotionInforAdapter(Context context, List<PromotionInfor> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_promotion_infor_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PromotionInfor movie = moviesList.get(position);
        String tv1 = movie.getV1();
        holder.tvOne.setText(tv1.substring(0, 4) + "-" + tv1.substring(4, 6) + "-" + tv1.substring(6, 8));
        String tv2 = movie.getV2();
        holder.tvTwo.setText(tv2.substring(0, 4) + "-" + tv2.substring(4, 6) + "-" + tv2.substring(6, 8));
//        holder.tvThree.setText(movie.getV3());
        holder.tvFour.setVisibility(View.GONE);


        String S3 = movie.getV3();
        String S4[] = S3.split("=>");
        String S5[] = S4[0].split(" OR ");
        String S6[] = S4[1].split(" OR ");
        holder.lnPromotion.removeAllViews();
        for (int i = 0; i < S5.length; i++) {
            TextView tv = new TextView(context);
//            tv.setTextColor(context.getResources().getColor(R.color.black));
//            tv.setBackgroundColor(context.getResources().getColor(R.color.backgroundbuy));


            if (S5.length > 1) {
                if (i == S5.length - 1) {
                    tv.setText("BUY " + S5[i]);
                } else {
                    tv.setText("BUY " + S5[i] + " OR ");
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
                    tv.setText("GIFT " + S6[i] + " OR ");
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
            holder.lnRoot.setLayoutParams(params);
        }


//        holder.tvThree.post(new Runnable() {
//            @Override
//            public void run() {
////                Log.d(TAG, "count:" + holder.tvThree.getLineCount());
//                if (holder.tvThree.getLineCount() > 3) {
//                    FrameLayout.LayoutParams params =
//                            new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                                    FrameLayout.LayoutParams.WRAP_CONTENT);
//                    holder.lnRoot.setLayoutParams(params);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}