package com.biology.domain.manage.smData.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.smData.db.SmDataEntity;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.smDevice.dto.SmDeviceDTO;

import cn.hutool.json.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ExcelSheet(name = "生命体征数据列表")
@Data
public class SmDataDTO {
    @ExcelColumn(name = "序号")
    @Schema(name = "smDataId", description = "数据ID")
    private Long smDataId;

    @Schema(description = "设备ID")
    private Long smDeviceId;

    @ExcelColumn(name = "设备sn")
    @Schema(description = "设备sn")
    private String deviceSn;

    @ExcelColumn(name = "操作员")
    @Schema(description = "绑定人名称")
    private String personnelName;

    @ExcelColumn(name = "电量")
    @Schema(description = "电量")
    private Double battery;

    @ExcelColumn(name = "co2浓度")
    @Schema(description = "co2浓度")
    private Double co2;

    @ExcelColumn(name = "温度")
    @Schema(description = "温度")
    private Double humility;

    @ExcelColumn(name = "湿度")
    @Schema(description = "湿度")
    private Double temp;

    @Schema(description = "呼吸")
    private List<Number> huxi;

    @Schema(description = "采样时间")
    private Long sendTime;

    @ExcelColumn(name = "采样时间")
    private String caiYangTime;

    @Schema(description = "心电")
    private List<Number> xinDian;

    @Schema(description = "心率")
    private Double xinlv;

    @Schema(description = "血氧")
    private Double xueYang;

    @Schema(description = "体态")
    private String tiTai;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "创建人ID")
    private Long creatorId;

    public SmDataDTO(SmDataEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addDevice();
            addTime();
        }
    }

    public void addDevice() {
        if (getSmDeviceId() != null && getSmDeviceId() != 0) {
            SmDeviceEntity smDeviceEntity = new SmDeviceEntity().selectById(smDeviceId);
            SmDeviceDTO sDto = new SmDeviceDTO(smDeviceEntity);
            setDeviceSn(sDto.getDeviceSn());
            setPersonnelName(sDto.getPersonnelName());
        }
    }

    public void addTime() {
        // 转换为 LocalDateTime
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(sendTime),
                ZoneId.systemDefault());

        // 格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        setCaiYangTime(formattedDateTime);
    }
}
