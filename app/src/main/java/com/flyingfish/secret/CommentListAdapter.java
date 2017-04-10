package com.flyingfish.secret;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.flyingfish.secret.net.Comments;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMOBBS on 2017/4/10.
 */

public class CommentListAdapter extends BaseAdapter{

    private Context context;
    private List<Comments> comments = new ArrayList<Comments>();


    public CommentListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comments getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    public void addAll(List<Comments> data){
        comments.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        comments.clear();
        notifyDataSetChanged();
    }
}
