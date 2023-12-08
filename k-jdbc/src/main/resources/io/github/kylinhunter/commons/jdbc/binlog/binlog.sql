CREATE TABLE IF NOT EXISTS `k_binlog_progress`
(
    `id`               int         NOT NULL COMMENT 'id',
    `sys_auto_updated` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'sys update time',
    `name`             varchar(64) NOT NULL COMMENT 'binlog name',
    `position`         bigint(20)  NOT NULL COMMENT 'next position',
    PRIMARY KEY (`id`)
) comment ='the binlog monitor';