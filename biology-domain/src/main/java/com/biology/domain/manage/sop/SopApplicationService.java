package com.biology.domain.manage.sop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.sop.command.AddSopCommand;
import com.biology.domain.manage.sop.command.UpdateSopCommand;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.sop.db.SopFileEntity;
import com.biology.domain.manage.sop.db.SopFileService;
import com.biology.domain.manage.sop.db.SopService;
import com.biology.domain.manage.sop.dto.SopDTO;
import com.biology.domain.manage.sop.model.SopFactory;
import com.biology.domain.manage.sop.model.SopModel;
import com.biology.domain.manage.sop.query.SearchSopQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SopApplicationService {

    private final SopService sopService;

    private final SopFactory sopFactory;

    private final SopFileService sopFileService;

    public void addSop(AddSopCommand command) {
        SopModel sopModel = sopFactory.create();
        sopModel.loadAddSopCommand(command);
        sopModel.insert();
        CacheCenter.sopCache.delete(sopModel.getSopId());
        CacheCenter.sopFileCache.delete(sopModel.getSopId());
        // CacheCenter.sopCache.set(sopModel.getSopId(), sopModel);
        // if (sopModel.getPaths() != null && sopModel.getPaths().size() > 0) {
        // List<SopFileEntity> kts = new ArrayList<>();
        // for (String path : sopModel.getPaths()) {
        // SopFileEntity kt = new SopFileEntity();
        // kt.setPath(path);
        // kt.setSopId(sopModel.getSopId());
        // kts.add(kt);
        // }
        // CacheCenter.sopFileCache.set(sopModel.getSopId(), kts);
        // } else {
        // CacheCenter.sopFileCache.delete(sopModel.getSopId());
        // }
    }

    public void updateSop(UpdateSopCommand command) {
        SopModel sopModel = sopFactory.loadById(command.getSopId());
        sopModel.loadAddSopCommand(command);
        sopModel.updateById();
        CacheCenter.sopCache.delete(sopModel.getSopId());
        CacheCenter.sopFileCache.delete(sopModel.getSopId());
        // CacheCenter.sopCache.set(sopModel.getSopId(), sopModel);
        // if (sopModel.getPaths() != null && sopModel.getPaths().size() > 0) {
        // List<SopFileEntity> kts = new ArrayList<>();
        // for (String path : sopModel.getPaths()) {
        // SopFileEntity kt = new SopFileEntity();
        // kt.setPath(path);
        // kt.setSopId(sopModel.getSopId());
        // kts.add(kt);
        // }
        // CacheCenter.sopFileCache.set(sopModel.getSopId(), kts);
        // } else {
        // CacheCenter.sopFileCache.delete(sopModel.getSopId());
        // }

    }

    public void deleteSop(Long sopId) {
        SopModel sopModel = sopFactory.loadById(sopId);
        sopModel.cleanFile();
        sopModel.deleteById();
        CacheCenter.sopCache.delete(sopModel.getSopId());
        CacheCenter.sopFileCache.delete(sopModel.getSopId());
    }

    public void deleteSops(List<Long> sopIds) {
        for (Long sopId : sopIds) {
            deleteSop(sopId);
        }
    }

    public SopDTO getSop(Long sopId) {
        SopModel sopModel = sopFactory.loadById(sopId);
        QueryWrapper<SopFileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sop_id", sopId);
        List<SopFileEntity> sopFiles = sopFileService.list(queryWrapper);
        sopModel.setPaths(sopFiles.stream().map(SopFileEntity::getPath).collect(Collectors.toList()));
        return new SopDTO(sopModel);
    }

    public PageDTO<SopDTO> getSopList(SearchSopQuery query) {
        Page<SopEntity> page = sopService.page(query.toPage(), query.toQueryWrapper());
        List<SopDTO> records = page.getRecords().stream().map(SopDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }
}
