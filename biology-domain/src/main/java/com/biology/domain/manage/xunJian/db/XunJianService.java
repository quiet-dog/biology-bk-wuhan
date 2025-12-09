package com.biology.domain.manage.xunJian.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;

public interface XunJianService extends IService<XunJianEntity> {
    public List<Long> getAllEquipmentByArea(List<String> areas);

    public List<Long> getAllEnvironmentByArea(List<String> areas);

    public List<String> getAllEquipmentAreas();

    public List<String> getAllEnvironmentAreas();
}
