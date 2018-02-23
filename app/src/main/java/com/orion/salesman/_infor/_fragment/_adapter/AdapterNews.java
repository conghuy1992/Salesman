package com.orion.salesman._infor._fragment._adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._infor._fragment._object.News;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.MyViewHolder> {
    private Context context;
    private List<News> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView S1;
        public ImageView PICTURE;

        public MyViewHolder(View view) {
            super(view);
            S1 = (TextView) view.findViewById(R.id.S1);
            PICTURE = (ImageView) view.findViewById(R.id.PICTURE);
        }
    }


    public AdapterNews(Context context, List<News> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_news, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        News movie = moviesList.get(position);
        holder.S1.setText(movie.getS1());
        if (position == 0)
            holder.PICTURE.setImageResource(R.drawable.img_test_3);
        else if (position == 1)
            holder.PICTURE.setImageResource(R.drawable.img_test_1);
        else if (position == 2)
            holder.PICTURE.setImageResource(R.drawable.img_test_2);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}