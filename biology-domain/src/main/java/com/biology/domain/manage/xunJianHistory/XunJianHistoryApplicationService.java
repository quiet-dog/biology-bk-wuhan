package com.biology.domain.manage.xunJianHistory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.xunJian.command.UpdateXunJianCommand;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.db.XunJianService;
import com.biology.domain.manage.xunJian.query.XunJianQuery;
import com.biology.domain.manage.xunJianHistory.command.AddXunJianHistoryCommand;
import com.biology.domain.manage.xunJianHistory.command.UpdateXunJianHistoryCommand;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryService;
import com.biology.domain.manage.xunJianHistory.dto.XunJianHistoryDTO;
import com.biology.domain.manage.xunJianHistory.model.XunJianHistoryFactory;
import com.biology.domain.manage.xunJianHistory.model.XunJianHistoryModel;
import com.biology.domain.manage.xunJianHistory.query.XunJianHistoryQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XunJianHistoryApplicationService {
    private final XunJianHistoryFactory xunJianHistoryFactory;

    private final XunJianHistoryService xunJianHistoryService;

    public void create(AddXunJianHistoryCommand command) {
        XunJianHistoryModel xunJianHistoryModel = xunJianHistoryFactory.create();
        xunJianHistoryModel.loadAddXunJianHistoryCommand(command);
        xunJianHistoryModel.insert();
    }

    public void update(UpdateXunJianHistoryCommand command) {
        XunJianHistoryModel xunJianHistoryModel = xunJianHistoryFactory.loadById(command.getXunJianId());
        xunJianHistoryModel.loadUpdateXunJianHistoryCommand(command);
        xunJianHistoryModel.updateById();
    }

    public void deleteReveives(List<Long> xunJianIds) {
        xunJianIds.forEach(xunJianId -> {
            XunJianHistoryModel xunJianHistoryModel = xunJianHistoryFactory.loadById(xunJianId);
            xunJianHistoryModel.deleteById();
        });
    }

    public PageDTO<XunJianHistoryDTO> getXunJians(XunJianHistoryQuery query) {

        Page<XunJianHistoryEntity> page = xunJianHistoryService.page(query.toPage(), query.toQueryWrapper());
        List<XunJianHistoryDTO> records = page.getRecords().stream().map(XunJianHistoryDTO::new)
                .collect(Collectors.toList());
        records.forEach(item -> {
            item.setTotal(xunJianHistoryService.getTotalByHistoryId(item.getXunJianHistoryId()));
        });
        return new PageDTO<>(records, page.getTotal());
    }

    public XunJianHistoryDTO getXunJianInfo(Long xunJianId) {
        XunJianHistoryEntity byId = xunJianHistoryService.getById(xunJianId);
        return new XunJianHistoryDTO(byId);
    }

}
