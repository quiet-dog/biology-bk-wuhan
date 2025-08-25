package com.biology.domain.manage.xsData.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.xsData.db.XsDataEntity;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

@Data
@ExcelSheet(name = "消杀工作数据")
public class XsDataDTO {

    private Long xsDataId;

    private Long xsDeviceId;

    private Long endTime;

    @ExcelColumn(name = "结束时间")
    private String endTimeStr;

    @ExcelColumn(name = "开始时间")
    private Date createTime;

    @ExcelColumn(name = "运行时长")
    private String runTime;

    @ExcelColumn(name = "设备编号")
    private String deviceSn;

    @ExcelColumn(name = "所属区域")
    private String area;

    public XsDataDTO(XsDataEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addOther();
        }
    }

    public void addOther() {
        if (!StrUtil.isEmpty(getDeviceSn())) {
            QueryWrapper<XsDeviceEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("device_sn", getDeviceSn());
            XsDeviceEntity xEntity = new XsDeviceEntity().selectOne(queryWrapper);
            if (xEntity != null) {
                setArea(xEntity.getArea());
            }
        }

        if (endTime != null && endTime > 0) {
            Date date = new Date(endTime);
            // 格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            endTimeStr = sdf.format(date);

            if (createTime == null || endTime == null) {
                return;
            }
            // 计算差值（毫秒）
            long diffMillis = endTime - createTime.getTime();
            System.out.println(diffMillis);
            System.out.println(endTime);
            System.out.println(createTime.getTime());
            if (diffMillis < 0) {
                return;
            }

            long seconds = diffMillis / 1000;
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            long secs = seconds % 60;
            if (hours > 0) {
                setRunTime(String.format("%d小时%d分%d秒", hours, minutes, secs));
            } else if (minutes > 0) {
                setRunTime(String.format("%d分%d秒", minutes, secs));
            } else {
                setRunTime(String.format("%d秒", secs));
            }
        }
    }
}
