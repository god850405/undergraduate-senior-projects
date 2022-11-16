package com.example.food;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static java.lang.System.currentTimeMillis;

public class diary extends AppCompatActivity {

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String selectedImagePath; // 圖片檔案位置
    private int serverResponseCode = 0;
    private String upLoadServerUri = "http://163.13.201.94/107_SD/UploadToServer.php";
    int result;
    String id;
    String str;
    private EditText editTextTitle;
    private EditText editTextAddress;
    private EditText editTextContent;
    private Button buttonCommit;
    private ImageButton addPhoto;
    private String DIARY_URL;
    private String DIARY_PHOTO_URL;
    private ImageView imageView;
    private TextView textView;
    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams layoutParams ;
    private ArrayList imgPath ;
    private ArrayList imgName ;
    private String newNumber;
    private static int uploadPhotoUrl =0, uploadPhotoFile=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        Toolbar DToolbar = (Toolbar)findViewById(R.id.DToolbar);
        DToolbar.setTitle("建立食記");
        DToolbar.setBackgroundColor(Color.parseColor("#FF8C00"));
        DToolbar.setNavigationIcon(R.drawable.back);
        DToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DIARY_URL = getString(R.string.diary_url);
        DIARY_PHOTO_URL ="http://163.13.201.94/107_SD/diaryphoto.php";
        Bundle bundle = this.getIntent().getExtras();/**接收首頁傳來的user ID**/
        id = bundle.getString("id");
        System.out.println("--------------------------------ID="+id+"------------------------------------------");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(currentTimeMillis()) ; // 獲取當前時間

        str = formatter.format(curDate);
        System.out.println("--------------------------------DATE="+str+"------------------------------------------");
        imgPath=new ArrayList();
        imgName=new ArrayList();
        editTextTitle = (EditText) findViewById(R.id.diary_title);
        editTextAddress = (EditText) findViewById(R.id.diary_address);
        editTextContent = (EditText) findViewById(R.id.diary_content);
        buttonCommit = (Button) findViewById(R.id.commit);
        addPhoto =(ImageButton) findViewById(R.id.btnAddPhoto);
        textView =(TextView) findViewById(R.id.addtext);

