package com.biology.domain.manage.kongTiaoData;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.kongTiaoData.command.AddKongTiaoDataCommand;
import com.biology.domain.manage.kongTiaoData.command.UpdateKongTiaoDataCommand;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataEntity;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataService;
import com.biology.domain.manage.kongTiaoData.dto.KongTiaoDataDTO;
import com.biology.domain.manage.kongTiaoData.model.KongTiaoDataFactory;
import com.biology.domain.manage.kongTiaoData.model.KongTiaoDataModel;
import com.biology.domain.manage.kongTiaoData.query.KongTiaoDataQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KongTiaoDataApplicationService {
    private final KongTiaoDataFactory kongTiaoDataFactory;

    private final KongTiaoDataService kongTiaoDataService;

    public void create(AddKongTiaoDataCommand command) {
        KongTiaoDataDTO kongTiaoDataDTO = new KongTiaoDataDTO(command);
        kongTiaoDataDTO.setCreateTime(new Date());
        CacheCenter.kongTiaoDataCache.set(kongTiaoDataDTO.getDeviceSn(), kongTiaoDataDTO);

        if (!command.getIsOnline()) {
            return;
        }

        KongTiaoDataModel kongTiaoDataModel = kongTiaoDataFactory.create();
        kongTiaoDataModel.loadAddKongTiaoDataCommand(command);
        kongTiaoDataModel.insert();

        // 将command赋值给

        return;
    }

    public void update(UpdateKongTiaoDataCommand command) {
        KongTiaoDataModel kongTiaoDataModel = kongTiaoDataFactory.loadById(command.getKongTiaoDataId());
        kongTiaoDataModel.loadUpdateKongTiaoDataCommand(command);
        kongTiaoDataModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> kongTiaoDataIds) {
        kongTiaoDataIds.forEach(kongTiaoDataId -> {
            KongTiaoDataModel kongTiaoDataModel = kongTiaoDataFactory.loadById(kongTiaoDataId);
            kongTiaoDataModel.deleteById();
        });
        return;
    }

    public PageDTO<KongTiaoDataDTO> getKongTiaoDatas(KongTiaoDataQuery query) {
        Page<KongTiaoDataEntity> page = kongTiaoDataService.page(query.toPage(), query.toQueryWrapper());
        List<KongTiaoDataDTO> records = page.getRecords().stream().map(KongTiaoDataDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public KongTiaoDataDTO getKongTiaoDataInfo(Long kongTiaoDataId) {
        KongTiaoDataEntity byId = kongTiaoDataService.getById(kongTiaoDataId);
        return new KongTiaoDataDTO(byId);
    }
}
