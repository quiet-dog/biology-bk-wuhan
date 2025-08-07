package com.biology.domain.manage.xwDevice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.xwDevice.command.AddXwDeviceCommand;
import com.biology.domain.manage.xwDevice.command.UpdateXwDeviceCommand;
import com.biology.domain.manage.xwDevice.db.XwDeviceEntity;
import com.biology.domain.manage.xwDevice.db.XwDeviceService;
import com.biology.domain.manage.xwDevice.dto.XwDeviceDTO;
import com.biology.domain.manage.xwDevice.model.XwDeviceFactory;
import com.biology.domain.manage.xwDevice.model.XwDeviceModel;
import com.biology.domain.manage.xwDevice.query.XwDeviceQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XwDeviceApplicationService {
    private final XwDeviceFactory xwDeviceFactory;

    private final XwDeviceService xwDeviceService;

    public void create(AddXwDeviceCommand command) {
        XwDeviceModel xwDeviceModel = xwDeviceFactory.create();
        xwDeviceModel.loadAddXwDeviceCommand(command);
        xwDeviceModel.insert();
        return;
    }

    public void update(UpdateXwDeviceCommand command) {
        XwDeviceModel xwDeviceModel = xwDeviceFactory.loadById(command.getXwDeviceId());
        xwDeviceModel.loadUpdateXwDeviceCommand(command);
        xwDeviceModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> xwDeviceIds) {
        xwDeviceIds.forEach(xwDeviceId -> {
            XwDeviceModel xwDeviceModel = xwDeviceFactory.loadById(xwDeviceId);
            xwDeviceModel.deleteById();
        });
        return;
    }

    public PageDTO<XwDeviceDTO> getXwDevices(XwDeviceQuery query) {
        Page<XwDeviceEntity> page = xwDeviceService.page(query.toPage(), query.toQueryWrapper());
        List<XwDeviceDTO> records = page.getRecords().stream().map(XwDeviceDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public XwDeviceDTO getXwDeviceInfo(Long xwDeviceId) {
        XwDeviceEntity byId = xwDeviceService.getById(xwDeviceId);
        return new XwDeviceDTO(byId);
    }
}
