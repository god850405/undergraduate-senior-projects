package com.example.food;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String user;
    String id=null;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        id = intent.getStringExtra("id");
        username = intent.getStringExtra("username");
        user =id;
        System.out.println("--------------------------------ID="+id+"------------------------------------------");
        TextView textView = findViewById(R.id.Text_ID);
        Toolbar mToolbar;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("好食記");
        mToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));


        if(id!=null){

            if(id=="out"){
                textView.setVisibility(View.INVISIBLE);
                mToolbar.inflateMenu(R.menu.menu_layout);
            }else{
                String m=username+"("+id+")";
                System.out.println("==========="+m);
                textView.setText(m);
                textView.setTextColor(Color.parseColor("#0018cc"));
                textView.setVisibility(View.VISIBLE);
                mToolbar.inflateMenu(R.menu.online);
                textView.setOnClickListener(new TextView.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,Profile.class);
                        System.out.println("--------------------------------User="+user+"------------------------------------------");
                        Bundle bundle = new Bundle();
                        bundle.putString("id", user);
                        //將Bundle物件assign給intent
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }else{
            textView.setVisibility(View.INVISIBLE);
            mToolbar.inflateMenu(R.menu.menu_layout);
        }
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_login:
                       Intent intent1 = new Intent(MainActivity.this, Login.class);
                       startActivity(intent1);
                       break;
                    case R.id.menu_register:
                       Intent intent2 = new Intent(MainActivity.this, Register.class);
                       startActivity(intent2);
                       break;
                    case R.id.menu_out:
                        Intent intent4 = new Intent(MainActivity.this, MainActivity.class);
                        intent4.putExtra("out","out");
                        startActivity(intent4);
                        break;
                }
                return false;
            }
        });
    }
    public void  ImageClick(View v){

        if(v.getId()==R.id.btn1){
            Intent intent = new Intent(MainActivity.this, restaurant.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn2){
            if(id==null){
                Toast.makeText(MainActivity.this, "請先登入!!", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(MainActivity.this, food_diary.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", user);
                //將Bundle物件assign給intent
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }else if(v.getId()==R.id.btn3) {
            Intent intent = new Intent(MainActivity.this, drink.class);
            startActivity(intent);
        }
    }
}

