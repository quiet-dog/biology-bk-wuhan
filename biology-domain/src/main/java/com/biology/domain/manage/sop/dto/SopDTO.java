package com.biology.domain.manage.sop.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.emergency.dto.EmergencyFileDTO;
import com.biology.domain.manage.equipment.db.EquipmentEntity;
import com.biology.domain.manage.equipment.dto.EquipmentDTO;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.sop.db.SopEquipmentEnity;
import com.biology.domain.manage.sop.db.SopFileEntity;
import com.biology.domain.system.post.db.SysPostEntity;
import com.biology.domain.system.post.dto.PostDTO;
import com.biology.domain.system.role.db.SysRoleEntity;
import com.biology.domain.system.role.dto.RoleDTO;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.system.user.dto.UserDTO;
import com.biology.domain.system.user.dto.UserDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SopDTO {

    @Schema(description = "sop Id")
    private Long sopId;

    @Schema(description = "sop 编号")
    private String code;

    @Schema(description = "sop 名称")
    private String name;

    @Schema(description = "sop 范围")
    private String scope;

    @Schema(description = "sop 内容路径")
    private List<SopFileDTO> paths;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "创建人")
    private Long creatorId;

    private UserDetailDTO creator;

    @Schema(description = "设备信息")
    private List<EquipmentDTO> equipments;

    public SopDTO(SopEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);

            SopFileEntity fileEntity = new SopFileEntity();
            QueryWrapper<SopFileEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sop_id", getSopId());
            List<SopFileEntity> list = fileEntity.selectList(queryWrapper);
            if (list != null && list.size() > 0) {
                this.paths = list.stream().map(SopFileDTO::new).collect(Collectors.toList());
            }

            // sop设备
            EquipmentEntity equipmentDB = new EquipmentEntity();
            QueryWrapper<EquipmentEntity> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.inSql("equipment_id",
                    "select equipment_id from manage_sop_equipment where sop_id = " + getSopId());
            List<EquipmentEntity> equipmentList = equipmentDB.selectList(queryWrapper2);
            if (equipmentList != null && equipmentList.size() > 0) {
                this.equipments = equipmentList.stream().map(EquipmentDTO::new).collect(Collectors.toList());
            }
            addCreator();
        }

    }

    public void addCreator() {
        if (getCreatorId() != null) {
            UserDetailDTO detailDTO = new UserDetailDTO();
            SysUserEntity userEntity = new SysUserEntity().selectById(getCreatorId());
            detailDTO.setUser(new UserDTO(userEntity));
            SysPostEntity postEntity = new SysPostEntity().selectById(userEntity.getPostId());
            detailDTO.setPostId(userEntity.getPostId());
            detailDTO.setPostOptions(new ArrayList<>());
            detailDTO.getPostOptions().add(new PostDTO(postEntity));
        }
    }
}
