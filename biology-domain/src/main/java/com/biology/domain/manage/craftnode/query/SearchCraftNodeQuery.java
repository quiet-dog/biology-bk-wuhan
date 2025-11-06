package com.biology.domain.manage.craftnode.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.craftnode.db.CraftNodeEntity;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchCraftNodeQuery extends AbstractPageQuery<CraftNodeEntity> {

    private String nodeName;
    private String craftArchiveName;
    private Long craftArchiveId;
    private String nodeCode;
    private String craftArchiveCode;
    // 根据ids查询
    private List<Long> ids;

    @Override
    public QueryWrapper<CraftNodeEntity> addQueryCondition() {
        QueryWrapper<CraftNodeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(nodeName), "node_name", nodeName);
        queryWrapper.like(StrUtil.isNotEmpty(nodeCode), "node_code", nodeCode);
        queryWrapper.inSql(StrUtil.isNotEmpty(craftArchiveCode), "craft_archive_id",
                "SELECT craft_archive_id FROM manage_craft_archive WHERE craft_archive_code LIKE '%"
                        + craftArchiveCode + "%'");
        if (StrUtil.isNotEmpty(craftArchiveName)) {
            queryWrapper.inSql("craft_archive_id",
                    "SELECT craft_archive_id FROM manage_craft_archive WHERE craft_archive_name LIKE '%"
                            + craftArchiveName + "%'");
        }
        if (craftArchiveId != null) {
            queryWrapper.eq("craft_archive_id", craftArchiveId);
        }
        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("craft_node_id", ids);
        }
        this.timeRangeColumn = "create_time";
        return queryWrapper;
    }
}
