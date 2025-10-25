package com.biology.domain.common.cache;

import cn.hutool.extra.spring.SpringUtil;
import com.biology.infrastructure.cache.RedisUtil;
import com.biology.infrastructure.cache.redis.CacheKeyEnum;
import com.biology.infrastructure.cache.redis.RedisCacheTemplate;
import com.biology.infrastructure.user.web.SystemLoginUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailEntity;
import com.biology.domain.manage.alarmlevel.db.AlarmlevelDetailService;
import com.biology.domain.manage.caiYangData.dto.CaiYangFunDTO;
import com.biology.domain.manage.emergency.db.EmergencyEntity;
import com.biology.domain.manage.emergency.db.EmergencyFileEntity;
import com.biology.domain.manage.emergency.db.EmergencyFileService;
import com.biology.domain.manage.emergency.db.EmergencyService;
import com.biology.domain.manage.emergency.dto.EmergencyDTO;
import com.biology.domain.manage.environment.db.EnvironmentEmergencyEntity;
import com.biology.domain.manage.environment.db.EnvironmentEmergencyService;
import com.biology.domain.manage.environment.db.EnvironmentSopEntity;
import com.biology.domain.manage.environment.db.EnvironmentSopService;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeEntity;
import com.biology.domain.manage.knowledge.db.KnowledgeTypeService;
import com.biology.domain.manage.nongDuData.command.NongDuDTO;
import com.biology.domain.manage.smData.dto.SmDataDTO;
import com.biology.domain.manage.sop.db.SopEntity;
import com.biology.domain.manage.sop.db.SopFileEntity;
import com.biology.domain.manage.sop.db.SopFileService;
import com.biology.domain.manage.sop.db.SopService;
import com.biology.domain.manage.sop.dto.SopDTO;
import com.biology.domain.manage.task.dto.SmOnlineDataDTO;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyEntity;
import com.biology.domain.manage.threshold.db.ThresholdEmergencyService;
import com.biology.domain.manage.threshold.db.ThresholdEntity;
import com.biology.domain.manage.threshold.db.ThresholdService;
import com.biology.domain.manage.threshold.db.ThresholdSopEntity;
import com.biology.domain.manage.threshold.db.ThresholdSopService;
import com.biology.domain.manage.threshold.db.ThresholdValueEntity;
import com.biology.domain.manage.threshold.db.ThresholdValueService;
import com.biology.domain.manage.websocket.dto.OnlineDTO;
import com.biology.domain.manage.xsData.command.XsDataFun1DTO;
import com.biology.domain.manage.xunJian.dto.XunJianDTO;
import com.biology.domain.manage.xwAlarm.dto.XingWeiDTO;
import com.biology.domain.system.post.db.SysPostEntity;
import com.biology.domain.system.role.db.SysRoleEntity;
import com.biology.domain.system.user.db.SysUserEntity;
import com.biology.domain.system.post.db.SysPostService;
import com.biology.domain.system.role.db.SysRoleService;
import com.biology.domain.system.user.db.SysUserService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author valarchie
 */
@Component
@RequiredArgsConstructor
public class RedisCacheService {

    private final RedisUtil redisUtil;

    public RedisCacheTemplate<String> captchaCache;
    public RedisCacheTemplate<SystemLoginUser> loginUserCache;
    public RedisCacheTemplate<SysUserEntity> userCache;
    public RedisCacheTemplate<SysRoleEntity> roleCache;

    public RedisCacheTemplate<SysPostEntity> postCache;

    public RedisCacheTemplate<KnowledgeTypeEntity> knowledgeTypeCache;

    public RedisCacheTemplate<OnlineDTO> onlineCache;

    public RedisCacheTemplate<List<ThresholdValueEntity>> thresholdValuesCache;

    public RedisCacheTemplate<List<EmergencyDTO>> thrsholdEmergencyCache;

    public RedisCacheTemplate<List<SopDTO>> thrsholdSopCache;

    public RedisCacheTemplate<SmOnlineDataDTO> smDeviceOnlineCache;

    public RedisCacheTemplate<XingWeiDTO> xingWeiOnlineCache;

    public RedisCacheTemplate<XsDataFun1DTO> xsDataFun1Cache;

    public RedisCacheTemplate<CaiYangFunDTO> caiYangFunCache;

