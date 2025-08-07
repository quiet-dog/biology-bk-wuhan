package com.biology.domain.manage.shiJuan;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.shiJuan.command.AddResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.command.UpdateResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanService;
import com.biology.domain.manage.shiJuan.dto.ResultShiJuanDTO;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanModel;
import com.biology.domain.manage.shiJuan.query.ResultShiJuanQuery;
import com.biology.infrastructure.user.AuthenticationUtils;
import com.biology.infrastructure.user.web.SystemLoginUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultShiJuanApplicationService {
    private final ResultShiJuanFactory resultShiJuanFactory;

    private final ResultShiJuanService resultShiJuanService;

    public void create(AddResultShiJuanCommand command) {
        ResultShiJuanModel resultShiJuanModel = resultShiJuanFactory.create();
        resultShiJuanModel.loadAddResultShiJuanCommand(command);
        resultShiJuanModel.insert();
        return;
    }

    public void update(UpdateResultShiJuanCommand command) {
        ResultShiJuanModel resultShiJuanModel = resultShiJuanFactory.loadById(command.getResultId());
        resultShiJuanModel.loadUpdateResultShiJuanCommand(command);
        resultShiJuanModel.updateById();
        return;
    }

    public void deleteReveives(List<Long> resultShiJuanIds) {
        resultShiJuanIds.forEach(resultShiJuanId -> {
            ResultShiJuanModel resultShiJuanModel = resultShiJuanFactory.loadById(resultShiJuanId);
            resultShiJuanModel.deleteById();
        });
        return;
    }

    public PageDTO<ResultShiJuanDTO> getResultShiJuans(ResultShiJuanQuery query) {
        Page<ResultShiJuanEntity> page = resultShiJuanService.page(query.toPage(), query.toQueryWrapper());
        List<ResultShiJuanDTO> records = page.getRecords().stream().map(ResultShiJuanDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public ResultShiJuanDTO getResultShiJuanInfo(Long resultShiJuanId) {
        ResultShiJuanEntity byId = resultShiJuanService.getById(resultShiJuanId);
        return new ResultShiJuanDTO(byId);
    }

    public PageDTO<ResultShiJuanDTO> getResultShiJuansByCreator(ResultShiJuanQuery query) {

        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        query.setCreatorId(loginUser.getUserId());
        Page<ResultShiJuanEntity> page = resultShiJuanService.page(query.toPage(), query.toQueryWrapper());
        List<ResultShiJuanDTO> records = page.getRecords().stream().map(ResultShiJuanDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public PageDTO<ResultShiJuanDTO> getResultShiJuansByUser(ResultShiJuanQuery query) {

        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        query.setUserId(loginUser.getUserId());
        Page<ResultShiJuanEntity> page = resultShiJuanService.page(query.toPage(), query.toQueryWrapper());
        List<ResultShiJuanDTO> records = page.getRecords().stream().map(ResultShiJuanDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }
}
