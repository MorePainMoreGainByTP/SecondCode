package com.example.swjtu.secondcode.entity;

/**
 * Created by tangpeng on 2017/4/19.
 */

public class ChatMessage {
    public static final int TYPE_MSG_SEND = 0;   //
    public static final int TYPE_MSG_RECEIVE = 1;   //

    private String content;
    private int type;

    public ChatMessage(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }


    public int getType() {
        return type;
    }

}
