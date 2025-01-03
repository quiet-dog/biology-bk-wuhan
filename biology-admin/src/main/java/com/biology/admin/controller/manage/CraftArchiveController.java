package com.biology.admin.controller.manage;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Internal;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.craftarchive.CraftArchiveApplicationService;
import com.biology.domain.manage.craftarchive.command.AddCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.command.ExcelCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.command.UpdateCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;
import com.biology.domain.manage.craftarchive.query.SearchCraftArchiveQuery;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Tag(name = "工艺档案API", description = "工艺档案的增删查改")
@RestController
@RequestMapping("/manage/craft-archive")
@Validated
@RequiredArgsConstructor
public class CraftArchiveController extends BaseController {

    private final CraftArchiveApplicationService craftArchiveApplicationService;

    @Operation(summary = "添加工艺档案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddCraftArchiveCommand command) {
        craftArchiveApplicationService.createCraftArchive(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新工艺档案")
    @PutMapping("/{craftArchiveId}")
    public ResponseDTO<Void> update(@PathVariable Long craftArchiveId,
            @Validated @RequestBody UpdateCraftArchiveCommand command) {
        command.setCraftArchiveId(craftArchiveId);
        craftArchiveApplicationService.updateCraftArchive(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除工艺档案")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> craftArchiveIds) {
        craftArchiveApplicationService.deleteCraftArchives(craftArchiveIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取工艺档案信息")
    @GetMapping("/{craftArchiveId}")
    public ResponseDTO<CraftArchiveDTO> info(@PathVariable Long craftArchiveId) {
        CraftArchiveDTO craftArchiveDTO = craftArchiveApplicationService.getCraftArchiveInfo(craftArchiveId);
        return ResponseDTO.ok(craftArchiveDTO);
    }

    @Operation(summary = "获取工艺档案列表")
    @GetMapping
    public ResponseDTO<PageDTO<CraftArchiveDTO>> list(SearchCraftArchiveQuery query) {
        PageDTO<CraftArchiveDTO> list = craftArchiveApplicationService.getCraftArchiveList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "导出工艺档案")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody SearchCraftArchiveQuery query)
            throws UnsupportedEncodingException {
        craftArchiveApplicationService.export(response, query);
    }

    @Operation(summary = "下载工艺档案导入模板")
    @GetMapping("/excelTemplate")
    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new ExcelCraftArchiveCommand()), ExcelCraftArchiveCommand.class,
                response);
    }

    // 导入工艺档案
    @Operation(summary = "导入工艺档案")
    @PostMapping("/excel")
    public ResponseDTO<String> importCraftArchive(MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseDTO.fail(new ApiException(Internal.EXCEL_PROCESS_ERROR));
        }

        // 读取Excel数据到ExcelCommand对象列表
        List<ExcelCraftArchiveCommand> excelCommands = CustomExcelUtil.readFromRequest(
                ExcelCraftArchiveCommand.class, file);

        // 转换成添加命令对象列表
        List<AddCraftArchiveCommand> addCommands = new ArrayList<>();
        for (ExcelCraftArchiveCommand excelCommand : excelCommands) {
            AddCraftArchiveCommand addCommand = new AddCraftArchiveCommand();
            // TODO: 根据Excel数据设置addCommand的属性
            addCommand.setCraftArchiveName(excelCommand.getCraftArchiveName());
            addCommand.setCraftArchiveCode(excelCommand.getCraftArchiveCode());
            addCommand.setVersion(excelCommand.getVersion());
            addCommand.setCreator(excelCommand.getCreator());
            addCommand.setCreateDate(excelCommand.getCreateDate());
            addCommands.add(addCommand);
        }

        // 批量处理导入
        for (AddCraftArchiveCommand command : addCommands) {
            craftArchiveApplicationService.createCraftArchive(command);
        }

        return ResponseDTO.ok();
    }
}
