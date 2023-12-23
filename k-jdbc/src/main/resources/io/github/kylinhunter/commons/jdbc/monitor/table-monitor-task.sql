
CREATE TABLE IF NOT EXISTS `%s`
(
    `db`               varchar(64) NOT NULL COMMENT 'database',
    `table_name`               varchar(64) NOT NULL COMMENT 'table-name',
    `sys_auto_updated` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'sys update time',
    `data_id`          varchar(64) NOT NULL COMMENT 'data_id',
    `op`               tinyint     NOT NULL DEFAULT 0 COMMENT '1、insert 2、update 3、delete',
    `status`           tinyint(4)  NOT NULL DEFAULT 0 COMMENT '0 wait，1 processing，2 success，3、retrying，4、error',
    PRIMARY KEY (`db`,`table_name`,`data_id`)
);