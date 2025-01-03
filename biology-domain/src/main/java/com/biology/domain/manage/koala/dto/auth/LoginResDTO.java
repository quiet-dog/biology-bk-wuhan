package com.biology.domain.manage.koala.dto.auth;

import java.util.ArrayList;

import lombok.Data;

@Data
public class LoginResDTO {
    public String auth_token;
    public String avatar;
    public Object company_id;
    public int id;
    public String lang;
    public String lang_code;
    public Object organization_id;
    public boolean password_reseted;
    public ArrayList<Object> permission;
    public int role_id;
    public String username;
    public boolean verify;
}
