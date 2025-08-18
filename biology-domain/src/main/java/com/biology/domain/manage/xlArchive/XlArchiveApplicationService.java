package com.biology.domain.manage.xlArchive;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.xlArchive.command.AddXlArchiveCommand;
import com.biology.domain.manage.xlArchive.command.ImportXlArchiveCommand;
import com.biology.domain.manage.xlArchive.command.UpdateXlArchiveCommand;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlArchive.dto.XlArchiveDTO;
import com.biology.domain.manage.xlArchive.model.XlArchiveModel;
import com.biology.domain.manage.xlArchive.query.XlArchiveQuery;

import cn.hutool.core.util.StrUtil;

import com.biology.domain.manage.xlArchive.db.XlArchiveService;
import com.biology.domain.manage.xlArchive.model.XlArchiveFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XlArchiveApplicationService {
    private final XlArchiveFactory xlArchiveFactory;

    private final XlArchiveService xlArchiveService;

    private final PersonnelService personnelService;

    public void create(AddXlArchiveCommand command) {
        XlArchiveModel xlArchiveModel = xlArchiveFactory.create();
        xlArchiveModel.loadAddXlArchiveCommand(command);
        xlArchiveModel.insert();
        return;
    }

    public void update(UpdateXlArchiveCommand command) {
        XlArchiveModel xlArchiveModel = xlArchiveFactory.loadById(command.getXlArchiveId());
        xlArchiveModel.loadUpdateXlArchiveCommand(command);
        xlArchiveModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> xlArchiveIds) {
        xlArchiveIds.forEach(xlArchiveId -> {
            XlArchiveModel xlArchiveModel = xlArchiveFactory.loadById(xlArchiveId);
            xlArchiveModel.deleteById();
        });
        return;
    }

    public PageDTO<XlArchiveDTO> getXlArchives(XlArchiveQuery query) {
        Page<XlArchiveEntity> page = xlArchiveService.page(query.toPage(), query.toQueryWrapper());
        List<XlArchiveDTO> records = page.getRecords().stream().map(XlArchiveDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public XlArchiveDTO getXlArchiveInfo(Long xlArchiveId) {
        XlArchiveEntity byId = xlArchiveService.getById(xlArchiveId);
        return new XlArchiveDTO(byId);
    }

    public void importExcel(List<ImportXlArchiveCommand> data) {
        Set<String> jobCodes = new HashSet<>();
        for (ImportXlArchiveCommand d : data) {
            if (!StrUtil.isEmpty(d.getJobCode())) {
                throw new ApiException(Business.PERSONNOT_SERVICE_UNAVAILABLE, d.getUsername() + "工号不能为空");
            }

            if (!jobCodes.add(d.getJobCode())) {
                throw new ApiException(Business.PERSONNOT_SERVICE_UNAVAILABLE, d.getJobCode() + "重复");
            }
        }

        for (ImportXlArchiveCommand d : data) {
            QueryWrapper<PersonnelEntity> queryWrapper = new QueryWrapper<PersonnelEntity>();
            queryWrapper.eq("code", d.getJobCode());
            PersonnelEntity pEntity = personnelService.getOne(queryWrapper);
            if (pEntity == null) {
                throw new ApiException(Business.PERSONNOT_SERVICE_UNAVAILABLE, d.getJobCode() + ",人员档案不存在该用户");
            }
            QueryWrapper<XlArchiveEntity> queryWrapper2 = new QueryWrapper<XlArchiveEntity>();
            queryWrapper2.eq("personnel_id", pEntity.getPersonnelId());
            XlArchiveEntity xEntity = xlArchiveService.getOne(queryWrapper2);
            if (xEntity != null) {
                AddXlArchiveCommand addXlArchiveCommand = new AddXlArchiveCommand();
                addXlArchiveCommand.setPersonnelId(pEntity.getPersonnelId());
                this.create(addXlArchiveCommand);
            }
        }
    }
}
