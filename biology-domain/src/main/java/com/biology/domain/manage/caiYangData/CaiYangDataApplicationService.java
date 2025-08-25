package com.biology.domain.manage.caiYangData;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.caiYangData.command.AddCaiYangDataCommand;
import com.biology.domain.manage.caiYangData.command.UpdateCaiYangDataCommand;
import com.biology.domain.manage.caiYangData.db.CaiYangDataEntity;
import com.biology.domain.manage.caiYangData.db.CaiYangDataService;
import com.biology.domain.manage.caiYangData.dto.CaiYangDataDTO;
import com.biology.domain.manage.caiYangData.dto.CaiYangFunDTO;
import com.biology.domain.manage.caiYangData.model.CaiYangDataFactory;
import com.biology.domain.manage.caiYangData.model.CaiYangDataModel;
import com.biology.domain.manage.caiYangData.query.CaiYangDataQuery;
import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CaiYangDataApplicationService {

    private final CaiYangDataFactory caiYangDataFactory;

    private final CaiYangDataService caiYangDataService;

    private final WebClient opcClient;

    public void create(AddCaiYangDataCommand command) {
        CaiYangDataModel caiYangDataModel = caiYangDataFactory.create();
        caiYangDataModel.loadAddCaiYangDataCommand(command);
        caiYangDataModel.insert();
        return;
    }

    public void update(UpdateCaiYangDataCommand command) {
        CaiYangDataModel caiYangDataModel = caiYangDataFactory.loadById(command.getCaiYangDataId());
        caiYangDataModel.loadUpdateCaiYangDataCommand(command);
        caiYangDataModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> caiYangDataIds) {
        caiYangDataIds.forEach(caiYangDataId -> {
            CaiYangDataModel caiYangDataModel = caiYangDataFactory.loadById(caiYangDataId);
            caiYangDataModel.deleteById();
        });
        return;
    }

    public PageDTO<CaiYangDataDTO> getCaiYangDatas(CaiYangDataQuery query) {
        Page<CaiYangDataEntity> page = caiYangDataService.page(query.toPage(), query.toQueryWrapper());
        List<CaiYangDataDTO> records = page.getRecords().stream().map(CaiYangDataDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public CaiYangDataDTO getCaiYangDataInfo(Long caiYangDataId) {
        CaiYangDataEntity byId = caiYangDataService.getById(caiYangDataId);
        return new CaiYangDataDTO(byId);
    }

    public void getCaiYangSend(List<CaiYangFunDTO> cDto) {
        long timestamp = System.currentTimeMillis();
        for (CaiYangFunDTO c : cDto) {
            CacheCenter.caiYangFunCache.set(c.getDeviceName(), c);

            AddCaiYangDataCommand command = new AddCaiYangDataCommand();
            command.setDeviceSn(c.getDeviceName());
            QueryWrapper<CaiYangDataEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("device_sn", c.getDeviceName()).orderByDesc("create_time")
                    .last("LIMIT 1");
            CaiYangDataEntity entity = caiYangDataService.getBaseMapper().selectOne(queryWrapper);
            if (entity == null || (entity != null && entity.getEndTime() != null)) {
                create(command);
            } else {
                if (c.getWorkStatus().equals(2)) {
                    entity.setEndTime(timestamp);
                    entity.updateById();
                }
            }

            QueryWrapper<NongDuDeviceEntity> nQueryWrapper = new QueryWrapper<>();
            nQueryWrapper.eq("device_sn", c.getDeviceName());
            NongDuDeviceEntity nEntity = new NongDuDeviceEntity().selectOne(nQueryWrapper);
            if (nEntity != null) {
                nEntity.setLastTime(timestamp);
                nEntity.updateById();
            }
        }
        for (CaiYangFunDTO c : cDto) {
            sendLuanSheng(c);
        }
    }

    public void sendLuanSheng(CaiYangFunDTO data) {
        SendType sType = new SendType();
        sType.setType("cy_data");
        sType.setData(data);
        opcClient.post().uri("/api/ketisan")
                .bodyValue(sType)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }
}
