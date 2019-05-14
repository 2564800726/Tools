package com.blogofyb.demo.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blogofyb.demo.R;
import com.blogofyb.demo.adapters.MyAdapter;
import com.blogofyb.demo.beans.Footer;
import com.blogofyb.demo.beans.Header;
import com.blogofyb.demo.beans.Item;
import com.blogofyb.tools.recyclerview.EasyRecyclerView;
import com.blogofyb.tools.recyclerview.interfaces.OnEndListener;

import java.util.ArrayList;
import java.util.List;

public class TestRecyclerViewActivity extends AppCompatActivity {
    private EasyRecyclerView mEasyRecyclerView;

    private String[] titles = {
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？",
            "怎样把一块铜变成金子？",
            "这些你熟知的逃生技巧，很有可能是错的",
            "瞎扯 · 如何正确地吐槽",
            "吴谢宇：暗夜藏身",
            "如果遇到女生被强奸，报警需不需要征得她的同意？",
            "为什么这几年男生都不愿意去追女生了？",
            "人的手指只有 5 根，有什么进化学意义吗？"
    };

    private String[] images = {
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg",
            "https://pic4.zhimg.com/v2-5a25d2c7da2b2efe83c924b929715793.jpg",
            "https://pic2.zhimg.com/v2-a355c56ac872c375cb546c4de065a941.jpg",
            "https://pic2.zhimg.com/v2-5ad7cd9ecc047b7ee7763d8cff8bbea9.jpg",
            "https://pic3.zhimg.com/v2-585b4c37a5f014cd5202bc220e24b026.jpg",
            "https://pic2.zhimg.com/v2-1ae343a22c7da3ef5c0ee539ee23cc25.jpg",
            "https://pic1.zhimg.com/v2-e98b5cce63edc9e782457b12f2252170.jpg",
            "https://pic1.zhimg.com/v2-7bb89bb0b3d8f9ab94f82727ab3b9818.jpg"
    };

    private List<Item> mItems = new ArrayList<>();
    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_recyclerview);
        mEasyRecyclerView = findViewById(R.id.erl);
        Header header = new Header();
        header.setHeader("Header");
        Footer footer = new Footer();
        addMoreData();
        mMyAdapter = new MyAdapter(header, mItems, footer);
        mEasyRecyclerView.setAdapter(mMyAdapter);
        mEasyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEasyRecyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mEasyRecyclerView.setRefreshing(true);
                addMoreData();
                mEasyRecyclerView.setRefreshing(false);
                mMyAdapter.refreshDataSet(mItems);
            }
        });
        mMyAdapter.setOnEndListener(new OnEndListener() {
            @Override
            public void onEnd() {
                addMoreData();
                mMyAdapter.refreshDataSet(mItems);
            }
        });
    }

    private void addMoreData() {
        for (int i = 0; i < titles.length; i++) {
            Item item = new Item();
            item.setTitle(titles[i]);
            item.setImage(images[i]);
            mItems.add(item);
        }
    }
}
