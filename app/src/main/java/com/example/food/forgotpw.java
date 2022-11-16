package com.example.food;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class forgotpw extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextTel;
    private Button buttonget;
    private static final String REGISTER_URL = "http://163.13.201.94/107_SD/forgot.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpw);

        editTextName = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextTel= (EditText) findViewById(R.id.tel);
        buttonget = (Button) findViewById(R.id.get);
        buttonget.setOnClickListener(buttonListener);

        Toolbar fToolbar = (Toolbar)findViewById(R.id.fToolbar);
        fToolbar.setTitle("忘記密碼");
        fToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        fToolbar.setNavigationIcon(R.drawable.back);
        fToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private Button.OnClickListener buttonListener = new Button.OnClickListener() {/**監聽查詢鈕是否被按下**/
    @Override
    public void onClick(View v) {
        if(v == buttonget){
            getpw();/**呼叫這函式進行使用者資料獲取**/
        }
    }
    };
    private void getpw() {/**讀取使用者輸入數據**/
        String name = editTextName.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String tel = editTextTel.getText().toString().trim().toLowerCase();
        get(name,email,tel);/**獲取資料成功後，開始進行傳送**/
    }
    private void get(String name,String email, String tel) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            HTTPconnect ruc = new HTTPconnect();/**使用Creatmem.class的功能(傳送用的httpclass)**/
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();/**當按下查詢鈕，出現提式窗**/
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), "你的密碼是："+s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params)/**將資料放入hashmap**/
            {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("email",params[1]);
                data.put("tel",params[2]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return  result;
            }
        }
        RegisterUser ru = new RegisterUser();/**傳送資料**/
        ru.execute(name,email, tel);
    }
}