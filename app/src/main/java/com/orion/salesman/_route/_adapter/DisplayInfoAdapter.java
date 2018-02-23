package com.orion.salesman._route._adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._route._object.DisplayInfo;

import java.util.List;

/**
 * Created by maidinh on 4/8/2016.
 */
public class DisplayInfoAdapter extends RecyclerView.Adapter<DisplayInfoAdapter.MyViewHolder> {

    private List<DisplayInfo> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
        }
    }

    public DisplayInfoAdapter(List<DisplayInfo> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_display_info, parent, false);

        return new MyViewHolder(itemView);
    }

    void settextNull(TextView v) {
        v.setText("");
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Const.setTextBlack(holder.tvOne);
        if (position % 2 == 0) {
            Const.setColor_0(holder.tvOne);
            Const.setColor_0(holder.tvTwo);
            Const.setColor_0(holder.tvThree);
            Const.setColor_0(holder.tvFour);
        } else {
            Const.setColor_1(holder.tvOne);
            Const.setColor_1(holder.tvTwo);
            Const.setColor_1(holder.tvThree);
            Const.setColor_1(holder.tvFour);
        }
        DisplayInfo movie = moviesList.get(position);
        settextNull(holder.tvOne);
        settextNull(holder.tvTwo);
        settextNull(holder.tvThree);
        settextNull(holder.tvFour);
        holder.tvOne.setText(movie.getV2());
        String V3 = movie.getV3();
        if (V3.equalsIgnoreCase("FACE")) {
            holder.tvTwo.setText(movie.getV4());
        } else if (V3.equalsIgnoreCase("HANGER")) {
            holder.tvThree.setText(movie.getV4());
        } else if (V3.equalsIgnoreCase("STANDEE")) {
            holder.tvFour.setText(movie.getV4());
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}