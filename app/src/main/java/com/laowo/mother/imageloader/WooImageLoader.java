package com.laowo.mother.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.laowo.mother.R;
import com.laowo.mother.common.Logger;
import com.laowo.mother.qcloud.QCOSManager;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;

/**
 * 图片加载类
 * Created by xsh on 2016/7/15.
 */
public class WooImageLoader {

    private static Context mContext;
    public static DisplayImageOptions.Builder mBuilder;
    public static DisplayImageOptions mOptions_normal;
    public static DisplayImageOptions mOptions_round;
    public static DisplayImageOptions mOptions_circle;

    public static void init(Context context) {
        mContext = context;
        initUIL();
    }

    private static void initUIL() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
//                .threadPoolSize(3)
                .memoryCache(new UsingFreqLimitedMemoryCache(8 * 1024 * 1024))
                .diskCacheFileCount(1200)
                .diskCacheFileNameGenerator(new FileNameGenerator() {

                    @Override
                    public String generate(String uri) {
                        if (uri.contains("?sign=")) {
                            uri = uri.substring(0, uri.lastIndexOf("?sign="));
                        } else if (uri.contains("&sign=")) {
                            uri = uri.substring(0, uri.lastIndexOf("&sign="));
                        }
                        return String.valueOf(uri.hashCode());
                    }
                })
                .build();
        ImageLoader.getInstance().init(config);

