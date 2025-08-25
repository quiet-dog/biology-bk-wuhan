package com.biology.domain.manage.xsDevice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.xsData.command.XsDataFun1DTO;
import com.biology.domain.manage.xsDevice.command.AddXsDeviceCommand;
import com.biology.domain.manage.xsDevice.command.UpdateXsDeviceCommand;
import com.biology.domain.manage.xsDevice.db.XsDeviceEntity;
import com.biology.domain.manage.xsDevice.dto.XsDeviceDTO;
import com.biology.domain.manage.xsDevice.model.XsDeviceModel;
import com.biology.domain.manage.xsDevice.query.XsDeviceQuery;
import com.biology.domain.manage.xsDevice.db.XsDeviceService;
import com.biology.domain.manage.xsDevice.model.XsDeviceFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XsDeviceApplicationService {
    private final XsDeviceFactory xsDeviceFactory;

    private final XsDeviceService xsDeviceService;

    public void create(AddXsDeviceCommand command) {
        XsDeviceModel xsDeviceModel = xsDeviceFactory.create();
        xsDeviceModel.loadAddXsDeviceCommand(command);
        xsDeviceModel.insert();
        return;
    }

    public void update(UpdateXsDeviceCommand command) {
        XsDeviceModel xsDeviceModel = xsDeviceFactory.loadById(command.getXsDeviceId());
        xsDeviceModel.loadUpdateXsDeviceCommand(command);
        xsDeviceModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> xsDeviceIds) {
        xsDeviceIds.forEach(xsDeviceId -> {
            XsDeviceModel xsDeviceModel = xsDeviceFactory.loadById(xsDeviceId);
            xsDeviceModel.deleteById();
        });
        return;
    }

    public PageDTO<XsDeviceDTO> getXsDevices(XsDeviceQuery query) {
        Page<XsDeviceEntity> page = xsDeviceService.page(query.toPage(), query.toQueryWrapper());
        List<XsDeviceDTO> records = page.getRecords().stream().map(XsDeviceDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public XsDeviceDTO getXsDeviceInfo(Long xsDeviceId) {
        XsDeviceEntity byId = xsDeviceService.getById(xsDeviceId);
        return new XsDeviceDTO(byId);
    }

    public Map<String, Object> getOnlineCount() {
        Map<String, Object> result = new HashMap<>();
        long count = xsDeviceService.count();
        List<Map<String, Object>> series = new ArrayList<>();
        Map<String, Object> onlineMap = new HashMap<>();
        Map<String, Object> notOnlineMap = new HashMap<>();
        long online = 0;
        long notOnline = 0;

        List<XsDataFun1DTO> list = CacheCenter.xsDataFun1Cache.getAllCache();
        for (XsDataFun1DTO r : list) {
            if (r.getOnline() != null && r.getOnline().equals(1)) {
                online++;
            }
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
