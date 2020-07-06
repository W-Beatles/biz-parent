/*
 Navicat Premium Data Transfer

 Source Server         : localhost-slave-3307
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3307
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 07/07/2020 00:52:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS project DEFAULT CHARSET utf8mb4;
USE project;

-- ----------------------------
-- Table structure for archetype
-- ----------------------------
DROP TABLE IF EXISTS `archetype`;
CREATE TABLE `archetype`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '原型id',
  `app_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'AppID',
  `app_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '项目名称',
  `app_type` tinyint(3) NOT NULL DEFAULT 0 COMMENT '项目类型: 0Service 1SDK',
  `package_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '包名',
  `description` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '项目描述',
  `status_code` tinyint(3) NOT NULL DEFAULT 0 COMMENT '状态: 0生成中 1成功 2失败',
  `git_upload_type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '上传git: 0否 1是',
  `git_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT 'git仓库地址',
  `download_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '原型下载地址',
  `created_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '更新人',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除: 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目原型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of archetype
-- ----------------------------
INSERT INTO `archetype` VALUES (1, 'int-cloud-service-order', 'biz-spring-cloud-service-order', 0, 'order', '订单服务', 1, 0, '', '', '', '2020-06-20 22:24:41', '', '2020-07-07 00:41:42', 0);
INSERT INTO `archetype` VALUES (2, 'int-cloud-service-product', 'biz-spring-cloud-service-product', 0, 'product', '产品库服务', 1, 0, '', '', '', '2020-06-20 22:24:59', '', '2020-07-07 00:41:44', 0);
INSERT INTO `archetype` VALUES (3, 'int-cloud-service-utility', 'biz-spring-cloud-service-utility', 0, 'utility', '公共服务', 1, 0, '', '', '', '2020-06-20 22:26:02', '', '2020-07-07 00:41:45', 0);
INSERT INTO `archetype` VALUES (4, 'int-cloud-service-archetype-portal', 'biz-spring-cloud-service-archetype-portal', 0, 'archetype', '测试项目', 0, 0, '', '', '', '2020-06-21 13:23:27', '', '2020-07-07 00:47:18', 0);
INSERT INTO `archetype` VALUES (5, 'int-cloud-service-pegion', 'biz-spring-cloud-sdk-pegion', 1, 'pegion', '消息服务', 2, 0, '', '', '', '2020-06-23 13:33:00', '', '2020-07-07 00:47:27', 0);
INSERT INTO `archetype` VALUES (6, 'int-cloud-service-sequence', 'biz-spring-cloud-sdk-sequence', 1, 'sequence', '分布式id生成器', 1, 0, '', '', '', '2020-06-23 13:39:45', '', '2020-07-07 00:41:50', 0);
INSERT INTO `archetype` VALUES (7, 'int-cloud-service-search', 'biz-spring-cloud-service-search', 0, 'search', '搜索服务', 1, 0, '', '', '', '2020-06-23 14:21:29', '', '2020-07-07 00:41:51', 0);
INSERT INTO `archetype` VALUES (8, 'int-cloud-service-test', 'biz-spring-cloud-service-test', 0, 'test', '测试项目', 1, 0, '', '', '', '2020-06-23 15:52:37', '', '2020-07-07 00:41:56', 0);
INSERT INTO `archetype` VALUES (9, 'ext-gateway-arch', 'biz-spring-cloud-sdk', 1, 'gateway', '网关服务', 1, 0, '', '', '', '2020-07-07 00:43:52', '', '2020-07-07 00:47:21', 0);
INSERT INTO `archetype` VALUES (10, 'int-dubbo-service-cashier', 'biz-dubbo-service-cashier', 2, 'cashier', '收银台服务', 2, 0, '', '', '', '2020-07-07 00:48:22', '', '2020-07-07 00:51:09', 0);

SET FOREIGN_KEY_CHECKS = 1;
