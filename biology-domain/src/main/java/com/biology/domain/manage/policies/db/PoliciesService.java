package com.biology.domain.manage.policies.db;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

public interface PoliciesService extends IService<PoliciesEntity> {

    List<String> getAppendixPaths(Long policiesId);
}
