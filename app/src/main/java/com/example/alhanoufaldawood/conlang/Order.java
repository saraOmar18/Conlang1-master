package com.example.alhanoufaldawood.conlang;

public class Order {

    private String customerId;
    private String translatorId;
    private String price;
    private String deadLine;
    private String documentPath;
    private String from;
    private String to;
    private String field;
    private String status;
    String orderNo;

    private String offerComment;
    private String requestComment;
    private String translatorStatus;
    private String name;
    public Order(){

    }

    public Order(String customerId, String translatorId, String price, String deadLine, String documentPath, String from, String to, String field, String status, String orderNo, String offerComment, String requestComment, String translatorStatus, String name) {
        this.customerId = customerId;
        this.translatorId = translatorId;
        this.price = price;
        this.deadLine = deadLine;
        this.documentPath = documentPath;
        this.from = from;
        this.to = to;
        this.field = field;
        this.status = status;
        this.orderNo = orderNo;
        this.offerComment = offerComment;
        this.requestComment = requestComment;
        this.translatorStatus = translatorStatus;
        this.name = name;
    }

    public String getOfferComment() {
        return offerComment;
    }

    public void setOfferComment(String offerComment) {
        this.offerComment = offerComment;
    }

    public String getRequestComment() {
        return requestComment;
    }

    public void setRequestComment(String requestComment) {
        this.requestComment = requestComment;
    }

    public String getTranslatorStatus() {
        return translatorStatus;
    }

    public void setTranslatorStatus(String translatorStatus) {
        this.translatorStatus = translatorStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getorderNo() {
        return orderNo;
    }

    public void setorderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTranslatorId() {
        return translatorId;
    }

    public void setTranslatorId(String translatorId) {
        this.translatorId = translatorId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


}

