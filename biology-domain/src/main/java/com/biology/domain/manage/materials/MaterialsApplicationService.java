package com.biology.domain.manage.materials;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.materials.command.AddMaterialsCommand;
import com.biology.domain.manage.materials.command.AddStockCommand;
import com.biology.domain.manage.materials.command.AddWarehouseCommand;
import com.biology.domain.manage.materials.command.UpdateMaterialsCommand;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.db.WarehouseEntity;
import com.biology.domain.manage.materials.db.WarehouseService;
import com.biology.domain.manage.materials.dto.MaterialsDTO;
import com.biology.domain.manage.materials.dto.NormalDTO;
import com.biology.domain.manage.materials.dto.StockEchatDTO;
import com.biology.domain.manage.materials.dto.WarehouseDTO;
import com.biology.domain.manage.materials.model.MaterialsFactory;
import com.biology.domain.manage.materials.model.MaterialsModel;
import com.biology.domain.manage.materials.model.WarehouseFactory;
import com.biology.domain.manage.materials.model.WarehouseModel;
import com.biology.domain.manage.materials.query.SearchMaterialsQuery;
import com.biology.domain.manage.materials.query.SearchWarehouseQuery;
import com.biology.domain.manage.task.db.MaterialsTaskEntity;
import com.biology.domain.manage.task.db.MaterialsTaskService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialsApplicationService {

    private final MaterialsFactory materialsFactory;

    private final MaterialsService materialsService;

    private final MaterialsTaskService materialsTaskService;

    private final WarehouseService warehouseService;

    private final WarehouseFactory warehouseFactory;

    public void addMaterials(AddMaterialsCommand command) {
        MaterialsModel materialsModel = materialsFactory.create();
        materialsModel.loadAddMaterialsCommand(command);
        materialsModel.insert();
    }

    public void addWarehouse(AddWarehouseCommand command) {
        WarehouseModel wModel = warehouseFactory.create();
        MaterialsEntity materialsEntity = materialsService.getById(command.getMaterialsId());
        if (materialsEntity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, command.getMaterialsId(), "物料");
        }
        wModel.loadAddMaterialsCommand(command);
        wModel.setRemainStock(materialsEntity.getStock());
        wModel.insert();

        materialsEntity.setStock(materialsEntity.getStock() + command.getStock());
        materialsEntity.updateById();
    }

    public void updateMaterials(UpdateMaterialsCommand command) {
        MaterialsModel materialsModel = materialsFactory.loadById(command.getMaterialsId());
        materialsModel.loadAddMaterialsCommand(command);
        materialsModel.updateById();
    }

    public void deleteMaterials(Long materialsId) {
        MaterialsModel materialsModel = materialsFactory.loadById(materialsId);
        materialsModel.deleteById();
    }

    public void deleteMaterialss(List<Long> materialsIds) {
        for (Long materialsId : materialsIds) {
            deleteMaterials(materialsId);
        }
    }

    public PageDTO<MaterialsDTO> getMaterialsList(SearchMaterialsQuery query) {
        Page<MaterialsEntity> page = materialsService.page(query.toPage(), query.toQueryWrapper());
        List<MaterialsDTO> records = page.getRecords().stream().map(MaterialsDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public PageDTO<WarehouseDTO> getWarehouseList(SearchWarehouseQuery query) {
        Page<WarehouseEntity> page = warehouseService.page(query.toPage(), query.toQueryWrapper());
        List<WarehouseDTO> records = page.getRecords().stream().map(WarehouseDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public MaterialsDTO get(Long materialsId) {
        MaterialsModel materialsModel = materialsFactory.loadById(materialsId);
        return new MaterialsDTO(materialsModel);
    }

    public StockEchatDTO stockMaterials(Long materialsId, String dayType) {
        return materialsTaskService.getStockMaterials(materialsId, dayType);
        // StockEchatDTO stockEchatDTO = new StockEchatDTO();
        // stockEchatDTO.setSeriesData(new ArrayList<>());
        // stockEchatDTO.setXAxisData(new ArrayList<>());
        // if (dayType.equals("week")) {
        // materialsTaskService.getBaseMapper().get

        // } else if (dayType.equals("month")) {

        // } else if (dayType.equals("year")) {

        // }

        // QueryWrapper<MaterialsTaskEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.eq("materials_id", materialsId);
        // List<MaterialsTaskEntity> materialsTaskEntities =
        // materialsTaskService.list(queryWrapper);
        // MaterialsModel materialsModel = materialsFactory.loadById(materialsId);
        // // 统计今年每个月的库存 1-12月份

        // LocalDate today = LocalDate.now();
        // LocalDate firstDay = LocalDate.of(today.getYear(), 1, 1);

        // for (int i = 0; i < 12; i++) {
        // String dataTime = firstDay.plusMonths(i).toString();
        // stockEchatDTO.getXAxisData().add(dataTime);
        // // 判断是不是本月
        // if (today.getMonthValue() == i + 1) {
        // stockEchatDTO.getSeriesData().add(materialsModel.getStock());
        // continue;
        // }

        // boolean isExit = false;
        // for (MaterialsTaskEntity materialsTaskEntity : materialsTaskEntities) {
        // if (materialsTaskEntity.getCreateTime().toString().contains(dataTime)) {
        // isExit = true;
        // stockEchatDTO.getSeriesData().add(materialsTaskEntity.getStock());
        // }
        // }
        // if (!isExit) {
        // stockEchatDTO.getSeriesData().add(0.0);
        // }
        // }

    }

    public NormalDTO getAllNormal() {
        return materialsService.getAllNormal();
    }

    public Void AddStock(AddStockCommand command) {
        MaterialsModel materialsModel = materialsFactory.loadById(command.getMaterialsId());
        materialsModel.setStock(materialsModel.getStock() + command.getStock());
        materialsModel.setLastStock(command.getStock());
        materialsModel.updateById();
        return null;
    }
}
