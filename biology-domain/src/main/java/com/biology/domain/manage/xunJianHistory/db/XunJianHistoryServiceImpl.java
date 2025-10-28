package com.biology.domain.manage.xunJianHistory.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class XunJianHistoryServiceImpl extends ServiceImpl<XunJianHistoryMapper, XunJianHistoryEntity>
                implements XunJianHistoryService {

        public Integer getTotalByHistoryId(Long xunJianHistoryId) {
                return baseMapper.getTotalByHistoryId(xunJianHistoryId);
        }

}
