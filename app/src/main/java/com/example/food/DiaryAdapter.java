package com.example.food;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2018/6/15.
 */
    public class DiaryAdapter extends BaseAdapter {

        private List<DiaryData> list_diary;
        private ListView listview_diary;
        String [] photoNumber ;
        String [] photo ;
        ArrayList photoCover = new ArrayList();
        ArrayList photoCoverNum = new ArrayList();

        public DiaryAdapter(List<DiaryData> list) {
            super();
            this.list_diary = list;
            getPhoto();
        }

        @Override
        public int getCount() {
            return list_diary.size();
        }

        @Override
        public Object getItem(int position) {
            return list_diary.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (listview_diary == null) {
                listview_diary = (ListView) parent;
            }
            com.example.food.DiaryAdapter.ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_item, null);
                holder = new com.example.food.DiaryAdapter.ViewHolder();
                holder.content_title = (TextView) convertView.findViewById(R.id.content_title);
                holder.content_address = (TextView) convertView.findViewById(R.id.content_add);
                holder.content_name = (TextView) convertView.findViewById(R.id.content_user);
                holder.content_createdate = (TextView) convertView.findViewById(R.id.content_date);
                holder.pic = (ImageView)convertView.findViewById(R.id.pic);
                holder.CommentCount = (TextView) convertView.findViewById(R.id.comcount) ;
                holder.img1 = (ImageView)convertView.findViewById(R.id.imageView);
                holder.img2 = (ImageView)convertView.findViewById(R.id.imageView2);
                holder.img3 = (ImageView)convertView.findViewById(R.id.imageView3);
                holder.img4 = (ImageView)convertView.findViewById(R.id.imageView4);
                holder.img5 = (ImageView)convertView.findViewById(R.id.imageView5);
                convertView.setTag(holder);
            } else {
                holder = (com.example.food.DiaryAdapter.ViewHolder) convertView.getTag();
            }
            DiaryData dd = list_diary.get(position);
            holder.content_title.setText(dd.getTitle());
            holder.content_address.setText(dd.getAddress());
            holder.content_name.setText(dd.getUserName());
            holder.content_createdate.setText(dd.getCreateDate());
            for(int i=0;i<photoCoverNum.size();i++){
                if(photoCoverNum.get(i).toString().equals(dd.getNumber())){
                    holder.pic.setTag(photoCover.get(i).toString());
                    ImageTask it = new ImageTask();
                    it.execute(photoCover.get(i).toString());
                    System.out.println("++++++++++++++++++++++++"+photoCover.get(i).toString());
                    i=photoCoverNum.size();
                }
            }
            int cc=dd.getCommentCount();
            holder.CommentCount.setText(String.valueOf(cc));
            int star=dd.getStarCount();
            switch (star){
                case 1:
                    holder.img1.setImageResource(R.mipmap.star);break;
                case 2:
                    holder.img1.setImageResource(R.mipmap.star);
                    holder.img2.setImageResource(R.mipmap.star);break;
                case 3:
                    holder.img1.setImageResource(R.mipmap.star);
                    holder.img2.setImageResource(R.mipmap.star);
                    holder.img3.setImageResource(R.mipmap.star);break;
                case 4:
                    holder.img1.setImageResource(R.mipmap.star);
                    holder.img2.setImageResource(R.mipmap.star);
                    holder.img3.setImageResource(R.mipmap.star);
                    holder.img4.setImageResource(R.mipmap.star);break;
                case 5:
                    holder.img1.setImageResource(R.mipmap.star);
                    holder.img2.setImageResource(R.mipmap.star);
                    holder.img3.setImageResource(R.mipmap.star);
                    holder.img4.setImageResource(R.mipmap.star);
                    holder.img5.setImageResource(R.mipmap.star);break;
            }
            // 如果本地已有缓存，就从本地读取，否则从网络请求数据

            return convertView;
        }

        class ViewHolder {
            TextView content_title, content_address , content_name,content_createdate ,CommentCount;
            ImageView img1,img2,img3,img4,img5,pic;
        }
        public void savePhoto(){
            for(int i=0;i<list_diary.size();i++){
                for(int j=0;j<photoNumber.length;j++){
                    if(list_diary.get(i).getNumber().equals(photoNumber[j])){
                        photoCoverNum.add(photoNumber[j]);
                        photoCover.add(photo[j]);
                        System.out.println("+++++++"+list_diary.get(i).getNumber()+"+++++++photoCoverNum  "+photoNumber[j]);
                        System.out.println("+++++++"+list_diary.get(i).getNumber()+"+++++++photoCover  "+photo[j]);
                        j=photoNumber.length;
                    }
                }
            }
        }
    class ImageTask extends AsyncTask<String, Void, BitmapDrawable> {
        private String imageUrl;

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downloadImage();
            BitmapDrawable db = new BitmapDrawable(listview_diary.getResources(),
                    bitmap);
            return db;
        }
        @Override
        protected void onPostExecute(BitmapDrawable result) {
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            ImageView pic=(ImageView) listview_diary.findViewWithTag(imageUrl);;
            if (pic!= null && result != null) {
                pic.setImageDrawable(result);
            }
        }
        /**
         * 根据url从网络上下载图片
         *
         * @return
         */
        private Bitmap downloadImage() {
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            return bitmap;
        }
    }
        private void getPhoto() {
            OkHttpClient client = new OkHttpClient();
            String HTTPURL = "http://163.13.201.94/107_SD/getdiaryphoto.php";
            Request request = new Request.Builder().url(HTTPURL).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        JSONObject ja = new JSONObject(response.body().string());
                        JSONArray data = ja.getJSONArray("data");
                        photoNumber = new String[data.length()];
                        photo = new String[data.length()];
                        for (int i =0;i<data.length();i++) {
                            JSONObject ob = data.getJSONObject(i);
                            photoNumber[i] = ob.getString("number");
                            photo[i] = "http://163.13.201.94/107_SD/uploads/"+ob.getString("photourl");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    savePhoto();
                }
                @Override
                public void onFailure(Request arg0, IOException arg1) {
                }
            });
        }
    }

