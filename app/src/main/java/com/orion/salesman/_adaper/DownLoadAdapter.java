package com.orion.salesman._adaper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orion.salesman.DownLoadActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._download._fragment.ObjectDownload;

import java.util.List;

/**
 * Created by maidinh on 3/11/2016.
 */
public class DownLoadAdapter extends RecyclerView.Adapter<DownLoadAdapter.MyViewHolder> {
    private String TAG = "adapterDownload";
    private List<ObjectDownload> moviesList;
    private Context context;
    private DownLoadActivity instance;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour;
        public CheckBox checkBox, cbTitle;
        public FrameLayout frThree;
        public ImageView IMG_CHECK;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            checkBox = (CheckBox) view.findViewById(R.id.cb);
            cbTitle = (CheckBox) view.findViewById(R.id.cbTitle);
            frThree = (FrameLayout) view.findViewById(R.id.frThree);
            IMG_CHECK = (ImageView) view.findViewById(R.id.IMG_CHECK);
        }
    }


    public DownLoadAdapter(Context context, List<ObjectDownload> moviesList, DownLoadActivity instance) {
        this.context = context;
        this.moviesList = moviesList;
        this.instance = instance;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_download, parent, false);

        return new MyViewHolder(itemView);
    }

    void setbackground(View v) {
//        v.setBackgroundColor(Color.parseColor("#dce6f2"));
        v.setBackgroundColor(Color.parseColor("#e6b9b8"));
    }

    public void REMOVE_ALL_CHECK_DOWNLOAD() {
        for (int i = 0; i < moviesList.size(); i++) {
            moviesList.get(i).setIsDownload(false);
        }
        notifyDataSetChanged();
    }

    public void updateDownLoadSuccess(int P) {
        moviesList.get(P).setIsDownload(true);
        notifyItemChanged(P);
    }

    void roundBackground(View v) {
        v.setBackgroundResource(R.drawable.route_button_download_first);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ObjectDownload movie = moviesList.get(position);
        holder.IMG_CHECK.setVisibility(movie.isDownload() ? View.VISIBLE : View.GONE);
        holder.tvThree.setVisibility(View.VISIBLE);

        holder.tvFour.setVisibility(View.GONE);
        Const.setColor_0(holder.tvTwo);
        Const.setColor_0(holder.frThree);
        Const.setTextBlack(holder.tvOne);
        Const.setTextBlack(holder.tvTwo);
        setbackground(holder.tvOne);
        roundBackground(holder.tvThree);
        holder.cbTitle.setVisibility(View.GONE);
        holder.checkBox.setVisibility(View.VISIBLE);
        holder.checkBox.setChecked(movie.isCheck());
        holder.tvTwo.setText(movie.getV2());
        holder.tvOne.setText(movie.getV1());
        holder.checkBox.setEnabled(false);
        holder.checkBox.setChecked(true);
        holder.tvThree.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

}
