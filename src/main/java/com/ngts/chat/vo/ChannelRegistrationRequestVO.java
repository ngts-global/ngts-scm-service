package com.ngts.chat.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChannelRegistrationRequestVO {

    @NotBlank
    @Size(min = 3, max = 20)
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
