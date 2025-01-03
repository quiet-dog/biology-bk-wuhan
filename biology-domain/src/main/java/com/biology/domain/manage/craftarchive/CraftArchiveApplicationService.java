package com.biology.domain.manage.craftarchive;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Internal;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.craftarchive.command.AddCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.command.UpdateCraftArchiveCommand;
import com.biology.domain.manage.craftarchive.db.CraftArchiveEntity;
import com.biology.domain.manage.craftarchive.db.CraftArchiveService;
import com.biology.domain.manage.craftarchive.dto.CraftArchiveDTO;
import com.biology.domain.manage.craftarchive.model.CraftArchiveFactory;
import com.biology.domain.manage.craftarchive.model.CraftArchiveModel;
import com.biology.domain.manage.craftarchive.query.SearchCraftArchiveQuery;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CraftArchiveApplicationService {

    private final CraftArchiveFactory craftArchiveFactory;
    private final CraftArchiveService craftArchiveService;

    public void createCraftArchive(AddCraftArchiveCommand command) {
        CraftArchiveModel craftArchiveModel = craftArchiveFactory.create();
        craftArchiveModel.loadAddCommand(command);
        craftArchiveModel.insert();
    }

    public void updateCraftArchive(UpdateCraftArchiveCommand command) {
        CraftArchiveModel craftArchiveModel = craftArchiveFactory.loadById(command.getCraftArchiveId());
        craftArchiveModel.loadUpdateCommand(command);
        craftArchiveModel.update();
    }

    public void deleteCraftArchives(List<Long> craftArchiveIds) {
        for (Long craftArchiveId : craftArchiveIds) {
            CraftArchiveModel craftArchiveModel = craftArchiveFactory.loadById(craftArchiveId);
            craftArchiveModel.deleteById();
        }
    }

    public PageDTO<CraftArchiveDTO> getCraftArchiveList(SearchCraftArchiveQuery query) {

        if (CollectionUtil.isNotEmpty(query.getIds())) {
            query.setPageNum(1);
            query.setPageSize(query.getIds().size());
        } else {
            // 如果pageSize为和pageNum为0，则不分页
            if (query.getPageSize() == 0 && query.getPageNum() == 0) {
                query.setPageSize(Integer.MAX_VALUE);
                query.setPageNum(1);
            }
        }

        Page<CraftArchiveEntity> page = craftArchiveService.page(query.toPage(), query.toQueryWrapper());
        List<CraftArchiveDTO> records = page.getRecords().stream().map(CraftArchiveDTO::new)
                .collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public CraftArchiveDTO getCraftArchiveInfo(Long craftArchiveId) {
        CraftArchiveModel craftArchiveModel = craftArchiveFactory.loadById(craftArchiveId);
        return new CraftArchiveDTO(craftArchiveModel);
    }

    public void export(HttpServletResponse response, SearchCraftArchiveQuery query)
            throws UnsupportedEncodingException {
        String fileName = "工艺档案列表_" + System.currentTimeMillis() + ".xlsx";
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName);

        PageDTO<CraftArchiveDTO> pageResult = this.getCraftArchiveList(query);

        CustomExcelUtil.writeToResponse(pageResult.getRows(), CraftArchiveDTO.class, response);
    }

    public void downloadImportTemplate(HttpServletResponse response) throws IOException {
        String fileName = "工艺档案导入模板_" + System.currentTimeMillis() + ".xlsx";
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + encodedFileName);

        // 创建一个空的CraftArchiveDTO作为模板
        CraftArchiveDTO template = new CraftArchiveDTO(new CraftArchiveEntity());
        List<CraftArchiveDTO> templateList = new ArrayList<>();
        templateList.add(template);

        // 导出模板
        CustomExcelUtil.writeToOutputStreamForImportTemp(templateList, CraftArchiveDTO.class,
                response.getOutputStream());
    }

}