    public RedisCacheTemplate<NongDuDTO> jianCeDataCache;

    public RedisCacheTemplate<List<AlarmlevelDetailEntity>> alarmlevelDetailCache;

    public RedisCacheTemplate<List<EnvironmentEmergencyEntity>> environmentEmergencyCache;

    public RedisCacheTemplate<EmergencyEntity> emergencyCache;

    public RedisCacheTemplate<ThresholdEntity> thresholdCache;

    public RedisCacheTemplate<List<ThresholdEmergencyEntity>> thresholdEmergencyCache;

    public RedisCacheTemplate<List<EmergencyFileEntity>> emergencyFileCache;

    public RedisCacheTemplate<List<ThresholdSopEntity>> thresholdSopCache;

    public RedisCacheTemplate<SopEntity> sopCache;

    public RedisCacheTemplate<List<SopFileEntity>> sopFileCache;

    public RedisCacheTemplate<List<EnvironmentSopEntity>> environmentSopCache;

    public RedisCacheTemplate<List<XunJianDTO>> xunJianDeviceCache;

    // public RedisCacheTemplate<RoleInfo> roleModelInfoCache;

    @PostConstruct
    public void init() {

        captchaCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.CAPTCHAT);

        loginUserCache = new RedisCacheTemplate<>(redisUtil, CacheKeyEnum.LOGIN_USER_KEY);

        userCache = new RedisCacheTemplate<SysUserEntity>(redisUtil, CacheKeyEnum.USER_ENTITY_KEY) {
            @Override
            public SysUserEntity getObjectFromDb(Object id) {
                SysUserService userService = SpringUtil.getBean(SysUserService.class);
                return userService.getById((Serializable) id);
            }
        };

        roleCache = new RedisCacheTemplate<SysRoleEntity>(redisUtil, CacheKeyEnum.ROLE_ENTITY_KEY) {
            @Override
            public SysRoleEntity getObjectFromDb(Object id) {
                SysRoleService roleService = SpringUtil.getBean(SysRoleService.class);
                return roleService.getById((Serializable) id);
            }
        };

        // roleModelInfoCache = new RedisCacheTemplate<RoleInfo>(redisUtil,
        // CacheKeyEnum.ROLE_MODEL_INFO_KEY) {
        // @Override
        // public RoleInfo getObjectFromDb(Object id) {
        // UserDetailsService userDetailsService =
        // SpringUtil.getBean(UserDetailsService.class);
        // return userDetailsService.getRoleInfo((Long) id);
        // }
        //
        // };

        postCache = new RedisCacheTemplate<SysPostEntity>(redisUtil, CacheKeyEnum.POST_ENTITY_KEY) {
            @Override
            public SysPostEntity getObjectFromDb(Object id) {
                SysPostService postService = SpringUtil.getBean(SysPostService.class);
                return postService.getById((Serializable) id);
            }

        };

        knowledgeTypeCache = new RedisCacheTemplate<KnowledgeTypeEntity>(redisUtil,
                CacheKeyEnum.KNOWLEDGE_TYPE_ENTITY_KEY) {
            @Override
            public KnowledgeTypeEntity getObjectFromDb(Object id) {
                KnowledgeTypeService knowledgeTypeService = SpringUtil.getBean(KnowledgeTypeService.class);
                return knowledgeTypeService.getById((Serializable) id);
            }
        };

        onlineCache = new RedisCacheTemplate<OnlineDTO>(redisUtil,
                CacheKeyEnum.ONLINE_DEVICE_KEY) {
            // @Override
            // public OnlineDTO getObjectFromDb(Object id) {
            // OnlineDTO oDto = new OnlineDTO();
            // // id为 type-id
            // String[] split = id.toString().split("-");
            // String type = split[0];
            // if (type.equals("equipment")) {
            // if (split.length > 1) {
            // // 设备id
            // oDto.setEquipmentId(Long.parseLong(split[1]));
            // }

            // }
            // if (type.equals("threshold")) {
            // if (split.length > 1) {
            // // 检测id
            // oDto.setThresholdId(Long.parseLong(split[1]));
            // }
            // }
            // if (type.equals("environment")) {
            // if (split.length > 1) {
            // // 环境id
            // oDto.setEnvironmentId(Long.parseLong(split[1]));
            // }
            // }
            // return oDto;
            // }

        };

