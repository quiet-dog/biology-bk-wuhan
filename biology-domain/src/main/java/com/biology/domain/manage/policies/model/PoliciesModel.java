package com.biology.domain.manage.policies.model;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.biology.domain.manage.policies.command.AddPoliciesCommand;
import com.biology.domain.manage.policies.command.UpdatePoliciesCommand;
import com.biology.domain.manage.policies.db.PoliciesAppendixEntity;
import com.biology.domain.manage.policies.db.PoliciesAppendixService;
import com.biology.domain.manage.policies.db.PoliciesEntity;
import com.biology.domain.manage.policies.db.PoliciesService;
import com.biology.domain.system.role.db.SysRoleMenuEntity;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PoliciesModel extends PoliciesEntity {

    private List<String> paths;

    private PoliciesService policiesService;

    private PoliciesAppendixService policiesAppendixService;

    public PoliciesModel(PoliciesService policiesService, PoliciesAppendixService policiesAppendixService) {
        this.policiesService = policiesService;
        this.policiesAppendixService = policiesAppendixService;
    }

    public PoliciesModel(PoliciesEntity entity, PoliciesService policiesService,
            PoliciesAppendixService policiesAppendixService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.policiesService = policiesService;
        this.policiesAppendixService = policiesAppendixService;
    }

    public void loadAddPoliciesCommand(AddPoliciesCommand command) {
        if (command != null) {
            BeanUtil.copyProperties(command, this, "policiesId");
        }
    }

    public void loadUpdatePoliciesCommand(UpdatePoliciesCommand command) {
        if (command != null) {
            loadAddPoliciesCommand(command);
        }
    }

    public boolean insert() {
        super.insert();
        return saveAppendix();
    }

    public boolean updateById() {
        cleanOldAppendix();
        saveAppendix();
        return super.updateById();
    }

    public boolean saveAppendix() {
        if (paths != null) {
            List<PoliciesAppendixEntity> appendixList = new ArrayList<>();
            for (String path : paths) {
                PoliciesAppendixEntity appendix = new PoliciesAppendixEntity();
                appendix.setPoliciesId(getPoliciesId());
                appendix.setPath(path);
                appendixList.add(appendix);
            }
            return policiesAppendixService.saveBatch(appendixList);
        }
        return true;
    }

    public boolean cleanOldAppendix() {
        LambdaQueryWrapper<PoliciesAppendixEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PoliciesAppendixEntity::getPoliciesId, getPoliciesId());
        return policiesAppendixService.remove(queryWrapper);
    }

}
