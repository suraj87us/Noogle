package com.giyer.noogle.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.giyer.noogle.feed.FeedActivity;

/**
 * Created by giyer7 on 3/9/17.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, FeedActivity.class);
        startActivity(intent);
        finish();
    }
}