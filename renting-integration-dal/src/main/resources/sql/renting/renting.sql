/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : renting

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 21/01/2019 10:30:54
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
  `status` int(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '房屋状态：0未审核 1审核通过 2已出租 3逻辑删除',
  `city_en_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '城市标记缩写 如 北京bj',
  `region_en_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '地区英文简写 如昌平区 cpq',
  `cover` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面',
  `direction` int(11) NOT NULL COMMENT '房屋朝向',
  `distance_to_subway` int(11) NOT NULL DEFAULT -1 COMMENT '距地铁距离 默认-1 附近无地铁',
  `parlour` int(11) NOT NULL DEFAULT 0 COMMENT '客厅数量',
  `district` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所在小区',
  `admin_id` bigint(20) NOT NULL COMMENT '所属管理员id',
  `bathroom` int(11) NOT NULL DEFAULT 0 COMMENT '卫生间数量',
  `street` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '街道',
  `created_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
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
INSERT INTO `house` VALUES (40, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (41, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (42, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (43, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (44, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (46, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (47, '测试1', 45, 45, 1, 45, 45, 0, 2016, 3, 'bj', 'dcq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 45, 45, '45', 2, 0, '45', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (48, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (49, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (50, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (51, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (52, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (53, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (54, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (55, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (56, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (57, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (58, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (59, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (60, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (61, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (62, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (63, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (64, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (65, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (66, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (67, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (68, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (69, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (70, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (71, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 11:21:06', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (72, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 03:40:31', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (73, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 03:41:05', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (74, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 03:43:39', NULL, '2018-12-24 11:49:49', 0);
INSERT INTO `house` VALUES (75, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 04:42:28', 'adminUpdate', '2018-12-24 04:43:27', 0);
INSERT INTO `house` VALUES (76, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 06:31:58', NULL, NULL, 0);
INSERT INTO `house` VALUES (77, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:01:01', NULL, NULL, 0);
INSERT INTO `house` VALUES (78, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:01:41', NULL, NULL, 0);
INSERT INTO `house` VALUES (79, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:04:28', NULL, NULL, 0);
INSERT INTO `house` VALUES (80, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 15:06:37', NULL, '2018-12-24 15:44:59', 0);
INSERT INTO `house` VALUES (81, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 15:08:13', NULL, '2018-12-24 15:45:00', 0);
INSERT INTO `house` VALUES (82, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 15:08:15', NULL, '2018-12-24 15:45:01', 0);
INSERT INTO `house` VALUES (83, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 15:08:16', NULL, '2018-12-24 15:45:05', 0);
INSERT INTO `house` VALUES (84, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 15:08:16', NULL, '2018-12-24 15:45:04', 0);
INSERT INTO `house` VALUES (85, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 15:08:17', NULL, '2018-12-24 15:45:06', 0);
INSERT INTO `house` VALUES (86, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:08:48', NULL, NULL, 0);
INSERT INTO `house` VALUES (87, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:08:51', NULL, NULL, 0);
INSERT INTO `house` VALUES (88, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:08:52', NULL, NULL, 0);
INSERT INTO `house` VALUES (89, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:08:52', NULL, NULL, 0);
INSERT INTO `house` VALUES (90, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:08:53', 'waynechu', '2018-12-24 07:09:41', 0);
INSERT INTO `house` VALUES (91, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:09:04', NULL, NULL, 0);
INSERT INTO `house` VALUES (92, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:09:05', NULL, NULL, 0);
INSERT INTO `house` VALUES (93, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2018-12-24 07:09:05', 'waynechu', '2018-12-24 07:13:35', 1);
INSERT INTO `house` VALUES (95, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 07:45:58', 'waynechu', '2019-01-08 06:53:02', 1);
INSERT INTO `house` VALUES (96, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 07:45:59', 'admin', '2018-12-24 07:46:32', 0);
INSERT INTO `house` VALUES (97, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 07:46:00', 'waynechu', '2019-01-08 04:39:12', 0);
INSERT INTO `house` VALUES (98, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 09:42:27', NULL, NULL, 0);
INSERT INTO `house` VALUES (99, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 09:42:35', NULL, NULL, 0);
INSERT INTO `house` VALUES (100, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-24 09:42:38', NULL, NULL, 0);
INSERT INTO `house` VALUES (101, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2018-12-28 05:29:23', NULL, NULL, 0);
INSERT INTO `house` VALUES (102, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'admin', '2018-12-28 05:33:08', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (103, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2019-01-08 02:50:48', NULL, NULL, 0);
INSERT INTO `house` VALUES (104, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:29:40', NULL, NULL, 0);
INSERT INTO `house` VALUES (105, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'admin', '2019-01-08 04:30:40', NULL, NULL, 0);
INSERT INTO `house` VALUES (106, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:38:54', NULL, NULL, 0);
INSERT INTO `house` VALUES (107, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:38:56', NULL, NULL, 0);
INSERT INTO `house` VALUES (108, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:38:57', NULL, NULL, 0);
INSERT INTO `house` VALUES (109, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:38:59', NULL, NULL, 0);
INSERT INTO `house` VALUES (110, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:39:00', NULL, NULL, 0);
INSERT INTO `house` VALUES (111, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:39:01', NULL, NULL, 0);
INSERT INTO `house` VALUES (112, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:39:03', NULL, NULL, 0);
INSERT INTO `house` VALUES (113, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-08 04:39:04', 'waynechu', '2019-01-18 08:52:31', 1);
INSERT INTO `house` VALUES (114, '两室一厅', 5200, 75, 2, 6, 6, 0, 2017, 3, 'sh', 'mhq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 500, 1, '316弄112', 2, 0, '古美西路', 'waynechu', '2019-01-09 05:12:36', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (115, '测试1', 45, 45, 1, 45, 45, 0, 2016, 3, 'bj', 'dcq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 45, 45, '45', 2, 0, '45', 'waynechu', '2019-01-09 05:13:16', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (116, '测试1', 45, 45, 1, 45, 45, 0, 2016, 3, 'bj', 'dcq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 45, 45, '45', 2, 0, '45', 'waynechu', '2019-01-09 05:18:17', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (117, '测试1', 45, 45, 1, 45, 45, 0, 2016, 3, 'bj', 'dcq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 45, 45, '45', 2, 0, '45', 'waynechu', '2019-01-09 06:37:09', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (118, 'string', 0, 0, 0, 0, 0, 0, 0, 0, 'string', 'string', 'string', 0, 0, 0, 'string', 0, 0, 'string', 'waynechu', '2019-01-11 09:38:17', NULL, NULL, 0);
INSERT INTO `house` VALUES (119, 'string', 0, 0, 0, 0, 0, 0, 0, 0, 'string', 'string', 'string', 0, 0, 0, 'string', 0, 0, 'string', 'waynechu', '2019-01-11 09:39:51', NULL, NULL, 0);
INSERT INTO `house` VALUES (120, 'string', 0, 0, 0, 0, 0, 0, 0, 0, 'string', 'string', 'string', 0, 0, 0, 'string', 0, 0, 'string', 'waynechu', '2019-01-11 09:39:52', NULL, NULL, 0);
INSERT INTO `house` VALUES (121, 'string', 0, 0, 0, 0, 0, 0, 0, 0, 'string', 'string', 'string', 0, 0, 0, 'string', 0, 0, 'string', 'waynechu', '2019-01-11 09:50:30', NULL, NULL, 0);
INSERT INTO `house` VALUES (122, 'string', 0, 0, 0, 0, 0, 0, 0, 0, 'string', 'string', 'string', 0, 0, 0, 'string', 0, 0, 'string', 'waynechu', '2019-01-11 09:50:32', NULL, NULL, 0);
INSERT INTO `house` VALUES (123, 'string', 0, 0, 0, 0, 0, 0, 0, 0, 'string', 'string', 'string', 0, 0, 0, 'string', 0, 0, 'string', 'waynechu', '2019-01-11 09:50:33', NULL, NULL, 0);
INSERT INTO `house` VALUES (124, 'string', 0, 0, 0, 0, 0, 0, 0, 0, 'string', 'string', 'string', 0, 0, 0, 'string', 0, 0, 'string', 'waynechu', '2019-01-11 09:50:35', NULL, NULL, 0);
INSERT INTO `house` VALUES (125, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-15 06:38:12', NULL, NULL, 0);
INSERT INTO `house` VALUES (126, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-15 12:30:28', NULL, NULL, 0);
INSERT INTO `house` VALUES (127, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-15 12:31:39', NULL, NULL, 0);
INSERT INTO `house` VALUES (128, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-15 12:43:44', NULL, NULL, 0);
INSERT INTO `house` VALUES (129, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-15 12:43:54', NULL, NULL, 0);
INSERT INTO `house` VALUES (130, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 07:02:09', NULL, NULL, 0);
INSERT INTO `house` VALUES (131, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 11:08:29', NULL, NULL, 0);
INSERT INTO `house` VALUES (132, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:05:17', NULL, NULL, 0);
INSERT INTO `house` VALUES (133, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:15:41', NULL, NULL, 0);
INSERT INTO `house` VALUES (134, '测试1', 45, 45, 1, 45, 45, 0, 2016, 3, 'bj', 'dcq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 45, 45, '45', 2, 0, '45', 'waynechu', '2019-01-16 12:16:12', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (135, '测试1', 45, 45, 1, 45, 45, 0, 2016, 3, 'bj', 'dcq', 'FsD_mbPatrAAbJ3D_5gRjumhOyEA', 2, 45, 45, '45', 2, 0, '45', 'waynechu', '2019-01-16 12:16:19', NULL, '2018-12-24 11:49:48', 0);
INSERT INTO `house` VALUES (136, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:49:57', NULL, NULL, 0);
INSERT INTO `house` VALUES (137, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:50:01', NULL, NULL, 0);
INSERT INTO `house` VALUES (138, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:50:02', NULL, NULL, 0);
INSERT INTO `house` VALUES (139, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:50:03', NULL, NULL, 0);
INSERT INTO `house` VALUES (140, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:50:03', NULL, NULL, 0);
INSERT INTO `house` VALUES (141, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:50:04', NULL, NULL, 0);
INSERT INTO `house` VALUES (142, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-16 12:50:05', NULL, NULL, 0);
INSERT INTO `house` VALUES (143, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:00:05', NULL, NULL, 0);
INSERT INTO `house` VALUES (144, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:00:07', NULL, NULL, 0);
INSERT INTO `house` VALUES (145, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:14:27', NULL, NULL, 0);
INSERT INTO `house` VALUES (146, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:15:37', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (147, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:36:36', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (148, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:37:01', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (149, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:37:02', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (150, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:38:55', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (151, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:42:41', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (152, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:42:44', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (153, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:43:41', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (154, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:43:43', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (155, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 06:43:52', 'waynechu', '2019-01-17 05:50:42', 0);
INSERT INTO `house` VALUES (156, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 08:16:36', NULL, NULL, 0);
INSERT INTO `house` VALUES (157, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 08:16:37', NULL, NULL, 0);
INSERT INTO `house` VALUES (158, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 08:24:11', NULL, NULL, 0);
INSERT INTO `house` VALUES (159, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 08:28:23', NULL, NULL, 0);
INSERT INTO `house` VALUES (160, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 08:28:24', NULL, NULL, 0);
INSERT INTO `house` VALUES (161, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 08:49:19', NULL, NULL, 0);
INSERT INTO `house` VALUES (162, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 08:49:22', NULL, NULL, 0);
INSERT INTO `house` VALUES (163, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 09:17:29', NULL, NULL, 0);
INSERT INTO `house` VALUES (164, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-17 09:17:31', NULL, NULL, 0);
INSERT INTO `house` VALUES (165, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 05:11:32', NULL, NULL, 0);
INSERT INTO `house` VALUES (166, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 05:47:10', 'waynechu', '2019-01-18 05:12:42', 0);
INSERT INTO `house` VALUES (167, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 05:52:14', NULL, NULL, 0);
INSERT INTO `house` VALUES (168, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 05:52:18', NULL, NULL, 0);
INSERT INTO `house` VALUES (169, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 05:53:01', 'waynechu', '2019-01-18 05:52:45', 0);
INSERT INTO `house` VALUES (170, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 06:00:51', 'waynechu', '2019-01-18 05:52:45', 0);
INSERT INTO `house` VALUES (171, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 07:48:45', 'waynechu', '2019-01-18 05:52:45', 0);
INSERT INTO `house` VALUES (172, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 08:11:11', 'waynechu', '2019-01-18 05:52:45', 0);
INSERT INTO `house` VALUES (177, '富力城 国贸CBD 时尚休闲 商务办公', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 08:22:13', 'waynechu', '2019-01-18 05:52:45', 0);
INSERT INTO `house` VALUES (178, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 08:52:22', NULL, NULL, 0);
INSERT INTO `house` VALUES (179, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 08:52:24', NULL, NULL, 0);
INSERT INTO `house` VALUES (181, 'insert', 6200, 70, 2, 10, 20, 2, 2005, 1, 'bj', 'dcq', 'FmHMPaaBfN6qBpwh_TtuyZxcUewL', 2, 10, 1, '融泽嘉园', 2, 0, '龙域西二路', 'waynechu', '2019-01-18 11:12:18', NULL, NULL, 0);

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
