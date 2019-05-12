package com.blogofyb.demo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blogofyb.demo.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        findViewById(R.id.btn_test_http).setOnClickListener(this);
        findViewById(R.id.btn_test_image_loader).setOnClickListener(this);
        findViewById(R.id.btn_test_json).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test_http:
                Intent intentForHttp = new Intent(this, TestHttpActivity.class);
                startActivity(intentForHttp);
                break;
            case R.id.btn_test_image_loader:
                Intent intentForImageLoader = new Intent(this, TestImageLoaderActivity.class);
                startActivity(intentForImageLoader);
                break;
            case R.id.btn_test_json:
                Intent intentForJson = new Intent(this, TestJsonActivity.class);
                startActivity(intentForJson);
                break;
        }
    }
}