        thresholdValuesCache = new RedisCacheTemplate<List<ThresholdValueEntity>>(redisUtil,
                CacheKeyEnum.THRESHOLD_VALUES_DEVICE_KEY) {
            public List<ThresholdValueEntity> getObjectFromDb(Object id) {
                ThresholdValueService thresholdValueService = SpringUtil.getBean(ThresholdValueService.class);
                QueryWrapper<ThresholdValueEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("threshold_id", id);
                return thresholdValueService.getBaseMapper().selectList(queryWrapper);
            }
        };

        smDeviceOnlineCache = new RedisCacheTemplate<SmOnlineDataDTO>(redisUtil,
                CacheKeyEnum.SM_DEVICE_ONLINE_KEY) {
        };

        xingWeiOnlineCache = new RedisCacheTemplate<XingWeiDTO>(redisUtil,
                CacheKeyEnum.XING_WEI_ONLINE_KEY) {
        };

        xsDataFun1Cache = new RedisCacheTemplate<XsDataFun1DTO>(redisUtil, CacheKeyEnum.XS_DATA_FUN1_KEY) {

        };

        caiYangFunCache = new RedisCacheTemplate<CaiYangFunDTO>(redisUtil, CacheKeyEnum.CAI_YANG_FUN_KEY) {
        };

        jianCeDataCache = new RedisCacheTemplate<NongDuDTO>(redisUtil, CacheKeyEnum.JIAN_CE_DATA_KEY) {
        };

        alarmlevelDetailCache = new RedisCacheTemplate<List<AlarmlevelDetailEntity>>(redisUtil,
                CacheKeyEnum.ALARMLEVEL_DETAIL_KEY) {
            public List<AlarmlevelDetailEntity> getObjectFromDb(Object id) {
                AlarmlevelDetailService alarmlevelDetailService = SpringUtil.getBean(AlarmlevelDetailService.class);
                QueryWrapper<AlarmlevelDetailEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("environment_id", id);
                return alarmlevelDetailService.getBaseMapper().selectList(queryWrapper);
            }
        };

        environmentEmergencyCache = new RedisCacheTemplate<List<EnvironmentEmergencyEntity>>(redisUtil,
                CacheKeyEnum.ENVIRONMENT_EMERGENCY_KEY) {
            public List<EnvironmentEmergencyEntity> getObjectFromDb(Object id) {
                EnvironmentEmergencyService emergencyService = SpringUtil.getBean(EnvironmentEmergencyService.class);
                QueryWrapper<EnvironmentEmergencyEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("environment_id", id)
                        .inSql("emergency_id",
                                "select emergency_id from manage_emergency where deleted = 0");
                List<EnvironmentEmergencyEntity> list = emergencyService.getBaseMapper().selectList(queryWrapper);
                return list;
            }
        };

        emergencyCache = new RedisCacheTemplate<EmergencyEntity>(redisUtil,
                CacheKeyEnum.EMERGENCY_KEY) {
            public EmergencyEntity getObjectFromDb(Object id) {
                EmergencyService emergencyService = SpringUtil.getBean(EmergencyService.class);
                return emergencyService.getById((Serializable) id);
            }
        };

        thresholdCache = new RedisCacheTemplate<ThresholdEntity>(redisUtil,
                CacheKeyEnum.THRESHOLD_KEY) {
            public ThresholdEntity getObjectFromDb(Object id) {
                ThresholdService thresholdService = SpringUtil.getBean(ThresholdService.class);
                return thresholdService.getById((Serializable) id);
            }
        };

        thresholdEmergencyCache = new RedisCacheTemplate<List<ThresholdEmergencyEntity>>(redisUtil,
                CacheKeyEnum.THRESHOLD_EMERGENCY_DEVICE_KEY) {
            public List<ThresholdEmergencyEntity> getObjectFromDb(Object id) {
                ThresholdEmergencyService thresholdEmergencyService = SpringUtil
                        .getBean(ThresholdEmergencyService.class);
                QueryWrapper<ThresholdEmergencyEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("threshold_id", id)
                        .inSql("emergency_id",
                                "select emergency_id from manage_emergency where deleted = 0");
                return thresholdEmergencyService.getBaseMapper().selectList(queryWrapper);
            }
        };

