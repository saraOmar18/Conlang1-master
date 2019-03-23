package com.example.alhanoufaldawood.conlang;

public class ChatMessage {

    private String messageText;
    private String messageSender;
    private String messageRecever;
    private String orderNo;



    public ChatMessage(String messageText, String messageSender, String messageRecever ,String orderNo) {
            this.messageText = messageText;
            this.messageSender = messageSender;
            this.messageRecever = messageRecever;
            this.orderNo = orderNo;

    }

    public ChatMessage() {

    }

    public String getMessageText() {
            return messageText;
        }

        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }

        public String getMessageSender() {
            return messageSender;
        }

        public void setMessageSender(String messageSender) {
            this.messageSender = messageSender;
        }

        public String getMessageRecever() {
            return messageRecever;
        }

        public void setMessageRecever(String messageRecever) {
            this.messageRecever = messageRecever;
        }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}


