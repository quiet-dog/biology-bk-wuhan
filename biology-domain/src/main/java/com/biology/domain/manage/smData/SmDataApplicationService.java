package com.biology.domain.manage.smData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.smAlarm.SmAlarmApplicationService;
import com.biology.domain.manage.smAlarm.command.AddSmAlarmCommand;
import com.biology.domain.manage.smData.command.AddSmDataCommand;
import com.biology.domain.manage.smData.command.UpdateSmDataCommand;
import com.biology.domain.manage.smData.db.SmDataEntity;
import com.biology.domain.manage.smData.db.SmDataService;
import com.biology.domain.manage.smData.dto.SmDataDTO;
import com.biology.domain.manage.smData.model.SmDataFactory;
import com.biology.domain.manage.smData.model.SmDataModel;
import com.biology.domain.manage.smData.query.SmDataLuanShengQuiery;
import com.biology.domain.manage.smData.query.SmDataQuery;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.smDevice.db.SmDeviceService;
import com.biology.domain.manage.smThreshold.db.SmThresholdEntity;
import com.biology.domain.manage.smThreshold.db.SmThresholdService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmDataApplicationService {
    private final SmDataFactory smDataFactory;

    private final SmDataService smDataService;

    private final SmThresholdService smThresholdService;

    private final SmAlarmApplicationService smAlarmApplicationService;

    private final SmDeviceService smDeviceService;

    public void create(AddSmDataCommand command) {
        SmDataModel smDataModel = smDataFactory.create();
        smDataModel.loadAddSmDataCommand(command);
        smDataModel.insert();

        QueryWrapper<SmDeviceEntity> smDeviceQueryWrapper = new QueryWrapper<>();
        smDeviceQueryWrapper.eq("sm_device_id", command.getSmDeviceId());
        SmDeviceEntity smDeviceEntity = smDeviceService.getOne(smDeviceQueryWrapper);

        QueryWrapper<SmThresholdEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sm_device_id", command.getSmDeviceId());
        List<SmThresholdEntity> smThresholdEntity = smThresholdService.list(queryWrapper);
        /**
         * xData.add("心率");
         * xData.add("血氧");
         * xData.add("体温");
         * xData.add("co2");
         * xData.add("呼吸");
         * xData.add("心电");
         */

        // if (command.getSmDeviceId() == null) {
        // return;
        // }
        // for (SmThresholdEntity a : smThresholdEntity) {

        // AddSmAlarmCommand addSmAlarmCommand = new AddSmAlarmCommand();
        // if (a.getType().equals("心率")) {
        // if (command.getXinlv() > a.getMin() && command.getXinlv() < a.getMax()) {
        // System.out.println("心率异常");
        // addSmAlarmCommand.setType("心率");
        // addSmAlarmCommand.setDescription("心率异常");
        // addSmAlarmCommand.setDeviceSn(command.getSmDeviceId().toString());
        // smAlarmApplicationService.create(addSmAlarmCommand);
        // }
        // }
        // if (a.getType().equals("血氧")) {
        // if (command.getXueYang() > a.getMin() && command.getXueYang() < a.getMax()) {
        // addSmAlarmCommand.setType("血氧");
        // addSmAlarmCommand.setDescription("血氧异常");
        // addSmAlarmCommand.setDeviceSn(command.getSmDeviceId().toString());
        // smAlarmApplicationService.create(addSmAlarmCommand);
        // }
        // }
        // if (a.getType().equals("体温")) {
        // if (command.getTemp() > a.getMin() && command.getTemp() < a.getMax()) {
        // addSmAlarmCommand.setType("体温");
        // addSmAlarmCommand.setDescription("体温异常");
        // addSmAlarmCommand.setDeviceSn(command.getSmDeviceId().toString());
        // smAlarmApplicationService.create(addSmAlarmCommand);
        // }
        // }
        // if (a.getType().equals("co2")) {
        // if (command.getCo2() > a.getMin() && command.getCo2() < a.getMax()) {
        // addSmAlarmCommand.setType("co2");
        // addSmAlarmCommand.setDescription("co2异常");
        // addSmAlarmCommand.setDeviceSn(command.getSmDeviceId().toString());
        // smAlarmApplicationService.create(addSmAlarmCommand);
        // }
        // }

        // if (a.getType().equals("心电") && command.getXinDian() != null &&
        // command.getXinDian().size() > 0) {
        // // 取平均值
        // double average =
        // command.getXinDian().stream().mapToDouble(Number::doubleValue).average().orElse(0);
        // if (average > a.getMin() && average < a.getMax()) {
        // addSmAlarmCommand.setType("心电");
        // addSmAlarmCommand.setDescription("心电异常");
        // addSmAlarmCommand.setDeviceSn(command.getSmDeviceId().toString());
        // smAlarmApplicationService.create(addSmAlarmCommand);
        // }
        // }
        // if (a.getType().equals("呼吸") && command.getHuxi() != null &&
        // command.getHuxi().size() > 0) {
        // // 取平均值
        // double average =
        // command.getHuxi().stream().mapToDouble(Number::doubleValue).average().orElse(0);
        // if (average > a.getMin() && average < a.getMax()) {
        // addSmAlarmCommand.setType("呼吸");
        // addSmAlarmCommand.setDescription("呼吸异常");
        // addSmAlarmCommand.setDeviceSn(command.getSmDeviceId().toString());
        // smAlarmApplicationService.create(addSmAlarmCommand);
        // }
        // }
        // }

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

    // 获取某天的历史数据
    public Object getSmDataHistory(SmDataLuanShengQuiery query) {

        Map<String, Object> result = new HashMap<>();
        // 执行sql,查询某一天的历史数据,每1h获取平均值,根据传的点位类型,获取不同的数据
        for (String dianWei : query.getDianWeis()) {
            if (dianWei.equals("battery")) {
                result.put("battery", smDataService.selectBatteryHistory(query.getSmDeviceId(), query.getStartTime(),
                        query.getEndTime()));
            } else if (dianWei.equals("co2")) {
                result.put("co2", smDataService.selectCo2History(query.getSmDeviceId(), query.getStartTime(),
                        query.getEndTime()));
            } else if (dianWei.equals("humility")) {
                result.put("humility", smDataService.selectHumilityHistory(query.getSmDeviceId(), query.getStartTime(),
                        query.getEndTime()));
            } else if (dianWei.equals("temp")) {
                result.put("temp", smDataService.selectTempHistory(query.getSmDeviceId(), query.getStartTime(),
                        query.getEndTime()));
            } else if (dianWei.equals("xinlv")) {
                result.put("xinlv", smDataService.selectXinlvHistory(query.getSmDeviceId(), query.getStartTime(),
                        query.getEndTime()));
            } else if (dianWei.equals("xueyang")) {
                result.put("xueyang", smDataService.selectXueYangHistory(query.getSmDeviceId(), query.getStartTime(),
                        query.getEndTime()));
            }

        }

        return result;
    }

    // 获取某天的在线状态
    public Object getSmDataOnlineHistory(SmDataLuanShengQuiery query) {
        return smDataService.selectIsOnlineHistory(query.getSmDeviceId(), query.getStartTime(), query.getEndTime());
    }

}
