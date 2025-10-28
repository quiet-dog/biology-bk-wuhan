package com.biology.domain.manage.xunJian.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class XunJianServiceImpl extends ServiceImpl<XunJianMapper, XunJianEntity> implements XunJianService {
    public List<Long> getAllEquipmentByArea(String area) {
        return baseMapper.getAllEquipmentByArea(area);
    }

    public List<Long> getAllEnvironmentByArea(String area) {
        return baseMapper.getAllEquipmentByArea(area);
    }
}
