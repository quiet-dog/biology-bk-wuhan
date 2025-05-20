package com.biology.admin.controller.manage;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.craftprocess.CraftProcessApplicationService;
import com.biology.domain.manage.craftprocess.command.AddCraftProcessCommand;
import com.biology.domain.manage.craftprocess.command.UpdateCraftProcessCommand;
import com.biology.domain.manage.craftprocess.dto.CraftProcessDTO;
import com.biology.domain.manage.craftprocess.query.SearchCraftProcessQuery;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@Tag(name = "工艺流程图API", description = "工艺流程图的增删查改")
@RestController
@RequestMapping("/manage/craft-process")
@Validated
@RequiredArgsConstructor
public class CraftProcessController extends BaseController {

    private final CraftProcessApplicationService craftProcessApplicationService;

    @Operation(summary = "添加工艺流程图")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddCraftProcessCommand command) {
        craftProcessApplicationService.addCraftProcess(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新工艺流程图")
    @PutMapping("/{craftProcessId}")
    public ResponseDTO<Void> update(@PathVariable Long craftProcessId, @RequestBody UpdateCraftProcessCommand command) {
        command.setCraftProcessId(craftProcessId);
        craftProcessApplicationService.updateCraftProcess(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除工艺流程图")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> craftProcessIds) {
        craftProcessApplicationService.deleteCraftProcesses(craftProcessIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取工艺流程图信息")
    @GetMapping("/{craftProcessId}")
    public ResponseDTO<CraftProcessDTO> info(@PathVariable Long craftProcessId) {
        CraftProcessDTO craftProcessDTO = craftProcessApplicationService.getCraftProcessInfo(craftProcessId);
        return ResponseDTO.ok(craftProcessDTO);
    }

    @Operation(summary = "获取工艺流程图列表")
    @GetMapping
    public ResponseDTO<PageDTO<CraftProcessDTO>> list(SearchCraftProcessQuery query) {
        PageDTO<CraftProcessDTO> list = craftProcessApplicationService.getCraftProcessList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "工艺流程图导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response, SearchCraftProcessQuery query)
            throws IOException {
        PageDTO<CraftProcessDTO> list = craftProcessApplicationService.getCraftProcessList(query);

        if (query.getExportType() != null && query.getExportType().equals("pdf")) {
            // 生成pdf
            if (list.getRows() == null || list.getRows().isEmpty()) {
                throw new IllegalArgumentException("工艺档案列表不能为空");
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
            document.add(new Paragraph("工艺档案信息")
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            // 创建表格
            Table table = new Table(new float[] { 100, 100, 100, 100, 100, 100, 100 });
            table.setWidth(500);
            table.addHeaderCell("序号");
            // table.addHeaderCell("工艺编号");
            table.addHeaderCell("节点顺序");
            table.addHeaderCell("人员要素");
            table.addHeaderCell("设备要素");
            table.addHeaderCell("原料要素");
            table.addHeaderCell("环境要素");
            table.addHeaderCell("所属工艺档案名称");

            // 填充数据
            for (CraftProcessDTO cDto : list.getRows()) {
                table.addCell(cDto.getCraftProcessId() != null ? cDto.getCraftProcessId().toString() : "");
                table.addCell(cDto.getNodeOrder() != null ? cDto.getNodeOrder().toString() : "");
                table.addCell(cDto.getPersonnelFactors() != null ? cDto.getPersonnelFactors() : "");
                table.addCell(cDto.getEquipmentFactors() != null ? cDto.getEquipmentFactors() : "");
                table.addCell(cDto.getMaterialFactors() != null ? cDto.getMaterialFactors() : "");
                table.addCell(cDto.getEnvironmentFactors() != null ? cDto.getEnvironmentFactors() : "");
                table.addCell(cDto.getCraftArchiveName() != null ? cDto.getCraftArchiveName() : "");

            }

            document.add(table);
            document.close();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=craft-process.pdf");
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
            if (list.getRows() == null || list.getRows().isEmpty()) {
                throw new IllegalArgumentException("工艺列表不能为空");
            }

            // 创建 Word 文档
            XWPFDocument document = new XWPFDocument();

            // 添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("工艺档案信息");
            titleRun.setFontSize(18);
            titleRun.setBold(true);
            titleRun.setFontFamily("SimSong"); // 使用宋体支持中文
            titleRun.addBreak();

            // 创建表格
            XWPFTable table = document.createTable(1, 7); // 1行（表头）, 11列
            table.setWidth(5000); // 设置表格宽度（单位：1/50 英寸）

            // 设置表头
            XWPFTableRow headerRow = table.getRow(0);
            String[] headers = { "序号", "工艺编号", "节点顺序", "人员要素", "设备要素", "原料要素", "环境要素" };
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
            for (CraftProcessDTO cDto : list.getRows()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(cDto.getCraftProcessId() != null ? cDto.getCraftProcessId().toString() : "");
                row.getCell(1).setText(cDto.getCraftArchiveId() != null ? cDto.getCraftArchiveId().toString() : "");
                row.getCell(2).setText(cDto.getNodeOrder() != null ? cDto.getNodeOrder().toString() : "");
                row.getCell(3).setText(cDto.getPersonnelFactors() != null ? cDto.getPersonnelFactors() : "");
                row.getCell(4).setText(cDto.getEquipmentFactors() != null ? cDto.getEquipmentFactors() : "");
                row.getCell(5).setText(cDto.getMaterialFactors() != null ? cDto.getMaterialFactors() : "");
                row.getCell(6).setText(cDto.getEnvironmentFactors() != null ? cDto.getEnvironmentFactors() : "");

                // 设置单元格字体
                for (int i = 0; i < 7; i++) {
                    XWPFParagraph cellParagraph = row.getCell(i).getParagraphs().get(0);
                    cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun cellRun = cellParagraph.createRun();
                    cellRun.setFontFamily("SimSong");
                }
            }

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=craft-process.docx");

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
            CustomExcelUtil.writeToResponse(list.getRows(), CraftProcessDTO.class, response);
        }
    }
}
