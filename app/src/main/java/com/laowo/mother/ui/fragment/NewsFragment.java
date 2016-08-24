package com.laowo.mother.ui.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.laowo.mother.BaseFragment;
import com.laowo.mother.R;
import com.laowo.mother.ui.commonAdapter.CommonAdapter;
import com.laowo.mother.ui.commonAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯首页模块
 * Created by xsh on 2016/8/22.
 */
public class NewsFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    private SwipeRefreshLayout mRefreshLayout;
    private ListView mListView_news;

    @Override
    protected void initView() {
        super.initView();
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.refreshLayout_news);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mListView_news = (ListView) mView.findViewById(R.id.recycleView_news);

    }

    @Override
    protected void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 51; i++)
            list.add("");

        mListView_news.setAdapter(new ListAdapter(mContext, list, mMultiItemTypeSupport));
    }

    private class ListAdapter extends CommonAdapter<String> {

        ListAdapter(Context context, List<String> list, MultiItemTypeSupport<String> multiItemTypeSupport) {
            super(context, list, multiItemTypeSupport);
        }

        @Override
        public void convert(ViewHolder holder, String item, int position) {
            if (position % 9 == 0) {
                holder.setText(R.id.tv_newsItem_videoTitle, "标题->" + position);
            } else {
                holder.setText(R.id.tv_newsItem_title, "标题->" + position);
            }
        }
    }

    CommonAdapter.MultiItemTypeSupport<String> mMultiItemTypeSupport = new CommonAdapter.MultiItemTypeSupport<String>() {

        @Override
        public int getItemViewType(int position, String data) {
            if (position % 9 == 0)
                return 0;
            return 1;
        }

        @Override
        public int getLayoutId(int itemType) {
            if (itemType == 0)
                return R.layout.item_news_video;
            return R.layout.item_news_common;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }
    };

}
