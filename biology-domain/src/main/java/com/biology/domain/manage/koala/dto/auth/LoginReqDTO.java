package com.biology.domain.manage.koala.dto.auth;

import lombok.Data;

@Data
public class LoginReqDTO {
    private String username;
    private String password;
    private Boolean auth_token;
}
