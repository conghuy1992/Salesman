package com.orion.salesman._route._adapter;

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
import com.orion.salesman._route._fragment.SnackFragment;
import com.orion.salesman._route._object.Snack;

import java.util.List;

/**
 * Created by Huy on 16/10/2016.
 */

public class StockParentSnack extends ExpandableRecyclerAdapter<StockParentSnack.MyParentViewHolder, StockParentSnack.MyChildViewHolder> {
    private LayoutInflater mInflater;
    String TAG = "StockParentSnack";
    SnackFragment snackFragment;
    public StockParentSnack(Context context, List<ParentListItem> itemList,SnackFragment snackFragment) {
        super(itemList);
        mInflater = LayoutInflater.from(context);
        this.snackFragment=snackFragment;
    }

    public List<?> getData() {
        return this.getParentItemList();
    }

    @Override
    public MyParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.row_snack_new, viewGroup, false);
        return new MyParentViewHolder(view);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.row_snack_new_child, viewGroup, false);
        return new MyChildViewHolder(view, new MyCustomEditTextListener(), new MyCustomEditFour());
    }


    @Override
    public void onBindParentViewHolder(final MyParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        final Snack subcategoryParentListItem = (Snack) parentListItem;
        parentViewHolder.handler(subcategoryParentListItem);
    }


    @Override
    public void onBindChildViewHolder(MyChildViewHolder holder, int position, Object childListItem) {
        Snack movie = (Snack) childListItem;

//        if(movie.getPRDCD().equals("A512000"))
//        {
//            Log.d(TAG,"getBXCEQTY:"+movie.getBXCEQTY());
//            Log.d(TAG,"getBXEAQTY:"+movie.getBXEAQTY());
//            Log.d(TAG,"getCSEEAQTY:"+movie.getCSEEAQTY());
//
//        }

        holder.tvOne.setText("" + movie.getColumnsFive());
        holder.tvTwo.setText(movie.getColumnsTwo());
        holder.tvProductCode.setText(movie.getPRDCD());
        holder.myCustomEditTextListener.updatePosition(movie);
        String S1 = movie.getColumnsThree();
        if (S1.equals("0"))
            S1 = "";
        holder.edStock.setText(S1);

        holder.myCustomEditFour.updatePosition(movie);
        String S2 = movie.getColumnsFour();
        if (S2.equals("0"))
            S2 = "";
        holder.edGood.setText(S2);
    }

    public class MyParentViewHolder extends ParentViewHolder {

        public TextView tvOne;
        public LinearLayout lnClick;

        public MyParentViewHolder(View itemView) {
            super(itemView);
            tvOne = (TextView) itemView.findViewById(R.id.tvOne);
            lnClick = (LinearLayout) itemView.findViewById(R.id.lnClick);
        }

        public void handler(final Snack subcategoryParentListItem) {
            if (subcategoryParentListItem.isCheck())
                expandView();
            else collapseView();
            tvOne.setText(subcategoryParentListItem.getColumnsOne());
            lnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subcategoryParentListItem.setIsCheck(subcategoryParentListItem.isCheck() ? false : true);
                    if (isExpanded()) {
                        collapseView();
                    } else {
                        expandView();
                        snackFragment.ScrollEndList(subcategoryParentListItem.getColumnsOne());
                    }
                }
            });
        }
    }

    public class MyChildViewHolder extends ChildViewHolder {
        public TextView tvOne, tvTwo,tvProductCode;
        public EditText edStock, edGood;
        public MyCustomEditTextListener myCustomEditTextListener;
        public MyCustomEditFour myCustomEditFour;

        public MyChildViewHolder(View view, MyCustomEditTextListener myCustomEditTextListener, MyCustomEditFour myCustomEditFour) {
            super(view);
            tvProductCode = (TextView) view.findViewById(R.id.tvProductCode);
            tvOne = (TextView) view.findViewById(R.id.tvOne);
            tvTwo = (TextView) view.findViewById(R.id.tvTwo);
            edStock = (EditText) view.findViewById(R.id.edStock);
            edGood = (EditText) view.findViewById(R.id.edGood);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.edStock.addTextChangedListener(myCustomEditTextListener);
            this.myCustomEditFour = myCustomEditFour;
            this.edGood.addTextChangedListener(myCustomEditFour);
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

    private class MyCustomEditFour implements TextWatcher {
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
                    movie.setColumnsFour("0");
                else movie.setColumnsFour(charSequence.toString());
            } else {
                String s = charSequence.toString();
                if (s.equals("0"))
                    s = "";
                movie.setColumnsFour(s);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}