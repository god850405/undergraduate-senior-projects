package com.example.food;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class food_diary extends AppCompatActivity {
    private String id;
    private ViewPager myViewPager;
    private TabLayout tabLayout;
    private int[] IconResID = {R.drawable.newdd,R.drawable.note,R.mipmap.favorite};
    private String[] TitleID = {"最新食記","我的食記","收藏"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_diary);



        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        setViewPager();
        tabLayout.setBackgroundColor(Color.parseColor("#FF8C00"));
        tabLayout.setupWithViewPager(myViewPager);
        setTabLayoutIcon();

        Toolbar dToolbar;
        dToolbar = (Toolbar) findViewById(R.id.toolbar_diary);
        dToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        dToolbar.inflateMenu(R.menu.diary);
        dToolbar.setTitle("美食日誌");
        dToolbar.setNavigationIcon(R.drawable.back);
        dToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_write:
                        Intent intent = new Intent(food_diary.this, diary.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        //將Bundle物件assign給intent
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        Bundle bundle = this.getIntent().getExtras();/**接收首頁傳來的user ID**/
        id = bundle.getString("id");
        System.out.println("--------------------------------ID="+id+"------------------------------------------");


    }
    public void setTabLayoutIcon(){
        for(int i =0; i < IconResID.length;i++){
            tabLayout.getTabAt(i).setIcon(IconResID[i]);
            tabLayout.getTabAt(i).setText(TitleID[i]);
        }

    }
    private void setViewPager(){
        FragmentList_Content myFragment1 = new FragmentList_Content();
        FragmentList_self myFragment2 = new FragmentList_self();
        FragmentList_Favorite myFragment3 = new FragmentList_Favorite();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myFragment1);
        fragmentList.add(myFragment2);
        fragmentList.add(myFragment3);
        ViewPagerFragmentAdapter myFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), fragmentList);
        myViewPager.setAdapter(myFragmentAdapter);
    }

}
