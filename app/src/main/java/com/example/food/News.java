package com.example.food;

/**
 * Created by Mark on 2018/6/13.
 */
public class News {

    private String imageUrl;
    private String title;
    private String summary;
    private String address;
    private String telphone;
    private String time;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAddress(){return address;}

    public void setAddress(String address){ this.address = address; }

    public  String getTelphone(){ return telphone;}

    public void setTelphone (String telphone){ this.telphone = telphone ;}

    public  String getTime(){ return time;}

    public void setTime (String time){ this.time = time ;}

    public News(String imageUrl, String title, String summary ,String address, String telphone , String time) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.summary = summary;
        this.address = address ;
        this.telphone =telphone;
        this.time = time ;
    }

    public News() {
    }
}