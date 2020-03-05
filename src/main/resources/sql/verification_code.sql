/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云数据库
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : cdb-nj2smsfc.bj.tencentcdb.com:10001
 Source Schema         : zhiku

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 26/02/2020 10:42:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for verification_code
-- ----------------------------
DROP TABLE IF EXISTS `verification_code`;
CREATE TABLE `verification_code`  (
  `uid` int(10) UNSIGNED NOT NULL COMMENT '用户',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码',
  `date` datetime(0) NOT NULL COMMENT '有效截止日期',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
