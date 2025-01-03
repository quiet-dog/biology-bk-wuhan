create table manage_policies (
    policies_id bigint auto_increment comment '政策法规ID' primary key,
    policies_code varchar(512) default '' null comment '编号',
    policies_name varchar(512) default '' null comment '名称',
    type varchar(512) default '' null comment '类型',
    release_date datetime null comment '发布日期',
    creator_id bigint null comment '更新者ID',
    create_time datetime null comment '创建时间',
    updater_id bigint null comment '更新者ID',
    update_time datetime null comment '更新时间',
    remark varchar(512) null comment '备注',
    deleted tinyint(1) default 0 not null comment '删除标志（0代表存在 1代表删除）'
) comment '政策法规表';
create table manage_policies_appendix (
    policies_id bigint not null comment '政策法规ID',
    path varchar(512) default '' null comment '路径'
) comment '政策附件表';
create table manage_knowledge_type (
    knowledge_type_id bigint auto_increment comment '知识库类型ID' primary key,
    type_name varchar(512) default '' null comment '知识库类型名称',
    creator_id bigint null comment '更新者ID',
    create_time datetime null comment '创建时间',
    updater_id bigint null comment '更新者ID',
    update_time datetime null comment '更新时间',
    remark varchar(512) null comment '备注',
    deleted tinyint(1) default 0 not null comment '删除标志（0代表存在 1代表删除）'
) comment '知识库类型表';
create table manage_knowledge (
    knowledge_id bigint auto_increment comment '知识库ID' primary key,
    knowledge_type varchar(512) null comment '知识库类型',
    code varchar(512) default '' null comment '知识库编号',
    title varchar(512) default '' null comment '知识库标题',
    view_count bigint null comment '浏览次数',
    creator_id bigint null comment '更新者ID',
    create_time datetime null comment '创建时间',
    updater_id bigint null comment '更新者ID',
    update_time datetime null comment '更新时间',
    remark varchar(512) null comment '备注',
    deleted tinyint(1) default 0 not null comment '删除标志（0代表存在 1代表删除）'
) comment '知识库';
create table manage_knowledge_file (
    knowledge_id bigint not null comment '知识库id',
    path varchar(512) default '' null comment '路径'
) comment '知识库文件信息表';
create table manage_emergency (
    emergency_id bigint auto_increment comment '应急ID' primary key,
    code varchar(512) default '' null comment '应急编号',
    title varchar(512) default '' null comment '应急名称',
    version varchar(512) default '' null comment '应急版本号',
    dept_id bigint null comment '部门id',
    scope varchar(512) default '' null comment '适用范围',
    risk_type varchar(512) default '' null comment '风险类型',
    creator_id bigint null comment '更新者ID',
    create_time datetime null comment '创建时间',
    updater_id bigint null comment '更新者ID',
    update_time datetime null comment '更新时间',
    remark varchar(512) null comment '备注',
    deleted tinyint(1) default 0 not null comment '删除标志（0代表存在 1代表删除）'
) comment '应急信息表';
create table manage_keyword (
    keyword_id bigint auto_increment comment '关键词ID' primary key,
    keyword varchar(512) default '' null comment '关键词',
    creator_id bigint null comment '更新者ID',
    create_time datetime null comment '创建时间',
    updater_id bigint null comment '更新者ID',
    update_time datetime null comment '更新时间',
    remark varchar(512) null comment '备注',
    deleted tinyint(1) default 0 not null comment '删除标志（0代表存在 1代表删除）'
) comment '应急关键词表';
create table manage_emergency_file (
    emergency_id bigint not null comment '应急id',
    path varchar(512) default '' null comment '路径'
) comment '应急文件信息表';
create table manage_emergency_keyword (
    emergency_id bigint not null comment '应急id',
    keyword_id bigint not null comment '关键词ID',
    primary key (emergency_id, keyword_id)
) comment '应急关键词关联表';
create table manage_craft_archive (
    craft_archive_id bigint NOT NULL AUTO_INCREMENT COMMENT '工艺档案ID',
    craft_archive_code varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '工艺档案编号',
    craft_archive_name varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '工艺档案名称',
    version varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '版本',
    creator varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '工艺制定人员',
    create_date datetime NULL DEFAULT NULL COMMENT '创建日期',
    creator_id bigint NULL DEFAULT NULL COMMENT '创建者ID',
    create_time datetime NULL DEFAULT NULL COMMENT '创建时间',
    updater_id bigint NULL DEFAULT NULL COMMENT '更新者ID',
    update_time datetime NULL DEFAULT NULL COMMENT '更新时间',
    remark varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    attachment_path JSON COMMENT '附件地址',
    PRIMARY KEY (`craft_archive_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工艺档案表' ROW_FORMAT = Dynamic;
CREATE TABLE manage_craft_node (
  craft_node_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工艺节点ID',
  node_code VARCHAR(255) NOT NULL COMMENT '节点编号',
  node_name VARCHAR(255) NOT NULL COMMENT '工艺节点名称',
  node_order INT NOT NULL COMMENT '节点顺序',
  craft_archive_id BIGINT NOT NULL COMMENT '所属工艺档案ID',
  required_time VARCHAR(50) COMMENT '所需时间',
  node_tags VARCHAR(255) COMMENT '节点标签',
  is_high_risk TINYINT(1) DEFAULT 0 COMMENT '是否为高风险',
  operation_description TEXT COMMENT '操作描述',
  operation_method TEXT COMMENT '操作方法',
  creator_id BIGINT COMMENT '创建者ID',
  create_time DATETIME COMMENT '创建时间',
  updater_id BIGINT COMMENT '更新者ID',
  update_time DATETIME COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (craft_node_id),
  INDEX idx_craft_archive_id (craft_archive_id),
  INDEX idx_node_name (node_name),
  INDEX idx_node_code (node_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工艺节点表';
CREATE TABLE manage_craft_dispose_manual (
  craft_dispose_manual_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工艺数据处置手册ID',
  craft_node_id BIGINT NOT NULL COMMENT '工艺节点ID',
  craft_archive_id BIGINT NOT NULL COMMENT '所属工艺档案ID',
  problem_description TEXT COMMENT '发生问题',
  emergency_process TEXT COMMENT '紧急处理流程',
  responsibility_division VARCHAR(255) COMMENT '责任划分',
  required_time VARCHAR(50) COMMENT '所需时间',
  preventive_measures TEXT COMMENT '预防措施',
  creator_id BIGINT COMMENT '创建者ID',
  create_time DATETIME COMMENT '创建时间',
  updater_id BIGINT COMMENT '更新者ID',
  update_time DATETIME COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (craft_dispose_manual_id),
  INDEX idx_craft_node (craft_node_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工艺数据处置手册表';
CREATE TABLE manage_craft_process (
  craft_process_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '工艺流程图ID',
  craft_archive_id BIGINT NOT NULL COMMENT '所属工艺档案ID',
  craft_node_id BIGINT NOT NULL COMMENT '工艺节点ID',
  node_order INT NOT NULL COMMENT '节点顺序',
  personnel_factors TEXT COMMENT '人员要素',
  equipment_factors TEXT COMMENT '设备要素',
  material_factors TEXT COMMENT '原料要素',
  environment_factors TEXT COMMENT '环境要素',
  creator_id BIGINT COMMENT '创建者ID',
  create_time DATETIME COMMENT '创建时间',
  updater_id BIGINT COMMENT '更新者ID',
  update_time DATETIME COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (craft_process_id),
  INDEX idx_craft_archive_id (craft_archive_id),
  INDEX idx_craft_node (craft_node_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工艺流程图表';
CREATE TABLE manage_equipment (
  equipment_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  equipment_code VARCHAR(255) COMMENT '设备编号',
  area VARCHAR(255) COMMENT '所属区域',
  equipment_name VARCHAR(255) COMMENT '设备名称',
  equipment_type VARCHAR(255) COMMENT '设备型号',
  manufacturer VARCHAR(255) COMMENT '生产厂家',
  purchase_date DATE COMMENT '购置日期',
  technical_spec VARCHAR(255) COMMENT '技术规格',
  performance_params VARCHAR(255) COMMENT '性能参数',
  installation_location VARCHAR(255) COMMENT '安装位置',
  room_number VARCHAR(255) COMMENT '房间号',
  usage_status VARCHAR(50) COMMENT '使用状态',
  biosafetydata_name VARCHAR(255) COMMENT '需要采集生物安全数据名称',
  creator_id BIGINT COMMENT '创建者ID',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updater_id BIGINT COMMENT '更新者ID',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (equipment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备档案表';
CREATE TABLE manage_equipment_repair_record (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    repair_code VARCHAR(255) COMMENT '维修编号',
    repair_date DATE NOT NULL COMMENT '维修日期',
    fault_reason TEXT COMMENT '故障原因',
    repair_content TEXT COMMENT '维修内容',
    repair_personnel VARCHAR(255) COMMENT '维修人员',
    repair_cost VARCHAR(255) COMMENT '维修费用',
    repair_result TEXT COMMENT '维修结果',
    repair_image_path JSON COMMENT '维修图片路径',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修记录表';
CREATE TABLE manage_equipment_inspection_record (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    inspection_code VARCHAR(255) NOT NULL COMMENT '检修编号',
    inspection_date DATE NOT NULL COMMENT '检修日期',
    inspection_content TEXT COMMENT '检修内容',
    inspector VARCHAR(255) COMMENT '检修人员',
    fault_reason TEXT COMMENT '故障原因',
    inspection_image_path JSON COMMENT '检修图片路径',
    inspection_report_path JSON COMMENT '检修报告路径',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检修记录表';
CREATE TABLE manage_equipment_maintenance_manual (
    manual_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '手册ID',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    manual_version VARCHAR(255) COMMENT '手册版本',
    manual_code VARCHAR(255) COMMENT '手册编号',
    is_enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用（1代表启用，0代表不启用）',
    manual_file_path JSON COMMENT '手册附件路径',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修手册表';
CREATE TABLE manage_equipment_inspection_manual (
    manual_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '手册ID',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    suitable_scope VARCHAR(255) COMMENT '适用范围',
    manual_version VARCHAR(255) COMMENT '手册版本',
    manual_code VARCHAR(255) COMMENT '手册编号',
    is_enabled TINYINT(1) DEFAULT 1 COMMENT '是否启用（1代表启用，0代表不启用）',
    manual_file_path JSON COMMENT '手册附件路径',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检修手册表';
CREATE TABLE manage_equipment_daily_inspection_record (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    inspection_date DATE NOT NULL COMMENT '巡检日期',
    inspection_code VARCHAR(255) NOT NULL COMMENT '巡检编号',
    task_description TEXT COMMENT '任务描述',
    inspector VARCHAR(255) COMMENT '巡检人员',
    anomaly_count INT COMMENT '异常数',
    anomaly_description TEXT COMMENT '异常说明',
    inspection_image_path JSON COMMENT '巡检图片路径',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='巡检记录表';
CREATE TABLE manage_equipment_data (
    equipment_data_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '设备数据ID',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    `threshold_id` BIGINT COMMENT '传感器阈值ID',
    monitoring_indicator VARCHAR(255) COMMENT '监测指标',
    equipment_data DOUBLE COMMENT '设备数据',
    remark TEXT COMMENT '备注',
    creator_id BIGINT COMMENT '创建者ID',
    updater_id BIGINT COMMENT '更新者ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备数据表';
CREATE TABLE `manage_sop` (
  `sop_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'sopID',
  `name` VARCHAR(255) COMMENT 'SOP名称',
  `code` VARCHAR(255) COMMENT 'sop编号',
  `scope` VARCHAR(255) COMMENT 'sop范围',
  creator_id BIGINT COMMENT '创建者ID',
  create_time DATETIME COMMENT '创建时间',
  updater_id BIGINT COMMENT '更新者ID',
  update_time DATETIME COMMENT '更新时间',
  deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`sop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Sop信息表';
create table manage_sop_file (
    sop_id bigint not null comment '知识库id',
    path varchar(512) default '' null comment '路径'
) comment 'sop文件信息表';
CREATE TABLE `manage_personnel` (
  `personnel_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'personnel_id',
  `code` VARCHAR(255) NULL COMMENT 'code',
  `name` VARCHAR(255) NULL COMMENT 'name',
  `card` VARCHAR(255) NULL COMMENT '身份证号',
  `certificates` VARCHAR(512) NULL COMMENT '岗位职责',
  `sex` VARCHAR(10) NULL COMMENT 'sex',
  `department` VARCHAR(255) NULL COMMENT 'department',
  `post` VARCHAR(255) NULL COMMENT 'post',
  `p_rank` VARCHAR(255) NULL COMMENT 'p_rank',
  `education` VARCHAR(255) NULL COMMENT 'education',
  `contact` VARCHAR(255) NULL COMMENT 'contact',
  `email` VARCHAR(255) NULL COMMENT 'email',
  `entry_time` DATETIME DEFAULT NULL COMMENT '入职时间',
  `leave_time` DATETIME DEFAULT NULL COMMENT '离职时间',
  `out_id` BIGINT(20) DEFAULT NULL COMMENT 'out_id',
  `creator_id` BIGINT COMMENT '创建者ID',
  `create_time` DATETIME COMMENT '创建时间',
  `updater_id` BIGINT COMMENT '更新者ID',
  `update_time` DATETIME COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`personnel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员档案';
create table manage_personnel_card_file (
    personnel_id bigint not null comment '人员档案Id',
    path varchar(512) default '' null comment '路径'
) comment '身份信息表';
create table manage_personnel_certificates_file (
    personnel_id bigint not null comment '人员档案Id',
    path varchar(512) default '' null comment '路径'
) comment '资质文件表';
CREATE TABLE `manage_personnel_healthy` (
  `healthy_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'healthy_id',
  `personnel_id` BIGINT,
  `temperature` DOUBLE COMMENT '体温',
  `heart_rate` DOUBLE COMMENT '心率',
  `high_blood_pressure` DOUBLE COMMENT '高压',
  `low_blood_pressure` DOUBLE COMMENT '低压',
  `creator_id` BIGINT COMMENT '创建者ID',
  `create_time` DATETIME COMMENT '创建时间',
  `updater_id` BIGINT COMMENT '更新者ID',
  `update_time` DATETIME COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`healthy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员健康表';
CREATE TABLE `manage_personnel_healthy_alarm` (
  `healthy_alarm_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'healthy_alarm_id',
  `healthy_id` BIGINT,
  `type` VARCHAR(512) COMMENT '体温',
  `creator_id` BIGINT COMMENT '创建者ID',
  `create_time` DATETIME COMMENT '创建时间',
  `updater_id` BIGINT COMMENT '更新者ID',
  `update_time` DATETIME COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`healthy_alarm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员健康表';
CREATE TABLE `manage_materials` (
  `materials_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '物料档案ID',
  `name` VARCHAR(255) COMMENT '物料名称',
  `code` VARCHAR(255) COMMENT '物料编号',
  `materials_type` VARCHAR(255) COMMENT '物料类型',
  `specification` VARCHAR(512) COMMENT '规格',
  `stock` DOUBLE COMMENT '库存量',
  `unit` VARCHAR(50) COMMENT '单位',
  `creator_id` BIGINT COMMENT '创建者ID',
  `create_time` DATETIME COMMENT '创建时间',
  `updater_id` BIGINT COMMENT '更新者ID',
  `update_time` DATETIME COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`materials_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料档案';
CREATE TABLE `manage_materials_task` (
  `materials_task_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '物料档案ID',
  `materials_id` BIGINT COMMENT '物料ID',
  `stock` DOUBLE COMMENT '库存量',
  `creator_id` BIGINT COMMENT '创建者ID',
  `create_time` DATETIME COMMENT '创建时间',
  `updater_id` BIGINT COMMENT '更新者ID',
  `update_time` DATETIME COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`materials_task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料档案';
CREATE TABLE `manage_receive` (
  `receive_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'receive_id',
  `receive_user_id` BIGINT COMMENT 'receive_user_id',
  `materials_id` BIGINT(20) COMMENT 'materials_id',
  `receive_explain` TEXT COMMENT 'receive_explain',
  `receive_num` DOUBLE COMMENT '领用数量',
  `out_id` BIGINT(20) DEFAULT NULL COMMENT 'out_id',
  `creator_id` BIGINT COMMENT '创建者ID',
  `create_time` DATETIME COMMENT '创建时间',
  `updater_id` BIGINT COMMENT '更新者ID',
  `update_time` DATETIME COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`receive_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='领用记录';
CREATE TABLE `manage_report` (
  `report_id` bigint auto_increment PRIMARY KEY COMMENT 'report_id',
  `code` VARCHAR(255) NOT NULL COMMENT 'code',
  `report_reason` Text COMMENT 'reporter_id',
  `materials_id` bigint COMMENT 'materials_id',
  `report_type` VARCHAR(255)  COMMENT 'report_type',
  `report_num` DOUBLE COMMENT 'report_num',
  `creator_id` BIGINT COMMENT '创建者ID',
  `create_time` DATETIME COMMENT '创建时间',
  `updater_id` BIGINT COMMENT '更新者ID',
  `update_time` DATETIME COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员上报';
CREATE TABLE `manage_report_file` (
  `report_id` bigint auto_increment PRIMARY KEY COMMENT 'report_id',
  `path` VARCHAR(255) COMMENT 'code'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员上报文件';
CREATE TABLE manage_threshold (
    `threshold_id` bigint auto_increment PRIMARY KEY,
    `equipment_id` bigint,
    `sensor_name` VARCHAR(255),
    `sensor_model` VARCHAR(255),
    `equipment_index` VARCHAR(255),
    `unit` VARCHAR(255),
    `code` VARCHAR(255),
    `purchase_date` DATE,
    `out_id` VARCHAR(255),
    `creator_id` BIGINT COMMENT '创建者ID',
    `create_time` DATETIME COMMENT '创建时间',
    `updater_id` BIGINT COMMENT '更新者ID',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人员上报';
CREATE TABLE manage_threshold_value (
    threshold_id BIGINT,
    min DOUBLE COMMENT '最小值',
    max DOUBLE COMMENT '最大值',
    level VARCHAR(255) NOT NULL COMMENT '级别'
);
CREATE TABLE manage_threshold_emergency (
    threshold_id BIGINT,
    emergency_id BIGINT
);
CREATE TABLE manage_threshold_sop (
    threshold_id BIGINT,
    sop_id BIGINT
);
CREATE TABLE manage_alarmlevel (
    `alarmlevel_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `environment_id` VARCHAR(255),
    `plc_address` VARCHAR(255),
    `unit` VARCHAR(255),
    `enable` BOOLEAN,
    `creator_id` BIGINT COMMENT '创建者ID',
    `create_time` DATETIME COMMENT '创建时间',
    `updater_id` BIGINT COMMENT '更新者ID',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
);
CREATE TABLE manage_alarmlevel_detail (
    `alarmlevel_id` BIGINT,
    `environment_id` BIGINT,
    `min` DOUBLE,
    `max` DOUBLE,
    `level` VARCHAR(512),
    `unit` VARCHAR(512)
);
CREATE TABLE manage_environment (
    `environment_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `description` VARCHAR(512),
    `plc_address` VARCHAR(512),
    `monitoring_point` VARCHAR(512),
    `e_area` VARCHAR(512),
    `tag` VARCHAR(512),
    `type` VARCHAR(512),
    `scope` VARCHAR(512),
    `e_signal` VARCHAR(512),
    `supplier` VARCHAR(512),
    `model` VARCHAR(512),
    `value` DOUBLE,
    `unit` VARCHAR(512),
    `unit_name` VARCHAR(512),
    `creator_id` BIGINT COMMENT '创建者ID',
    `create_time` DATETIME COMMENT '创建时间',
    `updater_id` BIGINT COMMENT '更新者ID',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
);
CREATE TABLE manage_environment_sop (
    `environment_id` BIGINT,
    `sop_id` BIGINT
);
CREATE TABLE manage_environment_emergency (
    `environment_id` BIGINT,
    `emergency_id` BIGINT
);
CREATE TABLE manage_environment_detection (
    `detection_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `environment_id` BIGINT,
    `power` DOUBLE,
    `value` DOUBLE,
    `water_value` DOUBLE,
    `electricity_value` DOUBLE,
    `creator_id` BIGINT COMMENT '创建者ID',
    `create_time` DATETIME COMMENT '创建时间',
    `updater_id` BIGINT COMMENT '更新者ID',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'
);
CREATE TABLE manage_event (
    `event_id` BIGINT AUTO_INCREMENT PRIMARY KEY,  
    `type` VARCHAR(255),                          
    `equipment_id` BIGINT,
    `materials_id` BIGINT,
    `environment_id` BIGINT,
    `craft_node_id` BIGINT,
    `materials_value` DOUBLE,                           
    `environment_value` DOUBLE,                           
    `equipment_value` DOUBLE,
    `alarmlevel_id` BIGINT,
    `threshold_id` BIGINT,
    `materials_unit` VARCHAR(255),                           
    `environment_unit` VARCHAR(255),                           
    `equipment_unit` VARCHAR(255),
    `materials_value_id` BIGINT,                           
    `description` TEXT,                           
    `handlerId` BIGINT,
    `level` VARCHAR(255) COMMENT '级别',
    `creator_id` BIGINT COMMENT '创建者ID',
    `create_time` DATETIME COMMENT '创建时间',
    `updater_id` BIGINT COMMENT '更新者ID',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'                 
);
create table manage_event_file (
    event_id bigint not null comment '事件Id',
    path varchar(512) default '' null comment '路径'
) comment '资质文件表';
CREATE TABLE manage_alarm (
    `alarm_id` BIGINT AUTO_INCREMENT PRIMARY KEY,  
    `materials_id` BIGINT,
    `stock` DOUBLE,
    `level` VARCHAR(255),                         
    `creator_id` BIGINT COMMENT '创建者ID',
    `create_time` DATETIME COMMENT '创建时间',
    `updater_id` BIGINT COMMENT '更新者ID',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'                 
);
CREATE TABLE manage_materials_value (
    `materials_id` BIGINT,
    `value` DOUBLE COMMENT '指标数值',                          
    `level` VARCHAR(255),                             
    `s_condition` VARCHAR(255)                                    
);
CREATE TABLE manage_emergency_alarm (
    `emergency_alarm_id` BIGINT AUTO_INCREMENT PRIMARY KEY,  
    `environment_id` BIGINT,
    `equipment_data_id` BIGINT,
    `detection_id` BIGINT,
    `code` VARCHAR(255),
    `type`VARCHAR(255) COMMENT '指标数值',                          
    `level` VARCHAR(255),                             
    `description` VARCHAR(255),                           
    `creator_id` BIGINT COMMENT '创建者ID',
    `create_time` DATETIME COMMENT '创建时间',
    `updater_id` BIGINT COMMENT '更新者ID',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'                 
);
CREATE TABLE manage_emergency_event (
    `emergency_event_id` BIGINT AUTO_INCREMENT PRIMARY KEY,  
    `event_name` VARCHAR(255),                          
    `code` VARCHAR(255),
    `type` VARCHAR(255) COMMENT '事件类型',
    `content` VARCHAR(255),
    `processing_flow` VARCHAR(512),
    `environment_id` BIGINT,
    `creator_id` BIGINT COMMENT '创建者ID',
    `create_time` DATETIME COMMENT '创建时间',
    `updater_id` BIGINT COMMENT '更新者ID',
    `update_time` DATETIME COMMENT '更新时间',
    `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）'                 
);
CREATE TABLE manage_emergency_event_personnel (
    `emergency_event_id` BIGINT,
    `personnel_id` BIGINT
);
CREATE TABLE manage_emergency_event_alarm (
    `emergency_event_id` BIGINT,
    `emergency_alarm_id` BIGINT
);
CREATE TABLE manage_emergency_connect (
    `emergency_alarm_id` BIGINT,
    `emergency_id` BIGINT,
    `sop_id` BIGINT
);
CREATE TABLE manage_notification (
    notification_id BIGINT AUTO_INCREMENT COMMENT '通知ID',
    notification_title VARCHAR(255) COMMENT '标题',
    notification_content TEXT COMMENT '内容',
    notification_type VARCHAR(255) COMMENT '类型',
    creator_id BIGINT COMMENT '创建者ID',
    importance TINYINT(1) DEFAULT 0 COMMENT '重要程度(0-普通 1-重要)',
    receiver_id BIGINT COMMENT '接收者ID',
    send_time DATETIME COMMENT '发送时间',
    create_time DATETIME COMMENT '创建时间',
    updater_id BIGINT COMMENT '更新者ID',
    update_time DATETIME COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    PRIMARY KEY (notification_id),
    INDEX idx_type (notification_type),
    INDEX idx_send_time (send_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知表';
CREATE TABLE manage_user_notification (
    user_notification_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户通知ID',
    notification_id BIGINT NOT NULL COMMENT '通知ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    read_time DATETIME COMMENT '读取时间',
    creator_id BIGINT COMMENT '创建者ID',
    create_time DATETIME COMMENT '创建时间',
    updater_id BIGINT COMMENT '更新者ID',
    update_time DATETIME COMMENT '更新时间',
    deleted TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
    INDEX idx_user_notification_id (user_notification_id),
    INDEX idx_notification (notification_id),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户通知状态表';

CREATE TABLE manage_sop_equipment (
    `sop_id` BIGINT,
    `equipment_id` BIGINT
);
CREATE TABLE manage_emergency_equipment (
    `emergency_id` BIGINT,
    `equipment_id` BIGINT
);
CREATE TABLE manage_craft_node_equipment (
    craft_node_id BIGINT NOT NULL COMMENT '工艺节点ID',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    PRIMARY KEY (craft_node_id, equipment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工艺节点设备关联表';
CREATE TABLE `manage_door` (
  `door_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'healthy_id',
  `personnel_id` BIGINT,
  `door_code` VARCHAR(512),
  `door_device_code` VARCHAR(512),
  `door_place` VARCHAR(512),
  `enter_status` VARCHAR(512),
  `clock_in` INT,
  `clock_out` INT,
  `door_date` INT,
  `department` VARCHAR(512),
  `name` VARCHAR(512),
  `worktime` VARCHAR(512),
  `description` VARCHAR(512),
  `avatar` VARCHAR(512),
  `job_number` VARCHAR(512),
  `extra_id` VARCHAR(512),
  `out_id` BIGINT,
  `check_in_time` INT,
  `check_out_time` INT,
  `event_type` VARCHAR(512),
  `verification_mode` VARCHAR(512),
  `creator_id` BIGINT COMMENT '创建者ID',
  `create_time` DATETIME COMMENT '创建时间',
  `updater_id` BIGINT COMMENT '更新者ID',
  `update_time` DATETIME COMMENT '更新时间',
  `deleted` TINYINT(1) DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  PRIMARY KEY (`door_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门禁表';