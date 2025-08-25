package com.biology.domain.manage.smThreshold;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.smThreshold.command.AddSmThresholdAll;
import com.biology.domain.manage.smThreshold.command.AddSmThresholdAllReq;
import com.biology.domain.manage.smThreshold.command.AddSmThresholdCommand;
import com.biology.domain.manage.smThreshold.db.SmThresholdEntity;
import com.biology.domain.manage.smThreshold.db.SmThresholdService;
import com.biology.domain.manage.smThreshold.dto.SmThresholdResDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmThresholdApplicationService {

    private final SmThresholdService smThresholdService;

    public void create(AddSmThresholdAllReq req) {
        List<SmThresholdEntity> list = new ArrayList<>();
        for (AddSmThresholdAll item : req.getData()) {
            for (AddSmThresholdCommand threshold : item.getValues()) {
                SmThresholdEntity sEntity = new SmThresholdEntity();
                sEntity.setLevel(threshold.getLevel());
                sEntity.setMax(threshold.getMax());
                sEntity.setMin(threshold.getMin());
                sEntity.setSmDeviceId(req.getId());
                sEntity.setType(threshold.getType());
                list.add(sEntity);
            }
        }

        QueryWrapper<SmThresholdEntity> qWrapper = new QueryWrapper<>();
        qWrapper.eq("sm_device_id", req.getId());
        smThresholdService.remove(qWrapper);
        smThresholdService.saveBatch(list);
    }

    public List<SmThresholdResDTO> get(Long id) {
        QueryWrapper<SmThresholdEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sm_device_id", id);

        List<SmThresholdEntity> list = smThresholdService.list(queryWrapper);

        List<SmThresholdResDTO> result = new ArrayList<>();
        SmThresholdResDTO xinlv = new SmThresholdResDTO();
        xinlv.setType("心率");
        xinlv.setData(new ArrayList<>());
        SmThresholdResDTO xueYang = new SmThresholdResDTO();
        xueYang.setType("血氧");
        xueYang.setData(new ArrayList<>());
        SmThresholdResDTO hum = new SmThresholdResDTO();
        hum.setType("体温");
        hum.setData(new ArrayList<>());
        SmThresholdResDTO co2 = new SmThresholdResDTO();
        co2.setType("co2");
        co2.setData(new ArrayList<>());
        SmThresholdResDTO huXi = new SmThresholdResDTO();
        huXi.setType("呼吸");
        huXi.setData(new ArrayList<>());
        SmThresholdResDTO xinDian = new SmThresholdResDTO();
        xinDian.setType("心电");
        xinDian.setData(new ArrayList<>());
        for (SmThresholdEntity item : list) {
            if (item.getType().equals("心率")) {
                xinlv.getData().add(item);
            }
            if (item.getType().equals("血氧")) {
                xueYang.getData().add(item);
            }
            if (item.getType().equals("体温")) {
                hum.getData().add(item);
            }
            if (item.getType().equals("co2")) {
                co2.getData().add(item);
            }
            if (item.getType().equals("呼吸")) {
                huXi.getData().add(item);
            }
            if (item.getType().equals("心电")) {
                xinDian.getData().add(item);
            }
        }

        result.add(xinlv);
        result.add(xueYang);
        result.add(hum);
        result.add(co2);
        result.add(huXi);
        result.add(xinDian);
        return result;
    }
}
