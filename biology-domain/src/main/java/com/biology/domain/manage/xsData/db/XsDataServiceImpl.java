package com.biology.domain.manage.xsData.db;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class XsDataServiceImpl extends ServiceImpl<XsDataMapper, XsDataEntity> implements XsDataService {

    @Override
    public List<Map<String, Object>> selectIsOnlineHistory(String xsDeviceId, String startTime, String endTime) {
        return baseMapper.selectIsOnlineHistory(xsDeviceId, startTime, endTime);
    }
}
