package com.biology.domain.manage.nongDuData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceEntity;
import com.biology.domain.manage.moni.dto.SendType;
import com.biology.domain.manage.nongDuData.command.AddNongDuDataCommand;
import com.biology.domain.manage.nongDuData.command.NongDuDTO;
import com.biology.domain.manage.nongDuData.command.UpdateNongDuDataCommand;
import com.biology.domain.manage.nongDuData.db.NongDuDataEntity;
import com.biology.domain.manage.nongDuData.db.NongDuDataService;
import com.biology.domain.manage.nongDuData.dto.NongDuDataDTO;
import com.biology.domain.manage.nongDuData.model.NongDuDataFactory;
import com.biology.domain.manage.nongDuData.model.NongDuDataModel;
import com.biology.domain.manage.nongDuData.query.NongDuDataLuanShengQuery;
import com.biology.domain.manage.nongDuData.query.NongDuDataQuery;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NongDuDataApplicationService {
    private final NongDuDataFactory nongDuDataFactory;

    private final NongDuDataService nongDuDataService;

    private final WebClient opcClient;

    public void create(AddNongDuDataCommand command) {
        NongDuDataModel nongDuDataModel = nongDuDataFactory.create();
        nongDuDataModel.loadAddNongDuDataCommand(command);
        nongDuDataModel.insert();
        return;
    }

    public void update(UpdateNongDuDataCommand command) {
        NongDuDataModel nongDuDataModel = nongDuDataFactory.loadById(command.getNongDuDataId());
        nongDuDataModel.loadUpdateNongDuDataCommand(command);
        nongDuDataModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> nongDuDataIds) {
        nongDuDataIds.forEach(nongDuDataId -> {
            NongDuDataModel nongDuDataModel = nongDuDataFactory.loadById(nongDuDataId);
            nongDuDataModel.deleteById();
        });
        return;
    }

    public PageDTO<NongDuDataDTO> getNongDuDatas(NongDuDataQuery query) {
        Page<NongDuDataEntity> page = nongDuDataService.page(query.toPage(), query.toQueryWrapper());
        List<NongDuDataDTO> records = page.getRecords().stream().map(NongDuDataDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public NongDuDataDTO getNongDuDataInfo(Long nongDuDataId) {
        NongDuDataEntity byId = nongDuDataService.getById(nongDuDataId);
        return new NongDuDataDTO(byId);
    }

    public void nongDuDateGet(NongDuDTO dataDTO) {
        if (dataDTO.getDeviceSn() == null) {
            dataDTO.setDeviceSn("deviceSn");
        }
        CacheCenter.jianCeDataCache.set(dataDTO.getDeviceSn(), dataDTO);

        AddNongDuDataCommand aCommand = new AddNongDuDataCommand();
        aCommand.setBiologicalConcentration(dataDTO.getBiologicalConcentrationToDouble());
        aCommand.setParticleConcentration(dataDTO.getParticleConcentrationToDouble());
        aCommand.setAlarm(dataDTO.getAlarm());
        aCommand.setWorkingStatus(dataDTO.getWorkingStatus());
        if (StrUtil.isEmpty(dataDTO.getDeviceSn())) {
            aCommand.setDeviceSn("deviceSn");
        }

        create(aCommand);
        sendLuanSheng(dataDTO);

        long timestamp = System.currentTimeMillis();
        UpdateWrapper<JianCeDeviceEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("device_sn", "deviceSn")
                .set("last_time", timestamp);
        new JianCeDeviceEntity().update(updateWrapper);
    }

    public void sendLuanSheng(NongDuDTO data) {
        SendType sType = new SendType();
        sType.setType("nd_data");
        sType.setData(data);
        opcClient.post().uri("/api/ketisan")
                .bodyValue(sType)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }

    public Object getNongDuDataOnlineHistory(NongDuDataLuanShengQuery query) {
        if (query.getDeviceSn() == null) {
            return nongDuDataService.selectIsOnlineHistoryIsNull(query.getStartTime(), query.getEndTime());
        } else {

            if (query.getDeviceSn() != null && query.getDeviceSn().equals("deviceSn")) {
                return nongDuDataService.selectIsOnlineHistoryIsNull(query.getStartTime(), query.getEndTime());
            }
            return nongDuDataService.selectIsOnlineHistory(query.getDeviceSn(), query.getStartTime(),
                    query.getEndTime());
        }
    }

    public Object getNongDuDataAlarmCount(String startTime, String endTime, List<String> deviceSn) {
        Map<String, Object> result = new HashMap<>();
        for (String sn : deviceSn) {
            Integer alarmCount = nongDuDataService.getAlarmCount(startTime, endTime, sn);
            result.put(sn, alarmCount);
        }
        return result;
    }

    public Map<String, Object> getJinRiBaoJing() {
        Map<String, Object> result = new HashMap<>();
        Integer num = nongDuDataService.getJinRiBaoJing();
        result.put("num", num);
        return result;
    }
}
