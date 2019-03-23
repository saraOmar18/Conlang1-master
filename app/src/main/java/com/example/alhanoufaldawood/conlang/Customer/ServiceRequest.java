package com.example.alhanoufaldawood.conlang.Customer;

import java.io.Serializable;

public class ServiceRequest implements Serializable {

    String field;
    String from;
    String to;
    String comment;
    String fileURL;
    String translatorID;
    String CustomerID;
    String status;
    String orderNo;
    String name;
    String translatorName;
    String orderType;
    String date;
    String translatorStatus;
    String customerImage;
    String translatorImage;

    public ServiceRequest() {
    }


    public ServiceRequest(String field, String from, String to, String comment, String fileURL, String translatorID, String customerID, String status, String orderNo, String name, String translatorName, String orderType, String date, String translatorStatus, String customerImage,String translatorImage) {
        this.field = field;
        this.from = from;
        this.to = to;
        this.comment = comment;
        this.fileURL = fileURL;
        this.translatorID = translatorID;
        CustomerID = customerID;
        this.status = status;
        this.orderNo = orderNo;
        this.name = name;
        this.translatorName = translatorName;
        this.orderType = orderType;
        this.date = date;
        this.translatorStatus = translatorStatus;
        this.customerImage = customerImage;
        this.translatorImage = translatorImage;
    }

    public String getcustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }


    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getTranslatorImage() {
        return translatorImage;
    }

    public void setTranslatorImage(String translatorImage) {
        this.translatorImage = translatorImage;
    }

    public String gettranslatorStatus() {
        return translatorStatus;
    }

    public void settranslatorStatus(String translatorStatus) {
        this.translatorStatus = translatorStatus;
    }

    public String getfield() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getto() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getcomment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getfileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String gettranslatorID() {
        return translatorID;
    }

    public void setTranslatorID(String translatorID) {
        this.translatorID = translatorID;
    }


    public String getstatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getorderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getname() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getorderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getdate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String gettranslatorName() {
        return translatorName;
    }

    public void setTranslatorName(String translatorName) {
        this.translatorName = translatorName;
    }
}

