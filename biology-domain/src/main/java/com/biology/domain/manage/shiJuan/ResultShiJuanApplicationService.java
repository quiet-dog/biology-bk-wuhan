package com.biology.domain.manage.shiJuan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.catalina.loader.ResourceEntry;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.chuqin.command.AddChuQinCommand;
import com.biology.domain.manage.detection.dto.SeriesDTO;
import com.biology.domain.manage.shiJuan.command.AddResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.command.UpdateResultShiJuanCommand;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ResultShiJuanService;
import com.biology.domain.manage.shiJuan.db.Score;
import com.biology.domain.manage.shiJuan.db.ShiJuanEntity;
import com.biology.domain.manage.shiJuan.db.ShiJuanService;
import com.biology.domain.manage.shiJuan.dto.CePingJieGuoTongJiEchart;
import com.biology.domain.manage.shiJuan.dto.CePingJieGuoTongJiEchartResult;
import com.biology.domain.manage.shiJuan.dto.CePingNumDTO;
import com.biology.domain.manage.shiJuan.dto.PingFuJieGuoNumDTO;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoEchart;
import com.biology.domain.manage.shiJuan.dto.PingGuJieGuoSeriesDTO;
import com.biology.domain.manage.shiJuan.dto.ResultGanYuDTO;
import com.biology.domain.manage.shiJuan.dto.ResultShiJuanDTO;
import com.biology.domain.manage.shiJuan.dto.ShiJuanDTO;
import com.biology.domain.manage.shiJuan.dto.SubmitResultDTO;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanFactory;
import com.biology.domain.manage.shiJuan.model.ResultShiJuanModel;
import com.biology.domain.manage.shiJuan.query.PingGuJieGuoFenXiQuery;
import com.biology.domain.manage.shiJuan.query.ResultShiJuanQuery;
import com.biology.domain.manage.shiJuan.query.ShiJuanQuery;
import com.biology.domain.manage.xlArchive.db.XlArchiveEntity;
import com.biology.domain.manage.xlArchive.model.XlArchiveFactory;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;
import com.biology.infrastructure.user.AuthenticationUtils;
import com.biology.infrastructure.user.web.SystemLoginUser;

