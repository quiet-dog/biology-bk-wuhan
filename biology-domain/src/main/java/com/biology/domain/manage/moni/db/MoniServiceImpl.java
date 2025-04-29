package com.biology.domain.manage.moni.db;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoniServiceImpl extends ServiceImpl<MoniMapper, MoniEntity> implements MoniService {
    // 这里可以添加一些业务逻辑的方法
    // 例如：根据某个条件查询数据
    // List<MoniEntity> findByCondition(String condition);

}
