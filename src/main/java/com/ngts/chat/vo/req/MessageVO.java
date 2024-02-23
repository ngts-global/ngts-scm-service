package com.ngts.chat.vo.req;

public class MessageVO {

    private String msgId;
    private String fromId;
    private String toId;
    private String fromName;
    private String toName;
    private String time;
    private String status;
    private String msgType;
    private String msgTxt;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgTxt() {
        return msgTxt;
    }

    public void setMsgTxt(String msgTxt) {
        this.msgTxt = msgTxt;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "msgId=" + msgId +
                ", fromId='" + fromId + '\'' +
                ", toId='" + toId + '\'' +
                ", fromName='" + fromName + '\'' +
                ", toName='" + toName + '\'' +
                ", time='" + time + '\'' +
                ", status='" + status + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgTxt='" + msgTxt + '\'' +
                '}';
    }
}