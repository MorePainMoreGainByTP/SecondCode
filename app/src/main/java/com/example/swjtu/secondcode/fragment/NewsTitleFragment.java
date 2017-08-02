package com.example.swjtu.secondcode.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swjtu.secondcode.phonePadCompatible.NewsContentActivity;
import com.example.swjtu.secondcode.R;
import com.example.swjtu.secondcode.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangpeng on 2017/4/19.
 */

public class NewsTitleFragment extends Fragment {

    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_titles, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new NewsTitleAdapter(initData()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    private List<News> initData() {
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            News news = new News("this is title :" + i, getRandomContent("-- this is title --"));
            newsList.add(news);
        }
        return newsList;
    }

    private String getRandomContent(String str) {
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < Math.random() * 50; i++) {
            builder.append(str);
        }
        return builder.toString();
    }

    class NewsTitleAdapter extends RecyclerView.Adapter<NewsTitleAdapter.ViewHolder> {

        private List<News> newsList;

        public NewsTitleAdapter(List<News> newsList) {
            this.newsList = newsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_title, parent, false);
            final ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.newsTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news = newsList.get(viewHolder.getAdapterPosition());
                    if (isTwoPane) {
                        NewsContentFragment contentFragment = (NewsContentFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
                        contentFragment.refresh(news);
                    } else {
                        NewsContentActivity.actionStart(getActivity(), news);
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.newsTitle.setText(newsList.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                newsTitle = (TextView) itemView.findViewById(R.id.news_title);
            }
        }

    }
}
