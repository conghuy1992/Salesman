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
import com.orion.salesman._route._fragment.RouteOrderFragmentPie;
import com.orion.salesman._route._object.Pie;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huy on 16/10/2016.
 */

public class OrderParent extends ExpandableRecyclerAdapter<OrderParent.MyParentViewHolder, OrderParent.MyChildViewHolder> {
    private LayoutInflater mInflater;
    String TAG = "OrderParent";
    Context context;

    RouteOrderFragmentPie instance;

    public OrderParent(Context context, List<ParentListItem> itemList, RouteOrderFragmentPie instance) {
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
        final Pie subcategoryParentListItem = (Pie) parentListItem;
        parentViewHolder.handler(subcategoryParentListItem);
    }


    @Override
    public void onBindChildViewHolder(final MyChildViewHolder holder, int position, Object childListItem) {
        final Pie movie = (Pie) childListItem;
        if (movie.ispmt())
            holder.imgPMT.setVisibility(View.VISIBLE);
        else holder.imgPMT.setVisibility(View.GONE);
        holder.tvOne.setText(movie.getColumnsTwo());
        String tv_3 = "";
        String tv_2 = "";
//        if(movie.getTPRDCD().equals("T000010")){
//            Log.d(TAG,movie.getColumnsTwo()+" EA:"+movie.getEAPRICE()+" CS:"+movie.getCASEPRICE()+" BX:"+movie.getSTOREPRICE()+" EASALE:"+movie.getEASALE()+" CASESALE:"+movie.getCASESALE());
//        }
        if (movie.getEASALE().equals("Y")) {
            tv_3 = Const.formatAMT(Long.parseLong(Const.getPartInt(movie.getEAPRICE()))) + "\n/" + context.getResources().getString(R.string.EA);
            tv_2 = movie.getBXEAQTY();
        } else if (movie.getCASESALE().equalsIgnoreCase("Y")) {
            tv_3 = Const.formatAMT(Long.parseLong(Const.getPartInt(movie.getCASEPRICE()))) + "\n/" + context.getResources().getString(R.string.CsPie);
            tv_2 = movie.getBXCEQTY();
        } else {
            tv_3 = Const.formatAMT(Long.parseLong(Const.getPartInt(movie.getSTOREPRICE()))) + "\n/" + context.getResources().getString(R.string.Bx);
            tv_2 = "1";
        }
        holder.tvTwo.setText(tv_2);
        holder.tvThree.setText(tv_3);
        holder.tvProductCode.setText(movie.getPRDCD());

//        if (movie.getBOXSALE().equalsIgnoreCase("Y") || movie.getCASESALE().equalsIgnoreCase("Y"))
//            tv_3=
//            holder.tvThree.setText(tv_3);

        String stock = movie.getColumnsThree();
        if (stock.equals(""))
            stock = "0";


        if (tv_3.contains(context.getResources().getString(R.string.EA))) {
            if (!stock.equals("0")) {
                if (!movie.getCSEEAQTY().equals("0")) {
                    float a = (float) Integer.parseInt(stock) / Integer.parseInt(movie.getCSEEAQTY());
                    stock = Const.RoundNumber("" + a);
                }
            }
        } else if (tv_3.contains(context.getResources().getString(R.string.Bx))) {
            if (!stock.equals("0")) {
                if (!movie.getBXCSEQTY().equals("0")) {
                    float a = (float) Integer.parseInt(stock) / Integer.parseInt(movie.getBXCSEQTY());
                    stock = Const.RoundNumber("" + a);
                }
            }
        }

        holder.tvFour.setText(stock);

        holder.imgPMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.TPRDCD = movie.getTPRDCD();
                OrderActivity.instance.gotoPromotionInfo();
            }
        });
        holder.myCustomEditTextListener.updatePosition(movie);
        String S1 = movie.getColumnsFive();
        if (S1.equals("0"))
            S1 = "";
        holder.edOrder.setText(S1);
        holder.checkbox.setChecked(movie.isCheck());

        holder.frSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.checkbox.setChecked(holder.checkbox.isChecked() ? false : true);
                movie.setIsCheck(holder.checkbox.isChecked());
                setCheckBoxParent(holder.checkbox.isChecked());
            }
        });
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setIsCheck(holder.checkbox.isChecked());
                setCheckBoxParent(holder.checkbox.isChecked());
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
        List<Pie> moviesList = new ArrayList<>();
        String json = new Gson().toJson(getParentItemList());
        Type listType = new TypeToken<ArrayList<Pie>>() {
        }.getType();
        moviesList = new Gson().fromJson(json, listType);
        for (int i = 0; i < moviesList.size(); i++) {
            Pie s = moviesList.get(i);
            List<Pie> aPieList = s.getArrPies();
            for (Pie p : aPieList) {
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

        public void handler(final Pie subcategoryParentListItem) {
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
        public TextView tvOne, tvTwo, tvThree, tvFour,tvProductCode;
        public ImageView imgPMT;
        public CheckBox checkbox;
        public EditText edOrder;
        public FrameLayout frSeven;
        public MyCustomEditTextListener myCustomEditTextListener;

        public MyChildViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener) {
            super(view);
            tvProductCode = (TextView) view.findViewById(R.id.tvProductCode);
            checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            tvThree = (TextView) view.findViewById(R.id.tvThree);
            tvFour = (TextView) view.findViewById(R.id.tvFour);
            frSeven = (FrameLayout) view.findViewById(R.id.frSeven);
            imgPMT = (ImageView) view.findViewById(R.id.imgPMT);
            edOrder = (EditText) view.findViewById(R.id.edOrder);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.edOrder.addTextChangedListener(myCustomEditTextListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private Pie movie;

        public void updatePosition(Pie movie) {
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
                    movie.setColumnsFive("0");
                else movie.setColumnsFive(charSequence.toString());
            } else {
                String s = charSequence.toString();
                if (s.equals("0"))
                    s = "";
                movie.setColumnsFive(s);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

}