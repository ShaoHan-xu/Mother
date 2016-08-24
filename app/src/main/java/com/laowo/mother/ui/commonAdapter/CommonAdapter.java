package com.laowo.mother.ui.commonAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用adapter
 * Created by xsh on 2016/8/23.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mList;
    private int mLayoutId;
    private MultiItemTypeSupport<T> mMultiItemTypeSupport;

    /**
     * 正常只有一种item的adapter构造方法
     *
     * @param context
     * @param list
     * @param layoutId
     */
    public CommonAdapter(Context context, List<T> list, int layoutId) {
        mContext = context;
        mList = list;
        mLayoutId = layoutId;
    }

    /**
     * 多种item的adapter构造方法<br>
     * 需要实现MultiItemTypeSupport接口
     *
     * @param context
     * @param list
     * @param multiItemTypeSupport
     */
    public CommonAdapter(Context context, List<T> list, MultiItemTypeSupport<T> multiItemTypeSupport) {
        mContext = context;
        mList = list;
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int i) {
        if (mList == null || i >= mList.size())
            return null;
        return mList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position, mList.get(position));
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getViewTypeCount();
        return super.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = getViewHelper(position, convertView, parent);
        convert(holder, mList.get(position), position);
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T item, int position);

    private ViewHolder getViewHelper(int position, View convertView, ViewGroup parent) {
        if (mMultiItemTypeSupport != null)
            mLayoutId = mMultiItemTypeSupport.getLayoutId(getItemViewType(position));
        return ViewHolder.get(mContext, convertView, parent, mLayoutId);
    }

    public interface MultiItemTypeSupport<T> {

        int getItemViewType(int position, T data);

        int getLayoutId(int itemType);

        int getViewTypeCount();
    }
}
