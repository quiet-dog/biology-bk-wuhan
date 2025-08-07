package com.biology.domain.manage.smData;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.smData.command.AddSmDataCommand;
import com.biology.domain.manage.smData.command.UpdateSmDataCommand;
import com.biology.domain.manage.smData.db.SmDataEntity;
import com.biology.domain.manage.smData.db.SmDataService;
import com.biology.domain.manage.smData.dto.SmDataDTO;
import com.biology.domain.manage.smData.model.SmDataFactory;
import com.biology.domain.manage.smData.model.SmDataModel;
import com.biology.domain.manage.smData.query.SmDataQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmDataApplicationService {
    private final SmDataFactory smDataFactory;

    private final SmDataService smDataService;

    public void create(AddSmDataCommand command) {
        SmDataModel smDataModel = smDataFactory.create();
        smDataModel.loadAddSmDataCommand(command);
        smDataModel.insert();
        return;
    }

    public void update(UpdateSmDataCommand command) {
        SmDataModel smDataModel = smDataFactory.loadById(command.getSmDataId());
        smDataModel.loadUpdateSmDataCommand(command);
        smDataModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> smDataIds) {
        smDataIds.forEach(smDataId -> {
            SmDataModel smDataModel = smDataFactory.loadById(smDataId);
            smDataModel.deleteById();
        });
        return;
    }

    public PageDTO<SmDataDTO> getSmDatas(SmDataQuery query) {
        Page<SmDataEntity> page = smDataService.page(query.toPage(), query.toQueryWrapper());
        List<SmDataDTO> records = page.getRecords().stream().map(SmDataDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public SmDataDTO getSmDataInfo(Long smDataId) {
        SmDataEntity byId = smDataService.getById(smDataId);
        return new SmDataDTO(byId);
    }

}
