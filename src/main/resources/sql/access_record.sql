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

 Date: 08/03/2020 15:08:19
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
  `stay_time` int(11) DEFAULT NULL COMMENT '总停留时间，单位秒',
  `latest_time` datetime(0) DEFAULT NULL COMMENT '最近次开始访问时间',
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
