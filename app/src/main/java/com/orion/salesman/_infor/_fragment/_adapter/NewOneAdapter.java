package com.orion.salesman._infor._fragment._adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orion.salesman.DocumentActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._infor._fragment._object.OneNew;

import java.util.List;

/**
 * Created by maidinh on 19/10/2016.
 */
public class NewOneAdapter extends RecyclerView.Adapter<NewOneAdapter.MyViewHolder> {
    private Context context;
    private List<OneNew> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree,tvRead;
        public FrameLayout frLayout;

        public MyViewHolder(View view) {
            super(view);
            tvRead = (TextView) view.findViewById(R.id.tvRead);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            frLayout = (FrameLayout) view.findViewById(R.id.frLayout);
        }
    }

    public void updateList(List<OneNew> moviesList) {
        if (moviesList != null) {
            this.moviesList = moviesList;
            notifyDataSetChanged();
        }

    }

    public NewOneAdapter(Context context, List<OneNew> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_news_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final OneNew movie = moviesList.get(position);
        String date = movie.getV9();
        if (date.length() == 8) {
            date = Const.formatDate(date);
        }
        holder.tvOne.setText(date);
        holder.tvTwo.setText(movie.getV11());
        holder.tvThree.setText(movie.getV4());
        holder.frLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DocumentActivity.class);
                intent.putExtra(Const.OneNew, new Gson().toJson(movie));
                context.startActivity(intent);
            }
        });
        if(movie.isFlag())
            holder.tvRead.setVisibility(View.VISIBLE);
        else
            holder.tvRead.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}