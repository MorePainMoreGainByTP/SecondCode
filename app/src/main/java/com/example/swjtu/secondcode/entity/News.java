package com.example.swjtu.secondcode.entity;

import java.io.Serializable;

/**
 * Created by tangpeng on 2017/4/19.
 */

public class News implements Serializable{
    private String title;
    private String content;

    public News(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
