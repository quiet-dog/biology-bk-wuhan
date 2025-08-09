package com.biology.domain.manage.xlArchive;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.xlArchive.command.AddXlArchiveCommand;
import com.biology.domain.manage.xlArchive.command.UpdateXlArchiveCommand;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlArchive.dto.XlArchiveDTO;
import com.biology.domain.manage.xlArchive.model.XlArchiveModel;
import com.biology.domain.manage.xlArchive.query.XlArchiveQuery;
import com.biology.domain.manage.xlArchive.db.XlArchiveService;
import com.biology.domain.manage.xlArchive.model.XlArchiveFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XlArchiveApplicationService {
    private final XlArchiveFactory xlArchiveFactory;

    private final XlArchiveService xlArchiveService;

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
}
