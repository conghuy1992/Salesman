package com.orion.salesman._class;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.orion.salesman.R;

/**
 * Created by maidinh on 12/9/2016.
 */
public class RobotoTextViewBold extends TextView {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RobotoTextViewBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public RobotoTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public RobotoTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public RobotoTextViewBold(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);

            try {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Bold.ttf");
                setTypeface(myTypeface);
            } catch (Exception e) {
                e.printStackTrace();
            }

            a.recycle();
        }
    }
}