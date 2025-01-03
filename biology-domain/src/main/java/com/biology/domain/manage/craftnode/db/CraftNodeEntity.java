package com.biology.domain.manage.craftnode.db;

import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.equipment.db.EquipmentEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_craft_node")
@ApiModel(value = "CraftNodeEntity对象", description = "工艺节点表")
public class CraftNodeEntity extends BaseEntity<CraftNodeEntity> {

    @ApiModelProperty("工艺节点ID")
    @TableId(value = "craft_node_id", type = IdType.AUTO)
    private Long craftNodeId;

    @ApiModelProperty("工艺节点名称")
    @TableField("node_name")
    private String nodeName;

    @ApiModelProperty("节点编号")
    @TableField("node_code")
    private String nodeCode;

    @ApiModelProperty("所属工艺档案ID")
    @TableField("craft_archive_id")
    private Long craftArchiveId;

    @ApiModelProperty("关联的设备列表")
    @TableField(exist = false) 
    private List<EquipmentEntity> equipmentList;

    @ApiModelProperty("设备ID列表")
    @TableField(exist = false)
    private List<Long> equipmentIds;

    @ApiModelProperty("节点顺序")
    @TableField("node_order")
    private Integer nodeOrder;

    @ApiModelProperty("所需时间")
    @TableField("required_time")
    private String requiredTime;

    @ApiModelProperty("节点标签")
    @TableField("node_tags")
    private String nodeTags;

    @ApiModelProperty("是否为高风险")
    @TableField("is_high_risk")
    private Boolean isHighRisk;

    @ApiModelProperty("操作描述")
    @TableField("operation_description")
    private String operationDescription;

    @ApiModelProperty("操作方法")
    @TableField("operation_method")
    private String operationMethod;

    @ApiModelProperty("所属工艺档案")
    @TableField(exist = false)
    private CraftArchiveEntity craftArchive;

    @Override
    public Long pkVal() {
        return this.craftNodeId;
    }
}
