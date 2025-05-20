package com.biology.domain.manage.task.db;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.materials.dto.StockEchatDTO;
import com.biology.domain.manage.task.dto.TaskMaterialsDTO;

public interface MaterialsTaskService extends IService<MaterialsTaskEntity> {

    public StockEchatDTO getStockMaterials(Long marterialsId, String dayType, Boolean isUnit);
}
