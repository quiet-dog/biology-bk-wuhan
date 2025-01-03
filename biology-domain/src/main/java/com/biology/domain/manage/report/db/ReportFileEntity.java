package com.biology.domain.manage.report.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("manage_report_file")
@ApiModel(value = "ReportFileEntity对象", description = "人员上报文件表")
public class ReportFileEntity extends Model<ReportFileEntity> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "report_id", type = IdType.AUTO)
    private Long reportId;

    @TableField(value = "path")
    private String path;

    @Override
    public Long pkVal() {
        return this.reportId;
    }
}
