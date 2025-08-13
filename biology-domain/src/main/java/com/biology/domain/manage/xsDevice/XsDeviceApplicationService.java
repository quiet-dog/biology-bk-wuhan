package com.biology.domain.manage.xsDevice;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
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

}
