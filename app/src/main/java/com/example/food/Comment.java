package com.example.food;

/**
 * Created by Mark on 2018/7/12.
 */

public class Comment {
    private String username;
    private String comment;
    private int star;

    public void setUsername(String username){ this.username=username;}

    public String getUsername (){return this.username;}

    public void setComment(String comment){this.comment=comment;}

    public String getComment(){return this.comment;}

    public void set(int star){this.star=star;}

    public int getStar (){return this.star;}

    public Comment(String username,String comment, int star){
        this.username=username;
        this.comment=comment;
        this.star=star;
    }
    public Comment(){

    }
}
