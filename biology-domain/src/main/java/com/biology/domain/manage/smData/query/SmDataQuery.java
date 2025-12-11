package com.biology.domain.manage.smData.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.smData.db.SmDataEntity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SmDataQuery extends AbstractPageQuery<SmDataEntity> {

    private String smDeviceSn;

    private List<Long> smDataIds;

    private List<String> dianWeis;

    private String personnelName;

    @Override
    public QueryWrapper<SmDataEntity> addQueryCondition() {
        QueryWrapper<SmDataEntity> queryWrapper = new QueryWrapper<SmDataEntity>();
        queryWrapper
                .in(smDataIds != null && smDataIds.size() > 0, "sm_data_id", getSmDataIds())
                .inSql(!StrUtil.isEmpty(getSmDeviceSn()), "sm_device_id",
                        String.format("select sm_device_id from manage_sm_device where device_sn like '%s'",
                                getSmDeviceSn()))
                .inSql(!StrUtil.isEmpty(getPersonnelName()), "sm_device_id",
                        String.format(
                                "select sm_device_id from manage_sm_device where personnel_id in (select personnel_id from manage_personnel where name like '%s')",
                                getPersonnelName()));

        // 创建时间倒序
        setOrderColumn("create_time");
        setOrderDirection("descending");

        return queryWrapper;
    }

}
