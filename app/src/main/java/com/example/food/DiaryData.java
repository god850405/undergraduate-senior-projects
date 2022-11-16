package com.example.food;

/**
 * Created by Mark on 2018/6/15.
 */

public class DiaryData {

        private String number;
        private String content_title;
        private String content_address;
        private String content_name;
        private String content_username;
        private String content_summary;
        private String content_createdate;
        private int CommentCount;
        private int StarCount;

        public String getNumber(){return  number;}

        public void setNumber(String number){this.number = number ;}

        public String getTitle() {
            return content_title;
        }

        public void setTitle(String title) {
            this.content_title = title;
        }

        public String getAddress(){return content_address;}

        public void setAddress(String address){ this.content_address = address; }

        public String getName() {   return content_name;        }

        public void setName(String name) {
            this.content_name = name;
        }

        public String getUserName() {   return content_username;        }

        public void setUserName(String username) {
        this.content_username = content_username;
    }

        public String getSummary() {   return content_summary;        }

        public void setSummary(String content_summary) {  this.content_summary = content_summary;    }

        public String getCreateDate(){ return content_createdate;}

        public void setCreateDate(String createdate){this.content_createdate=createdate;}

        public int getCommentCount(){return this.CommentCount;}

        public void setCommentCount(int commentCount){this.CommentCount=commentCount;}

        public int getStarCount(){return  this.StarCount;}

        public void setStarCount(int starCount){this.StarCount=starCount;}

        public DiaryData(String number, String title, String address,String username,String name ,String summary,String createdate ,int commentcount , int starcount) {
            this.number = number;
            this.content_title = title;
            this.content_address = address;
            this.content_username = username ;
            this.content_name = name ;
            this.content_summary = summary ;
            this.content_createdate = createdate ;
            this.CommentCount=commentcount;
            this.StarCount=starcount;
        }
        public DiaryData() {
        }
    }