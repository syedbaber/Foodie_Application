package com.example.foodapp.Model;

public class Rider_Registration_Model {
   private String fname;
   private String lname;
    private String email;
    private String pasword;
    private String confpassword;
    private String phone;
    private String status;

    public Rider_Registration_Model() {
    }

    public Rider_Registration_Model(String fname, String lname, String email, String pasword, String confpassword, String phone, String status) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.pasword = pasword;
        this.confpassword = confpassword;
        this.phone = phone;
        this.status = status="0";  // 0 for idle state, 1 for working state.
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
