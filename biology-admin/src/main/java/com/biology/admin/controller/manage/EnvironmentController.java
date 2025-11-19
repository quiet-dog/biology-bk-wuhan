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
import com.biology.domain.manage.environment.EnvironmentApplicationService;
import com.biology.domain.manage.environment.command.AddEnvironmentCommand;
import com.biology.domain.manage.environment.command.UpdateEnvironmentCommand;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
import com.biology.domain.manage.environment.dto.EnvironmentEchartDTO;
import com.biology.domain.manage.environment.dto.EnvironmentListDTO;
import com.biology.domain.manage.environment.dto.EnvironmentStatisticsDTO;
import com.biology.domain.manage.environment.dto.EnvironmentTypesDTO;
import com.biology.domain.manage.environment.query.EnvironmentQuery;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;
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

@Tag(name = "环境档案API", description = "环境档案的增删查改")
@RestController
@RequestMapping("/manage/environment")
@Validated
@RequiredArgsConstructor
public class EnvironmentController extends BaseController {
    private final EnvironmentApplicationService environmentApplicationService;

    @Operation(summary = "添加环境档案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEnvironmentCommand command) {
        environmentApplicationService.addEnvironment(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新环境档案")
    @PutMapping("/{environmentId}")
    public ResponseDTO<Void> update(@PathVariable Long environmentId, @RequestBody UpdateEnvironmentCommand command) {
        command.setEnvironmentId(environmentId);
        environmentApplicationService.updateEnvironment(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除环境档案")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> environmentIds) {
        environmentApplicationService.deleteEnvironments(environmentIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取环境档案详情")
    @GetMapping("/{environmentId}")
    public ResponseDTO<EnvironmentDTO> getEnvironmentInfo(
            @PathVariable(value = "environmentId", required = false) Long environmentId) {
        EnvironmentDTO environmentDetailDTO = environmentApplicationService.getEnvironment(environmentId);
        return ResponseDTO.ok(environmentDetailDTO);
    }

    @Operation(summary = "获取环境档案列表")
    @GetMapping
    public ResponseDTO<PageDTO<EnvironmentListDTO>> list(EnvironmentQuery query) {
        PageDTO<EnvironmentListDTO> list = environmentApplicationService.searchEnvironmentsList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "环境列表导出")
    @AccessLog(title = "环境管理", businessType = BusinessTypeEnum.EXPORT)
    // @PreAuthorize("@permission.has('system:user:export')")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, EnvironmentQuery query) throws IOException {
        PageDTO<EnvironmentDTO> userList = environmentApplicationService.searchEnvironments(query);

        if (query.getExportType() != null && query.getExportType().equals("pdf")) {
            // 生成pdf
            if (userList == null || userList.getRows().isEmpty()) {
                throw new IllegalArgumentException("人员列表不能为空");
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
            document.add(new Paragraph("环境档案信息")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // 创建表格
            Table table = new Table(
                    new float[] { 100, 100, 100, 100, 100, 50, 100, 100, 100, 150, 100, 100, 100, 100, });
            table.setWidth(500);
            table.addHeaderCell("序号");
            table.addHeaderCell("环境描述");
            table.addHeaderCell("位号");
            table.addHeaderCell("区域");
            table.addHeaderCell("类型");
            table.addHeaderCell("范围");
            table.addHeaderCell("监测点位");
            table.addHeaderCell("信号");
            table.addHeaderCell("设备仪表供应商");
            table.addHeaderCell("设备仪表型号");
            table.addHeaderCell("数值");
            table.addHeaderCell("单位");
            table.addHeaderCell("单位名称");
            table.addHeaderCell("plc地址");

            // 填充数据
            for (EnvironmentDTO environmentDTO : userList.getRows()) {
                table.addCell(String.valueOf(environmentDTO.getEnvironmentId()));
                table.addCell(environmentDTO.getDescription() != null ? environmentDTO.getDescription() : "");
                table.addCell(environmentDTO.getTag() != null ? environmentDTO.getTag() : "");
                table.addCell(environmentDTO.getEArea() != null ? environmentDTO.getEArea() : "");
                table.addCell(environmentDTO.getType() != null ? environmentDTO.getType() : "");
                table.addCell(environmentDTO.getScope() != null ? environmentDTO.getScope() : "");
                table.addCell(environmentDTO.getMonitoringPoint() != null ? environmentDTO.getMonitoringPoint() : "");
                table.addCell(environmentDTO.getESignal() != null ? environmentDTO.getESignal() : "");
                table.addCell(environmentDTO.getSupplier() != null ? environmentDTO.getSupplier() : "");
                table.addCell(environmentDTO.getModel() != null ? environmentDTO.getModel() : "");
                table.addCell(
                        String.valueOf(environmentDTO.getValue()) != null ? String.valueOf(environmentDTO.getValue())
                                : "");
                table.addCell(environmentDTO.getUnit() != null ? environmentDTO.getUnit() : "");
                table.addCell(environmentDTO.getUnitName() != null ? environmentDTO.getUnitName() : "");
                table.addCell(environmentDTO.getPlcAddress() != null ? environmentDTO.getPlcAddress() : "");
            }

            document.add(table);
            document.close();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=environment.pdf");
            response.setContentLength(baos.size());
            try {
                baos.writeTo(response.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException("无法写入PDF到响应: " + e.getMessage(), e);
            } finally {
                baos.close();
            }
        } else if (query.getExportType() != null && query.getExportType().equals("word")) {
            if (userList.getRows() == null || userList.getRows().isEmpty()) {
                throw new IllegalArgumentException("人员列表不能为空");
            }

            // 创建 Word 文档
            XWPFDocument document = new XWPFDocument();

            // 添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("环境档案信息");
            titleRun.setFontSize(18);
            titleRun.setBold(true);
            titleRun.setFontFamily("SimSong"); // 使用宋体支持中文
            titleRun.addBreak();

            // 创建表格
            XWPFTable table = document.createTable(1, 14); // 1行（表头）, 11列
            table.setWidth(5000); // 设置表格宽度（单位：1/50 英寸）

            // 设置表头
            XWPFTableRow headerRow = table.getRow(0);

            String[] headers = { "序号", "环境描述", "位号", "区域", "类型", "范围", "监测点位", "信号",
                    "设备仪表供应商", "设备仪表型号", "数值", "单位", "单位名称", "plc地址" };
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
            for (EnvironmentDTO environmentDTO : userList.getRows()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(String.valueOf(environmentDTO.getEnvironmentId()));
                row.getCell(1).setText(environmentDTO.getDescription());
                row.getCell(2).setText(environmentDTO.getTag());
                row.getCell(3).setText(environmentDTO.getEArea());
                row.getCell(4).setText(environmentDTO.getType());
                row.getCell(5).setText(environmentDTO.getScope());
                row.getCell(6).setText(environmentDTO.getMonitoringPoint());
                row.getCell(7).setText(environmentDTO.getESignal());
                row.getCell(8).setText(environmentDTO.getSupplier());
                row.getCell(9).setText(environmentDTO.getModel());
                row.getCell(10).setText(String.valueOf(environmentDTO.getValue()));
                row.getCell(11).setText(environmentDTO.getUnit());
                row.getCell(12).setText(environmentDTO.getUnitName());
                row.getCell(13).setText(environmentDTO.getPlcAddress());

                // 设置单元格字体
                for (int i = 0; i < 14; i++) {
                    XWPFParagraph cellParagraph = row.getCell(i).getParagraphs().get(0);
                    cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun cellRun = cellParagraph.createRun();
                    cellRun.setFontFamily("SimSong");
                }
            }

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=环境档案.docx");

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
            CustomExcelUtil.writeToResponse(userList.getRows(), EnvironmentDTO.class, response);
        }

    }

    @Operation(summary = "获取环境档案统计信息")
    @GetMapping("/data/{environmentId}")
    public ResponseDTO<List<EnvironmentEchartDTO>> getDayStatistics(
            @PathVariable(value = "environmentId", required = false) Long environmentId) {
        return ResponseDTO.ok(environmentApplicationService.getDayStatistics(environmentId));
    }

    @Operation(summary = "获取环境档案所有的分类")
    @GetMapping("/allGroup")
    public ResponseDTO<EnvironmentTypesDTO> getAllGroup() {
        return ResponseDTO.ok(environmentApplicationService.getAllGroup());
    }

    @Operation(summary = "获取环境档案所有的分类")
    @GetMapping("/areas")
    public ResponseDTO<List<String>> getAllAreas() {
        return ResponseDTO.ok(environmentApplicationService.getAllAreas());
    }

}
