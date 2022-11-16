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

public class getComment {
    private String number;
    private String [] name;
    private String [] id;
    private String [] comment;

    private String [] G_name;
    private String [] G_id;
    private String [] G_comment;

    public getComment(String num){
        this.number = num;
        getCommentData();
    }

    private void setComment(String [] id , String [] name ,String [] comment){
        this.id=id;
        this.name=name;
        this.comment=comment;
    }

    public String [] getID(){return this.id;}

    public String [] getName(){return  this.name;}

    public String [] getComment(){return this.comment; }

    public void getCommentData() {
        OkHttpClient client = new OkHttpClient();
        String HTTPURL = "http://163.13.201.94/107_SD/getcomment.php";
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject ja = new JSONObject(response.body().string());
                    JSONArray data = ja.getJSONArray("data");
                    G_id=new String[data.length()];
                    G_name=new String[data.length()];
                    G_comment=new String[data.length()];
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject ob = data.getJSONObject(i);
                        if(number.equals(ob.getString("number"))){
                            G_id[i]=ob.getString("id");
                            G_name[i]=ob.getString("name");
                            G_comment[i]=ob.getString("comment");
                        }
                        setComment(G_id,G_name,G_comment);
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
