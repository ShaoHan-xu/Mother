package com.laowo.mother.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.laowo.mother.BaseActivity;
import com.laowo.mother.R;
import com.laowo.mother.ui.fragment.HealthFragment;
import com.laowo.mother.ui.fragment.MessageFragment;
import com.laowo.mother.ui.fragment.MineFragment;
import com.laowo.mother.ui.fragment.NewsFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("main");
        initView();

        initData();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        findViewById(R.id.rl_bottomBar_tab1).setOnClickListener(this);
        findViewById(R.id.rl_bottomBar_tab2).setOnClickListener(this);
        findViewById(R.id.rl_bottomBar_tab3).setOnClickListener(this);
        findViewById(R.id.rl_bottomBar_tab4).setOnClickListener(this);

    }

    @Override
    protected void initData() {
        initFragment();

    }

    //TODO==========================================================================================
    //TODO=============================== fragment相关==============================================
    //TODO==========================================================================================

    private FragmentManager mFragmentManager;
    private Fragment[] mFragments;
    private NewsFragment mNewsFragment;
    private HealthFragment mHealthFragment;
    private MessageFragment mMsgFragment;
    private MineFragment mMineFragment;

    public static final int TAB1 = 0;
    public static final int TAB2 = 1;
    public static final int TAB3 = 2;
    public static final int TAB4 = 3;
    public int mCurTAB;

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mFragments = new Fragment[4];
        changeFragment(TAB1);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rl_bottomBar_tab1:
                changeFragment(TAB1);
                break;
            case R.id.rl_bottomBar_tab2:
                changeFragment(TAB2);
                break;
            case R.id.rl_bottomBar_tab3:
                changeFragment(TAB3);
                break;
            case R.id.rl_bottomBar_tab4:
                changeFragment(TAB4);
                break;
        }
    }

    private void changeFragment(int index) {
        mCurTAB = index;
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case TAB1:
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment();
                    transaction.add(R.id.main_content, mNewsFragment);
                    mFragments[0] = mNewsFragment;
                }
                transaction.show(mNewsFragment);
                setTitle(getString(R.string.word_main_tab1));
                break;
            case TAB2:
                if (mHealthFragment == null) {
                    mHealthFragment = new HealthFragment();
                    transaction.add(R.id.main_content, mHealthFragment);
                    mFragments[1] = mHealthFragment;
                }
                transaction.show(mHealthFragment);
                setTitle(getString(R.string.word_main_tab2));
                break;
            case TAB3:
                if (mMsgFragment == null) {
                    mMsgFragment = new MessageFragment();
                    transaction.add(R.id.main_content, mMsgFragment);
                    mFragments[2] = mMsgFragment;
                }
                transaction.show(mMsgFragment);
                setTitle(getString(R.string.word_main_tab3));
                break;
            case TAB4:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    transaction.add(R.id.main_content, mMineFragment);
                    mFragments[3] = mMineFragment;
                }
                transaction.show(mMineFragment);
                setTitle(getString(R.string.word_main_tab4));
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        for (int i = 0; i < mFragments.length; i++) {
            Fragment fragment = mFragments[i];
            if (i != mCurTAB && fragment != null)
                transaction.hide(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
