package com.biology.domain.manage.xunJian;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.xunJian.command.AddXunJianCommand;
import com.biology.domain.manage.xunJian.command.UpdateXunJianCommand;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.db.XunJianService;
import com.biology.domain.manage.xunJian.dto.XunJianDTO;
import com.biology.domain.manage.xunJian.model.XunJianFactory;
import com.biology.domain.manage.xunJian.model.XunJianModel;
import com.biology.domain.manage.xunJian.query.XunJianQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XunJianApplicationService {
    private final XunJianFactory xunJianFactory;

    private final XunJianService xunJianService;

    public void create(AddXunJianCommand command) {
        XunJianModel xunJianModel = xunJianFactory.create();
        xunJianModel.loadAddXunJianCommand(command);
        xunJianModel.insert();
    }

    public void update(UpdateXunJianCommand command) {
        XunJianModel xunJianModel = xunJianFactory.loadById(command.getXunJianId());
        xunJianModel.loadUpdateXunJianCommand(command);
        xunJianModel.updateById();
    }

    public void deleteReveives(List<Long> xunJianIds) {
        xunJianIds.forEach(xunJianId -> {
            XunJianModel xunJianModel = xunJianFactory.loadById(xunJianId);
            xunJianModel.deleteById();
        });
    }

    public PageDTO<XunJianDTO> getXunJians(XunJianQuery query) {
        Page<XunJianEntity> page = xunJianService.page(query.toPage(), query.toQueryWrapper());
        List<XunJianDTO> records = page.getRecords().stream().map(XunJianDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public XunJianDTO getXunJianInfo(Long xunJianId) {
        XunJianEntity byId = xunJianService.getById(xunJianId);
        return new XunJianDTO(byId);
    }

    public void enable(Long xunJianId) {
        XunJianModel xunJianModel = xunJianFactory.loadById(xunJianId);
        xunJianModel.setEnable(true);
        xunJianModel.updateById();
    }

    public void disable(Long xunJianId) {
        XunJianModel xunJianModel = xunJianFactory.loadById(xunJianId);
        xunJianModel.setEnable(false);
        xunJianModel.updateById();
    }
}
