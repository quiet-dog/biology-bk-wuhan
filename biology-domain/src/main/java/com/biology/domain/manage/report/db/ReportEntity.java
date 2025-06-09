package com.biology.domain.manage.report.db;

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
@TableName("manage_report")
@ApiModel(value = "ReportEntity对象", description = "人员上报")
public class ReportEntity extends BaseEntity<ReportEntity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("report_id")
    @TableId(value = "report_id", type = IdType.AUTO)
    private Long reportId;

    @ApiModelProperty("code")
    @TableField("code")
    private String code;

    @ApiModelProperty("materials_id")
    @TableField("materials_id")
    private Long materialsId;

    @ApiModelProperty("report_type")
    @TableField("report_type")
    private String reportType;

    @ApiModelProperty("report_num")
    @TableField("report_num")
    private double reportNum;

    // 上报原因
    @ApiModelProperty("report_reason")
    @TableField("report_reason")
    private String reportReason;

    @ApiModelProperty("当时库存数量")
    @TableField("stock")
    private double stock;

    @ApiModelProperty("批次")
    @TableField("batch")
    private String batch;

    @Override
    public Long pkVal() {
        return this.reportId;
    }
}
