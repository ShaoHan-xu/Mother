package com.laowo.mother;

import android.app.Application;

import com.laowo.mother.imageloader.WooImageLoader;

/**
 * Created by xsh on 2016/8/12.
 */
public class MoApplication extends Application {

    public static MoApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        WooImageLoader.init(this.getApplicationContext());
    }

}
