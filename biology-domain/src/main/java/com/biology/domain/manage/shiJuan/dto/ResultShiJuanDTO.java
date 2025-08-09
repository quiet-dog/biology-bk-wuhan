package com.biology.domain.manage.shiJuan.dto;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.Score;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;
import com.biology.domain.system.dept.db.SysDeptEntity;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.system.user.dto.UserDetailDTO;

import lombok.Data;

@Data
public class ResultShiJuanDTO {

    private Long resultId;

    private String type;

    private Double score;

    private Long xlShiJuanId;

    private Long xlFangAnId;

    private List<Score> result;

    private Long lastTime;

    private Long userId;

    private String userJobCode;

    private String userNickname;

    private String deptName;

    private Long startTime;

    private Long useTime;

    private String cePing;

    private String ganYuFangShi;

    private Long ganYuTime;

    private String execUser;

    private String ganYuResult;

    public ResultShiJuanDTO(ResultShiJuanEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addUser();
        }
    }

    public void addUser() {
        if (getUserId() != null && getUserId() > 0) {
            // SysUserEntity sEntity = new SysUserEntity().selectById(getUserId());
            // if (sEntity != null) {
            // setUserJobCode(sEntity.getJobCode());
            // setUserNickname(sEntity.getNickname());
            // SysDeptEntity sEntity2 = new SysDeptEntity().selectById(sEntity.getDeptId());
            // if (sEntity2 != null) {
            // setDeptName(sEntity2.getDeptName());
            // }
            // }

            QueryWrapper<XlArchiveEntity> aWrapper = new QueryWrapper<XlArchiveEntity>();
            aWrapper.eq("user_id", getUserId());
            XlArchiveEntity archiveEntity = new XlArchiveEntity().selectOne(aWrapper);
            if (archiveEntity != null) {
                QueryWrapper<PersonnelEntity> pQueryWrapper = new QueryWrapper<PersonnelEntity>();
                pQueryWrapper.eq("personnel_id", archiveEntity.getPersonnelId());
                PersonnelEntity personnelEntity = new PersonnelEntity().selectOne(pQueryWrapper);
                if (personnelEntity != null) {
                    setUserJobCode(personnelEntity.getCode());
                    setUserNickname(personnelEntity.getName());
                    setDeptName(personnelEntity.getDepartment());
                }
            }
        }
    }
}
