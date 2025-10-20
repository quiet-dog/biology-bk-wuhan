package com.biology.domain.manage.sop.model;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.knowledge.db.KnowledgeFileEntity;
import com.biology.domain.manage.sop.command.AddSopCommand;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.sop.db.SopEquipmentEnity;
import com.biology.domain.manage.sop.db.SopEquipmentService;
import com.biology.domain.manage.sop.db.SopFileEntity;
import com.biology.domain.manage.sop.db.SopFileService;
import com.biology.domain.manage.sop.db.SopService;
import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SopModel extends SopEntity {
    private List<String> paths;

    private List<Long> equipmentIds;

    private SopFileService sopFileService;

    private SopService sopService;

    private SopEquipmentService sopEquipmentService;

    public SopModel(SopService sopService, SopFileService sopFileService, SopEquipmentService sopEquipmentService) {
        this.sopService = sopService;
        this.sopFileService = sopFileService;
        this.sopEquipmentService = sopEquipmentService;
    }

    public SopModel(SopEntity entity, SopService sopService, SopFileService sopFileService,
            SopEquipmentService sopEquipmentService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.sopService = sopService;
        this.sopFileService = sopFileService;
        this.sopEquipmentService = sopEquipmentService;
    }

    public void loadAddSopCommand(AddSopCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "sopId");
        }
    }

    public void addEquipmentIds() {
        List<SopEquipmentEnity> kts = new ArrayList<>();
        for (Long equipmentId : equipmentIds) {
            SopEquipmentEnity kt = new SopEquipmentEnity();
            kt.setEquipmentId(equipmentId);
            kt.setSopId(getSopId());
            kts.add(kt);
        }
        sopEquipmentService.saveBatch(kts);
    }

    public void cleanEquipmentIds() {
        QueryWrapper<SopEquipmentEnity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sop_id", getSopId());
        sopEquipmentService.remove(queryWrapper);
    }

    public boolean saveFile() {
        if (paths != null && paths.size() > 0) {
            List<SopFileEntity> kts = new ArrayList<>();
            for (String path : paths) {
                SopFileEntity kt = new SopFileEntity();
                kt.setPath(path);
                kt.setSopId(getSopId());
                kts.add(kt);
            }
            sopFileService.saveBatch(kts);
            // CacheCenter.sopFileCache.set(getSopId(), kts);
            return true;
        } else {
            // CacheCenter.sopFileCache.delete(getSopId());
        }
        return true;
    }

    public boolean cleanFile() {
        return sopFileService.removeById(getSopId());
    }

    public boolean insert() {
        super.insert();
        // CacheCenter.sopCache.set(getSopId(), this);
        // addEquipmentIds();
        return saveFile();
    }

    public boolean updateById() {
        cleanFile();
        saveFile();
        super.updateById();
        // CacheCenter.sopCache.set(getSopId(), this);
        // cleanEquipmentIds();
        // addEquipmentIds();
        return true;
    }

    public boolean deleteById() {

        return super.deleteById();
    }
}
