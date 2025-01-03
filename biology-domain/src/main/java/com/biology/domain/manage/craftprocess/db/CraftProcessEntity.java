package com.biology.domain.manage.craftprocess.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.biology.common.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_craft_process")
@ApiModel(value = "CraftProcessEntity对象", description = "工艺流程图表")
public class CraftProcessEntity extends BaseEntity<CraftProcessEntity> {

    @ApiModelProperty("工艺流程图ID")
    @TableId(value = "craft_process_id", type = IdType.AUTO)
    private Long craftProcessId;

    @ApiModelProperty("所属工艺档案ID")
    @TableField("craft_archive_id")
    private Long craftArchiveId;

    @ApiModelProperty("工艺节点ID")
    @TableField("craft_node_id")
    private Long craftNodeId;

    @ApiModelProperty("节点顺序")
    @TableField("node_order")
    private Integer nodeOrder;

    @ApiModelProperty("人员要素")
    @TableField("personnel_factors")
    private String personnelFactors;

    @ApiModelProperty("设备要素")
    @TableField("equipment_factors")
    private String equipmentFactors;

    @ApiModelProperty("原料要素")
    @TableField("material_factors")
    private String materialFactors;

    @ApiModelProperty("环境要素")
    @TableField("environment_factors")
    private String environmentFactors;

    @Override
    public Long pkVal() {
        return this.craftProcessId;
    }
}
