package com.biology.admin.controller.manage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.biology.domain.manage.emergencyAlarm.EmergencyAlarmApplicationService;
import com.biology.domain.manage.emergencyAlarm.command.AddEmergencyAlarmCommand;
import com.biology.domain.manage.emergencyAlarm.command.UpdateEmergencyAlarmCommand;
import com.biology.domain.manage.emergencyAlarm.dto.EmergencyAlarmDTO;
import com.biology.domain.manage.emergencyAlarm.query.EmergencyAlarmQuery;
import com.biology.domain.manage.environment.dto.EnvironmentDTO;
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

@Tag(name = "报警信息API", description = "报警信息的增删查改")
@RestController
@RequestMapping("/manage/emergencyAlarm")
@Validated
@RequiredArgsConstructor
public class EmergencyAlarmController extends BaseController {
    private final EmergencyAlarmApplicationService emergencyAlarmApplicationService;

    @Operation(summary = "添加报警信息")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddEmergencyAlarmCommand command) {
        emergencyAlarmApplicationService.addEmergencyAlarm(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新报警信息")
    @PutMapping("/{emergencyAlarmId}")
    public ResponseDTO<Void> update(@PathVariable Long emergencyAlarmId,
            @RequestBody UpdateEmergencyAlarmCommand command) {
        command.setEmergencyAlarmId(emergencyAlarmId);
        emergencyAlarmApplicationService.updateEmergencyAlarm(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除报警信息")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> emergencyAlarmIds) {
        emergencyAlarmApplicationService.deleteEmergencyAlarms(emergencyAlarmIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取报警信息详情")
    @GetMapping("/{emergencyAlarmId}")
    public ResponseDTO<EmergencyAlarmDTO> getEmergencyAlarmInfo(
            @PathVariable(value = "emergencyAlarmId", required = false) Long emergencyAlarmId) {
        EmergencyAlarmDTO emergencyAlarmDetailDTO = emergencyAlarmApplicationService
                .getEmergencyAlarm(emergencyAlarmId);
        return ResponseDTO.ok(emergencyAlarmDetailDTO);
    }

    @Operation(summary = "获取报警信息列表")
    @GetMapping
    public ResponseDTO<PageDTO<EmergencyAlarmDTO>> list(EmergencyAlarmQuery query) {
        PageDTO<EmergencyAlarmDTO> list = emergencyAlarmApplicationService.searchEmergencyAlarms(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "用户列表导出")
    @GetMapping("/excel")
    public void exportUserByExcel(HttpServletResponse response, EmergencyAlarmQuery query) throws IOException {
        if (query.getExportType() != null && query.getExportType().equals("excel")) {
            PageDTO<EmergencyAlarmDTO> userList = emergencyAlarmApplicationService.searchEmergencyAlarms(query);
            CustomExcelUtil.writeToResponse(userList.getRows(), EmergencyAlarmDTO.class, response);
        } else if (query.getExportType() != null && query.getExportType().equals("pdf")) {
            PageDTO<EmergencyAlarmDTO> userList = emergencyAlarmApplicationService.searchEmergencyAlarms(query);
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
            document.add(new Paragraph("报警信息")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // 创建表格
            Table table = new Table(
                    new float[] { 100, 100, 100, 100, 100, 50, 100, 100, 100 });
            table.setWidth(500);
            table.addHeaderCell("应急ID");
            table.addHeaderCell("设备数据ID");
            table.addHeaderCell("环境数据ID");
            table.addHeaderCell("应急编号");
            table.addHeaderCell("报警级别");
            table.addHeaderCell("报警类型");
            table.addHeaderCell("报警描述");
            table.addHeaderCell("报警时间");

            // 填充数据
            for (EmergencyAlarmDTO environmentDTO : userList.getRows()) {
                table.addCell(String.valueOf(environmentDTO.getEmergencyAlarmId()));
                table.addCell(String.valueOf(environmentDTO.getEquipmentDataId()));
                table.addCell(String.valueOf(environmentDTO.getDetectionId()));
                table.addCell(environmentDTO.getCode());
                table.addCell(environmentDTO.getLevel());
                table.addCell(environmentDTO.getType());
                table.addCell(environmentDTO.getDescription());
                table.addCell(environmentDTO.getCreateTime().toString());
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
            PageDTO<EmergencyAlarmDTO> userList = emergencyAlarmApplicationService.searchEmergencyAlarms(query);
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
            document.add(new Paragraph("报警信息")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // 创建表格
            Table table = new Table(
                    new float[] { 100, 100, 100, 100, 100, 50, 100, 100, 100 });
            table.setWidth(500);
            table.addHeaderCell("应急ID");
            table.addHeaderCell("设备数据ID");
            table.addHeaderCell("环境数据ID");
            table.addHeaderCell("应急编号");
            table.addHeaderCell("报警级别");
            table.addHeaderCell("报警类型");
            table.addHeaderCell("报警描述");
            table.addHeaderCell("报警时间");

            // 填充数据
            for (EmergencyAlarmDTO environmentDTO : userList.getRows()) {
                table.addCell(String.valueOf(environmentDTO.getEmergencyAlarmId()));
                table.addCell(String.valueOf(environmentDTO.getEquipmentDataId()));
                table.addCell(String.valueOf(environmentDTO.getDetectionId()));
                table.addCell(environmentDTO.getCode());
                table.addCell(environmentDTO.getLevel());
                table.addCell(environmentDTO.getType());
                table.addCell(environmentDTO.getDescription());
                table.addCell(environmentDTO.getCreateTime().toString());
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
        } else {
            throw new IllegalArgumentException("不支持的导出类型: " + query.getExportType());
        }
    }
}
