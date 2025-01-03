package com.biology.domain.manage.craftprocess.query;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.craftprocess.db.CraftProcessEntity;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchCraftProcessQuery extends AbstractPageQuery<CraftProcessEntity> {

    private String craftNode;
    private String craftArchiveName;
    private Long craftArchiveId;
    // 根据ids查询
    private List<Long> ids;

    @Override
    public QueryWrapper<CraftProcessEntity> addQueryCondition() {
        QueryWrapper<CraftProcessEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.like(StrUtil.isNotEmpty(craftNode), "craft_node", craftNode);

        if (StrUtil.isNotEmpty(craftNode)) {
            queryWrapper.inSql("craft_node_id",
                    "SELECT craft_node_id FROM manage_craft_node WHERE node_name LIKE '%" + craftNode + "%'");
        }

        if (StrUtil.isNotEmpty(craftArchiveName)) {
            queryWrapper.inSql("craft_archive_id",
                    "SELECT craft_archive_id FROM manage_craft_archive WHERE craft_archive_name LIKE '%"
                            + craftArchiveName + "%'");
        }
        if (craftArchiveId != null) {
            queryWrapper.eq("craft_archive_id", craftArchiveId);
        }

        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("craft_process_id", ids);
        }

        this.timeRangeColumn = "create_time";
        return queryWrapper;
    }
}
