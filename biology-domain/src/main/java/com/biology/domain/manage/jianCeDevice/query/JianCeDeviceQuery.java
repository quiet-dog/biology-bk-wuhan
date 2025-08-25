package com.biology.domain.manage.jianCeDevice.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.jianCeDevice.db.JianCeDeviceEntity;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JianCeDeviceQuery extends AbstractPageQuery<JianCeDeviceEntity> {

    @Override
    public QueryWrapper<JianCeDeviceEntity> addQueryCondition() {

        QueryWrapper<JianCeDeviceEntity> queryWrapper = new QueryWrapper<>();
        return queryWrapper;
    }

}
