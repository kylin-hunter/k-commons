CREATE TABLE IF NOT EXISTS `k_jdbc_monitor_tables`
(
    `name`         varchar(64) NOT NULL DEFAULT '' COMMENT 'binlog name',
    `position`     bigint(20)  NOT NULL COMMENT 'next position',
    PRIMARY KEY (`name`)
    ) comment =' the roles';