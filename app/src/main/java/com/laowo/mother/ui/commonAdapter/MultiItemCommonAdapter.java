package com.laowo.mother.ui.commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * 多种Item类型的recyclerViewAdapter
 * Created by xsh on 2016/6/27.
 */
public abstract class MultiItemCommonAdapter<T> extends CommonRecyclerAdapter<T> {

    private MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> list, MultiItemTypeSupport<T> multiItemTypeSupport, RecyclerView.LayoutManager layoutManager) {
        super(context, list, -1, layoutManager);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position, list.get(position));
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        RecyclerViewHolder holder = RecyclerViewHolder.get(mContext, layoutId, parent);
        return holder;
    }

    @Override
    public void convert(RecyclerViewHolder holder, T item, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        convert(holder, list.get(position), position, mMultiItemTypeSupport.getItemViewType(position, list.get(position)));
        bindingClickListener(holder, position);
    }

    public abstract void convert(RecyclerViewHolder holder, T item, int position, int viewType);

    public interface MultiItemTypeSupport<T> {
        int getLayoutId(int itemType);

        int getItemViewType(int position, T t);
    }
}
