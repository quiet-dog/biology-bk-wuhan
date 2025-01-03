package com.biology.domain.manage.receive.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.dto.MaterialsDTO;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;
import com.biology.domain.manage.receive.db.ReceiveEntity;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.system.user.dto.UserDTO;
import com.biology.domain.system.user.dto.UserDetailDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@ExcelSheet(name = "领用记录")
@Data
public class ReceiveDTO {

    @Schema(description = "领用ID")
    private Long receiveId;

    @Schema(description = "领用用户ID")
    private String receiveUserId;

    @Schema(description = "领用用户信息")
    private PersonnelDTO receiverInfo;

    @Schema(description = "物资ID")
    private Long materialsId;

    @Schema(description = "物资信息")
    private MaterialsDTO materialsInfo;

    // 领用数量
    @Schema(description = "领用数量")
    private double receiveNum;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人信息")
    private UserDTO creatorInfo;

    @Schema(description = "领用说明")
    private String receiveExplain;

    public ReceiveDTO(ReceiveEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            if (entity.getMaterialsId() != null) {
                MaterialsEntity materialsEntity = new MaterialsEntity();
                this.materialsInfo = new MaterialsDTO(materialsEntity.selectById(entity.getMaterialsId()));
            }
            if (entity.getReceiveUserId() != null) {
                PersonnelEntity personnelEntity = new PersonnelEntity();
                this.receiverInfo = new PersonnelDTO(personnelEntity.selectById(entity.getReceiveUserId()));
            }

            if (getCreatorId() != null) {
                SysUserEntity sysUserEntityDB = new SysUserEntity();
                UserDTO userDTO = new UserDTO(sysUserEntityDB.selectById(getCreatorId()));
                setCreatorInfo(userDTO);
            }
        }
    }

}
