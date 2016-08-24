package com.laowo.mother.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.laowo.mother.BaseActivity;
import com.laowo.mother.R;

/**
 * 测试用界面
 * Created by xsh on 2016/8/16.
 */
public class TestAty extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("testActivity");
        showBack();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.aty_test;
    }
}