        linearLayout = (LinearLayout)findViewById(R.id.setphoto);
        layoutParams = new LinearLayout.LayoutParams(150,150);
        buttonCommit.setOnClickListener(buttonListener);
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                selectedImagePath=null;
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                selectedImagePath=null;
            }
        });
        // 取出最後圖片檔案位置
        try {
            SharedPreferences preferencesGet = getApplicationContext()
                    .getSharedPreferences("image",
                            android.content.Context.MODE_PRIVATE);
            selectedImagePath = preferencesGet.getString("selectedImagePath",
                    ""); // 圖片檔案位置，預設為空

            Log.i("selectedImagePath", selectedImagePath + "");

        } catch (Exception e) {
        }
    }
    private Button.OnClickListener buttonListener = new Button.OnClickListener() {/**監聽創見帳號鈕是否被按下**/
        @Override
        public void onClick(View v) {
            /**呼叫這函式進行使用者資料獲取**/if(v == buttonCommit) Diary_Create();
        }
    };
    private void Diary_Create() {/**讀取使用者輸入數據**/
        getUserInfo getUser = new getUserInfo(id);
        String name = id.trim().toLowerCase();
        String username = getUser.getUsername();
        String title = editTextTitle.getText().toString().trim();
        String addrerss = editTextAddress.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        String date =str.trim();
        create(name,username,title,addrerss,content,date);/**獲取資料成功後，開始進行傳送**/
    }
    private void create(String name,String username, String title, String address, String content,String date) {
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
                String[] splitans = s.split(",");
                System.out.println("----------上傳食記結果-"+splitans[0]+"-------------");
                if(!splitans[0].equals("0")&&!splitans[0].equals("<br />")){
                    newNumber=splitans[0];
                    uploadPhotoUrl(newNumber);
                }else{
                    Toast.makeText(getApplicationContext(), "建立失敗", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            protected String doInBackground(String... params)/**將資料放入hashmap**/
            {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("username",params[1]);
                data.put("title",params[2]);
                data.put("address",params[3]);
                data.put("content",params[4]);
                data.put("date",params[5]);
                String result = ruc.sendPostRequest(DIARY_URL,data);
                return  result;
            }
        }
        RegisterUser ru = new RegisterUser();/**傳送資料**/
        ru.execute(name, username,title, address, content,date);
    }
    /* 設定圖片 */
    private void setImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false; // 不顯示照片
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 144;  // 縮小
        int scale = 1;
		/* 圖片縮小2倍 */
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE) {
            scale *= 2;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false; // 顯示照片

        Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);
        Log.i("selectedImagePath", selectedImagePath + "");

        imageView = new ImageView(getApplicationContext());
        //imageView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));  //设置图片宽高
        imageView.setBackgroundColor(Color.WHITE);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(bm);// 將圖片顯示
        layoutParams.setMargins(5,0,5,0);
        linearLayout.addView(imageView,layoutParams);
        savephoto();
    }
    private void savephoto(){
        String split[] = selectedImagePath.split("/");
        String name=split[split.length-1];
        System.out.println("---------------- "+name+" -----------------");
        imgPath.add(selectedImagePath);
        imgName.add(name);
        for(int i=0;i<imgPath.size();i++){
            System.out.println("----------imgPath = "+imgPath.get(i).toString()+" -----------------");
        }
        for(int i=0;i<imgName.size();i++){
            System.out.println("----------imgName = "+imgName.get(i).toString()+" -----------------");
        }
    }
    private void  uploadFile(){
        for(int i=0;i<imgPath.size();i=i+1){
            final int count=i;
            System.out.println("----------------即將上傳的檔案-"+imgPath.get(i).toString()+"--------------");
            new Thread(new Runnable() {
                public void run() {
                    result=uploadFile(imgPath.get(count).toString());
                }
            }).start();
        }
        Intent intent = new Intent();
        intent.setClass(diary.this, food_diary.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        //將Bundle物件assign給intent
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "上傳成功", Toast.LENGTH_SHORT).show();
    }
    private void  uploadPhotoUrl(final String number){
        for(int i=0;i<imgName.size();i++){
            final int count=i;
            System.out.println("----------------即將上傳的路徑-"+imgName.get(i).toString()+"--------------");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Diary_Photo_Path_Create(number,imgName.get(count).toString(),"");
                }
            }).start();
        }
        uploadFile();
    }
    private void Diary_Photo_Path_Create(String number,String photourl ,String photodescription){
        class DiaryPhotoPathCreate extends AsyncTask<String, Void, String> {
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
                if(splitans[0].equals("Create Successful")){
                    uploadPhotoUrl=1;
                    System.out.println("----------------上傳路徑成功---"+splitans[0]+"------------");
                }else{
                    uploadPhotoUrl=2;
                    System.out.println("----------------上傳路徑失敗---"+splitans[0]+"------------");
                }
            }
            @Override
            protected String doInBackground(String... params)/**將資料放入hashmap**/
            {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("number",params[0]);
                data.put("photourl",params[1]);
                data.put("photodescription",params[2]);
                String result = ruc.sendPostRequest(DIARY_PHOTO_URL,data);
                return  result;
            }
        }
        DiaryPhotoPathCreate ru = new DiaryPhotoPathCreate();/**傳送資料**/
        ru.execute(number, photourl,photodescription);
    }
    private void selectImage() {
        final String item1, item2, item3;
        item1 = "拍一張照";
        item2 = "從圖庫選取";
        item3 = "取消";

        final CharSequence[] items = { item1, item2, item3 };

        AlertDialog.Builder builder = new AlertDialog.Builder(diary.this);
        builder.setTitle("新增照片");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0: // 拍一張照
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);
                        break;
                    case 1: // 從圖庫選取
                        Intent intent1 = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent1.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent1, "選擇開啟圖庫"),
                                SELECT_FILE);
                        break;
                    default: // 取消
                        dialog.dismiss(); // 關閉對畫框
                        break;
                }

            }
        });
        builder.show();
    }
    /* 啟動選擇方式 */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) // 從圖庫開啟
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA) // 拍照
                onCaptureImageResult(data);
        }
    }

    /* 拍照 */
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg"); // 輸出檔案名稱
        selectedImagePath = destination + ""; // 輸出檔案位置
        FileOutputStream fo;
        try {
            destination.createNewFile(); // 建立檔案
            fo = new FileOutputStream(destination); // 輸出
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setImage(); // 將圖片顯示
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();

        selectedImagePath = cursor.getString(column_index); // 選擇的照片位置

        setImage(); // 設定圖片
    }

    /* 結束時 */
    @Override
    protected void onDestroy() {
        super.onDestroy();
		/* 紀錄圖片檔案位置 */
        SharedPreferences preferencesSave = getApplicationContext()
                .getSharedPreferences("image",
                        android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesSave.edit();
        editor.putString("selectedImagePath", selectedImagePath); // 紀錄最後圖片位置
        editor.commit();

        Log.i("onDestroy", "onDestroy");
    }
    @SuppressLint("LongLogTag")
    public int uploadFile(String sourceFileUri) {
        String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File not exist :"+sourceFileUri);
            return 0;
        }
        else
        {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    uploadPhotoFile=1;
                    System.out.println("----------------上傳檔案成功---------------");
                }else{
                    uploadPhotoFile=2;
                }
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(diary.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(diary.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "  + e.getMessage(), e);
            }
            return serverResponseCode;
        } // End else block
    }
}
