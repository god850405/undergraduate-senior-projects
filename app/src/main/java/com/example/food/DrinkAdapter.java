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
 * Created by Mark on 2018/7/21.
 */

public class DrinkAdapter extends BaseAdapter {
    private List<drinkstore> list;
    private ListView listview;
    public DrinkAdapter(List<drinkstore> list) {
        super();
        this.list = list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
        Holder holder =null;
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.drink_list, null);
            holder = new Holder();
            holder.logo = convertView.findViewById(R.id.pic_logo);
            holder.title = convertView.findViewById(R.id.d_title);
            holder.name = convertView.findViewById(R.id.d_name);
            holder.tel = convertView.findViewById(R.id.d_tel);
            holder.address = convertView.findViewById(R.id.d_add);

            convertView.setTag(holder);
        }else{
            holder=(Holder)convertView.getTag();
        }
        drinkstore drinkstore =list.get(position);
        holder.name.setText(drinkstore.getName());
        holder.tel.setText(drinkstore.getTel());
        holder.address.setText(drinkstore.getAddress());

        switch (drinkstore.getTitle()){
            case "cama" :holder.logo.setScaleType(ImageView.ScaleType.FIT_CENTER);holder.logo.setImageResource(R.mipmap.p1);holder.title.setText("cama cafe：");break;
            case "chatime":holder.logo.setImageResource(R.mipmap.p2);holder.title.setText("日出茶太：");break;
            case "chingshin":holder.logo.setImageResource(R.mipmap.p3);holder.title.setText("清心福全：");break;
            case "chunshuitang":holder.logo.setImageResource(R.mipmap.p4);holder.title.setText("春水堂：");break;
            case "coco":holder.logo.setImageResource(R.mipmap.p5);holder.title.setText("coco 都可：");break;
            case "comebuy":holder.logo.setImageResource(R.mipmap.p6);holder.title.setText("Come Buy：");break;
            case "dante":holder.logo.setImageResource(R.mipmap.p7);holder.title.setText("丹堤咖啡：");break;
            case "dayungs":holder.logo.setImageResource(R.mipmap.p8);holder.title.setText("大苑子：");break;
            case "eightyfive":holder.logo.setImageResource(R.mipmap.p9);holder.title.setText("85度C：");break;
            case "fiftylen":holder.logo.setImageResource(R.mipmap.p10);holder.title.setText("50嵐：");break;
            case "kingyo":holder.logo.setImageResource(R.mipmap.p11);holder.title.setText("清玉：");break;
            case "macu":holder.logo.setImageResource(R.mipmap.p12);holder.title.setText("麻古茶坊：");break;
            case "mrwith":holder.logo.setImageResource(R.mipmap.p13);holder.title.setText("Mr.Wish：");break;
            case "presotea":holder.logo.setImageResource(R.mipmap.p14);holder.title.setText("鮮茶道：");break;
            case "starbucks":holder.logo.setImageResource(R.mipmap.p15);holder.title.setText("星巴克：");break;
            case "teasoup":holder.logo.setImageResource(R.mipmap.p16);holder.title.setText("茶湯會：");break;
        }
        return convertView;
    }
    class Holder {
        ImageView logo;
        TextView title, name, tel, address;
    }
}
