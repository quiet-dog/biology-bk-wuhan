
package com.biology.domain.manage.xlFangAn.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.smDevice.db.SmDeviceEntity;
import com.biology.domain.manage.xlFangAn.db.XlFangAnEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XlFangAnQuery extends AbstractPageQuery<XlFangAnEntity> {

    private String name;

    @Override
    public QueryWrapper<XlFangAnEntity> addQueryCondition() {
        QueryWrapper<XlFangAnEntity> queryWrapper = new QueryWrapper<XlFangAnEntity>();
        queryWrapper.like(!StrUtil.isEmpty(getName()), "name", getName());
        return queryWrapper;
    }

}