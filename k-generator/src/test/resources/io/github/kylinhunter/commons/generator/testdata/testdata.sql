
CREATE TABLE IF NOT EXISTS `test_role`
(
    `id`   bigint(20)  NOT NULL  COMMENT '主键id' AUTO_INCREMENT,
    `name` varchar(64) NOT NULL DEFAULT '' COMMENT '姓名',
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `test_user`
(
    `id`          bigint(20)     NOT NULL  COMMENT '主键id' AUTO_INCREMENT,
    `name`        varchar(64)    NOT NULL DEFAULT '' COMMENT '姓名',
    `birth`       datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生日',
    `age`         int            NOT NULL DEFAULT 0 COMMENT '年龄',
    `height`      float(9, 2)    NOT NULL DEFAULT 0 COMMENT '身高',
    `weight`      double(19, 2)  NOT NULL DEFAULT 0 COMMENT '体重',
    `money`       decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '存款',
    `address`     varchar(512)   NOT NULL DEFAULT 0 COMMENT '家庭地址',
    `delete_flag` int            NOT NULL DEFAULT 0 COMMENT '是否删除',
    `sex`         tinyint(2)     NOT NULL DEFAULT 0 COMMENT '0未知 1 男 2女',
    `role_id`     bigint(20)     NOT NULL DEFAULT 0 COMMENT '角色ID',
    `extend_1`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段1',
    `extend_2`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段2',
    `extend_3`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段3',
    PRIMARY KEY (`id`),
    constraint test_user_role_fk
        foreign key (role_id) references test_role (id)
) ;
