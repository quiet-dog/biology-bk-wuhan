package com.biology.domain.manage.caiYangData.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.biology.domain.manage.caiYangData.query.CayYangLuanSheng;

@Service
public class CaiYangDataServiceImpl extends ServiceImpl<CaiYangDataMapper, CaiYangDataEntity>
                implements CaiYangDataService {

        @Override
        public Object getCaiYangDataOnlineHistory(CayYangLuanSheng query) {
                return baseMapper.selectIsOnlineHistory(query.getDeviceSn(), query.getStartTime(),
                                query.getEndTime());
        }

}
