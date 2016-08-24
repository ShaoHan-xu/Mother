package com.laowo.mother.ui.commonAdapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通用RecyclerViewAdapter
 * Created by xsh on 2016/5/4.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {

    protected Context mContext;
    protected List<T> list;
    private int layoutId;
    private RecyclerView.LayoutManager mLayoutManager;
    protected Map<View, OnWidgetClickListener> mClickListeners;

    CommonRecyclerAdapter(Context context, List<T> list, int layoutId, RecyclerView.LayoutManager layoutManager) {
        this.mContext = context;
        this.list = list;
        this.layoutId = layoutId;
        this.mLayoutManager = layoutManager;
        mClickListeners = new HashMap<>();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = RecyclerViewHolder.get(mContext, layoutId, parent);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        convert(holder, list.get(position), position);
        bindingClickListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return null == list ? 0 : list.size();
    }

    public abstract void convert(RecyclerViewHolder holder, T item, int position);

    /**
     * 添加数据
     *
     * @param data
     */
    public void addData(T data) {
        list.add(data);
        notifyItemInserted(list.size());
        if (mLayoutManager != null)
            mLayoutManager.scrollToPosition(list.size() - 1);
    }

    /**
     * 添加数据到指定位置
     *
     * @param data
     * @param index 指定位置
     */
    public void addData(int index, T data) {
        if (index == list.size()) {
            addData(data);
            return;
        }
        list.add(index, data);
        notifyItemInserted(index);
        mHandler.sendEmptyMessageDelayed(0, 500);
        if (mLayoutManager != null && index == list.size() - 1)
            mLayoutManager.scrollToPosition(list.size() - 1);
    }

    /**
     * 移除数据
     *
     * @param position
     */
    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        if (position != list.size())
            notifyItemRangeChanged(position, list.size() - position + 1);
    }

    /**
     * 此处统一绑定item内各个单击事件。(调用setWidgetClickListener)<br>
     * 复写onWidgetClick方法、并在其内处理点击事件
     *
     * @param holder
     */
    public void bindingClickListener(RecyclerViewHolder holder, int position) {

    }

    public void onWidgetClick(View v, int position) {

    }

    /**
     * 设置列表内控件监听器、在此处设置可避免重复创建监听器
     *
     * @param helper
     * @param position
     * @param viewId
     */
    public void setWidgetClickListener(RecyclerViewHolder helper, final int position, int viewId) {
        View view = helper.getView(viewId);
        OnWidgetClickListener clickListener = mClickListeners.get(view);
        if (clickListener == null) {
            clickListener = new OnWidgetClickListener(position);
            mClickListeners.put(view, clickListener);
        } else {
            clickListener.updateData(position);
        }
        if (view != null)
            view.setOnClickListener(clickListener);
    }

    private class OnWidgetClickListener implements View.OnClickListener {

        private int position;

        public OnWidgetClickListener(final int position) {
            super();
            this.position = position;
        }

        public void updateData(final int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            onWidgetClick(v, position);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0)
                notifyDataSetChanged();
        }
    };

}
