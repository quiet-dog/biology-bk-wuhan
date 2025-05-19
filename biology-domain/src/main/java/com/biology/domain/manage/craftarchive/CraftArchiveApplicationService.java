package com.biology.domain.manage.craftarchive;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.MediaType;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Internal;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.craftarchive.command.AddCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.command.UpdateCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveService;
import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;
import com.biology.domain.manage.craftarchive.model.CraftArchiveFactory;
import com.biology.domain.manage.craftarchive.model.CraftArchiveModel;
import com.biology.domain.manage.craftarchive.query.SearchCraftArchiveQuery;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

@Service
@RequiredArgsConstructor
public class CraftArchiveApplicationService {

    private final CraftArchiveFactory craftArchiveFactory;
    private final CraftArchiveService craftArchiveService;

    public void createCraftArchive(AddCraftArchiveCommand command) {
        CraftArchiveModel craftArchiveModel = craftArchiveFactory.create();
        craftArchiveModel.loadAddCommand(command);
        craftArchiveModel.insert();
    }

    public void updateCraftArchive(UpdateCraftArchiveCommand command) {
        CraftArchiveModel craftArchiveModel = craftArchiveFactory.loadById(command.getCraftArchiveId());
        craftArchiveModel.loadUpdateCommand(command);
        craftArchiveModel.update();
    }

    public void deleteCraftArchives(List<Long> craftArchiveIds) {
        for (Long craftArchiveId : craftArchiveIds) {
            CraftArchiveModel craftArchiveModel = craftArchiveFactory.loadById(craftArchiveId);
            craftArchiveModel.deleteById();
        }
    }

    public PageDTO<CraftArchiveDTO> getCraftArchiveList(SearchCraftArchiveQuery query) {

        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        } else {
            // 如果pageSize为和pageNum为0，则不分页
            if (query.getPageSize() == 0 && query.getPageNum() == 0) {
                query.setPageSize(Integer.MAX_VALUE);
                query.setPageNum(1);
            }
        }

        Page<CraftArchiveEntity> page = craftArchiveService.page(query.toPage(), query.toQueryWrapper());
        List<CraftArchiveDTO> records = page.getRecords().stream().map(CraftArchiveDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public CraftArchiveDTO getCraftArchiveInfo(Long craftArchiveId) {
        CraftArchiveModel craftArchiveModel = craftArchiveFactory.loadById(craftArchiveId);
        return new CraftArchiveDTO(craftArchiveModel);
    }

    public void export(HttpServletResponse response, SearchCraftArchiveQuery query)
            throws IOException {
        String fileName = "工艺档案列表_" + System.currentTimeMillis() + ".xlsx";
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        PageDTO<CraftArchiveDTO> pageResult = this.getCraftArchiveList(query);

        if (query.getExportType() != null && query.getExportType().equals("pdf")) {
            if (pageResult.getRows() != null && pageResult.getRows().size() == 0) {

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
            // 创建表格（6列：工艺档案编号、名称、版本、制定人员、创建日期、附件地址）
            Table table = new Table(UnitValue.createPercentArray(new float[] { 15, 20, 10, 15, 20 }));
            table.setWidth(UnitValue.createPercentValue(100));

            // 添加表头
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("工艺档案编号").setFont(font)));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("工艺档案名称").setFont(font)));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("版本").setFont(font)));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("工艺制定人员").setFont(font)));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("创建日期").setFont(font)));
            // table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new
            // Paragraph("附件地址").setFont(font)));

            // 填充数据
            for (CraftArchiveDTO craft : pageResult.getRows()) {
                table.addCell(new com.itextpdf.layout.element.Cell().add(
                        new Paragraph(craft.getCraftArchiveCode() != null ? craft.getCraftArchiveCode() : "")
                                .setFont(font)));
                table.addCell(new com.itextpdf.layout.element.Cell().add(
                        new Paragraph(craft.getCraftArchiveName() != null ? craft.getCraftArchiveName() : "")
                                .setFont(font)));
                table.addCell(new com.itextpdf.layout.element.Cell().add(
                        new Paragraph(craft.getVersion() != null ? craft.getVersion() : "").setFont(font)));
                table.addCell(new com.itextpdf.layout.element.Cell().add(
                        new Paragraph(craft.getCreator() != null ? craft.getCreator() : "").setFont(font)));
                table.addCell(new com.itextpdf.layout.element.Cell().add(
                        new Paragraph(craft.getCreateDate() != null ? craft.getCreateDate().toString() : "")
                                .setFont(font)));
                // table.addCell(new com.itextpdf.layout.element.Cell().add(
                // new Paragraph(craft.getAttachmentPath() != null ?
                // craft.getAttachmentPath().toString() : "")
                // .setFont(font)));
            }

            document.add(table);
            document.close();

            // 写入响应
            response.setContentLength(baos.size());
            try {
                baos.writeTo(response.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException("无法写入PDF到响应: " + e.getMessage(), e);
            } finally {
                baos.close();
            }

        } else if (query.getExportType() != null && query.getExportType().equals("word")) {
            // 创建 Word 文档
            XWPFDocument document = new XWPFDocument();

            // 添加标题
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("工艺档案表");
            titleRun.setFontSize(18);
            titleRun.setBold(true);
            titleRun.setFontFamily("SimSong"); // 使用宋体支持中文
            titleRun.addBreak();

            // 创建表格（6列：工艺档案编号、名称、版本、制定人员、创建日期、附件地址）
            XWPFTable table = document.createTable(1, 6); // 1行（表头）, 6列
            table.setWidth(5000); // 设置表格宽度（单位：1/50 英寸）

            // 设置表头
            XWPFTableRow headerRow = table.getRow(0);
            String[] headers = { "工艺档案编号", "工艺档案名称", "版本", "工艺制定人员", "创建日期", };
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
            for (CraftArchiveDTO craft : pageResult.getRows()) {
                XWPFTableRow row = table.createRow();
                row.getCell(0).setText(craft.getCraftArchiveCode() != null ? craft.getCraftArchiveCode() : "");
                row.getCell(1).setText(craft.getCraftArchiveName() != null ? craft.getCraftArchiveName() : "");
                row.getCell(2).setText(craft.getVersion() != null ? craft.getVersion() : "");
                row.getCell(3).setText(craft.getCreator() != null ? craft.getCreator() : "");
                row.getCell(4).setText(craft.getCreateDate() != null ? craft.getCreateDate().toString() : "");

                // 设置单元格字体
                for (int i = 0; i < 5; i++) {
                    XWPFParagraph cellParagraph = row.getCell(i).getParagraphs().get(0);
                    cellParagraph.setAlignment(ParagraphAlignment.CENTER);
                    XWPFRun cellRun = cellParagraph.createRun();
                    cellRun.setFontFamily("SimSong");
                }
            }

            // 设置响应头
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=craft_archive.docx");

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
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName);
            CustomExcelUtil.writeToResponse(pageResult.getRows(), CraftArchiveDTO.class, response);
        }

    }

    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        String fileName = "工艺档案导入模板_" + System.currentTimeMillis() + ".xlsx";
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName);

        // 创建一个空的CraftArchiveDTO作为模板
        CraftArchiveDTO template = new CraftArchiveDTO(new CraftArchiveEntity());
        List<CraftArchiveDTO> templateList = new ArrayList<>();
        templateList.add(template);

        // 导出模板
        CustomExcelUtil.writeToOutputStreamForImportTemp(templateList, CraftArchiveDTO.class,
                response.getOutputStream());
    }

}
