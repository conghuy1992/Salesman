package com.orion.salesman._infor._fragment._adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orion.salesman.MessageActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._infor._fragment._fragment.fragment_three;
import com.orion.salesman._infor._fragment._object.ObjA0004;

import java.util.List;

/**
 * Created by maidinh on 28/10/2016.
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyViewHolder> {
    private Context context;
    private List<ObjA0004> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvRead;
        public FrameLayout frLayout;

        public MyViewHolder(View view) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvRead = (TextView) view.findViewById(R.id.tvRead);
            frLayout = (FrameLayout) view.findViewById(R.id.frLayout);
        }
    }

    public void updateAtPosition(int pos) {
        if (moviesList != null && moviesList.size() > pos) {
            moviesList.get(pos).setIsRead("1");
            notifyItemChanged(pos);
        }
    }

    public void updateList(List<ObjA0004> moviesList) {
        if (moviesList != null) {
            this.moviesList = moviesList;
            notifyDataSetChanged();
        }

    }

    public MsgAdapter(Context context, List<ObjA0004> moviesList) {
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
        final ObjA0004 movie = moviesList.get(position);
        String date = movie.getV1();
        if (date.length() == 8) {
            date = Const.formatDate(date);
        }
        holder.tvOne.setText(date);
        holder.tvTwo.setText(movie.getV4());
        holder.tvThree.setText(movie.getV5());
        holder.tvRead.setVisibility(movie.isRead().equals("1") ? View.GONE : View.VISIBLE);
        holder.frLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_three.MsgId = movie.getId();
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra(Const.MSG, new Gson().toJson(movie));
                intent.putExtra(Const.posMsg, position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}