package com.example.alhanoufaldawood.conlang.Translator;

public class currentOrders {
    String customerName;
    String Field;
    String Deadline;
    String status;

    public currentOrders() {
    }

    public currentOrders(String customerName, String Field, String Deadline, String status) {
        this.customerName = customerName;
        this.Field = Field;
        this.Deadline = Deadline;
         this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getField() {
        return Field;
    }

    public String getDeadline() {

        return Deadline;
    }

    public String getStatus() {
        return status;
    }
}



