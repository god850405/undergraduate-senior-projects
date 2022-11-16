package com.example.food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class diary_description extends AppCompatActivity {

    private TabLayout mTabs;
    private ViewPager mViewPager;
    private int[] IconResID = {R.drawable.home,R.drawable.commenticon};
    private String[] TitleID = {"介紹","評論"};
    String diary_description_number;
    String diary_description_name;
    String diary_description_username;
    String diary_description_title;
    String diary_description_add;
    String diary_description_description;
    String diary_description_createdate;
    String id;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.des_diary);

        mTabs=(TabLayout)findViewById(R.id.tabs);
        mViewPager= (ViewPager)findViewById(R.id.viewpager);
        setViewPager();
        mTabs.setBackgroundColor(Color.parseColor("#FF8C00"));
        mTabs.setupWithViewPager(mViewPager);
        setTabLayoutIcon();

        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        diary_description_number = intent.getStringExtra("diary_description_number");
        diary_description_name = intent.getStringExtra("diary_description_name");
        diary_description_username = intent.getStringExtra("diary_description_username");
        diary_description_title = intent.getStringExtra("diary_description_title");
        diary_description_add = intent.getStringExtra("diary_description_add");
        diary_description_description = intent.getStringExtra("diary_description_description");
        diary_description_createdate = intent.getStringExtra("diary_description_createdate");

        Toolbar diary_toolbar=(Toolbar)findViewById(R.id.diary_toolbar);
        diary_toolbar.inflateMenu(R.menu.description_menu);
        diary_toolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        diary_toolbar.setTitle(diary_description_title);
        diary_toolbar.setNavigationIcon(R.drawable.back);
        diary_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        diary_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_favorite:
                        create(id,diary_description_number);
                        break;
                }
                return false;
            }

        });
    }
    private void create(String name , String favorite){
        System.out.println("-----判斷"+name+"--"+favorite+"------");
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
                    Toast.makeText(diary_description.this, "收藏", Toast.LENGTH_SHORT).show();
                }
                if(splitans[0].equals("Delete Successful"))/**當字串比對成功返回登入頁面**/
                {
                    Toast.makeText(diary_description.this, "取消收藏", Toast.LENGTH_SHORT).show();
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
    public void setTabLayoutIcon(){
        for(int i =0; i < IconResID.length;i++){
            mTabs.getTabAt(i).setIcon(IconResID[i]);
            mTabs.getTabAt(i).setText(TitleID[i]);
        }
    }
    private void setViewPager(){
        Fragment_des myFragment1 = new Fragment_des();
        Fragment_com myFragment2 = new Fragment_com();
        List<android.support.v4.app.Fragment> fragmentList = new ArrayList<android.support.v4.app.Fragment>();
        fragmentList.add(myFragment1);
        fragmentList.add(myFragment2);
        ViewPagerFragmentAdapter myFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(myFragmentAdapter);
    }
}