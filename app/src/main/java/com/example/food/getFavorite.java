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

public class getFavorite {

    private String name;
    private String [] id;
    private String [] favorite;

    private String [] G_id;
    private String [] G_favorite;

    public getFavorite(String user){
        this.name=user;
        getfavorite();
    }
    private void setFavorite(String [] id ,String [] favorite){
        int length=0;
        for(int i=0;i<id.length;i++){
            if(id[i]!=null) length++;
        }
        this.id=new String[length];
        this.favorite=new String[length];
        for(int i=0;i<length;i++){
            this.id[i]=id[i];
            this.favorite[i]=favorite[i];
        }
    }
    public String [] getID(){ return this.id;}

    public String [] getFavorite(){return this.favorite;}

    public void getfavorite() {
        OkHttpClient client = new OkHttpClient();
        String HTTPURL = "http://163.13.201.94/107_SD/getfavoriteinfo.php";
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject ja = new JSONObject(response.body().string());
                    JSONArray data = ja.getJSONArray("data");
                    G_id=new String[data.length()];
                    G_favorite=new String[data.length()];
                    int j=0;
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject ob = data.getJSONObject(i);
                        if(name.equals(ob.getString("name"))){
                            G_id[j]=ob.getString("id");
                            G_favorite[j]=ob.getString("favorite");
                            j++;
                        }
                    }
                    setFavorite(G_id,G_favorite);
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
