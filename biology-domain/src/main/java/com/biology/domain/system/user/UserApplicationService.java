package com.biology.domain.system.user;

import cn.hutool.core.convert.Convert;
import com.biology.common.core.page.PageDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.common.command.BulkOperationCommand;
import com.biology.domain.common.dto.CurrentLoginUserDTO;
import com.biology.domain.manage.notification.NotificationApplicationService;
import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.manage.notification.db.NotificationEntity;
import com.biology.domain.manage.notification.dto.NotificationDTO;
import com.biology.domain.manage.notification.query.SearchNotificationQuery;
import com.biology.domain.system.post.dto.PostDTO;
import com.biology.domain.system.role.dto.RoleDTO;
import com.biology.domain.system.role.query.AllocatedRoleQuery;
import com.biology.domain.system.user.command.AddUserCommand;
import com.biology.domain.system.user.command.ChangeStatusCommand;
import com.biology.domain.system.user.command.ResetPasswordCommand;
import com.biology.domain.system.user.command.UpdateProfileCommand;
import com.biology.domain.system.user.command.UpdateUserAvatarCommand;
import com.biology.domain.system.user.command.UpdateUserCommand;
import com.biology.domain.system.user.command.UpdateUserPasswordCommand;
import com.biology.domain.system.user.db.SearchUserDO;
import com.biology.domain.system.user.dto.UserDTO;
import com.biology.domain.system.user.dto.UserDetailDTO;
import com.biology.domain.system.user.dto.UserProfileDTO;
import com.biology.domain.system.user.model.UserModel;
import com.biology.domain.system.user.model.UserModelFactory;
import com.biology.domain.system.user.query.SearchUserQuery;
import com.biology.infrastructure.user.web.SystemLoginUser;
import com.biology.domain.system.post.db.SysPostEntity;
import com.biology.domain.system.role.db.SysRoleEntity;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.system.post.db.SysPostService;
import com.biology.domain.system.role.db.SysRoleService;
import com.biology.domain.system.user.db.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author valarchie
 */
