
CREATE TABLE IF NOT EXISTS `%s`
(
    `id`               varchar(64) NOT NULL COMMENT 'table-name',
    `sys_auto_updated` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'sys update time',
    `data_id`          varchar(64) NOT NULL COMMENT 'data_id',
    `op`               tinyint     NOT NULL DEFAULT 1 COMMENT '1 update ，2 delete',
    `status`           tinyint(4)  NOT NULL DEFAULT 0 COMMENT '0 wait，1 processing，2 success，3、retrying，4、error',
    PRIMARY KEY (`id`,`data_id`)
);