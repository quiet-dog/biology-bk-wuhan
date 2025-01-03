package com.biology.domain.manage.emergency.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.db.EmergencyFileEntity;
import com.biology.domain.manage.emergency.db.KeyWordEntity;

import lombok.Data;

@Data
public class EmergencyDetailDTO {
    private EmergencyDTO emergency;

    private List<EmergencyFileDTO> paths;

    private List<KeywordDTO> keywords;

    public EmergencyDetailDTO(EmergencyEntity emergency, List<EmergencyFileEntity> paths,
            List<KeyWordEntity> keywords) {
        this.emergency = new EmergencyDTO(emergency);
        if (keywords != null) {
            this.keywords = keywords.stream().map(KeywordDTO::new).collect(Collectors.toList());
        }

        if (paths != null) {
            this.paths = paths.stream().map(EmergencyFileDTO::new).collect(Collectors.toList());
        }
    }
}
