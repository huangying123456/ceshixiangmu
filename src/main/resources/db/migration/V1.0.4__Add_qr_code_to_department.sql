ALTER TABLE `department`
ADD COLUMN `qr_code` VARCHAR(512) NULL COMMENT '二维码URL值' AFTER `wx_sub_qr_code_value`;