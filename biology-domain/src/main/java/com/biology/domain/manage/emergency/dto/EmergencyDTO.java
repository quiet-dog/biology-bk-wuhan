package com.biology.domain.manage.emergency.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.db.EmergencyFileEntity;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.system.dept.db.SysDeptEntity;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EmergencyDTO {
    @ApiModelProperty("应急编号")
    private Long emergencyId;

    @ApiModelProperty("应急编号")
    private String code;

    @ApiModelProperty("应急名称")
    private String title;

    @ApiModelProperty("应急版本号")
    private String version;

    @ApiModelProperty("颁发部门")
    private Long deptId;

    @ApiModelProperty("颁发部门名称")
    private String deptName;

    @ApiModelProperty("适用范围")
    private String scope;

    @ApiModelProperty("风险类型")
    private String riskType;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private Date updateTime;

    private List<String> paths;

    @Schema(description = "设备信息")
    private List<EquipmentDTO> equipments;

    public EmergencyDTO() {

    }

    public EmergencyDTO(EmergencyEntity entity) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
            SysDeptEntity deptEntity = CacheCenter.deptCache.get(getDeptId() + "");
            if (deptEntity != null) {
                setDeptName(deptEntity.getDeptName());
            }

            EmergencyFileEntity fileEntity = new EmergencyFileEntity();
            QueryWrapper<EmergencyFileEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("emergency_id", getEmergencyId());
            List<EmergencyFileEntity> files = fileEntity.selectList(queryWrapper);
            if (files != null) {
                paths = new ArrayList<>();
                for (EmergencyFileEntity file : files) {
                    paths.add(file.getPath());
                }
            }

            // 设备信息
            EquipmentEntity equipmentDB = new EquipmentEntity();
            QueryWrapper<EquipmentEntity> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.inSql("equipment_id",
                    "select equipment_id from manage_emergency_equipment where emergency_id = " + getEmergencyId());
            List<EquipmentEntity> equipmentList = equipmentDB.selectList(queryWrapper2);
            if (equipmentList != null && equipmentList.size() > 0) {
                this.equipments = equipmentList.stream().map(EquipmentDTO::new).collect(Collectors.toList());
            }
        }
    }
}
