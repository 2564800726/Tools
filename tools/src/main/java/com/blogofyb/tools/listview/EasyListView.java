package com.blogofyb.tools.listview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public class EasyListView extends SwipeRefreshLayout {
    private ListView mListView;

    public EasyListView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public EasyListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setAdapter(ListAdapter adapter) {
        mListView.setAdapter(adapter);
    }

    public void setDecoration(Drawable decoration) {
        mListView.setDivider(decoration);
    }

    private void initView(Context context) {
        mListView = new ListView(context);
        addView(mListView);
    }

    public static View getEmptyView(Context context) {
        View view = new View(context);
        view.setMinimumHeight(1);
        view.setBackgroundColor(Color.TRANSPARENT);
        return view;
    }
}
