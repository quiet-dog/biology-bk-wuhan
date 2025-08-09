package com.biology.domain.manage.smDevice.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.task.dto.SmOnlineDataDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ExcelSheet(name = "生命体征设备列表")
@Data
public class SmDeviceDTO {
    @Schema(description = "设备Id")
    private Long smDeviceId;

    @Schema(description = "设备sn")
    private String deviceSn;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "人员Id")
    private Long personnelId;

    @Schema(description = "区域")
    private String area;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "创建人ID")
    private Long creatorId;

    private String personnelName;

    private Boolean isOnline;

    private Long lastTime;

    public SmDeviceDTO(SmDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addPersonnelInfo();
        }
    }

    public void addPersonnelInfo() {
        if (getPersonnelId() != null && getPersonnelId() != 0) {
            PersonnelEntity pEntity = new PersonnelEntity().selectById(getPersonnelId());
            setPersonnelName(pEntity.getName());
        }
    }

    public void addIsOnline() {
        SmOnlineDataDTO isOnlone = CacheCenter.smDeviceOnlineCache.getObjectById(getDeviceSn());
        if (isOnlone == null) {
            setIsOnline(false);
        } else {
            setIsOnline(isOnlone.getOnline());
            setLastTime(isOnlone.getLastTime());
        }
    }

}
