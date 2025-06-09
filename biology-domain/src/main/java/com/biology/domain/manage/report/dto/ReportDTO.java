package com.biology.domain.manage.report.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.dto.MaterialsDTO;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.report.db.ReportEntity;
import com.biology.domain.manage.report.db.ReportFileEntity;
import com.biology.domain.system.post.db.SysPostEntity;
import com.biology.domain.system.role.db.SysRoleEntity;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.system.user.dto.UserProfileDTO;

import cn.hutool.db.sql.Query;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ExcelSheet(name = "人员上报")
@Data
public class ReportDTO {

    @ApiModelProperty(value = "报告ID")
    @Schema(description = "报告ID")
    private Long reportId;

    @ApiModelProperty(value = "报告编号")
    @Schema(description = "报告编号")
    private String code;

    @ApiModelProperty(value = "报告人信息")
    private UserProfileDTO reporterInfo;

    @ApiModelProperty(value = "物资ID")
    @Schema(description = "物资ID")
    private Long materialsId;

    @ApiModelProperty(value = "物资信息")
    @Schema(description = "物资信息")
    private MaterialsDTO materialsInfo;

    @ApiModelProperty(value = "报告类型")
    @Schema(description = "报告类型")
    private String reportType;

    @ApiModelProperty(value = "报告数量")
    @Schema(description = "报告数量")
    private double reportNum;

    @ApiModelProperty(value = "上报原因")
    @Schema(description = "上报原因")
    private String reportReason;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "文件路径")
    private List<String> paths;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "当时库存数量")
    private double stock;

    @Schema(description = "批次")
    private String batch;

    public ReportDTO(ReportEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);

            if (getReportId() != null) {

            }

            if (entity.getMaterialsId() != null) {
                MaterialsEntity materialsEntityDB = new MaterialsEntity();
                this.materialsInfo = new MaterialsDTO(materialsEntityDB.selectById(getMaterialsId()));
            }

            if (entity.getCreatorId() != null) {
                SysUserEntity sysUserEntity = new SysUserEntity();
                SysPostEntity postEntity = new SysPostEntity();
                SysRoleEntity roleEntity = new SysRoleEntity();
                sysUserEntity = sysUserEntity.selectById(entity.getCreatorId());
                if (sysUserEntity != null) {
                    postEntity = postEntity.selectById(sysUserEntity.getPostId());
                    roleEntity = roleEntity.selectById(sysUserEntity.getRoleId());
                    this.reporterInfo = new UserProfileDTO(sysUserEntity, postEntity, roleEntity);
                }
            }

            QueryWrapper<ReportFileEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("report_id", getReportId());
            List<ReportFileEntity> reportFileEntities = new ReportFileEntity().selectList(queryWrapper);
            if (reportFileEntities != null && !reportFileEntities.isEmpty()) {
                paths = new ArrayList<>();
                reportFileEntities.forEach(reportFileEntity -> {
                    paths.add(reportFileEntity.getPath());
                });
            }
        }
    }
}
