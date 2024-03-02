package com.ngts.chat.vo.req;

public class PinRequestVO {

    private String inputPin;
    private String token;
    private String chatId;

    public String getInputPin() {
        return inputPin;
    }

    public void setInputPin(String inputPin) {
        this.inputPin = inputPin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
