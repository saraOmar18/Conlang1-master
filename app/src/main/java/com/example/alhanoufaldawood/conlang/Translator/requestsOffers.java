package com.example.alhanoufaldawood.conlang.Translator;

public class requestsOffers {
    public  String price;
    public  String offerComments;
    public  String deadline;
    public  String customerID;
    public  String translatorID;
    public  String field;
    public  String comment;
    public  String from;
    public  String to;
    public  String orderNo;
    public  String customerName;
    String statuspay;


    public requestsOffers() {
    }

    public requestsOffers(String price, String offerComments, String deadline, String customerID,String field, String translatorID, String comment, String from, String to, String orderNo, String customerName, String statuspay){
        this.price=price;
        this.offerComments=offerComments;
        this.deadline=deadline;
        this.customerID=customerID;
        this.field=field;
        this.translatorID=translatorID;
        this.comment=comment;
        this.from=from;
        this.to=to;
        this.orderNo=orderNo;
        this.customerName=customerName;
        this.statuspay =statuspay;

    }

    public String getstatuspay() {
        return statuspay;
    }

    public void setstatuspay(String statuspay) {
        this.statuspay = statuspay;
    }

    public String getPrice() {
        return price;
    }

    public String getOfferComments() {
        return offerComments;
    }


    public String getDeadline() {
        return deadline;
    }

    public String getcustomerID() {
        return customerID;
    }

    public String getField() {
        return field;
    }

    public String getTranslatorID() {
        return translatorID;
    }

    public String getComment() {
        return comment;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getorderNo() {
        return orderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setOfferComments(String offerComments) {
        this.offerComments = offerComments;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setcustomerID(String customerID) {
        this.customerID = customerID;
    }

    public void setTranslatorID(String translatorID) {
        this.translatorID = translatorID;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setorderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}

