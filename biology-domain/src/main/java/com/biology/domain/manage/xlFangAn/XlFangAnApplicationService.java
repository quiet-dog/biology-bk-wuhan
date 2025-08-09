package com.biology.domain.manage.xlFangAn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.xlFangAn.command.AddXlFangAnCommand;
import com.biology.domain.manage.xlFangAn.command.UpdateXlFangAnCommand;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;
import com.biology.domain.manage.xlFangAn.db.XlFangAnService;
import com.biology.domain.manage.xlFangAn.dto.XlFangAnDTO;
import com.biology.domain.manage.xlFangAn.model.XlFangAnFactory;
import com.biology.domain.manage.xlFangAn.model.XlFangAnModel;
import com.biology.domain.manage.xlFangAn.query.XlFangAnQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XlFangAnApplicationService {

    private final XlFangAnFactory xlFangAnFactory;

    private final XlFangAnService xlFangAnService;

    public void create(AddXlFangAnCommand command) {
        XlFangAnModel xlFangAnModel = xlFangAnFactory.create();
        xlFangAnModel.loadAddXlFangAnCommand(command);
        xlFangAnModel.insert();
        return;
    }

    public void update(UpdateXlFangAnCommand command) {
        XlFangAnModel xlFangAnModel = xlFangAnFactory.loadById(command.getXlFangAnId());
        xlFangAnModel.loadUpdateXlFangAnCommand(command);
        xlFangAnModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> xlFangAnIds) {
        xlFangAnIds.forEach(xlFangAnId -> {
            XlFangAnModel xlFangAnModel = xlFangAnFactory.loadById(xlFangAnId);
            xlFangAnModel.deleteById();
        });
        return;
    }

    public PageDTO<XlFangAnDTO> getXlFangAns(XlFangAnQuery query) {
        Page<XlFangAnEntity> page = xlFangAnService.page(query.toPage(), query.toQueryWrapper());
        List<XlFangAnDTO> records = page.getRecords().stream().map(XlFangAnDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public XlFangAnDTO getXlFangAnInfo(Long xlFangAnId) {
        XlFangAnEntity byId = xlFangAnService.getById(xlFangAnId);
        return new XlFangAnDTO(byId);
    }

    public List<String> getDeptGroup() {
        return xlFangAnService.getPersonnelGroup();
    }

}
