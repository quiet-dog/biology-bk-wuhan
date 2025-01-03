package com.biology.domain.manage.emergencyAlarm.dto;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.dto.EmergencyDTO;
import com.biology.domain.manage.emergencyAlarm.db.EmergencyConnectEntity;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.sop.dto.SopDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmergencyConnectDTO {

    @Schema(description = "应急ID")
    private Long emergencyAlarmId;

    @Schema(description = "SOP ID")
    private Long sopId;

    @Schema(description = "sop信息")
    private SopDTO sop;

    @Schema(description = "应急ID")
    private Long emergencyId;

    @Schema(description = "应急信息")
    private EmergencyDTO emergency;

    public EmergencyConnectDTO(EmergencyConnectEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            if (getSopId() != null) {
                SopEntity sopEntity = new SopEntity();
                sopEntity = sopEntity.selectById(getSopId());
                setSop(new SopDTO(sopEntity));
            }

            if (getEmergencyId() != null) {
                EmergencyEntity emergency = new EmergencyEntity();
                emergency = emergency.selectById(getEmergencyId());
                setEmergency(new EmergencyDTO(emergency));
            }

        }
    }
}
