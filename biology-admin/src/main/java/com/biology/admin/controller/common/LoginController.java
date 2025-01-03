package com.biology.admin.controller.common;

import cn.hutool.core.util.StrUtil;
import com.biology.common.config.BiologyConfig;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.common.dto.CurrentLoginUserDTO;
import com.biology.domain.common.dto.TokenDTO;
import com.biology.domain.system.menu.MenuApplicationService;
import com.biology.domain.system.menu.dto.RouterDTO;
import com.biology.domain.system.user.UserApplicationService;
import com.biology.domain.system.user.command.AddUserCommand;
import com.biology.infrastructure.annotations.ratelimit.RateLimit;
import com.biology.infrastructure.annotations.ratelimit.RateLimit.CacheType;
import com.biology.infrastructure.annotations.ratelimit.RateLimit.LimitType;
import com.biology.infrastructure.user.AuthenticationUtils;
import com.biology.admin.customize.service.login.dto.CaptchaDTO;
import com.biology.admin.customize.service.login.dto.ConfigDTO;
import com.biology.admin.customize.service.login.command.LoginCommand;
import com.biology.infrastructure.user.web.SystemLoginUser;
import com.biology.infrastructure.annotations.ratelimit.RateLimitKey;
import com.biology.admin.customize.service.login.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author valarchie
 */
@Tag(name = "登录API", description = "登录相关接口")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    private final MenuApplicationService menuApplicationService;

    private final UserApplicationService userApplicationService;

    private final BiologyConfig agileBootConfig;

    /**
     * 访问首页，提示语
     */
    @Operation(summary = "首页")
    @GetMapping("/")
    @RateLimit(key = RateLimitKey.TEST_KEY, time = 10, maxCount = 5, cacheType = CacheType.Map, limitType = LimitType.GLOBAL)
    public String index() {
        return StrUtil.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。",
                agileBootConfig.getName(), agileBootConfig.getVersion());
    }

    /**
     * 获取系统的内置配置
     *
     * @return 配置信息
     */
    @GetMapping("/getConfig")
    public ResponseDTO<ConfigDTO> getConfig() {
        ConfigDTO configDTO = loginService.getConfig();
        return ResponseDTO.ok(configDTO);
    }

    /**
     * 生成验证码
     */
    @Operation(summary = "验证码")
    @RateLimit(key = RateLimitKey.LOGIN_CAPTCHA_KEY, time = 10, maxCount = 10, cacheType = CacheType.REDIS, limitType = LimitType.IP)
    @GetMapping("/captchaImage")
    public ResponseDTO<CaptchaDTO> getCaptchaImg() {
        CaptchaDTO captchaImg = loginService.generateCaptchaImg();
        return ResponseDTO.ok(captchaImg);
    }

    /**
     * 登录方法
     *
     * @param loginCommand 登录信息
     * @return 结果
     */
    @Operation(summary = "登录")
    @PostMapping("/login")
    public ResponseDTO<TokenDTO> login(@RequestBody LoginCommand loginCommand) {
        // 生成令牌
        String token = loginService.login(loginCommand);
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        CurrentLoginUserDTO currentUserDTO = userApplicationService.getLoginUserInfo(loginUser);
        currentUserDTO.setIsOverdue(userApplicationService.isOverduePassword(loginUser));
        return ResponseDTO.ok(new TokenDTO(token, currentUserDTO));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/getLoginUserInfo")
    public ResponseDTO<CurrentLoginUserDTO> getLoginUserInfo() {
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();

        CurrentLoginUserDTO currentUserDTO = userApplicationService.getLoginUserInfo(loginUser);

        return ResponseDTO.ok(currentUserDTO);
    }

    /**
     * 获取路由信息
     * TODO 如果要在前端开启路由缓存的话 需要在ServerConfig.json 中 设置CachingAsyncRoutes=true
     * 避免一直重复请求路由接口
     * 
     * @return 路由信息
     */
    @Operation(summary = "获取用户对应的菜单路由", description = "用于动态生成路由")
    @GetMapping("/getRouters")
    public ResponseDTO<List<RouterDTO>> getRouters() {
        SystemLoginUser loginUser = AuthenticationUtils.getSystemLoginUser();
        List<RouterDTO> routerTree = menuApplicationService.getRouterTree(loginUser);
        return ResponseDTO.ok(routerTree);
    }

    @Operation(summary = "注册接口", description = "暂未实现")
    @PostMapping("/register")
    public ResponseDTO<Void> register(@RequestBody AddUserCommand command) {
        return ResponseDTO.fail(new ApiException(Business.COMMON_UNSUPPORTED_OPERATION));
    }

}
