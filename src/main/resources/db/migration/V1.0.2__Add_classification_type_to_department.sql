ALTER TABLE `department`
ADD COLUMN `classification_type` VARCHAR(512) NULL COMMENT '区分科室的类型' AFTER `wx_sub_qr_code_value`,
MODIFY COLUMN `is_guest` BIGINT NOT NULL DEFAULT 0 COMMENT '是否为访客科室，1-是，0-不是';
