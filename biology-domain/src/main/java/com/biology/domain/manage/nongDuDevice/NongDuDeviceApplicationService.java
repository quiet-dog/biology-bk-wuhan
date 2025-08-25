package com.biology.domain.manage.nongDuDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.caiYangData.dto.CaiYangFunDTO;
import com.biology.domain.manage.nongDuDevice.command.AddNongDuDeviceCommand;
import com.biology.domain.manage.nongDuDevice.command.UpdateNongDuDeviceCommand;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceEntity;
import com.biology.domain.manage.nongDuDevice.db.NongDuDeviceService;
import com.biology.domain.manage.nongDuDevice.dto.NongDuDeviceDTO;
import com.biology.domain.manage.nongDuDevice.model.NongDuDeviceFactory;
import com.biology.domain.manage.nongDuDevice.model.NongDuDeviceModel;
import com.biology.domain.manage.nongDuDevice.query.NongDuDeviceQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NongDuDeviceApplicationService {
    private final NongDuDeviceFactory nongDuDeviceFactory;

    private final NongDuDeviceService nongDuDeviceService;

    public void create(AddNongDuDeviceCommand command) {
        NongDuDeviceModel nongDuDeviceModel = nongDuDeviceFactory.create();
        nongDuDeviceModel.loadAddNongDuDeviceCommand(command);
        nongDuDeviceModel.insert();
        return;
    }

    public void update(UpdateNongDuDeviceCommand command) {
        NongDuDeviceModel nongDuDeviceModel = nongDuDeviceFactory.loadById(command.getNongDuDeviceId());
        nongDuDeviceModel.loadUpdateNongDuDeviceCommand(command);
        nongDuDeviceModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> nongDuDeviceIds) {
        nongDuDeviceIds.forEach(nongDuDeviceId -> {
            NongDuDeviceModel nongDuDeviceModel = nongDuDeviceFactory.loadById(nongDuDeviceId);
            nongDuDeviceModel.deleteById();
        });
        return;
    }

    public PageDTO<NongDuDeviceDTO> getNongDuDevices(NongDuDeviceQuery query) {
        Page<NongDuDeviceEntity> page = nongDuDeviceService.page(query.toPage(), query.toQueryWrapper());
        List<NongDuDeviceDTO> records = page.getRecords().stream().map(NongDuDeviceDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public NongDuDeviceDTO getNongDuDeviceInfo(Long nongDuDeviceId) {
        NongDuDeviceEntity byId = nongDuDeviceService.getById(nongDuDeviceId);
        return new NongDuDeviceDTO(byId);
    }

    public Map<String, Object> getOnlineCount() {
        Map<String, Object> result = new HashMap<>();
        long count = nongDuDeviceService.count();

        List<CaiYangFunDTO> list = CacheCenter.caiYangFunCache.getAllCache();
        long online = 0;
        long notOnline = 0;

        for (CaiYangFunDTO r : list) {
            if (r.getOnline() != null && r.getOnline().equals(1)) {
                online++;
            }
        }
        notOnline = count - online;

        List<Map<String, Object>> series = new ArrayList<>();
        Map<String, Object> onlineMap = new HashMap<>();
        Map<String, Object> notOnlineMap = new HashMap<>();
        onlineMap.put("value", online);
        onlineMap.put("name", "在线");

        notOnlineMap.put("value", notOnline);
        notOnlineMap.put("name", "离线");

        series.add(notOnlineMap);
        series.add(onlineMap);

        result.put("series", series);

        return result;
    }
}
