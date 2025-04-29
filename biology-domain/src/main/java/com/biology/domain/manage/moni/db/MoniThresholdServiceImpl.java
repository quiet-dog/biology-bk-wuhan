package com.biology.domain.manage.moni.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoniThresholdServiceImpl extends ServiceImpl<MoniThresholdMapper, MoniThresholdEntity>
        implements MoniThresholdService {

}
