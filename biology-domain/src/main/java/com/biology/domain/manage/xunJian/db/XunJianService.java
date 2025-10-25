package com.biology.domain.manage.xunJian.db;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;

public interface XunJianService extends IService<XunJianEntity> {
    public List<Long> getAllEquipmentByArea(String area);

    public List<Long> getAllEnvironmentByArea(String area);

}
