package com.blogofyb.tools.recyclerview.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.blogofyb.tools.recyclerview.interfaces.OnEndListener;
import com.blogofyb.tools.thread.ThreadManager;

import java.util.List;

public abstract class BasedEasyAdapter<HEADER, ITEM, FOOTER> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    private HEADER mHeader;
    private List<ITEM> mItems;
    private FOOTER mFooter;

    private OnEndListener mListener;

    public BasedEasyAdapter(HEADER mHeader, List<ITEM> mItems, FOOTER mFooter) {
        this.mHeader = mHeader;
        this.mItems = mItems;
        this.mFooter = mFooter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_HEADER) {
            return createHeader(viewGroup);
        } else if (i == TYPE_ITEM) {
            return createItem(viewGroup);
        } else {
            return createFooter(viewGroup);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (getItemViewType(i) == TYPE_HEADER) {
            bindHeaderViewHolder(viewHolder, mHeader);
        } else if (getItemViewType(i) == TYPE_ITEM) {
            bindItemViewHolder(viewHolder, mItems.get(i - 1));
        } else {
            bindFooterViewHolder(viewHolder, mFooter);
            ThreadManager.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    mListener.onEnd();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size() + 2;
    }

    public void refreshDataSet(List<ITEM> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    public void setOnEndListener(OnEndListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 创建Header的ViewHolder
     * @return  Header
     */
    protected abstract RecyclerView.ViewHolder createHeader(@Nullable ViewGroup viewGroup);

    /**
     * 创建Item的ViewHolder
     * @return  Item
     */
    protected abstract RecyclerView.ViewHolder createItem(@Nullable ViewGroup viewGroup);

    /**
     * 创建Footer的ViewHolder
     * @return  Footer
     */
    protected abstract RecyclerView.ViewHolder createFooter(@Nullable ViewGroup viewGroup);

    /**
     * 为Header的ViewHolder绑定数据
     * @param holder    Header的ViewHolder
     * @param header    Header
     */
    protected abstract void bindHeaderViewHolder(RecyclerView.ViewHolder holder, HEADER header);

    /**
     * 为Item的ViewHolder绑定数据
     * @param holder    Item的ViewHolder
     * @param item    Item
     */
    protected abstract void bindItemViewHolder(RecyclerView.ViewHolder holder, ITEM item);

    /**
     * 为Footer的ViewHolder绑定数据
     * @param holder    Footer的ViewHolder
     * @param footer    Footer
     */
    protected abstract void bindFooterViewHolder(RecyclerView.ViewHolder holder, FOOTER footer);
}
