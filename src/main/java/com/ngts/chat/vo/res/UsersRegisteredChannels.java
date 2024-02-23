package com.ngts.chat.vo.res;

import com.ngts.chat.vo.res.ChannelRegResponseVO;
import com.ngts.chat.vo.res.UserResponseVO;

import java.util.List;

public class UsersRegisteredChannels {

    private List<ChannelRegResponseVO> channelRegResponseVOList;
    private List<UserResponseVO> userResponseVOList;

    public List<ChannelRegResponseVO> getChannelRegResponseVOList() {
        return channelRegResponseVOList;
    }

    public void setChannelRegResponseVOList(List<ChannelRegResponseVO> channelRegResponseVOList) {
        this.channelRegResponseVOList = channelRegResponseVOList;
    }

    public List<UserResponseVO> getUserResponseVOList() {
        return userResponseVOList;
    }

    public void setUserResponseVOList(List<UserResponseVO> userResponseVOList) {
        this.userResponseVOList = userResponseVOList;
    }
}
