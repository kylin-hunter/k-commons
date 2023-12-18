CREATE TABLE IF NOT EXISTS `k_table_scan_progress`
(
    `server_id`               varchar(64) NOT NULL COMMENT 'server_id',
    `table_name`               varchar(64) NOT NULL COMMENT 'table-name',
    `sys_auto_updated` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'sys update time',
    `save_destination` varchar(64) NOT NULL DEFAULT '' COMMENT 'table-name',
    `next_scan_time`   datetime    not null DEFAULT CURRENT_TIMESTAMP comment 'next time',
    `last_scan_id`     varchar(64) not null DEFAULT '' not null comment 'last id',
    PRIMARY KEY (`server_id`,`table_name` )
);