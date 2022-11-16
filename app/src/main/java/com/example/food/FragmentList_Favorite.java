package com.example.food;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Solinari on 2016/12/31.
 */

public class FragmentList_Favorite extends Fragment {
    private ListView listView_diary;
    private List<FavoriteData> list_diary;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("@@@@@@", "this is get message");
            switch (msg.what) {
                case 0:
                    FavoriteAdapter  fa = new FavoriteAdapter(list_diary,getContext());
                    listView_diary.setAdapter(fa);
                    break;
                default:
                    break;
            }
            //mAdapter.notifyDataSetChanged();
        }
    };
    private String User_id;
    String []F_number;
    String []F_name;
    String []F_username;
    String []F_title;
    String []F_add;
    String []F_des;
    String []F_crd;
    ArrayList fnumber = new ArrayList();
    ArrayList fname = new ArrayList();
    ArrayList fusername = new ArrayList();
    ArrayList ftitle = new ArrayList();
    ArrayList fadd = new ArrayList();
    ArrayList fdes = new ArrayList();
    ArrayList fcrd = new ArrayList();
    String [] F_favorite;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_list__favorite, container, false);
        Intent intent=getActivity().getIntent();
        User_id = intent.getStringExtra("id");
        listView_diary = (ListView) view.findViewById(R.id.list_diaryfavorite);
        listView_diary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(),diary_description.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",User_id);
                bundle.putString("diary_description_number",fnumber.get(arg2).toString());
                bundle.putString("diary_description_name", fname.get(arg2).toString());
                bundle.putString("diary_description_username", fusername.get(arg2).toString());
                bundle.putString("diary_description_title", ftitle.get(arg2).toString());
                bundle.putString("diary_description_add", fadd.get(arg2).toString());
                bundle.putString("diary_description_description", fdes.get(arg2).toString());
                bundle.putString("diary_description_createdate", fcrd.get(arg2).toString());
                //將Bundle物件assign給intent
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView_diary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                view.setBackgroundColor(Color.GRAY);
                new AlertDialog.Builder(view.getContext())
                        .setTitle("確定要取消收藏嗎?")
                        .setMessage("確定要取消收藏嗎?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                view.setBackgroundResource(0);
                            }
                        })
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                create(User_id,fnumber.get(position).toString());
                                view.setBackgroundResource(0);
                            }
                        })
                        .show();
                return false;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("##################每次執行#####################");
                    fnumber.clear();
                    fname.clear();
                    fusername.clear();
                    ftitle.clear();
                    fadd.clear();
                    fdes.clear();
                    fcrd.clear();
                    getFavoriteInfo();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return view;
    }
    private void getFavoriteInfo(){
        getFavorite gf = new getFavorite(User_id);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        F_favorite=gf.getFavorite();
        for(int i=0;i<F_favorite.length;i++){
            System.out.println("-------------"+F_favorite[i]+"------------------");
        }
        getdata(F_favorite);
    }
    private void getdata(final String [] favorite){
        list_diary = new ArrayList<FavoriteData>();
        getDiaryData gdd = new getDiaryData();
        FavoriteData fd = null;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        F_number=gdd.getNumber();
        F_name=gdd.getName();
        F_username=gdd.getUsername();
        F_title=gdd.getTitle();
        F_add=gdd.getAddress();
        F_des=gdd.getContent();
        F_crd=gdd.getCreatedate();
        for(int i=favorite.length-1;i>=0;i--){
            for(int j=0 ;j<F_number.length;j++){
                if(favorite[i].equals(F_number[j])){
                    fnumber.add(F_number[j]);
                    fname.add(F_name[j]);
                    fusername.add(F_username[j]);
                    ftitle.add(F_title[j]);
                    fadd.add(F_add[j]);
                    fdes.add(F_des[j]);
                    fcrd.add(F_crd[j]);
                    fd=new FavoriteData(F_number[j],F_username[j],F_title[j],F_add[j],F_des[j],F_crd[j]);
                    list_diary.add(fd);
                }
            }
        }
        mHandler.obtainMessage(0).sendToTarget();
    }
    private void create(String name , String favorite){
        System.out.println("-----刪除"+name+"--"+favorite+"------");
        class RegisterUser extends AsyncTask<String, Void, String> {
            HTTPconnect ruc = new HTTPconnect();/**使用Creatmem.class的功能**/
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();/**當按下創見鈕，出現提式窗**/
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String[] splitans = s.split(",");
                System.out.println("-------------"+splitans[0]+"-------------");
                if(splitans[0].equals("Create Successful"))/**當字串比對成功返回登入頁面**/
                {
                    Toast.makeText(getContext(), "收藏", Toast.LENGTH_SHORT).show();
                    fnumber.clear();
                    fname.clear();
                    fusername.clear();
                    ftitle.clear();
                    fadd.clear();
                    fdes.clear();
                    fcrd.clear();
                    getFavoriteInfo();
                }
                if(splitans[0].equals("Delete Successful"))/**當字串比對成功返回登入頁面**/
                {
                    Toast.makeText(getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                    fnumber.clear();
                    fname.clear();
                    fusername.clear();
                    ftitle.clear();
                    fadd.clear();
                    fdes.clear();
                    fcrd.clear();
                    getFavoriteInfo();
                }
            }
            @Override
            protected String doInBackground(String... params)/**將資料放入hashmap**/
            {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("favorite",params[1]);
                String result = ruc.sendPostRequest("http://163.13.201.94/107_SD/favoriteinfo.php",data);
                return  result;
            }
        }
        RegisterUser ru = new RegisterUser();/**傳送資料**/
        ru.execute(name, favorite);
    }
}