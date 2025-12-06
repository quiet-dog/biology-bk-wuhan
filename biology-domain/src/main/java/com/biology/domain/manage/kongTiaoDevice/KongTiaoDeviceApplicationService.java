package com.biology.domain.manage.kongTiaoDevice;

import java.util.List;

import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import com.biology.common.core.page.PageDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataEntity;
import com.biology.domain.manage.kongTiaoData.db.KongTiaoDataService;
import com.biology.domain.manage.kongTiaoData.dto.KongTiaoDataDTO;
import com.biology.domain.manage.kongTiaoDevice.command.AddKongTiaoDeviceCommand;
import com.biology.domain.manage.kongTiaoDevice.command.UpdateKongTiaoDeviceCommand;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceEntity;
import com.biology.domain.manage.kongTiaoDevice.db.KongTiaoDeviceService;
import com.biology.domain.manage.kongTiaoDevice.dto.KongTiaoDeviceDTO;
import com.biology.domain.manage.kongTiaoDevice.model.KongTiaoDeviceFactory;
import com.biology.domain.manage.kongTiaoDevice.model.KongTiaoDeviceModel;
import com.biology.domain.manage.kongTiaoDevice.query.KongTiaoDeviceQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KongTiaoDeviceApplicationService {
    private final KongTiaoDeviceFactory kongTiaoDeviceFactory;

    private final KongTiaoDeviceService kongTiaoDeviceService;

    private final KongTiaoDataService kongTiaoDataService;

    public void create(AddKongTiaoDeviceCommand command) {
        KongTiaoDeviceModel kongTiaoDeviceModel = kongTiaoDeviceFactory.create();
        kongTiaoDeviceModel.loadAddKongTiaoDeviceCommand(command);
        kongTiaoDeviceModel.insert();
        return;
    }

    public void update(UpdateKongTiaoDeviceCommand command) {
        KongTiaoDeviceModel kongTiaoDeviceModel = kongTiaoDeviceFactory.loadById(command.getKongTiaoDeviceId());
        kongTiaoDeviceModel.loadUpdateKongTiaoDeviceCommand(command);
        kongTiaoDeviceModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> kongTiaoDeviceIds) {
        kongTiaoDeviceIds.forEach(kongTiaoDeviceId -> {
            KongTiaoDeviceModel kongTiaoDeviceModel = kongTiaoDeviceFactory.loadById(kongTiaoDeviceId);
            kongTiaoDeviceModel.deleteById();
        });
        return;
    }

    public PageDTO<KongTiaoDeviceDTO> getKongTiaoDevices(KongTiaoDeviceQuery query) {
        Page<KongTiaoDeviceEntity> page = kongTiaoDeviceService.page(query.toPage(), query.toQueryWrapper());
        List<KongTiaoDeviceDTO> records = page.getRecords().stream().map(KongTiaoDeviceDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public KongTiaoDeviceDTO getKongTiaoDeviceInfo(Long kongTiaoDeviceId) {
        KongTiaoDeviceEntity byId = kongTiaoDeviceService.getById(kongTiaoDeviceId);
        return new KongTiaoDeviceDTO(byId);
    }

    public KongTiaoDataDTO getKongTiaoDataFirst(String deviceSn) {
        // 查询最后一条的数据
        QueryWrapper<KongTiaoDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_sn", deviceSn).last("LIMIT 1");
        KongTiaoDataEntity one = kongTiaoDataService.getBaseMapper().selectOne(queryWrapper);
        return new KongTiaoDataDTO(one);
    }
}
