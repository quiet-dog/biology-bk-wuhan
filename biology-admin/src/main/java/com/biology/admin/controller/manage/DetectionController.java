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
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.detection.DetectionApplicationService;
import com.biology.domain.manage.detection.command.AddDetectionCommand;
import com.biology.domain.manage.detection.dto.DareaResultDTO;
import com.biology.domain.manage.detection.dto.DetectionAreaTypeEchartDTO;
import com.biology.domain.manage.detection.dto.DetectionCountEchartTypeDTO;
import com.biology.domain.manage.detection.dto.DetectionDTO;
import com.biology.domain.manage.detection.dto.NengHaoDTO;
import com.biology.domain.manage.detection.dto.PowerDTO;
import com.biology.domain.manage.detection.dto.PowerEchartDTO;
import com.biology.domain.manage.detection.dto.StatisticsDetailDTO;
import com.biology.domain.manage.detection.query.AllAreaDTO;
import com.biology.domain.manage.detection.query.DetectionQuery;
import com.biology.domain.manage.detection.query.DetectionStockQuery;
import com.biology.domain.manage.detection.query.HistoryQuery;
import com.biology.domain.manage.detection.query.NengHaoEchartQuery;
import com.biology.domain.manage.detection.query.PowerQuery;
import com.biology.domain.manage.detection.query.SearchNengHaoQuery;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.event.dto.EventDTO;
import com.biology.domain.manage.event.dto.EventDeviceNameDTO;
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
import okhttp3.Response;
import com.biology.common.utils.poi.CustomExcelUtil;

@Tag(name = "环境监测API", description = "环境监测API的增删查改")
@RestController
@RequestMapping("/manage/detection")
@Validated
@RequiredArgsConstructor
public class DetectionController extends BaseController {
    private final DetectionApplicationService detectionApplicationService;

