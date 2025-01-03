package com.biology.domain.manage.policies.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PoliciesAppendixServiceImpl extends ServiceImpl<PoliciesAppendixMapper, PoliciesAppendixEntity>
        implements PoliciesAppendixService {

}
