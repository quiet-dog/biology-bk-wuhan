package com.biology.domain.manage.receive;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.receive.command.AddReceiveCommand;
import com.biology.domain.manage.receive.command.UpdateReceiveCommand;
import com.biology.domain.manage.receive.db.ReceiveEntity;
import com.biology.domain.manage.receive.db.ReceiveService;
import com.biology.domain.manage.receive.dto.ReceiveDTO;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsAllEchart;
import com.biology.domain.manage.receive.dto.ReceiveMaterialsStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockDTO;
import com.biology.domain.manage.receive.dto.ReceiveStockEchart;
import com.biology.domain.manage.receive.dto.ReceiveZhuDTO;
import com.biology.domain.manage.receive.model.ReceiveFactory;
import com.biology.domain.manage.receive.model.ReceiveModel;
import com.biology.domain.manage.receive.query.ReceiveMaterialsStockQuery;
import com.biology.domain.manage.receive.query.ReceiveMaterialsTypeQuery;
import com.biology.domain.manage.receive.query.ReceiveQuery;
import com.biology.domain.manage.receive.query.ReceiveStockQuery;
import com.biology.domain.manage.receive.query.ScreenReceiveMaterialsStockQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiveApplicationService {

    private final ReceiveFactory receiveFactory;

    private final ReceiveService receiveService;

    public void create(AddReceiveCommand command) {
        ReceiveModel receiveModel = receiveFactory.create();
        receiveModel.loadAddReceiveCommand(command);
        receiveModel.insert();
        return;
    }

    public void update(UpdateReceiveCommand command) {
        ReceiveModel receiveModel = receiveFactory.loadById(command.getReceiveId());
        receiveModel.loadUpdateReceiveCommand(command);
        receiveModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> receiveIds) {
        receiveIds.forEach(receiveId -> {
            ReceiveModel receiveModel = receiveFactory.loadById(receiveId);
            receiveModel.deleteById();
        });
        return;
    }

    public PageDTO<ReceiveDTO> getReceives(ReceiveQuery query) {
        Page<ReceiveEntity> page = receiveService.page(query.toPage(), query.toQueryWrapper());
        List<ReceiveDTO> records = page.getRecords().stream().map(ReceiveDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public ReceiveDTO getReceiveInfo(Long receiveId) {
        ReceiveEntity byId = receiveService.getById(receiveId);
        return new ReceiveDTO(byId);
    }

    public void stockReceive(Long receiveId) {
        ReceiveModel receiveModel = receiveFactory.loadById(receiveId);
    }

    public ReceiveStockEchart getReceiveStock(ReceiveStockQuery receiveStockDTO) {
        return receiveService.getReceiveStock(receiveStockDTO);
    }

    public List<ReceiveMaterialsStockDTO> getReceiveStockByName(ReceiveMaterialsStockQuery receiveMaterialsStockQuery) {
        if (receiveMaterialsStockQuery.getMaterialsName() == null
                || receiveMaterialsStockQuery.getMaterialsName().isEmpty()) {
            return null;
        }
        if (receiveMaterialsStockQuery.getDateType().equals("week")) {
            return receiveService.getReceiveStockWeekByName(receiveMaterialsStockQuery.getMaterialsName());
        } else if (receiveMaterialsStockQuery.getDateType().equals("month")) {
            return receiveService.getReceiveStockMonthByName(receiveMaterialsStockQuery.getMaterialsName());
        } else if (receiveMaterialsStockQuery.getDateType().equals("year")) {
            return receiveService.getReceiveStockYearByName(receiveMaterialsStockQuery.getMaterialsName());
        }
        return null;
    }

    public List<ReceiveMaterialsStockDTO> getReceiveStockByName(ScreenReceiveMaterialsStockQuery query) {
        if (!query.getStartTime().isEmpty() && !query.getEndTime().isEmpty() && query.getMaterialsId() != null) {
        }
        return null;
    }

    public List<ReceiveMaterialsAllEchart> getReceiveAllTypeStock() {
        return receiveService.getReceiveAllTypeStock();
    }

    public List<ReceiveMaterialsStockDTO> getReceiveAllTypeByName(ReceiveMaterialsTypeQuery query) {
        return receiveService.getReceiveAllTypeByName(query.getName());
    }

    public ReceiveZhuDTO getReceiveAllReceiveExplain() {
        return receiveService.getReceiveAllReceiveExplain();
    }

    public List<ReceiveMaterialsStockDTO> getReceiveStockByNameType(
            ReceiveMaterialsStockQuery receiveMaterialsStockQuery) {
        // if (receiveMaterialsStockQuery.getMaterialsName() == null
        // || receiveMaterialsStockQuery.getMaterialsName().isEmpty()) {
        // return null;
        // }
        if (receiveMaterialsStockQuery.getDateType().equals("week")) {
            return receiveService.getReceiveStockWeekByNameType(receiveMaterialsStockQuery.getMaterialsId());
        } else if (receiveMaterialsStockQuery.getDateType().equals("month")) {
            return receiveService.getReceiveStockMonthByNameType(receiveMaterialsStockQuery.getMaterialsId());
        } else if (receiveMaterialsStockQuery.getDateType().equals("year")) {
            return receiveService.getReceiveStockYearByNameType(receiveMaterialsStockQuery.getMaterialsId());
        }
        return null;
    }

}
