package com.biology.admin.controller.manage;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.smData.SmDataApplicationService;
import com.biology.domain.manage.smData.command.AddSmDataCommand;
import com.biology.domain.manage.smData.dto.PostRenTiDataDTO;
import com.biology.domain.manage.smData.dto.PostRenTiMessageDTO;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.smThreshold.SmThresholdApplicationService;
import com.biology.domain.manage.smThreshold.db.SmThresholdEntity;
import com.biology.domain.manage.smThreshold.dto.SmThresholdResDTO;
import com.biology.domain.manage.websocket.db.WebsocketService;
import com.biology.infrastructure.config.renti.RenTiConfig;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class RenTiWebsocketClient {

    private final RenTiConfig renTiConfig;

    private final SmDataApplicationService smDataApplicationService;

    private final SmThresholdApplicationService smThresholdApplicationService;

    private WebSocketStompClient stompClient;

    private volatile StompSession stompSession;

    private final WebsocketService websocketService;

    private final Object lock = new Object();

    @PostConstruct
    public void init() {
        initClient();
        // 第一次尝试连接，若URL为空，会跳过
        tryConnect();
    }

    private void initClient() {
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        List<Transport> transports = Collections.singletonList(new WebSocketTransport(webSocketClient));
        SockJsClient sockJsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockJsClient);

        // 配置心跳（可选）
        stompClient.setDefaultHeartbeat(new long[] { 10000, 10000 });
        // 添加 Jackson JSON 转换器
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        // 可配置心跳等
        stompClient.setDefaultHeartbeat(new long[] { 10000, 10000 });

        // **必须设置 TaskScheduler 来调度心跳任务**
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
        stompClient.setTaskScheduler(taskScheduler);
    }

    /**
     * 尝试连接，如果 URL 空或已经连接则跳过
     */
    private void tryConnect() {
        String url = renTiConfig.getHost();
        if (url == null || url.isEmpty()) {
            System.err.println("WebSocket URL 未配置，跳过连接");
            return;
        }

        synchronized (lock) {
            if (stompSession != null && stompSession.isConnected()) {
                System.out.println("已经连接，无需重复连接");
                return;
            }
            System.out.println("尝试连接 WebSocket: " + url);
            stompClient.connect(url, new MyStompSessionHandler());
        }
    }

    /**
     * 定时任务：每10秒检查连接状态和URL，断线或URL变更则尝试重连
     */
    @Scheduled(fixedDelay = 10000)
    public void scheduledCheckAndReconnect() {
        tryConnect();
    }

    private class MyStompSessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("WebSocket连接成功");
            synchronized (lock) {
                stompSession = session;
            }

            session.subscribe(renTiConfig.getTopic(), new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return String.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    System.out.println("收到消息: " + payload);
                    PostRenTiMessageDTO msg = (PostRenTiMessageDTO) payload;
                    if (msg.getData() != null) {
                        PostRenTiDataDTO data = msg.getData();
                        AddSmDataCommand command = new AddSmDataCommand();
                        command.setBattery(data.getBatteryAsDouble());
                        command.setCo2(data.getCo2AsDouble());
                        command.setHumility(data.getCo2HumilityAsDouble());
                        command.setHuxi(data.getHuxi());
                        command.setTemp(data.getTemp());
                        command.setXinDian(data.getXindian());
                        command.setXinlv(data.getXinlvAsDouble());
                        command.setXueYang(data.getXueyangAsDouble());
                        command.setSendTime(data.getSendTime());
                        command.setTiTai(data.getTitai());
                        QueryWrapper<SmDeviceEntity> sQueryWrapper = new QueryWrapper<>();
                        sQueryWrapper.eq("device_sn", data.getSn());
                        SmDeviceEntity sEntity = new SmDeviceEntity().selectOne(sQueryWrapper);
                        if (sEntity != null) {
                            command.setSmDeviceId(sEntity.getSmDeviceId());
                            smDataApplicationService.create(command);

                            // 判断是否报警
                            List<SmThresholdResDTO> list = smThresholdApplicationService.get(sEntity.getSmDeviceId());
                            for (SmThresholdResDTO s : list) {
                                if (s.getType() != null && s.getData() != null && s.getData().size() > 0) {
                                    double value = 0;
                                    if (s.getType().equals("心率")) {
                                        value = command.getXinlv();
                                    } else if (s.getType().equals("血氧")) {
                                        value = command.getXueYang();
                                    } else if (s.getType().equals("体温")) {
                                        value = command.getTemp();
                                    } else if (s.getType().equals("co2")) {
                                        value = command.getCo2();
                                    } else {
                                        continue;
                                    }
                                    for (SmThresholdEntity t : s.getData()) {
                                        if (t.getMin() < value && value < t.getMax()) {

                                            SendType sType = new SendType();
                                            sType.setType("sm_alarm");
                                            Map<String, Object> shuju = new HashMap<>();
                                            shuju.put("description", String.format("区域为%s的%s%s数值为%f触发报警",
                                                    sEntity.getArea(), sEntity.getName(), t.getType(), value));
                                            shuju.put("value", value);
                                            shuju.put("smDeviceId", sEntity.getSmDeviceId());
                                            shuju.put("name", sEntity.getName());
                                            shuju.put("level", t.getLevel());
                                            websocketService.sendTopicKetisan(s);
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            });
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            System.err.println("连接异常，准备重连: " + exception.getMessage());
            synchronized (lock) {
                stompSession = null;
            }
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            // 可处理错误帧等
        }

        @Override
        public void handleException(StompSession session, StompCommand command,
                StompHeaders headers, byte[] payload, Throwable exception) {
            System.err.println("处理异常: " + exception.getMessage());
        }
    }

}