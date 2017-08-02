package com.example.swjtu.secondcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swjtu.secondcode.R;
import com.example.swjtu.secondcode.entity.News;

/**
 * Created by tangpeng on 2017/4/19.
 */

public class NewsContentFragment extends Fragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_news_content, container, false);
        return root;
    }

    public void refresh(News news) {
        ((TextView) root.findViewById(R.id.news_title)).setText(news.getTitle());
        ((TextView) root.findViewById(R.id.news_content)).setText(news.getContent());
    }
}
