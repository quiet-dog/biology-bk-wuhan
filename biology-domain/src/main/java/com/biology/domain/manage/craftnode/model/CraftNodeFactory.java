package com.biology.domain.manage.craftnode.model;

import org.springframework.stereotype.Component;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;
import com.biology.domain.manage.craftnode.db.CraftNodeEquipmentService;
import com.biology.domain.manage.craftnode.db.CraftNodeService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CraftNodeFactory {

    private final CraftNodeService craftNodeService;
    private final CraftNodeEquipmentService craftNodeEquipmentService;


    public CraftNodeModel create() {
        return new CraftNodeModel(craftNodeService, craftNodeEquipmentService);
    }

    public CraftNodeModel loadById(Long nodeId) {
        CraftNodeEntity entity = craftNodeService.getById(nodeId);
        if (entity == null) {
            throw new ApiException(Business.COMMON_OBJECT_NOT_FOUND, nodeId, "工艺节点");
        }
        return new CraftNodeModel(entity, craftNodeService, craftNodeEquipmentService);
    }
}
