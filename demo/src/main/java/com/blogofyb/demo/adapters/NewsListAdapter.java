package com.blogofyb.demo.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogofyb.demo.MyApplication;
import com.blogofyb.demo.R;
import com.blogofyb.demo.beans.NewsBean;
import com.blogofyb.tools.img.CompressOptions;
import com.blogofyb.tools.img.ImageLoader;
import com.blogofyb.tools.img.cache.LruAndDiskLruCache;
import com.blogofyb.tools.img.cache.LruCacheOnly;
import com.blogofyb.tools.img.compress.QualityCompressor;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsHolder> {
    private List<NewsBean> mNews;
    private ImageLoader mImageLoader;

    public NewsListAdapter(List<NewsBean> mNews) {
        this.mNews = mNews;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewsHolder(LayoutInflater.from(MyApplication.getmContext()).inflate(R.layout.item_news, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {
        NewsBean news = mNews.get(i);
        newsHolder.newsTitle.setText(news.getTitle());
        if (i == 0) {
            CompressOptions compressOptions = new CompressOptions();
            compressOptions.setWidth(newsHolder.newsImage.getWidth());
            compressOptions.setHeight(newsHolder.newsImage.getHeight());
            ImageLoader.Options options = new ImageLoader.Options();
            options.cache(new LruAndDiskLruCache(MyApplication.getmContext(), "img"))
                    .context(MyApplication.getmContext())
                    .place(R.drawable.ic_launcher_foreground)
                    .failed(R.drawable.ic_launcher_background)
                    .compressor(QualityCompressor.getInstance())
                    .compressOptions(compressOptions)
                    .compressImage(true);
            mImageLoader = new ImageLoader();
            mImageLoader.apply(options);
        }
        mImageLoader.load(news.getImage()).withTag(news.getImage()).into(newsHolder.newsImage);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    static class NewsHolder extends RecyclerView.ViewHolder {
        private ImageView newsImage;
        private TextView newsTitle;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            newsImage = itemView.findViewById(R.id.iv_news_image);
            newsTitle = itemView.findViewById(R.id.tv_news_title);
        }
    }
}
