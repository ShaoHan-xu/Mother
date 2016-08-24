package com.laowo.mother.qcloud;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Calendar;

/**
 * 腾讯万象优图相关管理类
 */
public class QCOSManager {

    private final static String TAG = "QcloudManager ";
    public static final String APPID = "10001624";
    public static final String BUCKET_NAME_COS = "laowo";
    public static final String BUCKET_NAME_IMAGE = "image";
    public static final String IMAGE_SCALE_100_100 = "?imageView2/1/w/100/h/100";
    public static final String IMAGE_SCALE_300_300 = "?imageView2/1/w/300/h/300";
    public static final String IMAGE_SCALE_500_500 = "?imageView2/2/w/500/h/500/q/85";
    public static final String IMAGE_SCALE_1920_1080 = "?imageView2/1/w/1080/h/1920/q/85";
    public static final int IMAGE_UPLOAD_TYPE_DYNAMIC = 5;
    public static final int IMAGE_UPLOAD_TYPE_KINSHIP_TASK = 6;

    private static final String QCOS_KEY_COS_SIGN = "cos_sign";
    private static final String QCOS_KEY_IMG_SIGN = "img_sign";
    private static final String QCOS_KEY_SIGN_UPDATE_TIME = "sign_update_time";
    private static final String QCOS_KEY_USER_ID = "user_id";
    private static final String QCOS_KEY_USER_TOKEN = "user_token";

    /**
     * 多久更新一次签名
     */
    private static final long UPDATE_SIGN_DAY_MILLIS = 15 * (1000 * 60 * 60 * 24);

    private static Context context;
    private static QCOSManager sInstance;

    public static QCOSManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (QCOSManager.class) {
                if (sInstance == null) {
                    sInstance = new QCOSManager();
                }
            }
        }
        QCOSManager.context = context;
        return sInstance;
    }

    private QCOSManager() {
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        if (QCOSManager.context != null) return;

    }

    private static SharedPreferences getSharedPreferences() {
        if (context == null) {
            throw new RuntimeException("QCOSManager not init");
        }
        SharedPreferences sp = context.getSharedPreferences("QCOSManager_Store", 0);
        return sp;
    }

    /**
     * 获取 Cos 签名
     *
     * @return
     */
    public String getCosSign() {
        String cosSign = getSharedPreferences().getString(QCOS_KEY_COS_SIGN, null);
        if (TextUtils.isEmpty(cosSign) || (Calendar.getInstance().getTimeInMillis() - getSignUpdateTime() > UPDATE_SIGN_DAY_MILLIS)) {
            updateCosSign();
        }
        return cosSign;
    }

    /**
     * 获取 万象优图 签名
     *
     * @return
     */
    public String getImgSign() {
        String imgSign = getSharedPreferences().getString(QCOS_KEY_IMG_SIGN, null);
        if (TextUtils.isEmpty(imgSign) || (Calendar.getInstance().getTimeInMillis() - getSignUpdateTime() > UPDATE_SIGN_DAY_MILLIS)) {
            updateImgSign();
        }
        return imgSign;
    }

    /**
     * 获取签名最后更新的时间
     *
     * @return
     */
    private static long getSignUpdateTime() {
        long timeMillis = getSharedPreferences().getLong(QCOS_KEY_SIGN_UPDATE_TIME, 0);
        return timeMillis;
    }

    /**
     * 保存 万象优图 签名
     *
     * @param sign
     */
    private static void setImgSign(String sign) {
        if (TextUtils.isEmpty(sign)) return;

        getSharedPreferences().edit().putString(QCOS_KEY_IMG_SIGN, sign).commit();
    }

    /**
     * 保存 Cos 签名
     *
     * @param sign
     */
    private static void setCosSign(String sign) {
        if (TextUtils.isEmpty(sign)) return;

        getSharedPreferences().edit().putString(QCOS_KEY_COS_SIGN, sign).commit();
    }

    /**
     * 更新当前签名的用户
     */
    private static void updateUser() {

    }

    /**
     * 获取当前签名的 userId
     *
     * @return
     */
    private static String getUserId() {
        return getSharedPreferences().getString(QCOS_KEY_USER_ID, null);
    }

    /**
     * 获取 当前签名的 userToken
     *
     * @return
     */
    private static String getUserToken() {
        return getSharedPreferences().getString(QCOS_KEY_USER_TOKEN, null);
    }

    /**
     * 更新 签名更新时间
     */
    private static void updateSignUpdateTime() {
        if (isUpdateCosSignSuccess && isUpdateImgSignSuccess) {
            getSharedPreferences().edit().putLong(QCOS_KEY_SIGN_UPDATE_TIME, Calendar.getInstance().getTimeInMillis()).commit();
        }
    }

    /**
     * 更新签名
     */
    public static void updateSign() {
        updateCosSign();
        updateImgSign();
    }

    private static boolean isUpdateCosSignSuccess;
    private static boolean isUpdateImgSignSuccess;

    /**
     * 更新 Cos 签名
     */
    private static void updateCosSign() {

    }

    /**
     * 更新 万象优图 签名
     */
    private static void updateImgSign() {

    }
}
