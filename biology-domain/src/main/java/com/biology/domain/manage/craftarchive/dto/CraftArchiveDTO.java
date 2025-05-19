package com.biology.domain.manage.craftarchive.dto;

import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import org.springframework.beans.BeanUtils;
import com.alibaba.excel.annotation.ExcelProperty;

import java.util.Date;

@Data
@ExcelSheet(name = "工艺档案表")
public class CraftArchiveDTO {
    @Schema(description = "工艺档案ID")
    @ExcelColumn(name = "工艺档案ID", showInImportTemplate = false)
    @ExcelProperty("工艺档案ID")
    private Long craftArchiveId;

    @ExcelColumn(name = "工艺档案编号")
    @ExcelProperty("工艺档案编号")
    @Schema(description = "工艺档案编号")
    private String craftArchiveCode;

    @ExcelColumn(name = "工艺档案名称")
    @ExcelProperty("工艺档案名称")
    @Schema(description = "工艺档案名称")
    private String craftArchiveName;

    @ExcelColumn(name = "版本")
    @ExcelProperty("版本")
    @Schema(description = "版本")
    private String version;

    @ExcelColumn(name = "工艺制定人员")
    @ExcelProperty("工艺制定人员")
    @Schema(description = "工艺制定人员")
    private String creator;

    @ExcelColumn(name = "创建日期")
    @ExcelProperty("创建日期")
    @Schema(description = "创建日期")
    private Date createDate;

    @Schema(description = "附件地址")
    // @ExcelColumn(name = "附件地址", showInImportTemplate = false)
    @ExcelProperty("附件地址")
    private JsonNode attachmentPath;

    public CraftArchiveDTO(CraftArchiveEntity entity) {
        BeanUtils.copyProperties(entity, this);
    }

    public Long getId() {
        return craftArchiveId;
    }
}
