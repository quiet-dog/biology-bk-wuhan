package com.biology.domain.manage.xsData.model;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.shiJuan.command.AddResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanService;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanModel;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlFangAn.command.UpdateXlFangAnCommand;
import com.biology.domain.manage.xsData.command.AddXsDataCommand;
import com.biology.domain.manage.xsData.command.UpdateXsDataCommand;
import com.biology.domain.manage.xsData.db.XsDataEntity;
import com.biology.domain.manage.xsData.db.XsDataService;
import com.biology.domain.manage.xsData.dto.XsDataDTO;
import com.biology.domain.manage.xwAlarm.dto.XwAlarmDTO;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.manage.xsData.db.XsDataEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class XsDataModel extends XsDataEntity {
    private XsDataService xsDataService;

    private WebClient opcClient;

    public XsDataModel(XsDataService xsDataService, WebClient opcClient) {
        this.xsDataService = xsDataService;
        this.opcClient = opcClient;
    }

    public XsDataModel(XsDataEntity entity, XsDataService xsDataService, WebClient opcClient) {

        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.xsDataService = xsDataService;
        this.opcClient = opcClient;
    }

    public void loadAddXsDataCommand(AddXsDataCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "xsDataId");
        }
    }

    public void loadUpdateXsDataCommand(UpdateXsDataCommand command) {
        if (command != null) {
            loadAddXsDataCommand(command);
        }
    }

    public void sendLuanSheng() {
        SendType sType = new SendType();
        XsDataDTO xDto = new XsDataDTO(this);
        sType.setType("xs_data");
        sType.setData(xDto);
        opcClient.post().uri("/api/ketisan")
                .bodyValue(sType)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }

    public boolean insert() {
        return true;
    }

}
