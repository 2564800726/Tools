package com.blogofyb.tools.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

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

    public static RecyclerView.ViewHolder getEmptyViewHolder(@NonNull Context context) {
        View view = new View(context);
        view.setMinimumHeight(1);
        view.setBackgroundColor(Color.TRANSPARENT);
        return new EmptyViewHolder(view);
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
