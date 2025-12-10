package com.biology.domain.manage.smData.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.biology.domain.manage.smData.command.AddSmDataCommand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmDataCacheDTO {
    private Long smDeviceId;

    private Double battery;

    private Double co2;

    private Double humility;

    private Double temp;

    private List<Number> huxi;

    private Long sendTime;

    private List<Number> xinDian;

    private Double xinlv;

    private Double xueYang;

    private String tiTai;

    public SmDataCacheDTO(AddSmDataCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this);
        }
    }
}
