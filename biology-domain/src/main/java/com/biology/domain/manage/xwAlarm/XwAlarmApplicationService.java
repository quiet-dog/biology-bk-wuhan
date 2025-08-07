package com.biology.domain.manage.xwAlarm;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.xwAlarm.command.AddXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.command.UpdateXwAlarmCommand;
import com.biology.domain.manage.xwAlarm.db.XwAlarmEntity;
import com.biology.domain.manage.xwAlarm.db.XwAlarmService;
import com.biology.domain.manage.xwAlarm.dto.XwAlarmDTO;
import com.biology.domain.manage.xwAlarm.model.XwAlarmFactory;
import com.biology.domain.manage.xwAlarm.model.XwAlarmModel;
import com.biology.domain.manage.xwAlarm.query.XwAlarmQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XwAlarmApplicationService {
    private final XwAlarmFactory xwAlarmFactory;

    private final XwAlarmService xwAlarmService;

    public void create(AddXwAlarmCommand command) {
        XwAlarmModel xwAlarmModel = xwAlarmFactory.create();
        xwAlarmModel.loadAddXwAlarmCommand(command);
        xwAlarmModel.insert();
        return;
    }

    public void update(UpdateXwAlarmCommand command) {
        XwAlarmModel xwAlarmModel = xwAlarmFactory.loadById(command.getXwAlarmId());
        xwAlarmModel.loadUpdateXwAlarmCommand(command);
        xwAlarmModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> xwAlarmIds) {
        xwAlarmIds.forEach(xwAlarmId -> {
            XwAlarmModel xwAlarmModel = xwAlarmFactory.loadById(xwAlarmId);
            xwAlarmModel.deleteById();
        });
        return;
    }

    public PageDTO<XwAlarmDTO> getXwAlarms(XwAlarmQuery query) {
        Page<XwAlarmEntity> page = xwAlarmService.page(query.toPage(), query.toQueryWrapper());
        List<XwAlarmDTO> records = page.getRecords().stream().map(XwAlarmDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public XwAlarmDTO getXwAlarmInfo(Long xwAlarmId) {
        XwAlarmEntity byId = xwAlarmService.getById(xwAlarmId);
        return new XwAlarmDTO(byId);
    }
}
