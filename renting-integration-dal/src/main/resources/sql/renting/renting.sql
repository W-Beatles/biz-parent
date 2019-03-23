/*
 Navicat Premium Data Transfer

 Source Server         : localhost-master-3306
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : renting

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 23/03/2019 23:29:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for house
-- ----------------------------
DROP TABLE IF EXISTS `house`;
CREATE TABLE `house`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '房屋ID',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `price` int(11) UNSIGNED NOT NULL COMMENT '价格',
  `area` int(11) UNSIGNED NOT NULL COMMENT '面积',
  `room` int(11) UNSIGNED NOT NULL COMMENT '卧室数量',
  `floor` int(11) UNSIGNED NOT NULL COMMENT '楼层',
  `total_floor` int(11) UNSIGNED NOT NULL COMMENT '总楼层',
  `watch_times` int(11) UNSIGNED NULL DEFAULT 0 COMMENT '被看次数',
  `build_year` int(4) NOT NULL COMMENT '建立年限',
  `status` int(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '房屋状态: 0未审核 1审核通过 2已出租 3逻辑删除',
  `city_en_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市标记简写 如: 北京 bj',
  `region_en_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区英文简写 如: 昌平区 cpq',
  `cover` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面',
  `direction` int(11) NOT NULL COMMENT '房屋朝向',
  `distance_to_subway` int(11) NOT NULL DEFAULT -1 COMMENT '距地铁距离 默认-1 附近无地铁',
  `parlour` int(11) NOT NULL DEFAULT 0 COMMENT '客厅数量',
  `district` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所在小区',
  `admin_id` bigint(20) NOT NULL COMMENT '所属管理员ID',
  `bathroom` int(11) NOT NULL DEFAULT 0 COMMENT '卫生间数量',
  `street` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '街道',
  `create_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除：0否，1是。默认0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 182 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '房屋信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of house
-- ----------------------------
INSERT INTO `house` VALUES (15, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (16, '富力城 国贸CBD 时尚休闲 商务办公', 6300, 70, 2, 10, 20, 0, 2012, 1, 'bj', 'dcq', 'FheolbKZImrLyVnEjtAEhWIwU2t4', 1, -1, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (17, '二环东直门地铁站附近、王府井、天安门、国贸、三里屯、南锣鼓巷', 3000, 35, 1, 5, 10, 2, 2013, 1, 'bj', 'dcq', 'Fh81EAa26W9gTguo24fyXN0xl5_K', 1, 200, 0, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (18, '华贸城 东向一居挑空loft 干净温馨 随时可以签约', 5700, 52, 1, 7, 20, 0, 2012, 1, 'bj', 'dcq', 'Fo4n03eOIH1t9T606FsGoYfqBGRz', 2, 1085, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (19, '望春园板楼三居室 自住精装 南北通透 采光好视野棒！', 9200, 132, 3, 6, 14, 0, 2005, 1, 'bj', 'dcq', 'FglJ8Z709c5IaPgDvKfaebNYZbJX', 2, 1108, 2, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (20, '高大上的整租两居室 业主诚意出租', 5400, 56, 2, 12, 20, 0, 2012, 1, 'bj', 'dcq', 'FqSqrNmZE6Knlm77Ts1AbU3v2PP5', 2, -1, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (21, '新康园 正规三居室 精装修 家电家具齐全', 1900, 18, 1, 13, 25, 0, 2012, 1, 'bj', 'dcq', 'FvFY4WSHSRlIopcb4rdfK6_Wr1dt', 3, 1302, 0, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (24, '湖光壹号望京华府183-387㎡阔景大宅', 50000, 288, 5, 1, 1, 0, 2015, 1, 'bj', 'dcq', 'FnuWuYFBhV6Jip1FnDnOT8gT6dpC', 5, 200, 3, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (25, '测试房源-编辑', 3000, 59, 2, 10, 20, 0, 2010, 3, 'bj', 'dcq', 'FvFY4WSHSRlIopcb4rdfK6_Wr1dt', 2, 1000, 1, '融泽嘉园', 2, 0, '龙域中街', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (30, '仁和公寓', 6000, 70, 2, 6, 6, 0, 2016, 1, 'bj', 'dcq', 'FsthcXf_aidVmYqxN9QNkP3PwoBr', 2, 500, 1, '小区', 2, 0, '街道', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (31, '古美西路', 6000, 72, 12, 6, 6, 0, 2016, 1, 'bj', 'dcq', 'Fv4zStKn-dmSN1EPQfAgPXCgN3Rw', 2, 800, 1, '小区', 2, 0, '街道', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (32, '本市直招士官工作全面启动', 8000, 80, 2, 6, 2, 0, 2018, 1, 'bj', 'dcq', 'FqBlTq8sQQOat6wrT7rNOoknASwL', 1, 800, 2, '小区', 2, 0, '街道', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (33, '本市直招士官工作全面启动', 7000, 80, 6, 2, 2, 0, 2016, 1, 'bj', 'dcq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 200, 6, '小区', 2, 0, '街道', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (34, '古美西路316弄整租', 5200, 75, 2, 6, 6, 0, 2017, 1, 'sh', 'mhq', 'FqSmBXOMAsQTBMAosl4TxZgzvtx7', 3, -1, 1, '316弄', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (35, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', 'waynechu', '2019-01-18 08:52:28', 0);
INSERT INTO `house` VALUES (36, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (37, '这里是下架的房源', 1, 1, 1, 1, 1, 0, 2017, 2, 'sh', 'mhq', 'FvyV665Yqk-MRGA4hClLxgwDusbq', 2, -1, 1, '标题', 2, 0, '标题', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (38, '已下架的房源', 2, 2, 2, 2, 2, 0, 2016, 2, 'sh', 'mhq', 'Fv4zStKn-dmSN1EPQfAgPXCgN3Rw', 5, 2, 2, '已下架的房源', 2, 0, '已下架的房源', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (39, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', 'adminUpdate', '2018-12-24 04:23:18', 0);
INSERT INTO `house` VALUES (41, '两室一厅', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '古美西路316弄', 2, 0, '古美西路', 'Wayne Chu', '2019-03-23 13:19:45', NULL, NULL, 0);
INSERT INTO `house` VALUES (42, '富力城 国贸CBD 时尚休闲 商务办公', 16200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'Wayne Chu', '2019-03-23 13:19:45', 'Wayne Chu', '2019-03-23 13:19:45', 0);

-- ----------------------------
-- Table structure for house_detail
-- ----------------------------
DROP TABLE IF EXISTS `house_detail`;
CREATE TABLE `house_detail`  (
  `id` bigint(20) NOT NULL COMMENT '序列ID',
  `house_id` bigint(20) NOT NULL COMMENT '关联房屋ID',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细描述',
  `layout_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '户型介绍',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '房屋详情表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
