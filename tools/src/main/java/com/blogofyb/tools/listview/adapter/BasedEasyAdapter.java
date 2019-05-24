package com.blogofyb.tools.listview.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.blogofyb.tools.listview.interfaces.OnEndListener;
import com.blogofyb.tools.thread.ThreadManager;

import java.util.List;

public abstract class BasedEasyAdapter<HEADER, ITEM, FOOTER> extends ArrayAdapter<ITEM> {
    private HEADER mHeader;
    private List<ITEM> mItems;
    private FOOTER mFooter;

    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    private OnEndListener mListener;

    public BasedEasyAdapter(HEADER mHeader, List<ITEM> mItems, FOOTER mFooter, @NonNull Context context, @LayoutRes int resource) {
        super(context, resource, mItems);
        this.mHeader = mHeader;
        this.mItems = mItems;
        this.mFooter = mFooter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return createHeader(convertView, parent, mHeader);
        } else if (getItemViewType(position) == TYPE_ITEM) {
            return createItem(convertView, parent, mItems.get(position - 1));
        } else {
            if (mListener != null) {
                ThreadManager.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onEnd();
                    }
                });
            }
            return createFooter(convertView, parent, mFooter);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == getCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getCount() {
        return mItems.size() + 2;
    }

    /**
     * 更新ListView中的数据
     * @param mItems  新的数据集
     */
    public void refreshData(List<ITEM> mItems) {
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    public void setOnEndListener(OnEndListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 创建Header
     * @param convertView  缓存的View
     * @param viewGroup  parent
     * @param mHeader  Header
     * @return  Header
     */
    protected abstract View createHeader(@Nullable View convertView, @NonNull ViewGroup viewGroup, HEADER mHeader);

    /**
     * 创建Item
     * @param convertView  缓存的View
     * @param viewGroup  parent
     * @param item  Item
     * @return  Item
     */
    protected abstract View createItem(@Nullable View convertView, @NonNull ViewGroup viewGroup, ITEM item);

    /**
     * 创建Footer
     * @param convertView  缓存的View
     * @param viewGroup  parent
     * @param mFooter  Footer
     * @return  Footer
     */
    protected abstract View createFooter(@Nullable View convertView, @NonNull ViewGroup viewGroup, FOOTER mFooter);
}
