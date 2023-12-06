CREATE TABLE IF NOT EXISTS `k_binlog_progress`
(
    `id`       int         NOT NULL COMMENT 'id',
    `name`     varchar(64) NOT NULL COMMENT 'binlog name',
    `position` bigint(20)  NOT NULL COMMENT 'next position',
    PRIMARY KEY (`id`)
) comment ='the binlog monitor';