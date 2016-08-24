package com.laowo.mother;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.kale.activityoptions.ActivityCompatICS;
import com.kale.activityoptions.ActivityOptionsCompatICS;
import com.kale.activityoptions.transition.TransitionCompat;
import com.laowo.mother.util.ToastUtil;

/**
 * activity基类,封装一些界面常用的方法<br>
 * 当复写了onBackPress()要关闭一个使用动画启动的activity，请调用super.onBackPress(),如果直接finish会没有关闭的动画效果<br>
 * <p>
 * Created by xsh on 2016/8/12.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final int REQUEST_DEFAULT = 1314;

    public Context mContext;

    protected Toolbar mToolbar;
    protected TextView mTitleText;
    protected ImageView mBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //带动画跳转必须调用此方法
        TransitionCompat.startTransition(this, getLayoutId());

        mContext = this;

        initToolbar();

    }

    /**
     * 在此处返回界面布局ID
     *
     * @return 布局ID
     */
    protected abstract int getLayoutId();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        TransitionCompat.finishAfterTransition(this);
    }

    /*===============================================================================*/
    /*TODO=================================标题相关===================================*/
    /*===============================================================================*/

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
//        setSupportActionBar(mToolbar);
        mTitleText = (TextView) findViewById(R.id.tv_titleText);

    }

    protected void setTitle(String text) {
        mTitleText.setText(text);
//        getSupportActionBar().setTitle(text);
    }

    public void setTitle(int textResId) {
        mTitleText.setText(textResId);
    }

    protected void setTitleTextColor(int colorId) {
        mTitleText.setTextColor(getResources().getColor(colorId));
    }

    protected void showBack() {
        mBackButton = (ImageView) findViewById(R.id.iv_back);
        mBackButton.setVisibility(View.VISIBLE);
        mBackButton.setOnClickListener(this);
    }

    protected void showMenuItem(@MenuRes int resId, Toolbar.OnMenuItemClickListener listener) {
        mToolbar.inflateMenu(resId);
        mToolbar.setOnMenuItemClickListener(listener);
    }

    protected void initView() {

    }

    protected void initData() {

    }

    /*================================================================================*/
    /*TODO=================================共用工具===================================*/
    /*===============================================================================*/

    private MaterialDialog mMaterialDialog;

    protected void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    protected void showToast(String msg, int duration) {
        ToastUtil.showToast(mContext, msg, duration);
    }

    protected void showProDialog(boolean horizontal) {
        showProDialog("ALERT", "wait...", horizontal, true);
    }

    /**
     * 显示等待弹框
     *
     * @param horizontal             是否水平弹框
     * @param canceledOnTouchOutside 点击弹框外部是否可取消
     */
    protected void showProDialog(String title, String content, boolean horizontal, boolean canceledOnTouchOutside) {
        mMaterialDialog = new MaterialDialog.Builder(this)
                .title(title)
                .content(content)
                .progress(true, 0)
                .progressIndeterminateStyle(horizontal)
                .canceledOnTouchOutside(canceledOnTouchOutside)
                .show();
    }

    protected void dismissProDialog() {
        if (mMaterialDialog != null && mMaterialDialog.isShowing())
            mMaterialDialog.dismiss();
    }

    /**
     * 带关联动画的界面跳转,单一控件动画
     *
     * @param sharedElement   一个view对象，用来和新的activity中的一个view对象产生动画
     * @param sharedElementId 新的activity中的view的Id，这个view是用来和原始activity中的view产生动画的
     * @param intent          跳转界面意图
     */
    protected void startActivity(Intent intent, View sharedElement, int sharedElementId) {
        startActivityForResult(intent, REQUEST_DEFAULT, sharedElement, sharedElementId);
    }

    /**
     * 带关联动画的界面跳转,多个控件动画
     *
     * @param intent 跳转意图
     * @param pair   关联动画集合,Pair<View, Integer> pair = Pair.create(view, R.id.linearLayout);
     */
    @SafeVarargs
    protected final void startActivity(Intent intent, Pair<View, Integer>... pair) {
        startActivityForResult(intent, REQUEST_DEFAULT, pair);
    }

    @SafeVarargs
    protected final void startActivityForResult(Intent intent, int requestCode, Pair<View, Integer>... pair) {
        ActivityOptionsCompatICS compatICS = ActivityOptionsCompatICS.makeSceneTransitionAnimation(this, pair);
        ActivityCompatICS.startActivity(this, intent, compatICS.toBundle(), requestCode);
    }

    protected final void startActivityForResult(Intent intent, int requestCode, View sharedElement, int sharedElementId) {
        ActivityOptionsCompatICS compatICS = ActivityOptionsCompatICS.makeSceneTransitionAnimation(this, sharedElement, sharedElementId);
        ActivityCompatICS.startActivity(this, intent, compatICS.toBundle(), requestCode);
    }

}
