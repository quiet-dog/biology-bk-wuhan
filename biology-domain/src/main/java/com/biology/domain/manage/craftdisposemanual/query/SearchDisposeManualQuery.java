package com.biology.domain.manage.craftdisposemanual.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.craftdisposemanual.db.DisposeManualEntity;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchDisposeManualQuery extends AbstractPageQuery<DisposeManualEntity> {

    @ApiModelProperty("工艺节点")
    private String craftNode;
    @ApiModelProperty("责任划分")
    private String responsibilityDivision;

    @Override
    public QueryWrapper<DisposeManualEntity> addQueryCondition() {
        QueryWrapper<DisposeManualEntity> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotEmpty(craftNode)) {
            queryWrapper.inSql("craft_node_id",
                    "SELECT craft_node_id FROM manage_craft_node WHERE node_name LIKE '%" + craftNode + "%'");
        }

        queryWrapper
                .like(StrUtil.isNotEmpty(responsibilityDivision), "responsibility_division", responsibilityDivision);
        this.timeRangeColumn = "create_time";
        return queryWrapper;
    }
}
