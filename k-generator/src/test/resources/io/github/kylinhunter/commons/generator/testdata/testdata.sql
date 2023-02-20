CREATE TABLE IF NOT EXISTS `test_role`
(
    `id`   bigint(20)  NOT NULL COMMENT 'primary unique id' AUTO_INCREMENT,
    `name` varchar(64) NOT NULL DEFAULT '' COMMENT 'role name',
    PRIMARY KEY (`id`)
)comment=' the roles';

CREATE TABLE IF NOT EXISTS `test_user`
(
    `id`          bigint(20)     NOT NULL COMMENT 'primary unique id' AUTO_INCREMENT,
    `name`        varchar(64)    NOT NULL DEFAULT '' COMMENT 'user name ',
    `birth`       datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'birthday',
    `age`         int            NOT NULL DEFAULT 0 COMMENT 'age',
    `height`      float(9, 2)    NOT NULL DEFAULT 0 COMMENT 'height',
    `weight`      double(19, 2)  NOT NULL DEFAULT 0 COMMENT 'weight',
    `money`       decimal(20, 2) NOT NULL DEFAULT 0 COMMENT 'money',
    `address`     varchar(512)   NOT NULL DEFAULT 0 COMMENT 'address',
    `delete_flag` int            NOT NULL DEFAULT 0 COMMENT 'is deleted',
    `sex`         tinyint(2)     NOT NULL DEFAULT 0 COMMENT '0 unkown 1 male 2 female',
    `role_id`     bigint(20)     NOT NULL DEFAULT 0 COMMENT '角色 ID',
    `extend_1`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段1',
    `extend_2`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段2',
    `extend_3`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段3',
    PRIMARY KEY (`id`),
    constraint test_user_role_fk
        foreign key (role_id) references test_role (id)
) comment='the user'