package com.biology.domain.common.dto;

import com.biology.domain.system.user.dto.UserDTO;
import java.util.Set;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class CurrentLoginUserDTO {

    private UserDTO userInfo;
    private String roleKey;
    private Set<String> permissions;
    // 是否超过30天未修改密码；
    private Boolean isOverdue;

}
