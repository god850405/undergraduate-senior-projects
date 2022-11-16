package com.example.food;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mark on 2018/7/2.
 */

public class Fragment_com extends Fragment {
    private ListView lv_comment;
    private List<Comment> list;
    private String id , number;
    private int starCount=0;
    private String [] Number;
    private String [] UserName;
    private String []  ComMent;
    private int [] Star;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.v("@@@@@@", "this is get message");
            switch (msg.what) {
                case 0:
                    CommentAdapter adapter = new CommentAdapter(list);
                    //setListViewHeightBasedOnChildren(lv_comment);
                    lv_comment.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_twopage, container, false);
        Intent intent=getActivity().getIntent();
        id = intent.getStringExtra("id");
        number = intent.getStringExtra("diary_description_number");
        lv_comment=(ListView)view.findViewById(R.id.comment);

        final EditText com = (EditText)view.findViewById(R.id.editText);
        final ImageView img1 =(ImageView)view.findViewById(R.id.star_img1);
        final ImageView img2 =(ImageView)view.findViewById(R.id.star_img2);
        final ImageView img3 =(ImageView)view.findViewById(R.id.star_img3);
        final ImageView img4 =(ImageView)view.findViewById(R.id.star_img4);
        final ImageView img5 =(ImageView)view.findViewById(R.id.star_img5);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCount=1;
                img1.setImageResource(R.mipmap.star);
                img2.setImageResource(R.mipmap.star_null);
                img3.setImageResource(R.mipmap.star_null);
                img4.setImageResource(R.mipmap.star_null);
                img5.setImageResource(R.mipmap.star_null);

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCount=2;
                img1.setImageResource(R.mipmap.star);
                img2.setImageResource(R.mipmap.star);
                img3.setImageResource(R.mipmap.star_null);
                img4.setImageResource(R.mipmap.star_null);
                img5.setImageResource(R.mipmap.star_null);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCount=3;
                img1.setImageResource(R.mipmap.star);
                img2.setImageResource(R.mipmap.star);
                img3.setImageResource(R.mipmap.star);
                img4.setImageResource(R.mipmap.star_null);
                img5.setImageResource(R.mipmap.star_null);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCount=4;
                img1.setImageResource(R.mipmap.star);
                img2.setImageResource(R.mipmap.star);
                img3.setImageResource(R.mipmap.star);
                img4.setImageResource(R.mipmap.star);
                img5.setImageResource(R.mipmap.star_null);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starCount=5;
                img1.setImageResource(R.mipmap.star);
                img2.setImageResource(R.mipmap.star);
                img3.setImageResource(R.mipmap.star);
                img4.setImageResource(R.mipmap.star);
                img5.setImageResource(R.mipmap.star);
            }
        });
        Button send = (Button)view.findViewById(R.id.button2);
        send.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (starCount!=0) {
                    String com_text = com.getText().toString();
                    String starcount =String.valueOf(starCount);
                    createComment(number,id,com_text,starcount);
                    img1.setImageResource(R.mipmap.star_null);
                    img2.setImageResource(R.mipmap.star_null);
                    img3.setImageResource(R.mipmap.star_null);
                    img4.setImageResource(R.mipmap.star_null);
                    img5.setImageResource(R.mipmap.star_null);
                    com.setText("");
                    starCount=0;
                }
            }
        });
        getComment();
        return  view;
    }
    private void getComment() {
        list = new ArrayList<Comment>();
        OkHttpClient client = new OkHttpClient();
        String HTTPURL = "http://163.13.201.94/107_SD/getcomment.php";
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                Comment comment = null;
                try {
                    JSONObject ja = new JSONObject(response.body().string());
                    JSONArray data = ja.getJSONArray("data");
                    Number = new String[data.length()];
                    UserName=new String[data.length()];
                    ComMent=new String[data.length()];
                    Star=new int[data.length()];
                    for (int i = data.length()-1; i>=0; i--) {
                        JSONObject ob = data.getJSONObject(i);
                        Number[i]=ob.getString("number");
                        UserName[i]=ob.getString("username");
                        ComMent[i]=ob.getString("comment");
                        Star[i]=ob.getInt("star");
                        if(Number[i].equals(number)){
                            comment = new Comment(UserName[i],ComMent[i],Star[i]);
                            System.out.println("--------User = " + UserName[i]+"-------");
                            System.out.println("--------Comment = " + ComMent[i]+"-------");
                            System.out.println("--------Star = " + Star[i]+"-------");
                            System.out.println("----*******************************-------");
                            list.add(comment);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mHandler.obtainMessage(0).sendToTarget();
            }
            @Override
            public void onFailure(Request arg0, IOException arg1) {
            }
        });
    }
    private void createComment(String number,String name, String comment, String star) {
        class CreateComment extends AsyncTask<String, Void, String> {
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
                    Toast.makeText(getActivity(), "評論成功", Toast.LENGTH_SHORT).show();
                    getComment();
                }
            }
            @Override
            protected String doInBackground(String... params)/**將資料放入hashmap**/
            {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("number",params[0]);
                data.put("name",params[1]);
                data.put("comment",params[2]);
                data.put("star",params[3]);
                String result = ruc.sendPostRequest("http://163.13.201.94/107_SD/comment.php",data);
                return  result;
            }
        }
        CreateComment ru = new CreateComment();/**傳送資料**/
        ru.execute(number, name,comment, star);
    }
}
