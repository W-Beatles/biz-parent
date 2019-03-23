/*
 Navicat Premium Data Transfer

 Source Server         : localhost-master-3306
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : common

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 23/03/2019 23:29:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '字典ID',
  `type_code` int(10) NOT NULL COMMENT '字典类型Code',
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型Name',
  `code` int(10) NOT NULL COMMENT '字典Code',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典Name',
  `parent_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父节点ID。默认0，无父节点',
  `create_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_user` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除：0否，1是。默认0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------
INSERT INTO `sys_dictionary` VALUES (1, 1, '订单', 0, '未采购', 0, 'waynechu', '2019-01-18 07:40:41', 'waynechu', '2019-01-18 07:58:04', 1);
INSERT INTO `sys_dictionary` VALUES (2, 1, '订单', 1, '采购中', 0, 'waynechu', '2019-01-18 07:41:41', NULL, NULL, 0);
INSERT INTO `sys_dictionary` VALUES (3, 1, '订单', 8, '采购XX', 0, 'waynechu', '2019-01-18 07:42:11', 'Wayne Chu', '2019-03-23 13:19:45', 0);
INSERT INTO `sys_dictionary` VALUES (4, 1, '订单', 3, '采购失败', 0, 'waynechu', '2019-01-18 07:42:31', NULL, NULL, 0);
INSERT INTO `sys_dictionary` VALUES (5, 1, '订单', 8, '采购XX', 0, 'waynechu', '2019-01-18 07:50:19', 'waynechu', '2019-01-18 08:52:48', 1);
INSERT INTO `sys_dictionary` VALUES (6, 1, '订单', 0, '未采购', 0, 'waynechu', '2019-01-18 08:22:23', 'waynechu', '2019-01-18 07:58:04', 1);
INSERT INTO `sys_dictionary` VALUES (7, 1, '订单', 3, '采购失败', 0, 'waynechu', '2019-01-18 08:52:42', 'waynechu', '2019-03-23 15:19:31', 0);
INSERT INTO `sys_dictionary` VALUES (8, 1, '订单', 3, '采购失败', 0, 'Wayne Chu', '2019-03-23 22:37:03', NULL, NULL, 0);
INSERT INTO `sys_dictionary` VALUES (9, 1, '订单', 3, '采购失败', 0, 'Wayne Chu', '2019-03-23 13:19:45', NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
