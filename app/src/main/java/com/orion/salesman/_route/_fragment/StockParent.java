package com.orion.salesman._route._fragment;

/**
 * Created by Huy on 15/10/2016.
 */


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.orion.salesman.R;
import com.orion.salesman._route._object.Pie;

import java.util.List;

/**
 * Created by huy on 15/10/2016.
 */

public class StockParent extends ExpandableRecyclerAdapter<StockParent.MyParentViewHolder, StockParent.MyChildViewHolder> {
    private LayoutInflater mInflater;
    String TAG = "StockParent";
    PieFragment pieFragment;


    public StockParent(Context context, List<ParentListItem> itemList,PieFragment pieFragment) {
        super(itemList);
        mInflater = LayoutInflater.from(context);
        this.pieFragment=pieFragment;

    }

    public List<?> getData() {
        return this.getParentItemList();
    }

    @Override
    public MyParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.row_pie_new, viewGroup, false);
        return new MyParentViewHolder(view);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.row_pie_new_child, viewGroup, false);
        return new MyChildViewHolder(view, new MyCustomEditTextListener(), new MyCustomEdit_2());
    }


    @Override
    public void onBindParentViewHolder(final MyParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        final Pie subcategoryParentListItem = (Pie) parentListItem;
        parentViewHolder.handler(subcategoryParentListItem);

    }


    @Override
    public void onBindChildViewHolder(MyChildViewHolder holder, int position, Object childListItem) {
        Pie movie = (Pie) childListItem;
        holder.tvOne.setText("" + movie.getColumnsFour());
        holder.tvTwo.setText(movie.getColumnsTwo());
        holder.tvProductName.setText(movie.getPRDCD());
        holder.myCustomEditTextListener.updatePosition(movie);
        String S1 = movie.getColumnsThree();
        if (S1.equals("0"))
            S1 = "";
        holder.mEditText.setText(S1);
        holder.myCustomEdit_2.updatePosition(movie);
        String S2 = movie.getGOODS();
        if (S2.equals("0"))
            S2 = "";
        holder.etGoods.setText(S2);

    }

    public class MyParentViewHolder extends ParentViewHolder {

        public TextView tvOne;
        public LinearLayout lnDetails;

        public MyParentViewHolder(View itemView) {
            super(itemView);
            tvOne = (TextView) itemView.findViewById(R.id.tvOne);
            lnDetails = (LinearLayout) itemView.findViewById(R.id.lnDetails);
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
                        pieFragment.ScrollEndList(subcategoryParentListItem.getColumnsOne());

                    }
                }
            });

        }
    }

    public class MyChildViewHolder extends ChildViewHolder {
        public TextView tvOne, tvTwo, tvProductName;
        public EditText mEditText;
        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomEdit_2 myCustomEdit_2;
        public EditText etGoods;

        public MyChildViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener, MyCustomEdit_2 myCustomEdit_2) {
            super(view);
            tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            this.etGoods = (EditText) view.findViewById(R.id.etGoods);
            this.myCustomEdit_2 = myCustomEdit_2;
            this.etGoods.addTextChangedListener(myCustomEdit_2);
            this.mEditText = (EditText) view.findViewById(R.id.etInput);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.mEditText.addTextChangedListener(myCustomEditTextListener);
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
                    movie.setColumnsThree("0");
                else movie.setColumnsThree(charSequence.toString());
            } else {
                String s = charSequence.toString();
                if (s.equals("0"))
                    s = "";
                movie.setColumnsThree(s);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class MyCustomEdit_2 implements TextWatcher {
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
                    movie.setGOODS("0");
                else movie.setGOODS(charSequence.toString());
            } else {
                String s = charSequence.toString();
                if (s.equals("0"))
                    s = "";
                movie.setGOODS(s);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}