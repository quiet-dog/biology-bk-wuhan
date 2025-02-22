package com.biology.admin.controller.manage;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.receive.command.AddReceiveCommand;
import com.biology.domain.manage.receive.command.UpdateReceiveCommand;
import com.biology.domain.manage.receive.dto.ReceiveDTO;
import com.biology.domain.manage.receive.query.ReceiveQuery;
import com.biology.domain.manage.report.ReportApplicationService;
import com.biology.domain.manage.report.command.AddReportCommand;
import com.biology.domain.manage.report.command.UpdateReportCommand;
import com.biology.domain.manage.report.dto.ReportDTO;
import com.biology.domain.manage.report.dto.StockEchartDTO;
import com.biology.domain.manage.report.dto.StockReportDTO;
import com.biology.domain.manage.report.query.ReportQuery;
import com.biology.domain.manage.report.query.StockReportQuery;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "人员上报", description = "人员上报的增删查改")
@RestController
@RequestMapping("/manage/report")
@Validated
@RequiredArgsConstructor
public class ReportController extends BaseController {
    private final ReportApplicationService reportApplicationService;

    @Operation(summary = "添加人员上报")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddReportCommand command) {
        reportApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新人员上报")
    @PostMapping("/{reportId}")
    public ResponseDTO<Void> update(@RequestBody UpdateReportCommand command) {
        reportApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除人员上报")
    @DeleteMapping
    public ResponseDTO<Void> deleteReports(@RequestParam List<Long> reportIds) {
        reportApplicationService.deletes(reportIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取人员上报列表")
    @GetMapping
    public ResponseDTO<PageDTO<ReportDTO>> list(ReportQuery query) {
        PageDTO<ReportDTO> list = reportApplicationService.getReports(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取人员上报信息")
    @GetMapping("/{reportId}")
    public ResponseDTO<ReportDTO> info(@PathVariable(value = "reportId", required = false) Long reportId) {
        ReportDTO receiveDTO = reportApplicationService.getById(reportId);
        return ResponseDTO.ok(receiveDTO);
    }

    @Operation(summary = "获取人员上报统计")
    @GetMapping("/stock")
    public ResponseDTO<StockEchartDTO> stock(StockReportQuery query) {
        StockEchartDTO stock = reportApplicationService.stock(query);
        return ResponseDTO.ok(stock);
    }

}
