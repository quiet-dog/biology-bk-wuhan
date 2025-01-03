package com.biology.domain.manage.threshold.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class ThresholdValueServiceImpl extends ServiceImpl<ThresholdValueMapper, ThresholdValueEntity>
                implements ThresholdValueService {

        @Override
        public List<ThresholdValueEntity> selectByThresholdId(Long thresholdId) {
                QueryWrapper<ThresholdValueEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("threshold_id", thresholdId);
                return baseMapper.selectList(queryWrapper);
        }

        @Override
        public List<String> getTypeList() {
                QueryWrapper<ThresholdValueEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.select("level").groupBy("level");
                return baseMapper.selectObjs(queryWrapper).stream()
                                .filter(Objects::nonNull)
                                .map(Object::toString)
                                .collect(Collectors.toList());
        }

}
