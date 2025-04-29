package com.biology.domain.manage.moni;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.materials.db.WarehouseEntity;
import com.biology.domain.manage.materials.dto.WarehouseDTO;
import com.biology.domain.manage.moni.command.AddMoniCommand;
import com.biology.domain.manage.moni.command.UpdateMoniCommand;
import com.biology.domain.manage.moni.db.MoniEntity;
import com.biology.domain.manage.moni.db.MoniService;
import com.biology.domain.manage.moni.dto.MoniDTO;
import com.biology.domain.manage.moni.model.MoniFactory;
import com.biology.domain.manage.moni.model.MoniModel;
import com.biology.domain.manage.moni.query.SearchMoniQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoniApplicationService {

    private final MoniFactory moniFactory;

    private final MoniService moniService;

    public void addMoni(AddMoniCommand command) {
        MoniModel moniModel = moniFactory.create();
        moniModel.loadAddMoniCommand(command);
        moniModel.insert();
    }

    public void updateMoni(UpdateMoniCommand command) {
        MoniModel moniModel = moniFactory.loadById(command.getMoniId());
        moniModel.loadUpdateMoniCommand(command);
        moniModel.updateById();
    }

    public void deleteMoni(List<Long> moniIds) {
        for (Long moniId : moniIds) {
            MoniModel moniModel = moniFactory.loadById(moniId);
            moniModel.deleteById();
        }
    }

    public PageDTO<MoniDTO> getMoniList(SearchMoniQuery query) {
        Page<MoniEntity> page = moniService.page(query.toPage(), query.toQueryWrapper());
        List<MoniDTO> records = page.getRecords().stream().map(MoniDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }
}
