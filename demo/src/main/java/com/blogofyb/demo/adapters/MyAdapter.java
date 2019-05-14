package com.blogofyb.demo.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blogofyb.demo.MyApplication;
import com.blogofyb.demo.R;
import com.blogofyb.demo.beans.Footer;
import com.blogofyb.demo.beans.Header;
import com.blogofyb.demo.beans.Item;
import com.blogofyb.tools.img.CompressOptions;
import com.blogofyb.tools.img.ImageLoader;
import com.blogofyb.tools.img.cache.LruAndDiskLruCache;
import com.blogofyb.tools.img.compress.QualityCompressor;
import com.blogofyb.tools.recyclerview.adapter.BasedEasyAdapter;

import java.util.List;

public class MyAdapter extends BasedEasyAdapter<Header, Item, Footer> {
    private ImageLoader mImageLoader;

    public MyAdapter(Header mHeader, List<Item> mItems, Footer mFooter) {
        super(mHeader, mItems, mFooter);
    }

    @Override
    protected RecyclerView.ViewHolder createHeader(@Nullable ViewGroup viewGroup) {
        return new HeaderViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.header, viewGroup, false));
    }

    @Override
    protected RecyclerView.ViewHolder createItem(@Nullable ViewGroup viewGroup) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item, viewGroup, false));
        CompressOptions compressOptions = new CompressOptions();
        compressOptions.setWidth(itemViewHolder.mImageView.getWidth());
        compressOptions.setHeight(itemViewHolder.mImageView.getHeight());
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
        return itemViewHolder;
    }

    @Override
    protected RecyclerView.ViewHolder createFooter(@Nullable ViewGroup viewGroup) {
        return new FooterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.footer, viewGroup, false));
    }

    @Override
    protected void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, Header header) {
        HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        holder.mHeader.setText(header.getHeader());
    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, Item item) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        holder.mTextView.setText(item.getTitle());
        mImageLoader.load(item.getImage()).withTag(item.getImage()).into(holder.mImageView);
    }

    @Override
    protected void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder, Footer footer) {}

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView mHeader;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            mHeader = itemView.findViewById(R.id.tv_header);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_item);
            mTextView = itemView.findViewById(R.id.tv_item);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.pb_footer);
        }
    }
}
