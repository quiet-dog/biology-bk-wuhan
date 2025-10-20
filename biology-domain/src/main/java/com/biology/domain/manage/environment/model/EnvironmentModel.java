package com.biology.domain.manage.environment.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.alarmlevel.command.AlarmDetail;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailService;
import com.biology.domain.manage.environment.command.AddEnvironmentCommand;
import com.biology.domain.manage.environment.db.EnvironmentEmergencyEntity;
import com.biology.domain.manage.environment.db.EnvironmentEmergencyService;
import com.biology.domain.manage.environment.db.EnvironmentEntity;
import com.biology.domain.manage.environment.db.EnvironmentService;
import com.biology.domain.manage.environment.db.EnvironmentSopEntity;
import com.biology.domain.manage.environment.db.EnvironmentSopService;
import com.biology.domain.manage.environment.dto.SendEnvironmentDTO;
import com.biology.domain.manage.threshold.dto.SendThresholdDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EnvironmentModel extends EnvironmentEntity {

    private EnvironmentService environmentService;

    private AlarmlevelDetailService alarmlevelDetailService;

    private EnvironmentSopService environmentSopService;

    private EnvironmentEmergencyService environmentEmergencyService;

    private WebClient opcClient;

    @Schema(description = "报警级别信息")
    private List<AlarmDetail> alarmLevels;

    @Schema(description = "应急预案Ids")
    private List<Long> emergencyIds;

    @Schema(description = "SOPIds")
    private List<Long> sopIds;

    public EnvironmentModel(EnvironmentService environmentService, AlarmlevelDetailService alarmlevelDetailService,
            EnvironmentSopService environmentSopService, EnvironmentEmergencyService environmentEmergencyService,
            WebClient opcClient) {
        this.environmentService = environmentService;
        this.alarmlevelDetailService = alarmlevelDetailService;
        this.environmentSopService = environmentSopService;
        this.environmentEmergencyService = environmentEmergencyService;
        this.opcClient = opcClient;
    }

    public EnvironmentModel(EnvironmentEntity environmentEntity, EnvironmentService environmentService,
            AlarmlevelDetailService alarmlevelDetailService, EnvironmentSopService environmentSopService,
            EnvironmentEmergencyService environmentEmergencyService, WebClient opcClient) {
        if (environmentEntity != null) {
            BeanUtils.copyProperties(environmentEntity, this);
        }
        this.environmentService = environmentService;
        this.alarmlevelDetailService = alarmlevelDetailService;
        this.environmentSopService = environmentSopService;
        this.environmentEmergencyService = environmentEmergencyService;
        this.opcClient = opcClient;
    }

    public void loadAddEnvironmentCommand(AddEnvironmentCommand command) {
        if (command != null) {
            BeanUtils.copyProperties(command, this, "environmentId");
        }
    }

    public void loadUpdateEnvironmentCommand(AddEnvironmentCommand command) {
        if (command != null) {
            loadAddEnvironmentCommand(command);
        }
    }

    public void addLevels() {
        if (getAlarmLevels() != null && getAlarmLevels().size() > 0) {
            List<AlarmlevelDetailEntity> alarmlevelDetails = new ArrayList<>();
            for (AlarmDetail alarmlevelDetailDTO : getAlarmLevels()) {
                AlarmlevelDetailEntity alarmlevelDetailEntity = new AlarmlevelDetailEntity();
                alarmlevelDetailEntity.setEnvironmentId(getEnvironmentId());
                alarmlevelDetailEntity.setLevel(alarmlevelDetailDTO.getLevel());
                alarmlevelDetailEntity.setMax(alarmlevelDetailDTO.getMax());
                alarmlevelDetailEntity.setMin(alarmlevelDetailDTO.getMin());
                alarmlevelDetails.add(alarmlevelDetailEntity);
            }
            alarmlevelDetailService.saveBatch(alarmlevelDetails);
            // CacheCenter.alarmlevelDetailCache.set(getEnvironmentId(), alarmlevelDetails);
            SendEnvironmentDTO sDto = new SendEnvironmentDTO();
            sDto.setValues(alarmlevelDetails);
            sDto.setEnvironment(this);
            sendMsg(sDto);
        }

    }

    public void cleanLevels() {
        QueryWrapper<AlarmlevelDetailEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("environment_id", getEnvironmentId());
        alarmlevelDetailService.remove(queryWrapper);
    }

    public void addSops() {
        if (getSopIds() != null) {
            List<EnvironmentSopEntity> environmentSopEntitys = new ArrayList<>();
            for (Long id : getSopIds()) {
                EnvironmentSopEntity entity = new EnvironmentSopEntity();
                entity.setEnvironmentId(getEnvironmentId());
                entity.setSopId(id);
                environmentSopEntitys.add(entity);
            }
            environmentSopService.saveBatch(environmentSopEntitys);
        }
    }

    public void cleanSops() {
        QueryWrapper<EnvironmentSopEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("environment_id", getEnvironmentId());
        environmentSopService.remove(queryWrapper);
    }

    public void addEmergencies() {
        if (getEmergencyIds() != null) {
            List<EnvironmentEmergencyEntity> environmentEmergencyEntities = new ArrayList<>();
            for (Long id : getEmergencyIds()) {
                EnvironmentEmergencyEntity entity = new EnvironmentEmergencyEntity();
                entity.setEnvironmentId(getEnvironmentId());
                entity.setEmergencyId(id);
                environmentEmergencyEntities.add(entity);
            }
            environmentEmergencyService.saveBatch(environmentEmergencyEntities);
            // CacheCenter.environmentEmergencyCache.set(getEnvironmentId(),
            // environmentEmergencyEntities);
        }
    }

    public void cleanEmergencies() {
        QueryWrapper<EnvironmentEmergencyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("environment_id", getEnvironmentId());
        environmentEmergencyService.remove(queryWrapper);
    }

    public void sendMsg(SendEnvironmentDTO sDto) {
        opcClient.post()
                .uri("/api/recHuanYuZhiApi")
                .bodyValue(sDto)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }

    public boolean insert() {
        super.insert();
        addLevels();
        addEmergencies();
        addSops();
        return true;
    }

    public boolean updateById() {
        super.updateById();
        cleanLevels();
        addLevels();
        cleanSops();
        addSops();
        cleanEmergencies();
        addEmergencies();
        return true;
    }
}
