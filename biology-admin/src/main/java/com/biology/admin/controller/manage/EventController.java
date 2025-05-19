package com.biology.admin.controller.manage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.biology.admin.customize.aop.accessLog.AccessLog;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.enums.common.BusinessTypeEnum;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.detection.DetectionApplicationService;
import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
import com.biology.domain.manage.environment.query.EnvironmentQuery;
import com.biology.domain.manage.event.EventApplicationService;
import com.biology.domain.manage.event.command.AddEventCommand;
import com.biology.domain.manage.event.command.UpdateEventCommand;
import com.biology.domain.manage.event.dto.AllEventEchartDTO;
import com.biology.domain.manage.event.dto.AreaStatisticsDTO;
import com.biology.domain.manage.event.dto.EnvironmentStockEchart;
import com.biology.domain.manage.event.dto.EventDTO;
import com.biology.domain.manage.event.dto.EventDeviceNameDTO;
import com.biology.domain.manage.event.dto.EventEchartDTO;
import com.biology.domain.manage.event.dto.EventEchartOneDTO;
import com.biology.domain.manage.event.dto.EventTotalDTO;
import com.biology.domain.manage.event.dto.HistoryEventEchart;
import com.biology.domain.manage.event.dto.StatisticsDTO;
import com.biology.domain.manage.event.dto.TypeDTO;
import com.biology.domain.manage.event.dto.WeekStatisticsDTO;
import com.biology.domain.manage.event.query.AreaStatisticsQuery;
import com.biology.domain.manage.event.query.EnvironmentEventQuery;
import com.biology.domain.manage.event.query.EventSearch;
import com.biology.domain.manage.event.query.StatisticsQuery;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;
import com.biology.domain.manage.threshold.ThresholdApplicationService;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueService;
import com.biology.domain.manage.websocket.dto.DeviceDTO;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "报警事件API", description = "报警事件的增删查改")
@RestController
@RequestMapping("/manage/event")
@Validated
@RequiredArgsConstructor
public class EventController extends BaseController {

    private final EventApplicationService eventApplicationService;

    private final ThresholdValueService thresholdValueService;

    private final ThresholdApplicationService thresholdApplicationService;

    private final DetectionApplicationService detectionApplicationService;

