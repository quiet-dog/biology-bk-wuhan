package com.biology.domain.system.role;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import com.biology.common.core.page.AbstractPageQuery;
import com.biology.common.core.page.PageDTO;
import com.biology.domain.common.cache.CacheCenter;
import com.biology.domain.manage.notification.NotificationApplicationService;
import com.biology.domain.manage.notification.command.AddNotificationCommand;
import com.biology.domain.system.role.command.AddRoleCommand;
import com.biology.domain.system.role.command.UpdateDataScopeCommand;
import com.biology.domain.system.role.command.UpdateRoleCommand;
import com.biology.domain.system.role.command.UpdateStatusCommand;
import com.biology.domain.system.role.dto.RoleDTO;
import com.biology.domain.system.role.model.RoleModel;
import com.biology.domain.system.role.model.RoleModelFactory;
import com.biology.domain.system.role.query.AllocatedRoleQuery;
import com.biology.domain.system.role.query.RoleQuery;
import com.biology.domain.system.role.query.UnallocatedRoleQuery;
import com.biology.domain.system.user.dto.UserDTO;
import com.biology.domain.system.user.model.UserModel;
import com.biology.domain.system.user.model.UserModelFactory;
import com.biology.domain.system.user.query.SearchUserQuery;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

import com.biology.domain.system.role.db.SysRoleEntity;
import com.biology.domain.system.user.db.SearchUserDO;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.system.menu.db.SysMenuService;
import com.biology.domain.system.role.db.SysRoleService;
import com.biology.domain.system.user.db.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class RoleApplicationService {

    private final RoleModelFactory roleModelFactory;

    private final UserModelFactory userModelFactory;

    private final SysRoleService roleService;

    private final SysUserService userService;

    private final SysMenuService menuService;

    private final NotificationApplicationService notificationApplicationService;

    public PageDTO<RoleDTO> getRoleList(RoleQuery query) {
        Page<SysRoleEntity> page = roleService.page(query.toPage(), query.toQueryWrapper());
        List<RoleDTO> records = page.getRecords().stream().map(RoleDTO::new).collect(Collectors.toList());
        return new PageDTO<>(records, page.getTotal());
    }

    public RoleDTO getRoleInfo(Long roleId) {
        SysRoleEntity byId = roleService.getById(roleId);
        RoleDTO roleDTO = new RoleDTO(byId);
        List<Long> selectedDeptList = StrUtil.split(byId.getDeptIdSet(), ",")
                .stream().filter(StrUtil::isNotEmpty).map(Long::new).collect(Collectors.toList());
        roleDTO.setSelectedDeptList(selectedDeptList);
        roleDTO.setSelectedMenuList(menuService.getMenuIdsByRoleId(roleId));
        return roleDTO;
    }

    public void addRole(AddRoleCommand addCommand) {
        RoleModel roleModel = roleModelFactory.create();
        roleModel.loadAddCommand(addCommand);

        roleModel.checkRoleNameUnique();
        roleModel.checkRoleKeyUnique();

        roleModel.insert();
    }

    public void deleteRoleByBulk(List<Long> roleIds) {
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                RoleModel roleModel = roleModelFactory.loadById(roleId);

                roleModel.checkRoleCanBeDelete();

                roleModel.deleteById();
            }
        }
    }

    public void updateRole(UpdateRoleCommand updateCommand) {
        RoleModel roleModel = roleModelFactory.loadById(updateCommand.getRoleId());
        roleModel.loadUpdateCommand(updateCommand);

        roleModel.checkRoleKeyUnique();
        roleModel.checkRoleNameUnique();

        roleModel.updateById();

        // 发送角色权限更新通知给用户
        // 查询所有用户
        // 直接查询所有用户,不使用分页
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<>();
        List<SysUserEntity> userList = userService.list(queryWrapper);

        // 遍历用户列表
        for (SysUserEntity user : userList) {
            if (user.getRoleId() == null) {
                continue;
            }
            // 如果用户角色与更新角色相同，则发送通知
            if (user.getRoleId().equals(roleModel.getRoleId())) {
                // 发送通知
                AddNotificationCommand addNotificationCommand = new AddNotificationCommand();
                addNotificationCommand.setNotificationTitle("角色权限更新");
                addNotificationCommand.setNotificationContent("您的角色权限已更新!");
                addNotificationCommand.setNotificationType("role_permission_update");
                addNotificationCommand.setReceiverId(user.getUserId());
                notificationApplicationService.addNotification(addNotificationCommand);
            }
        }
    }

    public void updateStatus(UpdateStatusCommand command) {
        RoleModel roleModel = roleModelFactory.loadById(command.getRoleId());

        roleModel.setStatus(command.getStatus());

        roleModel.updateById();
    }

    public void updateDataScope(UpdateDataScopeCommand command) {
        RoleModel roleModel = roleModelFactory.loadById(command.getRoleId());

        roleModel.setDeptIds(command.getDeptIds());
        roleModel.setDataScope(command.getDataScope());
        roleModel.generateDeptIdSet();

        roleModel.updateById();
    }

    public PageDTO<UserDTO> getAllocatedUserList(AllocatedRoleQuery query) {
        Page<SysUserEntity> page = userService.getUserListByRole(query);
        List<UserDTO> dtoList = page.getRecords().stream().map(UserDTO::new).collect(Collectors.toList());
        return new PageDTO<>(dtoList, page.getTotal());
    }

    public PageDTO<UserDTO> getUnallocatedUserList(UnallocatedRoleQuery query) {
        Page<SysUserEntity> page = userService.getUserListByRole(query);
        List<UserDTO> dtoList = page.getRecords().stream().map(UserDTO::new).collect(Collectors.toList());
        return new PageDTO<>(dtoList, page.getTotal());
    }

    public void deleteRoleOfUserByBulk(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return;
        }

        for (Long userId : userIds) {
            LambdaUpdateWrapper<SysUserEntity> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(SysUserEntity::getRoleId, null).eq(SysUserEntity::getUserId, userId);

            userService.update(updateWrapper);

            CacheCenter.userCache.delete(userId);
        }
    }

    public void addRoleOfUserByBulk(Long roleId, List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return;
        }

        RoleModel roleModel = roleModelFactory.loadById(roleId);
        roleModel.checkRoleAvailable();

        for (Long userId : userIds) {
            UserModel user = userModelFactory.loadById(userId);
            user.setRoleId(roleId);
            user.updateById();

            CacheCenter.userCache.delete(userId);
        }
    }

}
