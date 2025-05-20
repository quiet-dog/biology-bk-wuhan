package com.biology.domain.manage.environment.dto;

import java.util.List;

import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.environment.db.EnvironmentEntity;

import lombok.Data;

@Data
public class SendEnvironmentDTO {
    private EnvironmentEntity environment;
    private List<AlarmlevelDetailEntity> values;
}
