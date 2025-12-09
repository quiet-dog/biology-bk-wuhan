package com.biology.domain.manage.xunJian.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xunJian.db.XunJianEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XunJianQuery extends AbstractPageQuery<XunJianEntity> {

    private String xunJianPinLu;

    @Override
    public QueryWrapper<XunJianEntity> addQueryCondition() {
        QueryWrapper<XunJianEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(!StrUtil.isEmpty(getXunJianPinLu()), "xun_jian_pin_lu", getXunJianPinLu());
        return queryWrapper;
    }

}
