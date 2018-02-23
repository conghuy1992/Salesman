package com.orion.salesman._route._adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._route._object.Pie;
import com.orion.salesman._route._object.RouteDisplayPie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 10/8/2016.
 */
public class RouteDisplayPieAdapter extends RecyclerView.Adapter<RouteDisplayPieAdapter.MyViewHolder> {

    private List<RouteDisplayPie> moviesList;

    public List<RouteDisplayPie> getData() {
        if (moviesList == null)
            moviesList = new ArrayList<>();
        return moviesList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvOne, tvTwo, tvThree;
        public EditText edThree;
        public MyCustomEditTextListener myCustomEditTextListener;

        public MyViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            edThree = (EditText) view.findViewById(R.id.edThree);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.edThree.addTextChangedListener(myCustomEditTextListener);
        }
    }

    public RouteDisplayPieAdapter(List<RouteDisplayPie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_route_display_pie_new, parent, false);

        MyViewHolder vh = new MyViewHolder(itemView, new MyCustomEditTextListener());
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RouteDisplayPie movie = moviesList.get(position);
        holder.tvOne.setText(movie.getColumnsOne());
        holder.tvTwo.setText(movie.getColumnsTwo());
        holder.tvThree.setText("");
        holder.edThree.setVisibility(View.VISIBLE);
        holder.myCustomEditTextListener.updatePosition(position);
        String S1 = moviesList.get(position).getColumnsThree();
        if (S1.equals("0"))
            S1 = "";
        holder.edThree.setText(S1);
//        Const.setTextBlack(holder.tvOne);
//        Const.setTextBlack(holder.tvTwo);
//        if (position % 2 == 0) {
//            Const.setColor_0(holder.tvOne);
//            Const.setColor_0(holder.tvTwo);
//            Const.setColor_0(holder.tvThree);
//        } else {
//            Const.setColor_1(holder.tvOne);
//            Const.setColor_1(holder.tvTwo);
//            Const.setColor_1(holder.tvThree);
//        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//            moviesList.get(position).setColumnsThree(charSequence.toString());
            if (moviesList.get(position).isSave()) {
                if (charSequence.toString().length() == 0)
                    moviesList.get(position).setColumnsThree("0");
                else moviesList.get(position).setColumnsThree(charSequence.toString());
            } else {
                String s = charSequence.toString();
                if (s.equals("0"))
                    s = "";
                moviesList.get(position).setColumnsThree(s);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}