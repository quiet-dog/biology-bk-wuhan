package com.biology.domain.manage.threshold.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.threshold.command.AddThresholdCommand;
import com.biology.domain.manage.threshold.command.AddThresholdValue;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyEntity;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyService;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdService;
import com.biology.domain.manage.threshold.db.ThresholdSopEntity;
import com.biology.domain.manage.threshold.db.ThresholdSopService;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueService;
import com.biology.domain.manage.threshold.dto.SendThresholdDTO;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ThresholdModel extends ThresholdEntity {
    private ThresholdService thresholdService;

    private ThresholdValueService thresholdValueService;

    private List<AddThresholdValue> values;

    private ThresholdEmergencyService thresholdEmergencyService;

    private ThresholdSopService thresholdSopService;

    private WebClient opcClient;

    @Schema(description = "应急预案Ids")
    private List<Long> emergencyIds;

    private List<Long> sopIds;

    public ThresholdModel(ThresholdService thresholdService, ThresholdValueService thresholdValueService,
            ThresholdEmergencyService thresholdEmergencyService, ThresholdSopService thresholdSopService,
            WebClient opcClient) {
        this.thresholdService = thresholdService;
        this.thresholdValueService = thresholdValueService;
        this.thresholdEmergencyService = thresholdEmergencyService;
        this.thresholdSopService = thresholdSopService;
        this.opcClient = opcClient;
    }

    public ThresholdModel(ThresholdEntity entity, ThresholdService thresholdService,
            ThresholdValueService thresholdValueService, ThresholdEmergencyService thresholdEmergencyService,
            ThresholdSopService thresholdSopService, WebClient opcClient) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.thresholdService = thresholdService;
        this.thresholdValueService = thresholdValueService;
        this.thresholdEmergencyService = thresholdEmergencyService;
        this.thresholdSopService = thresholdSopService;
        this.opcClient = opcClient;
    }

    public void loadAddThresholdCommand(AddThresholdCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "thresholdId");
        }
    }

    public void loadUpdateThresholdCommand(AddThresholdCommand command) {
        if (command != null) {
            loadAddThresholdCommand(command);
        }
    }

    public void deleteValues() {
        QueryWrapper<ThresholdValueEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("threshold_id", getThresholdId());
        thresholdValueService.remove(queryWrapper);
    }

    public void addEmergencys() {
        if (getEmergencyIds() == null || getEmergencyIds().isEmpty()) {
            return;
        }
        List<ThresholdEmergencyEntity> emergencyEntities = new ArrayList<>();
        for (Long emergencyId : getEmergencyIds()) {
            ThresholdEmergencyEntity emergencyEntity = new ThresholdEmergencyEntity();
            emergencyEntity.setThresholdId(getThresholdId());
            emergencyEntity.setEmergencyId(emergencyId);
            emergencyEntities.add(emergencyEntity);
        }
        thresholdEmergencyService.saveBatch(emergencyEntities);
        // CacheCenter.thresholdEmergencyCache.set(getThresholdId(), emergencyEntities);
    }

    public void cleanEmergencys() {
        QueryWrapper<ThresholdEmergencyEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("threshold_id", getThresholdId());
        thresholdEmergencyService.remove(queryWrapper);
    }

    public void addSops() {
        if (getSopIds() == null || getSopIds().isEmpty()) {
            return;
        }
        List<ThresholdSopEntity> sopEntities = new ArrayList<>();
        for (Long sopId : getSopIds()) {
            ThresholdSopEntity sopEntity = new ThresholdSopEntity();
            sopEntity.setThresholdId(getThresholdId());
            sopEntity.setSopId(sopId);
            sopEntities.add(sopEntity);
        }
        thresholdSopService.saveBatch(sopEntities);
        // CacheCenter.thresholdSopCache.set(getThresholdId(), sopEntities);
    }

    public void cleanSops() {
        QueryWrapper<ThresholdSopEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("threshold_id", getThresholdId());
        thresholdSopService.remove(queryWrapper);
    }

    public void addValues() {
        List<ThresholdValueEntity> valueEntities = new ArrayList<>();
        for (AddThresholdValue value : getValues()) {
            ThresholdValueEntity valueEntity = new ThresholdValueEntity();
            valueEntity.setThresholdId(getThresholdId());
            valueEntity.setMax(value.getMax());
            valueEntity.setMin(value.getMin());
            valueEntity.setLevel(value.getLevel());
            valueEntities.add(valueEntity);
        }
        thresholdValueService.saveBatch(valueEntities);
        // CacheCenter.thresholdValuesCache.set(getThresholdId(), valueEntities);
        // 发送消息
        SendThresholdDTO sDto = new SendThresholdDTO();
        sDto.setThreshold(this);
        sDto.setThresholdValues(valueEntities);
        sendMsg(sDto);
    }

    public void sendMsg(SendThresholdDTO sDto) {
        opcClient.post()
                .uri("/api/recYuZhiApi")
                .bodyValue(sDto)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }

    public boolean insert() {
        super.insert();
        addEmergencys();
        addSops();
        addValues();
        return true;
    }

    public boolean updateById() {
        super.updateById();
        cleanEmergencys();
        cleanSops();
        addEmergencys();
        addSops();
        deleteValues();
        addValues();
        return true;
    }

    public boolean deleteById() {
        super.deleteById();
        cleanEmergencys();
        deleteValues();
        cleanSops();
        return true;
    }
}
