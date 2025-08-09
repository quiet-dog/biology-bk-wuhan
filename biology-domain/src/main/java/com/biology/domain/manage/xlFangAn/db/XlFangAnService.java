package com.biology.domain.manage.xlFangAn.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface XlFangAnService extends IService<XlFangAnEntity> {

    public List<String> getPersonnelGroup();
}
