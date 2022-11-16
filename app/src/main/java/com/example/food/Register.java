package com.example.food;

import android.content.Intent;
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

public class Register extends AppCompatActivity {/**創建帳號用**/
private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextTel;
    private Button buttonRegister;
    private String REGISTER_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        REGISTER_URL = getString(R.string.register_url);
        editTextName = (EditText) findViewById(R.id.name);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextTel= (EditText) findViewById(R.id.tel);
        buttonRegister = (Button) findViewById(R.id.create);
        buttonRegister.setOnClickListener(buttonListener);
        Toolbar rToolbar = (Toolbar)findViewById(R.id.rToolbar);
        rToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        rToolbar.setTitle("會員註冊");
        rToolbar.setNavigationIcon(R.drawable.back);
        rToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private Button.OnClickListener buttonListener = new Button.OnClickListener() {/**監聽創見帳號鈕是否被按下**/
    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();/**呼叫這函式進行使用者資料獲取**/
        }
    }
    };
    private void registerUser() {/**讀取使用者輸入數據**/
        String name = editTextName.getText().toString().trim().toLowerCase();
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String tel = editTextTel.getText().toString().trim().toLowerCase();
        register(name,username,password,email,tel);/**獲取資料成功後，開始進行傳送**/
    }
    private void register(String name, String username, String password, String email, String tel) {
        class RegisterUser extends AsyncTask<String, Void, String> {
           HTTPconnect ruc = new HTTPconnect();/**使用Creatmem.class的功能**/
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();/**當按下創見鈕，出現提式窗**/
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.equals("Create Successful"))/**當字串比對成功返回登入頁面**/
                {
                    Toast.makeText(getApplicationContext(), "註冊成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(Register.this,Login.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            protected String doInBackground(String... params)/**將資料放入hashmap**/
            {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("password",params[1]);
                data.put("username",params[2]);
                data.put("email",params[3]);
                data.put("tel",params[4]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return  result;
            }
        }
        RegisterUser ru = new RegisterUser();/**傳送資料**/
        ru.execute(name, password, username, email,tel);
    }
}