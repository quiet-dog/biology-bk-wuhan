package com.biology.admin.controller.manage;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.craftnode.CraftNodeApplicationService;
import com.biology.domain.manage.craftnode.command.AddCraftNodeCommand;
import com.biology.domain.manage.craftnode.command.UpdateCraftNodeCommand;
import com.biology.domain.manage.craftnode.dto.CraftNodeDTO;
import com.biology.domain.manage.craftnode.query.SearchCraftNodeQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Tag(name = "工艺节点API", description = "工艺节点的增删查改")
@RestController
@RequestMapping("/manage/craft-node")
@Validated
@RequiredArgsConstructor
public class CraftNodeController extends BaseController {

    private final CraftNodeApplicationService craftNodeApplicationService;

    @Operation(summary = "添加工艺节点")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddCraftNodeCommand command) {
        craftNodeApplicationService.addCraftNode(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新工艺节点")
    @PutMapping("/{nodeId}")
    public ResponseDTO<Void> update(@PathVariable Long nodeId, @RequestBody UpdateCraftNodeCommand command) {
        command.setNodeId(nodeId);
        craftNodeApplicationService.updateCraftNode(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除工艺节点")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> nodeIds) {
        craftNodeApplicationService.deleteCraftNodes(nodeIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取工艺节点信息")
    @GetMapping("/{nodeId}")
    public ResponseDTO<CraftNodeDTO> info(@PathVariable Long nodeId) {
        CraftNodeDTO craftNodeDTO = craftNodeApplicationService.getCraftNodeInfo(nodeId);
        return ResponseDTO.ok(craftNodeDTO);
    }

    @Operation(summary = "获取工艺节点列表")
    @GetMapping
    public ResponseDTO<PageDTO<CraftNodeDTO>> list(SearchCraftNodeQuery query) {
        PageDTO<CraftNodeDTO> list = craftNodeApplicationService.getCraftNodeList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "导出工艺节点")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SearchCraftNodeQuery query)
            throws UnsupportedEncodingException {
        String fileName = "工艺节点列表_" + System.currentTimeMillis() + ".xlsx";
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName);

        PageDTO<CraftNodeDTO> list = craftNodeApplicationService.getCraftNodeList(query);

        CustomExcelUtil.writeToResponse(list.getRows(), CraftNodeDTO.class, response);
    }

}
