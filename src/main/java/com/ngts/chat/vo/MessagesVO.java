package com.ngts.chat.vo;

public class MessagesVO {

    private String message;
    private String fromLogin;

    public String getMessaageId() {
        return messaageId;
    }

    public void setMessaageId(String messaageId) {
        this.messaageId = messaageId;
    }

    private String messaageId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "message='" + message + '\'' +
                ", fromLogin='" + fromLogin + '\'' +
                '}';
    }
}
