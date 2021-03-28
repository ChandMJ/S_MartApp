package com.example.s_martapp;

public class CallHisShow {

    private String name,phone,sem,title,date;

    public CallHisShow(String name, String phone, String sem, String date, String title) {
        this.name = name;
        this.phone = phone;
        this.sem = sem;
        this.date = date;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
