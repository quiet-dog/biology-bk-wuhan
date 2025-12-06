package com.biology.domain.manage.caiYangData.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.biology.domain.manage.caiYangData.query.CaiYangDataQuery;
import com.biology.domain.manage.caiYangData.query.CayYangLuanSheng;

public interface CaiYangDataService extends IService<CaiYangDataEntity> {

    public Object getCaiYangDataOnlineHistory(CayYangLuanSheng query);
}
