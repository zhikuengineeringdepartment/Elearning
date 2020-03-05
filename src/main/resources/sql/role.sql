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

 Date: 21/02/2020 23:07:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `rid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色标识',
  `uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限-可访问的uri',
  PRIMARY KEY (`rid`, `uri`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('文件审核管理员', '/admin/getUncheckFiles');
INSERT INTO `role` VALUES ('文件审核管理员', '/admin/modifyFileStatus');
INSERT INTO `role` VALUES ('文件审核管理员', '/admin/preview');

SET FOREIGN_KEY_CHECKS = 1;
