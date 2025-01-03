package com.biology.domain.system.user.dto;

import com.biology.domain.system.role.dto.RoleDTO;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class UserInfoDTO {

    private UserDTO user;
    private RoleDTO role;

}
