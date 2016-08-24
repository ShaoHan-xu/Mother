package com.laowo.mother;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.laowo.mother.util.ToastUtil;

/**
 * Fragment基类
 * Created by xsh on 2016/8/22.
 */
public abstract class BaseFragment extends Fragment {

    public Context mContext;
    public Activity mActivity;
    public View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mActivity = getActivity();

        initView();
        initData();
    }

    public abstract int getLayoutId();

    protected void initView() {

    }

    protected void initData() {

    }


    protected void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    protected void showToast(String msg, int duration) {
        ToastUtil.showToast(mContext, msg, duration);
    }

}