    @Operation(summary = "添加报警事件")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEventCommand command) {
        eventApplicationService.createEvent(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新报警事件")
    @PutMapping("/{eventId}")
    public ResponseDTO<Void> update(@PathVariable Long eventId, @RequestBody UpdateEventCommand command) {
        command.setEventId(eventId);
        eventApplicationService.updateEvent(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除报警事件")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> eventIds) {
        eventApplicationService.deleteEmergencies(eventIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取报警事件详情")
    @GetMapping("/{eventId}")
    public ResponseDTO<EventDTO> getEventInfo(
            @PathVariable(value = "eventId", required = false) Long eventId) {
        EventDTO eventDetailDTO = eventApplicationService.getEventInfo(eventId);
        return ResponseDTO.ok(eventDetailDTO);
    }

    @Operation(summary = "获取报警事件列表")
    @GetMapping
    public ResponseDTO<PageDTO<EventDTO>> list(EventSearch query) {
        PageDTO<EventDTO> list = eventApplicationService.getEventList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "报警事件列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, EventSearch query) {
        PageDTO<EventDTO> userList = eventApplicationService.getEventList(query);
        CustomExcelUtil.writeToResponse(userList.getRows(), EventDTO.class, response);
    }

    @Operation(summary = "报警事件列表导出")
    @GetMapping("/excelDeviceName")
    public void exportDeviceNameByExcel(HttpServletResponse response, EventSearch query) throws IOException {
        PageDTO<EventDeviceNameDTO> userList = eventApplicationService.getEventDeviceNameList(query);
        if (query.getExportType() != null && query.getExportType().equals("pdf")) {
            // 生成pdf
            if (userList.getRows() == null || userList.getRows().isEmpty()) {
                throw new IllegalArgumentException("事件列表不能为空");
            }

            // 设置中文字体
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // 设置中文字体
            PdfFont font;
            try {
                font = PdfFontFactory.createFont("STSong-Light", "UniGB-UCS2-H",
                        PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
            } catch (IOException e) {
                document.close();
                throw new IOException("无法加载中文字体: " + e.getMessage(), e);
            }
            document.setFont(font);

            // 添加标题
            document.add(new Paragraph("事件信息")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // 创建表格
            Table table = new Table(new float[] { 100, 100, 50, 100, 100, 100, 150, 100, 100, 100, 100 });
            table.setWidth(500);
            table.addHeaderCell("报警编号");
            table.addHeaderCell("报警类型");
            table.addHeaderCell("报警级别");
            table.addHeaderCell("报警描述");
            table.addHeaderCell("报警时间");

            // 填充数据
            for (EventDTO eventDTO : userList.getRows()) {
                table.addCell(eventDTO.getEventId() != null ? eventDTO.getEventId().toString() : "");
                table.addCell(eventDTO.getType() != null ? eventDTO.getType() : "");
                table.addCell(eventDTO.getLevel() != null ? eventDTO.getLevel() : "");
                table.addCell(eventDTO.getDescription() != null ? eventDTO.getDescription() : "");
                table.addCell(eventDTO.getCreateTime() != null ? eventDTO.getCreateTime().toString() : "");
            }

            document.add(table);
            document.close();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=personnel.pdf");
            response.setContentLength(baos.size());
            try {
                baos.writeTo(response.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException("无法写入PDF到响应: " + e.getMessage(), e);
            } finally {
                baos.close();
            }
        } else if (query.getExportType() != null && query.getExportType().equals("word")) {
            // 生成 Word 文档
            if (userList.getRows() == null || userList.getRows().isEmpty()) {
                throw new IllegalArgumentException("事件列表不能为空");
            }

            // 创建 Word 文档
            XWPFDocument document = new XWPFDocument();

            // 添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("事件信息");
            titleRun.setFontSize(18);
            titleRun.setBold(true);
            titleRun.setFontFamily("SimSong"); // 使用宋体支持中文
            titleRun.addBreak();

            // 创建表格
            XWPFTable table = document.createTable(1, 11); // 1行（表头）, 11列
            table.setWidth(5000); // 设置表格宽度（单位：1/50 英寸）

            // 设置表头
            XWPFTableRow headerRow = table.getRow(0);
            String[] headers = { "报警编号", "报警类型", "报警级别", "报警描述", "报警时间" };
            for (int i = 0; i < headers.length; i++) {
                XWPFTableCell cell = headerRow.getCell(i);
                cell.setText(headers[i]);
                XWPFParagraph cellParagraph = cell.getParagraphs().get(0);
                cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun cellRun = cellParagraph.getRuns().get(0);
                cellRun.setFontFamily("SimSong");
                cellRun.setBold(true);
            }

            // 填充数据
            for (EventDTO eDto : userList.getRows()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(eDto.getEventId() != null ? eDto.getEventId().toString() : "");
                row.getCell(1).setText(eDto.getType() != null ? eDto.getType() : "");
                row.getCell(2).setText(eDto.getLevel() != null ? eDto.getLevel() : "");
                row.getCell(3).setText(eDto.getDescription() != null ? eDto.getDescription() : "");
                row.getCell(4).setText(eDto.getCreateTime() != null ? eDto.getCreateTime().toString() : "");

                // 设置单元格字体
                for (int i = 0; i < 5; i++) {
                    XWPFParagraph cellParagraph = row.getCell(i).getParagraphs().get(0);
                    cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun cellRun = cellParagraph.createRun();
                    cellRun.setFontFamily("SimSong");
                }
            }

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=personnel.docx");

            // 写入文档到响应流
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                document.write(baos);
                response.setContentLength(baos.size());
                baos.writeTo(response.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException("无法写入Word到响应: " + e.getMessage(), e);
            } finally {
                document.close();
            }
        } else {
            CustomExcelUtil.writeToResponse(userList.getRows(), EventDeviceNameDTO.class, response);
        }
    }

    @Operation(summary = "报警事件所有类型")
    @GetMapping("/types")
    public ResponseDTO<List<String>> getTypeList() {
        return eventApplicationService.getTypeList();
    }

    @Operation(summary = "测试环境报警")
    @PostMapping("/set")
    public ResponseDTO<Object> setEvent(@RequestBody DeviceDTO deviceDTO) {
        AddEventCommand command = new AddEventCommand();
        if (deviceDTO.getDeviceType().equals("环境档案")) {

            AlarmlevelDetailEntity alarmlevelDetailEntity = eventApplicationService
                    .checkEnvironment(deviceDTO.getEnvironmentAlarmInfo());
            if (alarmlevelDetailEntity == null) {
                return ResponseDTO.ok("无报警");
            }
            command.setEnvironmentValue(deviceDTO.getEnvironmentAlarmInfo().getValue());
            command.setType("环境报警");
            command.setLevel(alarmlevelDetailEntity.getLevel());
            command.setEnvironmentId(deviceDTO.getEnvironmentAlarmInfo().getEnvironmentId());
            command.setEnvironmentUnit(deviceDTO.getEnvironmentAlarmInfo().getUnit());
            command.setAlarmlevelId(alarmlevelDetailEntity.getAlarmlevelId());
            eventApplicationService.createEvent(command);
        }

        if (deviceDTO.getDeviceType().equals("设备档案")) {
            ThresholdValueEntity thresholdValueEntity = thresholdApplicationService.checkEquipmentInfo(
                    deviceDTO.getEquipmentInfo());
            if (thresholdValueEntity == null) {
                return ResponseDTO.ok("无报警");
            }
            command.setEquipmentId(deviceDTO.getEquipmentInfo().getEquipmentId());
            command.setType("设备报警");
            command.setLevel(thresholdValueEntity.getLevel());
            command.setThresholdId(thresholdValueEntity.getThresholdId());
            command.setEquipmentValue(deviceDTO.getEquipmentInfo().getValue());
            eventApplicationService.createEvent(command);
        }

        return ResponseDTO.ok();
    }

    @Operation(summary = "报警统计")
    @GetMapping("/getWeekStatistics")
    public ResponseDTO<List<EventEchartDTO>> getWeekStatistics(StatisticsQuery query) {
        return ResponseDTO.ok(eventApplicationService.getWeekStatistics(query.getDayType()));
    }

    @Operation(summary = "报警区域统计")
    @GetMapping("/getAreaStatistics")
    public ResponseDTO<List<AreaStatisticsDTO>> getAreaStatistics(AreaStatisticsQuery query) {
        return ResponseDTO.ok(eventApplicationService.getAreaStatistics(query));
    }

    @Operation(summary = "报警数量全部统计")
    @GetMapping("/eventTotal")
    public ResponseDTO<EventTotalDTO> getEventTotal() {
        return ResponseDTO.ok(eventApplicationService.getEventTotal());
    }

    @Operation(summary = "历史报警数量全部统计")
    @GetMapping("/eventHistory")
    public ResponseDTO<HistoryEventEchart> getHistoryEventAll(StatisticsQuery query) {
        return ResponseDTO.ok(eventApplicationService.getHistoryEventAll(query));
    }

    @Operation(summary = "数据大屏获取环境档案的历史报警数量")
    @GetMapping("/envrionment")
    public ResponseDTO<EnvironmentStockEchart> getEnvrionmentEventAllWeek(EnvironmentEventQuery query) {
        return ResponseDTO.ok(eventApplicationService.getEnvrionmentEventAllWeek(query));
    }

    @Operation(summary = "数据大屏获取环境档案区域的历史报警数量")
    @GetMapping("/envrionmentArea")
    public ResponseDTO<EnvironmentStockEchart> getEnvrionmentAreaEventAll(EnvironmentEventQuery query) {
        return ResponseDTO.ok(eventApplicationService.getEnvrionmentAreaEventAll(query));
    }

    @Operation(summary = "数据大屏根据获取环境档案id的历史报警数量")
    @GetMapping("/envrionmentById")
    public ResponseDTO<EnvironmentStockEchart> getEnvironmentEventById(EnvironmentEventQuery query) {
        return ResponseDTO.ok(eventApplicationService.getEnvironmentEventById(query));
    }

    @Operation(summary = "数据大屏根据获取不同区域的历史报警数量")
    @GetMapping("/getAreaStatisticsTotal")
    public ResponseDTO<EventEchartOneDTO> getAreaStatisticsTotal(StatisticsQuery query) {
        return ResponseDTO.ok(eventApplicationService.getAreaStatisticsTotal(query));
    }

    @Operation(summary = "数据大屏根据获取不同类型的全部报警数量")
    @GetMapping("/getAllEventEchart")
    public ResponseDTO<List<AllEventEchartDTO>> getAllEventEchart() {
        return ResponseDTO.ok(eventApplicationService.getAllEventEchart());
    }

    @Operation(summary = "数据大屏根据获取不同类型的全部报警数量")
    @GetMapping("/getAllEquipmentAreaEchart")
    public ResponseDTO<List<AllEventEchartDTO>> getAllEquipmentAreaEchart() {
        return ResponseDTO.ok(eventApplicationService.getAllEquipmentAreaEchart());
    }

    @Operation(summary = "数据大屏根据获取不同类型的全部报警数量")
    @GetMapping("/getAllEnvironmentAreaEchart")
    public ResponseDTO<List<AllEventEchartDTO>> getAllEnvironmentAreaEchart() {
        return ResponseDTO.ok(eventApplicationService.getAllEnvironmentAreaEchart());
    }

}
