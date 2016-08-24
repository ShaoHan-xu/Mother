package com.laowo.mother.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by xsh on 2016/8/16.
 */
public class ToastUtil {

    /**
     * 显示土司
     *
     * @param context
     * @param msg
     * @param duration
     */
    public static void showToast(Context context, String msg, int duration) {
        Toast.makeText(context, msg, duration).show();
    }
}
