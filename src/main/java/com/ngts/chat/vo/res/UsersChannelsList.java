package com.ngts.chat.vo.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngts.chat.vo.req.MessageVO;

import java.util.ArrayList;

public class UsersChannelsList{

    public String channelName;
    public int channelId;
    public String creationTime;
    public String channelStatus;
    @JsonProperty("ChannelMembers")
    public ArrayList<ChannelMember> channelMembers;
    public ArrayList<MessageVO> messages;


    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(String channelStatus) {
        this.channelStatus = channelStatus;
    }

    public ArrayList<ChannelMember> getChannelMembers() {
        return channelMembers;
    }

    public void setChannelMembers(ArrayList<ChannelMember> channelMembers) {
        this.channelMembers = channelMembers;
    }

    public ArrayList<MessageVO> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageVO> messages) {
        this.messages = messages;
    }
}
