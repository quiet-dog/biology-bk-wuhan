package com.biology.infrastructure.cache.redis;

import java.util.concurrent.TimeUnit;

/**
 * @author valarchie
 */
public enum CacheKeyEnum {

    /**
     * Redis各类缓存集合
     */
    CAPTCHAT("captcha_codes:", 2, TimeUnit.MINUTES),
    LOGIN_USER_KEY("login_tokens:", 30, TimeUnit.MINUTES),
    RATE_LIMIT_KEY("rate_limit:", 60, TimeUnit.SECONDS),
    USER_ENTITY_KEY("user_entity:", 60, TimeUnit.MINUTES),
    ROLE_ENTITY_KEY("role_entity:", 60, TimeUnit.MINUTES),
    POST_ENTITY_KEY("post_entity:", 60, TimeUnit.MINUTES),
    ROLE_MODEL_INFO_KEY("role_model_info:", 60, TimeUnit.MINUTES),
    KNOWLEDGE_TYPE_ENTITY_KEY("knowledge_type_entity:", 60, TimeUnit.MINUTES),
    ONLINE_DEVICE_KEY("online_device:", 60, TimeUnit.SECONDS),
    THRESHOLD_VALUES_DEVICE_KEY("threshold_values_device:", 60, TimeUnit.SECONDS),
    THRESHOLD_EMERGENCY_DEVICE_KEY("threshold_emergency_device:", 60, TimeUnit.SECONDS),
    SM_DEVICE_ONLINE_KEY("sm_device_online", 5, TimeUnit.SECONDS),
    ;

    CacheKeyEnum(String key, int expiration, TimeUnit timeUnit) {
        this.key = key;
        this.expiration = expiration;
        this.timeUnit = timeUnit;
    }

    private final String key;
    private final int expiration;
    private final TimeUnit timeUnit;

    public String key() {
        return key;
    }

    public int expiration() {
        return expiration;
    }

    public TimeUnit timeUnit() {
        return timeUnit;
    }

}
