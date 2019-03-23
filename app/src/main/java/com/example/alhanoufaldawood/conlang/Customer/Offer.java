package com.example.alhanoufaldawood.conlang.Customer;

public class Offer {
    String deadLine;
    String price;
    String comment;
    String translatorName;
    String key;


    public Offer() {

    }

    public Offer(String deadLine, String price, String comment, String translatorName,String key) {
        this.deadLine = deadLine;
        this.price = price;
        this.comment = comment;
        this.translatorName = translatorName;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTranslatorName() {
        return translatorName;
    }

    public void setTranslatorName(String translatorName) {
        this.translatorName = translatorName;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}