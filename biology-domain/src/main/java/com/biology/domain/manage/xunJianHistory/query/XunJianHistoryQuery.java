package com.biology.domain.manage.xunJianHistory.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.xunJian.db.XunJianEntity;
import com.biology.domain.manage.xunJianHistory.db.XunJianHistoryEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class XunJianHistoryQuery extends AbstractPageQuery<XunJianHistoryEntity> {

    private String status;

    private String area;

    @Override
    public QueryWrapper<XunJianHistoryEntity> addQueryCondition() {
        QueryWrapper<XunJianHistoryEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .eq(!StrUtil.isEmpty(status), "status", status)
                .inSql(!StrUtil.isEmpty(area), "xun_jian_id",
                        "SELECT xun_jian_id FROM manage_xun_jian WHERE fan_wei like '%" + area + "%'");
        // 按照status为巡检中的在前面
        // queryWrapper.orderByDesc("status", "create_time");
        /**
         * CASE
         * WHEN status = '巡检中' THEN 0
         * ELSE 1
         * END,
         */
        setTimeRangeColumn("create_time");
        setOrderColumn(null);
        queryWrapper.last("ORDER BY CASE WHEN status = '巡检中' THEN 0 ELSE 1 END");
        return queryWrapper;
    }

}
