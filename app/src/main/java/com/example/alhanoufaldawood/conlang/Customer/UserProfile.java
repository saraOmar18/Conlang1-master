package com.example.alhanoufaldawood.conlang.Customer;

public class UserProfile {
    private String name;
    private String email;
    private String phone ;

    public UserProfile () {

    }

    public UserProfile(String userName, String userEmail, String userPhone) {
        this.name = userName;
        this.email = userEmail;
        this.phone = userPhone;

    }

    public String getname() {
        return name;
    }

    public void setUserName(String Name) {
        this.name = Name;
    }

    public String getemail() {
        return email;
    }

    public void setUserEmail(String userEmail) {
        this.email = userEmail;
    }

    public String getphone() {
        return phone;
    }

    public void setUserPhone(String userPhone) {
        this.phone = userPhone;
    }
}
