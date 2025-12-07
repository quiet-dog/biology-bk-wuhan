package com.biology.domain.manage.smDevice.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.smData.db.SmDataEntity;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.task.dto.SmOnlineDataDTO;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ExcelSheet(name = "生命体征设备列表")
@Data
public class SmDeviceDTO {
    @Schema(description = "设备Id")
    private Long smDeviceId;

    @ExcelColumn(name = "设备SN号")
    @Schema(description = "设备SN号")
    private String deviceSn;

    @ExcelColumn(name = "设备名称")
    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "人员Id")
    private Long personnelId;

    @Schema(description = "岗位")
    private String post;

    @ExcelColumn(name = "所属区域")
    @Schema(description = "区域")
    private String area;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @ExcelColumn(name = "操作员")
    private String personnelName;

    private String code;

    private Boolean isOnline;

    @ExcelColumn(name = "设备在线状态")
    private String isOnlineStr;

    // @ExcelColumn(name = "末次通讯时间", showInImportTemplate = false)
    private Long lastTime;

    @ExcelColumn(name = "末次通讯时间", showInImportTemplate = false)
    private String lastTimeStr;

    public SmDeviceDTO(SmDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addPersonnelInfo();
            addIsOnline();
        }
    }

    public void addPersonnelInfo() {
        if (getPersonnelId() != null && getPersonnelId() != 0) {
            PersonnelEntity pEntity = new PersonnelEntity().selectById(getPersonnelId());
            setPersonnelName(pEntity.getName());
            setCode(pEntity.getCode());
            setPost(pEntity.getPost());
        }
    }

    public void addIsOnline() {
        SmOnlineDataDTO isOnlone = CacheCenter.smDeviceOnlineCache.getObjectById(getDeviceSn());
        if (isOnlone == null) {
            setIsOnline(false);
            setIsOnlineStr("离线");
            SmDataEntity queryEntity = new SmDataEntity();
            queryEntity.setSmDeviceId(getSmDeviceId());

            SmDataEntity data = queryEntity.selectOne(
                    new QueryWrapper<SmDataEntity>()
                            .eq("sm_device_id", getSmDeviceId())
                            .orderByDesc("create_time")
                            .last("LIMIT 1"));
            if (data != null) {
                if (data.getCreateTime() != null) {
                    setLastTime(data.getCreateTime().getTime());
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                setLastTimeStr(sdf.format(getLastTime()));
            }

        } else {
            setIsOnline(isOnlone.getOnline());
            setLastTime(isOnlone.getLastTime());
            setIsOnlineStr("在线");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            setLastTimeStr(sdf.format(getLastTime()));
        }
    }

}
