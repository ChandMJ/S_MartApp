package com.example.s_martapp;

public class User {

    private String Name;
    private String Password;
    private String Email;
    private String Mobile;
    private String Semester;
    private String Branch;
    private String Hostel;


    public User() {
    }


    public User(String name, String password, String email, String mobile, String sem, String hostel, String branch) {
        Name = name;
        Password = password;
        Email = email;
        Mobile = mobile;
        Semester = sem;
        Hostel = hostel;
        Branch = branch;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getHostel() {
        return Hostel;
    }

    public void setHostel(String hostel) {
        Hostel = hostel;
    }
}
