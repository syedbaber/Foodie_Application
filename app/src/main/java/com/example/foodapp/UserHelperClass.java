package com.example.foodapp;

public class UserHelperClass {
    String fname, lname, email, pasword, confpassword, phone;

    public UserHelperClass() {
    }

    public UserHelperClass(String fname, String lname, String email, String pasword, String confpassword, String phone) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.pasword = pasword;
        this.confpassword = confpassword;
        this.phone = phone;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public String getConfpassword() {
        return confpassword;
    }

    public void setConfpassword(String confpassword) {
        this.confpassword = confpassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
