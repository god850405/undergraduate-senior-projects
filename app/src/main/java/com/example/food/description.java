package com.example.food;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class description extends AppCompatActivity {
    private ImageView img ;
    private TextView time;
    private TextView summary;
    private TextView address;
    private TextView telphone;

    private String description_img;
    private String description_title;
    private String description_summary;
    private String description_add;
    private String description_tel;
    private String description_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent=getIntent();
        description_img = intent.getStringExtra("description_img");
        description_title = intent.getStringExtra("description_title");
        description_summary = intent.getStringExtra("description_summary");
        description_add = intent.getStringExtra("description_add");
        description_tel = intent.getStringExtra("description_tel");
        description_time = intent.getStringExtra("description_time");

        Toolbar desToolbar = (Toolbar)findViewById(R.id.desToolbar);
        desToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        desToolbar.setTitle(description_title);
        desToolbar.setNavigationIcon(R.drawable.back);
        desToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        System.out.println("======"+description_img);
        System.out.println("======"+description_title);
        System.out.println("======"+description_summary);
        System.out.println("======"+description_add);
        System.out.println("======"+description_tel);

        img=(ImageView)findViewById(R.id.iv);
        time=(TextView)findViewById(R.id.time);
        summary=(TextView)findViewById(R.id.summary);
        address=(TextView)findViewById(R.id.add);
        telphone=(TextView)findViewById(R.id.tel);


        time.setText(description_time);
        summary.setText(description_summary);
        address.setText(description_add);
        telphone.setText(description_tel);
        /*if(description_img!=""){
            convertBitMap(description_img);
        }else{
            img.setImageResource(R.drawable.pic_null);
        }*/
        if(description_img.isEmpty()){
            img.setImageResource(R.drawable.pic_null);
        }else{
            img.setTag(description_img);
            ImageTask it = new ImageTask();
            it.execute(description_img);
        }
    }
    class ImageTask extends AsyncTask<String, Void, BitmapDrawable> {

        private String imageUrl;

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downloadImage();
            BitmapDrawable db = new BitmapDrawable(getResources(),
                    bitmap);
            return db;
        }

        @Override
        protected void onPostExecute(BitmapDrawable result) {
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            if (img != null && result != null) {
                img.setImageDrawable(result);
            }
        }
        /**
         * 根据url从网络上下载图片
         *
         * @return
         */
        private Bitmap downloadImage() {
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }

            return bitmap;
        }

    }
}