        emergencyFileCache = new RedisCacheTemplate<List<EmergencyFileEntity>>(redisUtil,
                CacheKeyEnum.EMERGENCY_FILE_KEY) {
            public List<EmergencyFileEntity> getObjectFromDb(Object id) {
                EmergencyFileService emergencyFileService = SpringUtil.getBean(EmergencyFileService.class);
                QueryWrapper<EmergencyFileEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("emergency_id", id);
                return emergencyFileService.getBaseMapper().selectList(queryWrapper);
            }
        };

        thresholdSopCache = new RedisCacheTemplate<List<ThresholdSopEntity>>(redisUtil,
                CacheKeyEnum.THRESHOLD_SOP_DEVICE_KEY) {
            public List<ThresholdSopEntity> getObjectFromDb(Object id) {
                ThresholdSopService thresholdSopService = SpringUtil.getBean(ThresholdSopService.class);
                QueryWrapper<ThresholdSopEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("threshold_id", id);
                return thresholdSopService.getBaseMapper().selectList(queryWrapper);
            }
        };

        sopCache = new RedisCacheTemplate<SopEntity>(redisUtil, CacheKeyEnum.SOP_KEY) {
            public SopEntity getObjectFromDb(Object id) {
                SopService sopService = SpringUtil.getBean(SopService.class);
                return sopService.getById((Serializable) id);
            }
        };

        sopFileCache = new RedisCacheTemplate<List<SopFileEntity>>(redisUtil, CacheKeyEnum.SOP_FILE_KEY) {
            public List<SopFileEntity> getObjectFromDb(Object id) {
                SopFileService sopFileService = SpringUtil.getBean(SopFileService.class);
                QueryWrapper<SopFileEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("sop_id", id)
                        .inSql("sop_id", "select sop_id from manage_sop where deleted = 0");
                return sopFileService.getBaseMapper().selectList(queryWrapper);
            }
        };

        environmentSopCache = new RedisCacheTemplate<List<EnvironmentSopEntity>>(redisUtil,
                CacheKeyEnum.ENVIRONMENT_SOP_KEY) {
            public List<EnvironmentSopEntity> getObjectFromDb(Object id) {
                EnvironmentSopService environmentSopService = SpringUtil.getBean(EnvironmentSopService.class);
                QueryWrapper<EnvironmentSopEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("environment_id", id)
                        .inSql("sop_id", "select sop_id from manage_sop where deleted = 0");
                return environmentSopService.getBaseMapper().selectList(queryWrapper);
            }
        };

        xunJianDeviceCache = new RedisCacheTemplate<List<XunJianDTO>>(redisUtil, CacheKeyEnum.ENVIRONMENT_SOP_KEY) {

        };

        // thrsholdEmergencyCache = new
        // RedisCacheTemplate<List<EmergencyDTO>>(redisUtil,
        // CacheKeyEnum.THRESHOLD_EMERGENCY_DEVICE_KEY) {
        // public List<EmergencyDTO> getObjectFromDb(Object id) {
        // EmergencyService emergencyService =
        // SpringUtil.getBean(EmergencyService.class);
        // QueryWrapper<EmergencyEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.inSql("emergency_id",
        // "select emergency_id from manage_threshold_emergency where threshold_id = " +
        // id);
        // List<EmergencyDTO> list = new ArrayList<>();
        // List<EmergencyEntity> search =
        // emergencyService.getBaseMapper().selectList(queryWrapper);
        // for (EmergencyEntity eE : search) {
        // EmergencyDTO eDto = new EmergencyDTO(eE);
        // list.add(eDto);
        // }
        // return list;
        // }
        // };

        // thrsholdSopCache = new RedisCacheTemplate<List<SopDTO>>(redisUtil,
        // CacheKeyEnum.THRESHOLD_EMERGENCY_DEVICE_KEY) {
        // public List<SopDTO> getObjectFromDb(Object id) {
        // SopService sopService = SpringUtil.getBean(SopService.class);
        // QueryWrapper<SopEntity> queryWrapper = new QueryWrapper<>();
        // queryWrapper.inSql("sop_id", "select sop_id from manage_threshold_sop where
        // threshold_id = " + id);
        // List<SopDTO> list = new ArrayList<>();
        // List<SopEntity> search = sopService.getBaseMapper().selectList(queryWrapper);
        // for (SopEntity sop : search) {
        // list.add(new SopDTO(sop));
        // }
        // return list;
        // }
        // };

    }

}
