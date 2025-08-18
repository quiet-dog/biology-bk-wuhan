package com.biology.domain.manage.shiJuan.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.annotation.ExcelColumn;
import com.biology.common.annotation.ExcelSheet;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.Score;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;

import lombok.Data;

@ExcelSheet(name = "心理测评数据")
@Data
public class ResultShiJuanDTO {

    @ExcelColumn(name = "测试编号")
    private Long resultId;

    @ExcelColumn(name = "量表名称")
    private String type;

    @ExcelColumn(name = "最终得分")
    private Double score;

    private Long xlShiJuanId;

    private Long xlFangAnId;

    private List<Score> result;

    private Long lastTime;

    @ExcelColumn(name = "评估时间")
    private String LastTimeStr;

    private Long userId;

    @ExcelColumn(name = "人员工号")
    private String userJobCode;

    @ExcelColumn(name = "人员姓名")
    private String userNickname;

    @ExcelColumn(name = "所属部门")
    private String deptName;

    private Long startTime;

    private Long useTime;

    @ExcelColumn(name = "评估结果")
    private String cePing;

    private String ganYuFangShi;

    private Long ganYuTime;

    private String execUser;

    private String ganYuResult;

    public ResultShiJuanDTO(ResultShiJuanEntity entity) {
        if (entity != null) {
            BeanUtils.copyProperties(entity, this);
            addUser();
            addExcel();
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

    public void addExcel() {
        if (getLastTime() != null) {
            Date date = new Date(getLastTime());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(date);
            setLastTimeStr(formattedDate);
        }
    }
}
