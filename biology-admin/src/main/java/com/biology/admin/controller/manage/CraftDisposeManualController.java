package com.biology.admin.controller.manage;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Internal;
import com.biology.domain.manage.craftdisposemanual.CraftDisposeManualApplicationService;
import com.biology.domain.manage.craftdisposemanual.command.AddDisposeManualCommand;
import com.biology.domain.manage.craftdisposemanual.command.UpdateDisposeManualCommand;
import com.biology.domain.manage.craftdisposemanual.dto.DisposeManualDTO;
import com.biology.domain.manage.craftdisposemanual.query.SearchDisposeManualQuery;
import com.biology.domain.manage.craftnode.db.CraftNodeService;

import org.springframework.web.multipart.MultipartFile;

import com.biology.domain.manage.craftdisposemanual.command.ExcelDisposeManualCommand;

import javax.servlet.http.HttpServletResponse;

import com.biology.common.utils.poi.CustomExcelUtil;

import cn.hutool.core.collection.ListUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "工艺数据处置手册API", description = "工艺数据处置手册的增删查改")
@RestController
@RequestMapping("/manage/craft-dispose-manual")
@Validated
@RequiredArgsConstructor
public class CraftDisposeManualController extends BaseController {

    private final CraftDisposeManualApplicationService craftDisposeManualApplicationService;
    private final CraftNodeService craftNodeService;

    @Operation(summary = "添加处置手册")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddDisposeManualCommand command) {
        craftDisposeManualApplicationService.addDisposeManual(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新处置手册")
    @PutMapping("/{disposeManualId}")
    public ResponseDTO<Void> update(@PathVariable Long disposeManualId, @RequestBody UpdateDisposeManualCommand command) {
        command.setCraftDisposeManualId(disposeManualId);
        craftDisposeManualApplicationService.updateDisposeManual(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除处置手册")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> disposeManualIds) {
        craftDisposeManualApplicationService.deleteDisposeManuals(disposeManualIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取处置手册信息")
    @GetMapping("/{disposeManualId}")
    public ResponseDTO<DisposeManualDTO> info(@PathVariable Long disposeManualId) {
        DisposeManualDTO disposeManualDTO = craftDisposeManualApplicationService.getDisposeManualInfo(disposeManualId);
        return ResponseDTO.ok(disposeManualDTO);
    }

    @Operation(summary = "获取处置手册列表")
    @GetMapping
    public ResponseDTO<PageDTO<DisposeManualDTO>> list(SearchDisposeManualQuery query) {
        PageDTO<DisposeManualDTO> list = craftDisposeManualApplicationService.getDisposeManualList(query);
        return ResponseDTO.ok(list);
    }


    @Operation(summary = "下载处置手册导入模板")
    @GetMapping("/excelTemplate")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelDisposeManualCommand()), ExcelDisposeManualCommand.class, response);
    }

    @Operation(summary = "导入处置手册")
    @PostMapping("/excel")
    public ResponseDTO<String> importDisposeManual(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        // 读取Excel数据到ExcelCommand对象列表
        List<ExcelDisposeManualCommand> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelDisposeManualCommand.class, file);

        // 转换成添加命令对象列表
        List<AddDisposeManualCommand> addCommands = new ArrayList<>();
        for (ExcelDisposeManualCommand excelCommand : excelCommands) {
            AddDisposeManualCommand addCommand = new AddDisposeManualCommand();

            // 根据工艺节点编号查询工艺节点ID
            Long craftNodeId = craftNodeService.getCraftNodeIdByCode(excelCommand.getNodeCode());
            
            addCommand.setCraftNodeId(craftNodeId);
            addCommand.setProblemDescription(excelCommand.getProblemDescription());
            addCommand.setEmergencyProcess(excelCommand.getEmergencyProcess());
            addCommand.setResponsibilityDivision(excelCommand.getResponsibilityDivision());
            addCommand.setRequiredTime(excelCommand.getRequiredTime());
            addCommand.setPreventiveMeasures(excelCommand.getPreventiveMeasures());
            addCommands.add(addCommand);
        }

        // 批量处理导入
        for (AddDisposeManualCommand command : addCommands) {
            craftDisposeManualApplicationService.addDisposeManual(command);
        }

        return ResponseDTO.ok();
    }
}
