ALTER TABLE `department`
ADD COLUMN `classification_type` VARCHAR(512) NULL COMMENT '区分科室的类型' AFTER `wx_sub_qr_code_value`;
