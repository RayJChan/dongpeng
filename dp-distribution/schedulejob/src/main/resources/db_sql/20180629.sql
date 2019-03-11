SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_schedule_job`;
CREATE TABLE `sys_schedule_job` (
  id                   bigint not null COMMENT 'id',
  task_name               varchar(64) COMMENT '任务名',
  task_group              varchar(64) COMMENT '任务组',
  description          varchar(256) COMMENT '任务描述',
  expression           varchar(64) COMMENT '定时规则',
  status               varchar(64) COMMENT '启用状态',
  notify_user          bigint COMMENT '通知用户id',
  classname            varchar(256) COMMENT '任务类',
  delete_flag          boolean COMMENT '有效状态',
  creater_id           bigint  COMMENT '创建人ID',
  creater_name         varchar(10)  COMMENT '创建人名称',
  create_time          datetime COMMENT '创建时间',
  modifier_id          bigint COMMENT '修改人ID',
  modifier_name        varchar(10) COMMENT '修改人名称',
  modify_time          datetime COMMENT '修改时间',
  version              int(11)  COMMENT '数据版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务表';