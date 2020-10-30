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

 Date: 08/03/2020 15:08:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for web_page
-- ----------------------------
DROP TABLE IF EXISTS `web_page`;
CREATE TABLE `web_page`  (
  `uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '网页页面标识-uri',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '页面名称',
  PRIMARY KEY (`uri`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
