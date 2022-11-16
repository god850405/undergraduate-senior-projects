package com.example.food;

/**
 * Created by Mark on 2018/7/21.
 */

public class drinkstore {
    private String title;
    private String name;
    private String tel;
    private String address;

    public  String getTitle(){ return this.title;}

    public void setTitle (String title){ this.title = title ;}

    public  String getName(){ return this.name;}

    public void setName (String name){ this.name = name ;}

    public  String getTel(){ return this.tel;}

    public void setTel (String tel){ this.tel = tel ;}

    public  String getAddress(){ return this.address;}

    public void setAddress (String address){ this.address = address ;}

    public drinkstore(String title, String name , String tel,String address) {
        this.title = title;
        this.name = name;
        this.tel =tel;
        this.address = address ;
    }
    public drinkstore(){

    }
}
