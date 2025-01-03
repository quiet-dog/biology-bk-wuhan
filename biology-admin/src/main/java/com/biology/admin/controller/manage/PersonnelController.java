package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.admin.customize.aop.accessLog.AccessLog;
import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.enums.common.BusinessTypeEnum;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.alarm.command.AddAlarmCommand;
import com.biology.domain.manage.personnel.PersonnelApplicationService;
import com.biology.domain.manage.personnel.command.AddPersonnelCommand;
import com.biology.domain.manage.personnel.command.UpdatePersonnelCommand;
import com.biology.domain.manage.personnel.db.PersonnelEntity;
import com.biology.domain.manage.personnel.db.PersonnelService;
import com.biology.domain.manage.personnel.dto.PersonnelDTO;
import com.biology.domain.manage.personnel.model.PersonnelFactory;
import com.biology.domain.manage.personnel.model.PersonnelModel;
import com.biology.domain.manage.personnel.query.PersonnelQuery;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "人员档案", description = "人员档案的增删查改")
@RestController
@RequestMapping("/manage/personnel")
@Validated
@RequiredArgsConstructor
public class PersonnelController extends BaseController {

    private final PersonnelApplicationService personnelApplicationService;

    private final PersonnelService personnelService;

    private final PersonnelFactory personnelFactory;

    @Operation(summary = "添加人员档案")
    @PostMapping
    public ResponseDTO<Void> add(@RequestBody AddPersonnelCommand command) {
        personnelApplicationService.create(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "更新人员档案")
    @PutMapping("/{personnelId}")
    public ResponseDTO<Void> update(@PathVariable("personnelId") Long personnelId,
            @Validated @RequestBody UpdatePersonnelCommand command) {
        personnelApplicationService.update(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除人员档案")
    @DeleteMapping
    public ResponseDTO<Void> delete(@RequestParam List<Long> personnelIds) {
        personnelApplicationService.deletePersonnels(personnelIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取人员档案列表")
    @GetMapping
    public ResponseDTO<PageDTO<PersonnelDTO>> list(PersonnelQuery query) {
        PageDTO<PersonnelDTO> list = personnelApplicationService.getPersonnels(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "获取知识库信息")
    @GetMapping("/{personnelId}")
    public ResponseDTO<PersonnelDTO> info(
            @PathVariable(value = "personnelId", required = false) Long personnelId) {
        PersonnelDTO personnelDTO = personnelApplicationService.getPersonnel(personnelId);
        return ResponseDTO.ok(personnelDTO);
    }

    @Operation(summary = "人员档案列表导出")
    @GetMapping("/excel")
    public void exportPersonnelByExcel(HttpServletResponse response, PersonnelQuery query) {
        PageDTO<PersonnelDTO> personnelList = personnelApplicationService.getPersonnels(query);
        CustomExcelUtil.writeToResponse(personnelList.getRows(), PersonnelDTO.class, response);
    }

    // 人员档案导入
    @Operation(summary = "人员档案导入")
    @PostMapping("/excel")
    public ResponseDTO<Void> importPersonnel(MultipartFile file) {
        List<AddPersonnelCommand> commands = CustomExcelUtil.readFromRequest(AddPersonnelCommand.class, file);
        for (AddPersonnelCommand command : commands) {
            QueryWrapper<PersonnelEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("code", command.getCode());
            PersonnelEntity pEntity = personnelService.getOne(queryWrapper);
            if (pEntity != null) {
                UpdatePersonnelCommand updatePersonnelCommand = new UpdatePersonnelCommand();
                BeanUtils.copyProperties(command, updatePersonnelCommand);
                PersonnelModel pModel = personnelFactory.loadById(pEntity.getPersonnelId());
                pModel.loadUpdatePersonnelCommand(updatePersonnelCommand);
                pModel.updateById();
            } else {
                personnelApplicationService.create(command);
            }

        }
        return ResponseDTO.ok();
    }

    @Operation(summary = "人员档案导入模板下载")
    @GetMapping("/excelTemplate")
    public void downloadExcelTemplate(HttpServletResponse response) {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new AddPersonnelCommand()), AddPersonnelCommand.class,
                response);
    }
}
