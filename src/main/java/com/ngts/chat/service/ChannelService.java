package com.ngts.chat.service;

import com.ngts.chat.entity.Channels;
import com.ngts.chat.repository.ChannelsRepository;
import com.ngts.chat.utils.CommonUtils;
import com.ngts.chat.vo.req.ChannelRegistrationRequestVO;
import com.ngts.chat.vo.req.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    @Autowired
    public ChannelsRepository channelsRepository;

    public ChatUsersService chatUsersService;

    public void channelCreate(ChannelRegistrationRequestVO channelRegistrationRequestVO){

        MessageVO messageResponseVOFrom = chatUsersService.findByChatId(channelRegistrationRequestVO.getCreaterId());

        Channels channels  = new Channels();
        String channelId = CommonUtils.generateUID();
        channels.setChannelId(channelId);
        channels.setChannelName(channelRegistrationRequestVO.getChannelName());
        channels.setChannelStatus("ACTIVE");
        channels.setCreatedAt(CommonUtils.currentDateTime());
        channels.setCreatedEmail("Email");
        channels.setCreatedName(messageResponseVOFrom.getFromName());
        channels.setCreatorId(channelRegistrationRequestVO.getCreaterId());
        channels.setDeletedAt(null);


        channelsRepository.save(channels);
    }
}
