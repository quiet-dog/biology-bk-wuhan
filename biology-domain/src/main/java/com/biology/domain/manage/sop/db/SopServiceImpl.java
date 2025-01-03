package com.biology.domain.manage.sop.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SopServiceImpl extends ServiceImpl<SopMapper, SopEntity> implements SopService {

}
