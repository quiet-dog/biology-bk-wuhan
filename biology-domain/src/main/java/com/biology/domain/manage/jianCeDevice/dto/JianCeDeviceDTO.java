package com.biology.domain.manage.jianCeDevice.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceEntity;
import com.biology.domain.manage.nongDuData.command.NongDuDTO;

import lombok.Data;

@Data
public class JianCeDeviceDTO {
    private Long jianCeDeviceId;

    private String deviceSn;

    private String area;

    private String name;

    private String workStatus;

    private Boolean isOnline;

    private String isOnlineStr;

    private Long lastTime;

    private String lastTimeStr;

    public JianCeDeviceDTO(JianCeDeviceEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addOther();
        }
    }

    public void addOther() {
        NongDuDTO nDto = CacheCenter.jianCeDataCache.getObjectById("deviceSn");
        if (nDto != null) {
            setIsOnline(true);
            setIsOnlineStr("在线");

            if (nDto.getWorkingStatus() != null && nDto.getWorkingStatus().equals(1)) {
                setWorkStatus("启动");
            } else {
                setWorkStatus("停止");
            }
        } else {
            setIsOnlineStr("离线");
        }

        if (lastTime != null) {
            Date d = new Date(lastTime);
            // 格式化
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatted = sdf.format(d);
            setLastTimeStr(formatted);
        }

    }
}
