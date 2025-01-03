package com.biology.domain.system.post.model;

import cn.hutool.core.bean.BeanUtil;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode;
import com.biology.domain.system.post.command.AddPostCommand;
import com.biology.domain.system.post.command.UpdatePostCommand;
import com.biology.domain.system.post.db.SysPostEntity;
import com.biology.domain.system.post.db.SysPostService;
import lombok.NoArgsConstructor;

/**
 * @author valarchie
 */
@NoArgsConstructor
public class PostModel extends SysPostEntity {

    private SysPostService postService;

    public PostModel(SysPostService postService) {
        this.postService = postService;
    }

    public PostModel(SysPostEntity entity, SysPostService postService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.postService = postService;
    }

    public void loadFromAddCommand(AddPostCommand addCommand) {
        if (addCommand != null) {
            BeanUtil.copyProperties(addCommand, this, "postId");
        }
    }

    public void loadFromUpdateCommand(UpdatePostCommand command) {
        if (command != null) {
            loadFromAddCommand(command);
        }
    }

    public void checkCanBeDelete() {
        if (postService.isAssignedToUsers(this.getPostId())) {
            throw new ApiException(ErrorCode.Business.POST_ALREADY_ASSIGNED_TO_USER_CAN_NOT_BE_DELETED);
        }
    }

    public void checkPostNameUnique() {
        if (postService.isPostNameDuplicated(getPostId(), getPostName())) {
            throw new ApiException(ErrorCode.Business.POST_NAME_IS_NOT_UNIQUE, getPostName());
        }
    }

    public void checkPostCodeUnique() {
        if (postService.isPostCodeDuplicated(getPostId(), getPostCode())) {
            throw new ApiException(ErrorCode.Business.POST_CODE_IS_NOT_UNIQUE, getPostCode());
        }
    }

}
