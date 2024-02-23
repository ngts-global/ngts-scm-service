package com.ngts.chat.service;

import com.ngts.chat.entity.Messages;
import com.ngts.chat.repository.MessagesRepository;
import com.ngts.chat.utils.CommonUtils;
import com.ngts.chat.vo.req.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {

    public static String className = MessageService.class.getName();
    @Autowired
    public MessagesRepository messagesRepository;

    public void saveMsg(MessageVO messageVO){
        log.error(className + " Saving the messages " + messageVO.toString());
        Messages messages = new Messages();

        messages.setMsgId(messageVO.getMsgId());
        messages.setMessageType(messageVO.getMsgType());
        messages.setChannelId("NULL");
        messages.setCreatedAt(CommonUtils.currentDateTime());
        messages.setFromName(messageVO.getFromName());
        messages.setFromId(messageVO.getFromId());
        messages.setToId(messageVO.getToId());
        messages.setToName(messageVO.getToName());
        messages.setMessage(messageVO.getMsgTxt());
        messages.setMsgStatus("S");
        messages.setDeletedAt(null);
        messages.setConversationType("SINGLE");

        messagesRepository.save(messages);

    }
}
