package com.biology.domain.manage.receive.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.receive.db.ReceiveEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReceiveQuery extends AbstractPageQuery<ReceiveEntity> {

    @Schema(description = "领用人姓名")
    private String name;

    @Schema(description = "领用人部门")
    private String department;

    @Schema(description = "物资名称")
    private String materialName;

    @Override
    public QueryWrapper<ReceiveEntity> addQueryCondition() {
        QueryWrapper<ReceiveEntity> queryWrapper = new QueryWrapper<ReceiveEntity>();
        queryWrapper.inSql(StrUtil.isNotBlank(name), "receive_user_id",
                "select personnel_id from manage_personnel where name like '%" + name + "%'")
                .inSql(StrUtil.isNotBlank(department), "receive_user_id",
                        "select personnel_id from manage_personnel where department like '%" + department + "%'")
                .inSql(StrUtil.isNotBlank(materialName), "materials_id",
                        "select materials_id from manage_materials where name like '%" + materialName + "%'");

        setTimeRangeColumn("create_time");

        return queryWrapper;
    }

}
