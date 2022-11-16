package com.example.food;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.List;
/**
 * Created by Mark on 2018/5/26.
 */

    /**
     * Created by Solinari on 2016/12/31.
     */

    public class FragmentList_Content extends Fragment{
        private String id;
        private ListView listView_diary;
        private List<DiaryData> list_diary;
        private List<DiaryData> newlist = new ArrayList<DiaryData>();
        private List<DiaryData> selectlist = new ArrayList<>();
        private EditText search;
        public String [] content_number ;
        public String [] content_username;
        public String [] content_name ;
        public String [] content_title ;
        public String [] content_add ;
        public String [] content_summary ;
        public String [] content_createdate ;
        private String [] Number;
        private int [] Star;
        final DiaryComment diaryComment =new DiaryComment();
        private Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        DiaryAdapter dd = new DiaryAdapter(list_diary);
                        selectlist = list_diary ;
                        listView_diary.setAdapter(dd);
                        break;
                    case 1:
                        dd = new DiaryAdapter(newlist);
                        selectlist = newlist;
                        listView_diary.setAdapter(dd);
                        break;
                    default:
                        break;
                }
            }

        };
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_fragment_list_content, container, false);
            Intent intent=getActivity().getIntent();
            id = intent.getStringExtra("id");
            listView_diary = (ListView) view.findViewById(R.id.list_diary);
            listView_diary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(getActivity(),diary_description.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",id);
                    bundle.putString("diary_description_number",selectlist.get(arg2).getNumber());
                    bundle.putString("diary_description_name", selectlist.get(arg2).getName());
                    bundle.putString("diary_description_username", selectlist.get(arg2).getUserName());
                    bundle.putString("diary_description_title", selectlist.get(arg2).getTitle());
                    bundle.putString("diary_description_add", selectlist.get(arg2).getAddress());
                    bundle.putString("diary_description_description", selectlist.get(arg2).getSummary());
                    bundle.putString("diary_description_createdate", selectlist.get(arg2).getCreateDate());
                    //將Bundle物件assign給intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            search = (EditText)view.findViewById(R.id.search);
            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().isEmpty()){
                        mHandler.obtainMessage(0).sendToTarget();
                    }else{
                        newlist.clear();
                        newlist = getNewData(s.toString());
                        mHandler.obtainMessage(1).sendToTarget();
                    }
                }
            });
            getComment();
            return view;
        }
        private List<DiaryData> getNewData(String input_info){
            for(int i=0;i<list_diary.size();i++){
               DiaryData diaryData = list_diary.get(i);
                if(diaryData.getTitle().contains(input_info)){
                    DiaryData diaryData1 = new DiaryData(diaryData.getNumber(),diaryData.getTitle(),diaryData.getAddress(),diaryData.getUserName(),diaryData.getName(),diaryData.getSummary(),diaryData.getCreateDate(),diaryData.getCommentCount(),diaryData.getStarCount());
                    newlist.add(diaryData1);
                }
            }
            return newlist;
        }
        private void getdata(){
            list_diary = new ArrayList<DiaryData>();
            getDiaryData gdd = new getDiaryData();
            DiaryData dd = null;
            try {
                Thread.sleep(500);
                content_number=gdd.getNumber();
                content_name=gdd.getName();
                content_username=gdd.getUsername();
                content_title=gdd.getTitle();
                content_add=gdd.getAddress();
                content_summary=gdd.getContent();
                content_createdate=gdd.getCreatedate();
                for(int i=content_number.length-1;i>=0;i--){
                    dd = new DiaryData(content_number[i],content_title[i],content_add[i],content_username[i],content_name[i],content_summary[i],content_createdate[i],diaryComment.getCommentCount(content_number[i]),diaryComment.getStar(content_number[i]));
                    System.out.println("----撈食記資料 第"+i+" 筆-------");
                    list_diary.add(dd);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mHandler.obtainMessage(0).sendToTarget();
        }
        private void getComment() {
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
                            Number = new String[data.length()];
                            Star=new int[data.length()];
                            for (int i = 0;i<data.length(); i++) {
                                JSONObject ob = data.getJSONObject(i);
                                Number[i]=ob.getString("number");
                                Star[i]=ob.getInt("star");
                            }
                            diaryComment.setNumber(Number);
                            diaryComment.setStar(Star);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Request arg0, IOException arg1) {
                }
            });
            getdata();
        }
        class DiaryComment{
            String [] number;
            int [] star;
            public int getStar(String num){
                int sum=0;
                int c=0;
                int starCount;
                for(int i=0;i<this.star.length;i++){
                    if(num.equals(number[i])){
                        sum+=star[i];c++;
                    }
                }
                if(c!=0){
                    starCount=sum/c;
                }else{
                    starCount=0;
                }

                System.out.println("----------平均星星數---- "+starCount+" ---------------");
                return  starCount;
            }
            public int getCommentCount(String num){
                int Count=0;
                for(int i=0;i<this.number.length;i++){
                    if(num.equals(number[i])){
                        Count++;
                    }
                }
                System.out.println("--------------共 "+Count+" 則留言---------------");
                return Count;
            }
            public void setStar(int [] star){
                this.star=star;
            }
            public void setNumber(String [] number){
                this.number=number;
            }
        }
    }