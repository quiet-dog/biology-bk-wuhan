package com.biology.domain.manage.smDevice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.smDevice.dto.SmDeviceDTO;
import com.biology.domain.manage.smDevice.command.AddSmDeviceCommand;
import com.biology.domain.manage.smDevice.command.UpdateSmDeviceCommand;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.smDevice.db.SmDeviceService;
import com.biology.domain.manage.smDevice.dto.SmDeviceDTO;
import com.biology.domain.manage.smDevice.model.SmDeviceModel;
import com.biology.domain.manage.smDevice.query.SmDeviceQuery;
import com.biology.domain.manage.smDevice.model.SmDeviceFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmDeviceApplicationService {

    private final SmDeviceFactory smDeviceFactory;

    private final SmDeviceService smDeviceService;

    public void create(AddSmDeviceCommand command) {
        SmDeviceModel smDeviceModel = smDeviceFactory.create();
        smDeviceModel.loadAddSmDeviceCommand(command);
        smDeviceModel.insert();
        return;
    }

    public void update(UpdateSmDeviceCommand command) {
        SmDeviceModel smDeviceModel = smDeviceFactory.loadById(command.getSmDeviceId());
        smDeviceModel.loadUpdateSmDeviceCommand(command);
        smDeviceModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> smDeviceIds) {
        smDeviceIds.forEach(smDeviceId -> {
            SmDeviceModel smDeviceModel = smDeviceFactory.loadById(smDeviceId);
            smDeviceModel.deleteById();
        });
        return;
    }

    public PageDTO<SmDeviceDTO> getSmDevices(SmDeviceQuery query) {
        Page<SmDeviceEntity> page = smDeviceService.page(query.toPage(), query.toQueryWrapper());
        List<SmDeviceDTO> records = page.getRecords().stream().map(SmDeviceDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public SmDeviceDTO getSmDeviceInfo(Long smDeviceId) {
        SmDeviceEntity byId = smDeviceService.getById(smDeviceId);
        return new SmDeviceDTO(byId);
    }

}
