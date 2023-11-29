CREATE TABLE IF NOT EXISTS `k_jdbc_monitor_tables`
(
    `name`         varchar(64) NOT NULL DEFAULT '' COMMENT 'binlog name',
    `position`     bigint(20)  NOT NULL COMMENT 'next position',
    `auto_updated`      datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '系统更新时间',
    PRIMARY KEY (`name`)
    ) comment =' the roles';