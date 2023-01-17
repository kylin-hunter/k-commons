CREATE TABLE IF NOT EXISTS `test_kylin_user`
(
    `id`     bigint(20)     NOT NULL COMMENT '主键id' AUTO_INCREMENT,
    `name`   varchar(64)    NOT NULL DEFAULT '' COMMENT '姓名',
    `birth`  datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '生日',
    `age`    int            NOT NULL DEFAULT 0 COMMENT '年龄',
    `height` float(9, 3)   NOT NULL DEFAULT 0 COMMENT '身高',
    `weight` double(20, 2)  NOT NULL DEFAULT 0 COMMENT '体重',
    `money`  decimal(20, 2) NOT NULL DEFAULT 0 COMMENT '存款',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin;