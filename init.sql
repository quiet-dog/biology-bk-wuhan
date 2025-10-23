/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-12.0.2-MariaDB, for Linux (x86_64)
--
-- Host: 192.168.0.11    Database: wu_bio
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父部门id',
  `ancestors` text NOT NULL COMMENT '祖级列表',
  `dept_name` varchar(64) NOT NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `leader_id` bigint DEFAULT NULL,
  `leader_name` varchar(64) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `status` smallint NOT NULL DEFAULT '0' COMMENT '部门状态（0停用 1启用）',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sys_dept` VALUES
(1,0,'0','武汉生物制品研究所有限责任公司',0,NULL,'valarchie','15888888888','valarchie@163.com',1,NULL,'2022-05-21 08:30:54',1,'2025-04-25 09:54:13',0),
(2,1,'0,1','高生物安全风险车间车间生物安全负责人',1,NULL,'valarchie','15888888888','valarchie@163.com',1,NULL,'2022-05-21 08:30:54',1,'2025-04-25 09:54:49',0),
(3,1,'0,1','长沙分公司',2,NULL,'valarchie','15888888888','valarchie@163.com',1,NULL,'2022-05-21 08:30:54',NULL,NULL,1),
(4,2,'0,1,2','生物安全监控组',1,NULL,'valarchie','15888888888','valarchie@163.com',1,NULL,'2022-05-21 08:30:54',1,'2025-04-25 09:55:12',0),
(5,4,'0,1,2,4','生产',2,NULL,'valarchie','15888888888','valarchie@163.com',1,NULL,'2022-05-21 08:30:54',1,'2025-04-25 09:56:19',0),
(6,4,'0,1,2,4','质量控制',3,NULL,'valarchie','15888888888','valarchie@163.com',1,NULL,'2022-05-21 08:30:54',1,'2025-04-25 09:55:57',0),
(7,2,'0,1,2','综合管理组',4,NULL,'valarchie','15888888888','valarchie@163.com',1,NULL,'2022-05-21 08:30:54',1,'2024-12-31 14:22:01',1),
(8,2,'0,1,2','技术组',5,NULL,'valarchie','15888888888','valarchie@163.com',1,NULL,'2022-05-21 08:30:54',1,'2024-12-31 14:22:24',1),
(9,3,'0,1,3','市场部!',1,NULL,'valarchie!!','15888188888','valarc1hie@163.com',0,NULL,'2022-05-21 08:30:54',1,'2023-07-20 22:33:31',1),
(10,3,'0,1,3','财务部门',2,NULL,'valarchie','15888888888','valarchie@163.com',0,NULL,'2022-05-21 08:30:54',NULL,NULL,1),
(11,0,'0','武汉生物制品研究所有限责任公司',0,NULL,'','','',1,1,'2025-04-24 21:32:07',NULL,NULL,1),
(12,11,'0,11','高生物安全风险车间车间生物安全负责人',0,NULL,'','','',1,1,'2025-04-24 21:32:21',NULL,NULL,1),
(13,12,'0,11,12','生物安全监控组',0,NULL,'','','',1,1,'2025-04-24 21:32:33',NULL,NULL,1),
(14,13,'0,11,12,13','生产',0,NULL,'','','',1,1,'2025-04-24 21:32:49',NULL,NULL,1),
(15,13,'0,11,12,13','质量控制',0,NULL,'','','',1,1,'2025-04-24 21:33:07',NULL,NULL,1),
(16,13,'0,11,12,13','工程运维',0,NULL,'','','',1,1,'2025-04-24 21:33:23',NULL,NULL,1),
(17,14,'0,11,12,13,14','反应器组',0,NULL,'','','',1,1,'2025-04-24 21:33:35',NULL,NULL,1),
(18,14,'0,11,12,13,14','后处理组',0,NULL,'','','',1,1,'2025-04-24 21:33:49',NULL,NULL,1),
(19,15,'0,11,12,13,15','过程检定组',0,NULL,'','','',1,1,'2025-04-24 21:34:03',NULL,NULL,1),
(20,16,'0,11,12,13,16','工程运维组',0,NULL,'','','',1,1,'2025-04-24 21:34:15',NULL,NULL,1),
(21,4,'0,1,2,4','工程运维',0,NULL,'','','',1,1,'2025-04-25 09:56:14',NULL,NULL,0),
(22,5,'0,1,2,4,5','反应器组',0,NULL,'','','',1,1,'2025-04-25 09:56:41',NULL,NULL,0),
(23,5,'0,1,2,4,5','后处理组',0,NULL,'','','',1,1,'2025-04-25 09:56:57',NULL,NULL,0),
(24,6,'0,1,2,4,6','过程检定组',0,NULL,'','','',1,1,'2025-04-25 09:57:10',NULL,NULL,0),
(25,21,'0,1,2,4,21','工程运维组',0,NULL,'','','',1,1,'2025-04-25 09:57:22',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(128) NOT NULL DEFAULT '' COMMENT '配置名称',
  `config_key` varchar(128) NOT NULL DEFAULT '' COMMENT '配置键名',
  `config_options` varchar(1024) NOT NULL DEFAULT '' COMMENT '可选的选项',
  `config_value` varchar(256) NOT NULL DEFAULT '' COMMENT '配置值',
  `is_allow_change` tinyint(1) NOT NULL COMMENT '是否允许修改',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `config_key_uniq_idx` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sys_config` VALUES
(1,'主框架页-默认皮肤样式名称','sys.index.skinName','[\"skin-blue\",\"skin-green\",\"skin-purple\",\"skin-red\",\"skin-yellow\"]','skin-blue',1,NULL,1,'2025-01-13 21:19:51','2022-05-21 08:30:55','蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow',0),
(2,'用户管理-账号初始密码','sys.user.initPassword','','123456',1,NULL,1,'2023-07-20 14:42:08','2022-05-21 08:30:55','初始化密码 123456',0),
(3,'主框架页-侧边栏主题','sys.index.sideTheme','[\"theme-dark\",\"theme-light\"]','theme-dark',1,NULL,NULL,'2022-08-28 22:12:15','2022-08-20 08:30:55','深色主题theme-dark，浅色主题theme-light',0),
(4,'账号自助-验证码开关','sys.account.captchaOnOff','[\"true\",\"false\"]','false',0,NULL,1,'2023-07-20 14:39:36','2022-05-21 08:30:55','是否开启验证码功能（true开启，false关闭）',0),
(5,'账号自助-是否开启用户注册功能','sys.account.registerUser','[\"true\",\"false\"]','true',0,NULL,1,'2022-10-05 22:18:57','2022-05-21 08:30:55','是否开启注册用户功能（true开启，false关闭）',0);
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(64) NOT NULL COMMENT '菜单名称',
  `menu_type` smallint NOT NULL DEFAULT '0' COMMENT '菜单的类型(1为普通菜单2为目录3为内嵌iFrame4为外链跳转)',
  `router_name` varchar(255) NOT NULL DEFAULT '' COMMENT '路由名称（需保持和前端对应的vue文件中的name保持一致defineOptions方法中设置的name）',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `path` varchar(255) DEFAULT NULL COMMENT '组件路径（对应前端项目view文件夹中的路径）',
  `is_button` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否按钮',
  `permission` varchar(128) DEFAULT NULL COMMENT '权限标识',
  `meta_info` varchar(1024) NOT NULL DEFAULT '{}' COMMENT '路由元信息（前端根据这个信息进行逻辑处理）',
  `status` smallint NOT NULL DEFAULT '0' COMMENT '菜单状态（1启用 0停用）',
  `remark` varchar(256) DEFAULT '' COMMENT '备注',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sys_menu` VALUES
(1,'系统管理',2,'',0,'/system',0,'','{\"icon\": \"\", \"rank\": 14, \"title\": \"系统管理\", \"showParent\": true}',1,'系统管理目录',0,'2022-05-21 08:30:54',1,'2024-10-24 10:52:01',0),
(2,'系统监控',2,'',0,'/monitor',0,'','{\"icon\": \"\", \"rank\": 3, \"title\": \"系统监控\", \"showParent\": true}',1,'系统监控目录',0,'2022-05-21 08:30:54',1,'2023-08-14 23:09:15',1),
(3,'系统工具',2,'',0,'/tool',0,'','{\"icon\": \"\", \"rank\": 15, \"title\": \"系统工具\", \"showParent\": true}',0,'系统工具目录',0,'2022-05-21 08:30:54',1,'2024-10-24 10:52:06',0),
(4,'Biology官网',3,'BiologyguanwangIframeRouter',0,'/BiologyguanwangIframeLink',0,'','{\"icon\": \"\", \"rank\": 8, \"title\": \"Biology官网\", \"frameSrc\": \"https://element-plus.org/zh-CN/\", \"showParent\": true}',1,'Biology官网地址',0,'2022-05-21 08:30:54',1,'2023-08-14 23:09:40',1),
(5,'账号管理',1,'SystemUser',1,'/system/user/index',0,'system:user:list','{\"title\":\"账号管理\",\"icon\":\"\",\"showParent\":true}',1,'用户管理菜单',0,'2022-05-21 08:30:54',1,'2024-12-31 12:42:17',0),
(6,'权限管理',1,'SystemRole',1,'/system/role/index',0,'system:role:list','{\"title\":\"权限管理\",\"icon\":\"\",\"showParent\":true}',1,'角色管理菜单',0,'2022-05-21 08:30:54',1,'2024-12-31 12:45:12',0),
(7,'菜单管理',1,'MenuManagement',1,'/system/menu/index',0,'system:menu:list','{\"icon\": \"\", \"title\": \"菜单管理\", \"showParent\": true}',1,'菜单管理菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:15:41',1),
(8,'部门管理',1,'Department',1,'/system/dept/index',0,'system:dept:list','{\"icon\": \"\", \"title\": \"部门管理\", \"showParent\": true}',1,'部门管理菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:15:35',0),
(9,'岗位管理',1,'Post',1,'/system/post/index',0,'system:post:list','{\"icon\": \"\", \"title\": \"岗位管理\", \"showParent\": true}',1,'岗位管理菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:15:11',0),
(10,'参数设置',1,'Config',1,'/system/config/index',0,'system:config:list','{\"icon\": \"\", \"title\": \"参数设置\", \"showParent\": true}',1,'参数设置菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:15:03',1),
(11,'通知公告',1,'SystemNotice',1,'/system/notice/index',0,'system:notice:list','{\"icon\": \"\", \"title\": \"通知公告\", \"showParent\": true}',1,'通知公告菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:14:56',1),
(12,'日志管理',1,'LogManagement',1,'/system/logd',0,'','{\"icon\": \"\", \"title\": \"日志管理\", \"showParent\": true}',1,'日志管理菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:14:47',1),
(13,'在线用户',1,'OnlineUser',2,'/system/monitor/onlineUser/index',0,'monitor:online:list','{\"icon\": \"\", \"title\": \"在线用户\", \"showParent\": true}',1,'在线用户菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:13:13',1),
(14,'数据监控',1,'DataMonitor',2,'/system/monitor/druid/index',0,'monitor:druid:list','{\"icon\": \"\", \"title\": \"数据监控\", \"frameSrc\": \"/druid/login.html\", \"showParent\": true, \"isFrameSrcInternal\": true}',1,'数据监控菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:13:25',1),
(15,'服务监控',1,'ServerInfo',2,'/system/monitor/server/index',0,'monitor:server:list','{\"icon\": \"\", \"title\": \"服务监控\", \"showParent\": true}',1,'服务监控菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:13:34',1),
(16,'缓存监控',1,'CacheInfo',2,'/system/monitor/cache/index',0,'monitor:cache:list','{\"icon\": \"\", \"title\": \"缓存监控\", \"showParent\": true}',1,'缓存监控菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:12:59',1),
(17,'系统接口',1,'SystemAPI',3,'/tool/swagger/index',0,'tool:swagger:list','{\"icon\": \"\", \"title\": \"系统接口\", \"frameSrc\": \"/swagger-ui/index.html\", \"showParent\": true, \"isFrameSrcInternal\": true}',1,'系统接口菜单',0,'2022-05-21 08:30:54',1,'2023-08-14 23:14:01',0),
(18,'操作日志',1,'OperationLog',12,'/system/log/operationLog/index',0,'monitor:operlog:list','{\"title\":\"操作日志\"}',1,'操作日志菜单',0,'2022-05-21 08:30:54',NULL,NULL,1),
(19,'登录日志',1,'LoginLog',12,'/system/log/loginLog/index',0,'monitor:logininfor:list','{\"title\":\"登录日志\"}',1,'登录日志菜单',0,'2022-05-21 08:30:54',NULL,NULL,1),
(20,'用户查询',0,' ',5,'',1,'system:user:query','{\"title\":\"用户查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(21,'用户新增',0,' ',5,'',1,'system:user:add','{\"title\":\"用户新增\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(22,'用户修改',0,' ',5,'',1,'system:user:edit','{\"title\":\"用户修改\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(23,'用户删除',0,' ',5,'',1,'system:user:remove','{\"title\":\"用户删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(24,'用户导出',0,' ',5,'',1,'system:user:export','{\"title\":\"用户导出\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(25,'用户导入',0,' ',5,'',1,'system:user:import','{\"title\":\"用户导入\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(26,'重置密码',0,' ',5,'',1,'system:user:resetPwd','{\"title\":\"重置密码\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(27,'角色查询',0,' ',6,'',1,'system:role:query','{\"title\":\"角色查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(28,'角色新增',0,' ',6,'',1,'system:role:add','{\"title\":\"角色新增\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(29,'角色修改',0,' ',6,'',1,'system:role:edit','{\"title\":\"角色修改\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(30,'角色删除',0,' ',6,'',1,'system:role:remove','{\"title\":\"角色删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(31,'角色导出',0,' ',6,'',1,'system:role:export','{\"title\":\"角色导出\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(32,'菜单查询',0,' ',7,'',1,'system:menu:query','{\"title\":\"菜单查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(33,'菜单新增',0,' ',7,'',1,'system:menu:add','{\"title\":\"菜单新增\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(34,'菜单修改',0,' ',7,'',1,'system:menu:edit','{\"title\":\"菜单修改\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(35,'菜单删除',0,' ',7,'',1,'system:menu:remove','{\"title\":\"菜单删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(36,'部门查询',0,' ',8,'',1,'system:dept:query','{\"title\":\"部门查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(37,'部门新增',0,' ',8,'',1,'system:dept:add','{\"title\":\"部门新增\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(38,'部门修改',0,' ',8,'',1,'system:dept:edit','{\"title\":\"部门修改\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(39,'部门删除',0,' ',8,'',1,'system:dept:remove','{\"title\":\"部门删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(40,'岗位查询',0,' ',9,'',1,'system:post:query','{\"title\":\"岗位查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(41,'岗位新增',0,' ',9,'',1,'system:post:add','{\"title\":\"岗位新增\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(42,'岗位修改',0,' ',9,'',1,'system:post:edit','{\"title\":\"岗位修改\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(43,'岗位删除',0,' ',9,'',1,'system:post:remove','{\"title\":\"岗位删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(44,'岗位导出',0,' ',9,'',1,'system:post:export','{\"title\":\"岗位导出\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(45,'参数查询',0,' ',10,'',1,'system:config:query','{\"title\":\"参数查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(46,'参数新增',0,' ',10,'',1,'system:config:add','{\"title\":\"参数新增\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(47,'参数修改',0,' ',10,'',1,'system:config:edit','{\"title\":\"参数修改\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(48,'参数删除',0,' ',10,'',1,'system:config:remove','{\"title\":\"参数删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(49,'参数导出',0,' ',10,'',1,'system:config:export','{\"title\":\"参数导出\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(50,'公告查询',0,' ',11,'',1,'system:notice:query','{\"title\":\"公告查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(51,'公告新增',0,' ',11,'',1,'system:notice:add','{\"title\":\"公告新增\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(52,'公告修改',0,' ',11,'',1,'system:notice:edit','{\"title\":\"公告修改\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(53,'公告删除',0,' ',11,'',1,'system:notice:remove','{\"title\":\"公告删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(54,'操作查询',0,' ',18,'',1,'monitor:operlog:query','{\"title\":\"操作查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(55,'操作删除',0,' ',18,'',1,'monitor:operlog:remove','{\"title\":\"操作删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(56,'日志导出',0,' ',18,'',1,'monitor:operlog:export','{\"title\":\"日志导出\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(57,'登录查询',0,' ',19,'',1,'monitor:logininfor:query','{\"title\":\"登录查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(58,'登录删除',0,' ',19,'',1,'monitor:logininfor:remove','{\"title\":\"登录删除\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,0),
(59,'日志导出',0,' ',19,'',1,'monitor:logininfor:export','{\"title\":\"日志导出\",\"rank\":22}',1,'',0,'2022-05-21 08:30:54',1,'2023-07-22 17:02:28',0),
(60,'在线查询',0,' ',13,'',1,'monitor:online:query','{\"title\":\"在线查询\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,1),
(61,'批量强退',0,' ',13,'',1,'monitor:online:batchLogout','{\"title\":\"批量强退\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,1),
(62,'单条强退',0,' ',13,'',1,'monitor:online:forceLogout','{\"title\":\"单条强退\"}',1,'',0,'2022-05-21 08:30:54',NULL,NULL,1),
(63,'Biology Github地址',4,'https://github.com/valarchie/Biology-Back-End',0,'/external',0,'','{\"icon\": \"\", \"rank\": 9, \"title\": \"Biology Github地址\", \"showParent\": true}',1,'Biology github地址',0,'2022-05-21 08:30:54',1,'2023-08-14 23:12:13',1),
(64,'首页',2,'',0,'/global',0,'121212','{\"title\":\"首页\",\"showParent\":true,\"rank\":3}',1,'',1,'2023-07-24 22:36:03',1,'2023-07-24 22:38:37',1),
(65,'个人中心',1,'PersonalCenter',2053,'/system/user/profile',0,'434sdf','{\"title\":\"个人中心\",\"showParent\":true,\"rank\":3}',1,'',1,'2023-07-24 22:36:55',NULL,NULL,1),
(66,'通知中心',2,'',0,'/notificationCenter',0,'','{\"title\":\"通知中心\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-24 15:17:19',NULL,NULL,1),
(67,'通知中心',1,'notificationCenter',0,'/notificationCenter/index',0,'','{\"title\":\"通知中心\",\"showLink\":true,\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-24 15:19:23',1,'2024-09-25 10:17:20',0),
(69,'知识库',1,'ManageKnowledge',0,'/manage/knowledge/index',0,'','{\"title\":\"知识库\",\"showLink\":true,\"showParent\":true,\"rank\":11}',1,'',1,'2024-09-25 10:24:41',1,'2024-09-25 22:25:21',0),
(70,'政策法规',1,'policiesAndRegulations',0,'/policiesAndRegulations/index',0,'','{\"title\":\"政策法规\",\"showLink\":true,\"showParent\":true,\"rank\":3}',1,'',1,'2024-09-25 10:25:29',1,'2024-09-25 10:27:00',1),
(71,'报警平台',2,'',0,'/alarmPlatform',0,'','{\"title\":\"报警平台\",\"showLink\":true,\"showParent\":true,\"rank\":3}',1,'',1,'2024-09-25 10:30:24',1,'2024-09-25 22:23:29',0),
(72,'报警事件',1,'alarmEvents',71,'/alarmPlatform/alarmEvents/index',0,'','{\"title\":\"报警事件\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-09-25 10:31:25',NULL,NULL,0),
(73,'阈值设置',1,'thresholdSetting',71,'/alarmPlatform/thresholdSetting/index',0,'','{\"title\":\"阈值设置\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 10:33:48',1,'2024-11-24 21:03:21',0),
(74,'政策法规',1,'ManagePolicies',0,'/manage/policies/index',0,'','{\"title\":\"政策法规\",\"showLink\":true,\"showParent\":true,\"rank\":2}',1,'',1,'2024-09-25 17:51:01',1,'2024-11-15 17:12:07',0),
(75,'应急调度',2,'',0,'/emergencyDispatch',0,'','{\"title\":\"应急调度\",\"showParent\":true,\"rank\":9}',1,'',1,'2024-09-25 21:48:53',1,'2024-09-25 22:25:08',0),
(76,'应急预案',1,'emergencyPlan',75,'/emergencyDispatch/emergencyPlan/index',0,'','{\"title\":\"应急预案\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-09-25 21:49:48',NULL,NULL,0),
(77,'报警信息',1,'alarmInformation',75,'/emergencyDispatch/alarmInformation/index',0,'','{\"title\":\"报警信息\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 21:50:33',1,'2024-11-22 22:51:11',0),
(78,'SOP手册',1,'SOPManual',75,'/emergencyDispatch/SOPManual/index',0,'','{\"title\":\"SOP手册\",\"showParent\":true,\"rank\":2}',1,'',1,'2024-09-25 21:51:07',1,'2024-11-23 09:21:28',0),
(79,'人员数据',2,'',0,'/personnelData',0,'','{\"title\":\"人员数据\",\"showParent\":true,\"rank\":4}',1,'',1,'2024-09-25 21:55:56',1,'2024-09-25 22:23:45',0),
(80,'人员档案',1,'personnelProfile',79,'/personnelData/personnelProfile/index',0,'','{\"title\":\"人员档案\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-09-25 21:56:33',NULL,NULL,0),
(81,'门禁记录',1,'accessControlRecords',79,'/personnelData/accessControlRecords/index',0,'','{\"title\":\"门禁记录\",\"showParent\":true,\"rank\":2}',1,'',1,'2024-09-25 21:58:41',NULL,NULL,1),
(82,'设备数据',2,'',0,'/deviceData',0,'','{\"title\":\"设备数据\",\"showParent\":true,\"rank\":5}',1,'',1,'2024-09-25 22:06:31',1,'2024-09-25 22:23:58',0),
(83,'设备档案',1,'equipmentProfile',82,'/deviceData/equipmentProfile/index',0,'','{\"title\":\"设备档案\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-09-25 22:06:57',NULL,NULL,0),
(84,'维修记录',1,'WeiXiuRecords',82,'/deviceData/WeiXiuRecords/index',0,'','{\"title\":\"维修记录\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:07:37',NULL,NULL,0),
(85,'检修记录',1,'JianXiuRecords',82,'/deviceData/JianXiuRecords/index',0,'','{\"title\":\"检修记录\",\"showParent\":true,\"rank\":2}',1,'',1,'2024-09-25 22:08:11',NULL,NULL,0),
(86,'维修手册',1,'WeiXiuManual',82,'/deviceData/WeiXiuManual/index',0,'','{\"title\":\"维修手册\",\"showParent\":true,\"rank\":3}',1,'',1,'2024-09-25 22:08:41',NULL,NULL,0),
(87,'巡检手册',1,'JianXiuManual',82,'/deviceData/JianXiuManual/index',0,'','{\"title\":\"巡检手册\",\"showParent\":true,\"rank\":4}',1,'',1,'2024-09-25 22:09:08',NULL,NULL,0),
(88,'日常巡检记录',1,'dailyInspectionRecords',82,'/deviceData/dailyInspectionRecords/index',0,'','{\"title\":\"日常巡检记录\",\"showParent\":true,\"rank\":5}',1,'',1,'2024-09-25 22:09:36',NULL,NULL,0),
(89,'设备数据',1,'deviceDatas',82,'/deviceData/deviceDatas/index',0,'','{\"title\":\"设备数据\",\"showParent\":true,\"rank\":6}',1,'',1,'2024-09-25 22:10:31',NULL,NULL,0),
(90,'物料数据',2,'',0,'/materialData',0,'','{\"title\":\"物料数据\",\"showParent\":true,\"rank\":6}',1,'',1,'2024-09-25 22:12:16',1,'2024-09-25 22:24:13',0),
(91,'物料档案',1,'materialFiles',90,'/materialData/materialFiles/index',0,'','{\"title\":\"物料档案\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-09-25 22:13:37',NULL,NULL,0),
(92,'领用记录',1,'receiptRecord',90,'/materialData/receiptRecord/index',0,'','{\"title\":\"领用记录\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:14:08',1,'2024-09-25 22:14:56',0),
(93,'库存报警',1,'materialAlarm',90,'/materialData/materialAlarm/index',0,'','{\"title\":\"库存报警\",\"showParent\":true,\"rank\":2}',1,'',1,'2024-09-25 22:14:49',1,'2024-11-11 14:42:37',0),
(94,'人员上报',1,'personnelReporting',90,'/materialData/personnelReporting/index',0,'','{\"title\":\"人员上报\",\"showParent\":true,\"rank\":3}',1,'',1,'2024-09-25 22:15:24',NULL,NULL,0),
(95,'工艺数据',2,'',0,'/processData',0,'','{\"title\":\"工艺数据\",\"showParent\":true,\"rank\":7}',1,'',1,'2024-09-25 22:16:57',1,'2024-09-25 22:24:52',0),
(96,'工艺档案',1,'processArchives',95,'/processData/processArchives/index',0,'','{\"title\":\"工艺档案\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-09-25 22:17:38',NULL,NULL,0),
(97,'工艺节点',1,'processNode',95,'/processData/processNode/index',0,'','{\"title\":\"工艺节点\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:18:11',NULL,NULL,0),
(98,'处置手册',1,'processingManual',95,'/processData/processingManual/index',0,'','{\"title\":\"处置手册\",\"showParent\":true,\"rank\":2}',1,'',1,'2024-09-25 22:18:35',1,'2024-11-18 15:41:30',0),
(99,'工艺流程图',1,'processFlowChart',95,'/processData/processFlowChart/index',0,'','{\"title\":\"工艺流程图\",\"showParent\":true,\"rank\":3}',1,'',1,'2024-09-25 22:19:02',1,'2024-09-25 22:19:12',0),
(100,'环境数据',2,'',0,'/environmentalData',0,'','{\"title\":\"环境数据\",\"showParent\":true,\"rank\":8}',1,'',1,'2024-09-25 22:20:46',1,'2024-09-25 22:25:02',0),
(101,'环境档案',1,'environmentalArchives',100,'/environmentalData/environmentalArchives/index',0,'','{\"title\":\"环境档案\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-09-25 22:21:05',1,'2024-11-21 21:57:42',0),
(102,'环境监测',1,'alarmLevelSetting',100,'/environmentalData/alarmLevelSetting/index',0,'','{\"title\":\"环境监测\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',1,'2024-11-21 21:57:58',0),
(105,'/emergencyDispatch/emergencyAlarmevent/index',0,'',75,'',0,'emergencyAlarmevent','{\"icon\": \"\", \"rank\": 0, \"title\": \"/emergencyDispatch/emergencyAlarmevent/index\", \"showParent\": true}',1,'',1,'2024-11-22 22:49:31',NULL,NULL,1),
(106,'报警事件',1,'emergencyAlarmevent',75,'/emergencyDispatch/emergencyAlarmevent/index',0,'','{\"title\":\"报警事件\",\"showParent\":true,\"rank\":3}',1,'',1,'2024-11-22 22:50:37',1,'2024-11-23 09:21:12',0),
(107,'视频监控',1,'ManageVideo',0,'/shipin/index',0,'','{\"title\":\"视频监控\",\"showLink\":true,\"showParent\":true,\"rank\":0}',1,'',1,'2024-12-14 10:35:15',1,'2024-12-14 11:48:04',0),
(108,'数据填报',2,'moni',0,'/moni',0,NULL,'{\"title\":\"数据填报\",\"showParent\":true,\"rank\":2}',1,'',1,'2024-12-14 10:35:16',1,NULL,0),
(109,'设备数据填报',1,'thresholdMoni',108,'/moni/threshold/index',0,NULL,'{\"title\":\"设备数据填报\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-12-14 10:35:17',1,NULL,0),
(110,'环境数据填报',1,'environmentMoni',108,'/moni/environment/index',0,NULL,'{\"title\":\"环境数据填报\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-12-14 10:35:18',NULL,NULL,0),
(111,'出勤统计',1,'chuqin',79,'/personnelData/chuqin/index',0,NULL,'{\"title\":\"出勤统计\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-12-14 10:35:19',NULL,NULL,0),
(112,'动画模拟填报',1,'animationMoni',108,'/moni/animation/index',0,NULL,'{\"title\":\"动画模拟填报\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-12-14 10:35:19',NULL,NULL,0),
(113,'能耗监测',1,'nenghao',100,'/environmentalData/nenghao/index',0,'','{\"title\":\"能耗监测\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',1,'2024-11-21 21:57:58',0),
(114,'课题三',2,'',0,'/ketisan',0,NULL,'{\"title\":\"课题三\",\"showParent\":true,\"rank\":8}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(115,'生命体征设备',1,'smDevice',114,'/ketisan/smDevice/index',0,NULL,'{\"title\":\"生命体征设备\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(116,'生命体征数据',1,'smData',114,'/ketisan/smData/index',0,NULL,'{\"title\":\"生命体征数据\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(117,'行为监测设备',1,'xwDevice',114,'/ketisan/xwDevice/index',0,NULL,'{\"title\":\"行为监测设备\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(118,'行为监测数据',1,'xwData',114,'/ketisan/xwData/index',0,NULL,'{\"title\":\"行为监测数据\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(119,'心理测评方案',1,'xlFangAn',114,'/ketisan/xlFangAn/index',0,NULL,'{\"title\":\"心理测评方案\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(120,'心理测评数据',1,'resultShiJuan',114,'/ketisan/resultShiJuan/index',0,NULL,'{\"title\":\"心理测评数据\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(121,'心理测评',1,'cePing',114,'/ketisan/cePing/index',0,NULL,'{\"title\":\"心理测评\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(122,'心理健康档案',1,'xlArchive',114,'/ketisan/xlArchive/index',0,NULL,'{\"title\":\"心理健康档案\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(123,'课题四',2,'',0,'/ketisi',0,NULL,'{\"title\":\"课题四\",\"showParent\":true,\"rank\":8}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(128,'监测设备',1,'jianCeDevice',123,'/ketisi/jianCeDevice/index',0,NULL,'{\"title\":\"监测设备\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(129,'监测设备数据',1,'nongDuData',123,'/ketisi/nongDuData/index',0,NULL,'{\"title\":\"监测设备数据\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(130,'采样设备',1,'nongDuDevice',123,'/ketisi/nongDuDevice/index',0,NULL,'{\"title\":\"采样设备\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(131,'消杀设备',1,'xsDevice',123,'/ketisi/xsDevice/index',0,NULL,'{\"title\":\"消杀设备\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(132,'消杀数据',1,'xsData',123,'/ketisi/xsData/index',0,NULL,'{\"title\":\"消杀数据\",\"showParent\":true,\"rank\":1}',1,'',1,'2024-09-25 22:21:47',NULL,NULL,0),
(133,'人员健康数据填报',1,'healthyMoni',108,'/moni/healthy/index',0,NULL,'{\"title\":\"人员健康数据填报\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-12-14 10:35:18',NULL,NULL,0),
(134,'自动巡检',1,'ziDongXunJian',0,'/ziDongXunJian',0,NULL,'{\"title\":\"自动巡检\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-12-14 10:35:18',NULL,NULL,0),
(135,'巡检计划',1,'xunJianJiHua',134,'/xunJianJiHua/index',0,NULL,'{\"title\":\"巡检计划\",\"showParent\":true,\"rank\":0}',1,'',1,'2024-12-14 10:35:18',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sys_role_menu` VALUES
(1,1),
(1,3),
(1,5),
(1,6),
(1,7),
(1,8),
(1,9),
(1,10),
(1,11),
(1,12),
(1,17),
(1,18),
(1,19),
(1,67),
(1,69),
(1,71),
(1,72),
(1,73),
(1,74),
(1,95),
(1,96),
(1,97),
(1,98),
(1,99),
(1,108),
(1,109),
(1,110),
(1,111),
(1,112),
(1,113),
(1,114),
(1,115),
(1,116),
(1,117),
(1,118),
(1,121),
(1,122),
(1,125),
(1,127),
(1,128),
(2,1),
(2,5),
(2,6),
(2,8),
(2,9),
(2,17),
(2,67),
(2,69),
(2,71),
(2,72),
(2,73),
(2,82),
(2,83),
(2,84),
(2,85),
(2,86),
(2,87),
(2,88),
(2,89),
(2,95),
(2,96),
(2,97),
(2,98),
(2,99),
(3,1),
(3,67),
(3,74),
(5,67),
(5,71),
(5,72),
(5,73),
(5,74),
(5,79),
(5,80),
(5,81),
(5,82),
(5,83),
(5,84),
(5,85),
(5,86),
(5,87),
(5,88),
(5,89),
(5,90),
(5,91),
(5,92),
(5,93),
(5,94),
(5,107),
(6,1),
(6,5),
(6,6),
(6,8),
(6,9),
(6,67),
(6,69),
(6,71),
(6,72),
(6,73),
(6,82),
(6,83),
(6,84),
(6,85),
(6,86),
(6,87),
(6,88),
(6,89),
(6,95),
(6,96),
(6,97),
(6,98),
(6,99),
(7,114),
(7,121),
(111,1);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(64) NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` smallint NOT NULL COMMENT '状态（1正常 0停用）',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sys_post` VALUES
(1,'1','高生物安全风险车间车间生物安全负责人',1,1,'',NULL,'2022-05-21 08:30:54',1,'2025-04-24 21:35:39',0),
(2,'2','生物安全监控组',2,1,'',NULL,'2022-05-21 08:30:54',1,'2025-04-24 21:36:23',0),
(3,'3','生产负责人',3,1,'',NULL,'2022-05-21 08:30:54',1,'2025-04-24 21:36:42',0),
(4,'4','质量控制负责人',3,1,'',NULL,'2022-05-21 08:30:54',1,'2025-04-25 10:12:00',0),
(5,'5','工程运维负责人',3,1,'',1,'2025-04-24 21:37:38',NULL,NULL,0),
(6,'6','反应器组',4,1,'',1,'2025-04-24 21:38:07',NULL,NULL,0),
(7,'7','后处理组',4,1,'',1,'2025-04-24 21:38:23',NULL,NULL,0),
(8,'8','过程检定组',4,1,'',1,'2025-04-24 21:38:36',NULL,NULL,0),
(9,'9','工程运维组',4,1,'',1,'2025-04-24 21:38:48',NULL,NULL,0);
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `post_id` bigint DEFAULT NULL COMMENT '职位id',
  `role_id` bigint DEFAULT NULL COMMENT '角色id',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `username` varchar(64) NOT NULL COMMENT '用户账号',
  `nickname` varchar(32) NOT NULL COMMENT '用户昵称',
  `user_type` smallint DEFAULT '0' COMMENT '用户类型（00系统用户）',
  `email` varchar(128) DEFAULT '' COMMENT '用户邮箱',
  `phone_number` varchar(18) DEFAULT '' COMMENT '手机号码',
  `sex` smallint DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(512) DEFAULT '' COMMENT '头像地址',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
  `status` smallint NOT NULL DEFAULT '0' COMMENT '帐号状态（1正常 2停用 3冻结）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '超级管理员标志（1是，0否）',
  `creator_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `last_password_reset_time` datetime DEFAULT NULL,
  `job_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '工号',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sys_user` VALUES
(1,1,1,23,'admin','valarchie1',0,'biology@163.com','15888888883',0,'/profile/avatar/20230725164110_blob_6b7a989b1cdd4dd396665d2cfd2addc5.png','$2a$10$qOYMMZpVHWsciWZN8JFmruZriLudlFkGI4ufdB7fQ4.MHLs5MGuq.',1,'127.0.0.1','2025-10-23 17:40:34',1,NULL,'2022-05-21 08:30:54',1,'2025-10-23 17:40:34','管理员',0,'2025-10-22 15:30:05','123123'),
(2,2,2,22,'ag1','valarchie2',0,'agileboot1@qq.com','15666666666',1,'/profile/avatar/20230725114818_avatar_b5bf400732bb43369b4df58802049b22.png','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2',1,'127.0.0.1','2022-05-21 08:30:54',0,NULL,'2022-05-21 08:30:54',1,'2025-08-07 13:49:47','测试员1',0,NULL,''),
(3,2,0,5,'ag2','valarchie3',0,'agileboot2@qq.com','15666666667',1,'/profile/avatar/20230725114818_avatar_b5bf400732bb43369b4df58802049b22.png','$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2',1,'127.0.0.1','2022-05-21 08:30:54',0,NULL,'2022-05-21 08:30:54',NULL,NULL,'测试员2',0,NULL,''),
(4,1,1,4,'screen','dasd',0,'archzks@gmaio.cpom','123',1,'','$2a$10$b31Hqv0ynIE9kel0JkyyFeFpWaERgi.5OIce4GTdzdLjiTJkdcXN.',1,'0:0:0:0:0:0:0:1','2024-12-01 17:55:41',0,1,'2024-11-26 16:11:44',4,'2024-12-04 11:39:55','',0,NULL,''),
(5,NULL,NULL,NULL,'123','',0,'','',0,'','$2a$10$dcGTHTRKZXsgPQIErzYT0uaCshHV.7LktCIc52aaS9XrU1OvmmLcK',1,'',NULL,0,1,'2024-11-29 17:07:23',1,'2024-11-29 17:07:32','',0,NULL,''),
(6,1,2,4,'zzz','小张1',0,'1111@qq.com','1111111111111',0,'','$2a$10$7BX6tu7a/HuLppxzo8YPR.segMdopGd/zu4rSpEv7pVKVv5xBBQqa',1,'223.88.17.202','2025-08-04 09:36:22',0,1,'2024-12-02 13:26:03',1,'2025-08-04 09:36:22','123',0,'2025-08-04 09:27:19',''),
(7,NULL,NULL,NULL,'','',0,'','',0,'','$2a$10$WzFQjOYjTdwRMveifPtJru4J.oHBvJCChoPm3BPZ9JmQu8tKKTbbK',0,'',NULL,0,1,'2024-12-04 11:41:55',NULL,NULL,'',1,NULL,''),
(8,2,5,4,'1232321','321313',0,'12321321213@qq.com','31313131',1,'','$2a$10$XCgQ0WmwTulSnZU8rqYII.m495lXfQb.ytJ9g6ZhCAJ5XW5eRFbQa',1,'127.0.0.1','2024-12-31 16:50:12',0,1,'2024-12-26 15:34:31',1,'2024-12-31 16:50:12','',1,NULL,'123123'),
(9,NULL,NULL,NULL,'test','',0,'','',0,'','$2a$10$1qG3blYu988CH7iGmjCWkeTEk3itwR/w2aAQi45no6t53eEylMlUm',1,'',NULL,0,1,'2025-01-13 21:20:36',1,'2025-01-13 21:20:41','',1,NULL,''),
(10,NULL,6,6,'test','',0,'11111@qq.com','12312412411',1,'','$2a$10$OcEKolc5.YuqJImA3pk7/etFAl9eggu8gV7y8Pbh2xaS1ZLDeyHlS',1,'42.233.182.105','2025-10-20 10:19:27',0,1,'2025-01-13 21:22:42',1,'2025-10-20 10:19:27','',0,'2025-10-20 10:17:19',''),
(11,NULL,7,22,'beice','test',0,'123@qq.com','1231512325',1,'','$2a$10$zoB.w73wl3GEel6ZFKMQ7eSV2pIxUz8SYFTuOZm1W8baIdO6TrwoO',1,'127.0.0.1','2025-08-09 11:07:14',0,1,'2025-08-09 11:06:58',11,'2025-08-09 11:07:14','123',0,NULL,'12345'),
(12,NULL,5,NULL,'12123421','123213',0,'a@qq.com','41421241241',1,'','$2a$10$v0zUKrX6QChWrryKPwD0r.Nf09aijbcZJC1YWIFYo23TYd9pAIEGa',0,'',NULL,0,1,'2025-08-09 11:16:33',NULL,NULL,'',1,NULL,'1231341'),
(13,NULL,2,NULL,'1231','',0,'a@123.com','',2,'','$2a$10$C6uGQJxJXBEcFchHpjrsrezu2fmCWGYS39f6PoAE24E4xmtrS.qm.',1,'',NULL,0,1,'2025-08-09 11:26:53',NULL,NULL,'',0,NULL,''),
(16,NULL,7,NULL,'20250809郭长福','郭长福',0,'','',0,'','$2a$10$oH6y2vuADvLXXLzInSlp7OTjbnYOZ.QhY0R1.j1l21TuAQl/S3PsW',1,'127.0.0.1','2025-08-09 14:16:23',0,1,'2025-08-09 13:07:12',16,'2025-08-09 14:16:23',NULL,0,NULL,'000518');
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `role_key` varchar(128) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` smallint DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3: 本部门数据权限 4: 本部门及以下数据权限 5: 本人权限）',
  `dept_id_set` varchar(1024) DEFAULT '' COMMENT '角色所拥有的部门数据权限',
  `status` smallint NOT NULL COMMENT '角色状态（1正常 0停用）',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sys_role` VALUES
(1,'超级管理员','admin',1,1,'',1,NULL,'2022-05-21 08:30:54',1,'2024-12-03 22:10:12','超级管理员',0),
(2,'普通角色','common',3,2,'',1,NULL,'2022-05-21 08:30:54',1,'2025-08-04 09:34:49','普通角色',0),
(3,'闲置角色','free',4,2,'',1,NULL,'2022-05-21 08:30:54',1,'2024-12-05 16:35:43','未使用的角色',0),
(4,'234','432',1,1,'',1,1,'2024-12-05 17:37:44',NULL,NULL,'',1),
(5,'12313213','2131313',1,1,'',1,1,'2024-12-26 15:33:24',1,'2024-12-31 16:48:48','',0),
(6,'test','test',1,1,'',1,1,'2025-01-13 21:21:44',1,'2025-10-20 10:15:03','',0),
(7,'心理测评角色','beice',2,2,'',1,1,'2025-08-09 11:05:19',NULL,NULL,'用于被测者的角色',0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;
commit;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(64) NOT NULL COMMENT '公告标题',
  `notice_type` smallint NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` text COMMENT '公告内容',
  `status` smallint NOT NULL DEFAULT '0' COMMENT '公告状态（1正常 0关闭）',
  `creator_id` bigint NOT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
set autocommit=0;
INSERT INTO `sys_notice` VALUES
(1,'温馨提醒：2018-07-01 Biology新版本发布啦',2,'新版本内容~~~~~~~~~~',1,1,'2022-05-21 08:30:55',1,'2022-08-29 20:12:37','管理员',0),
(2,'维护通知：2018-07-01 Biology系统凌晨维护',1,'维护内容',1,1,'2022-05-21 08:30:55',NULL,NULL,'管理员',0),
(3,'123',1,'213213',1,1,'2024-12-26 16:31:20',NULL,NULL,'',0);
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;
commit;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-10-23 17:52:08
