package com.example.swjtu.secondcode.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swjtu.secondcode.R;
import com.example.swjtu.secondcode.entity.ChatMessage;

import java.util.List;

/**
 * Created by tangpeng on 2017/4/19.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<ChatMessage> chatMessages;

    public MsgAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatMessage message = chatMessages.get(position);
        if (message.getType() == ChatMessage.TYPE_MSG_RECEIVE) {
            holder.layoutRight.setVisibility(View.GONE);
            holder.layoutLeft.setVisibility(View.VISIBLE);
            holder.contentL.setText(message.getContent());
        } else {
            holder.layoutRight.setVisibility(View.VISIBLE);
            holder.layoutLeft.setVisibility(View.GONE);
            holder.contentR.setText(message.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutLeft, layoutRight;
        TextView contentL, contentR;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutLeft = (LinearLayout) itemView.findViewById(R.id.layoutLeft);
            layoutRight = (LinearLayout) itemView.findViewById(R.id.layoutRight);
            contentL = (TextView) itemView.findViewById(R.id.contentL);
            contentR = (TextView) itemView.findViewById(R.id.contentR);
        }
    }
}
