package com.biology.domain.manage.door.model;

import org.springframework.stereotype.Component;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.door.db.DoorEntity;
import com.biology.domain.manage.door.db.DoorService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DoorFactory {

    private final DoorService doorService;

    public DoorModel create() {
        return new DoorModel(doorService);
    }

    public DoorModel loadById(Long doorId) {
        DoorEntity doorModel = doorService.getById(doorId);
        if (doorModel == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, doorId, "门禁");
        }
        return new DoorModel(doorModel, doorService);
    }
}
