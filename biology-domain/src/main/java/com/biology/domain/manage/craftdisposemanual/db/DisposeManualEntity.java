package com.biology.domain.manage.craftdisposemanual.db;

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
@TableName("manage_craft_dispose_manual")
@ApiModel(value = "DisposeManualEntity对象", description = "工艺数据处置手册信息表")
public class DisposeManualEntity extends BaseEntity<DisposeManualEntity> {

    @ApiModelProperty("工艺数据处置手册ID")
    @TableId(value = "craft_dispose_manual_id", type = IdType.AUTO)
    private Long craftDisposeManualId;

    @ApiModelProperty("工艺节点")
    @TableField("craft_node_id")
    private Long craftNodeId;

    @ApiModelProperty("所属工艺档案ID")
    @TableField("craft_archive_id")
    private Long craftArchiveId;

    @ApiModelProperty("发生问题")
    @TableField("problem_description")
    private String problemDescription;

    @ApiModelProperty("紧急处理流程")
    @TableField("emergency_process")
    private String emergencyProcess;

    @ApiModelProperty("责任划分")
    @TableField("responsibility_division")
    private String responsibilityDivision;

    @ApiModelProperty("所需时间")
    @TableField("required_time")
    private String requiredTime;

    @ApiModelProperty("预防措施")
    @TableField("preventive_measures")
    private String preventiveMeasures;

    @Override
    public Long pkVal() {
        return this.craftDisposeManualId;
    }
}
