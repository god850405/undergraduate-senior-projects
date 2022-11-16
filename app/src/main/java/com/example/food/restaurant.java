package com.example.food;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class restaurant extends AppCompatActivity {
    private ListView lv;
    private List<News> list= new ArrayList<News>();;
    private List<News> newlist = new ArrayList<News>();
    private List<News> selectlist = new ArrayList<>();
    public String [] description_img;
    public String [] description_title;
    public String [] description_summary;
    public String [] description_add ;
    public String [] description_tel ;
    public String [] description_time ;
    private EditText search;
    ProgressDialog pd;
    Spinner spinner_county;
    Spinner spinner_city;
    final String[] county = {"請選擇縣市","基隆市", "台北市", "新北市", "桃園市", "新竹市", "新竹縣", "苗栗縣", "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "台南市", "高雄市", "屏東縣", "台東縣", "花蓮縣", "宜蘭縣", "澎湖縣", "金門縣", "連江縣"};
    final String[] Default_city ={"請先選擇縣市"};
    final String[] Taipei_city ={"全部","松山區","大安區","大同區","中山區","內湖區","南港區","士林區","北投區","信義區","中正區","萬華區","文山區"};
    final String[] Taichung_city ={"全部","豐原區","東勢區","大甲區","清水區","沙鹿區","梧棲區","后里區","神岡區","潭子區","大雅區","新社區","石岡區","外埔區","大安區","烏日區","大肚區","龍井區","霧峰區","太平區","大里區","和平區","中區","東區","西區","南區","北區","西屯區","南屯區","北屯區"};
    final String[] Tainan_city = {"全部","新營區","鹽水區","白河區","麻豆區","佳里區","新化區","善化區","學甲區","柳營區","後壁區","東山區","下營區","六甲區","官田區","大內區","西港區","七股區","將軍區","北門區","新市區","安定區","山上區","玉井區","楠西區","南化區","左鎮區","仁德區","歸仁區","關廟區","龍崎區","永康區","東區","南區","中西區","北區","安南區","安平區"};
    final String[] Kaohsiung_city = {"全部","鳳山區","岡山區","旗山區","美濃區","林園區","大寮區","大樹區","仁武區","大社區","鳥松區","橋頭區","燕巢區","田寮區","阿蓮區","路竹區","湖內區","茄萣區","永安區","彌陀區","梓官區","六龜區","甲仙區","杉林區","內門區","茂林區","桃園區","那瑪夏區","鹽埕區","鼓山區","左營區","楠梓區","三民區","新興區","前金區","苓雅區","前鎮區","旗津區","小港區"};
    final String[] Keelung_city = {"全部","中正區","七堵區","暖暖區","仁愛區","中山區","安樂區","信義區"};
    final String[] Hsinchu_city ={"全部","東區","北區","香山區"};
    final String[] Chiayi_city = {"全部","東區","西區"};
    final String[] New_Taipei_city = {"全部","板橋區","三重區","永和區","中和區","新店區","新莊區","樹林區","鶯歌區","三峽區","淡水區","汐止區","瑞芳區","土城區","蘆洲區","五股區","泰山區","林口區","深坑區","石碇區","坪林區","三芝區","石門區","八里區","平溪區","雙溪區","貢寮區","金山區","萬里區","烏來區"};
    final String[] Taoyuan_city ={"全部","桃園區","中壢區","大溪區","楊梅區","蘆竹區","大園區","龜山區","八德區","龍潭區","平鎮區","新屋區","觀音區","復興區"};
    final String[] Hsinchu ={"全部","關西鎮","新埔鎮","竹東鎮","竹北市","湖口鄉","橫山鄉","新豐鄉","芎林鄉","寶山鄉","北埔鄉","峨眉鄉","尖石鄉","五峰鄉"};
    final String[] Yilan = {"全部","宜蘭市","羅東鎮","蘇澳鎮","頭城鎮","礁溪鄉","壯圍鄉","員山鄉","冬山鄉","五結鄉","三星鄉","大同鄉","南澳鄉"};
    final String[] Miaoli = {"全部","苗栗市","苑裡鎮","通霄鎮","竹南鎮","頭份市","後龍鎮","卓蘭鎮","大湖鄉","公館鄉","銅鑼鄉","南庄鄉","頭屋鄉","三義鄉","西湖鄉","造橋鄉","三灣鄉","獅潭鄉","泰安鄉"};
    final String[] Changhua = {"全部","彰化市","鹿港鎮","和美鎮","北斗鎮","員林市","溪湖鎮","田中鎮","二林鎮","線西鄉","伸港鄉","福興鄉","秀水鄉","花壇鄉","芬園鄉","大村鄉","埔鹽鄉","埔心鄉","永靖鄉","社頭鄉","二水鄉","田尾鄉","埤頭鄉","芳苑鄉","大城鄉","竹塘鄉","溪州鄉"};
    final String[] Nantou ={"全部","南投市","埔里鎮","草屯鎮","竹山鎮","集集鎮","名間鄉","鹿谷鄉","中寮鄉","魚池鄉","國姓鄉","水里鄉","信義鄉","仁愛鄉"};
    final String[] Yunlin ={"全部","斗六市","斗南鎮","虎尾鎮","西螺鎮","土庫鎮","北港鎮","古坑鄉","大埤鄉","莿桐鄉","林內鄉","二崙鄉","崙背鄉","麥寮鄉","東勢鄉","褒忠鄉","台西鄉","元長鄉","四湖鄉","口湖鄉","水林鄉"};
    final String[] Chiayi ={"全部","朴子市","布袋鎮","大林鎮","民雄鄉","溪口鄉","新港鄉","六腳鄉","東石鄉","義竹鄉","鹿草鄉","太保市","水上鄉","中埔鄉","竹崎鄉","梅山鄉","番路鄉","大埔鄉","阿里山鄉"};
    final String[] Pingtung ={"全部","屏東市","潮州鎮","東港鎮","恆春鎮","萬丹鄉","長治鄉","麟洛鄉","九如鄉","里港鄉","鹽埔鄉","高樹鄉","萬巒鄉","內埔鄉","竹田鄉","新埤鄉","枋寮鄉","新園鄉","崁頂鄉","林邊鄉","南州鄉","佳冬鄉","琉球鄉","車城鄉","滿洲鄉","枋山鄉","三地門鄉","霧台鄉","瑪家鄉","泰武鄉","來義鄉","春日鄉","獅子鄉","牡丹鄉"};
    final String[] Penghu ={"全部","馬公市","湖西鄉","白沙鄉","西嶼鄉","望安鄉","七美鄉"};
    final String[] Hualien ={"全部","花蓮市","鳳林鎮","玉里鎮","新城鄉","吉安鄉","壽豐鄉","光復鄉","豐濱鄉","瑞穗鄉","富里鄉","秀林鄉","萬榮鄉","卓溪鄉"};
    final String[] Taitung ={"全部","台東市","成功鎮","關山鎮","卑南鄉","大武鄉","太麻里鄉","東河鄉","長濱鄉","鹿野鄉","池上鄉","綠島鄉","延平鄉","海端鄉","達仁鄉","金峰鄉","蘭嶼鄉"};
    final String[] Kinmen ={"全部","金城鎮","金沙鎮","金湖鎮","金寧鄉","烈嶼鄉","烏坵鄉"};
    final String[] Lianjiang ={"全部","南竿鄉","北竿鄉","莒光鄉","東引鄉"};
    String[] Choose=null;
    String City=null;

    private String HTTPURL = "http://163.13.201.94/107_SD/restaurrr.php";
    //http://gis.taiwan.net.tw/XMLReleaseALL_public/restaurant_C_f.json
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    System.out.println("----------Handler [0]-----------");
                    MyAdaper adapter = new MyAdaper(list);
                    selectlist = list;
                    lv.setAdapter(adapter);
                    pd.dismiss();
                    break;
                case 1:
                    System.out.println("----------Handler [1]-----------");
                    adapter = new MyAdaper(newlist);
                    selectlist = newlist;
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
        setContentView(R.layout.activity_restaurant);

        final ArrayAdapter<String> CountyList = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                county);
        final ArrayAdapter<String> CityList = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Default_city);
        final ArrayAdapter<String> TaipeiCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Taipei_city);
        final ArrayAdapter<String> TaichungCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Taichung_city);
        final ArrayAdapter<String> TainanCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Tainan_city);
        final ArrayAdapter<String> KaohsiungCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Kaohsiung_city);
        final ArrayAdapter<String> KeelungCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Keelung_city);
        final ArrayAdapter<String> HsinchuCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Hsinchu_city);
        final ArrayAdapter<String> YilanCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Yilan);
        final ArrayAdapter<String> NewTaipeiCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                New_Taipei_city);
        final ArrayAdapter<String> TaoyuanCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Taoyuan_city);
        final ArrayAdapter<String> Hsinchu_ = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Hsinchu);
        final ArrayAdapter<String> MiaoliCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Miaoli);
        final ArrayAdapter<String> NantouCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Nantou);
        final ArrayAdapter<String> ChanghuaCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Changhua);
        final ArrayAdapter<String> YunlinCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Yunlin);
        final ArrayAdapter<String> Chiayi_ = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Chiayi);
        final ArrayAdapter<String> ChiayiCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Chiayi_city);
        final ArrayAdapter<String> PingtungCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Pingtung);
        final ArrayAdapter<String> PenghuCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Penghu);
        final ArrayAdapter<String> HualienCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Hualien);
        final ArrayAdapter<String> TaitungCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Taitung);
        final ArrayAdapter<String> KinmenCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Kinmen);
        final ArrayAdapter<String> LianjiangCity = new ArrayAdapter<>(restaurant.this,
                android.R.layout.simple_spinner_dropdown_item,
                Lianjiang);

        Toolbar RToolbar = (Toolbar)findViewById(R.id.RToolbar);
        RToolbar.setTitle("找餐廳");
        RToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        RToolbar.setNavigationIcon(R.drawable.back);
        RToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner_county = (Spinner)findViewById(R.id.spinner_county);
        spinner_county.setAdapter(CountyList);
        spinner_city = (Spinner)findViewById(R.id.spinner_city);
        spinner_city.setAdapter(CityList);

        spinner_county.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (county[position]){
                    case "台北市": spinner_city.setAdapter(TaipeiCity);Choose=Taipei_city;City=county[position];break;
                    case "台中市": spinner_city.setAdapter(TaichungCity);Choose=Taichung_city;City=county[position];break;
                    case "台南市": spinner_city.setAdapter(TainanCity);Choose=Tainan_city;City=county[position];break;
                    case "高雄市": spinner_city.setAdapter(KaohsiungCity);Choose=Kaohsiung_city;City=county[position];break;
                    case "基隆市": spinner_city.setAdapter(KeelungCity);Choose=Keelung_city;City=county[position];break;
                    case "新竹市": spinner_city.setAdapter(HsinchuCity);Choose=Hsinchu_city;City=county[position];break;
                    case "嘉義市": spinner_city.setAdapter(ChiayiCity);Choose=Chiayi_city;City=county[position];break;
                    case "新北市": spinner_city.setAdapter(NewTaipeiCity);Choose=New_Taipei_city;City=county[position];break;
                    case "桃園市": spinner_city.setAdapter(TaoyuanCity);Choose=Taoyuan_city;City=county[position];break;
                    case "新竹縣": spinner_city.setAdapter(Hsinchu_);Choose=Hsinchu;City=county[position];break;
                    case "宜蘭縣": spinner_city.setAdapter(YilanCity);Choose=Yilan;City=county[position];break;
                    case "苗栗縣": spinner_city.setAdapter(MiaoliCity);Choose=Miaoli;City=county[position];break;
                    case "彰化縣": spinner_city.setAdapter(ChanghuaCity);Choose=Changhua;City=county[position];break;
                    case "南投縣": spinner_city.setAdapter(NantouCity);Choose=Nantou;City=county[position];break;
                    case "雲林縣": spinner_city.setAdapter(YunlinCity);Choose=Yunlin;City=county[position];break;
                    case "嘉義縣": spinner_city.setAdapter(Chiayi_);Choose=Chiayi;City=county[position];break;
                    case "屏東縣": spinner_city.setAdapter(PingtungCity);Choose=Pingtung;City=county[position];break;
                    case "澎湖縣": spinner_city.setAdapter(PenghuCity);Choose=Penghu;City=county[position];break;
                    case "花蓮縣": spinner_city.setAdapter(HualienCity);Choose=Hualien;City=county[position];break;
                    case "台東縣": spinner_city.setAdapter(TaitungCity);Choose=Taitung;City=county[position];break;
                    case "金門縣": spinner_city.setAdapter(KinmenCity);Choose=Kinmen;City=county[position];break;
                    case "連江縣": spinner_city.setAdapter(LianjiangCity);Choose=Lianjiang;City=county[position];break;
                    default:
                        City=null;
                        spinner_city.setAdapter(CityList);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (Choose != null) {
                    if(City==null){
                        initData();
                    }else{
                        newlist.clear();
                        if (Choose[position].equals("全部")) {
                            newlist = getNewData(City);
                            mHandler.obtainMessage(1).sendToTarget();
                        }else{
                            newlist = getNewData(City+Choose[position]);
                            mHandler.obtainMessage(1).sendToTarget();
                        }
                    }

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv = (ListView) findViewById(R.id.lv);
        initData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                if(City==null){
                    Intent intent = new Intent();
                    intent.setClass(restaurant.this,description.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("description_img", selectlist.get(arg2).getImageUrl());
                    bundle.putString("description_title", selectlist.get(arg2).getTitle());
                    bundle.putString("description_summary", selectlist.get(arg2).getSummary());
                    bundle.putString("description_add", selectlist.get(arg2).getAddress());
                    bundle.putString("description_tel", selectlist.get(arg2).getTelphone());
                    bundle.putString("description_time", selectlist.get(arg2).getTime());
                    //將Bundle物件assign給intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent();
                    intent.setClass(restaurant.this,description.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("description_img", newlist.get(arg2).getImageUrl());
                    bundle.putString("description_title", newlist.get(arg2).getTitle());
                    bundle.putString("description_summary", newlist.get(arg2).getSummary());
                    bundle.putString("description_add", newlist.get(arg2).getAddress());
                    bundle.putString("description_tel", newlist.get(arg2).getTelphone());
                    bundle.putString("description_time", newlist.get(arg2).getTime());
                    //將Bundle物件assign給intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
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
                    spinner_county.setVisibility(View.VISIBLE);
                    spinner_city.setVisibility(View.VISIBLE);
                    mHandler.obtainMessage(0).sendToTarget();
                }else{
                    spinner_county.setVisibility(View.GONE);
                    spinner_city.setVisibility(View.GONE);
                    spinner_county.setAdapter(CountyList);
                    spinner_city.setAdapter(CityList);
                    newlist.clear();
                    newlist = getNewDataByTitle(s.toString());
                    mHandler.obtainMessage(1).sendToTarget();
                }
            }
        });
    }
    private List<News> getNewDataByTitle(String input_info){
        for(int i=0;i<list.size();i++){
            News news = list.get(i);
            if(news.getTitle().contains(input_info)){
                News news1 = new News(news.getImageUrl(),news.getTitle(),news.getSummary(),news.getAddress(),news.getTelphone(),news.getTime());
                newlist.add(news1);
            }
        }
        return newlist;
    }
    private List<News> getNewData(String input_info){
        int count =0;
        for(int i=0;i<list.size();i++){
            News news = list.get(i);
            if(news.getAddress().contains(input_info)){
                count++;
                News news1 = new News(news.getImageUrl(),news.getTitle(),news.getSummary(),news.getAddress(),news.getTelphone(),news.getTime());
                System.out.println("----------測試更改 Title --------"+news.getTitle());
                System.out.println("----------測試更改 Address ------"+news.getAddress());
                System.out.println("@@@@@@@@@@@@@@@@"+count+"@@@@@@@@@@@@@@@@@@@"+news.getImageUrl());
                newlist.add(news1);
            }
        }
        return newlist;
    }
    private void initData() {
        pd = android.app.ProgressDialog.show(restaurant.this,"資料處理中...","請稍等...");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject ja = new JSONObject(response.body().string());
                    JSONArray data = ja.getJSONArray("Info");
                    News news = null;
                    description_img = new String[data.length()];
                    description_title=new String[data.length()];
                    description_summary=new String[data.length()];
                    description_add = new String[data.length()];
                    description_tel = new String[data.length()];
                    description_time = new String[data.length()];
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject ob = data.getJSONObject(i);
                        description_img[i] = ob.getString("Picture1");
                        description_title[i]= ob.getString("Name");
                        description_summary[i] = ob.getString("Description");
                        description_add[i] = ob.getString("Address");
                        description_tel [i]= ob.getString("Tel");
                        description_time [i] =ob.getString("Opentime");
                        //news = new News(DefaultPic,description_title[i], description_summary[i], description_add[i],description_tel[i],description_time[i]);
                        news = new News(description_img[i],description_title[i], description_summary[i], description_add[i],description_tel[i],description_time[i]);
                        list.add(news);
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


