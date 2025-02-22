package com.biology.domain.common.cache;

import cn.hutool.extra.spring.SpringUtil;
import com.biology.infrastructure.cache.guava.AbstractGuavaCacheTemplate;
import com.biology.infrastructure.cache.redis.RedisCacheTemplate;
import com.biology.infrastructure.user.web.SystemLoginUser;
import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.dto.EmergencyDTO;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.manage.sop.dto.SopDTO;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.websocket.dto.OnlineDTO;
import com.biology.domain.system.dept.db.SysDeptEntity;
import com.biology.domain.system.post.db.SysPostEntity;
import com.biology.domain.system.role.db.SysRoleEntity;
import com.biology.domain.system.user.db.SysUserEntity;

import java.util.List;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * 缓存中心 提供全局访问点
 * 如果是领域类的缓存 可以自己新建一个直接放在CacheCenter 不用放在infrastructure包里的GuavaCacheService
 * 或者RedisCacheService
 * 
 * @author valarchie
 */
@Component
public class CacheCenter {

    public static AbstractGuavaCacheTemplate<String> configCache;

    public static AbstractGuavaCacheTemplate<SysDeptEntity> deptCache;

    public static RedisCacheTemplate<String> captchaCache;

    public static RedisCacheTemplate<SystemLoginUser> loginUserCache;

    public static RedisCacheTemplate<SysUserEntity> userCache;

    public static RedisCacheTemplate<SysRoleEntity> roleCache;

    public static RedisCacheTemplate<SysPostEntity> postCache;

    public static RedisCacheTemplate<KnowledgeTypeEntity> knowledgeTypeCache;

    public static RedisCacheTemplate<OnlineDTO> onlineCache;

    public static RedisCacheTemplate<List<ThresholdValueEntity>> thresholdValuesCache;

    public static RedisCacheTemplate<List<EmergencyDTO>> thrsholdEmergencyCache;

    public static RedisCacheTemplate<List<SopDTO>> thrsholdSopCache;

    @PostConstruct
    public void init() {
        GuavaCacheService guavaCache = SpringUtil.getBean(GuavaCacheService.class);
        RedisCacheService redisCache = SpringUtil.getBean(RedisCacheService.class);

        configCache = guavaCache.configCache;
        deptCache = guavaCache.deptCache;

        captchaCache = redisCache.captchaCache;
        loginUserCache = redisCache.loginUserCache;
        userCache = redisCache.userCache;
        roleCache = redisCache.roleCache;
        postCache = redisCache.postCache;
        knowledgeTypeCache = redisCache.knowledgeTypeCache;
        onlineCache = redisCache.onlineCache;
        thresholdValuesCache = redisCache.thresholdValuesCache;
        // thrsholdEmergencyCache = redisCache.thrsholdEmergencyCache;
        // thrsholdSopCache = redisCache.thrsholdSopCache;
    }

}
