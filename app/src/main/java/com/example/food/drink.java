package com.example.food;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

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

public class drink extends AppCompatActivity {
    private ListView lv;
    private List<drinkstore> list= new ArrayList<drinkstore>();
    private List<drinkstore> newlist = new ArrayList<drinkstore>();
    public String [] store_title;
    public String [] store_name;
    public String [] store_tel;
    public String [] store_address;
    private EditText search;
    ProgressDialog pd;
    Spinner spinnerName;
    Spinner spinnerCity;
    private boolean isSelect=false;
    private int ChoosePosition;
    final String[] county = {"請選擇縣市","基隆市", "台北市", "新北市", "桃園市", "新竹市", "新竹縣", "苗栗縣", "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "台南市", "高雄市", "屏東縣", "台東縣", "花蓮縣", "宜蘭縣", "澎湖縣", "金門縣", "連江縣"};
    final String[] store ={"請選擇店家","cama cafe","日出茶太","清心福全","春水堂","coco都可","ComeBuy","丹堤咖啡","大苑子","85度C","50嵐","清玉","麻古茶坊","Mr.wish","鮮茶道","星巴克","茶湯會"};
    final String[] storeName={"cama","chatime","chingshin","chunshuitang","coco","comebuy","dante","dayungs","eightyfive","fiftylen","kingyo","macu","mrwith","presotea","starbucks","teasoup"};
    private String HTTPURL="http://163.13.201.94/107_SD/drinkstore.php";
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    System.out.println("----------Handler [0]-----------");
                    DrinkAdapter adapter = new DrinkAdapter(list);
                    lv.setAdapter(adapter);
                    pd.dismiss();
                    break;
                case 1:
                    System.out.println("----------Handler [1]-----------");
                    adapter = new DrinkAdapter(newlist);
                    lv.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        final ArrayAdapter<String> CountyList = new ArrayAdapter<>(drink.this,
                android.R.layout.simple_spinner_dropdown_item,
                county);
        final ArrayAdapter<String> StoreList = new ArrayAdapter<>(drink.this,
                android.R.layout.simple_spinner_dropdown_item,
                store);
        spinnerCity = (Spinner)findViewById(R.id.city);
        spinnerName = (Spinner)findViewById(R.id.name);
        spinnerCity.setAdapter(CountyList);
        spinnerName.setAdapter(StoreList);
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                }else{
                    newlist.clear();
                    newlist=getNewDataByAdd(county[position],isSelect);
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    isSelect=false;
                    initData();
                    spinnerCity.setAdapter(CountyList);
                }else{
                    newlist.clear();
                    newlist=getNewDataByName(storeName[position-1]);
                    isSelect=true;
                    spinnerCity.setAdapter(CountyList);
                    ChoosePosition=position-1;
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Toolbar DRToolbar = (Toolbar)findViewById(R.id.DRToolbar);
        DRToolbar.setTitle("找飲料店");
        DRToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        DRToolbar.setNavigationIcon(R.drawable.back);
        DRToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv =(ListView) findViewById(R.id.liv);
        search = (EditText)findViewById(R.id.search);
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
                    spinnerCity.setVisibility(View.VISIBLE);
                    spinnerName.setVisibility(View.VISIBLE);
                    mHandler.obtainMessage(0).sendToTarget();
                }else{
                    spinnerCity.setVisibility(View.GONE);
                    spinnerName.setVisibility(View.GONE);
                    spinnerCity.setAdapter(CountyList);
                    spinnerName.setAdapter(StoreList);
                    newlist.clear();
                    newlist = getNewData(s.toString());
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }
        });
    }
    private List<drinkstore> getNewData(String input_info){
        for(int i=0;i<list.size();i++){
            drinkstore drinkstore = list.get(i);
            if(drinkstore.getAddress().contains(input_info)){
                drinkstore drinkstore1 = new drinkstore(drinkstore.getTitle(),drinkstore.getName(),drinkstore.getTel(),drinkstore.getAddress());
                newlist.add(drinkstore1);
            }
        }
        return newlist;
    }
    private List<drinkstore> getNewDataByAdd(String input_info ,boolean isSelect){
        if(isSelect){
            int count =0;
            for(int i=0;i<list.size();i++){
                drinkstore drinkstore = list.get(i);
                if(drinkstore.getAddress().contains(input_info)&&drinkstore.getTitle().equals(storeName[ChoosePosition])){
                    count++;
                    drinkstore drinkstore1 = new drinkstore(drinkstore.getTitle(),drinkstore.getName(),drinkstore.getTel(),drinkstore.getAddress());
                    System.out.println("----------測試更改 Title --------"+drinkstore.getTitle());
                    System.out.println("----------測試更改 Address ------"+drinkstore.getName());
                    System.out.println("@@@@@@@@@@@@@@@@"+count+"@@@@@@@@@@@@@@@@@@@");
                    newlist.add(drinkstore1);
                }
            }
        }else{
            int count =0;
            for(int i=0;i<list.size();i++){
                drinkstore drinkstore = list.get(i);
                if(drinkstore.getAddress().contains(input_info)){
                    count++;
                    drinkstore drinkstore1 = new drinkstore(drinkstore.getTitle(),drinkstore.getName(),drinkstore.getTel(),drinkstore.getAddress());
                    System.out.println("----------測試更改 Title --------"+drinkstore.getTitle());
                    System.out.println("----------測試更改 Address ------"+drinkstore.getName());
                    System.out.println("@@@@@@@@@@@@@@@@"+count+"@@@@@@@@@@@@@@@@@@@");
                    newlist.add(drinkstore1);
                }
            }
        }

        return newlist;
    }
    private List<drinkstore> getNewDataByName(String input_info){
        int count =0;
        for(int i=0;i<list.size();i++){
            drinkstore drinkstore = list.get(i);
            if(drinkstore.getTitle().equals(input_info)){
                count++;
                drinkstore drinkstore1 = new drinkstore(drinkstore.getTitle(),drinkstore.getName(),drinkstore.getTel(),drinkstore.getAddress());
                System.out.println("----------測試更改 Title --------"+drinkstore.getTitle());
                System.out.println("----------測試更改 Address ------"+drinkstore.getName());
                System.out.println("@@@@@@@@@@@@@@@@"+count+"@@@@@@@@@@@@@@@@@@@");
                newlist.add(drinkstore1);
            }
        }
        return newlist;
    }
    private void initData() {
        pd = android.app.ProgressDialog.show(drink.this,"資料處理中...","請稍等...");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    drinkstore drinkstore=null;
                    JSONObject ja = new JSONObject(response.body().string());
                    JSONArray data = ja.getJSONArray("Info");
                    store_title = new String[data.length()];
                    store_name = new String[data.length()];
                    store_tel = new String[data.length()];
                    store_address = new String[data.length()];
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject ob = data.getJSONObject(i);
                        store_title[i] = ob.getString("title");
                        store_name[i] = ob.getString("name");
                        store_tel[i] = ob.getString("tel");
                        store_address[i] = ob.getString("address");
                        drinkstore = new drinkstore(store_title[i],store_name[i],store_tel[i],store_address[i]);
                        list.add(drinkstore);
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
}
