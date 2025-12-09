package com.biology.domain.manage.xunJian.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class XunJianServiceImpl extends ServiceImpl<XunJianMapper, XunJianEntity> implements XunJianService {
    public List<Long> getAllEquipmentByArea(List<String> areas) {
        String areaStr = String.join(",", areas);
        return baseMapper.getAllEquipmentByArea(areaStr);
    }

    public List<Long> getAllEnvironmentByArea(List<String> areas) {
        String areaStr = String.join(",", areas);
        return baseMapper.getAllEnvironmentByArea(areaStr);
    }

    public List<String> getAllEquipmentAreas() {
        return baseMapper.getAllEquipmentAreas();
    }

    public List<String> getAllEnvironmentAreas() {
        return baseMapper.getAllEnvironmentAreas();
    }
}