        mBuilder = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true)
                .resetViewBeforeLoading(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true);

        mOptions_normal = mBuilder.showImageOnLoading(R.drawable.ic_image_onloading)
                .showImageOnFail(R.drawable.ic_image_onloading)
                .build();

        mOptions_round = mBuilder.showImageOnLoading(R.drawable.ic_image_onloading)
                .showImageOnFail(R.drawable.ic_image_onloading)
                .displayer(new RoundedBitmapDisplayer(10))
                .build();

        mOptions_circle = mBuilder.showImageOnLoading(R.drawable.ic_image_onloading)
                .showImageOnFail(R.drawable.ic_image_onloading)
                .displayer(new RoundedBitmapDisplayer(360))
                .build();

    }

    /**
     * 显示圆形图片
     *
     * @param url 图片URL
     * @param iv  显示图片的imageview
     */
    public static void displayCircleImg(String url, final ImageView iv) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_circle);
            return;
        }

        if (url.contains("http://")) {
            ImageLoader.getInstance().displayImage(url + getImageSign(), iv, mOptions_circle);
        } else if (url.startsWith("drawable://")) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_circle);
        } else {
            ImageLoader.getInstance().displayImage("file://" + url, iv, mOptions_circle);
        }
    }

    /**
     * 显示圆角图片
     *
     * @param url 图片URL
     * @param iv  显示图片的imageview
     */
    public static void displayRoundImg(String url, final ImageView iv) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_round);
            return;
        }

        if (url.contains("http://")) {
            ImageLoader.getInstance().displayImage(url + getImageSign(), iv, mOptions_round);
        } else if (url.startsWith("drawable://")) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_round);
        } else {
            ImageLoader.getInstance().displayImage("file://" + url, iv, mOptions_round);
        }
    }

    /**
     * 显示网络或本地图片/大图
     *
     * @param url 图片URL
     * @param iv  显示图片的imageview
     */
    public static void displayImg(String url, final ImageView iv) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_normal);
            return;
        }

        if (url.contains("http://")) {
            ImageLoader.getInstance().displayImage(url + getImageSign(), iv, mOptions_normal);
        } else if (url.startsWith("drawable://")) {
            ImageLoader.getInstance().displayImage(url, iv);
        } else {
            ImageLoader.getInstance().displayImage("file://" + url, iv,
                    mOptions_normal);
        }
    }

    /**
     * 显示网络或本地图片/大图
     *
     * @param url        图片URL
     * @param iv         显示图片的imageview
     * @param defaultImg 加载失败显示的图片
     */
    public static void displayImage(String url, final ImageView iv, int defaultImg) {
        DisplayImageOptions options_normal = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(defaultImg)
                .showImageOnFail(defaultImg)
                .showImageForEmptyUri(defaultImg)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true)
                .cacheOnDisk(true).build();

        if (TextUtils.isEmpty(url)) {
            displayImg(defaultImg, iv);
            return;
        }

        if (url.contains("http://")) {
            ImageLoader.getInstance().displayImage(url + getImageSign(), iv, options_normal);
        } else {
            ImageLoader.getInstance().displayImage("file://" + url, iv,
                    options_normal);
        }
    }

    /**
     * 显示网络或本地图片/自定义大小
     *
     * @param url 图片URL
     * @param iv  显示图片的imageview
     */
    public static void displayImg(String url, final ImageView iv, int width) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_normal);
            return;
        }

        StringBuffer style = new StringBuffer();
        style.append("?imageView2/2/w/").append(width).append("/q/85");
        if (url.contains("http://")) {
            ImageLoader.getInstance().displayImage(url + style + getImageSignWithStyle(), iv, mOptions_normal);
        } else {
            ImageLoader.getInstance().displayImage("file://" + url, iv, mOptions_normal);
        }
    }

    /**
     * 显示长图
     *
     * @param url
     * @param iv
     * @param width
     */
    public static void displayLargeImg(String url, final ImageView iv, int width) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_normal);
            return;
        }

        StringBuffer style = new StringBuffer();
        style.append("?imageView2/1/w/").append(width).append("/h/").append(width * 1.5).append("/q/85");
        if (url.contains("http://")) {
            ImageLoader.getInstance().displayImage(url + style + getImageSignWithStyle(), iv, mOptions_normal);
        } else {
            ImageLoader.getInstance().displayImage("file://" + url, iv, mOptions_normal);
        }
    }

    /**
     * 显示网络或本地图片/默认缩略图-500*500
     *
     * @param url 图片URL
     * @param iv  显示图片的imageview
     */
    public static void displayImgThumb(String url, final ImageView iv) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_normal);
            return;
        }
        if (url.contains("http://")) {
            ImageLoader.getInstance().displayImage(
                    url + QCOSManager.IMAGE_SCALE_500_500 + getImageSignWithStyle(), iv, mOptions_normal);
        } else {
            ImageLoader.getInstance().displayImage("file://" + url, iv,
                    mOptions_normal);
        }
    }

    /**
     * 显示网络或本地图片/缩略图-300*300、用于照片墙、家庭相册、多图动态显示
     *
     * @param url 图片URL
     * @param iv  显示图片的imageview
     */
    public static void displayImgThumb_300(String url, final ImageView iv) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_normal);
            return;
        }
        if (url.contains("http://")) {
            ImageLoader.getInstance().displayImage(url + QCOSManager.IMAGE_SCALE_300_300 + getImageSignWithStyle(), iv, mOptions_normal);
        } else {
            ImageLoader.getInstance().displayImage("file://" + url, iv, mOptions_normal);
        }
    }

    /**
     * 显示网络或本地大图,带进度显示
     *
     * @param url 图片URL
     * @param iv  显示图片的imageView
     */
    public static void displayImg(String url, final ImageView iv, ProgressBar progressBar) {
        if (TextUtils.isEmpty(url)) {
            ImageLoader.getInstance().displayImage(url, iv, mOptions_normal);
            return;
        }

        // 如果本地已有大图则直接显示、否则判断是否有缩略图、有则显示、同时下载大图
        File file = ImageLoader.getInstance().getDiskCache().get(url);
        if (file != null && file.exists()) {
            Logger.d("xsh", "原图");
            progressBar.setVisibility(View.GONE);
            displayImg(url, iv);
            return;
        } else {
            File file_500 = ImageLoader.getInstance().getDiskCache().get(url + QCOSManager.IMAGE_SCALE_500_500 + getImageSignWithStyle());
            if (file_500 != null && file_500.exists()) {
                Logger.d("xsh", "500*500图");
                displayImgThumb(url, iv);
            } else {
                File file_300 = ImageLoader.getInstance().getDiskCache().get(url + QCOSManager.IMAGE_SCALE_300_300 + getImageSignWithStyle());
                if (file_300 != null && file_300.exists()) {
                    Logger.d("xsh", "300*300图");
                    displayImgThumb_300(url, iv);
                }
            }
        }

    }

    /**
     * 显示drawable图片
     *
     * @param resourceID 本地图片id
     * @param iv         显示图片的imageview
     */
    public static void displayImg(int resourceID, final ImageView iv) {
        ImageLoader.getInstance().displayImage("drawable://" + resourceID, iv,
                mOptions_normal);
    }

    public static String getImageSign() {
        return null;
    }

    public static String getImageSignWithStyle() {
        return null;
    }

}
