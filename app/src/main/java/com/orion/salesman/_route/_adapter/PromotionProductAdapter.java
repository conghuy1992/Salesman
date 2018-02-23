package com.orion.salesman._route._adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orion.salesman.R;
import com.orion.salesman._adaper.PromotionSP;
import com.orion.salesman._class.Const;
import com.orion.salesman._object.PromotionCal;
import com.orion.salesman._route._object.PromotionProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 16/8/2016.
 */
public class PromotionProductAdapter extends RecyclerView.Adapter<PromotionProductAdapter.MyViewHolder> {
    String TAG = "PromotionProductAdapter";
    private List<PromotionProduct> moviesList;
    private Context context;

    public List<PromotionProduct> getList() {
        return moviesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvThree, tvProductCode;
        public Spinner spPMT;
        public ImageView imgSP;
        public ImageView imgPMT;

        public MyViewHolder(View view) {
            super(view);
            tvProductCode = (TextView) view.findViewById(R.id.tvProductCode);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            spPMT = (Spinner) view.findViewById(R.id.spPMT);
            imgSP = (ImageView) view.findViewById(R.id.imgSP);
            imgPMT = (ImageView) view.findViewById(R.id.imgPMT);
        }
    }

    public void updateList(List<PromotionProduct> moviesList) {
        if (moviesList != null) {
//            for(PromotionProduct obj:moviesList)
//            {
//                Log.d(TAG,"update:"+new Gson().toJson(obj));
//            }
            this.moviesList = moviesList;
            notifyDataSetChanged();
        }

    }

    public PromotionProductAdapter(Context context, List<PromotionProduct> moviesList) {
//        for(PromotionProduct obj:moviesList)
//        {
//            Log.d(TAG,"update:"+new Gson().toJson(obj));
//        }
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_promotion_product_new, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setTextColorBlack(TextView v) {
        v.setTextColor(Color.BLACK);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PromotionProduct movie = moviesList.get(position);
        holder.spPMT.setVisibility(View.VISIBLE);

        Log.d(TAG,"movie PM:"+movie.getPMID());

        setTextColorBlack(holder.tvThree);

        Const.setColor_0(holder.tvThree);

        final ArrayList<String> arrayList = new ArrayList<>();

        try {
            String S1[] = movie.getV2().split(" => ");
            String S2[] = S1[1].split(" OR ");
            for (int i = 0; i < S2.length; i++) {
                arrayList.add(S1[0] + " => " + S2[i]);
            }
        } catch (Exception e) {
            arrayList.clear();
            arrayList.add(movie.getV2());
            e.printStackTrace();
        }


        if (arrayList.size() == 1) {
            holder.imgSP.setVisibility(View.GONE);
//            holder.spPMT.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    return true;
//                }
//            });
        } else {
            holder.imgSP.setVisibility(View.VISIBLE);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.sp_promotion, arrayList);
        adapter.setDropDownViewResource(R.layout.sp_pmt_drop);

//        PromotionSP adapter = new PromotionSP(context, LIST);
        holder.spPMT.setAdapter(adapter);
        holder.spPMT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                holder.tvProductCode.setText("" + movie.getItemList().get(pos).getPRDCD());
                holder.tvThree.setText("" + movie.getItemList().get(pos).getQTY());
                holder.tvOne.setText(movie.getItemList().get(pos).getPRDNM());
                holder.imgPMT.setVisibility(movie.getItemList().get(pos).isDS() ? View.VISIBLE : View.GONE);
                movie.setPositionChoose(pos);
                Log.d(TAG,"PMID:"+movie.getPMID()+" - "+movie.getItemList().get(pos).isDS() +" - "+ movie.getItemList().get(pos).getQTY()+" - "+movie.getItemList().get(pos).getPRDCD());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
