package com.example.food;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Mark on 2018/6/23.
 */

public class getDiaryData {
    private String [] G_name;
    private String [] G_number;
    private String [] G_username;
    private String [] G_title;
    private String [] G_address;
    private String [] G_content;
    private String [] G_createdate;

    private String [] name;
    private String [] number;
    private String [] username;
    private String [] title;
    private String [] address;
    private String [] content;
    private String [] createdate;

    public getDiaryData(){
        getData();
    }
    private void setData(String [] G_number,String [] G_name,String [] G_username ,String [] G_title,String [] G_address,String [] G_content,String [] G_createdate){
        this.number=G_number;
        this.name=G_name;
        this.username=G_username;
        this.title=G_title;
        this.address=G_address;
        this.content=G_content;
        this.createdate=G_createdate;
    }
    public String[] getNumber(){return this.number;  }
    public String[] getName(){return this.name;}
    public String[] getUsername(){return this.username;}
    public String[] getTitle(){return this.title;}
    public String[] getAddress(){return this.address;}
    public String[] getContent(){return this.content;}
    public String[] getCreatedate(){return this.createdate;}

    private void getData() {
        OkHttpClient client = new OkHttpClient();
        String HTTPURL = "http://163.13.201.94/107_SD/food_diary.php";
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject ja = new JSONObject(response.body().string());
                    JSONArray data = ja.getJSONArray("data");
                    G_number=new String[data.length()];
                    G_name=new String[data.length()];
                    G_username=new String[data.length()];
                    G_title=new String[data.length()];
                    G_address=new String[data.length()];
                    G_content=new String[data.length()];
                    G_createdate=new String[data.length()];
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject ob = data.getJSONObject(i);
                        G_number[i]=ob.getString("number");
                        G_name[i]=ob.getString("name");
                        G_username[i]=ob.getString("username");
                        G_title[i]= ob.getString("title");
                        G_address[i] = ob.getString("address");
                        G_content[i] = ob.getString("content");
                        G_createdate[i]=ob.getString("createdate");
                    }
                    setData(G_number,G_name,G_username,G_title,G_address,G_content,G_createdate);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Request arg0, IOException arg1) {
            }
        });
    }
}
