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
-- Table structure for table `manage_emergency_alarm`
--

DROP TABLE IF EXISTS `manage_emergency_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_alarm` (
  `emergency_alarm_id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '指标数值',
  `level` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `environment_id` bigint DEFAULT NULL,
  `equipment_data_id` bigint DEFAULT NULL,
  `detection_id` bigint DEFAULT NULL,
  PRIMARY KEY (`emergency_alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1297 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_jian_ce_device`
--

DROP TABLE IF EXISTS `manage_jian_ce_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_jian_ce_device` (
  `jian_ce_device_id` bigint NOT NULL AUTO_INCREMENT,
  `device_sn` varchar(255) DEFAULT NULL COMMENT '设备编号',
  `name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `area` varchar(255) DEFAULT NULL COMMENT '所属区域',
  `last_time` bigint DEFAULT NULL COMMENT '末次通讯时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`jian_ce_device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_threshold_sop`
--

DROP TABLE IF EXISTS `manage_threshold_sop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_threshold_sop` (
  `threshold_id` bigint DEFAULT NULL,
  `sop_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `manage_kong_tiao_data`
--

DROP TABLE IF EXISTS `manage_kong_tiao_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_kong_tiao_data` (
  `kong_tiao_data_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `device_sn` varchar(255) DEFAULT NULL COMMENT '设备序列号',
  `zhi_ban_gong_kuan_ya_li_she_ding` double DEFAULT NULL COMMENT '值班工况压力设定',
  `zhi_ban_gong_kuan_feng_liang_she_ding` double DEFAULT NULL COMMENT '值班工况风量设定',
  `feng_fa_wen_ding_zhuang_tai` int DEFAULT NULL COMMENT '风阀稳定状态',
  `fa_wei_fan_kuan` int DEFAULT NULL COMMENT '阀位反馈',
  `qiang_zhi_fa_wei_she_ding` int DEFAULT NULL COMMENT '强制阀位设定',
  `qiang_zhi_mo_shi_kai_guan` int DEFAULT NULL COMMENT '强制模式开关',
  `pid_kong_zhi_ji_fen_xi_shu` int DEFAULT NULL COMMENT 'PID控制积分系数',
  `pod_kong_zhi_bi_li_xi_shu` int DEFAULT NULL COMMENT 'PID控制比例系数',
  `feng_liang_fan_kui` int DEFAULT NULL COMMENT '风量反馈',
  `fang_jian_shi_ji_ya_li` double DEFAULT NULL COMMENT '房间实际压力',
  `gong_kuang_mo_shi` int DEFAULT NULL COMMENT '工况模式',
  `shuang_gong_kuang_qie_huan_shi_jian` int DEFAULT NULL COMMENT '双工况切换时间',
  `feng_liang_she_ding` int DEFAULT NULL COMMENT '风量设定',
  `ya_li_she_ding` double DEFAULT NULL COMMENT '压力设定',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`kong_tiao_data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_moni_threshold`
--

DROP TABLE IF EXISTS `manage_moni_threshold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_moni_threshold` (
  `moni_id` bigint NOT NULL COMMENT '模拟ID',
  `threshold_id` bigint DEFAULT NULL COMMENT '阈值Id',
  `environment_id` bigint DEFAULT NULL COMMENT '环境Id',
  `equipment_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_emergency`
--

DROP TABLE IF EXISTS `manage_emergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency` (
  `emergency_id` bigint NOT NULL AUTO_INCREMENT COMMENT '应急ID',
  `code` varchar(512) DEFAULT '' COMMENT '应急编号',
  `title` varchar(512) DEFAULT '' COMMENT '应急名称',
  `version` varchar(512) DEFAULT '' COMMENT '应急版本号',
  `dept_id` bigint DEFAULT NULL COMMENT '部门id',
  `scope` varchar(512) DEFAULT '' COMMENT '适用范围',
  `risk_type` varchar(512) DEFAULT '' COMMENT '风险类型',
  `creator_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`emergency_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应急信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_warehouse`
--

DROP TABLE IF EXISTS `manage_warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_warehouse` (
  `warehouse_id` bigint NOT NULL AUTO_INCREMENT COMMENT '入库ID',
  `materials_id` bigint DEFAULT NULL COMMENT '物料ID',
  `stock` double DEFAULT NULL COMMENT '库存量',
  `batch` varchar(255) DEFAULT NULL COMMENT '批次',
  `remain_stock` double DEFAULT NULL COMMENT '剩余库存',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`warehouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料档案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_knowledge_type`
--

DROP TABLE IF EXISTS `manage_knowledge_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_knowledge_type` (
  `knowledge_type_id` bigint NOT NULL AUTO_INCREMENT COMMENT '知识库类型ID',
  `type_name` varchar(512) DEFAULT '' COMMENT '知识库类型名称',
  `creator_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`knowledge_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='政策法规表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_sm_device`
--

DROP TABLE IF EXISTS `manage_sm_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_sm_device` (
  `sm_device_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `device_sn` varchar(512) DEFAULT NULL COMMENT '设备sn',
  `name` varchar(512) DEFAULT NULL COMMENT '设备名称',
  `personnel_id` bigint NOT NULL COMMENT '人员id',
  `area` varchar(512) DEFAULT NULL COMMENT '所属区域',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `last_time` bigint DEFAULT NULL,
  PRIMARY KEY (`sm_device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1280 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生命体征设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_equipment_maintenance_manual`
--

DROP TABLE IF EXISTS `manage_equipment_maintenance_manual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_equipment_maintenance_manual` (
  `manual_id` bigint NOT NULL AUTO_INCREMENT COMMENT '手册ID',
  `equipment_id` bigint NOT NULL COMMENT '设备ID',
  `manual_version` varchar(255) DEFAULT NULL COMMENT '手册版本',
  `manual_code` varchar(255) DEFAULT NULL COMMENT '手册编号',
  `is_enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用（1代表启用，0代表不启用）',
  `manual_file_path` json DEFAULT NULL COMMENT '手册附件路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `creator_id` bigint DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  PRIMARY KEY (`manual_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='维修手册表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_emergency_event_personnel`
--

DROP TABLE IF EXISTS `manage_emergency_event_personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_event_personnel` (
  `emergency_event_id` bigint DEFAULT NULL,
  `personnel_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_materials`
--

DROP TABLE IF EXISTS `manage_materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_materials` (
  `materials_id` bigint NOT NULL AUTO_INCREMENT COMMENT '物料档案ID',
  `name` varchar(255) DEFAULT NULL COMMENT '物料名称',
  `code` varchar(255) DEFAULT NULL COMMENT '物料编号',
  `materials_type` varchar(255) DEFAULT NULL COMMENT '物料类型',
  `specification` varchar(512) DEFAULT NULL COMMENT '规格',
  `stock` double DEFAULT NULL COMMENT '库存量',
  `unit` varchar(50) DEFAULT NULL COMMENT '单位',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `last_stock` double DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `out_stock` varchar(255) DEFAULT NULL,
  `batch` varchar(255) DEFAULT NULL,
  `model` varchar(512) DEFAULT NULL COMMENT '批次',
  `supplier` varchar(512) DEFAULT NULL COMMENT '批次',
  `color` varchar(512) DEFAULT NULL COMMENT '批次',
  `color_description` varchar(512) DEFAULT NULL COMMENT '批次',
  `total` double DEFAULT NULL,
  `technical_specification` varchar(255) DEFAULT NULL,
  `performance_parameters` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`materials_id`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料档案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_personnel_certificates_file`
--

DROP TABLE IF EXISTS `manage_personnel_certificates_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_personnel_certificates_file` (
  `personnel_id` bigint NOT NULL COMMENT '人员档案Id',
  `path` varchar(512) DEFAULT '' COMMENT '路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资质文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_equipment_inspection_manual`
--

DROP TABLE IF EXISTS `manage_equipment_inspection_manual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_equipment_inspection_manual` (
  `manual_id` bigint NOT NULL AUTO_INCREMENT COMMENT '手册ID',
  `equipment_id` bigint DEFAULT NULL COMMENT '设备ID',
  `suitable_scope` varchar(255) DEFAULT NULL COMMENT '适用范围',
  `manual_version` varchar(255) DEFAULT NULL COMMENT '手册版本',
  `manual_code` varchar(255) DEFAULT NULL COMMENT '手册编号',
  `is_enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用（1代表启用，0代表不启用）',
  `manual_file_path` json DEFAULT NULL COMMENT '手册附件路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `creator_id` bigint DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `manual_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`manual_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='检修手册表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_craft_archive`
--

DROP TABLE IF EXISTS `manage_craft_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_craft_archive` (
  `craft_archive_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工艺档案ID',
  `craft_archive_code` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '工艺档案编号',
  `craft_archive_name` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '工艺档案名称',
  `version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '版本',
  `creator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '工艺制定人员',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `attachment_path` json DEFAULT NULL COMMENT '附件地址',
  `label_name` varchar(512) DEFAULT NULL COMMENT '批次',
  `color` varchar(512) DEFAULT NULL COMMENT '批次',
  `color_description` varchar(512) DEFAULT NULL COMMENT '批次',
  PRIMARY KEY (`craft_archive_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='工艺档案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_keyword`
--

DROP TABLE IF EXISTS `manage_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_keyword` (
  `keyword_id` bigint NOT NULL AUTO_INCREMENT COMMENT '关键词ID',
  `keyword` varchar(512) DEFAULT '' COMMENT '关键词',
  `creator_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`keyword_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应急关键词表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_environment_sop`
--

DROP TABLE IF EXISTS `manage_environment_sop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_environment_sop` (
  `environment_id` bigint DEFAULT NULL,
  `sop_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_knowledge_file`
--

DROP TABLE IF EXISTS `manage_knowledge_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_knowledge_file` (
  `knowledge_id` bigint NOT NULL COMMENT '知识库id',
  `path` varchar(512) DEFAULT '' COMMENT '路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='知识库文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_cai_yang_data`
--

DROP TABLE IF EXISTS `manage_cai_yang_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_cai_yang_data` (
  `cai_yang_data_id` bigint NOT NULL AUTO_INCREMENT,
  `device_sn` varchar(255) DEFAULT NULL,
  `work_time` bigint DEFAULT NULL COMMENT '工作时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`cai_yang_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `manage_emergency_personnel`
--

DROP TABLE IF EXISTS `manage_emergency_personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_personnel` (
  `emergency_event_id` bigint DEFAULT NULL,
  `personnel_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_animation_craft_archive`
--

DROP TABLE IF EXISTS `manage_animation_craft_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_animation_craft_archive` (
  `animation_id` bigint DEFAULT NULL,
  `craft_archive_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_event_file`
--

DROP TABLE IF EXISTS `manage_event_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_event_file` (
  `event_id` bigint NOT NULL COMMENT '事件Id',
  `path` varchar(512) DEFAULT '' COMMENT '路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资质文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_sm_data`
--

DROP TABLE IF EXISTS `manage_sm_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_sm_data` (
  `sm_data_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sm_device_id` bigint NOT NULL COMMENT '设备Id',
  `battery` double DEFAULT NULL COMMENT '电量',
  `co2` double DEFAULT NULL COMMENT '二氧化碳',
  `humility` double DEFAULT NULL COMMENT '湿度',
  `temp` double DEFAULT NULL COMMENT '温度',
  `huxi` json DEFAULT NULL COMMENT '呼吸',
  `xin_dian` json DEFAULT NULL COMMENT '心电',
  `xinlv` double DEFAULT NULL COMMENT '心率',
  `xue_yang` double DEFAULT NULL COMMENT '血氧',
  `send_time` bigint DEFAULT NULL COMMENT '发送时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `ti_tai` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`sm_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1271 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生命体征设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_nong_du_device`
--

DROP TABLE IF EXISTS `manage_nong_du_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_nong_du_device` (
  `nong_du_device_id` bigint NOT NULL AUTO_INCREMENT,
  `device_sn` varchar(255) DEFAULT NULL COMMENT '设备编号',
  `name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `area` varchar(255) DEFAULT NULL COMMENT '所属区域',
  `last_time` bigint DEFAULT NULL COMMENT '末次通讯时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `receive_id` bigint DEFAULT NULL,
  `report_id` bigint DEFAULT NULL,
  PRIMARY KEY (`nong_du_device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_moni_personnel_healthy`
--

DROP TABLE IF EXISTS `manage_moni_personnel_healthy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_moni_personnel_healthy` (
  `healthy_moni_id` bigint NOT NULL AUTO_INCREMENT COMMENT '模拟ID',
  `field_type` varchar(255) DEFAULT NULL COMMENT '推送哪个',
  `personnel_id` bigint DEFAULT NULL COMMENT '阈值Id',
  `push_frequency` int DEFAULT NULL COMMENT '推送频率',
  `min` double DEFAULT NULL COMMENT '最小值',
  `max` double DEFAULT NULL COMMENT '最大值',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `is_push` tinyint DEFAULT '0',
  PRIMARY KEY (`healthy_moni_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_sop`
--

DROP TABLE IF EXISTS `manage_sop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_sop` (
  `sop_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'sopID',
  `name` varchar(255) NOT NULL COMMENT 'SOP名称',
  `code` varchar(255) NOT NULL COMMENT 'sop编号',
  `scope` varchar(255) NOT NULL COMMENT 'sop范围',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`sop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Sop信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_emergency_equipment`
--

DROP TABLE IF EXISTS `manage_emergency_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_equipment` (
  `emergency_id` bigint DEFAULT NULL,
  `equipment_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_personnel_card_file`
--

DROP TABLE IF EXISTS `manage_personnel_card_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_personnel_card_file` (
  `personnel_id` bigint NOT NULL COMMENT '人员档案Id',
  `path` varchar(512) DEFAULT '' COMMENT '路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='身份信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_policies_appendix`
--

DROP TABLE IF EXISTS `manage_policies_appendix`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_policies_appendix` (
  `policies_id` bigint NOT NULL COMMENT '政策法规ID',
  `path` varchar(512) DEFAULT '' COMMENT '路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='政策附件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_xl_archive`
--

DROP TABLE IF EXISTS `manage_xl_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_xl_archive` (
  `xl_archive_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'healthy_id',
  `personnel_id` bigint DEFAULT NULL COMMENT '人员档案ID',
  `user_id` bigint DEFAULT NULL COMMENT '账号ID',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`xl_archive_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1309 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='心理健康档案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_emergency_keyword`
--

DROP TABLE IF EXISTS `manage_emergency_keyword`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_keyword` (
  `emergency_id` bigint NOT NULL COMMENT '应急id',
  `keyword_id` bigint NOT NULL COMMENT '关键词ID',
  PRIMARY KEY (`emergency_id`,`keyword_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应急关键词关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_craft_node`
--

DROP TABLE IF EXISTS `manage_craft_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_craft_node` (
  `craft_node_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工艺节点ID',
  `node_code` varchar(255) NOT NULL COMMENT '节点编号',
  `node_name` varchar(255) NOT NULL COMMENT '工艺节点名称',
  `node_order` int NOT NULL COMMENT '节点顺序',
  `craft_archive_id` bigint NOT NULL COMMENT '所属工艺档案ID',
  `required_time` varchar(50) DEFAULT NULL COMMENT '所需时间',
  `node_tags` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '节点状态',
  `is_high_risk` tinyint(1) DEFAULT '0' COMMENT '是否为高风险',
  `operation_description` text COMMENT '操作描述',
  `operation_method` text COMMENT '操作方法',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `color` varchar(100) DEFAULT NULL,
  `label_name` text,
  `color_description` text,
  PRIMARY KEY (`craft_node_id`),
  KEY `idx_craft_archive_id` (`craft_archive_id`),
  KEY `idx_node_name` (`node_name`),
  KEY `idx_node_code` (`node_code`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工艺节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_xs_data`
--

DROP TABLE IF EXISTS `manage_xs_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_xs_data` (
  `xs_data_id` bigint NOT NULL AUTO_INCREMENT,
  `xs_device_id` bigint DEFAULT NULL,
  `end_time` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `receive_id` bigint DEFAULT NULL,
  `report_id` bigint DEFAULT NULL,
  `device_sn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`xs_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_environment_emergency`
--

DROP TABLE IF EXISTS `manage_environment_emergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_environment_emergency` (
  `environment_id` bigint DEFAULT NULL,
  `emergency_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_personnel_healthy`
--

DROP TABLE IF EXISTS `manage_personnel_healthy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_personnel_healthy` (
  `healthy_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'healthy_id',
  `personnel_id` bigint DEFAULT NULL,
  `temperature` double DEFAULT NULL COMMENT '体温',
  `heart_rate` double DEFAULT NULL COMMENT '心率',
  `high_blood_pressure` double DEFAULT NULL COMMENT '高压',
  `low_blood_pressure` double DEFAULT NULL COMMENT '低压',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`healthy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人员健康表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_personnel_healthy_alarm`
--

DROP TABLE IF EXISTS `manage_personnel_healthy_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_personnel_healthy_alarm` (
  `healthy_alarm_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'healthy_alarm_id',
  `healthy_id` bigint DEFAULT NULL,
  `type` varchar(512) DEFAULT NULL COMMENT '体温',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`healthy_alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人员健康表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_craft_process`
--

DROP TABLE IF EXISTS `manage_craft_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_craft_process` (
  `craft_process_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工艺流程图ID',
  `craft_archive_id` bigint NOT NULL COMMENT '所属工艺档案ID',
  `craft_node_id` bigint NOT NULL COMMENT '工艺节点ID',
  `node_order` int NOT NULL COMMENT '节点顺序',
  `personnel_factors` text COMMENT '人员要素',
  `equipment_factors` text COMMENT '设备要素',
  `material_factors` text COMMENT '原料要素',
  `environment_factors` text COMMENT '环境要素',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`craft_process_id`),
  KEY `idx_craft_archive_id` (`craft_archive_id`),
  KEY `idx_craft_node` (`craft_node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工艺流程图表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_kong_tiao_device`
--

DROP TABLE IF EXISTS `manage_kong_tiao_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_kong_tiao_device` (
  `kong_tiao_device_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `device_sn` varchar(255) DEFAULT NULL COMMENT '设备序列号',
  `name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `area` varchar(255) DEFAULT NULL COMMENT '所属区域',
  `last_time` bigint DEFAULT NULL COMMENT '最后一次时间',
  `extend` json DEFAULT NULL COMMENT '扩展字段，使用JacksonTypeHandler解析',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`kong_tiao_device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_notification`
--

DROP TABLE IF EXISTS `manage_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_notification` (
  `notification_id` bigint NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `notification_title` varchar(255) NOT NULL COMMENT '标题',
  `notification_content` text NOT NULL COMMENT '内容',
  `notification_type` varchar(255) NOT NULL COMMENT '类型',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `importance` tinyint(1) DEFAULT '0' COMMENT '重要程度(0-普通 1-重要)',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `receiver_id` bigint DEFAULT NULL COMMENT '接收者ID',
  `event_id` bigint DEFAULT NULL,
  `inspection_record_id` bigint DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `idx_type` (`notification_type`),
  KEY `idx_send_time` (`send_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1531 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='通知表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_nong_du_data`
--

DROP TABLE IF EXISTS `manage_nong_du_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_nong_du_data` (
  `nong_du_data_id` bigint NOT NULL AUTO_INCREMENT,
  `device_sn` varchar(255) DEFAULT NULL,
  `particle_concentration` double DEFAULT NULL COMMENT '粒子浓度',
  `biological_concentration` double DEFAULT NULL COMMENT '生物浓度',
  `end_time` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `alarm` int DEFAULT NULL,
  `working_status` int DEFAULT NULL,
  PRIMARY KEY (`nong_du_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_sop_equipment`
--

DROP TABLE IF EXISTS `manage_sop_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_sop_equipment` (
  `sop_id` bigint DEFAULT NULL,
  `equipment_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_materials_alarm`
--

DROP TABLE IF EXISTS `manage_materials_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_materials_alarm` (
  `emergency_alarm_id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '指标数值',
  `level` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`emergency_alarm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_dian_shui`
--

DROP TABLE IF EXISTS `manage_dian_shui`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_dian_shui` (
  `detection_id` bigint NOT NULL AUTO_INCREMENT,
  `environment_id` bigint DEFAULT NULL,
  `power` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `water_value` double DEFAULT NULL,
  `electricity_value` double DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`detection_id`)
) ENGINE=InnoDB AUTO_INCREMENT=361880 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_threshold_value`
--

DROP TABLE IF EXISTS `manage_threshold_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_threshold_value` (
  `threshold_id` bigint DEFAULT NULL,
  `min` double DEFAULT NULL COMMENT '最小值',
  `max` double DEFAULT NULL COMMENT '最大值',
  `level` varchar(255) NOT NULL COMMENT '级别'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `manage_xs_device`
--

DROP TABLE IF EXISTS `manage_xs_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_xs_device` (
  `xs_device_id` bigint NOT NULL AUTO_INCREMENT,
  `device_sn` varchar(255) DEFAULT NULL COMMENT '设备编号',
  `name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `area` varchar(255) DEFAULT NULL COMMENT '所属区域',
  `last_time` bigint DEFAULT NULL COMMENT '末次通讯时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `receive_id` bigint DEFAULT NULL,
  `report_id` bigint DEFAULT NULL,
  PRIMARY KEY (`xs_device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_threshold_emergency`
--

DROP TABLE IF EXISTS `manage_threshold_emergency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_threshold_emergency` (
  `threshold_id` bigint DEFAULT NULL,
  `emergency_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `manage_threshold`
--

DROP TABLE IF EXISTS `manage_threshold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_threshold` (
  `threshold_id` bigint NOT NULL AUTO_INCREMENT,
  `equipment_id` bigint DEFAULT NULL,
  `sensor_name` varchar(255) DEFAULT NULL,
  `sensor_model` varchar(255) DEFAULT NULL,
  `equipment_index` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `out_id` varchar(255) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`threshold_id`)
) ENGINE=InnoDB AUTO_INCREMENT=624 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人员上报';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_emergency_connect`
--

DROP TABLE IF EXISTS `manage_emergency_connect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_connect` (
  `emergency_alarm_id` bigint DEFAULT NULL,
  `emergency_id` bigint DEFAULT NULL,
  `sop_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_receive`
--

DROP TABLE IF EXISTS `manage_receive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_receive` (
  `receive_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'receive_id',
  `receive_user_id` bigint DEFAULT NULL COMMENT 'receive_user_id',
  `materials_id` bigint DEFAULT NULL COMMENT 'materials_id',
  `receive_explain` text COMMENT 'receive_explain',
  `receive_num` double DEFAULT NULL COMMENT '领用数量',
  `out_id` bigint DEFAULT NULL COMMENT 'out_id',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `type` varchar(512) DEFAULT NULL,
  `stock` double DEFAULT NULL COMMENT '当时库存数量',
  `batch` varchar(512) DEFAULT NULL COMMENT '批次',
  PRIMARY KEY (`receive_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='领用记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_xl_ceping_fangan`
--

DROP TABLE IF EXISTS `manage_xl_ceping_fangan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_xl_ceping_fangan` (
  `xl_fang_an_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `shi_juan_types` json DEFAULT NULL,
  `user_ids` json DEFAULT NULL,
  `ping_gu_time` bigint DEFAULT NULL,
  PRIMARY KEY (`xl_fang_an_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1281 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='心理测评方案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_equipment`
--

DROP TABLE IF EXISTS `manage_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_equipment` (
  `equipment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `equipment_code` varchar(255) DEFAULT NULL COMMENT '设备编号',
  `area` varchar(255) DEFAULT NULL COMMENT '所属区域',
  `equipment_name` varchar(255) DEFAULT NULL COMMENT '设备名称',
  `equipment_type` varchar(255) DEFAULT NULL COMMENT '设备型号',
  `manufacturer` varchar(255) DEFAULT NULL COMMENT '生产厂家',
  `purchase_date` date DEFAULT NULL COMMENT '购置日期',
  `technical_spec` varchar(255) DEFAULT NULL COMMENT '技术规格',
  `performance_params` varchar(255) DEFAULT NULL COMMENT '性能参数',
  `installation_location` varchar(255) DEFAULT NULL COMMENT '安装位置',
  `room_number` varchar(255) DEFAULT NULL COMMENT '房间号',
  `usage_status` varchar(50) DEFAULT NULL COMMENT '使用状态',
  `biosafetydata_name` varchar(255) DEFAULT NULL COMMENT '需要采集生物安全数据名称',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `monitoring_point` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '监测指标',
  PRIMARY KEY (`equipment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备档案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_craft_node_equipment`
--

DROP TABLE IF EXISTS `manage_craft_node_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_craft_node_equipment` (
  `craft_node_id` bigint NOT NULL COMMENT '工艺节点ID',
  `equipment_id` bigint NOT NULL COMMENT '设备ID',
  PRIMARY KEY (`craft_node_id`,`equipment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工艺节点设备关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `manage_materials_task`
--

DROP TABLE IF EXISTS `manage_materials_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_materials_task` (
  `materials_task_id` bigint NOT NULL AUTO_INCREMENT COMMENT '物料档案ID',
  `materials_id` bigint DEFAULT NULL COMMENT '物料ID',
  `stock` double DEFAULT NULL COMMENT '库存量',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`materials_task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5463 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='物料档案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_operation_log`
--

DROP TABLE IF EXISTS `sys_operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_operation_log` (
  `operation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `business_type` smallint NOT NULL DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `request_method` smallint NOT NULL DEFAULT '0' COMMENT '请求方式',
  `request_module` varchar(64) NOT NULL DEFAULT '' COMMENT '请求模块',
  `request_url` varchar(256) NOT NULL DEFAULT '' COMMENT '请求URL',
  `called_method` varchar(128) NOT NULL DEFAULT '' COMMENT '调用方法',
  `operator_type` smallint NOT NULL DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `user_id` bigint DEFAULT '0' COMMENT '用户ID',
  `username` varchar(32) DEFAULT '' COMMENT '操作人员',
  `operator_ip` varchar(128) DEFAULT '' COMMENT '操作人员ip',
  `operator_location` varchar(256) DEFAULT '' COMMENT '操作地点',
  `dept_id` bigint DEFAULT '0' COMMENT '部门ID',
  `dept_name` varchar(64) DEFAULT NULL COMMENT '部门名称',
  `operation_param` varchar(2048) DEFAULT '' COMMENT '请求参数',
  `operation_result` varchar(2048) DEFAULT '' COMMENT '返回参数',
  `status` smallint NOT NULL DEFAULT '1' COMMENT '操作状态（1正常 0异常）',
  `error_stack` varchar(2048) DEFAULT '' COMMENT '错误消息',
  `operation_time` datetime NOT NULL COMMENT '操作时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`operation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=937 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_door`
--

DROP TABLE IF EXISTS `manage_door`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_door` (
  `door_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'healthy_id',
  `personnel_id` bigint DEFAULT NULL,
  `door_code` varchar(512) DEFAULT NULL,
  `door_device_code` varchar(512) DEFAULT NULL,
  `door_place` varchar(512) DEFAULT NULL,
  `enter_status` varchar(512) DEFAULT NULL,
  `clock_in` int DEFAULT NULL,
  `clock_out` int DEFAULT NULL,
  `door_date` bigint DEFAULT NULL,
  `department` varchar(512) DEFAULT NULL,
  `name` varchar(512) DEFAULT NULL,
  `worktime` varchar(512) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL,
  `avatar` varchar(512) DEFAULT NULL,
  `job_number` varchar(512) DEFAULT NULL,
  `extra_id` varchar(512) DEFAULT NULL,
  `out_id` bigint DEFAULT NULL,
  `check_in_time` int DEFAULT NULL,
  `check_out_time` int DEFAULT NULL,
  `event_type` varchar(512) DEFAULT NULL,
  `verification_mode` varchar(512) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`door_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1844 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='门禁表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_environment`
--

DROP TABLE IF EXISTS `manage_environment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_environment` (
  `environment_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(512) DEFAULT NULL,
  `plc_address` varchar(512) DEFAULT NULL,
  `tag` varchar(512) DEFAULT NULL,
  `type` varchar(512) DEFAULT NULL,
  `scope` varchar(512) DEFAULT NULL,
  `e_signal` varchar(512) DEFAULT NULL,
  `supplier` varchar(512) DEFAULT NULL,
  `model` varchar(512) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `unit` varchar(512) DEFAULT NULL,
  `unit_name` varchar(512) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `e_area` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `monitoring_point` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '监测指标',
  PRIMARY KEY (`environment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_animation`
--

DROP TABLE IF EXISTS `manage_animation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_animation` (
  `animation_id` bigint NOT NULL AUTO_INCREMENT COMMENT '动画ID',
  `name` varchar(255) DEFAULT NULL COMMENT '动画名称',
  `description` text COMMENT '动画描述',
  `type` varchar(255) DEFAULT NULL COMMENT '动画类型',
  `key` varchar(255) DEFAULT NULL COMMENT '键值',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`animation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='动画表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_report_file`
--

DROP TABLE IF EXISTS `manage_report_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_report_file` (
  `report_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'report_id',
  `path` varchar(255) DEFAULT NULL COMMENT 'code',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人员上报文件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_equipment_data`
--

DROP TABLE IF EXISTS `manage_equipment_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_equipment_data` (
  `equipment_data_id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备数据ID',
  `equipment_id` bigint NOT NULL COMMENT '设备ID',
  `threshold_id` bigint DEFAULT NULL COMMENT '传感器阈值ID',
  `monitoring_indicator` varchar(255) DEFAULT NULL COMMENT '监测指标',
  `equipment_data` double DEFAULT NULL COMMENT '设备数据',
  `remark` text COMMENT '���注',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`equipment_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=217697 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_report`
--

DROP TABLE IF EXISTS `manage_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_report` (
  `report_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'report_id',
  `code` varchar(255) NOT NULL COMMENT 'code',
  `reporter_id` bigint DEFAULT NULL COMMENT 'reporter_id',
  `report_reason` text COMMENT 'reporter_id',
  `materials_id` bigint DEFAULT NULL COMMENT 'materials_id',
  `report_type` varchar(255) DEFAULT NULL COMMENT 'report_type',
  `report_num` double DEFAULT NULL COMMENT 'report_num',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `stock` double DEFAULT NULL COMMENT '当时库存数量',
  `batch` varchar(512) DEFAULT NULL COMMENT '批次',
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人员上报';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sys_login_info`
--

DROP TABLE IF EXISTS `sys_login_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_login_info` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户账号',
  `ip_address` varchar(128) NOT NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) NOT NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) NOT NULL DEFAULT '' COMMENT '浏览器类型',
  `operation_system` varchar(50) NOT NULL DEFAULT '' COMMENT '操作系统',
  `status` smallint NOT NULL DEFAULT '0' COMMENT '登录状态（1成功 0失败）',
  `msg` varchar(255) NOT NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7629 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_environment_detection`
--

DROP TABLE IF EXISTS `manage_environment_detection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_environment_detection` (
  `detection_id` bigint NOT NULL AUTO_INCREMENT,
  `environment_id` bigint DEFAULT NULL,
  `power` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `water_value` double DEFAULT NULL,
  `electricity_value` double DEFAULT NULL,
  `type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`detection_id`)
) ENGINE=InnoDB AUTO_INCREMENT=361539 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_sm_threshold`
--

DROP TABLE IF EXISTS `manage_sm_threshold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_sm_threshold` (
  `sm_device_id` bigint DEFAULT NULL,
  `min` double DEFAULT NULL,
  `max` double DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_alarmlevel_detail`
--

DROP TABLE IF EXISTS `manage_alarmlevel_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_alarmlevel_detail` (
  `alarmlevel_id` bigint DEFAULT NULL,
  `environment_id` bigint DEFAULT NULL,
  `min` double DEFAULT NULL,
  `max` double DEFAULT NULL,
  `level` varchar(512) DEFAULT NULL,
  `unit` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `manage_policies`
--

DROP TABLE IF EXISTS `manage_policies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_policies` (
  `policies_id` bigint NOT NULL AUTO_INCREMENT COMMENT '政策法规ID',
  `policies_code` varchar(512) DEFAULT '' COMMENT '编号',
  `policies_name` varchar(512) DEFAULT '' COMMENT '名称',
  `release_date` datetime DEFAULT NULL COMMENT '发布日期',
  `creator_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `type` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`policies_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='政策法规表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_xw_alarm`
--

DROP TABLE IF EXISTS `manage_xw_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_xw_alarm` (
  `xw_alarm_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `alarm_id` bigint NOT NULL COMMENT '外部报警ID',
  `camera_id` varchar(255) NOT NULL COMMENT '摄像头id',
  `pic_path` varchar(512) DEFAULT NULL COMMENT '处理后图片路径',
  `seat_number` varchar(255) NOT NULL COMMENT '机位号',
  `pic_path_org` varchar(512) NOT NULL COMMENT '原始图片路径',
  `time_stamp` bigint DEFAULT NULL COMMENT '时间戳',
  `flag` bigint DEFAULT NULL COMMENT '报警标志',
  `filter_flag` bigint DEFAULT NULL COMMENT '过滤标志',
  `function_type` bigint DEFAULT NULL COMMENT '功能类型',
  `display_flag` bigint DEFAULT NULL COMMENT '显示标志',
  `created_at` bigint DEFAULT NULL COMMENT '外部创建时间',
  `push_time` bigint DEFAULT NULL COMMENT '推送时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`xw_alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1270 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='行为报警表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_equipment_daily_inspection_record`
--

DROP TABLE IF EXISTS `manage_equipment_daily_inspection_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_equipment_daily_inspection_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `inspection_date` date NOT NULL COMMENT '巡检日期',
  `inspection_code` varchar(255) NOT NULL COMMENT '巡检编号',
  `task_description` text COMMENT '任务描述',
  `inspector` varchar(255) DEFAULT NULL COMMENT '巡检人员',
  `anomaly_count` int DEFAULT NULL COMMENT '异常数',
  `anomaly_description` text COMMENT '异常说明',
  `inspection_image_path` json DEFAULT NULL COMMENT '巡检图片路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `creator_id` bigint DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  `inspector_id` bigint DEFAULT NULL,
  `inspection_result` text,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='巡检记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `manage_alarmlevel`
--

DROP TABLE IF EXISTS `manage_alarmlevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_alarmlevel` (
  `alarmlevel_id` bigint NOT NULL AUTO_INCREMENT,
  `environment_id` varchar(255) DEFAULT NULL,
  `plc_address` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`alarmlevel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_threshold_wh`
--

DROP TABLE IF EXISTS `manage_threshold_wh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_threshold_wh` (
  `threshold_id` bigint NOT NULL AUTO_INCREMENT,
  `equipment_id` bigint DEFAULT NULL,
  `sensor_name` varchar(255) DEFAULT NULL,
  `sensor_model` varchar(255) DEFAULT NULL,
  `equipment_index` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `out_id` varchar(255) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`threshold_id`)
) ENGINE=InnoDB AUTO_INCREMENT=371 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人员上报';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_equipment_repair_record`
--

DROP TABLE IF EXISTS `manage_equipment_repair_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_equipment_repair_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `equipment_id` bigint NOT NULL COMMENT '设备ID',
  `repair_code` varchar(255) DEFAULT NULL COMMENT '维修编号',
  `repair_date` date NOT NULL COMMENT '维修日期',
  `fault_reason` text COMMENT '故障原因',
  `repair_content` text COMMENT '维修内容',
  `repair_personnel` varchar(255) DEFAULT NULL COMMENT '维修人员',
  `repair_cost` varchar(255) DEFAULT NULL COMMENT '维修费用',
  `repair_result` text COMMENT '维修结果',
  `repair_image_path` json DEFAULT NULL COMMENT '维修图片路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `creator_id` bigint DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='维修记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_moni`
--

DROP TABLE IF EXISTS `manage_moni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_moni` (
  `moni_id` bigint NOT NULL AUTO_INCREMENT COMMENT '模拟ID',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `threshold_id` bigint DEFAULT NULL COMMENT '阈值Id',
  `push_type` varchar(255) DEFAULT NULL COMMENT '推送类型',
  `push_frequency` int DEFAULT NULL COMMENT '推送频率',
  `min` double DEFAULT NULL COMMENT '最小值',
  `max` double DEFAULT NULL COMMENT '最大值',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `is_push` tinyint DEFAULT '0',
  PRIMARY KEY (`moni_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_event`
--

DROP TABLE IF EXISTS `manage_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_event` (
  `event_id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(255) DEFAULT NULL,
  `equipment_id` bigint DEFAULT NULL,
  `materials_id` bigint DEFAULT NULL,
  `environment_id` bigint DEFAULT NULL,
  `materials_value` double DEFAULT NULL,
  `environment_value` double DEFAULT NULL,
  `equipment_value` double DEFAULT NULL,
  `alarmlevel_id` bigint DEFAULT NULL,
  `threshold_id` bigint DEFAULT NULL,
  `materials_unit` varchar(255) DEFAULT NULL,
  `environment_unit` varchar(255) DEFAULT NULL,
  `equipment_unit` varchar(255) DEFAULT NULL,
  `materials_value_id` bigint DEFAULT NULL,
  `description` text,
  `handlerId` bigint DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL COMMENT '级别',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `craft_node_id` bigint DEFAULT NULL,
  `push_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1557 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_craft_dispose_manual`
--

DROP TABLE IF EXISTS `manage_craft_dispose_manual`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_craft_dispose_manual` (
  `craft_dispose_manual_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工艺数据处置手册ID',
  `craft_node_id` bigint NOT NULL COMMENT '工艺节点ID',
  `craft_archive_id` bigint NOT NULL COMMENT '所属工艺档案ID',
  `problem_description` text COMMENT '发生问题',
  `emergency_process` text COMMENT '紧急处理流程',
  `responsibility_division` varchar(255) DEFAULT NULL COMMENT '责任划分',
  `required_time` varchar(50) DEFAULT NULL COMMENT '所需时间',
  `preventive_measures` text COMMENT '预防措施',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`craft_dispose_manual_id`),
  KEY `idx_craft_node` (`craft_node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工艺数据处置手册表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_sm_alarm`
--

DROP TABLE IF EXISTS `manage_sm_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_sm_alarm` (
  `sm_alarm_id` bigint NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `device_sn` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `desciption` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`sm_alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1386 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生命报警表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_personnel`
--

DROP TABLE IF EXISTS `manage_personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_personnel` (
  `personnel_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'personnel_id',
  `code` varchar(255) DEFAULT NULL COMMENT 'code',
  `name` varchar(255) DEFAULT NULL COMMENT 'name',
  `card` varchar(255) DEFAULT NULL COMMENT '身份证号',
  `certificates` varchar(512) DEFAULT NULL COMMENT '岗位职责',
  `sex` varchar(10) DEFAULT NULL COMMENT 'sex',
  `department` varchar(255) DEFAULT NULL COMMENT 'department',
  `post` varchar(255) DEFAULT NULL COMMENT 'post',
  `p_rank` varchar(255) DEFAULT NULL COMMENT 'p_rank',
  `education` varchar(255) DEFAULT NULL COMMENT 'education',
  `contact` varchar(255) DEFAULT NULL COMMENT 'contact',
  `email` varchar(255) DEFAULT NULL COMMENT 'email',
  `entry_time` datetime DEFAULT NULL COMMENT '入职时间',
  `leave_time` datetime DEFAULT NULL COMMENT '离职时间',
  `out_id` bigint DEFAULT NULL COMMENT 'out_id',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`personnel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=398 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='人员档案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_xl_shijuan_result`
--

DROP TABLE IF EXISTS `manage_xl_shijuan_result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_xl_shijuan_result` (
  `result_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(255) DEFAULT NULL COMMENT '试卷类型',
  `score` double DEFAULT NULL COMMENT '得分',
  `xl_shi_juan_id` bigint DEFAULT NULL COMMENT '试卷ID',
  `xl_fang_an_id` bigint DEFAULT NULL COMMENT '方案ID',
  `result` json DEFAULT NULL COMMENT '结果',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `last_time` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `start_time` bigint DEFAULT NULL COMMENT '开始时间',
  `use_time` bigint DEFAULT NULL COMMENT '使用时间',
  `ce_ping` varchar(255) DEFAULT NULL,
  `gan_yu_fang_shi` varchar(255) DEFAULT NULL,
  `gan_yu_time` bigint DEFAULT NULL,
  `exec_user` varchar(255) DEFAULT NULL,
  `gan_yu_result` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`result_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1293 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='测评结果表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_user_notification`
--

DROP TABLE IF EXISTS `manage_user_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_user_notification` (
  `notification_id` bigint NOT NULL COMMENT '通知ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `read_time` datetime DEFAULT NULL COMMENT '读取时间',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`notification_id`,`user_id`),
  KEY `idx_notification` (`notification_id`),
  KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户通知状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_alarm`
--

DROP TABLE IF EXISTS `manage_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_alarm` (
  `alarm_id` bigint NOT NULL AUTO_INCREMENT,
  `materials_id` bigint DEFAULT NULL,
  `stock` double DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `receive_id` bigint DEFAULT NULL,
  `report_id` bigint DEFAULT NULL,
  PRIMARY KEY (`alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_emergency_event`
--

DROP TABLE IF EXISTS `manage_emergency_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_event` (
  `emergency_event_id` bigint NOT NULL AUTO_INCREMENT,
  `event_name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `processing_flow` varchar(512) DEFAULT NULL,
  `environment_id` bigint DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`emergency_event_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_equipment_inspection_record`
--

DROP TABLE IF EXISTS `manage_equipment_inspection_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_equipment_inspection_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `equipment_id` bigint NOT NULL COMMENT '设备ID',
  `inspection_code` varchar(255) NOT NULL COMMENT '检修编号',
  `inspection_date` date NOT NULL COMMENT '检修日期',
  `inspection_content` text COMMENT '检修内容',
  `inspector` varchar(255) DEFAULT NULL COMMENT '检修人员',
  `fault_reason` text COMMENT '故障原因',
  `inspection_image_path` json DEFAULT NULL COMMENT '检修图片路径',
  `inspection_report_path` json DEFAULT NULL COMMENT '检修报告路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `creator_id` bigint DEFAULT NULL,
  `updater_id` bigint DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='检修记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_xw_device`
--

DROP TABLE IF EXISTS `manage_xw_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_xw_device` (
  `xw_device_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `camera_id` varchar(255) NOT NULL COMMENT '摄像头id',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `seat_number` varchar(255) NOT NULL COMMENT '机位号',
  `content` varchar(255) NOT NULL COMMENT '机位对应内容',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`xw_device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1269 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='行为设备表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `manage_sop_file`
--

DROP TABLE IF EXISTS `manage_sop_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_sop_file` (
  `sop_id` bigint NOT NULL COMMENT '知识库id',
  `path` varchar(512) DEFAULT '' COMMENT '路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='sop文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_emergency_event_alarm`
--

DROP TABLE IF EXISTS `manage_emergency_event_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_event_alarm` (
  `emergency_event_id` bigint DEFAULT NULL,
  `emergency_alarm_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_materials_value`
--

DROP TABLE IF EXISTS `manage_materials_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_materials_value` (
  `materials_id` bigint DEFAULT NULL,
  `value` double DEFAULT NULL COMMENT '指标数值',
  `level` varchar(255) DEFAULT NULL,
  `s_condition` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_materials_connect`
--

DROP TABLE IF EXISTS `manage_materials_connect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_materials_connect` (
  `emergency_alarm_id` bigint NOT NULL AUTO_INCREMENT,
  `materials_id` bigint DEFAULT NULL,
  `sop_id` bigint DEFAULT NULL,
  PRIMARY KEY (`emergency_alarm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_alarm`
--

DROP TABLE IF EXISTS `manage_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_alarm` (
  `alarm_id` bigint NOT NULL AUTO_INCREMENT,
  `materials_id` bigint DEFAULT NULL,
  `stock` double DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `receive_id` bigint DEFAULT NULL,
  `report_id` bigint DEFAULT NULL,
  PRIMARY KEY (`alarm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_run_time`
--

DROP TABLE IF EXISTS `manage_run_time`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_run_time` (
  `run_time_id` bigint NOT NULL AUTO_INCREMENT,
  `equipment_id` bigint DEFAULT NULL,
  `environment_id` bigint DEFAULT NULL,
  `is_stop` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`run_time_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_emergency_file`
--

DROP TABLE IF EXISTS `manage_emergency_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_emergency_file` (
  `emergency_id` bigint NOT NULL COMMENT '应急id',
  `path` varchar(512) DEFAULT '' COMMENT '路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='应急文件信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_knowledge`
--

DROP TABLE IF EXISTS `manage_knowledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_knowledge` (
  `knowledge_id` bigint NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
  `knowledge_type_id` bigint DEFAULT NULL COMMENT '知识库类型ID',
  `code` varchar(512) DEFAULT '' COMMENT '知识库编号',
  `title` varchar(512) DEFAULT '' COMMENT '知识库标题',
  `view_count` bigint DEFAULT NULL COMMENT '浏览次数',
  `creator_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `knowledge_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`knowledge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='政策法规表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manage_xl_shijuan`
--

DROP TABLE IF EXISTS `manage_xl_shijuan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `manage_xl_shijuan` (
  `xl_shi_juan_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` varchar(255) DEFAULT NULL COMMENT '试卷类型',
  `name` longtext COMMENT '题目',
  `sort` int DEFAULT NULL COMMENT '排序',
  `creator_id` bigint DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater_id` bigint DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`xl_shi_juan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1399 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='心理测评方案表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-10-23 17:50:01
