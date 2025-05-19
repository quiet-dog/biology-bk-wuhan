package com.biology.admin.controller.manage;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import org.springframework.beans.BeanUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.admin.customize.aop.accessLog.AccessLog;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.enums.common.BusinessTypeEnum;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.alarm.command.AddAlarmCommand;
import com.biology.domain.manage.personnel.PersonnelApplicationService;
import com.biology.domain.manage.personnel.command.AddPersonnelCommand;
import com.biology.domain.manage.personnel.command.UpdatePersonnelCommand;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;
import com.biology.domain.manage.personnel.model.PersonnelFactory;
import com.biology.domain.manage.personnel.model.PersonnelModel;
import com.biology.domain.manage.personnel.query.PersonnelQuery;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "人员档案", description = "人员档案的增删查改")
@RestController
@RequestMapping("/manage/personnel")
@Validated
@RequiredArgsConstructor
public class PersonnelController extends BaseController {

    private final PersonnelApplicationService personnelApplicationService;

    private final PersonnelService personnelService;

    private final PersonnelFactory personnelFactory;

    @Operation(summary = "添加人员档案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddPersonnelCommand command) {
        personnelApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新人员档案")
    @PutMapping("/{personnelId}")
    public ResponseDTO<Void> update(@PathVariable("personnelId") Long personnelId,
            @Validated @RequestBody UpdatePersonnelCommand command) {
        personnelApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除人员档案")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> personnelIds) {
        personnelApplicationService.deletePersonnels(personnelIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取人员档案列表")
    @GetMapping
    public ResponseDTO<PageDTO<PersonnelDTO>> list(PersonnelQuery query) {
        PageDTO<PersonnelDTO> list = personnelApplicationService.getPersonnels(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取知识库信息")
    @GetMapping("/{personnelId}")
    public ResponseDTO<PersonnelDTO> info(
            @PathVariable(value = "personnelId", required = false) Long personnelId) {
        PersonnelDTO personnelDTO = personnelApplicationService.getPersonnel(personnelId);
        return ResponseDTO.ok(personnelDTO);
    }

    @Operation(summary = "人员档案列表导出")
    @GetMapping("/excel")
    public void exportPersonnelByExcel(HttpServletResponse response, PersonnelQuery query) throws IOException {
        PageDTO<PersonnelDTO> personnelList = personnelApplicationService.getPersonnels(query);
        if (query.getExportType() != null && query.getExportType().equals("pdf")) {
            // 生成pdf
            if (personnelList == null || personnelList.getRows() == null || personnelList.getRows().isEmpty()) {
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
            document.add(new Paragraph("人员档案信息")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // 创建表格
            Table table = new Table(new float[] { 100, 100, 50, 100, 100, 100, 150, 100, 100, 100, 100 });
            table.setWidth(500);
            table.addHeaderCell("员工编号");
            table.addHeaderCell("员工姓名");
            table.addHeaderCell("性别");
            table.addHeaderCell("部门");
            table.addHeaderCell("岗位");
            table.addHeaderCell("邮箱");
            table.addHeaderCell("身份证号");
            table.addHeaderCell("学历");
            table.addHeaderCell("联系方式");
            table.addHeaderCell("入职时间");
            table.addHeaderCell("离职时间");

            // 填充数据
            for (PersonnelDTO personnel : personnelList.getRows()) {
                table.addCell(personnel.getCode() != null ? personnel.getCode() : "");
                table.addCell(personnel.getName() != null ? personnel.getName() : "");
                table.addCell(personnel.getSex() != null ? personnel.getSex() : "");
                table.addCell(personnel.getDepartment() != null ? personnel.getDepartment() : "");
                table.addCell(personnel.getPost() != null ? personnel.getPost() : "");
                table.addCell(personnel.getEmail() != null ? personnel.getEmail() : "");
                table.addCell(personnel.getCard() != null ? personnel.getCard() : "");
                table.addCell(personnel.getEducation() != null ? personnel.getEducation() : "");
                table.addCell(personnel.getContact() != null ? personnel.getContact() : "");
                table.addCell(personnel.getEntryTime() != null ? personnel.getEntryTime().toString() : "");
                table.addCell(personnel.getLeaveTime() != null ? personnel.getLeaveTime().toString() : "");
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
            if (personnelList == null || personnelList.getRows() == null || personnelList.getRows().isEmpty()) {
                throw new IllegalArgumentException("人员列表不能为空");
            }

            // 创建 Word 文档
            XWPFDocument document = new XWPFDocument();

            // 添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("人员档案信息");
            titleRun.setFontSize(18);
            titleRun.setBold(true);
            titleRun.setFontFamily("SimSong"); // 使用宋体支持中文
            titleRun.addBreak();

            // 创建表格
            XWPFTable table = document.createTable(1, 11); // 1行（表头）, 11列
            table.setWidth(5000); // 设置表格宽度（单位：1/50 英寸）

            // 设置表头
            XWPFTableRow headerRow = table.getRow(0);
            String[] headers = { "员工编号", "员工姓名", "性别", "部门", "岗位", "邮箱", "身份证号", "学历", "联系方式", "入职时间", "离职时间" };
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
            for (PersonnelDTO personnel : personnelList.getRows()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(personnel.getCode() != null ? personnel.getCode() : "");
                row.getCell(1).setText(personnel.getName() != null ? personnel.getName() : "");
                row.getCell(2).setText(personnel.getSex() != null ? personnel.getSex() : "");
                row.getCell(3).setText(personnel.getDepartment() != null ? personnel.getDepartment() : "");
                row.getCell(4).setText(personnel.getPost() != null ? personnel.getPost() : "");
                row.getCell(5).setText(personnel.getEmail() != null ? personnel.getEmail() : "");
                row.getCell(6).setText(personnel.getCard() != null ? personnel.getCard() : "");
                row.getCell(7).setText(personnel.getEducation() != null ? personnel.getEducation() : "");
                row.getCell(8).setText(personnel.getContact() != null ? personnel.getContact() : "");
                row.getCell(9).setText(personnel.getEntryTime() != null ? personnel.getEntryTime().toString() : "");
                row.getCell(10).setText(personnel.getLeaveTime() != null ? personnel.getLeaveTime().toString() : "");

                // 设置单元格字体
                for (int i = 0; i < 11; i++) {
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
            CustomExcelUtil.writeToResponse(personnelList.getRows(), PersonnelDTO.class, response);
        }
    }

    // 人员档案导入
    @Operation(summary = "人员档案导入")
    @PostMapping("/excel")
    public ResponseDTO<Void> importPersonnel(MultipartFile file) {
        List<AddPersonnelCommand> commands = CustomExcelUtil.readFromRequest(AddPersonnelCommand.class, file);
        for (AddPersonnelCommand command : commands) {
            QueryWrapper<PersonnelEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", command.getCode());
            PersonnelEntity pEntity = personnelService.getOne(queryWrapper);
            if (pEntity != null) {
                UpdatePersonnelCommand updatePersonnelCommand = new UpdatePersonnelCommand();
                BeanUtils.copyProperties(command, updatePersonnelCommand);
                PersonnelModel pModel = personnelFactory.loadById(pEntity.getPersonnelId());
                pModel.loadUpdatePersonnelCommand(updatePersonnelCommand);
                pModel.updateById();
            } else {
                personnelApplicationService.create(command);
            }

        }
        return ResponseDTO.ok();
    }

    @Operation(summary = "人员档案导入模板下载")
    @GetMapping("/excelTemplate")
    public void downloadExcelTemplate(HttpServletResponse response) {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new AddPersonnelCommand()), AddPersonnelCommand.class,
                response);
    }
}
