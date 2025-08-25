package com.biology.domain.manage.smData.model;

import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.smData.command.AddSmDataCommand;
import com.biology.domain.manage.smData.command.UpdateSmDataCommand;
import com.biology.domain.manage.smData.db.SmDataEntity;
import com.biology.domain.manage.smData.db.SmDataService;
import com.biology.domain.manage.smData.dto.SmDataDTO;
import com.biology.domain.manage.smThreshold.db.SmThresholdEntity;
import com.biology.domain.manage.smThreshold.db.SmThresholdService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SmDataModel extends SmDataEntity {
    private SmDataService smDataService;

    private WebClient opcClient;

    private SmThresholdService smThresholdService;

    public SmDataModel(SmDataService smDataService, WebClient opcClient, SmThresholdService smThresholdService) {
        this.smDataService = smDataService;
        this.opcClient = opcClient;
        this.smThresholdService = smThresholdService;
    }

    public SmDataModel(SmDataEntity entity, SmDataService smDataService, WebClient opcClient,
            SmThresholdService smThresholdService) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.smDataService = smDataService;
        this.opcClient = opcClient;
        this.smThresholdService = smThresholdService;
    }

    public void loadAddSmDataCommand(AddSmDataCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "smDataId");
        }
    }

    public void loadUpdateSmDataCommand(UpdateSmDataCommand command) {
        if (command != null) {
            loadAddSmDataCommand(command);
        }
    }

    public void sendLuanSheng() {
        SmDataDTO sDto = new SmDataDTO(this);
        SendType sType = new SendType();
        sType.setData(sDto);
        sType.setType("rt_data");
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
