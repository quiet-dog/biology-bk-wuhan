package com.biology.domain.manage.xsData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.xsData.command.AddXsDataCommand;
import com.biology.domain.manage.xsData.command.UpdateXsDataCommand;
import com.biology.domain.manage.xsData.command.XsDataFun1DTO;
import com.biology.domain.manage.xsData.db.XsDataEntity;
import com.biology.domain.manage.xsData.db.XsDataService;
import com.biology.domain.manage.xsData.dto.XsDataDTO;
import com.biology.domain.manage.xsData.model.XsDataFactory;
import com.biology.domain.manage.xsData.model.XsDataModel;
import com.biology.domain.manage.xsData.query.XsDataQuery;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XsDataApplicationService {
    private final XsDataFactory xsDataFactory;

    private final XsDataService xsDataService;

    private final WebClient opcClient;

    public void create(AddXsDataCommand command) {
        XsDataModel xsDataModel = xsDataFactory.create();
        xsDataModel.loadAddXsDataCommand(command);
        xsDataModel.insert();
        return;
    }

    public void update(UpdateXsDataCommand command) {
        XsDataModel xsDataModel = xsDataFactory.loadById(command.getXsDataId());
        xsDataModel.loadUpdateXsDataCommand(command);
        xsDataModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> xsDataIds) {
        xsDataIds.forEach(xsDataId -> {
            XsDataModel xsDataModel = xsDataFactory.loadById(xsDataId);
            xsDataModel.deleteById();
        });
        return;
    }

    public PageDTO<XsDataDTO> getXsDatas(XsDataQuery query) {
        Page<XsDataEntity> page = xsDataService.page(query.toPage(), query.toQueryWrapper());
        List<XsDataDTO> records = page.getRecords().stream().map(XsDataDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public XsDataDTO getXsDataInfo(Long xsDataId) {
        XsDataEntity byId = xsDataService.getById(xsDataId);
        return new XsDataDTO(byId);
    }

    public void xiaoSha(List<XsDataFun1DTO> data) {
        long timestamp = System.currentTimeMillis();
        for (XsDataFun1DTO x : data) {
            CacheCenter.xsDataFun1Cache.set(x.getDeviceName(), x);
            QueryWrapper<XsDataEntity> qWrapper = new QueryWrapper<>();
            qWrapper.eq("device_sn", x.getDeviceName())
                    .orderByDesc("create_time")
                    .last("LIMIT 1");
            XsDataEntity xEntity = xsDataService.getBaseMapper().selectOne(qWrapper);
            if (xEntity == null || (xEntity != null && xEntity.getEndTime() != null)) {
                AddXsDataCommand aCommand = new AddXsDataCommand();
                aCommand.setDeviceSn(x.getDeviceName());
                create(aCommand);
            } else {
                if (x.getWorkStatus().equals(0)) {
                    xEntity.setEndTime(timestamp);
                    xEntity.updateById();
                }
            }

            QueryWrapper<XsDeviceEntity> xWrapper = new QueryWrapper<>();
            xWrapper.eq("device_sn", x.getDeviceName());
            XsDeviceEntity xEntity2 = new XsDeviceEntity().selectOne(xWrapper);
            if (xEntity2 != null) {
                xEntity2.setLastTime(timestamp);
                xEntity2.updateById();
            }

        }

        for (XsDataFun1DTO x : data) {
            sendLuanSheng(x);
        }

    }

    public void sendLuanSheng(XsDataFun1DTO data) {
        SendType sType = new SendType();
        sType.setType("xs_data");
        sType.setData(data);
        opcClient.post().uri("/api/ketisan")
                .bodyValue(sType)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }
}
