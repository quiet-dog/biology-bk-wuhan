package com.biology.domain.manage.equipment.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.IService;

@Service
public interface EquipmentService extends IService<EquipmentEntity> {

    EquipmentEntity getById(Long equipmentId);

    List<EquipmentEntity> listByEquipmentIds(List<Long> equipmentIds);

    String getEquipmentNameByCode(String equipmentCode);

    EquipmentEntity getByEquipmentCode(String equipmentCode);

}
