package com.laowo.mother.ui.widget;

import android.app.Activity;

import com.github.mrengineer13.snackbar.SnackBar;

/**
 * 自定义吐司,引用Material Design风格的snackbar <br>
 * for activity:<br>
 * new SnackBar.Builder(this)<br>
 * for fragment:<br>
 * new SnackBar.Builder(getActivity().getApplicationContext(), root)<br>
 * ----.withOnClickListener(this)<br>
 * ----.withMessage("This library is awesome!") // OR<br>
 * ----.withMessageId(messageId)<br>
 * ----.withTypeFace(myAwesomeTypeFace)<br>
 * ----.withActionMessage("Action") // OR<br>
 * ----.withActionMessageId(actionMsgId)<br>
 * ----.withTextColorId(textColorId)<br>
 * ----.withBackGroundColorId(bgColorId)<br>
 * ----.withVisibilityChangeListener(this)<br>
 * ----.withStyle(style)<br>
 * ----.withDuration(duration)<br>
 * ----.show();<br>
 * <p>
 * Created by xsh on 2016/8/15.
 */
public class CustomToast {

    public static final Short mDuration = 1000;
    public static final SnackBar.Style mStyle = SnackBar.Style.DEFAULT;

    public static void show(Activity activity, String msg) {
        show(activity, mStyle, msg, mDuration, null);
    }

    public static void show(Activity activity, String msg, Short duration) {
        show(activity, mStyle, msg, duration, null);
    }

    public static void show(Activity activity, SnackBar.Style style, String msg) {
        show(activity, style, msg, mDuration, null);
    }

    public static void show(Activity activity, SnackBar.Style style, String msg, Short duration) {
        show(activity, style, msg, duration, null);
    }

    public static void show(Activity activity, SnackBar.Style style, String msg, Short duration, SnackBar.OnMessageClickListener listener) {
        new SnackBar.Builder(activity).withStyle(style).withMessage(msg).withDuration(duration).withOnClickListener(listener).show();
    }

}
