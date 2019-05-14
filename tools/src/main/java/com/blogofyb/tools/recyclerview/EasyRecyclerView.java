package com.blogofyb.tools.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.blogofyb.tools.recyclerview.adapter.BasedEasyAdapter;

public class EasyRecyclerView extends SwipeRefreshLayout {
    private RecyclerView mRecyclerView;

    public EasyRecyclerView(@NonNull Context context) {
        super(context);
        initRecyclerView(context);
    }

    public EasyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRecyclerView(context);
    }

    public void setAdapter(BasedEasyAdapter<?, ?, ?> mAdapter) {
        mRecyclerView.setAdapter(mAdapter);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void initRecyclerView(Context context) {
        mRecyclerView = new RecyclerView(context);
        addView(mRecyclerView);
    }
}
