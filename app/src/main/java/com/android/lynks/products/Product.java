package com.android.lynks.products;


import android.content.Intent;

public class Product {
    int id;
    String title;
    String price;
    String link;
    String image;


    public Product(){
        super();
    }

    public Product( int id,String title,String price,String link, String image) {
        super();
        this.id=id;
        this.title=title;
        this.price=price;
        this.image=image;
        this.link=link;
    }

    public int getId(){return id;}
    public String getImage(){return image;}
    public String getTitle(){return title;}
    public String getPrice(){return price;}
    public String getLink(){return link;}

    public void setId(int  id){this.id=id;}
    public void setTitle(String title){this.title=title;}
    public void setPrice(String price){this.price=price;}
    public void setLink(String link){this.link=link;}
    public void setImage(String image){this.image=image;}
}
