use phone;

SET NAMES utf8;

CREATE TABLE IF NOT EXISTS phone.`phone_marker_info`
(
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `phone_number` VARCHAR(256) NOT NULL COMMENT '手机号',
    `harassment_count` BIGINT DEFAULT 0 NOT NULL COMMENT '被标记为骚扰电话的次数',
    `fraud_count` BIGINT DEFAULT 0 NOT NULL COMMENT '被标记为诈骗电话的次数',
    `advertisement_count` BIGINT DEFAULT 0 NOT NULL COMMENT '被标记为广告推销的次数',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    UNIQUE (`phone_number`),
    INDEX idx_phone_number (`phone_number`)
) COMMENT '手机号标注信息';



INSERT INTO phone.`marker_info` (`phone_number`, `harassment_count`, `fraud`, `advertisement`)
VALUES
    ('17735878881', 5, 25, 35),
    ('15697564307', 25, 10, 50),
    ('15340963494', 30, 20, 5),
    ('13206417142', 20, 30, 40),
    ('15736514295', 10, 5, 45),
    ('17032213646', 50, 40, 30),
    ('15393150004', 40, 15, 25),
    ('15259259992', 5, 50, 20),
    ('17522009147', 35, 25, 15),
    ('17506469657', 5, 25, 30),
    ('17509388900', 25, 40, 50),
    ('14738899978', 20, 50, 50);