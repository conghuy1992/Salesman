package com.orion.salesman._fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.orion.salesman.R;

/**
 * Created by maidinh on 14/10/2016.
 */
public class FragmentImage extends Fragment {
    String URL = "";
    String TAG = "FragmentImage";

    @SuppressLint("ValidFragment")
    public FragmentImage(String URL) {
        this.URL = URL;
    }

    public FragmentImage() {

    }

    ImageView IMG;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        Log.d(TAG, "URL:" + URL);
        IMG = (ImageView) v.findViewById(R.id.IMG);
        Glide.with(this)
                .load(URL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        IMG.setScaleType(ImageView.ScaleType.CENTER);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        IMG.setScaleType(ImageView.ScaleType.FIT_XY);
                        return false;
                    }
                })
                .into(new GlideDrawableImageViewTarget(IMG) {
                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        IMG.setImageResource(R.drawable.warning);
                    }
                });
        return v;
    }
}
