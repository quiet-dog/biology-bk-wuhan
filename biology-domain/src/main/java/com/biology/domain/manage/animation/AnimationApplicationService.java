package com.biology.domain.manage.animation;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.manage.animation.command.AddAnimationCommand;
import com.biology.domain.manage.animation.command.SendAnimationCommand;
import com.biology.domain.manage.animation.command.UpdateAnimationCommand;
import com.biology.domain.manage.animation.db.AnimationEntity;
import com.biology.domain.manage.animation.db.AnimationService;
import com.biology.domain.manage.animation.dto.AnimationDTO;
import com.biology.domain.manage.animation.model.AnimationFactory;
import com.biology.domain.manage.animation.model.AnimationModel;
import com.biology.domain.manage.animation.query.AnimationQuery;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimationApplicationService {
    private final AnimationFactory animationFactory;

    private final AnimationService animationService;

    private final WebClient opcClient;

    public void addAnimation(AddAnimationCommand command) {
        AnimationModel AnimationModel = animationFactory.create();
        AnimationModel.loadAddCommand(command);
        AnimationModel.insert();
    }

    public void updateAnimation(UpdateAnimationCommand command) {
        AnimationModel AnimationModel = animationFactory.loadById(command.getAnimationId());
        AnimationModel.loadAddCommand(command);
        AnimationModel.updateById();
    }

    public void deleteAnimation(Long AnimationId) {
        AnimationModel AnimationModel = animationFactory.loadById(AnimationId);
        AnimationModel.deleteById();
    }

    public void deleteAnimations(List<Long> AnimationIds) {
        for (Long AnimationId : AnimationIds) {
            deleteAnimation(AnimationId);
        }
    }

    public PageDTO<AnimationDTO> getAnimationList(AnimationQuery query) {
        Page<AnimationEntity> page = animationService.page(query.toPage(), query.toQueryWrapper());
        List<AnimationDTO> records = page.getRecords().stream().map(AnimationDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public AnimationDTO get(Long AnimationId) {
        AnimationModel AnimationModel = animationFactory.loadById(AnimationId);
        return new AnimationDTO(AnimationModel);
    }

    public void Send(SendAnimationCommand command) {
        QueryWrapper<AnimationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(command.getType() != null, "type", command.getType());
        List<AnimationEntity> list = animationService.list(queryWrapper);
        if (list.isEmpty()) {
            return;
        }

        if (command.getType().equals("AnimationController")) {
            Map<String, Boolean> map = new HashMap<>();
            for (AnimationEntity entity : list) {
                Boolean status = false;
                if (command.getAnimationIds() != null && command.getAnimationIds().contains(entity.getAnimationId())) {
                    status = true;
                }
                map.put(entity.getKey(), status);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("Data", map);
            data.put("systemName", "AnimationController");
            opcClient.post()
                    .uri("/api/animation")
                    .bodyValue(data)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe();
        }

        if (command.getType().equals("送排风启闭灯")) {
            Map<String, Map<String, Boolean>> area = new HashMap<>();
            for (AnimationEntity entity : list) {
                if (entity.getType() == null || entity.getType().isEmpty() || entity.getKey() == null
                        || entity.getKey().isEmpty()) {
                    continue;
                }
                // 字符串根据 - 分割
                String[] parts = entity.getKey().split("-");
                if (parts.length < 2) {
                    continue;
                }
                // 先获取area有没有当前的key
                String areaKey = parts[0];
                Map<String, Boolean> dataArea = area.get(areaKey);
                if (dataArea == null) {
                    dataArea = new HashMap<>();
                    area.put(areaKey, dataArea);
                }
                Boolean status = false;
                if (command.getAnimationIds() != null && command.getAnimationIds().contains(entity.getAnimationId())) {
                    status = true;
                }
                dataArea.put(parts[1], status);
            }
            Map<String, Object> data = new HashMap<>();
            data.put("Data", area);
            data.put("systemName", "送排风启闭灯");
            opcClient.post()
                    .uri("/api/animation")
                    .bodyValue(data)
                    .retrieve()
                    .bodyToMono(String.class)
                    .subscribe();
        }

    }
}
