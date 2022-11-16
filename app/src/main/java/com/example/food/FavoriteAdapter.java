package com.example.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mark on 2018/6/17.
 */

public class FavoriteAdapter extends BaseAdapter {
    private List<FavoriteData> mList;
    private Context mContext;

    public FavoriteAdapter(List<FavoriteData> list, Context context) {
        mList = list;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Holder holder = null;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.favorite_list_item, null);
            holder = new Holder();
            holder.Title = (TextView)view.findViewById(R.id.title);
            holder.Des = (TextView)view.findViewById(R.id.des);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
         Holder.Title.setText(mList.get(position).getTitle());
        Holder.Des.setText(mList.get(position).getDescription());
        return view;
    }

    final static class Holder {
        static TextView Title ,Des;
    }
}
