-- 创建 数据库
-- 创建 公司表、部门表、用户表、字典表、菜单表、角色菜单表、用户菜单表、用户公司表、用户部门表、操作记录明细表，仓库表，用户仓库权限表，库位表、物流档案表、用户物流权限表
--      品类表、运费项目表、费用折扣表、费用项表、运费计算规则表、运费价格明细表
CREATE DATABASE IF NOT EXISTS dongpengmos default character set utf8mb4 COLLATE utf8mb4_general_ci;
USE dongpengmos;

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company` (
   id                   bigint not null COMMENT 'id',
   company_code         varchar(10) COMMENT '公司编码',
   company_name         varchar(20) COMMENT '公司名称',
   company_duty         varchar(20) COMMENT '公司税号',
   ele_invoice          boolean COMMENT '电子发票',
   company_type         varchar(10) COMMENT '公司类型',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司表';
ALTER TABLE `sys_company`
ADD UNIQUE INDEX (`company_code`) ;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
   id                   bigint not null COMMENT 'id',
   department_code      varchar(30) COMMENT '部门编码',
   department_name      varchar(10) COMMENT '部门名称',
   /*updepartment_id      bigint COMMENT '上级部门ID',*/
   company_id           bigint COMMENT '所属公司 ID',
   company_name         varchar(20) COMMENT '所属公司名称',
   o2o_flag             boolean COMMENT '是否启用O2O',
   shop_name            varchar(20) COMMENT '店铺名称',
   single_letters       varchar(3) COMMENT '单号字母',
   dock_system          varchar(10) COMMENT '对接系统',
   dock_custNo          varchar(20) COMMENT '对接客编',
   leader               varchar(10) COMMENT '负责人',
   phone                varchar(11) COMMENT '联系电话',
   parent_ids           varchar(2000) COMMENT '所有上级部门id',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
   parent_id            bigint comment '上级id',
   parent_name          varchar(64) comment '上级名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';
ALTER TABLE `sys_department`
ADD UNIQUE INDEX (`department_code`) ;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
   id                   bigint not null COMMENT 'id',
   dictionary_name      varchar(20) COMMENT '字典项名称',
   detail_name          varchar(255) COMMENT '字典项明细名称',
   detail_code          varchar(64) COMMENT '字典项明细code',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典项表';
ALTER TABLE `sys_dictionary`
   ADD UNIQUE INDEX (`detail_code`) ;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
   id                   bigint not null COMMENT 'id',
   user_code            varchar(10) COMMENT '用户工号',
   user_name            varchar(10) COMMENT '用户名称',
   password             varchar(64) COMMENT '帐号密码',
   department_id        bigint COMMENT '部门ID',
   department_name      varchar(20) COMMENT '部门名称',
   role_id              bigint COMMENT '角色ID（字典项ID）',
   role_name            varchar(20) COMMENT '角色名称（字典项名称）',
   phone                varchar(11) COMMENT '联系方式',
   user_type            bigint COMMENT '用户类型ID',
   user_typename        varchar(20) COMMENT '用户类型名称',
   user_binding         varchar(20) COMMENT '绑定客编',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
ALTER TABLE `sys_user`
ADD UNIQUE INDEX (`user_code`) ;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
   id                   bigint not null COMMENT 'id',
   menu_code            varchar(64) COMMENT '菜单编码',
   meun_icon            varchar(10) COMMENT '图标',
   meun_type            varchar(10) COMMENT '菜单类型',
   menu_name            varchar(10) COMMENT '菜单名称',
   menu_link            varchar(200) COMMENT '菜单链接',
   menu_folder          varchar(255) COMMENT '菜单文件夹路径',
   meun_sequence        int COMMENT '排序号',
   parent_id            bigint COMMENT '上级菜单id',
   parent_name          varchar(10) COMMENT '上级菜单名称',
   parent_ids           varchar(2000) COMMENT '所有上级id',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';
ALTER TABLE `sys_menu`
ADD UNIQUE INDEX (`menu_code`) ;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
   id                   bigint not null COMMENT 'id',
   dictionary_id        bigint COMMENT '角色id',
   menu_id              bigint COMMENT '菜单ID',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- ----------------------------
-- Table structure for sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu`;
CREATE TABLE `sys_user_menu` (
   id                   bigint not null COMMENT 'id',
   user_id              bigint COMMENT '用户id',
   menu_id              bigint COMMENT '菜单ID',
   dictionary_id        bigint COMMENT '角色id',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户菜单表';

-- ----------------------------
-- Table structure for sys_user_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_company`;
CREATE TABLE `sys_user_company` (
   id                   bigint not null COMMENT 'id',
   user_id              bigint COMMENT '用户id',
   company_id           bigint COMMENT '公司ID',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户公司权限表';

-- ----------------------------
-- Table structure for sys_user_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_department`;
CREATE TABLE `sys_user_department` (
   id                   bigint not null COMMENT 'id',
   user_id              bigint COMMENT '用户id',
   department_id        bigint COMMENT '部门ID',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户部门权限表';

-- ----------------------------
-- Table structure for operating_record
-- ----------------------------
DROP TABLE IF EXISTS `operating_record`;
CREATE TABLE `operating_record` (
   id                   bigint not null COMMENT 'id',
   relevance_id      bigint COMMENT '关联订单id',
   relevance_code          varchar(32) COMMENT '关联订单单号',
   handledescribe          varchar(100) COMMENT '操作描述',
   handlerecord          varchar(500) COMMENT '操作记录',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作记录明细表';

-- ----------------------------
-- Table structure for sys_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `sys_warehouse`;
CREATE TABLE `sys_warehouse` (
   id                   bigint not null COMMENT 'id',
   warehouse_code       varchar(10) COMMENT '库区编码',
   warehouse_name       varchar(20) COMMENT '库区名称',
   parent_id            bigint comment '父类id',
   parent_name          varchar(20) comment '父类名称',
   company_id           bigint COMMENT '所属公司 ID',
   company_name         varchar(20) COMMENT '所属公司名称',
   warehouse_system     varchar(10) COMMENT '仓库对应系统',
   warehouse_type       varchar(10) COMMENT '库区类型',
   corr_identifying     varchar(10) COMMENT '对应标识',
   inventory            boolean COMMENT '负库存',
   province             varchar(10) COMMENT '发货省',
   city                 varchar(10) COMMENT '发货市',
   district             varchar(10) COMMENT '发货区',
   address              varchar(100) COMMENT '发货地址',
   location             boolean COMMENT '是否启用库位',
   parent_ids           varchar(2000) COMMENT '所有上级库区id',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
   parent_id            bigint COMMENT '上级id',
   parent_name          varchar(10) COMMENT '上级名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库表';
ALTER TABLE `sys_warehouse`
ADD UNIQUE INDEX (`warehouse_code`) ;

-- ----------------------------
-- Table structure for sys_user_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_warehouse`;
CREATE TABLE `sys_user_warehouse` (
   id                   bigint not null COMMENT 'id',
   user_id              bigint COMMENT '用户id',
   warehouse_id        bigint COMMENT '仓库ID',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户仓库权限表';

-- ----------------------------
-- Table structure for sys_storage_location
-- ----------------------------
DROP TABLE IF EXISTS `sys_storage_location`;
CREATE TABLE `sys_storage_location` (
   id                   bigint not null COMMENT 'id',
   storagelocation_name varchar(10) COMMENT '库位名称',
   warehouse_id         bigint COMMENT '仓库ID',
   warehouse_name       varchar(10) COMMENT '库区名称',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='库位表';


-- ----------------------------
-- Table structure for sys_logistics
-- ----------------------------
DROP TABLE IF EXISTS `sys_logistics`;
CREATE TABLE `sys_logistics` (
   id                   bigint not null COMMENT 'id',
   logistics_name       varchar(10) COMMENT '物流公司名称',
   logistics_code       varchar(2) COMMENT '编码',
   tax_invoice          int COMMENT '发票税率',
   tm_code              varchar(5) COMMENT '天猫回写编码',
   sn_code              varchar(5) COMMENT '苏宁回写编码',
   jd_code              varchar(5) COMMENT '京东回写编码',
   order_receiving      int COMMENT '是否启用接单功能',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物流档案表';
ALTER TABLE `sys_logistics`
ADD UNIQUE INDEX (`logistics_code`) ;

-- ----------------------------
-- Table structure for sys_user_logistics
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_logistics`;
CREATE TABLE `sys_user_logistics` (
   id                   bigint not null COMMENT 'id',
   user_id              bigint COMMENT '用户id',
   logistics_id         bigint COMMENT '物流ID',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户物流权限表';

-- ----------------------------
-- Table structure for sys_category
-- ----------------------------
DROP TABLE IF EXISTS `sys_category`;
CREATE TABLE `sys_category` (
`id` bigint(20) NOT NULL,
`first_category_name` varchar(20) DEFAULT NULL COMMENT '一级品类',
`secod_category_name` varchar(20) DEFAULT NULL COMMENT '二级品类',
`third_category_name` varchar(20) DEFAULT NULL COMMENT '三级品类',
`delete_flag` tinyint(1) DEFAULT NULL COMMENT '是否有效',
`creater_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
`creater_name` varchar(10) DEFAULT NULL COMMENT '创建人名称',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人ID',
`modifier_name` varchar(10) DEFAULT NULL COMMENT '修改人名称',
`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
`version` int(11) DEFAULT NULL COMMENT '版本号',
PRIMARY KEY (`id`),
KEY `AK_Key_2` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='品类表';

-- ----------------------------
-- Table structure for sys_freight_project
-- ----------------------------
DROP TABLE IF EXISTS `sys_freight_project`;
CREATE TABLE `sys_freight_project` (
   id                   bigint not null COMMENT 'id',
   project_name         varchar(20) COMMENT '运费项目名称',
   category_id          bigint COMMENT '品类ID',
   category_name        varchar(20) COMMENT '品类名称',
   validity_start       datetime COMMENT '有效期开始时间',
   validity_end         datetime COMMENT '有效期结束时间',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费项目表';

-- ----------------------------
-- Table structure for sys_freight_discount
-- ----------------------------
DROP TABLE IF EXISTS `sys_freight_discount`;
CREATE TABLE `sys_freight_discount` (
   id                   bigint not null COMMENT 'id',
   freightt_id          bigint COMMENT '项目费用ID',
   amount               decimal(10,2) COMMENT '总金额',
   discount             decimal(10,2) COMMENT '折扣',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='费用折扣表';

-- ----------------------------
-- Table structure for sys_freight_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_freight_item`;
CREATE TABLE `sys_freight_item` (
   id                   bigint not null COMMENT 'id',
   item_name            varchar(10)  COMMENT '费用项名称',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='费用项表';
ALTER TABLE `sys_freight_item`
ADD UNIQUE INDEX (`item_name`) ;

-- ----------------------------
-- Table structure for sys_freight_rule
-- ----------------------------
DROP TABLE IF EXISTS `sys_freight_rule`;
CREATE TABLE `sys_freight_rule` (
   id                   bigint not null COMMENT 'id',
   item_id              bigint COMMENT '运费项ID',
   item_name            varchar(20)  COMMENT '费用名称',
   section_one          varchar(50)  COMMENT '区间公式一',
   section_value        decimal(10,2) COMMENT '区间阀值',
   section_two          varchar(50)  COMMENT '区间公式二',
   min_value            decimal(10,2) COMMENT '最低值',
   max_value            decimal(10,2) COMMENT '最高值',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费计算规则表';


-- ----------------------------
-- Table structure for sys_freight_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_freight_details`;
CREATE TABLE `sys_freight_details` (
   id                   bigint not null COMMENT 'id',
   project_id           bigint COMMENT '项目ID',
   area_id              bigint COMMENT '地区ID',
   province             varchar(20)  COMMENT '省',
   city                 varchar(20)  COMMENT '市',
   district             varchar(20) COMMENT '区',
   volume_price         decimal(10,2) COMMENT '体积单价',
   weight_price         decimal(10,2) COMMENT '重量单价',
   continuew_price      decimal(10,2) COMMENT '续重单价',
   piece_price          decimal(10,2) COMMENT '件数单价',
   delivery_price       decimal(10,2) COMMENT '送货单价',
   upstairs_price       decimal(10,2) COMMENT '上楼费单价',
   arrival_time         decimal(10,2) COMMENT '到货时间',
   assess_time          decimal(10,2) COMMENT '考核时间',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运费价格明细表';
ALTER TABLE `sys_freight_details`
ADD UNIQUE INDEX (`project_id`, `area_id`) ;

-- ----------------------------
-- Table structure for sys_region
-- ----------------------------
DROP TABLE IF EXISTS `sys_region`;
CREATE TABLE `sys_region` (
   id                   bigint not null COMMENT 'id',
   level                int COMMENT '层级',
   hot_degreet          int COMMENT '热度',
   name                 varchar(64) comment '名称',
   pinyin               varchar(255) comment '拼音',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
   parent_id            bigint comment '上级id',
   parent_name          varchar(64) comment '上级名称',
   parent_ids           varchar(2000) comment '所有上级id',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='省市区表';
ALTER TABLE `sys_region`
   ADD UNIQUE INDEX (`name`) ;

-- ----------------------------
-- Table structure for sys_breed_logistics
-- ----------------------------
DROP TABLE IF EXISTS `sys_breed_logistics`;
CREATE TABLE `sys_breed_logistics` (
   id                   bigint not null COMMENT 'id',
   breed_id             bigint not null COMMENT '品类ID',
   logistics_id         bigint not null COMMENT '物流公司ID',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品类物流表';
ALTER TABLE `sys_breed_logistics`
ADD UNIQUE INDEX (`breed_id`, `logistics_id`) ;

-- ----------------------------
-- Table structure for sys_breed_logistics_region
-- ----------------------------
DROP TABLE IF EXISTS `sys_breed_logistics_region`;
CREATE TABLE `sys_breed_logistics_region` (
   id                   bigint not null COMMENT 'id',
   breed_logistics_id   bigint not null COMMENT '品类物流ID',
   region_id            bigint not null COMMENT '区域ID',
   province             varchar(20)  COMMENT '省',
   city                 varchar(20)  COMMENT '市',
   district             varchar(20) COMMENT '区',
   percent              decimal(10,2) COMMENT '物流百分比',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品类物流区域表';

-- ----------------------------
-- Table structure for sys_interface_record
-- ----------------------------
DROP TABLE IF EXISTS `sys_interface_record`;
CREATE TABLE `sys_interface_record` (
   id                   bigint not null COMMENT 'id',
   account_name         varchar(20)  COMMENT '帐号名称',
   app_key              varchar(20)  COMMENT 'app_key',
   app_secret           varchar(20) COMMENT 'app_secret',
   app_token            varchar(200) COMMENT 'app_token',
   interface_address    varchar(200) COMMENT '接口地址',
   interface_platform   varchar(20) COMMENT '接口平台',
   interface_type       varchar(20) COMMENT '接口类型',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口档案表';


drop table if exists sys_region_client;

/*==============================================================*/
/* Table: sys_region_client                                     */
/*==============================================================*/
create table sys_region_client
(
   id                   bigint not null COMMENT 'id',
   district_id          bigint comment '区ID',
   client_id            bigint comment '客户ID',
   client_type          int comment '客户类型(1-现货派单，2-特权派单,3-留资派单)',
   receipt_proportion   int comment '接单比例',
   delete_flag         boolean DEFAULT 0 comment '有效标识',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int(11) comment '数据版本号',
   primary key (id),
   key AK_Key_2 (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域与客编关系表';



-- ----------------------------
-- Table structure for sys_product
-- ----------------------------
DROP TABLE IF EXISTS `sys_product`;
CREATE TABLE `sys_product` (
   id                   BIGINT NOT NULL AUTO_INCREMENT COMMENT 'id',
   brand_id             BIGINT NOT NULL COMMENT '品牌id',
   brand_name           VARCHAR(10) COMMENT '品牌名称',
   commodity_code       VARCHAR(10) UNIQUE NOT NULL COMMENT '商品编码',
   item_code             VARCHAR(10) COMMENT '厂家货号',
   category_id          BIGINT NOT NULL COMMENT '品类id',
   first_category_name VARCHAR (10) COMMENT '一品类名称',
   second_category_name VARCHAR (10) COMMENT '二品类名称',
   third_category_name VARCHAR (10) COMMENT '三品类名称',

   weight               NUMERIC(10,2) COMMENT '重量',
   volume               NUMERIC(10,2) COMMENT '体积',
   area                 NUMERIC(10,2) COMMENT '面积',
   description          VARCHAR(10) COMMENT '描述',
   abbreviation         varchar(100) COMMENT '商品简称',
   pack_box             int(11) COMMENT '包装箱',
   pack_pc              int(11) COMMENT '包装件',
   size                 varchar(20) COMMENT '规格',
   commodity_type       int COMMENT '商品类型',
   is_batch             boolean COMMENT '区分批次',
   is_factory           boolean COMMENT '区分工厂',
   bid_price            numeric(10,2) COMMENT '标价',
   unit_id              BIGINT COMMENT '单位id',
   unit_name            varchar(20) COMMENT '单位名称',
   identify_id          BIGINT COMMENT '商品标识',
   identify_name        varchar(20) COMMENT '商品标识名称',
   custom               boolean COMMENT '是否定制',
   is_discontinued      boolean COMMENT '是否停产',
   safe_stock           boolean COMMENT '安全库存',
   procurement_date     int COMMENT '采购周期',
   photograph           varchar(100) COMMENT '照片',
   delivery_id          bigint COMMENT '送货方式id',
   delivery_name        varchar(20) COMMENT '送货方式名称',
   delete_flag         boolean NOT NULL DEFAULT 0 COMMENT '有效标识',
   bar_code             varchar(20) COMMENT '条形码',
   creater_id           bigint NOT NULL COMMENT '创建人id',
   creater_name         varchar(10) COMMENT '创建人名称',
   create_time          datetime DEFAULT current_timestamp COMMENT '创建时间',
   modify_time          datetime DEFAULT current_timestamp ON UPDATE current_timestamp COMMENT '修改时间',
   modifier_id       bigint COMMENT '修改人id',
   modifier_name          varchar(10) COMMENT '修改人名称',
   version              int(11)  NOT NULL DEFAULT 1 COMMENT '数据版本号',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';


/*==============================================================*/
/* Table: sys_shop_record                                       */
/*==============================================================*/
drop table if exists sys_shop_record;
create table sys_shop_record
(
   id                   bigint not null,
   shop_code            varchar(20) comment '门店编码',
   shop_name            varchar(20) comment '门店名称',
   client_id            bigint comment '所属经销商ID',
   client_name          varchar(20) comment '所属经销商名称',
   leader               varchar(20) comment '负责人',
   province             varchar(20) comment '省',
   city                 varchar(20) comment '市',
   district             varchar(20) comment '区',
   shop_address         varchar(20) comment '地址',
   shop_account         varchar(20) comment '门店帐号',
   shop_longitude       varchar(20) comment '门店经度',
   shop_latitude        varchar(20) comment '门店纬度',
   service_radius       varchar(20) comment '服务半径',
   leader_tel           varchar(20) comment '联系电话',
   delete_flag          boolean comment '有效标志',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int(11)  NOT NULL DEFAULT 1 COMMENT '数据版本号',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment='门店表';

drop table if exists sys_client_record;

/*==============================================================*/
/* Table: sys_client_record                                     */
/*==============================================================*/
create table sys_client_record
(
   id                   bigint not null comment 'id',
   client_code          varchar(20) comment '客户编码',
   client_name          varchar(20) comment '客户名称',
   client_abbreviation varchar(20) comment '客户简称',
   salesman       varchar(20) comment '业务员',
   client_type_id      bigint comment '客户类型id（字典项ID）',
   client_type        varchar(20) comment '客户类型名称（字典项名称）',
   department_id        bigint comment '所属部门id',
   department_name      varchar(20) comment '所属部门名称',
   company_id           bigint comment '公司编码id',
   company_name         varchar(20) comment '公司名称',
   company_adress       varchar(20) comment '公司地址',
   Signing_date         datetime comment '签约时间',
   invoice_issuing      varchar(20) comment '开票单位',
   channel_id           bigint comment '渠道id（字典项ID）',
   channel_name         varchar(20) comment '渠道名称（字典项名称）',
   wechat               varchar(20) comment '微信',
   alipaynum               varchar(20) comment '支付宝账号',
   integral             boolean DEFAULT 0 comment '启用积分',
   integral_conversion       double DEFAULT 0 comment '积分换算',
   integral_percent       varchar(20) comment '积分百分比',
   interest             varchar(20) comment '日利息',
   interest_starttime   datetime comment '利息开始时间',
   interest_endtime     datetime comment '利息结束时间',
   bankname             varchar(20) comment '开户银行',
   bankaccount          varchar(20) comment '银行帐号',
   thetaxno             varchar(20) comment '税务号',
   credit             boolean DEFAULT 0 comment '是否授信',
   creditlimes          varchar(20) comment '授信额度',
   credit_starttime     datetime comment '授信开始',
   credit_endtime       datetime comment '授信结束',
   credit_percent       varchar(20) comment '授信百分比',
   client_grade         boolean DEFAULT 0 comment '客户级别',
   storage_fee          boolean DEFAULT 0 comment '调仓费',
   province             varchar(20) comment '省',
   province_id         bigint comment '省ID',
   city                 varchar(20) comment '市',
   city_id         bigint comment '市ID',
   district             varchar(20) comment '区',
   district_id         bigint comment '区ID',
   client_address       varchar(50) comment '客户地址',
   client_man           varchar(20) comment '联系人',
   client_tel           varchar(20) comment '联系电话',
	 sal_undertake        boolean DEFAULT 0 comment '留资承接',
	 privilege_undertake  boolean DEFAULT 0 comment '特权承接',
	 material_undertake  boolean DEFAULT 0 comment '实物承接',
   delete_flag          boolean DEFAULT 0 comment '有效标志',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(20) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(20) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int(11)  NOT NULL DEFAULT 1 COMMENT '数据版本号',
   primary key (id),
   key AK_Key_2 (id),
   key AK_Key_3 (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户档案';



drop table if exists sys_combination;
create table sys_combination
(
   id                   bigint not null,
   combination_id       int comment '套餐ID',
   combination_name     varchar(20) comment '套餐品牌（名称）',
   combination_code     varchar(20) comment '套餐明细编码',
   combination_style_id int comment '套餐类型ID',
   combination_style    varchar(20) comment '套餐类型（品类）',
   combination_description varchar(20) comment '套餐描述',
   combination_price    decimal comment '套餐标价',
   combination_unit_id  int comment '套餐单位id',
   combination_unit     varchar(20) comment '套餐单位',
   delete_flag          boolean comment '有效标志',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int(11)  NOT NULL DEFAULT 1 COMMENT '数据版本号',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 comment='套餐表';


drop table if exists sys_merchant_code;

/*==============================================================*/
/* Table: sys_merchant_code                                     */
/*==============================================================*/
create table sys_merchant_code
(
   id                   bigint not null comment 'id',
   product_id          bigint comment '商品id',
   product_code       varchar(20) comment '商品编码',
   product_brand       varchar(20) comment '商品品牌',
   product_describe       varchar(20) comment '商品描述',
   merchant_code        varchar(20) comment '商家编码',
   conversion_logicname varchar(20) comment '换算逻辑',
   delete_flag          boolean comment '有效标志',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(20) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(20) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int(11)  NOT NULL DEFAULT 1 COMMENT '数据版本号',
   primary key (id),
   key AK_Key_2 (id),
   key AK_Key_3 (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商家编码';


drop table if exists sys_o2o_function;
/*==============================================================*/
/* Table: sys_o2o_function                                     */
/*==============================================================*/
create table sys_o2o_function
(
   id                   bigint not null comment 'id',
   client_automatic_distribute          varchar(20) comment '意向客户自动派单功能',
   material_automatic_distribute       varchar(20) comment '实物自动派单',
   privilege_automatic_distribute       varchar(20) comment '特权自动派单',
   client_distribute_charge           varchar(20) comment '意向客户派单收费标准',
   privilege_automatic_charge           varchar(20) comment '特权自动派单收费标准',
   client_not_time           varchar(20) comment '意向客户未操作提醒时长',
   privilege_not_time           varchar(20) comment '特权未操作提醒时长',
   material_not_time           varchar(20) comment '实物未操作提醒时长',
   client_revoke_time           varchar(20) comment '意向客户未接撤单时长',
   privilege_revoke_time           varchar(20) comment '特权未接撤单时长',
   material_revoke_time           varchar(20) comment '实物未接撤单时长',
   delete_flag          boolean comment '有效标志',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(20) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(20) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int(11)  NOT NULL DEFAULT 1 COMMENT '数据版本号',
   primary key (id),
   key AK_Key_2 (id),
   key AK_Key_3 (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='o2o功能设置';



-- 插入超级管理员
INSERT INTO `sys_user` (`id`, `user_code`, `user_name`, `password`, `department_id`, `department_name`, `role_id`, `role_name`, `phone`, `user_type`, `user_typename`, `user_binding`, `delete_flag`, `creater_id`, `creater_name`, `create_time`, `modifier_id`, `modifier_name`, `modify_time`, `version`) VALUES ('1', 'admin', 'admin', 'a82c261cf3f8430462d6fbc842a8981aac04b7d355190f0f1ba989fe', NULL, NULL, NULL, NULL, '13702653778', NULL, NULL, NULL, '0', '1', 'admin', '2018-06-07 10:14:08', '1', 'admin', '2018-06-07 10:14:15', '0');

drop table if exists sys_mm_supplier;

/*==============================================================*/
/* Table: sys_mm_supplier                                       */
/*==============================================================*/
create table sys_mm_supplier
(
   id                   bigint not null comment 'id',
   supplier_code        varchar(20) comment '供应商编码',
   supplier_name        varchar(20) comment '供应商名称',
   company_id           bigint comment '公司ID',
   company_name         varchar(20) comment '公司名称',
   linkman              varchar(10) comment '联系人',
   phone                varchar(11) comment '联系电话',
   deposit_bank         varchar(20) comment '开户银行',
   bank_account         varchar(30) comment '银行帐号',
   province_id          bigint comment '发货省ID',
   province             varchar(10),
   city_id              bigint comment '发货市ID',
   city                 varchar(10),
   district_id          bigint comment '发货区ID',
   district             varchar(10),
   delete_flag          boolean comment '有效标志',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int(11)  NOT NULL DEFAULT 1 COMMENT '数据版本号',
   primary key (id),
   key AK_Key_2 (id),
   key AK_Key_3 (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商档案';







