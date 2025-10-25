package com.biology.domain.manage.xunJianHistory.db;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;

public interface XunJianHistoryService extends IService<XunJianHistoryEntity> {

    public Integer getTotalByHistoryId(Long xunJianHistoryId);
}
