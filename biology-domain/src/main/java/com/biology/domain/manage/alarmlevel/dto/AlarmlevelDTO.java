package com.biology.domain.manage.alarmlevel.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelEntity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AlarmlevelDTO {
    @ApiModelProperty("alarmlevel_id")
    @Schema(description = "报警级别Id")
    private Long alarmlevelId;

    @ApiModelProperty("环境档案Id")
    @Schema(description = "环境档案Id")
    private String environmentId;

    @ApiModelProperty("PLC地址")
    @Schema(description = "PLC地址")
    private String plcAddress;

    @ApiModelProperty("启用/禁用")
    @Schema(description = "启用/禁用")
    private boolean enable;

    @ApiModelProperty("报警单位")
    @Schema(description = "报警单位")
    private String unit;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "报警级别详情")
    private List<AlarmlevelDetailDTO> alarmlevelDetailList;

    public AlarmlevelDTO(AlarmlevelEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);

            AlarmlevelDetailEntity alarmlevelDetailEntity = new AlarmlevelDetailEntity();
            QueryWrapper<AlarmlevelDetailEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("alarmlevel_id", entity.getAlarmlevelId());
            List<AlarmlevelDetailEntity> alarmlevelDetailList = alarmlevelDetailEntity.selectList(queryWrapper);
            List<AlarmlevelDetailDTO> alarmlevelDetailDTOList = new ArrayList<>();
            for (AlarmlevelDetailEntity alarmlevelDetail : alarmlevelDetailList) {
                alarmlevelDetailDTOList.add(new AlarmlevelDetailDTO(alarmlevelDetail));
            }
            this.alarmlevelDetailList = alarmlevelDetailDTOList;
        }
    }
}
