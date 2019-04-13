package com.blogofyb.test;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogofyb.tools.R;
import com.blogofyb.tools.img.CompressOptions;
import com.blogofyb.tools.img.ImageLoader;
import com.blogofyb.tools.img.cache.DiskLruCacheOnly;
import com.blogofyb.tools.img.compress.InSampleSizeCompressor;
import com.blogofyb.tools.img.compress.QualityCompressor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.iv_test);
        ImageLoader.Options options = new ImageLoader.Options();
        CompressOptions options1 = new CompressOptions();
        options1.setMaxSize(10000);
        options1.setQuality(50);
        options.url("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1986179278,1118313821&fm=27&gp=0.jpg")
                .compressor(QualityCompressor.getInstance(),
                        InSampleSizeCompressor.getInstance())
                .cache(new DiskLruCacheOnly(this, "img"))
                .compressOptions(options1)
                .context(this)
                .decodeOptions(true)
                .place(R.drawable.ic_launcher_background)
                .failed(R.drawable.ic_launcher_background)
                .tag("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1986179278,1118313821&fm=27&gp=0.jpg");
        new ImageLoader().with(this).apply(options).into(imageView);
    }
}
