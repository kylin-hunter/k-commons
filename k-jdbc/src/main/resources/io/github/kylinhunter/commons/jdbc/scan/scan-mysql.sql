CREATE TABLE IF NOT EXISTS `k_table_scan_progress`
(
    `id`               varchar(64) NOT NULL COMMENT 'table-name',
    `sys_auto_updated` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'sys update time',
    `save_destination` varchar(64) NOT NULL DEFAULT 'k_table_scan_processor' COMMENT 'table-name',
    `next_scan_time`   datetime    not null DEFAULT CURRENT_TIMESTAMP comment 'next time',
    `last_scan_id`     varchar(64) not null DEFAULT '' not null comment 'last id',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `k_table_scan_processor`
(
    `id`               varchar(64) NOT NULL COMMENT 'table-name',
    `sys_auto_updated` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'sys update time',
    `data_id`          varchar(64) NOT NULL COMMENT 'data_id',
    `op`               tinyint     NOT NULL DEFAULT 0 COMMENT '0 update ，1 delete',
    `status`           tinyint(4)  NOT NULL DEFAULT 0 COMMENT '0 wait，1 processing，2 success，3、retrying，4、error',
    PRIMARY KEY (`id`)
);