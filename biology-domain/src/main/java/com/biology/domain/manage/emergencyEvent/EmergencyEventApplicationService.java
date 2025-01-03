package com.biology.domain.manage.emergencyEvent;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.emergencyEvent.command.AddEmergencyEventCommand;
import com.biology.domain.manage.emergencyEvent.command.UpdateEmergencyEventCommand;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventEntity;
import com.biology.domain.manage.emergencyEvent.db.EmergencyEventService;
import com.biology.domain.manage.emergencyEvent.dto.EmergencyEventDTO;
import com.biology.domain.manage.emergencyEvent.dto.EmergencyEventStaticDTO;
import com.biology.domain.manage.emergencyEvent.model.EmergencyEventFactory;
import com.biology.domain.manage.emergencyEvent.model.EmergencyEventModel;
import com.biology.domain.manage.emergencyEvent.query.EmergencyEventQuery;
import com.biology.domain.manage.event.dto.EventEchartDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmergencyEventApplicationService {
    private final EmergencyEventFactory emergencyEventFactory;

    private final EmergencyEventService emergencyEventService;

    public void addEmergencyEvent(AddEmergencyEventCommand command) {
        EmergencyEventModel emergencyEventModel = emergencyEventFactory.create();
        emergencyEventModel.loadAddEmergencyEventCommand(command);
        emergencyEventModel.insert();
    }

    public void updateEmergencyEvent(UpdateEmergencyEventCommand command) {
        EmergencyEventModel emergencyEventModel = emergencyEventFactory.loadById(command.getEmergencyEventId());
        emergencyEventModel.loadUpdateEmergencyEventCommand(command);
        emergencyEventModel.updateById();
    }

    public void deleteEmergencyEvents(List<Long> emergencyEventIds) {
        for (Long emergencyEventId : emergencyEventIds) {
            deleteEmergencyEvent(emergencyEventId);
        }
    }

    public void deleteEmergencyEvent(Long emergencyEventId) {
        EmergencyEventModel emergencyEventModel = emergencyEventFactory.loadById(emergencyEventId);
        emergencyEventModel.deleteById();
    }

    public EmergencyEventDTO getEmergencyEvent(Long emergencyEventId) {
        EmergencyEventModel emergencyEventModel = emergencyEventFactory.loadById(emergencyEventId);
        return new EmergencyEventDTO(emergencyEventModel);
    }

    public PageDTO<EmergencyEventDTO> searchEmergencyEvents(EmergencyEventQuery query) {
        Page<EmergencyEventEntity> page = emergencyEventService.page(query.toPage(), query.toQueryWrapper());
        List<EmergencyEventDTO> records = page.getRecords().stream().map(EmergencyEventDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public EventEchartDTO getStock(EmergencyEventStaticDTO query) {
        return emergencyEventService.getStock(query);
    }
}
