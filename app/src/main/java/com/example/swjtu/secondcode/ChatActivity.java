package com.example.swjtu.secondcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.swjtu.secondcode.adapter.MsgAdapter;
import com.example.swjtu.secondcode.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangpeng on 2017/4/19.
 */

public class ChatActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private EditText edit1, edit2;
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private MsgAdapter msgAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getViews();
        setViews();
    }

    private void getViews() {
        recyclerView = (RecyclerView) findViewById(R.id.msgRecycler);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
    }

    public void setViews() {
        msgAdapter = new MsgAdapter(chatMessages);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(msgAdapter);
    }

    public void send1(View v) {
        String str = edit1.getText().toString();
        if (!str.equals("")) {
            addChatItem(str, ChatMessage.TYPE_MSG_RECEIVE);
            edit1.setText("");
        } else {
            Toast.makeText(this, "消息不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void send2(View v) {
        String str = edit2.getText().toString();
        if (!str.equals("")) {
            addChatItem(str, ChatMessage.TYPE_MSG_SEND);
            edit2.setText("");
        } else {
            Toast.makeText(this, "消息不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private void addChatItem(String str, int type) {
        ChatMessage chatMessage = new ChatMessage(str, type);
        chatMessages.add(chatMessage);
        msgAdapter.notifyItemInserted(chatMessages.size() - 1);
        recyclerView.scrollToPosition(chatMessages.size() - 1);
    }

}
