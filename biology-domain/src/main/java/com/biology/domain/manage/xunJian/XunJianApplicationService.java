package com.biology.domain.manage.xunJian;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.xunJian.command.AddXunJianCommand;
import com.biology.domain.manage.xunJian.command.UpdateXunJianCommand;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJian.db.XunJianService;
import com.biology.domain.manage.xunJian.dto.XunJianDTO;
import com.biology.domain.manage.xunJian.model.XunJianFactory;
import com.biology.domain.manage.xunJian.model.XunJianModel;
import com.biology.domain.manage.xunJian.query.XunJianQuery;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryService;
import com.biology.domain.manage.xunJianHistory.model.XunJianHistoryFactory;
import com.biology.domain.manage.xunJianHistory.model.XunJianHistoryModel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class XunJianApplicationService {
    private final XunJianFactory xunJianFactory;

    private final XunJianService xunJianService;

    private final XunJianHistoryService xunJianHistoryService;

    private final XunJianHistoryFactory xunJianHistoryFactory;

    public void create(AddXunJianCommand command) {
        XunJianModel xunJianModel = xunJianFactory.create();
        xunJianModel.loadAddXunJianCommand(command);
        xunJianModel.insert();
        checkXunJian(xunJianModel);
    }

    public void update(UpdateXunJianCommand command) {
        XunJianModel xunJianModel = xunJianFactory.loadById(command.getXunJianId());
        xunJianModel.loadUpdateXunJianCommand(command);
        xunJianModel.updateById();
        checkXunJian(xunJianModel);
    }

    public void deleteReveives(List<Long> xunJianIds) {
        xunJianIds.forEach(xunJianId -> {
            XunJianModel xunJianModel = xunJianFactory.loadById(xunJianId);
            xunJianModel.deleteById();
            checkXunJian(xunJianModel);
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

    public void checkXunJian(XunJianModel model) {
        if (model == null) {
            return;
        }
        List<Long> envionmentIds = xunJianService.getAllEnvironmentByArea(model.getFanWei());
        List<Long> equipmentIds = xunJianService.getAllEquipmentByArea(model.getFanWei());

        QueryWrapper<XunJianHistoryEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("xun_jian_id", model.getXunJianId())
                .eq("status", "巡检中");
        XunJianHistoryEntity xunJianHistoryEntity = xunJianHistoryService.getOne(queryWrapper);

        LocalTime now = LocalTime.now();

        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();
        int day = LocalDate.now().getDayOfMonth();
        int totalSeconds = hour * 3600 + minute * 60 + second;
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        if (model.getEnable()) {
            XunJianDTO xDto = new XunJianDTO(model);
            for (Long id : envionmentIds) {
                String redisId = "environment-" + id.toString();
                List<XunJianDTO> xDtos = CacheCenter.xunJianDeviceCache.getObjectById(redisId);
                Boolean isExit = false;
                if (xDtos != null) {
                    isExit = xDtos.stream().anyMatch(i -> i.getXunJianId().equals(model.getXunJianId()));
                } else {
                    xDtos = new ArrayList<>();
                }
                if (!isExit) {
                    xDtos.add(xDto);
                }
                CacheCenter.xunJianDeviceCache.set(redisId, xDtos);
            }
            for (Long id : equipmentIds) {
                String redisId = "equipment-" + id.toString();
                List<XunJianDTO> xDtos = CacheCenter.xunJianDeviceCache.getObjectById(redisId);
                Boolean isExit = false;
                if (xDtos != null) {
                    isExit = xDtos.stream().anyMatch(i -> i.getXunJianId().equals(model.getXunJianId()));
                } else {
                    xDtos = new ArrayList<>();
                }
                if (!isExit) {
                    xDtos.add(xDto);
                }
                CacheCenter.xunJianDeviceCache.set(redisId, xDtos);
            }

            Boolean isExit = false;
            Boolean timeExit = false;

            if (model.getXunJianLeiXing() != null && model.getXunJianLeiXing().equals("持续巡检")) {
                if (model.getXunJianPinLu() != null && model.getTimeRange() != null
                        && model.getTimeRange().size() == 2) {
                    if (model.getTimeRange().get(0) < totalSeconds
                            && totalSeconds < model.getTimeRange().get(1)) {
                        isExit = true;
                    }

                    if (model.getXunJianPinLu().equals("每日")) {
                        timeExit = true;
                    }
                    if (model.getXunJianPinLu().equals("每周") && model.getDayRange() != null
                            && model.getDayRange().size() > 0) {
                        timeExit = model.getDayRange().contains(dayOfWeek - 1);
                    }
                    if (model.getXunJianPinLu().equals("每月") && model.getDayRange() != null
                            && model.getDayRange().size() > 0) {
                        timeExit = model.getDayRange().contains(day - 1);
                    }
                }
            }

            System.out.println("==============================");
            System.out.println(dayOfWeek);
            System.out.println(isExit);
            System.out.println(timeExit);

            if (isExit && timeExit && xunJianHistoryEntity == null) {
                XunJianHistoryModel xunJianHistoryModel = xunJianHistoryFactory.create();
                xunJianHistoryModel.setXunJianId(model.getXunJianId());
                xunJianHistoryModel.setStatus("巡检中");
                xunJianHistoryModel.setXunJianLeiXing(model.getXunJianLeiXing());
                xunJianHistoryModel.setXunJianPinLu(model.getXunJianPinLu());
                xunJianHistoryModel.setDayRange(model.getDayRange());
                xunJianHistoryModel.setTimeRange(model.getTimeRange());
                xunJianHistoryModel.setFanWei(model.getFanWei());
                xunJianHistoryModel.insert();
            }

        } else {
            for (Long id : envionmentIds) {
                String redisId = "environment-" + id.toString();
                List<XunJianDTO> xDtos = CacheCenter.xunJianDeviceCache.getObjectById(redisId);
                if (xDtos != null) {
                    xDtos.removeIf(i -> i.getXunJianId().equals(model.getXunJianId()));
                }
                CacheCenter.xunJianDeviceCache.set(redisId, xDtos);
            }
            for (Long id : equipmentIds) {
                String redisId = "equipment-" + id.toString();
                List<XunJianDTO> xDtos = CacheCenter.xunJianDeviceCache.getObjectById(redisId);
                if (xDtos != null) {
                    xDtos.removeIf(i -> i.getXunJianId().equals(model.getXunJianId()));
                }
                CacheCenter.xunJianDeviceCache.set(redisId, xDtos);
            }

        }

    }

}
