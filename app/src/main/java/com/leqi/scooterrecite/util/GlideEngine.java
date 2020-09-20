package com.leqi.scooterrecite.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.engine.ImageEngine;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Glide4.x的加载图片引擎实现,单例模式
 * Glide4.x的缓存机制更加智能，已经达到无需配置的境界。如果使用Glide3.x，需要考虑缓存机制。
 * Created by huan on 2018/1/15.
 */

public class GlideEngine implements ImageEngine {
    //单例
    private static GlideEngine instance = null;
    //单例模式，私有构造方法
    private GlideEngine() {
    }
    //获取单例
    public static GlideEngine getInstance() {
        if (null == instance) {
            synchronized (GlideEngine.class) {
                if (null == instance) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }


    @Override
    public void loadPhoto(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView) {
        Glide.with(context).load(uri).transition(withCrossFade()).into(imageView);
    }

    @Override
    public void loadGifAsBitmap(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        Glide.with(context).asBitmap().load(gifUri).into(imageView);
    }

    @Override
    public void loadGif(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {
        Glide.with(context).asGif().load(gifUri).transition(withCrossFade()).into(imageView);
    }

    @Override
    public Bitmap getCacheBitmap(@NonNull Context context, @NonNull Uri uri, int width, int height) throws Exception {
        return Glide.with(context).asBitmap().load(uri).submit(width, height).get();
    }
}
