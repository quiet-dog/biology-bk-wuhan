package com.biology.domain.manage.nongDuData.model;

import com.biology.domain.manage.nongDuData.db.NongDuDataEntity;

import org.springframework.web.reactive.function.client.WebClient;

import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.nongDuData.command.AddNongDuDataCommand;
import com.biology.domain.manage.nongDuData.command.UpdateNongDuDataCommand;
import com.biology.domain.manage.nongDuData.db.NongDuDataService;
import com.biology.domain.manage.nongDuData.dto.NongDuDataDTO;
import com.biology.domain.manage.xwAlarm.dto.XwAlarmDTO;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NongDuDataModel extends NongDuDataEntity {
    private NongDuDataService nongDuDataService;
    private WebClient opcClient;

    public NongDuDataModel(NongDuDataService nongDuDataService, WebClient opcClient) {
        this.nongDuDataService = nongDuDataService;
        this.opcClient = opcClient;
    }

    public NongDuDataModel(NongDuDataEntity entity, NongDuDataService nongDuDataService, WebClient opcClient) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.nongDuDataService = nongDuDataService;
        this.opcClient = opcClient;
    }

    public void loadAddNongDuDataCommand(AddNongDuDataCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "nongDuDataId");
        }
    }

    public void loadUpdateNongDuDataCommand(UpdateNongDuDataCommand command) {
        if (command != null) {
            loadAddNongDuDataCommand(command);
        }
    }

    public boolean insert() {
        super.insert();
        // sendLuanSheng();
        return true;
    }
}
