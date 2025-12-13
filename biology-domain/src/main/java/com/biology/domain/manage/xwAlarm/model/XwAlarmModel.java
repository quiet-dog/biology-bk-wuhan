package com.biology.domain.manage.xwAlarm.model;

import org.springframework.web.reactive.function.client.WebClient;

import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.xwAlarm.command.AddXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.command.UpdateXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.db.XwAlarmEntity;
import com.biology.domain.manage.xwAlarm.db.XwAlarmService;
import com.biology.domain.manage.xwAlarm.dto.XwAlarmDTO;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XwAlarmModel extends XwAlarmEntity {
    private XwAlarmService xwAlarmService;

    private WebClient opcClient;

    public XwAlarmModel(XwAlarmService xwAlarmService, WebClient opcClient) {
        this.xwAlarmService = xwAlarmService;
        this.opcClient = opcClient;
    }

    public XwAlarmModel(XwAlarmEntity entity, XwAlarmService xwAlarmService, WebClient opcClient) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xwAlarmService = xwAlarmService;
        this.opcClient = opcClient;
    }

    public void loadAddXwAlarmCommand(AddXwAlarmCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xwAlarmId");
        }
    }

    public void loadUpdateXwAlarmCommand(UpdateXwAlarmCommand command) {
        if (command != null) {
            loadAddXwAlarmCommand(command);
        }
    }

    public void sendLuanSheng() {
        SendType sType = new SendType();
        XwAlarmDTO xDto = new XwAlarmDTO(this);
        sType.setType("sb_alarm");
        sType.setData(xDto);
        opcClient.post().uri("/api/ketisan")
                .bodyValue(sType)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }

    public boolean insert() {
        super.insert();
        sendLuanSheng();
        return true;
    }

}
