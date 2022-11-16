package com.example.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * Created by Mark on 2018/5/26.
 */

/**
 * Created by Solinari on 2016/12/31.
 */

public class FragmentList_self extends Fragment  {
    private String id;
    private ListView listView;
    private ArrayAdapter adapter;
    public String [] content_number;
    public String [] content_name ;
    public String [] content_username;
    public String [] content_title ;
    public String [] content_add ;
    public String [] content_summary ;
    public String [] content_createdate ;
    int g[]=new int[9999999];
    int j=0;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_list_self, container, false);
        Intent intent=getActivity().getIntent();
        id = intent.getStringExtra("id");
        listView = (ListView) view.findViewById(R.id.list_diaryself);

        // 清單陣列
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1);
        getdata();
        try {
            Thread.sleep(1000);
            for(int i=0;i<content_name.length;i++){
                if(content_name[i].equals(id)){
                    adapter.add(content_title[i]);
                    g[j]=i;
                    j++;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                ListView listView = (ListView) arg0;
                Intent intent = new Intent(getActivity(),diary_description.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("diary_description_number",content_number[g[arg2]]);
                bundle.putString("diary_description_name", content_name[g[arg2]]);
                bundle.putString("diary_description_username", content_username[g[arg2]]);
                bundle.putString("diary_description_title", content_title[g[arg2]]);
                bundle.putString("diary_description_add", content_add[g[arg2]]);
                bundle.putString("diary_description_description", content_summary[g[arg2]]);
                bundle.putString("diary_description_createdate", content_createdate[g[arg2]]);
                //將Bundle物件assign給intent
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        return view;
    }
    private void getdata(){
        getDiaryData gdd = new getDiaryData();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        content_number=gdd.getNumber();
        content_name=gdd.getName();
        content_username=gdd.getUsername();
        content_title=gdd.getTitle();
        content_add=gdd.getAddress();
        content_summary=gdd.getContent();
        content_createdate=gdd.getCreatedate();
    }
}