package com.example.alhanoufaldawood.conlang.Translator;

public class PublicRequests {
    private String comment;
    private String customerID;
    private String customerName;
    private String field ;
    private String fileURL;
    private String from;
    private String orderNo;
    private String status;
    private String to;
    private String translatorID;

    public PublicRequests(){

    }
    public PublicRequests(String comment, String customerID, String customerName, String field, String fileURL, String from, String orderNo, String status, String to, String translatorID) {
        this.comment = comment;
        this.customerID = customerID;
        this.customerName = customerName;
        this.field = field;
        this.fileURL = fileURL;
        this.from = from;
        this.orderNo = orderNo;
        this.status = status;
        this.to = to;
        this.translatorID = translatorID;
    }

    public String getcomment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getcustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getcustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getfield() {
        return field;
    }

    public void setfield(String field) {
        this.field = field;
    }

    public String getfileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getorderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getto() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String gettranslatorID() {
        return translatorID;
    }

    public void setTranslatorID(String translatorID) {
        this.translatorID = translatorID;
    }


}
