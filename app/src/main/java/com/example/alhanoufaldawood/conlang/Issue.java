package com.example.alhanoufaldawood.conlang;

public class Issue {

    public String descriptions;
    public String issueType;
    public String reporterType;
    public String reporterId;

    public Issue(){
        }

    public Issue(String descriptions, String issueType, String reporterType,String reporterId){
        this.descriptions=descriptions;
        this.issueType=issueType;
        this.reporterType=reporterType;
        this.reporterId = reporterId;

    }
}
