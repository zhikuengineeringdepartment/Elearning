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

 Date: 21/02/2020 23:08:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `uid` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `rid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色标识',
  PRIMARY KEY (`uid`, `rid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (10005, '文件审核管理员');

SET FOREIGN_KEY_CHECKS = 1;
