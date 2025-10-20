package com.biology.domain.manage.emergency.model;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.emergency.command.AddEmergencyCommand;
import com.biology.domain.manage.emergency.command.UpdateEmergencyCommand;
import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.db.EmergencyEquipmentEntity;
import com.biology.domain.manage.emergency.db.EmergencyEquipmentService;
import com.biology.domain.manage.emergency.db.EmergencyFileEntity;
import com.biology.domain.manage.emergency.db.EmergencyFileService;
import com.biology.domain.manage.emergency.db.EmergencyKeywordEntity;
import com.biology.domain.manage.emergency.db.EmergencyKeywordService;
import com.biology.domain.manage.emergency.db.KeyWordService;
import com.biology.domain.manage.sop.db.SopEquipmentEnity;
import com.biology.domain.manage.emergency.db.EmergencyService;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EmergencyModel extends EmergencyEntity {

    private List<String> paths;

    private List<Long> keywordIds;

    private List<Long> equipmentIds;

    private EmergencyService emergencyService;

    private KeyWordService keyWordService;

    private EmergencyFileService emergencyFileService;

    private EmergencyKeywordService emergencyKeywordService;

    private EmergencyEquipmentService emergencyEquipmentService;

    public EmergencyModel(EmergencyService emergencyService, KeyWordService keyWordService,
            EmergencyFileService emergencyFileService, EmergencyKeywordService emergencyKeywordService,
            EmergencyEquipmentService emergencyEquipmentService) {
        this.emergencyService = emergencyService;
        this.keyWordService = keyWordService;
        this.emergencyFileService = emergencyFileService;
        this.emergencyKeywordService = emergencyKeywordService;
        this.emergencyEquipmentService = emergencyEquipmentService;
    }

    public EmergencyModel(EmergencyEntity entity, EmergencyService emergencyService,
            KeyWordService keyWordService,
            EmergencyFileService emergencyFileService, EmergencyKeywordService emergencyKeywordService,
            EmergencyEquipmentService emergencyEquipmentService) {
        this(emergencyService, keyWordService, emergencyFileService, emergencyKeywordService,
                emergencyEquipmentService);
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
    }

    public void loadAddEmergencyCommand(AddEmergencyCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "emergencyId");
        }
    }

    public void loadUpdateEmergencyCommand(UpdateEmergencyCommand command) {
        if (command != null) {
            loadAddEmergencyCommand(command);
        }
    }

    public void checkEmergencyKeyWord() {
        if (keywordIds != null && !keywordIds.isEmpty()) {
            keywordIds.forEach(keywordId -> {
                if (keyWordService.getById(keywordId) == null) {
                    throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, keywordId, "关键词");
                }
            });
        }
    }

    public boolean saveKeyword() {
        List<EmergencyKeywordEntity> emergencyKeywordEntities = new ArrayList<>();
        if (keywordIds != null && !keywordIds.isEmpty()) {
            keywordIds.forEach(keywordId -> {
                EmergencyKeywordEntity emergencyKeywordEntity = new EmergencyKeywordEntity();
                emergencyKeywordEntity.setEmergencyId(getEmergencyId());
                emergencyKeywordEntity.setKeywordId(keywordId);
                emergencyKeywordEntities.add(emergencyKeywordEntity);
            });
        }

        return emergencyKeywordService.saveBatch(emergencyKeywordEntities);
    }

    public boolean savePath() {
        List<EmergencyFileEntity> emergencyFileServices = new ArrayList<>();
        if (paths != null && !paths.isEmpty()) {
            paths.forEach(path -> {
                EmergencyFileEntity emergencyFileService = new EmergencyFileEntity();
                emergencyFileService.setPath(path);
                emergencyFileService.setEmergencyId(getEmergencyId());
                emergencyFileServices.add(emergencyFileService);
            });
        }

        return emergencyFileService.saveBatch(emergencyFileServices);
    }

    public boolean cleanOldPath() {
        QueryWrapper<EmergencyFileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("emergency_id", getEmergencyId());
        return emergencyFileService.remove(queryWrapper);
    }

    public boolean cleanOldKeyword() {
        QueryWrapper<EmergencyKeywordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("emergency_id", getEmergencyId());
        return emergencyKeywordService.remove(queryWrapper);
    }

    public void addEquipmentIds() {
        List<EmergencyEquipmentEntity> kts = new ArrayList<>();

        if (getEquipmentIds() != null) {
            for (Long equipmentId : equipmentIds) {
                EmergencyEquipmentEntity kt = new EmergencyEquipmentEntity();
                kt.setEquipmentId(equipmentId);
                kt.setEmergencyId(getEmergencyId());
                kts.add(kt);
            }
        }
        emergencyEquipmentService.saveBatch(kts);
    }

    public void cleanEquipmentIds() {
        QueryWrapper<EmergencyEquipmentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("emergency_id", getEmergencyId());
        emergencyEquipmentService.remove(queryWrapper);
    }

    public boolean insert() {
        super.insert();
        addEquipmentIds();
        return savePath() && saveKeyword();
    }

    public boolean updateById() {
        super.updateById();
        cleanOldPath();
        cleanOldKeyword();
        cleanEquipmentIds();
        addEquipmentIds();
        return savePath() && saveKeyword();
    }

    public boolean deleteById() {
        cleanOldPath();
        cleanOldKeyword();
        cleanEquipmentIds();

        return super.deleteById();
    }
}
