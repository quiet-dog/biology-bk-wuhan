package com.biology.admin.controller.manage;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

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

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.core.page.PageDTO;
import com.biology.common.utils.poi.CustomExcelUtil;
import com.biology.domain.manage.animation.command.AddAnimationCommand;
import com.biology.domain.manage.animation.command.SendAnimationCommand;
import com.biology.domain.manage.animation.command.UpdateAnimationCommand;
import com.biology.domain.manage.animation.dto.AnimationDTO;
import com.biology.domain.manage.animation.query.AnimationQuery;
import com.biology.domain.manage.animation.AnimationApplicationService;

import cn.hutool.core.collection.ListUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "动画API", description = "动画的增删查改")
@RestController
@RequestMapping("/manage/animation")
@Validated
@RequiredArgsConstructor
public class AnimationController extends BaseController {
    private final AnimationApplicationService animationApplicationService;

    @Operation(summary = "创建动画")
    @PostMapping
    public ResponseDTO<Void> createPolicy(@RequestBody AddAnimationCommand command) {
        animationApplicationService.addAnimation(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "获取动画信息")
    @GetMapping("{animationId}")
    public ResponseDTO<AnimationDTO> info(@PathVariable(value = "animationId", required = false) Long animationId) {
        AnimationDTO animationDTO = animationApplicationService.get(animationId);
        return ResponseDTO.ok(animationDTO);
    }

    @Operation(summary = "获取动画列表")
    @GetMapping
    public ResponseDTO<PageDTO<AnimationDTO>> list(AnimationQuery query) {
        PageDTO<AnimationDTO> list = animationApplicationService.getAnimationList(query);
        return ResponseDTO.ok(list);
    }

    @Operation(summary = "跟新动画")
    @PutMapping("{animationId}")
    public ResponseDTO<Void> edit(@RequestBody UpdateAnimationCommand command) {
        animationApplicationService.updateAnimation(command);
        return ResponseDTO.ok();
    }

    @Operation(summary = "删除动画")
    @DeleteMapping
    public ResponseDTO<Void> remove(@RequestParam @NotNull List<Long> animationIds) {
        animationApplicationService.deleteAnimations(animationIds);
        return ResponseDTO.ok();
    }

    @Operation(summary = "动画倒入")
    @PostMapping("/excel")
    public ResponseDTO<Void> importAnimationByExcel(MultipartFile file) {
        List<AddAnimationCommand> commands = CustomExcelUtil.readFromRequest(AddAnimationCommand.class, file);

        for (AddAnimationCommand command : commands) {
            animationApplicationService.addAnimation(command);
        }
        return ResponseDTO.ok();
    }

    /**
     * 下载批量导入模板
     */
    @Operation(summary = "动画导入excel下载")
    @GetMapping("/excelTemplate")
    public void downloadExcelTemplate(HttpServletResponse response) {
        CustomExcelUtil.writeToResponse(ListUtil.toList(new AddAnimationCommand()), AddAnimationCommand.class,
                response);
    }

    @Operation(summary = "控制动画开始")
    @PostMapping("/control")
    public ResponseDTO<Void> send(@RequestBody SendAnimationCommand command) {
        animationApplicationService.Send(command);
        return ResponseDTO.ok();
    }
}
