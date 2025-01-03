package com.biology.domain.manage.personnel.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.healthy.db.HealthyEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.manage.personnel.db.PersonnelCardEntity;
import com.biology.domain.manage.personnel.db.PersonnelCertificatesEntity;
import com.biology.domain.manage.personnel.db.PersonnelEntity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PersonnelDTO {

    @Schema(description = "人员ID")
    // @ExcelColumn(name = "用户Id")
    private Long personnelId;

    @Schema(description = "人员编号")
    @ExcelColumn(name = "员工编号")
    private String code;

    @Schema(description = "人员姓名")
    @ExcelColumn(name = "员工姓名")
    private String name;

    @Schema(description = "性别")
    @ExcelColumn(name = "性别")
    private String sex;

    // 部门
    @Schema(description = "部门")
    @ExcelColumn(name = "部门")
    private String department;

    // 岗位
    @Schema(description = "岗位")
    @ExcelColumn(name = "岗位")
    private String post;
    // 职级
    @Schema(description = "职级")
    // @ExcelColumn(name = "职级")
    private String pRank;
    @Schema(description = "邮箱")
    @ExcelColumn(name = "邮箱")
    private String email;
    @Schema(description = "身份证号")
    @ExcelColumn(name = "身份证号")
    private String card;

    @Schema(description = "证件照")
    private List<PersonnelCardEntity> cardFiles;

    @Schema(description = "证书与资质")
    // @ExcelColumn(name = "证书与资质")
    private String certificates;

    private List<PersonnelCertificatesEntity> certificateFiles;
    // 学历
    @Schema(description = "学历")
    @ExcelColumn(name = "学历")
    private String education;
    // 联系方式
    @Schema(description = "联系方式")
    @ExcelColumn(name = "联系方式")
    private String contact;
    // 入职时间
    @Schema(description = "入职时间")
    @ExcelColumn(name = "入职时间")
    private Date entryTime;
    // 离职时间
    @Schema(description = "离职时间")
    @ExcelColumn(name = "离职时间")
    private Date leaveTime;

    @Schema(description = "最近的健康信息")
    private HealthyEntity healthyInfo;

    private Integer outId;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    public PersonnelDTO(PersonnelEntity entity) {
        if (entity != null) {

            BeanUtils.copyProperties(entity, this);
            PersonnelCardEntity cardEntity = new PersonnelCardEntity();
            QueryWrapper<PersonnelCardEntity> cardWrapper = new QueryWrapper<>();
            this.setCardFiles(cardEntity.selectList(cardWrapper.eq("personnel_id", entity.getPersonnelId())));
            QueryWrapper<PersonnelCertificatesEntity> certificateWrapper = new QueryWrapper<>();
            PersonnelCertificatesEntity personnelCertificatesEntity = new PersonnelCertificatesEntity();
            this.setCertificateFiles(personnelCertificatesEntity
                    .selectList(certificateWrapper.eq("personnel_id", entity.getPersonnelId())));
        }
    }
}