@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final SysUserService userService;

    private final SysRoleService roleService;

    private final SysPostService postService;

    private final UserModelFactory userModelFactory;

    private final NotificationApplicationService notificationApplicationService;

    public PageDTO<UserDTO> getUserList(SearchUserQuery<SearchUserDO> query) {
        Page<SearchUserDO> userPage = userService.getUserList(query);
        List<UserDTO> userDTOList = userPage.getRecords().stream().map(UserDTO::new).collect(Collectors.toList());
        return new PageDTO<>(userDTOList, userPage.getTotal());
    }

    public UserProfileDTO getUserProfile(Long userId) {

        SysUserEntity userEntity = userService.getById(userId);
        SysPostEntity postEntity = userService.getPostOfUser(userId);
        SysRoleEntity roleEntity = userService.getRoleOfUser(userId);

        return new UserProfileDTO(userEntity, postEntity, roleEntity);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息
     */
    public CurrentLoginUserDTO getLoginUserInfo(SystemLoginUser loginUser) {
        CurrentLoginUserDTO permissionDTO = new CurrentLoginUserDTO();
        SysUserEntity id = CacheCenter.userCache.getObjectById(loginUser.getUserId());
        permissionDTO.setUserInfo(new UserDTO(id));
        permissionDTO.setRoleKey(loginUser.getRoleInfo().getRoleKey());
        permissionDTO.setPermissions(loginUser.getRoleInfo().getMenuPermissions());

        SysUserEntity userEntity = userService.getById(loginUser.getUserId());

        if (userEntity.getLastPasswordResetTime() == null) {
            Instant createTimeInstant = userEntity.getCreateTime().toInstant();
            LocalDateTime createTime = createTimeInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();

            long daysBetween = ChronoUnit.DAYS.between(
                    createTime, LocalDate.now().atStartOfDay());

            if (daysBetween > 29) {
                // 发送密码修改通知
                // 查找是否存在密码修改通知
                SearchNotificationQuery searchNotificationQuery = new SearchNotificationQuery();
                searchNotificationQuery.setNotificationType("password_reset");
                searchNotificationQuery.setUserId(userEntity.getUserId());
                searchNotificationQuery.setIsPersonal(true);
                PageDTO<NotificationDTO> notificationPage = notificationApplicationService
                        .getNotificationList(searchNotificationQuery);
                if (notificationPage.getTotal() == 0) {

                    AddNotificationCommand addNotificationCommand = new AddNotificationCommand();
                    addNotificationCommand.setNotificationTitle("密码修改通知");
                    addNotificationCommand.setNotificationContent("您的密码已超过30天未修改，请及时修改密码");
                    addNotificationCommand.setNotificationType("password_reset");
                    addNotificationCommand.setReceiverId(userEntity.getUserId());

                    // 发送密码修改通知
                    notificationApplicationService.addNotification(addNotificationCommand);
                }
            }
        } else {
            Instant lastPasswordResetTimeInstant = userEntity.getLastPasswordResetTime().toInstant();
            LocalDateTime lastPasswordResetTime = lastPasswordResetTimeInstant.atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            long daysBetween = ChronoUnit.DAYS.between(lastPasswordResetTime, LocalDate.now().atStartOfDay());
            if (daysBetween > 29) {
                // 发送密码修改通知
                // 查找是否存在密码修改通知
                SearchNotificationQuery searchNotificationQuery = new SearchNotificationQuery();
                searchNotificationQuery.setNotificationType("password_reset");
                searchNotificationQuery.setUserId(userEntity.getUserId());
                searchNotificationQuery.setIsPersonal(true);
                PageDTO<NotificationDTO> notificationPage = notificationApplicationService
                        .getNotificationList(searchNotificationQuery);
                if (notificationPage.getTotal() == 0) {

                    AddNotificationCommand addNotificationCommand = new AddNotificationCommand();
                    addNotificationCommand.setNotificationTitle("密码修改通知");
                    addNotificationCommand.setNotificationContent("您的密码已超过30天未修改，请及时修改密码");
                    addNotificationCommand.setNotificationType("password_reset");
                    addNotificationCommand.setReceiverId(userEntity.getUserId());

                    // 发送密码修改通知
                    notificationApplicationService.addNotification(addNotificationCommand);
                }
            }
        }
        return permissionDTO;
    }

    public void updateUserProfile(UpdateProfileCommand command) {
        UserModel userModel = userModelFactory.loadById(command.getUserId());
        userModel.loadUpdateProfileCommand(command);

        userModel.checkPhoneNumberIsUnique();
        userModel.checkEmailIsUnique();

        userModel.updateById();

        CacheCenter.userCache.delete(userModel.getUserId());
    }

    @SuppressWarnings("null")
    public UserDetailDTO getUserDetailInfo(Long userId) {
        SysUserEntity userEntity = userService.getById(userId);
        UserDetailDTO detailDTO = new UserDetailDTO();

        LambdaQueryWrapper<SysRoleEntity> roleQuery = new LambdaQueryWrapper<SysRoleEntity>()
                .orderByAsc(SysRoleEntity::getRoleSort);
        List<RoleDTO> roleDtoList = roleService.list(roleQuery).stream().map(RoleDTO::new).collect(Collectors.toList());
        List<PostDTO> postDtoList = postService.list().stream().map(PostDTO::new).collect(Collectors.toList());
        detailDTO.setRoleOptions(roleDtoList);
        detailDTO.setPostOptions(postDtoList);

        if (userEntity != null) {
            detailDTO.setUser(new UserDTO(userEntity));
            detailDTO.setRoleId(userEntity.getRoleId());
            detailDTO.setPostId(userEntity.getPostId());
        }

        return detailDTO;
    }

    public void addUser(AddUserCommand command) {
        UserModel model = userModelFactory.create();
        model.loadAddUserCommand(command);

        model.checkUsernameIsUnique();
        model.checkPhoneNumberIsUnique();
        model.checkEmailIsUnique();
        model.checkFieldRelatedEntityExist();
        model.resetPassword(command.getPassword());

        model.insert();
    }

    public void updateUser(UpdateUserCommand command) {
        UserModel model = userModelFactory.loadById(command.getUserId());

        // 获取原始角色ID
        Long originalRoleId = model.getRoleId();

        model.loadUpdateUserCommand(command);
        // 判断角色是否变更
        if (originalRoleId != null && !originalRoleId.equals(model.getRoleId())) {
            // 发送权限变更通知
            AddNotificationCommand roleChangeNotification = new AddNotificationCommand();
            roleChangeNotification.setNotificationTitle("用户权限变更");
            roleChangeNotification.setNotificationContent("您的用户权限已变更,请重新登录!");
            roleChangeNotification.setNotificationType("user_role_change");
            roleChangeNotification.setReceiverId(model.getUserId());
            notificationApplicationService.addNotification(roleChangeNotification);
        } else {
            // 发送普通信息更新通知
            AddNotificationCommand addNotificationCommand = new AddNotificationCommand();
            addNotificationCommand.setNotificationTitle("用户信息更新");
            addNotificationCommand.setNotificationContent("您的用户信息已更新!");
            addNotificationCommand.setNotificationType("user_info_update");
            addNotificationCommand.setReceiverId(model.getUserId());
            notificationApplicationService.addNotification(addNotificationCommand);
        }

        model.checkPhoneNumberIsUnique();
        model.checkEmailIsUnique();
        model.checkFieldRelatedEntityExist();
        model.updateById();

        CacheCenter.userCache.delete(model.getUserId());
    }

    public void deleteUsers(SystemLoginUser loginUser, BulkOperationCommand<Long> command) {
        for (Long id : command.getIds()) {
            UserModel userModel = userModelFactory.loadById(id);
            userModel.checkCanBeDelete(loginUser);
            userModel.deleteById();
        }
    }

    public void updatePasswordBySelf(SystemLoginUser loginUser, UpdateUserPasswordCommand command) {
        UserModel userModel = userModelFactory.loadById(command.getUserId());
        userModel.modifyPassword(command);
        try {
            Date currentDate = new Date();
            // 定义日期时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 将当前时间格式化为字符串
            String formattedDate = sdf.format(currentDate);

            // 将格式化后的字符串解析为 Date 类型
            Date formattedDateAsDate = sdf.parse(formattedDate);

            userModel.setLastPasswordResetTime(formattedDateAsDate);
        } catch (Exception e) {
            throw new ApiException(Business.COMMON_UNSUPPORTED_OPERATION);
        }
        userModel.updateById();

        // 删除通知
        SearchNotificationQuery searchNotificationQuery = new SearchNotificationQuery();
        searchNotificationQuery.setNotificationType("password_reset");
        searchNotificationQuery.setUserId(userModel.getUserId());
        searchNotificationQuery.setIsPersonal(true);

        // 查找是否存在密码修改通知
        PageDTO<NotificationDTO> notificationPage = notificationApplicationService
                .getNotificationList(searchNotificationQuery);
        if (notificationPage.getTotal() > 0) {
            notificationApplicationService.deleteNotifications(notificationPage.getRows().stream()
                    .map(NotificationDTO::getNotificationId).collect(Collectors.toList()));
        }

        CacheCenter.userCache.delete(userModel.getUserId());
    }

    public void resetUserPassword(ResetPasswordCommand command) {
        UserModel userModel = userModelFactory.loadById(command.getUserId());

        userModel.resetPassword(command.getPassword());
        userModel.updateById();

        // 发送密码修改通知
        AddNotificationCommand addNotificationCommand = new AddNotificationCommand();
        addNotificationCommand.setNotificationTitle("密码修改通知");
        addNotificationCommand.setNotificationContent("您的密码已修改!");
        addNotificationCommand.setNotificationType("password_udpate");
        addNotificationCommand.setReceiverId(userModel.getUserId());
        notificationApplicationService.addNotification(addNotificationCommand);

        // 删除通知
        SearchNotificationQuery searchNotificationQuery = new SearchNotificationQuery();
        searchNotificationQuery.setNotificationType("password_reset");
        searchNotificationQuery.setUserId(userModel.getUserId());
        searchNotificationQuery.setIsPersonal(true);

        // 查找是否存在密码修改通知
        PageDTO<NotificationDTO> notificationPage = notificationApplicationService
                .getNotificationList(searchNotificationQuery);
        if (notificationPage.getTotal() > 0) {
            notificationApplicationService.deleteNotifications(notificationPage.getRows().stream()
                    .map(NotificationDTO::getNotificationId).collect(Collectors.toList()));
        }

        CacheCenter.userCache.delete(userModel.getUserId());
    }

    public void changeUserStatus(ChangeStatusCommand command) {
        UserModel userModel = userModelFactory.loadById(command.getUserId());

        userModel.setStatus(Convert.toInt(command.getStatus()));
        userModel.updateById();

        // 发送用户状态更新通知给用户
        AddNotificationCommand addNotificationCommand = new AddNotificationCommand();
        addNotificationCommand.setNotificationTitle("用户状态更新");
        addNotificationCommand.setNotificationContent("您的用户状态已更新!");
        addNotificationCommand.setNotificationType("user_status_update");
        addNotificationCommand.setReceiverId(userModel.getUserId());
        notificationApplicationService.addNotification(addNotificationCommand);

        CacheCenter.userCache.delete(userModel.getUserId());
    }

    public void updateUserAvatar(UpdateUserAvatarCommand command) {
        UserModel userModel = userModelFactory.loadById(command.getUserId());

        userModel.setAvatar(command.getAvatar());
        userModel.updateById();

        CacheCenter.userCache.delete(userModel.getUserId());
    }

    // 是否超过30天未修改密码
    public Boolean isOverduePassword(SystemLoginUser user) {
        LocalDate currentDate = LocalDate.now();

        SysUserEntity userEntity = userService.getById(user.getUserId());
        if (userEntity == null) {
            return false;
        }
        if (userEntity.getLastPasswordResetTime() == null) {
            Instant createTimeInstant = userEntity.getCreateTime().toInstant();
            LocalDateTime createTime = createTimeInstant.atZone(ZoneId.systemDefault()).toLocalDateTime(); // 转换为
                                                                                                           // LocalDateTime
            // 判断创建时间是否超过30天
            long daysBetween = ChronoUnit.DAYS.between(
                    createTime, currentDate.atStartOfDay() // 使用当前日期的00:00作为时间
            );

            if (daysBetween > 30) {
                return true;
            }

            return false;
        }
        Instant lastPasswordResetTimeInstant = userEntity.getLastPasswordResetTime().toInstant(); // 转换为 Instant
        LocalDateTime lastPasswordResetTime = lastPasswordResetTimeInstant.atZone(ZoneId.systemDefault())
                .toLocalDateTime(); // 转换为 LocalDateTime

        // 判断最后修改密码时间是否超过30天
        long daysBetween = ChronoUnit.DAYS.between(lastPasswordResetTime, currentDate.atStartOfDay()); // 使用当前日期的00:00作为时间

        if (daysBetween > 30) {
            return true;
        }
        return false;
    }

}
