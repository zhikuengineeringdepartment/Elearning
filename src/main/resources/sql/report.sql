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

 Date: 19/01/2020 20:44:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
  `rid` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '举报id',
  `pid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '帖子id',
  `reid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '回答id',
  `uid` int(11) UNSIGNED NOT NULL COMMENT '举报人id',
  `type` enum('post','first_reply','second_reply') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '举报类型',
  `reason` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '举报理由(不超过600汉字)',
  `date` datetime(0) DEFAULT NULL COMMENT '举报日期',
  `state` enum('u','n','f') CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '处理状态(\'u-未处理\',\'n-通过\',\'f-不通过\')',
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
