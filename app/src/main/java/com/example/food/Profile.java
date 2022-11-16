package com.example.food;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

/**修改會員資料**/
public class Profile extends AppCompatActivity {
    String id;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextTel;
    private Button buttonchange;
    private String SET_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SET_URL = getString(R.string.set_url);
        Bundle bundle = this.getIntent().getExtras();/**接收首頁傳來的user ID**/
        id = bundle.getString("id");
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextTel= (EditText) findViewById(R.id.tel);
        buttonchange = (Button) findViewById(R.id.create);
        buttonchange.setOnClickListener(buttonListener);
        Toolbar pToolbar = (Toolbar)findViewById(R.id.pToolbar);
        pToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        pToolbar.setTitle("修改會員資料");
        pToolbar.setNavigationIcon(R.drawable.back);
        pToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Button.OnClickListener buttonListener = new Button.OnClickListener() {/**監聽修改鈕是否被按下**/
    @Override
    public void onClick(View v) {

        if(v == buttonchange){
            checkDialog();
        }
    }
    };
    private void checkDialog()
    {
        Dialog dialog=new AlertDialog.Builder(this).setTitle("修改會員資料").setMessage("確定要修改嗎？").setNegativeButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                System.out.println(id);
                changeinfoget();/**呼叫這函式進行使用者資料獲取**/
            }
        }).setPositiveButton("Cancel", new AlertDialog.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            }
        }).create();/**創建Dialog**/
        dialog.show();/**顯示對話框**/
    }

    private void changeinfoget() {/**讀取使用者輸入數據**/
        String name = id.trim().toLowerCase();
        String username = editTextUsername.getText().toString().trim().toLowerCase();
        String password = editTextPassword.getText().toString().trim().toLowerCase();
        String email = editTextEmail.getText().toString().trim().toLowerCase();
        String tel = editTextTel.getText().toString().trim().toLowerCase();
        change(name, username, password, email, tel);/**獲取資料成功後，開始進行傳送**/
    }
    private void change(String name,String username, String password, String email, String tel) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            HTTPconnect ruc = new HTTPconnect();/**使用Creatmem.class的功能**/
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();/**當按下修改鈕，出現提式窗**/
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String[] splitans = s.split(",");
                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
            }
            @Override
            protected String doInBackground(String... params)/**將資料放入hashmap**/
            {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("username",params[1]);
                data.put("password",params[2]);
                data.put("email",params[3]);
                data.put("tel",params[4]);
                String result = ruc.sendPostRequest(SET_URL,data);
                return  result;
            }
        }
        RegisterUser ru = new RegisterUser();/**傳送資料**/
        ru.execute(name ,username, password, email, tel);
    }
}