/*
 Navicat Premium Data Transfer

 Source Server         : localhost-master-3306
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 24/06/2020 09:31:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS project;
CREATE DATABASE project DEFAULT CHARSET utf8mb4;
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
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '项目描述',
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
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目原型表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
