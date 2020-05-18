/*
 Navicat Premium Data Transfer

 Source Server         : localhost_13306
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:13306
 Source Schema         : ApolloPortalDB

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 18/05/2020 19:33:32
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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of App
-- ----------------------------
INSERT INTO `App` VALUES (1, 'SampleApp', 'Sample App', 'TEST1', '样例部门1', 'apollo', 'apollo@acme.com', b'1', 'default', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `App` VALUES (2, 'biz-spring-cloud-eureka', 'biz-spring-cloud-eureka', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `App` VALUES (3, 'biz-spring-cloud-gateway', 'biz-spring-cloud-gateway', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `App` VALUES (4, 'service-utility', 'service-utility', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `App` VALUES (5, 'service-order', 'service-order', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `App` VALUES (6, 'biz-spring-cloud-public', 'biz-spring-cloud-public', 'Archer', '中间件组', 'waynechu', 'waynechu@waynechu.cn', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');

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
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用namespace定义' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of AppNamespace
-- ----------------------------
INSERT INTO `AppNamespace` VALUES (1, 'application', 'SampleApp', 'properties', b'0', 'default app namespace', b'1', '', '2020-05-15 10:59:44', 'apollo', '2020-05-15 11:09:28');
INSERT INTO `AppNamespace` VALUES (2, 'application', 'biz-spring-cloud-eureka', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-15 11:48:50', 'apollo', '2020-05-15 11:48:50');
INSERT INTO `AppNamespace` VALUES (3, 'application', 'biz-spring-cloud-gateway', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-15 14:18:07', 'apollo', '2020-05-15 14:18:07');
INSERT INTO `AppNamespace` VALUES (4, 'application', 'service-utility', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `AppNamespace` VALUES (5, 'application', 'service-order', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `AppNamespace` VALUES (6, 'application', 'biz-spring-cloud-public', 'properties', b'0', 'default app namespace', b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `AppNamespace` VALUES (7, 'Archer.biz-spring-cloud-public', 'biz-spring-cloud-public', 'properties', b'1', '', b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');

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
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'permission表' ROW_FORMAT = Dynamic;

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
INSERT INTO `Permission` VALUES (23, 'CreateNamespace', 'service-utility', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Permission` VALUES (24, 'AssignRole', 'service-utility', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Permission` VALUES (25, 'CreateCluster', 'service-utility', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Permission` VALUES (26, 'ManageAppMaster', 'service-utility', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Permission` VALUES (27, 'ModifyNamespace', 'service-utility+application', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Permission` VALUES (28, 'ReleaseNamespace', 'service-utility+application', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Permission` VALUES (29, 'ModifyNamespace', 'service-utility+application+DEV', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Permission` VALUES (30, 'ReleaseNamespace', 'service-utility+application+DEV', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Permission` VALUES (31, 'CreateNamespace', 'service-order', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Permission` VALUES (32, 'CreateCluster', 'service-order', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Permission` VALUES (33, 'AssignRole', 'service-order', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Permission` VALUES (34, 'ManageAppMaster', 'service-order', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Permission` VALUES (35, 'ModifyNamespace', 'service-order+application', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Permission` VALUES (36, 'ReleaseNamespace', 'service-order+application', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Permission` VALUES (37, 'ModifyNamespace', 'service-order+application+DEV', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Permission` VALUES (38, 'ReleaseNamespace', 'service-order+application+DEV', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
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
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

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
INSERT INTO `Role` VALUES (17, 'Master+service-utility', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Role` VALUES (18, 'ManageAppMaster+service-utility', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Role` VALUES (19, 'ModifyNamespace+service-utility+application', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Role` VALUES (20, 'ReleaseNamespace+service-utility+application', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Role` VALUES (21, 'ModifyNamespace+service-utility+application+DEV', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Role` VALUES (22, 'ReleaseNamespace+service-utility+application+DEV', b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `Role` VALUES (23, 'Master+service-order', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Role` VALUES (24, 'ManageAppMaster+service-order', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Role` VALUES (25, 'ModifyNamespace+service-order+application', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Role` VALUES (26, 'ReleaseNamespace+service-order+application', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Role` VALUES (27, 'ModifyNamespace+service-order+application+DEV', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `Role` VALUES (28, 'ReleaseNamespace+service-order+application+DEV', b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
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
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和权限的绑定表' ROW_FORMAT = Dynamic;

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
INSERT INTO `RolePermission` VALUES (23, 17, 23, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `RolePermission` VALUES (24, 17, 24, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `RolePermission` VALUES (25, 17, 25, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `RolePermission` VALUES (26, 18, 26, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `RolePermission` VALUES (27, 19, 27, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `RolePermission` VALUES (28, 20, 28, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `RolePermission` VALUES (29, 21, 29, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `RolePermission` VALUES (30, 22, 30, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `RolePermission` VALUES (31, 23, 32, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `RolePermission` VALUES (32, 23, 33, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `RolePermission` VALUES (33, 23, 31, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `RolePermission` VALUES (34, 24, 34, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `RolePermission` VALUES (35, 25, 35, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `RolePermission` VALUES (36, 26, 36, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `RolePermission` VALUES (37, 27, 37, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `RolePermission` VALUES (38, 28, 38, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
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
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和role的绑定表' ROW_FORMAT = Dynamic;

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
INSERT INTO `UserRole` VALUES (11, 'waynechu', 17, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `UserRole` VALUES (12, 'apollo', 19, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `UserRole` VALUES (13, 'apollo', 20, b'0', 'apollo', '2020-05-15 14:22:26', 'apollo', '2020-05-15 14:22:26');
INSERT INTO `UserRole` VALUES (14, 'waynechu', 23, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `UserRole` VALUES (15, 'apollo', 25, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `UserRole` VALUES (16, 'apollo', 26, b'0', 'apollo', '2020-05-15 14:22:50', 'apollo', '2020-05-15 14:22:50');
INSERT INTO `UserRole` VALUES (17, 'waynechu', 29, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `UserRole` VALUES (18, 'apollo', 31, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `UserRole` VALUES (19, 'apollo', 32, b'0', 'apollo', '2020-05-15 14:25:36', 'apollo', '2020-05-15 14:25:36');
INSERT INTO `UserRole` VALUES (20, 'apollo', 35, b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `UserRole` VALUES (21, 'apollo', 36, b'0', 'apollo', '2020-05-15 14:26:03', 'apollo', '2020-05-15 14:26:03');
INSERT INTO `UserRole` VALUES (22, 'waynechu', 35, b'0', 'apollo', '2020-05-15 14:26:08', 'apollo', '2020-05-15 14:26:08');
INSERT INTO `UserRole` VALUES (23, 'waynechu', 36, b'0', 'apollo', '2020-05-15 14:26:10', 'apollo', '2020-05-15 14:26:10');

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
