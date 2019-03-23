package com.example.alhanoufaldawood.conlang.Translator;

public class CustomRequests {
    private String name;
    String status;
    private String comment;
    private String orderNo;
    String field;
    String from;
    String to;
    String translatorID;
    String statuspay;

    public CustomRequests() {
    }

    public CustomRequests(String customerName, String field, String from, String to, String orderNo, String status, String comment,String translatorID,String statuspay) {
        this.name = customerName;
        this.field = field;
        this.from = from;
        this.to = to;
        this.orderNo = orderNo;
        this.status = status;
        this.comment = comment;
        this.translatorID = translatorID;
        this.statuspay = statuspay;
    }

    public String gettranslatorID() {
        return translatorID;
    }

    public void settranslatorID(String translatorID) {
        this.translatorID = translatorID;
    }

    public String getorderNo() {
        return orderNo;
    }

    public String getname() {
        return name;
    }

    public String getfield() {

        return field;
    }

    public String getfrom() {
        return from;
    }

    public String getto() {
        return to;
    }



    public String getstatus() {
        return status;
    }

    public String getcomment() {
        return comment;
    }

    public String getstatuspay() {
        return statuspay;
    }

    public void setstatuspay(String statuspay) {
        this.statuspay = statuspay;
    }
}


