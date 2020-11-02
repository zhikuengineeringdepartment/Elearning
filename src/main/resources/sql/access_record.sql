/*
 Navicat Premium Data Transfer

 Source Server         : zhiku正式版
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : 211.87.227.234:3306
 Source Schema         : zhiku

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 02/11/2020 20:11:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for access_record
-- ----------------------------
DROP TABLE IF EXISTS `access_record`;
CREATE TABLE `access_record`  (
  `aid` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ip地址',
  `uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '页面标识-uri',
  `date` date DEFAULT NULL COMMENT '日期，仅精确到天',
  `number` int(11) DEFAULT NULL COMMENT '重复访问次数//合并每天的重复访问记录，如果不合并，每天百万点击一年会超亿记录',
  `stay_time` bigint(20) DEFAULT NULL COMMENT '总停留时间，单位毫秒',
  `latest_time` datetime(0) DEFAULT NULL COMMENT '最近次开始访问时间',
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
