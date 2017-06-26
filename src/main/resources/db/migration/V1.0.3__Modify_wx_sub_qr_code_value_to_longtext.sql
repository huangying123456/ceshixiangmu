ALTER TABLE `department`
  MODIFY COLUMN `wx_sub_qr_code_value` LONGTEXT DEFAULT NULL COMMENT '微信扫码关注二维码的原始值，带有场景值：departmentId_123, 前缀固定为：departmentId_';