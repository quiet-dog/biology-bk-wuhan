package com.biology.domain.manage.jianCeDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.jianCeDevice.command.AddJianCeDeviceCommand;
import com.biology.domain.manage.jianCeDevice.command.UpdateJianCeDeviceCommand;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceEntity;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceService;
import com.biology.domain.manage.jianCeDevice.dto.JianCeDeviceDTO;
import com.biology.domain.manage.jianCeDevice.model.JianCeDeviceFactory;
import com.biology.domain.manage.jianCeDevice.model.JianCeDeviceModel;
import com.biology.domain.manage.jianCeDevice.query.JianCeDeviceQuery;
import com.biology.domain.manage.nongDuData.command.NongDuDTO;
import com.biology.domain.manage.xsData.command.XsDataFun1DTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JianCeDeviceApplicationService {
    private final JianCeDeviceFactory jianCeDeviceFactory;

    private final JianCeDeviceService jianCeDeviceService;

    public void create(AddJianCeDeviceCommand command) {
        JianCeDeviceModel jianCeDeviceModel = jianCeDeviceFactory.create();
        jianCeDeviceModel.loadAddJianCeDeviceCommand(command);
        jianCeDeviceModel.insert();
        return;
    }

    public void update(UpdateJianCeDeviceCommand command) {
        JianCeDeviceModel jianCeDeviceModel = jianCeDeviceFactory.loadById(command.getJianCeDeviceId());
        jianCeDeviceModel.loadUpdateJianCeDeviceCommand(command);
        jianCeDeviceModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> jianCeDeviceIds) {
        jianCeDeviceIds.forEach(jianCeDeviceId -> {
            JianCeDeviceModel jianCeDeviceModel = jianCeDeviceFactory.loadById(jianCeDeviceId);
            jianCeDeviceModel.deleteById();
        });
        return;
    }

    public PageDTO<JianCeDeviceDTO> getJianCeDevices(JianCeDeviceQuery query) {
        Page<JianCeDeviceEntity> page = jianCeDeviceService.page(query.toPage(), query.toQueryWrapper());
        List<JianCeDeviceDTO> records = page.getRecords().stream().map(JianCeDeviceDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public JianCeDeviceDTO getJianCeDeviceInfo(Long jianCeDeviceId) {
        JianCeDeviceEntity byId = jianCeDeviceService.getById(jianCeDeviceId);
        return new JianCeDeviceDTO(byId);
    }

    public Map<String, Object> getOnlineCount() {
        Map<String, Object> result = new HashMap<>();
        long count = jianCeDeviceService.count();
        List<Map<String, Object>> series = new ArrayList<>();
        Map<String, Object> onlineMap = new HashMap<>();
        Map<String, Object> notOnlineMap = new HashMap<>();
        long online = 0;
        long notOnline = 0;

        List<NongDuDTO> list = CacheCenter.jianCeDataCache.getAllCache();
        for (NongDuDTO r : list) {
            online++;
        }
        notOnline = count - online;
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
