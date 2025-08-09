package com.biology.domain.manage.xlFangAn.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class XlFangAnServiceImpl extends ServiceImpl<XlFangAnMapper, XlFangAnEntity> implements XlFangAnService {

    public List<String> getPersonnelGroup() {
        return baseMapper.getPersonnelGroup();
    }
}
