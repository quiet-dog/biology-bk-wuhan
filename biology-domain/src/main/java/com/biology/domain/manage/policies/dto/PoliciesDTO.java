package com.biology.domain.manage.policies.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.policies.db.PoliciesAppendixEntity;
import com.biology.domain.manage.policies.db.PoliciesEntity;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ExcelSheet(name = "政策法规列表")
@Data
public class PoliciesDTO {

    public PoliciesDTO(PoliciesEntity entity) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
            QueryWrapper<PoliciesAppendixEntity> queryWrapper = new QueryWrapper<PoliciesAppendixEntity>();
            queryWrapper.eq("policies_id", getPoliciesId());
            PoliciesAppendixEntity appendixDB = new PoliciesAppendixEntity();
            List<PoliciesAppendixEntity> appendixList = appendixDB.selectList(queryWrapper);
            setPaths(appendixList);
        }
    }

    @Schema(description = "政策法规ID")
    @ExcelColumn(name = "政策法规ID")
    private Long policiesId;

    @Schema(description = "政策法规编号")
    @ExcelColumn(name = "政策法规编号")
    private String policiesCode;

    @Schema(description = "政策法规名称")
    @ExcelColumn(name = "政策法规名称")
    private String policiesName;

    // @Schema(description = "发布日期")
    // @ExcelColumn(name = "发布日期")
    // private Date releaseDate;
    @Schema(description = "发布日期")
    @ExcelColumn(name = "发布日期")
    private Date createTime;

    private List<PoliciesAppendixEntity> paths;

    @Schema(description = "类型")
    private String type;

}
