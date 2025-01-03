package com.biology.domain.manage.craftarchive.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.JsonNode;

@Data
public class AddCraftArchiveCommand {

    @NotBlank(message = "工艺档案编号不能为空")
    @Size(max = 50, message = "工艺档案编号长度不能超过50个字符")
    @Schema(description = "工艺档案编号")
    private String craftArchiveCode;

    @NotBlank(message = "工艺档案名称不能为空")
    @Size(max = 100, message = "工艺档案名称长度不能超过100个字符")
    @Schema(description = "工艺档案名称")
    private String craftArchiveName;

    @NotBlank(message = "版本号不能为空")
    @Size(max = 20, message = "版本号长度不能超过20个字符")
    @Schema(description = "版本")
    private String version;

    @NotBlank(message = "工艺制定人员不能为空")
    @Size(max = 50, message = "工艺制定人员长度不能超过50个字符")
    @Schema(description = "工艺制定人员")
    private String creator;

    @NotNull(message = "创建日期不能为空")
    @Past(message = "创建日期必须是过去的时间")
    @Schema(description = "创建日期")
    private Date createDate;

    @Schema(description = "附件地址")
    private JsonNode attachmentPath;
}