import cn.hutool.core.date.DateUtil;
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
                    score += 4;
                    continue;
                }
                if (rModel.getType().equals("SAS量表") && sas.contains(num)) {
                    score += 4;
                    continue;
                }
                score += 1;
            }
            if (item.getResult().equals("B")) {
                if (rModel.getType().equals("SDS量表") && sds.contains(num)) {
                    score += 3;
                    continue;
                }
                if (rModel.getType().equals("SAS量表") && sas.contains(num)) {
                    score += 3;
                    continue;
                }
                score += 2;
            }
            if (item.getResult().equals("C")) {
                if (rModel.getType().equals("SDS量表") && sds.contains(num)) {
                    score += 2;
                    continue;
                }
                if (rModel.getType().equals("SAS量表") && sas.contains(num)) {
                    score += 2;
                    continue;
                }
                score += 3;
            }
            if (item.getResult().equals("D")) {
                if (rModel.getType().equals("SDS量表") && sds.contains(num)) {
                    score += 1;
                    continue;
                }
                if (rModel.getType().equals("SAS量表") && sas.contains(num)) {
                    score += 1;
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

        // else {
        // UpdateWrapper<XlArchiveEntity> updateWrapper = new UpdateWrapper<>();
        // updateWrapper.eq("user_id", rModel.getUserId()) // WHERE 条件
        // .set("status", "正常"); // 设置更新字段
        // new XlArchiveEntity().update(updateWrapper);
        // }
    }

    public void setGanYu(ResultGanYuDTO req) {
        ResultShiJuanModel rModel = resultShiJuanFactory.loadById(req.getResultId());
        rModel.setGanYuFangShi(req.getGanYuFangShi());
        rModel.setGanYuResult(req.getGanYuResult());
        rModel.setGanYuTime(req.getGanYuTime());
        rModel.setExecUser(req.getExecUser());
        rModel.updateById();
    }

    public Map<String, Map<String, Integer>> getResultNum(Long fangAnId) {
        QueryWrapper<ResultShiJuanEntity> queryWrapper = new QueryWrapper<ResultShiJuanEntity>();
        List<ResultShiJuanEntity> list = resultShiJuanService.list(queryWrapper);
        Map<String, Integer> sas = new HashMap<>();
        Map<String, Integer> sds = new HashMap<>();
        Map<String, Integer> xl = new HashMap<>();
        xl.put("正常范围", 0);
        xl.put("轻度异常", 0);
        xl.put("中度异常", 0);
        xl.put("重度异常", 0);

        sds.put("无明显抑郁症状", 0);
        sds.put("轻度抑郁", 0);
        sds.put("中度抑郁", 0);
        sds.put("重度抑郁", 0);

        sas.put("无明显焦虑症状", 0);
        sas.put("轻度焦虑", 0);
        sas.put("中度焦虑", 0);
        sas.put("重度焦虑", 0);
        for (ResultShiJuanEntity r : list) {
            Integer num = null;

            if (r.getType().equals("SAS量表")) {
                num = sas.get(r.getCePing());
                if (num != null) {
                    num++;
                    sas.put(r.getCePing(), num);
                }
            }
            if (r.getType().equals("SDS量表")) {
                num = sds.get(r.getCePing());
                if (num != null) {
                    num++;
                    sds.put(r.getCePing(), num);
                }
            }
            if (r.getType().equals("心理调查评估问卷")) {
                num = xl.get(r.getCePing());
                if (num != null) {
                    num++;
                    xl.put(r.getCePing(), num);
                }
            }

        }

        Map<String, Map<String, Integer>> result = new HashMap<>();
        result.put("SAS量表", sas);
        result.put("SDS量表", sds);
        result.put("心理调查评估问卷", xl);
        return result;
    }

    public PingGuJieGuoEchart pingGuJieGuoFenXi(PingGuJieGuoFenXiQuery query) {
        List<PingFuJieGuoNumDTO> list = resultShiJuanService.getPingGuJieGuoFenXi(query.getType());
        PingGuJieGuoEchart result = new PingGuJieGuoEchart();
        List<PingGuJieGuoSeriesDTO> l = new ArrayList<>();
        List<String> sou = new ArrayList<>();

        if (query.getType().equals("心理调查评估问卷")) {
            sou.add("正常范围");
            sou.add("轻度异常");
            sou.add("中度异常");
            sou.add("重度异常");
        }

        if (query.getType().equals("SDS量表")) {
            sou.add("无明显抑郁症状");
            sou.add("轻度抑郁");
            sou.add("中度抑郁");
            sou.add("重度抑郁");
        }

        if (query.getType().equals("SAS量表")) {
            sou.add("无明显焦虑症状");
            sou.add("轻度焦虑");
            sou.add("中度焦虑");
            sou.add("重度焦虑");
        }

        sou.forEach(item -> {
            PingGuJieGuoSeriesDTO d = new PingGuJieGuoSeriesDTO();
            d.setName(item);
            d.setValue(0);
            l.add(d);
        });
        for (PingFuJieGuoNumDTO r : list) {
            for (Integer i = 0; i < l.size(); i++) {
                PingGuJieGuoSeriesDTO tar = l.get(i);
                if (tar.getName().equals(r.getCePing())) {
                    tar.setValue(r.getNum());
                    l.set(i, tar);
                }
            }
        }
        result.setSeries(l);
        return result;
    }

    public Map<String, Map<String, Integer>> cePingJieGuoTongJi(PingGuJieGuoFenXiQuery query) {
        // List<PingFuJieGuoNumDTO> list =
        // resultShiJuanService.cePingJieGuoTongJi(query.getXlFangAnId());
        List<PingFuJieGuoNumDTO> list = resultShiJuanService.getPingJieGuoTongJiByFangAnName(query.getFangAnName());
        List<String> categories = Arrays.asList(
                "正常范围", "轻度异常", "中度异常", "重度异常",
                "无明显抑郁症状", "轻度抑郁", "中度抑郁", "重度抑郁",
                "无明显焦虑症状", "轻度焦虑", "中度焦虑", "重度焦虑");
        Map<String, Integer> sas = new HashMap<>();
        // sas.put("无明显焦虑症状", 0);
        // sas.put("轻度焦虑", 0);
        // sas.put("中度焦虑", 0);
        // sas.put("重度焦虑", 0);
        sas.put("正常", 0);
        sas.put("异常", 0);
        Map<String, Integer> sds = new HashMap<>();
        // sds.put("无明显抑郁症状", 0);
        // sds.put("轻度抑郁", 0);
        // sds.put("中度抑郁", 0);
        // sds.put("重度抑郁", 0);
        sds.put("正常", 0);
        sds.put("异常", 0);
        Map<String, Integer> xl = new HashMap<>();
        // xl.put("正常范围", 0);
        // xl.put("轻度异常", 0);
        // xl.put("中度异常", 0);
        // xl.put("重度异常", 0);
        xl.put("正常", 0);
        xl.put("异常", 0);
        Boolean sasB = false;
        Boolean sdsB = false;
        Boolean xlB = false;
        for (PingFuJieGuoNumDTO r : list) {
            if (r.getType().equals("SAS量表")) {
                if (r.getCePing().equals("无明显焦虑症状")) {
                    sas.put("正常", r.getNum());
                } else {
                    Integer num = sas.get(r.getCePing());
                    if (num == null) {
                        num = 0;
                    }
                    num = num + r.getNum();
                    sas.put("异常", num);
                }
                sasB = true;
            }
            if (r.getType().equals("SDS量表")) {
                if (r.getCePing().equals("无明显抑郁症状")) {
                    sds.put("正常", r.getNum());
                } else {
                    Integer num = sds.get(r.getCePing());
                    if (num == null) {
                        num = 0;
                    }
                    num = num + r.getNum();
                    sds.put("异常", num);
                }
                sdsB = true;
            }
            if (r.getType().equals("心理调查评估问卷")) {
                if (r.getCePing().equals("正常范围")) {
                    xl.put("正常", r.getNum());
                } else {
                    Integer num = xl.get(r.getCePing());
                    if (num == null) {
                        num = 0;
                    }
                    num = num + r.getNum();
                    xl.put("异常", num);
                }
                xlB = true;
            }
        }

        Map<String, Map<String, Integer>> result = new HashMap<>();
        // if (sasB) {
        result.put("SAS量表", sas);
        // }
        // if (sdsB) {
        result.put("SDS量表", sds);
        // }
        // if (xlB) {
        result.put("心理调查评估问卷", xl);
        // }

        return result;
    }

    // 获取当天异常的人数
    public long getDayExceptionCount() {
        // 正常范围 无明显抑郁症状 无明显焦虑症状
        List<String> list = Arrays.asList("正常范围", "无明显抑郁症状", "无明显焦虑症状");
        QueryWrapper<ResultShiJuanEntity> queryWrapper = new QueryWrapper<ResultShiJuanEntity>();
        queryWrapper.eq("DATE_FORMAT(create_time, '%Y-%m-%d')", DateUtil.format(new Date(), "yyyy-MM-dd"))
                .notIn("ce_ping", list)
                .isNotNull("ce_ping");
        return resultShiJuanService.count(queryWrapper);
    }

}
