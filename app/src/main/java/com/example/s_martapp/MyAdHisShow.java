package com.example.s_martapp;

public class MyAdHisShow {

    private String category,title,price,date;

    public MyAdHisShow(String category, String title, String price, String date) {
        this.category = category;
        this.price = price;
        this.date = date;
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
