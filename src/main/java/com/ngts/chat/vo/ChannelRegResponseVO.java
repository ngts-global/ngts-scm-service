package com.ngts.chat.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChannelRegResponseVO {

    @NotBlank
    @Size(min = 3, max = 20)
    private String channelName;

    private int channelId;

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
}
