

CREATE TABLE IF NOT EXISTS `k_jdbc_test_role`
(
    `id`   bigint(20)  NOT NULL COMMENT 'primary unique id' AUTO_INCREMENT,
    `name` varchar(64) NOT NULL DEFAULT '' COMMENT 'role name',
    PRIMARY KEY (`id`)
) comment =' the roles';

CREATE TABLE IF NOT EXISTS `k_jdbc_test_user`
(
    `id`                  bigint(20)     NOT NULL COMMENT 'primary unique id' AUTO_INCREMENT,
    `name`                varchar(64)    NOT NULL DEFAULT '' COMMENT 'user name ',
    `birth`               datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'birthday',
    `leave_company_time`  timestamp      NOT NULL COMMENT 'the time leave company',
    `join_company_date`   date           NOT NULL COMMENT 'what time to join the company',
    `work_time_work_time` time           NOT NULL COMMENT 'what time to work ervery moring',
    `work_hours`          int            NOT NULL DEFAULT 0 COMMENT 'how many hours to work everyday',
    `age`                 smallint       NOT NULL DEFAULT 0 COMMENT 'age',
    `height`              float(9, 2)    NOT NULL DEFAULT 0 COMMENT 'height',
    `weight`              double(19, 2)  NOT NULL DEFAULT 0 COMMENT 'weight',
    `money_income`        decimal(20, 2) NOT NULL DEFAULT 0 COMMENT 'all money income',
    `money_spend`         decimal(19, 0) NOT NULL DEFAULT 0 COMMENT 'the money spent',
    `address`             varchar(512)   NOT NULL DEFAULT 0 COMMENT 'address',
    `delete_flag`         tinyint(1)     NOT NULL DEFAULT 0 COMMENT 'is deleted',
    `sex`                 tinyint(2)     NOT NULL DEFAULT 0 COMMENT '0 unkown 1 male 2 female',
    `role_id`             bigint(20)     NOT NULL DEFAULT 0 COMMENT '角色 ID',
    `extend_1`            varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段1',
    `extend_2`            varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段2',
    `extend_3`            varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段3',
    PRIMARY KEY (`id`),
    constraint k_jdbc_test_user_fk foreign key (role_id) references k_jdbc_test_role (id)
) comment ='the user';