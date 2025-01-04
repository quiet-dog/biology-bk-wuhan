package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.bouncycastle.asn1.ocsp.ResponderID;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.admin.customize.aop.accessLog.AccessLog;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.enums.common.BusinessTypeEnum;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Internal;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.materials.MaterialsApplicationService;
import com.biology.domain.manage.materials.command.AddMaterialsCommand;
import com.biology.domain.manage.materials.command.UpdateMaterialsCommand;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.materials.dto.MaterialsDTO;
import com.biology.domain.manage.materials.dto.StockEchatDTO;
import com.biology.domain.manage.materials.query.SearchMaterialsQuery;
import com.biology.domain.manage.task.query.TaskStockQuery;
import com.biology.domain.system.user.command.AddUserCommand;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "物料档案", description = "物料档案的增删查改")
@RestController
@RequestMapping("/manage/materials")
@Validated
@RequiredArgsConstructor
public class MaterialsController extends BaseController {
    private final MaterialsApplicationService materialsApplicationService;

    private final MaterialsService materialsService;

    @Operation(summary = "创建物料档案")
    @PostMapping
    public ResponseDTO<Void> createPolicy(@RequestBody AddMaterialsCommand command) {

        // 限制物料编码唯一
        QueryWrapper<MaterialsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", command.getCode());
        queryWrapper.last("LIMIT 1");
        MaterialsEntity materialsEntity = materialsService.getOne(queryWrapper);
        if (materialsEntity != null) {
            return ResponseDTO.fail(new ApiException(Internal.MATERIALS_CODE_EXISTS));
        }

        materialsApplicationService.addMaterials(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取物料档案信息")
    @GetMapping("{materialsId}")
    public ResponseDTO<MaterialsDTO> info(@PathVariable(value = "materialsId", required = false) Long materialsId) {
        MaterialsDTO materialsDTO = materialsApplicationService.get(materialsId);
        return ResponseDTO.ok(materialsDTO);
    }

    @Operation(summary = "获取物料档案列表")
    @GetMapping
    public ResponseDTO<PageDTO<MaterialsDTO>> list(SearchMaterialsQuery query) {
        PageDTO<MaterialsDTO> list = materialsApplicationService.getMaterialsList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "更新物料档案")
    @PutMapping("{materialsId}")
    public ResponseDTO<Void> edit(@PathVariable("materialsId") Long materialsId,
            @RequestBody UpdateMaterialsCommand command) {
        command.setMaterialsId(materialsId);
        materialsApplicationService.updateMaterials(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除物料档案")
    @DeleteMapping
    public ResponseDTO<Void> remove(@RequestParam @NotNull List<Long> materialsIds) {
        materialsApplicationService.deleteMaterialss(materialsIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "物料列表导入")
    @PostMapping("/excel")
    public ResponseDTO<Void> importMaterialsByExcel(MultipartFile file) {
        List<AddMaterialsCommand> commands = CustomExcelUtil.readFromRequest(AddMaterialsCommand.class, file);

        for (AddMaterialsCommand command : commands) {
            QueryWrapper<MaterialsEntity> queryWrapper = new QueryWrapper<>();
            if (command.getCode() != null) {
                queryWrapper = queryWrapper.eq("code", command.getCode());
                // 限制物料编码唯一
                queryWrapper = queryWrapper.last("LIMIT 1");
                // 查找是否存在
                MaterialsEntity materialsEntity = materialsService.getOne(queryWrapper);
                if (materialsEntity != null) {
                    UpdateMaterialsCommand updateMaterialsCommand = new UpdateMaterialsCommand();
                    updateMaterialsCommand.setName(materialsEntity.getName());
                    updateMaterialsCommand.setCode(materialsEntity.getCode());
                    updateMaterialsCommand.setType(materialsEntity.getType());
                    updateMaterialsCommand.setSpecification(materialsEntity.getSpecification());
                    updateMaterialsCommand.setUnit(materialsEntity.getUnit());
                    updateMaterialsCommand.setMaterialsId(materialsEntity.getMaterialsId());
                    updateMaterialsCommand.setStock(materialsEntity.getStock() + command.getStock());
                    updateMaterialsCommand.setLastStock(command.getStock());
                    materialsApplicationService.updateMaterials(updateMaterialsCommand);
                } else {
                    materialsApplicationService.addMaterials(command);
                }
            } else {
                materialsApplicationService.addMaterials(command);
            }
        }
        return ResponseDTO.ok();
    }

    /**
     * 下载批量导入模板
     */
    @Operation(summary = "物料导入excel下载")
    @GetMapping("/excelTemplate")
    public void downloadExcelTemplate(HttpServletResponse response) {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new AddMaterialsCommand()), AddMaterialsCommand.class,
                response);
    }

    @Operation(summary = "获取物料档案统计")
    @GetMapping("/stock/{materialsId}")
    public ResponseDTO<StockEchatDTO> stockMaterials(
            @PathVariable(value = "materialsId", required = false) Long materialsId, TaskStockQuery dateType) {
        return ResponseDTO.ok(materialsApplicationService.stockMaterials(materialsId, dateType.getDayType()));
    }

}
