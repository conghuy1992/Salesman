package com.orion.salesman._route._adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.OrderActivity;
import com.orion.salesman.R;
import com.orion.salesman._class.Const;
import com.orion.salesman._route._fragment.RouteOrderFragmentSnack;
import com.orion.salesman._route._object.Snack;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 16/10/2016.
 */

public class OrderParentSnack extends ExpandableRecyclerAdapter<OrderParentSnack.MyParentViewHolder, OrderParentSnack.MyChildViewHolder> {
    private LayoutInflater mInflater;
    String TAG = "OrderParent";
    Context context;
    RouteOrderFragmentSnack instance;

    public OrderParentSnack(Context context, List<ParentListItem> itemList, RouteOrderFragmentSnack instance) {
        super(itemList);
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.instance = instance;
    }

    public List<?> getData() {
        return this.getParentItemList();
    }

    @Override
    public MyParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.row_route_order_fragment_pie, viewGroup, false);
        return new MyParentViewHolder(view);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.row_route_order_fragment_pie_new, viewGroup, false);
        return new MyChildViewHolder(view, new MyCustomEditTextListener());
    }


    @Override
    public void onBindParentViewHolder(final MyParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        final Snack subcategoryParentListItem = (Snack) parentListItem;
        parentViewHolder.handler(subcategoryParentListItem);
    }


    @Override
    public void onBindChildViewHolder(final MyChildViewHolder holder, int position, Object childListItem) {
        final Snack movie = (Snack) childListItem;
        if (movie.ispmt())
            holder.imgPMT.setVisibility(View.VISIBLE);
        else holder.imgPMT.setVisibility(View.GONE);
        holder.tvOne.setText(movie.getColumnsTwo());
        String tv_3 = "";
        String tv_2 = "";

//        if(movie.getPRDCD().equals("A512000"))
//        {
//            Log.d(TAG,"getBXCEQTY:"+movie.getBXCEQTY());
//            Log.d(TAG,"getBXEAQTY:"+movie.getBXEAQTY());
//            Log.d(TAG,"getCSEEAQTY:"+movie.getCSEEAQTY());
//        }

        if (movie.getEASALE().equals("Y")
                && !Const.formatAMT(Long.parseLong(Const.getPartInt(movie.getEAPRICE()))).equals("0")) {
            tv_3 = Const.formatAMT(Long.parseLong(Const.getPartInt(movie.getEAPRICE()))) + "\n/" + context.getResources().getString(R.string.EA);
            tv_2 = movie.getBXEAQTY();
        } else if (movie.getCASESALE().equalsIgnoreCase("Y")
                && !Const.formatAMT(Long.parseLong(Const.getPartInt(movie.getCASEPRICE()))).equals("0")) {
            tv_3 = Const.formatAMT(Long.parseLong(Const.getPartInt(movie.getCASEPRICE()))) + "\n/" + context.getResources().getString(R.string.CsSNack);
            tv_2 = movie.getBXCEQTY();
        } else {
            tv_3 = Const.formatAMT(Long.parseLong(Const.getPartInt(movie.getSTOREPRICE()))) + "\n/" + context.getResources().getString(R.string.Bx);
            tv_2 = "1";
        }
        holder.tvTwo.setText(tv_2);
        holder.tvThree.setText(tv_3);
        holder.tvProductCode.setText(movie.getPRDCD());
        String stock = movie.getColumnsThree();
        if (stock.length() == 0)
            stock = "0";
        if (tv_3.contains(context.getResources().getString(R.string.CsSNack))) {
            if (!stock.equals("0")) {
                if (!movie.getCSEEAQTY().equals("0")) {
                    float a = (float) Integer.parseInt(stock) / Integer.parseInt(movie.getCSEEAQTY());
                    stock = Const.RoundNumber("" + a);
                }
            }
        } else if (tv_3.contains(context.getResources().getString(R.string.Bx))) {
            if (!stock.equals("0")) {
                if (!movie.getBXEAQTY().equals("0")) {
                    float a = (float) Integer.parseInt(stock) / Integer.parseInt(movie.getBXEAQTY());
                    stock = Const.RoundNumber("" + a);
                }
            }
        }

        holder.tvFour.setText(stock);
//        Const.setTextBlack(holder.tvOne);
//        Const.setTextBlack(holder.tvTwo);
//        Const.setTextBlack(holder.tvThree);
//        Const.setTextBlack(holder.tvFour);
        holder.imgPMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.TPRDCD = movie.getTPRDCD();
                OrderActivity.instance.gotoPromotionInfo();
            }
        });
        holder.myCustomEditTextListener.updatePosition(movie);
        String S1 = movie.getColumnsSix();
        if (S1.equals("0"))
            S1 = "";
        holder.edOrder.setText(S1);
        holder.checkBox.setChecked(movie.isCheck());
        holder.frSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkBox.setChecked(holder.checkBox.isChecked() ? false : true);
                movie.setIsCheck(holder.checkBox.isChecked());
                setCheckBoxParent(holder.checkBox.isChecked());
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setIsCheck(holder.checkBox.isChecked());
                setCheckBoxParent(holder.checkBox.isChecked());
            }
        });
    }

    void setCheckBoxParent(boolean c) {
        if (!c)
            instance.setCB(c);
        else
            instance.setCB(checkAll());
    }

    boolean checkAll() {
        List<Snack> moviesList = new ArrayList<>();
        String json = new Gson().toJson(getParentItemList());
        Type listType = new TypeToken<ArrayList<Snack>>() {
        }.getType();
        moviesList = new Gson().fromJson(json, listType);
        for (int i = 0; i < moviesList.size(); i++) {
            Snack s = moviesList.get(i);
            List<Snack> aPieList = s.getArrSnacks();
            for (Snack p : aPieList) {
                if (!p.isCheck())
                    return false;
            }
        }
        return true;
    }


    public class MyParentViewHolder extends ParentViewHolder {

        public TextView tvOne;
        public LinearLayout lnDetails;

        public MyParentViewHolder(View itemView) {
            super(itemView);
            tvOne = (TextView) itemView.findViewById(R.id.tvOne);
            lnDetails = (LinearLayout) itemView.findViewById(R.id.lnClick);
        }

        public void handler(final Snack subcategoryParentListItem) {
            if (subcategoryParentListItem.isCheck())
                expandView();
            else collapseView();
            tvOne.setText(subcategoryParentListItem.getColumnsOne());
            lnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subcategoryParentListItem.setIsCheck(subcategoryParentListItem.isCheck() ? false : true);
                    if (isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                        instance.ScrollEndList(subcategoryParentListItem.getColumnsOne());
                    }
                }
            });
        }
    }

    public class MyChildViewHolder extends ChildViewHolder {
        public TextView tvOne, tvTwo, tvThree, tvFour, tvFive, tvSix,tvProductCode;
        FrameLayout frSeven;
        public ImageView imgPMT;
        public CheckBox checkBox;
        public EditText edOrder;
        public MyCustomEditTextListener myCustomEditTextListener;

        public MyChildViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            tvProductCode= (TextView) view.findViewById(R.id.tvProductCode);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            tvFive = (TextView) view.findViewById(R.id.tvFive);
            tvSix = (TextView) view.findViewById(R.id.tvSix);
            frSeven = (FrameLayout) view.findViewById(R.id.frSeven);
            imgPMT = (ImageView) view.findViewById(R.id.imgPMT);
            checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            edOrder = (EditText) view.findViewById(R.id.edOrder);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.edOrder.addTextChangedListener(myCustomEditTextListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private Snack movie;

        public void updatePosition(Snack movie) {
            this.movie = movie;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (movie.isSave()) {
                if (charSequence.toString().length() == 0)
                    movie.setColumnsSix("0");
                else movie.setColumnsSix(charSequence.toString());
            } else {
                String s = charSequence.toString();
                if (s.equals("0"))
                    s = "";
                movie.setColumnsSix(s);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}
