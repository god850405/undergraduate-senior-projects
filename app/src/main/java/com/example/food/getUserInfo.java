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
 * Created by Mark on 2018/6/22.
 */

public class getUserInfo {
    private String  id;
    private String  username;
    private String  email;
    private String  tel;
    public getUserInfo(String user){
        this.id=user;
        getInfo();
    }
    public getUserInfo(){

    }
    void set(String id ,String username,String email,String tel){
        this.id=id;
        this.username=username;
        this.email=email;
        this.tel=tel;
    }
    public String getId(){return this.id;}
    public String getUsername(){
        while (this.username==null){
            try{
                Thread.sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            if(this.username!=null) break;
        }
        return this.username;
    }
    public String getUsername(String user){
        this.id=user;
        getInfo();
        return this.username;
    }
    public String getEmail(){return this.email;}
    public String getTel(){return this.tel;}

    public void getInfo() {
        System.out.println("----------------------GET USER DATA------------------------");
        OkHttpClient client = new OkHttpClient();
        String HTTPURL = "http://163.13.201.94/107_SD/getuserinfo.php";
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject ja = new JSONObject(response.body().string());
                    JSONArray data = ja.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject ob = data.getJSONObject(i);
                        if(id.equals(ob.getString("name"))){
                            set(ob.getString("id"),ob.getString("username"),ob.getString("email"),ob.getString("tel"));
                        }
                    }
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