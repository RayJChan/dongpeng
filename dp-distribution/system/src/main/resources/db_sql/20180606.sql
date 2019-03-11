-- 创建 数据库
CREATE DATABASE IF NOT EXISTS dp_distribution default character set utf8mb4 COLLATE utf8mb4_general_ci;
USE dp_distribution;

SET FOREIGN_KEY_CHECKS=0;

-- 建表,按照范式编写
-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
   id                   bigint not null COMMENT 'id',
   user_name            varchar(10) COMMENT '用户名称',
   password             varchar(64) COMMENT '帐号密码',
   role_id              bigint COMMENT '角色ID（字典项ID）',
   role_name            varchar(20) COMMENT '角色名称（字典项名称）',
   phone                varchar(11) COMMENT '联系方式',

   person_name          varchar(20) COMMENT '姓名',
   head                 varchar(255) COMMENT '头像',
   wx_account           varchar(40) COMMENT '微信号',
   wx_id                varchar(64) COMMENT '微信id',
   type_id              bigint COMMENT '用户类型id',
   type_name            varchar(20) COMMENT '用户类型名称',
   org_id               bigint COMMENT '组织id',
   org_name             varchar(64) COMMENT '组织名称',
   rank_id              bigint COMMENT '职级id',
   rank_name            varchar(20) COMMENT '职级名称',
   score                int COMMENT '总积分',

   examine_status_id    bigint not null comment '审核状态id',
   examine_status_name  varchar(20) not null comment '审核状态名称',

   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
ALTER TABLE `sys_user`
ADD UNIQUE INDEX (`user_name`) ;
ALTER TABLE `sys_user`
ADD UNIQUE INDEX (`wx_id`) ;
ALTER TABLE `sys_user`
ADD UNIQUE INDEX (`phone`) ;

