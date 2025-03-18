DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE
    `sys_dict`
(
    `id`          INT(11) AUTO_INCREMENT COMMENT '字典ID',
    `code`        VARCHAR(64) UNIQUE NOT NULL COMMENT '字典编码',
    `name`        VARCHAR(64) UNIQUE NOT NULL COMMENT '字典名称',
    `comment`     VARCHAR(128) COMMENT '字典备注',
    `is_enabled`  TINYINT(1) DEFAULT 1 COMMENT '是否启用. 0: 禁用, 1: 启用',
    `create_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(32)        NOT NULL COMMENT '创建人',
    `update_time` DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '信息更新时间',
    `update_by`   VARCHAR(32) COMMENT '信息更新人',
    PRIMARY KEY `pk_dict_id` (`id`)
);

DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE
    `sys_dict_item`
(
    `id`          INT(11) AUTO_INCREMENT COMMENT '字典项ID',
    `dict_id`     INT(11)     NOT NULL COMMENT '字典ID',
    `dict_code`   VARCHAR(64) NOT NULL COMMENT '字典编码',
    `label`       VARCHAR(64) NOT NULL COMMENT '字典项标签',
    `value`       VARCHAR(64) NOT NULL COMMENT '字典项值',
    `comment`     VARCHAR(128) COMMENT '字典项备注',
    `sort`        INT(11)    DEFAULT 0 COMMENT '字典项排序',
    `parent_id`   INT(11)    DEFAULT 0 COMMENT '父级字典项ID. 0表示没有父级字典项',
    `is_enabled`  TINYINT(1) DEFAULT 1 COMMENT '是否启用. 0: 禁用, 1: 启用',
    `create_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `update_time` DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '信息更新时间',
    `update_by`   VARCHAR(32) COMMENT '信息更新人',
    PRIMARY KEY `pk_dict_item_id` (`id`)
);


DROP TABLE IF EXISTS `sys_org`;

CREATE TABLE
    `sys_org`
(
    `id`          BIGINT(20)  NOT NULL COMMENT '组织ID',
    `code`        VARCHAR(64) NOT NULL COMMENT '组织编码. 4位一级. 0001,00010001,000100010001,以此类推',
    `name`        VARCHAR(64) NOT NULL COMMENT '组织名称',
    `name_abbr`   VARCHAR(64) NOT NULL COMMENT '组织名称简称',
    `comment`     VARCHAR(128) COMMENT '组织备注',
    `parent_id`   BIGINT(20) DEFAULT 0 COMMENT '父级组织ID. 0表示没有父组织',
    `create_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `update_time` DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '信息更新时间',
    `update_by`   VARCHAR(32) COMMENT '信息更新人',
    PRIMARY KEY `pk_org_id` (`id`)
);