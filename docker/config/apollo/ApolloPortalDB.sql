/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3316
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3316
 Source Schema         : ApolloPortalDB

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 24/08/2020 20:49:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

# Create Database
# ------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS ApolloPortalDB DEFAULT CHARACTER SET = utf8mb4;

Use ApolloPortalDB;

-- ----------------------------
-- Table structure for App
-- ----------------------------
DROP TABLE IF EXISTS `App`;
CREATE TABLE `App`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `AppId` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `Name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '应用名',
  `OrgId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '部门Id',
  `OrgName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '部门名字',
  `OwnerName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT 'ownerName',
  `OwnerEmail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT 'ownerEmail',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `AppId`(`AppId`(191)) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_Name`(`Name`(191)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of App
-- ----------------------------
INSERT INTO `App` VALUES (1, 'SampleApp', 'Sample App', 'TEST1', '样例部门1', 'apollo', 'apollo@acme.com', b'1', 'default', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `App` VALUES (2, 'biz-spring-cloud-eureka', 'biz-spring-cloud-eureka', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `App` VALUES (3, 'biz-spring-cloud-gateway', 'biz-spring-cloud-gateway', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `App` VALUES (4, 'service-utility', 'service-utility', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `App` VALUES (5, 'service-order', 'service-order', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `App` VALUES (6, 'biz-spring-cloud-public', 'biz-spring-cloud-public', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `App` VALUES (7, 'biz-boot-starter-logger', 'biz-boot-starter-logger', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `App` VALUES (8, 'biz-spring-boot-admin', 'biz-spring-boot-admin', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `App` VALUES (9, 'service-product', 'service-product', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `App` VALUES (10, 'biz-dynamic-datasource', 'biz-dynamic-datasource', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `App` VALUES (11, 'dynamic-datasource-test', 'dynamic-datasource-test', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `App` VALUES (12, 'dynamic-datasource-all', 'dynamic-datasource-all', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `App` VALUES (13, 'biz-dynamic-datasource-all', 'biz-dynamic-datasource-all', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `App` VALUES (14, 'service-order', 'service-order', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-28 15:53:15', 'apollo', '2020-05-28 15:53:15');
INSERT INTO `App` VALUES (15, 'service-utility', 'service-utility', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `App` VALUES (16, 'biz-spring-cloud-oauth-server', 'biz-spring-cloud-oauth-server', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `App` VALUES (17, 'biz-archetype-portal', 'biz-archetype-portal', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');

-- ----------------------------
-- Table structure for AppNamespace
-- ----------------------------
DROP TABLE IF EXISTS `AppNamespace`;
CREATE TABLE `AppNamespace`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `Name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'namespace名字，注意，需要全局唯一',
  `AppId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'app id',
  `Format` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'properties' COMMENT 'namespace的format类型',
  `IsPublic` bit(1) NOT NULL DEFAULT b'0' COMMENT 'namespace是否为公共',
  `Comment` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '注释',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_AppId`(`AppId`) USING BTREE,
  INDEX `Name_AppId`(`Name`, `AppId`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用namespace定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of AppNamespace
-- ----------------------------
INSERT INTO `AppNamespace` VALUES (1, 'application', 'SampleApp', 'properties', b'0', 'default app namespace', b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `AppNamespace` VALUES (2, 'application', 'biz-spring-cloud-eureka', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `AppNamespace` VALUES (3, 'application', 'biz-spring-cloud-gateway', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `AppNamespace` VALUES (4, 'application', 'service-utility', 'properties', b'0', 'default app namespace', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `AppNamespace` VALUES (5, 'application', 'service-order', 'properties', b'0', 'default app namespace', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `AppNamespace` VALUES (6, 'application', 'biz-spring-cloud-public', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `AppNamespace` VALUES (7, 'Archer.biz-spring-cloud-public', 'biz-spring-cloud-public', 'properties', b'1', '', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `AppNamespace` VALUES (8, 'application', 'biz-boot-starter-logger', 'properties', b'0', 'default app namespace', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `AppNamespace` VALUES (9, 'Archer.biz-boot-starter-logger', 'biz-boot-starter-logger', 'properties', b'1', 'biz-boot-starter-logger配置', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `AppNamespace` VALUES (10, 'application', 'biz-spring-boot-admin', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `AppNamespace` VALUES (11, 'application', 'service-product', 'properties', b'0', 'default app namespace', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `AppNamespace` VALUES (12, 'application', 'biz-dynamic-datasource', 'properties', b'0', 'default app namespace', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `AppNamespace` VALUES (13, 'Archer.biz-dynamic-datasource', 'biz-dynamic-datasource', 'properties', b'1', '动态数据源通用配置', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `AppNamespace` VALUES (14, 'application', 'dynamic-datasource-test', 'properties', b'0', 'default app namespace', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `AppNamespace` VALUES (15, 'Archer.datasource-order', 'biz-dynamic-datasource', 'properties', b'1', '', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `AppNamespace` VALUES (16, 'application', 'dynamic-datasource-all', 'properties', b'0', 'default app namespace', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `AppNamespace` VALUES (17, 'application', 'biz-dynamic-datasource-all', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `AppNamespace` VALUES (18, 'Archer.datasource-order', 'biz-dynamic-datasource-all', 'properties', b'1', '', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `AppNamespace` VALUES (19, 'Archer.datasource-product', 'biz-dynamic-datasource-all', 'properties', b'1', '', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `AppNamespace` VALUES (20, 'application', 'service-order', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `AppNamespace` VALUES (21, 'application', 'service-utility', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `AppNamespace` VALUES (22, 'Archer.datasource-common', 'biz-dynamic-datasource-all', 'properties', b'1', 'common库', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `AppNamespace` VALUES (23, 'application', 'biz-spring-cloud-oauth-server', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `AppNamespace` VALUES (24, 'Archer.datasource-project', 'biz-dynamic-datasource-all', 'properties', b'1', '项目库', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `AppNamespace` VALUES (25, 'application', 'biz-archetype-portal', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');

-- ----------------------------
-- Table structure for Authorities
-- ----------------------------
DROP TABLE IF EXISTS `Authorities`;
CREATE TABLE `Authorities`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `Authority` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Authorities
-- ----------------------------
INSERT INTO `Authorities` VALUES (1, 'apollo', 'ROLE_user');
INSERT INTO `Authorities` VALUES (2, 'waynechu', 'ROLE_user');

-- ----------------------------
-- Table structure for Consumer
-- ----------------------------
DROP TABLE IF EXISTS `Consumer`;
CREATE TABLE `Consumer`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `AppId` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `Name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '应用名',
  `OrgId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '部门Id',
  `OrgName` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '部门名字',
  `OwnerName` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT 'ownerName',
  `OwnerEmail` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT 'ownerEmail',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `AppId`(`AppId`(191)) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '开放API消费者' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ConsumerAudit
-- ----------------------------
DROP TABLE IF EXISTS `ConsumerAudit`;
CREATE TABLE `ConsumerAudit`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `ConsumerId` int(11) UNSIGNED NULL DEFAULT NULL COMMENT 'Consumer Id',
  `Uri` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '访问的Uri',
  `Method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '访问的Method',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_ConsumerId`(`ConsumerId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'consumer审计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ConsumerRole
-- ----------------------------
DROP TABLE IF EXISTS `ConsumerRole`;
CREATE TABLE `ConsumerRole`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `ConsumerId` int(11) UNSIGNED NULL DEFAULT NULL COMMENT 'Consumer Id',
  `RoleId` int(10) UNSIGNED NULL DEFAULT NULL COMMENT 'Role Id',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_RoleId`(`RoleId`) USING BTREE,
  INDEX `IX_ConsumerId_RoleId`(`ConsumerId`, `RoleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'consumer和role的绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ConsumerToken
-- ----------------------------
DROP TABLE IF EXISTS `ConsumerToken`;
CREATE TABLE `ConsumerToken`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `ConsumerId` int(11) UNSIGNED NULL DEFAULT NULL COMMENT 'ConsumerId',
  `Token` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'token',
  `Expires` datetime(0) NOT NULL DEFAULT '2099-01-01 00:00:00' COMMENT 'token失效时间',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  UNIQUE INDEX `IX_Token`(`Token`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'consumer token表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for Favorite
-- ----------------------------
DROP TABLE IF EXISTS `Favorite`;
CREATE TABLE `Favorite`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `UserId` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '收藏的用户',
  `AppId` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT 'AppID',
  `Position` int(32) NOT NULL DEFAULT 10000 COMMENT '收藏顺序',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `AppId`(`AppId`(191)) USING BTREE,
  INDEX `IX_UserId`(`UserId`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for Permission
-- ----------------------------
DROP TABLE IF EXISTS `Permission`;
CREATE TABLE `Permission`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `PermissionType` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限类型',
  `TargetId` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限对象类型',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_TargetId_PermissionType`(`TargetId`(191), `PermissionType`) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 175 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'permission表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Permission
-- ----------------------------
INSERT INTO `Permission` VALUES (1, 'CreateCluster', 'SampleApp', b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `Permission` VALUES (2, 'CreateNamespace', 'SampleApp', b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `Permission` VALUES (3, 'AssignRole', 'SampleApp', b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `Permission` VALUES (4, 'ModifyNamespace', 'SampleApp+application', b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `Permission` VALUES (5, 'ReleaseNamespace', 'SampleApp+application', b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `Permission` VALUES (6, 'CreateApplication', 'SystemRole', b'0', 'apollo', '2020-05-15 11:02:58', 'apollo', '2020-05-15 11:02:58');
INSERT INTO `Permission` VALUES (7, 'CreateCluster', 'biz-spring-cloud-eureka', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Permission` VALUES (8, 'AssignRole', 'biz-spring-cloud-eureka', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Permission` VALUES (9, 'CreateNamespace', 'biz-spring-cloud-eureka', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Permission` VALUES (10, 'ManageAppMaster', 'biz-spring-cloud-eureka', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Permission` VALUES (11, 'ModifyNamespace', 'biz-spring-cloud-eureka+application', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Permission` VALUES (12, 'ReleaseNamespace', 'biz-spring-cloud-eureka+application', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Permission` VALUES (13, 'ModifyNamespace', 'biz-spring-cloud-eureka+application+DEV', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Permission` VALUES (14, 'ReleaseNamespace', 'biz-spring-cloud-eureka+application+DEV', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Permission` VALUES (15, 'CreateCluster', 'biz-spring-cloud-gateway', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Permission` VALUES (16, 'CreateNamespace', 'biz-spring-cloud-gateway', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Permission` VALUES (17, 'AssignRole', 'biz-spring-cloud-gateway', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Permission` VALUES (18, 'ManageAppMaster', 'biz-spring-cloud-gateway', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Permission` VALUES (19, 'ModifyNamespace', 'biz-spring-cloud-gateway+application', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Permission` VALUES (20, 'ReleaseNamespace', 'biz-spring-cloud-gateway+application', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Permission` VALUES (21, 'ModifyNamespace', 'biz-spring-cloud-gateway+application+DEV', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Permission` VALUES (22, 'ReleaseNamespace', 'biz-spring-cloud-gateway+application+DEV', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Permission` VALUES (23, 'CreateNamespace', 'service-utility', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Permission` VALUES (24, 'AssignRole', 'service-utility', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Permission` VALUES (25, 'CreateCluster', 'service-utility', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Permission` VALUES (26, 'ManageAppMaster', 'service-utility', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Permission` VALUES (27, 'ModifyNamespace', 'service-utility+application', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Permission` VALUES (28, 'ReleaseNamespace', 'service-utility+application', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Permission` VALUES (29, 'ModifyNamespace', 'service-utility+application+DEV', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Permission` VALUES (30, 'ReleaseNamespace', 'service-utility+application+DEV', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Permission` VALUES (31, 'CreateNamespace', 'service-order', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Permission` VALUES (32, 'CreateCluster', 'service-order', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Permission` VALUES (33, 'AssignRole', 'service-order', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Permission` VALUES (34, 'ManageAppMaster', 'service-order', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Permission` VALUES (35, 'ModifyNamespace', 'service-order+application', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Permission` VALUES (36, 'ReleaseNamespace', 'service-order+application', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Permission` VALUES (37, 'ModifyNamespace', 'service-order+application+DEV', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Permission` VALUES (38, 'ReleaseNamespace', 'service-order+application+DEV', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Permission` VALUES (39, 'CreateCluster', 'biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Permission` VALUES (40, 'AssignRole', 'biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Permission` VALUES (41, 'CreateNamespace', 'biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Permission` VALUES (42, 'ManageAppMaster', 'biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Permission` VALUES (43, 'ModifyNamespace', 'biz-spring-cloud-public+application', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Permission` VALUES (44, 'ReleaseNamespace', 'biz-spring-cloud-public+application', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Permission` VALUES (45, 'ModifyNamespace', 'biz-spring-cloud-public+application+DEV', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Permission` VALUES (46, 'ReleaseNamespace', 'biz-spring-cloud-public+application+DEV', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Permission` VALUES (47, 'ModifyNamespace', 'biz-spring-cloud-public+Archer.biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `Permission` VALUES (48, 'ReleaseNamespace', 'biz-spring-cloud-public+Archer.biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `Permission` VALUES (49, 'ModifyNamespace', 'biz-spring-cloud-public+Archer.biz-spring-cloud-public+DEV', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `Permission` VALUES (50, 'ReleaseNamespace', 'biz-spring-cloud-public+Archer.biz-spring-cloud-public+DEV', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `Permission` VALUES (51, 'CreateNamespace', 'biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (52, 'CreateCluster', 'biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (53, 'AssignRole', 'biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (54, 'ManageAppMaster', 'biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (55, 'ModifyNamespace', 'biz-boot-starter-logger+application', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (56, 'ReleaseNamespace', 'biz-boot-starter-logger+application', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (57, 'ModifyNamespace', 'biz-boot-starter-logger+application+DEV', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (58, 'ReleaseNamespace', 'biz-boot-starter-logger+application+DEV', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (59, 'ModifyNamespace', 'biz-boot-starter-logger+Archer.biz-spring-cloud-public', b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (60, 'ReleaseNamespace', 'biz-boot-starter-logger+Archer.biz-spring-cloud-public', b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (61, 'ModifyNamespace', 'biz-boot-starter-logger+Archer.biz-spring-cloud-public+DEV', b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (62, 'ReleaseNamespace', 'biz-boot-starter-logger+Archer.biz-spring-cloud-public+DEV', b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (63, 'ModifyNamespace', 'biz-boot-starter-logger+Archer.biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (64, 'ReleaseNamespace', 'biz-boot-starter-logger+Archer.biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (65, 'ModifyNamespace', 'biz-boot-starter-logger+Archer.biz-boot-starter-logger+DEV', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (66, 'ReleaseNamespace', 'biz-boot-starter-logger+Archer.biz-boot-starter-logger+DEV', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Permission` VALUES (67, 'CreateNamespace', 'biz-spring-boot-admin', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Permission` VALUES (68, 'AssignRole', 'biz-spring-boot-admin', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Permission` VALUES (69, 'CreateCluster', 'biz-spring-boot-admin', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Permission` VALUES (70, 'ManageAppMaster', 'biz-spring-boot-admin', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Permission` VALUES (71, 'ModifyNamespace', 'biz-spring-boot-admin+application', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Permission` VALUES (72, 'ReleaseNamespace', 'biz-spring-boot-admin+application', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Permission` VALUES (73, 'ModifyNamespace', 'biz-spring-boot-admin+application+DEV', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Permission` VALUES (74, 'ReleaseNamespace', 'biz-spring-boot-admin+application+DEV', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Permission` VALUES (75, 'CreateCluster', 'service-product', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Permission` VALUES (76, 'AssignRole', 'service-product', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Permission` VALUES (77, 'CreateNamespace', 'service-product', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Permission` VALUES (78, 'ManageAppMaster', 'service-product', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Permission` VALUES (79, 'ModifyNamespace', 'service-product+application', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Permission` VALUES (80, 'ReleaseNamespace', 'service-product+application', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Permission` VALUES (81, 'ModifyNamespace', 'service-product+application+DEV', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Permission` VALUES (82, 'ReleaseNamespace', 'service-product+application+DEV', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Permission` VALUES (83, 'CreateCluster', 'biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (84, 'AssignRole', 'biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (85, 'CreateNamespace', 'biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (86, 'ManageAppMaster', 'biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (87, 'ModifyNamespace', 'biz-dynamic-datasource+application', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (88, 'ReleaseNamespace', 'biz-dynamic-datasource+application', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (89, 'ModifyNamespace', 'biz-dynamic-datasource+application+DEV', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (90, 'ReleaseNamespace', 'biz-dynamic-datasource+application+DEV', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (91, 'ModifyNamespace', 'biz-dynamic-datasource+Archer.biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `Permission` VALUES (92, 'ReleaseNamespace', 'biz-dynamic-datasource+Archer.biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `Permission` VALUES (93, 'ModifyNamespace', 'biz-dynamic-datasource+Archer.biz-dynamic-datasource+DEV', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (94, 'ReleaseNamespace', 'biz-dynamic-datasource+Archer.biz-dynamic-datasource+DEV', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (95, 'CreateCluster', 'dynamic-datasource-test', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Permission` VALUES (96, 'AssignRole', 'dynamic-datasource-test', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Permission` VALUES (97, 'CreateNamespace', 'dynamic-datasource-test', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Permission` VALUES (98, 'ManageAppMaster', 'dynamic-datasource-test', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Permission` VALUES (99, 'ModifyNamespace', 'dynamic-datasource-test+application', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Permission` VALUES (100, 'ReleaseNamespace', 'dynamic-datasource-test+application', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Permission` VALUES (101, 'ModifyNamespace', 'dynamic-datasource-test+application+DEV', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Permission` VALUES (102, 'ReleaseNamespace', 'dynamic-datasource-test+application+DEV', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Permission` VALUES (103, 'ModifyNamespace', 'biz-dynamic-datasource+Archer.datasource-order', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `Permission` VALUES (104, 'ReleaseNamespace', 'biz-dynamic-datasource+Archer.datasource-order', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `Permission` VALUES (105, 'ModifyNamespace', 'biz-dynamic-datasource+Archer.datasource-order+DEV', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (106, 'ReleaseNamespace', 'biz-dynamic-datasource+Archer.datasource-order+DEV', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Permission` VALUES (107, 'AssignRole', 'dynamic-datasource-all', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (108, 'CreateCluster', 'dynamic-datasource-all', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (109, 'CreateNamespace', 'dynamic-datasource-all', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (110, 'ManageAppMaster', 'dynamic-datasource-all', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (111, 'ModifyNamespace', 'dynamic-datasource-all+application', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (112, 'ReleaseNamespace', 'dynamic-datasource-all+application', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (113, 'ModifyNamespace', 'dynamic-datasource-all+application+DEV', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (114, 'ReleaseNamespace', 'dynamic-datasource-all+application+DEV', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (115, 'ModifyNamespace', 'dynamic-datasource-all+Archer.biz-dynamic-datasource', b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (116, 'ReleaseNamespace', 'dynamic-datasource-all+Archer.biz-dynamic-datasource', b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (117, 'ModifyNamespace', 'dynamic-datasource-all+Archer.biz-dynamic-datasource+DEV', b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (118, 'ReleaseNamespace', 'dynamic-datasource-all+Archer.biz-dynamic-datasource+DEV', b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Permission` VALUES (119, 'AssignRole', 'biz-dynamic-datasource-all', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Permission` VALUES (120, 'CreateNamespace', 'biz-dynamic-datasource-all', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Permission` VALUES (121, 'CreateCluster', 'biz-dynamic-datasource-all', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Permission` VALUES (122, 'ManageAppMaster', 'biz-dynamic-datasource-all', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Permission` VALUES (123, 'ModifyNamespace', 'biz-dynamic-datasource-all+application', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Permission` VALUES (124, 'ReleaseNamespace', 'biz-dynamic-datasource-all+application', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Permission` VALUES (125, 'ModifyNamespace', 'biz-dynamic-datasource-all+application+DEV', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Permission` VALUES (126, 'ReleaseNamespace', 'biz-dynamic-datasource-all+application+DEV', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Permission` VALUES (127, 'ModifyNamespace', 'biz-dynamic-datasource-all+Archer.datasource-order', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `Permission` VALUES (128, 'ReleaseNamespace', 'biz-dynamic-datasource-all+Archer.datasource-order', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `Permission` VALUES (129, 'ModifyNamespace', 'biz-dynamic-datasource-all+Archer.datasource-order+DEV', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `Permission` VALUES (130, 'ReleaseNamespace', 'biz-dynamic-datasource-all+Archer.datasource-order+DEV', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `Permission` VALUES (131, 'ModifyNamespace', 'biz-dynamic-datasource-all+Archer.datasource-product', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `Permission` VALUES (132, 'ReleaseNamespace', 'biz-dynamic-datasource-all+Archer.datasource-product', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `Permission` VALUES (133, 'ModifyNamespace', 'biz-dynamic-datasource-all+Archer.datasource-product+DEV', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `Permission` VALUES (134, 'ReleaseNamespace', 'biz-dynamic-datasource-all+Archer.datasource-product+DEV', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `Permission` VALUES (135, 'CreateNamespace', 'service-order', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Permission` VALUES (136, 'AssignRole', 'service-order', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Permission` VALUES (137, 'CreateCluster', 'service-order', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Permission` VALUES (138, 'ManageAppMaster', 'service-order', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Permission` VALUES (139, 'ModifyNamespace', 'service-order+application', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Permission` VALUES (140, 'ReleaseNamespace', 'service-order+application', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Permission` VALUES (141, 'ModifyNamespace', 'service-order+application+DEV', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Permission` VALUES (142, 'ReleaseNamespace', 'service-order+application+DEV', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Permission` VALUES (143, 'CreateCluster', 'service-utility', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Permission` VALUES (144, 'AssignRole', 'service-utility', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Permission` VALUES (145, 'CreateNamespace', 'service-utility', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Permission` VALUES (146, 'ManageAppMaster', 'service-utility', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Permission` VALUES (147, 'ModifyNamespace', 'service-utility+application', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Permission` VALUES (148, 'ReleaseNamespace', 'service-utility+application', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Permission` VALUES (149, 'ModifyNamespace', 'service-utility+application+DEV', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Permission` VALUES (150, 'ReleaseNamespace', 'service-utility+application+DEV', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Permission` VALUES (151, 'ModifyNamespace', 'biz-dynamic-datasource-all+Archer.datasource-common', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `Permission` VALUES (152, 'ReleaseNamespace', 'biz-dynamic-datasource-all+Archer.datasource-common', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `Permission` VALUES (153, 'ModifyNamespace', 'biz-dynamic-datasource-all+Archer.datasource-common+DEV', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `Permission` VALUES (154, 'ReleaseNamespace', 'biz-dynamic-datasource-all+Archer.datasource-common+DEV', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `Permission` VALUES (155, 'AssignRole', 'biz-spring-cloud-oauth-server', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Permission` VALUES (156, 'CreateCluster', 'biz-spring-cloud-oauth-server', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Permission` VALUES (157, 'CreateNamespace', 'biz-spring-cloud-oauth-server', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Permission` VALUES (158, 'ManageAppMaster', 'biz-spring-cloud-oauth-server', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Permission` VALUES (159, 'ModifyNamespace', 'biz-spring-cloud-oauth-server+application', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Permission` VALUES (160, 'ReleaseNamespace', 'biz-spring-cloud-oauth-server+application', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Permission` VALUES (161, 'ModifyNamespace', 'biz-spring-cloud-oauth-server+application+DEV', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Permission` VALUES (162, 'ReleaseNamespace', 'biz-spring-cloud-oauth-server+application+DEV', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Permission` VALUES (163, 'ModifyNamespace', 'biz-dynamic-datasource-all+Archer.datasource-project', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `Permission` VALUES (164, 'ReleaseNamespace', 'biz-dynamic-datasource-all+Archer.datasource-project', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `Permission` VALUES (165, 'ModifyNamespace', 'biz-dynamic-datasource-all+Archer.datasource-project+DEV', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `Permission` VALUES (166, 'ReleaseNamespace', 'biz-dynamic-datasource-all+Archer.datasource-project+DEV', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `Permission` VALUES (167, 'CreateNamespace', 'biz-archetype-portal', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Permission` VALUES (168, 'AssignRole', 'biz-archetype-portal', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Permission` VALUES (169, 'CreateCluster', 'biz-archetype-portal', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Permission` VALUES (170, 'ManageAppMaster', 'biz-archetype-portal', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Permission` VALUES (171, 'ModifyNamespace', 'biz-archetype-portal+application', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Permission` VALUES (172, 'ReleaseNamespace', 'biz-archetype-portal+application', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Permission` VALUES (173, 'ModifyNamespace', 'biz-archetype-portal+application+DEV', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Permission` VALUES (174, 'ReleaseNamespace', 'biz-archetype-portal+application+DEV', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');

-- ----------------------------
-- Table structure for Role
-- ----------------------------
DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `RoleName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Role name',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_RoleName`(`RoleName`(191)) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 141 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Role
-- ----------------------------
INSERT INTO `Role` VALUES (1, 'Master+SampleApp', b'1', 'default', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `Role` VALUES (2, 'ModifyNamespace+SampleApp+application', b'1', 'default', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `Role` VALUES (3, 'ReleaseNamespace+SampleApp+application', b'1', 'default', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `Role` VALUES (4, 'CreateApplication+SystemRole', b'0', 'apollo', '2020-05-15 11:02:58', 'apollo', '2020-05-15 11:02:58');
INSERT INTO `Role` VALUES (5, 'Master+biz-spring-cloud-eureka', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Role` VALUES (6, 'ManageAppMaster+biz-spring-cloud-eureka', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Role` VALUES (7, 'ModifyNamespace+biz-spring-cloud-eureka+application', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Role` VALUES (8, 'ReleaseNamespace+biz-spring-cloud-eureka+application', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Role` VALUES (9, 'ModifyNamespace+biz-spring-cloud-eureka+application+DEV', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Role` VALUES (10, 'ReleaseNamespace+biz-spring-cloud-eureka+application+DEV', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `Role` VALUES (11, 'Master+biz-spring-cloud-gateway', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Role` VALUES (12, 'ManageAppMaster+biz-spring-cloud-gateway', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Role` VALUES (13, 'ModifyNamespace+biz-spring-cloud-gateway+application', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Role` VALUES (14, 'ReleaseNamespace+biz-spring-cloud-gateway+application', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Role` VALUES (15, 'ModifyNamespace+biz-spring-cloud-gateway+application+DEV', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Role` VALUES (16, 'ReleaseNamespace+biz-spring-cloud-gateway+application+DEV', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `Role` VALUES (17, 'Master+service-utility', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Role` VALUES (18, 'ManageAppMaster+service-utility', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Role` VALUES (19, 'ModifyNamespace+service-utility+application', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Role` VALUES (20, 'ReleaseNamespace+service-utility+application', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Role` VALUES (21, 'ModifyNamespace+service-utility+application+DEV', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Role` VALUES (22, 'ReleaseNamespace+service-utility+application+DEV', b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `Role` VALUES (23, 'Master+service-order', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Role` VALUES (24, 'ManageAppMaster+service-order', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Role` VALUES (25, 'ModifyNamespace+service-order+application', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Role` VALUES (26, 'ReleaseNamespace+service-order+application', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Role` VALUES (27, 'ModifyNamespace+service-order+application+DEV', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Role` VALUES (28, 'ReleaseNamespace+service-order+application+DEV', b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `Role` VALUES (29, 'Master+biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Role` VALUES (30, 'ManageAppMaster+biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Role` VALUES (31, 'ModifyNamespace+biz-spring-cloud-public+application', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Role` VALUES (32, 'ReleaseNamespace+biz-spring-cloud-public+application', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Role` VALUES (33, 'ModifyNamespace+biz-spring-cloud-public+application+DEV', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Role` VALUES (34, 'ReleaseNamespace+biz-spring-cloud-public+application+DEV', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `Role` VALUES (35, 'ModifyNamespace+biz-spring-cloud-public+Archer.biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `Role` VALUES (36, 'ReleaseNamespace+biz-spring-cloud-public+Archer.biz-spring-cloud-public', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `Role` VALUES (37, 'ModifyNamespace+biz-spring-cloud-public+Archer.biz-spring-cloud-public+DEV', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `Role` VALUES (38, 'ReleaseNamespace+biz-spring-cloud-public+Archer.biz-spring-cloud-public+DEV', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `Role` VALUES (39, 'Master+biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (40, 'ManageAppMaster+biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (41, 'ModifyNamespace+biz-boot-starter-logger+application', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (42, 'ReleaseNamespace+biz-boot-starter-logger+application', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (43, 'ModifyNamespace+biz-boot-starter-logger+application+DEV', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (44, 'ReleaseNamespace+biz-boot-starter-logger+application+DEV', b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (45, 'ModifyNamespace+biz-boot-starter-logger+Archer.biz-spring-cloud-public', b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (46, 'ReleaseNamespace+biz-boot-starter-logger+Archer.biz-spring-cloud-public', b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (47, 'ModifyNamespace+biz-boot-starter-logger+Archer.biz-spring-cloud-public+DEV', b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (48, 'ReleaseNamespace+biz-boot-starter-logger+Archer.biz-spring-cloud-public+DEV', b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (49, 'ModifyNamespace+biz-boot-starter-logger+Archer.biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (50, 'ReleaseNamespace+biz-boot-starter-logger+Archer.biz-boot-starter-logger', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (51, 'ModifyNamespace+biz-boot-starter-logger+Archer.biz-boot-starter-logger+DEV', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (52, 'ReleaseNamespace+biz-boot-starter-logger+Archer.biz-boot-starter-logger+DEV', b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `Role` VALUES (53, 'Master+biz-spring-boot-admin', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Role` VALUES (54, 'ManageAppMaster+biz-spring-boot-admin', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Role` VALUES (55, 'ModifyNamespace+biz-spring-boot-admin+application', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Role` VALUES (56, 'ReleaseNamespace+biz-spring-boot-admin+application', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Role` VALUES (57, 'ModifyNamespace+biz-spring-boot-admin+application+DEV', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Role` VALUES (58, 'ReleaseNamespace+biz-spring-boot-admin+application+DEV', b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `Role` VALUES (59, 'Master+service-product', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Role` VALUES (60, 'ManageAppMaster+service-product', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Role` VALUES (61, 'ModifyNamespace+service-product+application', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Role` VALUES (62, 'ReleaseNamespace+service-product+application', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Role` VALUES (63, 'ModifyNamespace+service-product+application+DEV', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Role` VALUES (64, 'ReleaseNamespace+service-product+application+DEV', b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `Role` VALUES (65, 'Master+biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (66, 'ManageAppMaster+biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (67, 'ModifyNamespace+biz-dynamic-datasource+application', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (68, 'ReleaseNamespace+biz-dynamic-datasource+application', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (69, 'ModifyNamespace+biz-dynamic-datasource+application+DEV', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (70, 'ReleaseNamespace+biz-dynamic-datasource+application+DEV', b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (71, 'ModifyNamespace+biz-dynamic-datasource+Archer.biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `Role` VALUES (72, 'ReleaseNamespace+biz-dynamic-datasource+Archer.biz-dynamic-datasource', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `Role` VALUES (73, 'ModifyNamespace+biz-dynamic-datasource+Archer.biz-dynamic-datasource+DEV', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (74, 'ReleaseNamespace+biz-dynamic-datasource+Archer.biz-dynamic-datasource+DEV', b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (75, 'Master+dynamic-datasource-test', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Role` VALUES (76, 'ManageAppMaster+dynamic-datasource-test', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Role` VALUES (77, 'ModifyNamespace+dynamic-datasource-test+application', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Role` VALUES (78, 'ReleaseNamespace+dynamic-datasource-test+application', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Role` VALUES (79, 'ModifyNamespace+dynamic-datasource-test+application+DEV', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Role` VALUES (80, 'ReleaseNamespace+dynamic-datasource-test+application+DEV', b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `Role` VALUES (81, 'ModifyNamespace+biz-dynamic-datasource+Archer.datasource-order', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `Role` VALUES (82, 'ReleaseNamespace+biz-dynamic-datasource+Archer.datasource-order', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `Role` VALUES (83, 'ModifyNamespace+biz-dynamic-datasource+Archer.datasource-order+DEV', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (84, 'ReleaseNamespace+biz-dynamic-datasource+Archer.datasource-order+DEV', b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `Role` VALUES (85, 'Master+dynamic-datasource-all', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (86, 'ManageAppMaster+dynamic-datasource-all', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (87, 'ModifyNamespace+dynamic-datasource-all+application', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (88, 'ReleaseNamespace+dynamic-datasource-all+application', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (89, 'ModifyNamespace+dynamic-datasource-all+application+DEV', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (90, 'ReleaseNamespace+dynamic-datasource-all+application+DEV', b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (91, 'ModifyNamespace+dynamic-datasource-all+Archer.biz-dynamic-datasource', b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (92, 'ReleaseNamespace+dynamic-datasource-all+Archer.biz-dynamic-datasource', b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (93, 'ModifyNamespace+dynamic-datasource-all+Archer.biz-dynamic-datasource+DEV', b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (94, 'ReleaseNamespace+dynamic-datasource-all+Archer.biz-dynamic-datasource+DEV', b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `Role` VALUES (95, 'Master+biz-dynamic-datasource-all', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Role` VALUES (96, 'ManageAppMaster+biz-dynamic-datasource-all', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Role` VALUES (97, 'ModifyNamespace+biz-dynamic-datasource-all+application', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Role` VALUES (98, 'ReleaseNamespace+biz-dynamic-datasource-all+application', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Role` VALUES (99, 'ModifyNamespace+biz-dynamic-datasource-all+application+DEV', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Role` VALUES (100, 'ReleaseNamespace+biz-dynamic-datasource-all+application+DEV', b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `Role` VALUES (101, 'ModifyNamespace+biz-dynamic-datasource-all+Archer.datasource-order', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `Role` VALUES (102, 'ReleaseNamespace+biz-dynamic-datasource-all+Archer.datasource-order', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `Role` VALUES (103, 'ModifyNamespace+biz-dynamic-datasource-all+Archer.datasource-order+DEV', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `Role` VALUES (104, 'ReleaseNamespace+biz-dynamic-datasource-all+Archer.datasource-order+DEV', b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `Role` VALUES (105, 'ModifyNamespace+biz-dynamic-datasource-all+Archer.datasource-product', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `Role` VALUES (106, 'ReleaseNamespace+biz-dynamic-datasource-all+Archer.datasource-product', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `Role` VALUES (107, 'ModifyNamespace+biz-dynamic-datasource-all+Archer.datasource-product+DEV', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `Role` VALUES (108, 'ReleaseNamespace+biz-dynamic-datasource-all+Archer.datasource-product+DEV', b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `Role` VALUES (109, 'Master+service-order', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Role` VALUES (110, 'ManageAppMaster+service-order', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Role` VALUES (111, 'ModifyNamespace+service-order+application', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Role` VALUES (112, 'ReleaseNamespace+service-order+application', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Role` VALUES (113, 'ModifyNamespace+service-order+application+DEV', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Role` VALUES (114, 'ReleaseNamespace+service-order+application+DEV', b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `Role` VALUES (115, 'Master+service-utility', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Role` VALUES (116, 'ManageAppMaster+service-utility', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Role` VALUES (117, 'ModifyNamespace+service-utility+application', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Role` VALUES (118, 'ReleaseNamespace+service-utility+application', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Role` VALUES (119, 'ModifyNamespace+service-utility+application+DEV', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Role` VALUES (120, 'ReleaseNamespace+service-utility+application+DEV', b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `Role` VALUES (121, 'ModifyNamespace+biz-dynamic-datasource-all+Archer.datasource-common', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `Role` VALUES (122, 'ReleaseNamespace+biz-dynamic-datasource-all+Archer.datasource-common', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `Role` VALUES (123, 'ModifyNamespace+biz-dynamic-datasource-all+Archer.datasource-common+DEV', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `Role` VALUES (124, 'ReleaseNamespace+biz-dynamic-datasource-all+Archer.datasource-common+DEV', b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `Role` VALUES (125, 'Master+biz-spring-cloud-oauth-server', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Role` VALUES (126, 'ManageAppMaster+biz-spring-cloud-oauth-server', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Role` VALUES (127, 'ModifyNamespace+biz-spring-cloud-oauth-server+application', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Role` VALUES (128, 'ReleaseNamespace+biz-spring-cloud-oauth-server+application', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Role` VALUES (129, 'ModifyNamespace+biz-spring-cloud-oauth-server+application+DEV', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Role` VALUES (130, 'ReleaseNamespace+biz-spring-cloud-oauth-server+application+DEV', b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `Role` VALUES (131, 'ModifyNamespace+biz-dynamic-datasource-all+Archer.datasource-project', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `Role` VALUES (132, 'ReleaseNamespace+biz-dynamic-datasource-all+Archer.datasource-project', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `Role` VALUES (133, 'ModifyNamespace+biz-dynamic-datasource-all+Archer.datasource-project+DEV', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `Role` VALUES (134, 'ReleaseNamespace+biz-dynamic-datasource-all+Archer.datasource-project+DEV', b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `Role` VALUES (135, 'Master+biz-archetype-portal', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Role` VALUES (136, 'ManageAppMaster+biz-archetype-portal', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Role` VALUES (137, 'ModifyNamespace+biz-archetype-portal+application', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Role` VALUES (138, 'ReleaseNamespace+biz-archetype-portal+application', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Role` VALUES (139, 'ModifyNamespace+biz-archetype-portal+application+DEV', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `Role` VALUES (140, 'ReleaseNamespace+biz-archetype-portal+application+DEV', b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');

-- ----------------------------
-- Table structure for RolePermission
-- ----------------------------
DROP TABLE IF EXISTS `RolePermission`;
CREATE TABLE `RolePermission`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `RoleId` int(10) UNSIGNED NULL DEFAULT NULL COMMENT 'Role Id',
  `PermissionId` int(10) UNSIGNED NULL DEFAULT NULL COMMENT 'Permission Id',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_RoleId`(`RoleId`) USING BTREE,
  INDEX `IX_PermissionId`(`PermissionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 175 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和权限的绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of RolePermission
-- ----------------------------
INSERT INTO `RolePermission` VALUES (1, 1, 1, b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `RolePermission` VALUES (2, 1, 2, b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `RolePermission` VALUES (3, 1, 3, b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `RolePermission` VALUES (4, 2, 4, b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `RolePermission` VALUES (5, 3, 5, b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `RolePermission` VALUES (6, 4, 6, b'0', 'apollo', '2020-05-15 11:02:58', 'apollo', '2020-05-15 11:02:58');
INSERT INTO `RolePermission` VALUES (7, 5, 7, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `RolePermission` VALUES (8, 5, 8, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `RolePermission` VALUES (9, 5, 9, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `RolePermission` VALUES (10, 6, 10, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `RolePermission` VALUES (11, 7, 11, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `RolePermission` VALUES (12, 8, 12, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `RolePermission` VALUES (13, 9, 13, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `RolePermission` VALUES (14, 10, 14, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `RolePermission` VALUES (15, 11, 16, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `RolePermission` VALUES (16, 11, 17, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `RolePermission` VALUES (17, 11, 15, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `RolePermission` VALUES (18, 12, 18, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `RolePermission` VALUES (19, 13, 19, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `RolePermission` VALUES (20, 14, 20, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `RolePermission` VALUES (21, 15, 21, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `RolePermission` VALUES (22, 16, 22, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `RolePermission` VALUES (23, 17, 23, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `RolePermission` VALUES (24, 17, 24, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `RolePermission` VALUES (25, 17, 25, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `RolePermission` VALUES (26, 18, 26, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `RolePermission` VALUES (27, 19, 27, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `RolePermission` VALUES (28, 20, 28, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `RolePermission` VALUES (29, 21, 29, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `RolePermission` VALUES (30, 22, 30, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `RolePermission` VALUES (31, 23, 32, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `RolePermission` VALUES (32, 23, 33, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `RolePermission` VALUES (33, 23, 31, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `RolePermission` VALUES (34, 24, 34, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `RolePermission` VALUES (35, 25, 35, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `RolePermission` VALUES (36, 26, 36, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `RolePermission` VALUES (37, 27, 37, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `RolePermission` VALUES (38, 28, 38, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `RolePermission` VALUES (39, 29, 39, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `RolePermission` VALUES (40, 29, 40, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `RolePermission` VALUES (41, 29, 41, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `RolePermission` VALUES (42, 30, 42, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `RolePermission` VALUES (43, 31, 43, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `RolePermission` VALUES (44, 32, 44, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `RolePermission` VALUES (45, 33, 45, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `RolePermission` VALUES (46, 34, 46, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `RolePermission` VALUES (47, 35, 47, b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `RolePermission` VALUES (48, 36, 48, b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `RolePermission` VALUES (49, 37, 49, b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `RolePermission` VALUES (50, 38, 50, b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `RolePermission` VALUES (51, 39, 51, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (52, 39, 52, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (53, 39, 53, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (54, 40, 54, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (55, 41, 55, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (56, 42, 56, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (57, 43, 57, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (58, 44, 58, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (59, 45, 59, b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (60, 46, 60, b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (61, 47, 61, b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (62, 48, 62, b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (63, 49, 63, b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (64, 50, 64, b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (65, 51, 65, b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (66, 52, 66, b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `RolePermission` VALUES (67, 53, 67, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `RolePermission` VALUES (68, 53, 68, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `RolePermission` VALUES (69, 53, 69, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `RolePermission` VALUES (70, 54, 70, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `RolePermission` VALUES (71, 55, 71, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `RolePermission` VALUES (72, 56, 72, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `RolePermission` VALUES (73, 57, 73, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `RolePermission` VALUES (74, 58, 74, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `RolePermission` VALUES (75, 59, 75, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `RolePermission` VALUES (76, 59, 76, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `RolePermission` VALUES (77, 59, 77, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `RolePermission` VALUES (78, 60, 78, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `RolePermission` VALUES (79, 61, 79, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `RolePermission` VALUES (80, 62, 80, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `RolePermission` VALUES (81, 63, 81, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `RolePermission` VALUES (82, 64, 82, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `RolePermission` VALUES (83, 65, 83, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (84, 65, 84, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (85, 65, 85, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (86, 66, 86, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (87, 67, 87, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (88, 68, 88, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (89, 69, 89, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (90, 70, 90, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (91, 71, 91, b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `RolePermission` VALUES (92, 72, 92, b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `RolePermission` VALUES (93, 73, 93, b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (94, 74, 94, b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (95, 75, 96, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `RolePermission` VALUES (96, 75, 97, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `RolePermission` VALUES (97, 75, 95, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `RolePermission` VALUES (98, 76, 98, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `RolePermission` VALUES (99, 77, 99, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `RolePermission` VALUES (100, 78, 100, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `RolePermission` VALUES (101, 79, 101, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `RolePermission` VALUES (102, 80, 102, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `RolePermission` VALUES (103, 81, 103, b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `RolePermission` VALUES (104, 82, 104, b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `RolePermission` VALUES (105, 83, 105, b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (106, 84, 106, b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `RolePermission` VALUES (107, 85, 107, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (108, 85, 108, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (109, 85, 109, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (110, 86, 110, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (111, 87, 111, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (112, 88, 112, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (113, 89, 113, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (114, 90, 114, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (115, 91, 115, b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (116, 92, 116, b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (117, 93, 117, b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (118, 94, 118, b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `RolePermission` VALUES (119, 95, 119, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `RolePermission` VALUES (120, 95, 120, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `RolePermission` VALUES (121, 95, 121, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `RolePermission` VALUES (122, 96, 122, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `RolePermission` VALUES (123, 97, 123, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `RolePermission` VALUES (124, 98, 124, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `RolePermission` VALUES (125, 99, 125, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `RolePermission` VALUES (126, 100, 126, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `RolePermission` VALUES (127, 101, 127, b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `RolePermission` VALUES (128, 102, 128, b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `RolePermission` VALUES (129, 103, 129, b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `RolePermission` VALUES (130, 104, 130, b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `RolePermission` VALUES (131, 105, 131, b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `RolePermission` VALUES (132, 106, 132, b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `RolePermission` VALUES (133, 107, 133, b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `RolePermission` VALUES (134, 108, 134, b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `RolePermission` VALUES (135, 109, 135, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `RolePermission` VALUES (136, 109, 136, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `RolePermission` VALUES (137, 109, 137, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `RolePermission` VALUES (138, 110, 138, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `RolePermission` VALUES (139, 111, 139, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `RolePermission` VALUES (140, 112, 140, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `RolePermission` VALUES (141, 113, 141, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `RolePermission` VALUES (142, 114, 142, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `RolePermission` VALUES (143, 115, 144, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `RolePermission` VALUES (144, 115, 145, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `RolePermission` VALUES (145, 115, 143, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `RolePermission` VALUES (146, 116, 146, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `RolePermission` VALUES (147, 117, 147, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `RolePermission` VALUES (148, 118, 148, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `RolePermission` VALUES (149, 119, 149, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `RolePermission` VALUES (150, 120, 150, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `RolePermission` VALUES (151, 121, 151, b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `RolePermission` VALUES (152, 122, 152, b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `RolePermission` VALUES (153, 123, 153, b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `RolePermission` VALUES (154, 124, 154, b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `RolePermission` VALUES (155, 125, 155, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `RolePermission` VALUES (156, 125, 156, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `RolePermission` VALUES (157, 125, 157, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `RolePermission` VALUES (158, 126, 158, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `RolePermission` VALUES (159, 127, 159, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `RolePermission` VALUES (160, 128, 160, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `RolePermission` VALUES (161, 129, 161, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `RolePermission` VALUES (162, 130, 162, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `RolePermission` VALUES (163, 131, 163, b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `RolePermission` VALUES (164, 132, 164, b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `RolePermission` VALUES (165, 133, 165, b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `RolePermission` VALUES (166, 134, 166, b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `RolePermission` VALUES (167, 135, 167, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `RolePermission` VALUES (168, 135, 168, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `RolePermission` VALUES (169, 135, 169, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `RolePermission` VALUES (170, 136, 170, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `RolePermission` VALUES (171, 137, 171, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `RolePermission` VALUES (172, 138, 172, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `RolePermission` VALUES (173, 139, 173, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `RolePermission` VALUES (174, 140, 174, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');

-- ----------------------------
-- Table structure for ServerConfig
-- ----------------------------
DROP TABLE IF EXISTS `ServerConfig`;
CREATE TABLE `ServerConfig`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '配置项Key',
  `Value` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '配置项值',
  `Comment` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '注释',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_Key`(`Key`) USING BTREE,
  INDEX `DataChange_LastTime`(`DataChange_LastTime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '配置服务自身配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ServerConfig
-- ----------------------------
INSERT INTO `ServerConfig` VALUES (1, 'apollo.portal.envs', 'dev', '可支持的环境列表', b'0', 'default', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:23:57');
INSERT INTO `ServerConfig` VALUES (2, 'organizations', '[{\"orgId\":\"Archer\",\"orgName\":\"中间件组\"}]', '部门列表', b'0', 'default', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:26:16');
INSERT INTO `ServerConfig` VALUES (3, 'superAdmin', 'apollo', 'Portal超级管理员', b'0', 'default', '2020-05-15 10:59:44', '', '2020-05-15 10:59:44');
INSERT INTO `ServerConfig` VALUES (4, 'api.readTimeout', '10000', 'http接口read timeout', b'0', 'default', '2020-05-15 10:59:44', '', '2020-05-15 10:59:44');
INSERT INTO `ServerConfig` VALUES (5, 'consumer.token.salt', 'someSalt', 'consumer token salt', b'0', 'default', '2020-05-15 10:59:44', '', '2020-05-15 10:59:44');
INSERT INTO `ServerConfig` VALUES (6, 'admin.createPrivateNamespace.switch', 'true', '是否允许项目管理员创建私有namespace', b'0', 'default', '2020-05-15 10:59:44', '', '2020-05-15 10:59:44');
INSERT INTO `ServerConfig` VALUES (7, 'configView.memberOnly.envs', 'dev', '只对项目成员显示配置信息的环境列表，多个env以英文逗号分隔', b'0', 'default', '2020-05-15 10:59:44', '', '2020-05-15 10:59:44');
INSERT INTO `ServerConfig` VALUES (8, 'apollo.portal.meta.servers', '{}', '各环境Meta Service列表', b'0', 'default', '2020-05-15 10:59:44', '', '2020-05-15 10:59:44');

-- ----------------------------
-- Table structure for UserRole
-- ----------------------------
DROP TABLE IF EXISTS `UserRole`;
CREATE TABLE `UserRole`  (
  `Id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `UserId` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户身份标识',
  `RoleId` int(10) UNSIGNED NULL DEFAULT NULL COMMENT 'Role Id',
  `IsDeleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '1: deleted, 0: normal',
  `DataChange_CreatedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建人邮箱前缀',
  `DataChange_CreatedTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `DataChange_LastModifiedBy` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后修改人邮箱前缀',
  `DataChange_LastTime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后修改时间',
  PRIMARY KEY (`Id`) USING BTREE,
  INDEX `IX_DataChange_LastTime`(`DataChange_LastTime`) USING BTREE,
  INDEX `IX_RoleId`(`RoleId`) USING BTREE,
  INDEX `IX_UserId_RoleId`(`UserId`, `RoleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和role的绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of UserRole
-- ----------------------------
INSERT INTO `UserRole` VALUES (1, 'apollo', 1, b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `UserRole` VALUES (2, 'apollo', 2, b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `UserRole` VALUES (3, 'apollo', 3, b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `UserRole` VALUES (4, 'waynechu', 4, b'0', 'apollo', '2020-05-15 11:09:45', 'apollo', '2020-05-15 11:09:45');
INSERT INTO `UserRole` VALUES (5, 'waynechu', 5, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `UserRole` VALUES (6, 'apollo', 7, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `UserRole` VALUES (7, 'apollo', 8, b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `UserRole` VALUES (8, 'waynechu', 11, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `UserRole` VALUES (9, 'apollo', 13, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `UserRole` VALUES (10, 'apollo', 14, b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `UserRole` VALUES (11, 'waynechu', 17, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `UserRole` VALUES (12, 'apollo', 19, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `UserRole` VALUES (13, 'apollo', 20, b'1', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-28 14:40:38');
INSERT INTO `UserRole` VALUES (14, 'waynechu', 23, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `UserRole` VALUES (15, 'apollo', 25, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `UserRole` VALUES (16, 'apollo', 26, b'1', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-28 14:40:54');
INSERT INTO `UserRole` VALUES (17, 'waynechu', 29, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `UserRole` VALUES (18, 'apollo', 31, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `UserRole` VALUES (19, 'apollo', 32, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `UserRole` VALUES (20, 'apollo', 35, b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `UserRole` VALUES (21, 'apollo', 36, b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `UserRole` VALUES (22, 'waynechu', 35, b'0', 'apollo', '2020-05-15 14:26:08', 'apollo', '2020-05-15 14:26:08');
INSERT INTO `UserRole` VALUES (23, 'waynechu', 36, b'0', 'apollo', '2020-05-15 14:26:10', 'apollo', '2020-05-15 14:26:10');
INSERT INTO `UserRole` VALUES (24, 'waynechu', 39, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (25, 'apollo', 41, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (26, 'apollo', 42, b'1', 'apollo', '2020-05-19 14:57:02', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (27, 'apollo', 45, b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (28, 'apollo', 46, b'1', 'apollo', '2020-05-19 14:57:26', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (29, 'waynechu', 45, b'1', 'apollo', '2020-05-19 14:57:34', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (30, 'waynechu', 46, b'1', 'apollo', '2020-05-19 14:57:36', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (31, 'apollo', 49, b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (32, 'apollo', 50, b'1', 'apollo', '2020-05-19 15:00:11', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (33, 'waynechu', 49, b'1', 'apollo', '2020-05-19 15:10:29', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (34, 'waynechu', 50, b'1', 'apollo', '2020-05-19 15:10:33', 'apollo', '2020-05-19 15:17:07');
INSERT INTO `UserRole` VALUES (35, 'waynechu', 53, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `UserRole` VALUES (36, 'apollo', 55, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `UserRole` VALUES (37, 'apollo', 56, b'0', 'apollo', '2020-05-19 15:17:28', 'apollo', '2020-05-19 15:17:28');
INSERT INTO `UserRole` VALUES (38, 'waynechu', 59, b'1', 'apollo', '2020-05-19 16:06:21', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `UserRole` VALUES (39, 'apollo', 61, b'1', 'apollo', '2020-05-19 16:06:22', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `UserRole` VALUES (40, 'apollo', 62, b'1', 'apollo', '2020-05-19 16:06:22', 'apollo', '2020-05-28 14:41:11');
INSERT INTO `UserRole` VALUES (41, 'waynechu', 65, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `UserRole` VALUES (42, 'apollo', 67, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `UserRole` VALUES (43, 'apollo', 68, b'1', 'apollo', '2020-05-19 23:55:15', 'apollo', '2020-05-28 14:27:59');
INSERT INTO `UserRole` VALUES (44, 'apollo', 71, b'1', 'apollo', '2020-05-19 23:55:42', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `UserRole` VALUES (45, 'apollo', 72, b'1', 'apollo', '2020-05-19 23:55:43', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `UserRole` VALUES (46, 'waynechu', 71, b'1', 'apollo', '2020-05-19 23:55:47', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `UserRole` VALUES (47, 'waynechu', 72, b'1', 'apollo', '2020-05-19 23:55:50', 'apollo', '2020-05-28 14:27:42');
INSERT INTO `UserRole` VALUES (48, 'waynechu', 75, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `UserRole` VALUES (49, 'apollo', 77, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `UserRole` VALUES (50, 'apollo', 78, b'1', 'apollo', '2020-05-28 14:00:57', 'apollo', '2020-05-28 14:41:24');
INSERT INTO `UserRole` VALUES (51, 'apollo', 81, b'1', 'apollo', '2020-05-28 14:12:14', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `UserRole` VALUES (52, 'apollo', 82, b'1', 'apollo', '2020-05-28 14:12:15', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `UserRole` VALUES (53, 'waynechu', 81, b'1', 'apollo', '2020-05-28 14:12:19', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `UserRole` VALUES (54, 'waynechu', 82, b'1', 'apollo', '2020-05-28 14:12:20', 'apollo', '2020-05-28 14:16:38');
INSERT INTO `UserRole` VALUES (55, 'waynechu', 85, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `UserRole` VALUES (56, 'apollo', 87, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `UserRole` VALUES (57, 'apollo', 88, b'1', 'apollo', '2020-05-28 14:15:35', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `UserRole` VALUES (58, 'apollo', 91, b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `UserRole` VALUES (59, 'apollo', 92, b'1', 'apollo', '2020-05-28 14:17:05', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `UserRole` VALUES (60, 'waynechu', 91, b'1', 'apollo', '2020-05-28 14:17:11', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `UserRole` VALUES (61, 'waynechu', 92, b'1', 'apollo', '2020-05-28 14:17:12', 'apollo', '2020-05-28 14:23:20');
INSERT INTO `UserRole` VALUES (62, 'waynechu', 95, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `UserRole` VALUES (63, 'apollo', 97, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `UserRole` VALUES (64, 'apollo', 98, b'0', 'apollo', '2020-05-28 14:28:21', 'apollo', '2020-05-28 14:28:21');
INSERT INTO `UserRole` VALUES (65, 'apollo', 101, b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `UserRole` VALUES (66, 'apollo', 102, b'0', 'apollo', '2020-05-28 14:28:38', 'apollo', '2020-05-28 14:28:38');
INSERT INTO `UserRole` VALUES (67, 'waynechu', 101, b'0', 'apollo', '2020-05-28 14:28:42', 'apollo', '2020-05-28 14:28:42');
INSERT INTO `UserRole` VALUES (68, 'waynechu', 102, b'0', 'apollo', '2020-05-28 14:28:44', 'apollo', '2020-05-28 14:28:44');
INSERT INTO `UserRole` VALUES (69, 'apollo', 105, b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `UserRole` VALUES (70, 'apollo', 106, b'0', 'apollo', '2020-05-28 14:30:12', 'apollo', '2020-05-28 14:30:12');
INSERT INTO `UserRole` VALUES (71, 'waynechu', 105, b'0', 'apollo', '2020-05-28 14:30:15', 'apollo', '2020-05-28 14:30:15');
INSERT INTO `UserRole` VALUES (72, 'waynechu', 106, b'0', 'apollo', '2020-05-28 14:30:17', 'apollo', '2020-05-28 14:30:17');
INSERT INTO `UserRole` VALUES (73, 'waynechu', 109, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `UserRole` VALUES (74, 'apollo', 111, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `UserRole` VALUES (75, 'apollo', 112, b'0', 'apollo', '2020-05-28 15:53:16', 'apollo', '2020-05-28 15:53:16');
INSERT INTO `UserRole` VALUES (76, 'waynechu', 115, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `UserRole` VALUES (77, 'apollo', 117, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `UserRole` VALUES (78, 'apollo', 118, b'0', 'apollo', '2020-08-22 15:17:18', 'apollo', '2020-08-22 15:17:18');
INSERT INTO `UserRole` VALUES (79, 'apollo', 121, b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `UserRole` VALUES (80, 'apollo', 122, b'0', 'apollo', '2020-08-22 15:19:28', 'apollo', '2020-08-22 15:19:28');
INSERT INTO `UserRole` VALUES (81, 'waynechu', 121, b'0', 'apollo', '2020-08-22 15:19:34', 'apollo', '2020-08-22 15:19:34');
INSERT INTO `UserRole` VALUES (82, 'waynechu', 122, b'0', 'apollo', '2020-08-22 15:19:36', 'apollo', '2020-08-22 15:19:36');
INSERT INTO `UserRole` VALUES (83, 'waynechu', 125, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `UserRole` VALUES (84, 'apollo', 127, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `UserRole` VALUES (85, 'apollo', 128, b'0', 'apollo', '2020-08-24 14:54:41', 'apollo', '2020-08-24 14:54:41');
INSERT INTO `UserRole` VALUES (86, 'apollo', 131, b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `UserRole` VALUES (87, 'apollo', 132, b'0', 'apollo', '2020-08-24 17:20:40', 'apollo', '2020-08-24 17:20:40');
INSERT INTO `UserRole` VALUES (88, 'waynechu', 131, b'0', 'apollo', '2020-08-24 17:20:44', 'apollo', '2020-08-24 17:20:44');
INSERT INTO `UserRole` VALUES (89, 'waynechu', 132, b'0', 'apollo', '2020-08-24 17:20:46', 'apollo', '2020-08-24 17:20:46');
INSERT INTO `UserRole` VALUES (90, 'waynechu', 135, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `UserRole` VALUES (91, 'apollo', 137, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');
INSERT INTO `UserRole` VALUES (92, 'apollo', 138, b'0', 'apollo', '2020-08-24 17:47:28', 'apollo', '2020-08-24 17:47:28');

-- ----------------------------
-- Table structure for Users
-- ----------------------------
DROP TABLE IF EXISTS `Users`;
CREATE TABLE `Users`  (
  `Id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `Username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '用户名',
  `Password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '密码',
  `Email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'default' COMMENT '邮箱地址',
  `Enabled` tinyint(4) NULL DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of Users
-- ----------------------------
INSERT INTO `Users` VALUES (1, 'apollo', '$2a$10$7r20uS.BQ9uBpf3Baj3uQOZvMVvB1RN3PYoKE94gtz2.WAOuiiwXS', 'apollo@acme.com', 1);
INSERT INTO `Users` VALUES (2, 'waynechu', '$2a$10$x43Bi3mCsd24SxAwvIKeMuwIThRH.Cf4we1qAV8RTfTlodc1MT52K', 'waynechu@waynechu.cn', 1);

SET FOREIGN_KEY_CHECKS = 1;
