package com.example.alhanoufaldawood.conlang.Translator;

public class ReportedIssues {
    public String descriptions;
    public String issueType;
    public String reporterType;
    public String reporterId;


    public ReportedIssues() {
    }

    public ReportedIssues(String descriptions, String IssueType, String reporterType, String reporterId) {
        this.descriptions = descriptions;
        this.issueType = IssueType;
        this.reporterType = reporterType;
        this.reporterId = reporterId;

    }
}