-- ----------------------------
-- Table structure for operating_record
-- ----------------------------
DROP TABLE IF EXISTS `operating_record`;
CREATE TABLE `operating_record` (
   id                   bigint not null COMMENT 'id',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作记录明细表';


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典项表';
ALTER TABLE `sys_dictionary`
ADD UNIQUE INDEX (`detail_code`) ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='省市区表';
ALTER TABLE `sys_region`
ADD UNIQUE INDEX (`name`, `parent_id`) ;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单表';

-- ----------------------------
-- Table structure for sys_user_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_menu`;
CREATE TABLE `sys_user_menu` (
   id                   bigint not null COMMENT 'id',
   user_id              bigint COMMENT '用户id',
   user_name            varchar(20) COMMENT '用户名称',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户菜单表';

drop table if exists bl_coupon;

/*==============================================================*/
/* Table: bl_coupon                                             */
/*==============================================================*/
create table bl_coupon
(
   id                   bigint not null comment 'id',
   cpn_name             varchar(20) not null comment '优惠券名称',
   cpn_no               varchar(64) not null unique comment '优惠券编码',
   cpn_intro            varchar(200) not null comment '优惠券描述',
   cpn_num              int not null comment  '优惠券数量',
   cpn_received_num     int not null default 0 comment '优惠券已领用数量',
   org_id               bigint not null comment '组织id',
   org_name             varchar(20) not null comment '组织名称',
   type_id              bigint not null comment '优惠类型id',
   type_name            varchar(20) not null comment '优惠类型名称',
   use_condition        decimal(10,2) not null comment '使用门槛',
   face_value           varchar(200) not null comment '面值',
   score_percent        varchar(10) not null comment '积分比例',
   receive_begin_time   datetime not null comment '领取开始时间',
   receive_end_time     datetime not null comment '领取结束时间',
   use_begin_time       datetime not null comment '使用开始时间',
   use_end_time         datetime not null comment '使用结束时间',
   rank_id              bigint not null comment '可领职级id',
   rank_name            varchar(20) not null comment '职级名称',
   is_share             boolean not null comment '是否可分享',
   receive_type_id      bigint not null comment '领用类型id',
   receive_type_name    varchar(20) not null comment '领用类型名称',
   status_id            bigint not null comment '状态id',
   status_name          varchar(20) not null comment '状态名称',
   examine_status_id    bigint not null comment '审核状态id',
   examine_status_name  varchar(20) not null comment '审核状态名称',
   use_note             varchar(400) comment '使用须知',
   receive_percent      decimal(10,2) comment '领取率',
   writeoff_num         int default 0 comment '核销数量',
   writeoff_percent     decimal(10,2) default 0 comment '核销率',
   writeoff_amount      decimal(10,2) default 0 comment '核销金额',
   score                int default 0 comment '产生积分',
   pay_amount           decimal(10,2) default 0 comment '实付金额',
   source_id            bigint not null comment '来源id（字典',
   source_name          varchar(20) not null comment '来源名称',
   tag                  varchar(20)  comment '标签',
   service_area         varchar(64)  COMMENT '可用区域',
   creater_id           bigint not null comment '创建人ID',
   creater_name         varchar(10) not null default '' comment '创建人名称',
   create_time          datetime  not null default current_timestamp comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime   comment '修改时间',
   delete_flag          boolean default false COMMENT '有效状态',
   version              int default 0 comment '数据版本号',
   primary key (id),
   key AK_Key_1 (id)
);
ALTER TABLE `bl_coupon`
ADD UNIQUE INDEX (`cpn_no`) ;
alter table bl_coupon comment '优惠券表';


drop table if exists bl_person_coupon;

/*==============================================================*/
/* Table: bl_person_coupon                                      */
/*==============================================================*/
create table bl_person_coupon
(
   id                   bigint not null comment 'id',
   pc_no                varchar(64) not null comment '流水码',
   cpn_name             varchar(20) not null comment '优惠券名称',
   cpn_id               bigint not null comment '优惠券id',
   cpn_no               varchar(64) not null comment '优惠券编码',
   person_id            bigint not null comment '用户id',
   person_name          varchar(20)  comment '用户名称',
   person_phone         bigint comment '用户号码',
   from_id              bigint comment '来源用户id',
   from_name            varchar(20) comment '来源用户名称',
   status_id            bigint not null comment '状态id',
   status_name          varchar(20) not null comment '状态名称',
   org_id               bigint comment '核销组织id',
   org_name             varchar(20) comment '核销组织名称',
   writeoff_id          bigint comment '核销用户id',
   writeoff_name        varchar(20) comment '核销用户名称',
   writeoff_phone        bigint comment '核销用户号码',
   writeoff_time        datetime comment '核销时间',
   writeoff_remark      varchar(200) comment '核销备注',
   deal_amount          decimal(10,2) comment '成交金额',
   pay_amount         decimal(10,2) comment '实付金额',
   score                int comment '产生积分',
   deal_bill_url        varchar(255) comment '成交单据',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime not null default current_timestamp comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time        datetime on update current_timestamp comment '修改时间',
   delete_flag          boolean default false COMMENT '有效状态',
   version              int default 0 comment '数据版本号',
   primary key (id),
   key AK_Key_1 (id)
);

alter table bl_person_coupon comment '小程序用户优惠券表';


drop table if exists bl_person_coupon_share_link;

/*==============================================================*/
/* Table: bl_person_coupon_share_link                           */
/*==============================================================*/
create table bl_person_coupon_share_link
(
   id                   bigint not null comment 'id',
   cpn_name             varchar(20) not null comment '优惠券名称',
   cpn_id               bigint not null comment '优惠券id',
   from_id              bigint not null comment '来源用户id',
   from_name            varchar(20) comment '来源用户名称',
   to_id                bigint not null comment '目标用户id',
   to_name              varchar(20) not null comment '目标用户名称',
   from_ids             varchar(2000) not null comment '所有来源用户ids',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime default current_timestamp comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time        datetime default current_timestamp on update current_timestamp comment '修改时间',
   version              int default 0 comment '数据版本号',
   primary key (id),
   key AK_Key_1 (id)
);

alter table bl_person_coupon_share_link comment '小程序用户优惠券分享链路表';


-- ----------------------------
-- Table structure for bl_organization
-- ----------------------------
DROP TABLE IF EXISTS `bl_organization`;
CREATE TABLE `bl_organization` (
   id                   bigint not null COMMENT 'id',
   org_name             varchar(64) COMMENT '组织名称',
   org_code             varchar(20) COMMENT '组织编码',
   org_leader           varchar(20) COMMENT '组织负责人',
   approval_id          bigint COMMENT '审批负责人id',
   approval_name        varchar(20) COMMENT '审批负责人name',
   phone                varchar(11) COMMENT '联系电话',
   type_id              bigint COMMENT '组织类型id',
   type_name            varchar(20) COMMENT '组织类型名称',
   province             varchar(20) COMMENT '省',
   city                 varchar(20) COMMENT '市',
   district             varchar(20) COMMENT '区',
   address              varchar(255) COMMENT '地址',
   lat                  decimal(10,6) COMMENT '纬度',
   lng                  decimal(10,6) COMMENT '经度',
   photo                varchar(255) COMMENT '图片',
   business_hours       varchar(20) COMMENT '营业时间',
   parent_id            bigint COMMENT '上级菜单id',
   parent_name          varchar(64) COMMENT '上级菜单名称',
   parent_ids           varchar(2000) COMMENT '所有上级id',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint  COMMENT '创建人ID',
   creater_name         varchar(10)  COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          bigint COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int(11)  COMMENT '数据版本号',
   service_area         varchar(64)  COMMENT '可用区域',
   province_id          bigint COMMENT '省id',
   city_id              bigint COMMENT '市id',
   district_id          bigint COMMENT '区id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织表';
ALTER TABLE `bl_organization`
ADD UNIQUE INDEX (`org_name`, `parent_id`) ;
ALTER TABLE `bl_organization`
ADD UNIQUE INDEX (`org_code`) ;
ALTER TABLE `bl_organization`
ADD INDEX (`lat`, `lng`) ;


-- ----------------------------
-- Table structure for bl_feedback
-- ----------------------------
DROP TABLE IF EXISTS `bl_feedback`;
CREATE TABLE `bl_feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `score` decimal(10,2) DEFAULT NULL COMMENT '评分',
  `content` varchar(200) DEFAULT NULL COMMENT '内容',
  `person_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `person_account` varchar(40) DEFAULT NULL COMMENT '用户账号',
  `creater_id` bigint(20) DEFAULT NULL COMMENT '创建人ID',
  `creater_name` varchar(10) DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `modifier_name` varchar(10) DEFAULT NULL COMMENT '修改人名称',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT NULL COMMENT '数据版本号',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '有效标志',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='意见反馈表';

-- ----------------------------
-- Table structure for bl_person
-- ----------------------------
/*drop table if exists bl_person;
create table bl_person
(
   id                   bigint not null COMMENT 'id',
   person_name          varchar(20) COMMENT '用户名称',
   person_account       varchar(40) COMMENT '用户账号',
   head                 varchar(255) COMMENT '头像',
   wx_account           varchar(40) COMMENT '微信号',
   wx_id                varchar(64) COMMENT '微信id',
   phone                varchar(11) COMMENT '手机号码',
   type_id              bigint COMMENT '用户类型id',
   type_name            varchar(20) COMMENT '用户类型名称',
   org_id               bigint COMMENT '组织id',
   org_name             varchar(64) COMMENT '组织名称',
   rank_id              bigint COMMENT '职级id',
   rank_name            varchar(20) COMMENT '职级名称',
   score                int COMMENT '总积分',
   delete_flag          boolean COMMENT '有效标志',
   creater_id           int COMMENT '创建人ID',
   creater_name         varchar(10) COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          int COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int COMMENT '数据版本号',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小程序用户表';
ALTER TABLE `bl_person`
ADD UNIQUE INDEX (`person_account`) ;*/

-- ----------------------------
-- Table structure for bl_person
-- ----------------------------
/*drop table if exists bl_person;
create table bl_person
(
   id                   bigint not null COMMENT 'id',
   person_name          varchar(20) COMMENT '用户名称',
   person_account       varchar(40) COMMENT '用户账号',
   head                 varchar(255) COMMENT '头像',
   wx_account           varchar(40) COMMENT '微信号',
   wx_id                varchar(64) COMMENT '微信id',
   phone                varchar(11) COMMENT '手机号码',
   type_id              bigint COMMENT '用户类型id',
   type_name            varchar(20) COMMENT '用户类型名称',
   org_id               bigint COMMENT '组织id',
   org_name             varchar(64) COMMENT '组织名称',
   rank_id              bigint COMMENT '职级id',
   rank_name            varchar(20) COMMENT '职级名称',
   score                int COMMENT '总积分',
   delete_flag          boolean COMMENT '有效标志',
   creater_id           int COMMENT '创建人ID',
   creater_name         varchar(10) COMMENT '创建人名称',
   create_time          datetime COMMENT '创建时间',
   modifier_id          int COMMENT '修改人ID',
   modifier_name        varchar(10) COMMENT '修改人名称',
   modify_time          datetime COMMENT '修改时间',
   version              int COMMENT '数据版本号',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='小程序用户表';
ALTER TABLE `bl_person`
ADD UNIQUE INDEX (`person_account`) ;*/


/*==============================================================*/
/* Table: bl_org_area                                           */
/*==============================================================*/
drop table if exists bl_org_area;
create table bl_org_area
(
   id                   bigint not null comment 'id',
   org_id               bigint comment '组织id',
   org_name             varchar(20) comment '组织名称',
   region_id            bigint comment '区域id',
   region_name          varchar(20) comment '区域名称',
   delete_flag          boolean COMMENT '有效状态',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int comment '数据版本号',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织服务范围表';
ALTER TABLE `bl_org_area`
ADD INDEX (`org_id`) ;

/*==============================================================*/
/* Table: bl_rank                                           */
/*==============================================================*/
drop table if exists bl_rank;
create table bl_rank
(
   id                   bigint not null comment 'id',
   rank_name            varchar(20)  COMMENT  '职级名称',
   is_enable 			boolean  COMMENT  '是否启用',
   parent_id            bigint  COMMENT  '上级id',
   parent_name          varchar(20)  COMMENT  '上级名称',
   parent_ids           varchar(2000)  COMMENT  '所有上级id',
   version              int  COMMENT  '数据版本号',
   delete_flag          boolean  COMMENT  '有效状态',
   creater_id           bigint  COMMENT  '创建人ID',
   creater_name         varchar(10)  COMMENT  '创建人名称',
   create_time          datetime  COMMENT  '创建时间',
   modifier_id          bigint  COMMENT  '修改人ID',
   modifier_name        varchar(10)  COMMENT  '修改人名称',
   modify_time        datetime  COMMENT  '修改时间',
   PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT ='职级表';

/*==============================================================*/
/* Table: bl_examine_info                                           */
/*==============================================================*/
drop table if exists bl_examine_info;
create table bl_examine_info
(
   id                   bigint not null comment 'id',
   business_id          bigint comment '所属业务ID',
   business_type        varchar(20) comment '所属业务类型',
   business_type_id     bigint comment '所属业务类型ID',
   business_desc        varchar(255) comment '业务描述',
   user_id              bigint comment '审核人id',
   user_name            varchar(20) comment '审核人名称',
   next_user_id         bigint comment '下级审核人id',
   next_user_name       varchar(20) comment '下级审核人名称',
   examine_result_id    bigint comment '审核结果id',
   examine_result_name  varchar(20) comment '审核结果名称',
   examine_remark       varchar(255) comment '审核备注',
   process_end         boolean comment '是否结束',
   delete_flag          boolean comment '有效标志',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int comment '数据版本号',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT ='审核表明细';

/*==============================================================*/
/* Table: bl_person_coupon_examine                                           */
/*==============================================================*/
drop table if exists bl_person_coupon_examine;
create table bl_person_coupon_examine(
   id                   bigint not null comment 'id',
   person_id            bigint comment '申请人id',
   person_name          varchar(64) comment '申请人名称',
   apply_org_id         bigint comment '申请人所在组织id',
   apply_org_name       varchar(64) comment '申请人所在组织名称',
   phone                varchar(11) comment '申请人电话',
   use_org_id           bigint comment '使用的组织id',
   use_org_name         varchar(64) comment '使用的组织名称',
   apply_product_name          varchar(255) comment '申请的商品名称',
   discount             decimal(10,2) comment '申请的折扣',
   use_type_id          bigint comment '使用类型id',
   use_type_name        varchar(20) comment '使用类型名称',
   use_reason           varchar(255) comment '申请原因',
   apply_num            INT comment '申请数量',
   service_area         varchar(255) comment '使用范围',
   coupon_id            bigint comment '生成的优惠券id',
   coupon_name          varchar(20) comment '生成的优惠券名称',
   apply_status_name    varchar(20) comment '申请状态名称',
   apply_status_id      bigint comment '申请状态',
   examine_user_name   varchar(64) comment '审核人名称',
   examine_user_id      bigint comment '审核人ID',
   delete_flag          boolean comment '有效标志',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int comment '数据版本号',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT ='员工优惠券审核申请表';

/*==============================================================*/
/* Table: bl_user_org                                           */
/*==============================================================*/
drop table if exists bl_user_org;
create table bl_user_org
(
   id                   bigint not null comment 'id',
   user_id              bigint comment '用户ID',
   user_name            varchar(20) comment '用户名称',
   org_id               bigint comment '组织ID',
   org_name             varchar(20) comment '组织名称',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time          datetime comment '修改时间',
   version              int comment '数据版本号',
   delete_flag          boolean comment '有效标志',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户组织表';
ALTER TABLE `bl_user_org`
ADD INDEX (`user_id`) ;

/*==============================================================*/
/* Table: bl_user_org                                           */
/*==============================================================*/
drop table if exists bl_coupon_use_person;
create table bl_coupon_use_person
(
   id                   bigint not null,
   examine_coupon_id    bigint,
   user_name            varchar(20),
   phone                varchar(20),
  delete_flag         boolean comment '有效标志',
  creater_id          bigint comment '创建人ID',
  creater_name        varchar(10) comment '创建人名称',
  create_time         datetime comment '创建时间',
  modifier_id         bigint comment '修改人ID',
  modifier_name       varchar(10) comment '修改人名称',
  modify_time         datetime comment '修改时间',
  version             int comment '数据版本号',
  primary key (id)
)ENGINE = InnoDB
  COMMENT ='优惠券申请用户明细表';

/*==============================================================*/
/* Table: bl_score_detail                                           */
/*==============================================================*/
drop table if exists bl_score_detail;
create table bl_score_detail
(
   id                   bigint not null comment 'id',
   user_id              bigint comment '用户ID',
   pc_id                bigint comment '用户优惠券id',
   source_id            bigint comment '积分来源id（字典项）',
   source_name          varchar(20) comment '积分来源名称',
   org_name             varchar(20) comment '消费组织名称',
   produce_time        datetime comment '消费时间',
   score                int comment '分值（+增 -减）',
   creater_id           bigint comment '创建人ID',
   creater_name         varchar(10) comment '创建人名称',
   create_time          datetime comment '创建时间',
   modifier_id          bigint comment '修改人ID',
   modifier_name        varchar(10) comment '修改人名称',
   modify_time        datetime comment '修改时间',
   delete_flag          boolean comment '有效标志',
   version              int comment '数据版本号',
   primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4  COMMENT ='积分明细表';

-- ----------------------------
-- Table structure for bl_person_examine
-- ----------------------------
DROP TABLE IF EXISTS `bl_person_examine`;
CREATE TABLE `bl_person_examine` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `person_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `org_id` bigint(20) DEFAULT NULL COMMENT '组织id',
  `org_name` varchar(64) DEFAULT NULL COMMENT '组织名称',
  `province` varchar(20) DEFAULT NULL COMMENT '省',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `district` varchar(20) DEFAULT NULL COMMENT '区',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `apply_id` bigint(20) DEFAULT NULL COMMENT '申请状态',
  `apply_name` varchar(20) DEFAULT NULL COMMENT '申请状态名称',
  `delete_flag` tinyint(1) DEFAULT NULL COMMENT '有效标志',
  `creater_id` bigint(11) DEFAULT NULL COMMENT '创建人ID',
  `creater_name` varchar(10) DEFAULT NULL COMMENT '创建人名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modifier_id` bigint(11) DEFAULT NULL COMMENT '修改人ID',
  `modifier_name` varchar(10) DEFAULT NULL COMMENT '修改人名称',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) DEFAULT NULL COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工申请审核表';

