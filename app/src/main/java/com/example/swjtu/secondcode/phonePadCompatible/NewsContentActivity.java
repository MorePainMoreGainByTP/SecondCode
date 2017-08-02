package com.example.swjtu.secondcode.phonePadCompatible;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.swjtu.secondcode.R;
import com.example.swjtu.secondcode.entity.News;
import com.example.swjtu.secondcode.fragment.NewsContentFragment;

/**
 * Created by tangpeng on 2017/4/19.
 */

public class NewsContentActivity extends AppCompatActivity {

    public static void actionStart(Context context, News news) {
        Intent intent = new Intent(context, NewsContentActivity.class);
        intent.putExtra("news", news);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        News news = (News) getIntent().getSerializableExtra("news");

        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(news);
    }
}
