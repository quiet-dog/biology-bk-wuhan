package com.biology.domain.manage.gosip.dto.streams;

import lombok.Data;

@Data
public class ChannelStreamsDTO {
    private int id;
    private int addtime;
    private int uptime;
    private int t;
    private String deviceid;
    private String channelid;
    private String streamtype;
    private int status;
    private String callid;
    private boolean stop;
    private String msg;
    private int cseqno;
    private String streamid;
    private String http;
    private String rtmp;
    private String rtsp;
    private String wsflv;
    private boolean stream;
}
