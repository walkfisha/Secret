package com.flyingfish.secret;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.flyingfish.secret.net.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMOBBS on 2017/4/6.
 */

public class MessageListAdapter extends BaseAdapter {
    private Context context = null;
    public MessageListAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Message getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder{
        TextView msg_content;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_msglist,null);
            viewHolder = new ViewHolder();
            viewHolder.msg_content = (TextView) view.findViewById(R.id.msg_content);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Message msg = getItem(i);
        viewHolder.msg_content.setText(msg.getMsg());

        return view;
    }

    public Context getContext() {
        return context;
    }

    private List<Message> data = new ArrayList<Message>();

    public void addAll(List<Message> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }

}
