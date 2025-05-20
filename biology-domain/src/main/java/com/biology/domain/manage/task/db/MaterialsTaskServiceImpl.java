package com.biology.domain.manage.task.db;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.common.utils.time.DatePickUtil;
import com.biology.domain.manage.detection.dto.PowerDTO;
import com.biology.domain.manage.materials.db.MaterialsEntity;
import com.biology.domain.manage.materials.dto.StockEchatDTO;
import com.biology.domain.manage.task.dto.TaskMaterialsDTO;

import cn.hutool.core.util.StrUtil;

@Service
public class MaterialsTaskServiceImpl extends ServiceImpl<MaterialsTaskMapper, MaterialsTaskEntity>
                implements MaterialsTaskService {

        public StockEchatDTO getStockMaterials(Long materialsId, String dayType, Boolean isUnit) {
                StockEchatDTO result = new StockEchatDTO();
                result.setXAxisData(new ArrayList<>());
                result.setSeriesData(new ArrayList<>());
                List<TaskMaterialsDTO> list = new ArrayList<>();
                // LocalDate today = LocalDate.now();
                if ("week".equals(dayType)) {
                        list = baseMapper.getStockWeek(materialsId);
                        result.setXAxisData(DatePickUtil.getWeekNowMMDD());
                        for (String s : result.getXAxisData()) {
                                boolean isExit = false;
                                for (TaskMaterialsDTO p : list) {
                                        if (p.getTime().contains(s)) {
                                                result.getSeriesData().add(p.getCount());
                                                isExit = true;
                                        }
                                }
                                if (!isExit) {
                                        result.getSeriesData().add(0.0);
                                }
                        }

                        QueryWrapper<MaterialsEntity> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("materials_id", materialsId);
                        MaterialsEntity materialsEntity = new MaterialsEntity().selectOne(queryWrapper);
                        if (materialsEntity != null) {
                                result.getSeriesData().set(6, materialsEntity.getStock());
                        }

                        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

                        // LocalDate start = today.minusDays(6);
                        // list = baseMapper.getStockWeek(materialsId);
                        // for (int i = 0; i < 7; i++) {
                        // LocalDate date = start.plusDays(i); // 获取前 i 天的日期
                        // result.getXAxisData().add(date.format(formatter));
                        // boolean isExit = false;
                        // if (i < 6) {
                        // for (TaskMaterialsDTO p : list) {
                        // if (p.getTime().contains(date.format(formatter))) {
                        // result.getSeriesData().add(p.getCount());
                        // isExit = true;
                        // break;
                        // }
                        // }
                        // if (!isExit) {
                        // result.getSeriesData().add(0.0);
                        // }
                        // } else {
                        // MaterialsEntity materialsEntity = new MaterialsEntity()
                        // .selectById(materialsId);
                        // if (materialsEntity != null) {
                        // result.getSeriesData().add(materialsEntity.getStock());
                        // } else {
                        // result.getSeriesData().add(0.0);
                        // }
                        // }

                        // }

                } else if ("month".equals(dayType)) {
                        list = baseMapper.getStockMonth(materialsId);
                        result.setXAxisData(DatePickUtil.getMonthNowMMDD());
                        for (String s : result.getXAxisData()) {
                                boolean isExit = false;
                                for (TaskMaterialsDTO t : list) {
                                        if (t.getTime().contains(s)) {
                                                result.getSeriesData().add(t.getCount());
                                                isExit = true;
                                        }
                                }
                                if (!isExit) {
                                        result.getSeriesData().add(0.0);
                                }
                        }

                        // LocalDate oneMonthAgo = today.minusMonths(1);
                        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
                        // list = baseMapper.getStockMonth(materialsId);
                        // if (list.size() == 0) {
                        // return result;
                        // }

                        // for (LocalDate date = oneMonthAgo; !date.isAfter(today); date =
                        // date.plusDays(1)) {
                        // result.getXAxisData().add(date.format(formatter));
                        // boolean isExit = false;
                        // for (TaskMaterialsDTO p : list) {
                        // if (p.getTime().contains(date.format(formatter))) {
                        // result.getSeriesData().add(p.getCount());
                        // isExit = true;
                        // break;
                        // }
                        // }
                        // if (!isExit) {
                        // result.getSeriesData().add(0.0);
                        // }

                        // }
                        // LocalDate firstDayOfMonth = today.withDayOfMonth(1); // 本月的第一天
                        // LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth()); //
                        // 本月的最
                        // LocalDate currentDate = firstDayOfMonth;
                        // while (!currentDate.isAfter(lastDayOfMonth)) {
                        // result.getXAxisData().add(currentDate.format(formatter));
                        // currentDate = currentDate.plusDays(1); // 加一天
                        // boolean isExit = false;
                        // for (TaskMaterialsDTO p : list) {
                        // if (p.getTime().contains(currentDate.format(formatter))) {
                        // result.getSeriesData().add(p.getCount());
                        // isExit = true;
                        // break;
                        // }
                        // }
                        // if (!isExit) {
                        // result.getSeriesData().add(0.0);
                        // }
                        // }

                } else if ("year".equals(dayType)) {
                        list = baseMapper.getStockYear(materialsId);
                        // result.setXAxisData(DatePickUtil.getYearNow());
                        result.setXAxisData(DatePickUtil.getYearNowBefore());
                        for (String s : result.getXAxisData()) {
                                boolean isExit = false;
                                for (TaskMaterialsDTO t : list) {
                                        if (t.getTime().equals(s)) {
                                                result.getSeriesData().add(t.getCount());
                                                isExit = true;
                                        }
                                }
                                if (!isExit) {
                                        result.getSeriesData().add(0.0);
                                }
                        }

                        // dayType = "year";
                        // list = baseMapper.getStockYear(materialsId);
                        // for (int i = 0; i < 12; i++) {
                        // result.getXAxisData().add(String.valueOf(i + 1));
                        // boolean isExit = false;
                        // for (TaskMaterialsDTO p : list) {
                        // if (p.getTime().contains(String.valueOf(i + 1))) {
                        // result.getSeriesData().add(p.getCount());
                        // isExit = true;
                        // break;
                        // }
                        // }
                        // if (!isExit) {
                        // result.getSeriesData().add(0.0);
                        // }
                        // }
                }
                return result;
        }
}
