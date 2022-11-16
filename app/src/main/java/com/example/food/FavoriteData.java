package com.example.food;

public class FavoriteData {

    private String favorite_number;
    private String favorite_name;
    private String favorite_title;
    private String favorite_add;
    private String favrotie_des;
    private String favorite_crd;

    public String getNumber(){ return favorite_number;}

    public void setNumber(String number){ this.favorite_number=number;}

    public String getName() {   return favorite_name;        }

    public void setName(String name) {  this.favorite_name = name;  }

    public String getTitle() {
        return favorite_title;
    }

    public void setTitle(String title) {
        this.favorite_title = title;
    }

    public String getAddress(){return favorite_add;}

    public void setAddress(String address){ this.favorite_add = address; }

   public String getDescription (){ return  this.favrotie_des;}

   public void setDescription(String description){this.favrotie_des=description;}

    public String getCreateDate(){ return favorite_crd;}

    public void setCreateDate(String createdate){this.favorite_crd=createdate;}

    public FavoriteData( String number,String name,String title, String address,String description ,String createdate ) {
        this.favorite_number=number;
        this.favorite_name = name ;
        this.favorite_title = title;
        this.favorite_add = address;
        this.favrotie_des = description ;
        this.favorite_crd = createdate ;
    }
    public FavoriteData() {
    }
}
