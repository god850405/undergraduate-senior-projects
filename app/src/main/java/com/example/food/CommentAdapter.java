package com.example.food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mark on 2018/7/12.
 */

public class CommentAdapter extends BaseAdapter {
    private List<Comment> List;
    private ListView listview;

    public CommentAdapter(List<Comment> list){
        super();
        this.List=list;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (listview == null) {
            listview = (ListView) parent;
        }
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.comment_list, null);
            holder = new Holder();
            holder.username = (TextView) convertView.findViewById(R.id.user);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);
            holder.star1 = (ImageView) convertView.findViewById(R.id.star1);
            holder.star2 = (ImageView) convertView.findViewById(R.id.star2);
            holder.star3 = (ImageView) convertView.findViewById(R.id.star3);
            holder.star4 = (ImageView) convertView.findViewById(R.id.star4);
            holder.star5 = (ImageView) convertView.findViewById(R.id.star5);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Comment comment = List.get(position);
        holder.username.setText(comment.getUsername());
        holder.comment.setText(comment.getComment());
        int star=comment.getStar();
        switch (star){
            case 1:
                holder.star1.setImageResource(R.mipmap.star);
                holder.star2.setImageResource(R.mipmap.star_null);
                holder.star3.setImageResource(R.mipmap.star_null);
                holder.star4.setImageResource(R.mipmap.star_null);
                holder.star5.setImageResource(R.mipmap.star_null);
                break;
            case 2:
                holder.star1.setImageResource(R.mipmap.star);
                holder.star2.setImageResource(R.mipmap.star);
                holder.star3.setImageResource(R.mipmap.star_null);
                holder.star4.setImageResource(R.mipmap.star_null);
                holder.star5.setImageResource(R.mipmap.star_null);
                break;
            case 3:
                holder.star1.setImageResource(R.mipmap.star);
                holder.star2.setImageResource(R.mipmap.star);
                holder.star3.setImageResource(R.mipmap.star);
                holder.star4.setImageResource(R.mipmap.star_null);
                holder.star5.setImageResource(R.mipmap.star_null);
                break;
            case 4:
                holder.star1.setImageResource(R.mipmap.star);
                holder.star2.setImageResource(R.mipmap.star);
                holder.star3.setImageResource(R.mipmap.star);
                holder.star4.setImageResource(R.mipmap.star);
                holder.star5.setImageResource(R.mipmap.star_null);
                break;
            case 5:
                holder.star1.setImageResource(R.mipmap.star);
                holder.star2.setImageResource(R.mipmap.star);
                holder.star3.setImageResource(R.mipmap.star);
                holder.star4.setImageResource(R.mipmap.star);
                holder.star5.setImageResource(R.mipmap.star);break;
        }
        return convertView;
    }
    class Holder {
        TextView username ,comment;
        ImageView star1,star2,star3,star4,star5;
    }
}
