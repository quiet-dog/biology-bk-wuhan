package com.biology.domain.manage.shiJuan;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.chuqin.command.AddChuQinCommand;
import com.biology.domain.manage.shiJuan.command.AddResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.command.UpdateResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanService;
import com.biology.domain.manage.shiJuan.db.Score;
import com.biology.domain.manage.shiJuan.db.ShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ShiJuanService;
import com.biology.domain.manage.shiJuan.dto.ResultGanYuDTO;
import com.biology.domain.manage.shiJuan.dto.ResultShiJuanDTO;
import com.biology.domain.manage.shiJuan.dto.ShiJuanDTO;
import com.biology.domain.manage.shiJuan.dto.SubmitResultDTO;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanModel;
import com.biology.domain.manage.shiJuan.query.ResultShiJuanQuery;
import com.biology.domain.manage.shiJuan.query.ShiJuanQuery;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlArchive.model.XlArchiveFactory;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;
import com.biology.infrastructure.user.AuthenticationUtils;
import com.biology.infrastructure.user.web.SystemLoginUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultShiJuanApplicationService {
    private final ResultShiJuanFactory resultShiJuanFactory;

    private final ResultShiJuanService resultShiJuanService;

    private final ShiJuanService rShiJuanService;

    private final XlArchiveFactory xlArchiveFactory;

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
        query.setIsNeedLastTime(true);
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

    public PageDTO<ShiJuanDTO> getShiJuanList(ShiJuanQuery query) {
        Page<ShiJuanEntity> page = rShiJuanService.page(query.toPage(), query.toQueryWrapper());
        List<ShiJuanDTO> records = page.getRecords().stream().map(ShiJuanDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public void submitResult(SubmitResultDTO req) {
        ResultShiJuanModel rModel = resultShiJuanFactory.loadById(req.getResultId());
        rModel.setResult(req.getResult());
        double score = 0;

        Set<Integer> sds = new HashSet<>(Arrays.asList(2, 5, 6, 11, 12, 14, 16, 17, 18, 20));
        Set<Integer> sas = new HashSet<>(Arrays.asList(5, 9, 13, 17, 19));

        for (int i = 0; i < rModel.getResult().size(); i++) {
            Score item = rModel.getResult().get(i);
            Integer num = i + 1;
            if (item.getResult().equals("A")) {
                if (rModel.getType().equals("SDS量表") && sds.contains(num)) {
                    score -= 1;
                    continue;
                }
                if (rModel.getType().equals("SAS量表") && sas.contains(num)) {
                    score -= 1;
                    continue;
                }
                score += 1;
            }
            if (item.getResult().equals("B")) {
                if (rModel.getType().equals("SDS量表") && sds.contains(num)) {
                    score -= 2;
                    continue;
                }
                if (rModel.getType().equals("SAS量表") && sas.contains(num)) {
                    score -= 2;
                    continue;
                }
                score += 2;
            }
            if (item.getResult().equals("C")) {
                if (rModel.getType().equals("SDS量表") && sds.contains(num)) {
                    score -= 3;
                    continue;
                }
                if (rModel.getType().equals("SAS量表") && sas.contains(num)) {
                    score -= 3;
                    continue;
                }
                score += 3;
            }
            if (item.getResult().equals("D")) {
                if (rModel.getType().equals("SDS量表") && sds.contains(num)) {
                    score -= 4;
                    continue;
                }
                if (rModel.getType().equals("SAS量表") && sas.contains(num)) {
                    score -= 4;
                    continue;
                }
                score += 4;
            }
            if (item.getResult().equals("E")) {
                score += 5;
            }
        }

        if (rModel.getType().equals("SAS量表") || rModel.getType().equals("SDS量表")) {
            score = score * 1.25;
        }

        if (rModel.getType().equals("心理调查评估问卷")) {
            if (score < 160) {
                rModel.setCePing("正常范围");
            }
            if (160 <= score && score < 200) {
                rModel.setCePing("轻度异常");
            }
            if (200 <= score && score < 250) {
                rModel.setCePing("中度异常");
            }
            if (250 <= score) {
                rModel.setCePing("重度异常");
            }
        }

        if (rModel.getType().equals("SAS量表")) {
            if (score < 50) {
                rModel.setCePing("无明显焦虑症状");
            }
            if (50 <= score && score <= 59) {
                rModel.setCePing("轻度焦虑");
            }
            if (60 <= score && score <= 69) {
                rModel.setCePing("中度焦虑");
            }
            if (score >= 70) {
                rModel.setCePing("重度焦虑");
            }
        }

        if (rModel.getType().equals("SDS量表")) {
            if (score < 53) {
                rModel.setCePing("无明显抑郁症状");
            }
            if (53 <= score && score <= 62) {
                rModel.setCePing("轻度抑郁");
            }
            if (63 <= score && score <= 72) {
                rModel.setCePing("中度抑郁");
            }
            if (score >= 73) {
                rModel.setCePing("重度抑郁");
            }
        }

        rModel.setScore(score);

        long timestamp = System.currentTimeMillis();
        rModel.setLastTime(timestamp);

        rModel.updateById();
        if (!rModel.getCePing().equals("正常范围") && rModel.getCePing().equals("无明显焦虑症状")
                && !rModel.getCePing().equals("无明显抑郁症状")) {
            UpdateWrapper<XlArchiveEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", rModel.getUserId()) // WHERE 条件
                    .set("status", "重点关注"); // 设置更新字段
            new XlArchiveEntity().update(updateWrapper);
        }
    }

    public void setGanYu(ResultGanYuDTO req) {
        ResultShiJuanModel rModel = resultShiJuanFactory.loadById(req.getResultId());
        rModel.setGanYuFangShi(req.getGanYuFangShi());
        rModel.setGanYuResult(req.getGanYuResult());
        rModel.setGanYuTime(req.getGanYuTime());
        rModel.setExecUser(req.getExecUser());
        rModel.updateById();
    }
}
