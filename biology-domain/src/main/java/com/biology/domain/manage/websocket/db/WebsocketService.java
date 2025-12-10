package com.biology.domain.manage.websocket.db;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.websocket.dto.ContentDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;

    // 通过构造器注入 SimpMessagingTemplate
    // public WebsocketService(SimpMessagingTemplate messagingTemplate) {
    // this.messagingTemplate = messagingTemplate;
    // }

    // 服务层方法用于发送消息到特定的目的地
    public void sendMessage(String destination, String message) {
        messagingTemplate.convertAndSend(destination, message);
    }

    public void sendTopicInfo(Object message) {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContent(message);
        messagingTemplate.convertAndSend("/topic/info", contentDTO);
    }

    public void sendTopicData(Object message) {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContent(message);
        messagingTemplate.convertAndSend("/topic/data", contentDTO);
    }

    public void sendTopicKetisan(Object message) {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContent(message);
        messagingTemplate.convertAndSend("/topic/ketisan", contentDTO);
    }

    public void sendTopicSmData(Object message) {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContent(message);
        messagingTemplate.convertAndSend("/topic/smData", contentDTO);
    }

    // 发送通知
    public void sendTopicNotice(Object message) {
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContent(message);
        messagingTemplate.convertAndSend("/topic/notice", contentDTO);
    }

}
