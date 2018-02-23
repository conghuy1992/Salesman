package com.orion.salesman._infor._fragment._adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._infor._fragment._object.CpnInfo_ListT;

import java.util.List;

/**
 * Created by maidinh on 1/11/2016.
 */
public class FmlAdapter extends RecyclerView.Adapter<FmlAdapter.MyViewHolder> {
    private Context context;
    private List<CpnInfo_ListT> moviesList;
    String TAG="FmlAdapter";
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTwo, tvThree, tvBirthDay;
        public ImageView ivIcon;

        public MyViewHolder(View view) {
            super(view);
            tvBirthDay = (TextView) view.findViewById(R.id.tvBirthDay);
            tvTwo = (TextView) view.findViewById(R.id.tv2);
            tvThree = (TextView) view.findViewById(R.id.tv3);
            ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
        }
    }


    public void updateList(List<CpnInfo_ListT> moviesList) {
        if (moviesList != null) {
            this.moviesList = moviesList;
            notifyDataSetChanged();
        }

    }

    public FmlAdapter(Context context, List<CpnInfo_ListT> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_adapter_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    Bitmap Base64ToBitmap(String myImageData) {
        byte[] imageAsBytes = Base64.decode(myImageData.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CpnInfo_ListT movie = moviesList.get(position);
        String birthDay = movie.getBIRTH().trim();
        if (birthDay.length() == 8) {
//            birthDay= Const.formatDate(birthDay);
            birthDay = birthDay.substring(6, 8) + " - " + birthDay.substring(4, 6);
        }
        holder.tvBirthDay.setText(birthDay);
        holder.tvTwo.setText(movie.getEMPNM().trim());
        holder.tvThree.setText(movie.getPOSITION().trim() + " - " + movie.getDEPTNM().trim());
        if (movie.getPHOTO().trim().length() > 0)
            holder.ivIcon.setImageBitmap(Base64ToBitmap(movie.getPHOTO().trim()));
        else holder.ivIcon.setImageResource(R.drawable.img_default);
//        holder.tvThree.setText();

//        Const.longInfo(TAG,"base64:"+movie.getPHOTO().trim());
//        Const.longInfo(TAG,"convert:"+Base64ToBitmap(movie.getPHOTO().trim()));
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
