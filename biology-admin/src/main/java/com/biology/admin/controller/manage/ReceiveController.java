package com.biology.admin.controller.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.db.MaterialsService;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.receive.ReceiveApplicationService;
import com.biology.domain.manage.receive.command.AddReceiveCommand;
import com.biology.domain.manage.receive.command.ExcelReceiveCommand;
import com.biology.domain.manage.receive.command.UpdateReceiveCommand;
import com.biology.domain.manage.receive.db.ReceiveEntity;
import com.biology.domain.manage.receive.dto.ReceiveDTO;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsAllEchart;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockEchart;
import com.biology.domain.manage.receive.dto.ReceiveZhuDTO;
import com.biology.domain.manage.receive.query.ReceiveMaterialsStockQuery;
import com.biology.domain.manage.receive.query.ReceiveMaterialsTypeQuery;
import com.biology.domain.manage.receive.query.ReceiveQuery;
import com.biology.domain.manage.receive.query.ReceiveStockQuery;
import com.biology.domain.manage.receive.query.ScreenReceiveMaterialsStockQuery;
import com.biology.domain.system.user.command.AddUserCommand;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "领用记录", description = "领用记录的增删查改")
@RestController
@RequestMapping("/manage/receive")
@Validated
@RequiredArgsConstructor
public class ReceiveController extends BaseController {

    private final ReceiveApplicationService receiveApplicationService;

    private final MaterialsService materialsService;

    private final PersonnelService personnelService;

    @Operation(summary = "添加领用记录")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddReceiveCommand command) {
        receiveApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新领用记录")
    @PostMapping("/{receiveId}")
    public ResponseDTO<Void> update(@RequestBody UpdateReceiveCommand command) {
        receiveApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除领用记录")
    @DeleteMapping
    public ResponseDTO<Void> deleteReveives(@RequestParam List<Long> reveiveIds) {
        receiveApplicationService.deleteReveives(reveiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取领用记录列表")
    @GetMapping
    public ResponseDTO<PageDTO<ReceiveDTO>> list(ReceiveQuery query) {
        PageDTO<ReceiveDTO> list = receiveApplicationService.getReceives(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取领用记录信息")
    @GetMapping("/{receiveId}")
    public ResponseDTO<ReceiveDTO> info(@PathVariable(value = "receiveId", required = false) Long receiveId) {
        ReceiveDTO receiveDTO = receiveApplicationService.getReceiveInfo(receiveId);
        return ResponseDTO.ok(receiveDTO);
    }

    @Operation(summary = "领用记录导入excel下载")
    @GetMapping("/excelTemplate")
    public void downloadExcelTemplate(HttpServletResponse response) {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelReceiveCommand()), ExcelReceiveCommand.class,
                response);
    }

    @Operation(summary = "领用记录列表导入")
    @PostMapping("/excel")
    public ResponseDTO<Void> importReceiveByExcel(MultipartFile file) {
        List<ExcelReceiveCommand> commands = CustomExcelUtil.readFromRequest(ExcelReceiveCommand.class, file);

        List<AddReceiveCommand> addReceiveCommands = new ArrayList<>();
        for (ExcelReceiveCommand command : commands) {
            // receiveApplicationService.create(command);
            AddReceiveCommand a = new AddReceiveCommand();
            QueryWrapper<MaterialsEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", command.getMaterialCode());
            MaterialsEntity entity = materialsService.getOne(queryWrapper);
            if (entity == null) {
                throw new ApiException(Business.MAT_SERVICE_UNAVAILABLE, command.getMaterialCode());
            }

            PersonnelEntity personnelEntity = personnelService.getPersonnelByCode(command.getReceiverCode());
            if (personnelEntity == null) {
                throw new ApiException(Business.RECEIVER_SERVICE_UNAVAILABLE, command.getReceiverCode());
            }
            a.setReceiveUserId(personnelEntity.getPersonnelId());
            a.setReceiveNum(command.getReceiveNum());
            a.setReceiveExplain(a.getReceiveExplain());
            a.setMaterialsId(entity.getMaterialsId());
            addReceiveCommands.add(a);
        }
        for (AddReceiveCommand command : addReceiveCommands) {
            receiveApplicationService.create(command);
        }
        return ResponseDTO.ok();
    }

    @Operation(summary = "数据大屏获取物料每天的接收数量")
    @GetMapping("/stock")
    public ReceiveStockEchart getStock(ReceiveStockQuery query) {
        return receiveApplicationService.getReceiveStock(query);
    }

    @Operation(summary = "管理后台根据物料名称获取各种类型的数量")
    @GetMapping("/stockName")
    public ResponseDTO<List<ReceiveMaterialsStockDTO>> getReceiveStockByName(ReceiveMaterialsStockQuery query) {
        return ResponseDTO.ok(receiveApplicationService.getReceiveStockByName(query));
    }

    @Operation(summary = "数据大屏获取物料不同种类的用量分析")
    @GetMapping("/allType")
    public ResponseDTO<List<ReceiveMaterialsAllEchart>> getReceiveAllTypeStock() {
        return ResponseDTO.ok(receiveApplicationService.getReceiveAllTypeStock());
    }

    @Operation(summary = "数据大屏根据物料名称获取物料不同种类的用量分析")
    @GetMapping("/allTypeByName")
    public ResponseDTO<List<ReceiveMaterialsStockDTO>> getReceiveAllTypeByName(ReceiveMaterialsTypeQuery query) {
        return ResponseDTO.ok(receiveApplicationService.getReceiveAllTypeByName(query));
    }

    @Operation(summary = "数据大屏根据不同领用说明获取物料不同种类的用量分析")
    @GetMapping("/allByReceiveExplain")
    public ReceiveZhuDTO getReceiveAllReceiveExplain() {
        return receiveApplicationService.getReceiveAllReceiveExplain();
    }

    @Operation(summary = "管理后台根据物料名称获取各种领用类型的数量")
    @GetMapping("/stockNameType")
    public ResponseDTO<List<ReceiveMaterialsStockDTO>> getReceiveStockByNameAndType(ReceiveMaterialsStockQuery query) {
        return ResponseDTO.ok(receiveApplicationService.getReceiveStockByNameType(query));
    }

}
