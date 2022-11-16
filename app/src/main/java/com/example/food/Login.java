package com.example.food;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;

public class Login extends AppCompatActivity {
    private CheckBox remembercheck;//判斷使用者有沒有勾選記住帳號密碼
        private EditText username, password;//使用者的帳號密碼欄位
        private Button login,register,forgot;//登入按鈕,創建按鈕,忘記密碼按鈕
        private String LOGIN_URL;/**目前暫定用在同一個router下傳資料**/
        public static String hostip;             //獲取裝置IP
        public static String hostmac;           //獲取裝置MAC
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            LOGIN_URL = getString(R.string.login_url);
            username = (EditText) findViewById(R.id.accounts);
            password = (EditText) findViewById(R.id.password);
            remembercheck = (CheckBox)findViewById(R.id.auto_save_password);
            login = (Button) findViewById(R.id.login);
            login.setOnClickListener(buttonListener);//login監聽設置
            register = (Button) findViewById(R.id.regist);
            register.setOnClickListener(buttonListener2);//創建帳號監聽設置
            forgot = (Button) findViewById(R.id.forgot);
            forgot.setOnClickListener(buttonListener3);//忘記密碼監聽設置
            Toolbar lToolbar = (Toolbar)findViewById(R.id.lToolbar);
            lToolbar.setTitle("會員登入");
            lToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
            lToolbar.setNavigationIcon(R.drawable.back);
            lToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            /**先從設定檔userinfo分別抓出username和pw兩個字串來看使用者是否曾經設置過**/
            SharedPreferences settings = getSharedPreferences("userinfo",0);
            String nowgetname = settings.getString("username", "");
            username.setText(nowgetname);

            SharedPreferences settings2 = getSharedPreferences("userinfo",0);
            String nowgetpw = settings2.getString("pw","");
            password.setText(nowgetpw);

            hostip = getLocalIpAddress();  //獲取裝置IP
            hostmac = getLocalMacAddress();//獲取裝置MAC
        }
        /**獲取IP >> IPv4的(如果把!inetAddress.isLinkLocalAddress()刪掉，就是IPv6的)**/
        public String getLocalIpAddress() {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface
                        .getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()&&!inetAddress.isLinkLocalAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException ex) {
            }
            return null;
        }

        /**獲取MAC**/
        public String getLocalMacAddress() {
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        }

        /**登入紐是否被按下**/
        private Button.OnClickListener buttonListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remembercheck.isChecked())/**如果登入紐被按下且記住帳密室被勾選的**/
                {
                    /**連接userinfo設定檔，並且找到對應的字串將使用者輸入寫進去**/
                    SharedPreferences settings = getSharedPreferences("userinfo", 0);
                    settings.edit().putString("username", username.getText().toString()).apply();

                    SharedPreferences settings3 = getSharedPreferences("userinfo", 0);
                    settings3.edit().putString("pw", password.getText().toString()).apply();
                }
                loginUser();/**呼叫這函式進行使用者資料獲取**/
            }
        };

        private Button.OnClickListener buttonListener2 = new Button.OnClickListener() {/**前往創建會員介面**/
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Login.this,Register.class);
            startActivity(intent);
        }
        };

        private Button.OnClickListener buttonListener3 = new Button.OnClickListener() {/**前往忘記密碼查詢介面**/
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(Login.this,forgotpw.class);
            startActivity(intent);
        }
        };

        private void loginUser() {/**讀取使用者輸入數據**/
            String name = username.getText().toString().trim().toLowerCase();
            String pd = password.getText().toString().trim().toLowerCase();
            String ip = hostip.trim().toLowerCase();
            String mac = hostmac.trim().toLowerCase();
            login(name, pd, ip, mac);/**獲取資料成功後，開始進行傳送**/
        }

        private void login(String name, String password, String ip, String mac) {
            class RegisterUser extends AsyncTask<String, Void, String> {
                private ProgressDialog loading;
                private HTTPconnect ruc = new HTTPconnect();/**使用Creatmem.class的功能**/
                @Override
                protected void onPreExecute()
                {
                    super.onPreExecute();/**當按下創見鈕，出現提式窗**/
                    loading = ProgressDialog.show(Login.this, "登入中...",null, true, true);
                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();/**當提式窗結束，出現登入成功與否的訊息**/
                    if(s.equals("Login Success"))/**當字串比對成功即可登入**/
                    {
                        Toast.makeText(getApplicationContext(), "登入成功", Toast.LENGTH_SHORT).show();
                        String id = username.getText().toString();/**將使用者唯一帳號變成字串準被傳去首頁**/
                        Intent intent = new Intent();
                        intent.setClass(Login.this,MainActivity.class);
                        getUserInfo getUser = new getUserInfo(id);
                        String name = getUser.getUsername();
                        intent.putExtra("id",id);
                        intent.putExtra("username",name);
                        startActivity(intent);
                        finish();
                    }
                    else if(s.equals(""))/**如果沒連接到網路**/
                    {
                        String show = "請檢查網路連線!";
                        Toast.makeText(getApplicationContext(), show, Toast.LENGTH_SHORT).show();
                    }
                    else/**連接到網路 登入有問題**/
                    {
                        Toast.makeText(getApplicationContext(), "登入失敗", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                protected String doInBackground(String... params)/**將資料放入hashmap**/
                {
                    HashMap<String, String> data = new HashMap<String,String>();
                    data.put("name",params[0]);
                    data.put("password",params[1]);
                    data.put("ip",params[2]);
                    data.put("mac",params[3]);
                    String result = ruc.sendPostRequest(LOGIN_URL,data);
                    return  result;
                }
            }
            RegisterUser ru = new RegisterUser();/**傳送資料**/
            ru.execute(name, password, ip, mac);
        }
    }