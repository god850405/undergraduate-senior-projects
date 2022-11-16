package com.example.food;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Mark on 2018/7/2.
 */

public class Fragment_des extends Fragment{

    private TextView name;
    private TextView title;
    private TextView add;
    private TextView des;
    private TextView crd;
    private ImageView img;
    private ImageButton imgbtn1;
    private ImageButton imgbtn2;
    String diary_description_number;
    String diary_description_name;
    String diary_description_username;
    String diary_description_title;
    String diary_description_add;
    String diary_description_description;
    String diary_description_createdate;
    String id;
    ArrayList photo = new ArrayList();
    Bitmap [] des_bitmap ;
    private int cnt=0;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    img.setImageBitmap(des_bitmap[cnt]);
                    if(cnt==0) {
                        if(photo.size()<2){
                            img.setVisibility(View.VISIBLE);
                            imgbtn1.setVisibility(View.INVISIBLE);
                            imgbtn2.setVisibility(View.INVISIBLE);
                        }else{
                            img.setVisibility(View.VISIBLE);
                            imgbtn1.setVisibility(View.INVISIBLE);
                            imgbtn2.setVisibility(View.VISIBLE);
                        }
                    }else if(cnt==(des_bitmap.length-1)){
                        imgbtn1.setVisibility(View.VISIBLE);
                        imgbtn2.setVisibility(View.INVISIBLE);
                    } else{
                        imgbtn1.setVisibility(View.VISIBLE);
                        imgbtn2.setVisibility(View.VISIBLE);
                    }
                    break;
                case 1:
                    cnt--;
                    img.setImageBitmap(des_bitmap[cnt]);
                    if(cnt==0) {
                        imgbtn1.setVisibility(View.INVISIBLE);
                        imgbtn2.setVisibility(View.VISIBLE);
                    }else if(cnt==(des_bitmap.length-1)){
                        imgbtn1.setVisibility(View.VISIBLE);
                        imgbtn2.setVisibility(View.INVISIBLE);
                    } else{
                        imgbtn1.setVisibility(View.VISIBLE);
                        imgbtn2.setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    cnt++;
                    img.setImageBitmap(des_bitmap[cnt]);
                    if(cnt==0) {
                        imgbtn1.setVisibility(View.INVISIBLE);
                        imgbtn2.setVisibility(View.VISIBLE);
                    }else if(cnt==(des_bitmap.length-1)){
                        imgbtn1.setVisibility(View.VISIBLE);
                        imgbtn2.setVisibility(View.INVISIBLE);
                    } else{
                        imgbtn1.setVisibility(View.VISIBLE);
                        imgbtn2.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    if(photo.size()>0){
                       img.setVisibility(View.VISIBLE);
                       imgbtn1.setVisibility(View.VISIBLE);
                       imgbtn2.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }

        }

    };
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                    Bundle savedInstanceState) {
                View view = inflater.inflate(R.layout.fragment_onepage, container, false);
            Intent intent= getActivity().getIntent();
            id=intent.getStringExtra("id");
            diary_description_number = intent.getStringExtra("diary_description_number");
            diary_description_name = intent.getStringExtra("diary_description_name");
            diary_description_username = intent.getStringExtra("diary_description_username");
            diary_description_title = intent.getStringExtra("diary_description_title");
            diary_description_add = intent.getStringExtra("diary_description_add");
            diary_description_description = intent.getStringExtra("diary_description_description");
            diary_description_createdate = intent.getStringExtra("diary_description_createdate");

            name=(TextView)view.findViewById(R.id.diary_description_user);
            title=(TextView)view.findViewById(R.id.diary_description_title);
            add=(TextView)view.findViewById(R.id.diary_description_add);
            des=(TextView)view.findViewById(R.id.diary_description_description);
            crd=(TextView)view.findViewById(R.id.diary_description_createdate);
            img = (ImageView)view.findViewById(R.id.image);
            imgbtn1 = (ImageButton)view.findViewById(R.id.left);
            imgbtn2 = (ImageButton)view.findViewById(R.id.right);
            imgbtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cnt!=0){
                        mHandler.obtainMessage(1).sendToTarget();
                    }
                }
            });
            imgbtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cnt<des_bitmap.length-1) {
                        mHandler.obtainMessage(2).sendToTarget();
                    }
                }
            });

            name.setText(diary_description_username);
            title.setText(diary_description_title);
            add.setText(diary_description_add);
            des.setText(diary_description_description);
            crd.setText(diary_description_createdate);
            getPhoto();
            return view;
        }
    public void convertBitMap() {
        des_bitmap = new Bitmap[photo.size()];
        Bitmap bitmap =null;
        URL myFileUrl = null;
        for(int i=0;i<photo.size();i++){
            System.out.println("-----------URL :"+photo.get(i).toString()+"   ---------------------");
            try {
                myFileUrl = new URL(photo.get(i).toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            des_bitmap[i]=bitmap;
        }
        mHandler.obtainMessage(0).sendToTarget();
    }
    private void getPhoto() {
        OkHttpClient client = new OkHttpClient();
        String HTTPURL = "http://163.13.201.94/107_SD/getdiaryphoto.php";
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject ja = new JSONObject(response.body().string());
                    JSONArray data = ja.getJSONArray("data");
                    for (int i =0;i<data.length();i++) {
                        JSONObject ob = data.getJSONObject(i);
                       if(diary_description_number.equals(ob.getString("number"))){
                           photo.add("http://163.13.201.94/107_SD/uploads/"+ob.getString("photourl"));
                       }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.obtainMessage(3).sendToTarget();
                if(photo.size()>0){
                    convertBitMap();
                }
            }
            @Override
            public void onFailure(Request arg0, IOException arg1) {
            }
        });

    }

}
