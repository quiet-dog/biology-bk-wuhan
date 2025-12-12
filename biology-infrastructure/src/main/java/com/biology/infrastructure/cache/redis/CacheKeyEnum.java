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
    XING_WEI_ONLINE_KEY("xing_wei_online", 5, TimeUnit.SECONDS),
    XS_DATA_FUN1_KEY("xs_data_fun1", 5, TimeUnit.SECONDS),
    CAI_YANG_FUN_KEY("cai_yang_fun", 5, TimeUnit.SECONDS),
    JIAN_CE_DATA_KEY("jian_ce_data", 5, TimeUnit.SECONDS),
    ALARMLEVEL_DETAIL_KEY("alarmlevel_detail:", 60, TimeUnit.MINUTES),
    ENVIRONMENT_EMERGENCY_KEY("environment_emergency:", 60, TimeUnit.MINUTES),
    EMERGENCY_KEY("emergency:", 60, TimeUnit.MINUTES),
    THRESHOLD_KEY("threshold:", 60, TimeUnit.MINUTES),
    EMERGENCY_FILE_KEY("emergency_file:", 60, TimeUnit.MINUTES),
    THRESHOLD_SOP_DEVICE_KEY("threshold_sop_device:", 60, TimeUnit.MINUTES),
    SOP_KEY("sop_entity:", 60, TimeUnit.MINUTES),
    SOP_FILE_KEY("sop_file:", 60, TimeUnit.MINUTES),
    ENVIRONMENT_SOP_KEY("environment_sop:", 60, TimeUnit.MINUTES),
    KONG_TIAO_DATA_KEY("kong_tiao_data:", 60, TimeUnit.MINUTES),
    XUN_JIAN_DEVICE_KEY("xun_jian_device:", 365, TimeUnit.DAYS),
    SM_DATA_KEY("sm_data:", 60, TimeUnit.MINUTES),
    XW_ALARM_KEY("xw_alarm:", 60, TimeUnit.MINUTES),
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