    @Operation(summary = "添加环境数据")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddDetectionCommand command) {
        detectionApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取环境数据列表")
    @GetMapping
    public ResponseDTO<PageDTO<DetectionDTO>> list(DetectionQuery query) {
        return ResponseDTO.ok(detectionApplicationService.list(query));
    }

    @Operation(summary = "获取环境监测信息统计")
    @GetMapping("/data/{detectionId}")
    public ResponseDTO<StatisticsDetailDTO> getStatistics(
            @PathVariable(value = "detectionId", required = false) Long detectionId) {
        return ResponseDTO.ok(detectionApplicationService.getStatistics(detectionId));
    }

    @Operation(summary = "获取环境监测功率统计")
    @GetMapping("/powerStatic")
    public ResponseDTO<PowerEchartDTO> getPowerStatic(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getPowerStatic(query));
    }

    @Operation(summary = "获取环境监测功率统计")
    @GetMapping("/historyStatic")
    public ResponseDTO<PowerEchartDTO> getHistoryData(HistoryQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getHistoryData(query));
    }

    @Operation(summary = "数据大屏获取不同类型环境档案的当天功耗趋势")
    @GetMapping("/powerByType")
    public ResponseDTO<DetectionCountEchartTypeDTO> getHistoryPowersByType(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getHistoryPowersByType(query));
    }

    @Operation(summary = "数据大屏获取水电不同类型环境档案的周月年总功耗")
    @GetMapping("/powerByTypeTotal")
    public ResponseDTO<DetectionCountEchartTypeDTO> getHistoryPowersByTypeTotal(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getHistoryPowersByTypeTotal(query));
    }

    @Operation(summary = "数据大屏获取水电不同类型环境档案的区域的周月年")
    @GetMapping("/powerByAreaTotal")
    public ResponseDTO<DetectionCountEchartTypeDTO> getHistoryAreaByTypeTotal(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getHistoryAreaByTypeTotal(query));
    }

    @Operation(summary = "首页获取环境监测指标")
    @GetMapping("/unitNameAndArea")
    public ResponseDTO<DetectionCountEchartTypeDTO> getAareUnitNameHistory(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getAareUnitNameHistory(query));
    }

    @Operation(summary = "数据大屏获取环境监测指标")
    @GetMapping("/environmentDay")
    public DetectionCountEchartTypeDTO getHistoryDayByEnvironmentId(PowerQuery query) {
        return detectionApplicationService.getHistoryDayByEnvironmentId(query);
    }

    @Operation(summary = "数据大屏获取环境监测指标")
    @GetMapping("/getZuiXinShuJu")
    public ResponseDTO<DetectionCountEchartTypeDTO> getZuiXinShuJu(PowerQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getZuiXinShuJu(query));
    }

    @Operation(summary = "数据大屏获取环境监测指标")
    @GetMapping("/getNenghao")
    public ResponseDTO<PageDTO<NengHaoDTO>> getNengHaoList(SearchNengHaoQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getNengHaoList(query));
    }

    @Operation(summary = "能耗导出")
    @GetMapping("/exportNengHao")
    public void exportNengHao(HttpServletResponse response, SearchNengHaoQuery query) throws IOException {
        // PageDTO<NengHaoDTO> userList =
        // detectionApplicationService.getNengHaoList(query);
        // CustomExcelUtil.writeToResponse(userList.getRows(), NengHaoDTO.class,
        // response);
        PageDTO<NengHaoDTO> userList = detectionApplicationService.getNengHaoList(query);
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
            document.add(new Paragraph("能耗信息")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // 创建表格
            Table table = new Table(new float[] { 100, 100, 50, 100, });
            table.setWidth(500);
            table.addHeaderCell("当天能耗量");
            table.addHeaderCell("本月能耗量");
            table.addHeaderCell("创建时间");
            table.addHeaderCell("类型");
            table.addHeaderCell("描述");

            // 填充数据
            for (NengHaoDTO eventDTO : userList.getRows()) {
                table.addCell(eventDTO.getValue() != 0 ? String.valueOf(eventDTO.getValue()) : "");
                table.addCell(eventDTO.getTotalValue() != 0 ? String.valueOf(eventDTO.getTotalValue()) : "");
                table.addCell(eventDTO.getCreateTime() != null ? eventDTO.getCreateTime().toString() : "");
                table.addCell(eventDTO.getType() != null ? eventDTO.getType() : "");
                table.addCell(eventDTO.getEnvironment() != null && eventDTO.getEnvironment().getDescription() != null
                        ? eventDTO.getEnvironment().getDescription()
                        : "");

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
            String[] headers = { "当天能耗量", "本月能耗量", "创建时间", "类型", "描述" };
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
            for (NengHaoDTO eDto : userList.getRows()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(eDto.getValue() != 0 ? String.valueOf(eDto.getValue()) : "");
                row.getCell(1).setText(eDto.getTotalValue() != 0 ? String.valueOf(eDto.getTotalValue()) : "");
                row.getCell(2).setText(eDto.getCreateTime() != null ? eDto.getCreateTime().toString() : "");
                row.getCell(3).setText(eDto.getType() != null ? eDto.getType() : "");
                row.getCell(4).setText(eDto.getEnvironment() != null && eDto.getEnvironment().getDescription() != null
                        ? eDto.getEnvironment().getDescription()
                        : "");

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
            CustomExcelUtil.writeToResponse(userList.getRows(), NengHaoDTO.class, response);
        }

    }

    @Operation(summary = "统计能耗")
    @GetMapping("/getTongJiNenghao")
    public ResponseDTO<DetectionCountEchartTypeDTO> getTongJiNenghao(NengHaoEchartQuery query) {
        return ResponseDTO.ok(detectionApplicationService.getTongJiNenghao(query));
    }

    @Operation(summary = "统计能耗")
    @PostMapping("/getBuTong")
    public ResponseDTO<DareaResultDTO> getTemperatureDataByAreaAndTimeSlot(@RequestBody AllAreaDTO query) {
        return ResponseDTO.ok(detectionApplicationService.getTemperatureDataByAreaAndTimeSlot(query.getUnitName(),
                query.getBeginTime(), query.getEndTime()));
    }
}
