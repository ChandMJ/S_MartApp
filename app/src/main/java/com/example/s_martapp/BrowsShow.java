package com.example.s_martapp;

public class BrowsShow {

    private String title,desc,price,postedby,postedon,url;

    public BrowsShow(String title, String desc, String price,String postedby,String date,String image) {
        this.title = title;
        this.price = price;
        this.postedon = date;
        this.desc = desc;
        this.postedby=postedby;
        this.url=image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getPostedon() {
        return postedon;
    }

    public void setPostedon(String postedon) {
        this.postedon = postedon;
    }
}
