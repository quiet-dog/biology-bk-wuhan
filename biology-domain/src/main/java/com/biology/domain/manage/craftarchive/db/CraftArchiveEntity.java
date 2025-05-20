package com.biology.domain.manage.craftarchive.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.biology.common.core.base.BaseEntity;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName(value = "manage_craft_archive", autoResultMap = true)
@ApiModel(value = "CraftArchiveEntity对象", description = "工艺档案信息表")
public class CraftArchiveEntity extends BaseEntity<CraftArchiveEntity> {

    @ApiModelProperty("工艺档案ID")
    @TableId(value = "craft_archive_id", type = IdType.AUTO)
    private Long craftArchiveId;

    @ApiModelProperty("工艺档案编号")
    @TableField("craft_archive_code")
    private String craftArchiveCode;

    @ApiModelProperty("工艺档案名称")
    @TableField("craft_archive_name")
    private String craftArchiveName;

    @ApiModelProperty("版本")
    @TableField("version")
    private String version;

    @ApiModelProperty("工艺制定人员")
    @TableField("creator")
    private String creator;

    @ApiModelProperty("创建日期")
    @TableField("create_date")
    private Date createDate;

    @ApiModelProperty("附件地址")
    @TableField(value = "attachment_path", typeHandler = JacksonTypeHandler.class)
    private JsonNode attachmentPath;

    @ApiModelProperty("标签名称")
    @TableField("label_name")
    private String labelName;

    @ApiModelProperty("标签颜色")
    @TableField("color")
    private String color;

    @ApiModelProperty("标签描述")
    @TableField("color_description")
    private String colorDescription;
}
