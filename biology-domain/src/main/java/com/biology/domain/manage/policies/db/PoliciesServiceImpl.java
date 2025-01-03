package com.biology.domain.manage.policies.db;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PoliciesServiceImpl extends ServiceImpl<PoliciesMapper, PoliciesEntity> implements PoliciesService {
    @Override
    public List<String> getAppendixPaths(Long policiesId) {
        return baseMapper.getAppendixPaths(policiesId);
    }

}
