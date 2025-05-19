package com.biology.domain.manage.craftarchive.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.common.core.page.AbstractPageQuery;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchCraftArchiveQuery extends AbstractPageQuery<CraftArchiveEntity> {
    private String craftArchiveCode;
    private String craftArchiveName;
    private String version;
    // 根据ids查询
    private List<Long> ids;

    private Long craftArchiveId;

    private String exportType;

    @Override
    public QueryWrapper<CraftArchiveEntity> addQueryCondition() {
        QueryWrapper<CraftArchiveEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotEmpty(craftArchiveCode), "craft_archive_code", craftArchiveCode)
                .like(StrUtil.isNotEmpty(craftArchiveName), "craft_archive_name", craftArchiveName)
                .like(StrUtil.isNotEmpty(version), "version", version)
                .eq(craftArchiveId != null, "craft_archive_id", craftArchiveId);
        if (CollectionUtil.isNotEmpty(ids)) {
            queryWrapper.in("craft_archive_id", ids);
        }
        this.timeRangeColumn = "create_time";
        return queryWrapper;
    }
}