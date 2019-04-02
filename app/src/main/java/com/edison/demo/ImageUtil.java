package com.edison.demo;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


public class ImageUtil {

    public static void displayPortrait(Context context, String url, final ImageView imageView) {
        if (url == null) {
            imageView.setImageResource(R.mipmap.default_image);
            return;

        } else if (url.trim().isEmpty()) {
            imageView.setImageResource(R.mipmap.default_image);
            return;
        }

        //我这里是先获取屏幕的宽高，然后把屏幕的宽设为imageView的宽。

        WindowManager wm = (WindowManager) context

                .getSystemService(Context.WINDOW_SERVICE);

        assert wm != null;
        int width = wm.getDefaultDisplay().getWidth();

        int height = wm.getDefaultDisplay().getHeight();

        ViewGroup.LayoutParams params = imageView.getLayoutParams();

        params.width = width;

        imageView.setLayoutParams(params);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.default_image)
                .error(R.mipmap.default_image)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                        //首先设置imageView的ScaleType属性为ScaleType.FIT_XY，
                        // 让图片不按比例缩放，把图片塞满整个View。

                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {

                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                        }

                        //得到当前imageView的宽度（设置的是屏幕宽度），
                        // 获取到imageView与图片宽的比例，
                        // 然后通过这个比例去设置imageView的高

                        ViewGroup.LayoutParams params = imageView.getLayoutParams();

                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();

                        float scale = (float) vw / (float) resource.getIntrinsicWidth();

                        int vh = Math.round(resource.getIntrinsicHeight() * scale);

                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();

                        imageView.setLayoutParams(params);

                        return false;
                    }

                })
                .thumbnail(Glide.with(context).load(url))
                .into(imageView);
    }

}
