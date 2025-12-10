package com.biology.admin.controller.manage;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.CloseStatus;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.smData.SmDataApplicationService;
import com.biology.domain.manage.smData.command.AddSmDataCommand;
import com.biology.domain.manage.smData.dto.PostRenTiDataDTO;
import com.biology.domain.manage.smData.dto.PostRenTiMessageDTO;
import com.biology.domain.manage.smData.dto.SmDataCacheDTO;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.smThreshold.SmThresholdApplicationService;
import com.biology.domain.manage.smThreshold.db.SmThresholdEntity;
import com.biology.domain.manage.smThreshold.dto.SmThresholdResDTO;
import com.biology.domain.manage.websocket.db.WebsocketService;
import com.biology.infrastructure.config.renti.RenTiConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
    private final WebsocketService websocketService;

    private WebSocketStompClient stompClient;
    private volatile StompSession stompSession;
    private final Object lock = new Object();

    // 异步线程池
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    @PostConstruct
    public void init() {
        initClient();
        tryConnect();
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("人体设备   关闭WebSocket连接");
        // 断开WebSocket
        if (stompSession != null && stompSession.isConnected()) {
            stompSession.disconnect();
        }
        // 关闭线程池
        executor.shutdown();
    }

    private void initClient() {
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        SockJsClient sockJsClient = new SockJsClient(
                Collections.singletonList(new WebSocketTransport(webSocketClient)));
        stompClient = new WebSocketStompClient(sockJsClient);

        // 配置心跳
        stompClient.setDefaultHeartbeat(new long[] { 10000, 10000 });
        stompClient.setMessageConverter(new StringMessageConverter());

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
        stompClient.setTaskScheduler(taskScheduler);
    }

    private void tryConnect() {
        String url = renTiConfig.getHost();
        if (url == null || url.isEmpty()) {
            System.err.println("人体设备   WebSocket URL 未配置，跳过连接");
            return;
        }

        synchronized (lock) {
            if (stompSession != null && stompSession.isConnected()) {
                System.out.println("人体设备   已经连接，无需重复连接");
                return;
            }
            System.out.println("人体设备   尝试连接 WebSocket: " + url);
            stompClient.connect(url, new MyStompSessionHandler());
        }
    }

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
                    // 异步处理消息
                    executor.submit(() -> processMessage((String) payload));
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
    }

    // 核心消息处理逻辑
    private void processMessage(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            PostRenTiMessageDTO msg = mapper.readValue(json, PostRenTiMessageDTO.class);
            long timestamp = System.currentTimeMillis();

            System.out.println("人体设备   收到消息: " + msg);

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

                SmDataCacheDTO smCacheDTO = CacheCenter.smDataCache.getObjectById(data.getSn());

                SmDataCacheDTO smDataCacheDTO = new SmDataCacheDTO(command);
                CacheCenter.smDataCache.set(data.getSn(), smDataCacheDTO);
                websocketService.sendTopicSmData(smDataCacheDTO);

                if (smCacheDTO != null && data.getSendTime() - smCacheDTO.getSendTime() > 5 * 60 * 1000) {
                    // 发送数据
                    return;
                }

                // 查询设备
                QueryWrapper<SmDeviceEntity> query = new QueryWrapper<>();
                query.eq("device_sn", data.getSn());
                SmDeviceEntity sEntity = new SmDeviceEntity().selectOne(query);
                if (sEntity != null) {
                    sEntity.setLastTime(timestamp);
                    sEntity.updateById();
                    command.setSmDeviceId(sEntity.getSmDeviceId());

                    // 安全调用数据库服务
                    try {
                        smDataApplicationService.create(command);
                    } catch (CannotGetJdbcConnectionException e) {
                        System.out.println("数据库连接异常，跳过该条消息: " + e.getMessage());
                        return;
                    }

                    // 判断报警
                    List<SmThresholdResDTO> list = smThresholdApplicationService.get(sEntity.getSmDeviceId());
                    for (SmThresholdResDTO s : list) {
                        if (s.getType() != null && s.getData() != null && !s.getData().isEmpty()) {
                            double value = extractValueByType(s.getType(), command);
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
                                    websocketService.sendTopicKetisan(sType);
                                }
                            }
                        }
                    }
                }
            }
        } catch (

        Exception e) {
            System.out.println("人体设备   处理消息异常: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private double extractValueByType(String type, AddSmDataCommand command) {
        switch (type) {
            case "心率":
                return command.getXinlv();
            case "血氧":
                return command.getXueYang();
            case "体温":
                return command.getTemp();
            case "co2":
                return command.getCo2();
            case "心电":
                return command.getXinDian() != null && !command.getXinDian().isEmpty()
                        ? command.getXinDian().stream().mapToDouble(Number::doubleValue).average().orElse(0)
                        : 0;
            case "呼吸":
                return command.getHuxi() != null && !command.getHuxi().isEmpty()
                        ? command.getHuxi().stream().mapToDouble(Number::doubleValue).average().orElse(0)
                        : 0;
            default:
                return 0;
        }
    }
}