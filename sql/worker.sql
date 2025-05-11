/*
 Navicat Premium Dump SQL

 Source Server         : local_mysql
 Source Server Type    : MySQL
 Source Server Version : 80028 (8.0.28)
 Source Host           : localhost:3306
 Source Schema         : worker

 Target Server Type    : MySQL
 Target Server Version : 80028 (8.0.28)
 File Encoding         : 65001

 Date: 11/05/2025 22:55:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clock_in_record
-- ----------------------------
DROP TABLE IF EXISTS `clock_in_record`;
CREATE TABLE `clock_in_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `worker_id` bigint NOT NULL COMMENT '工人ID',
  `clock_type` tinyint NOT NULL COMMENT '打卡类型（1-上班打卡，2-下班打卡）',
  `clock_time` datetime NOT NULL COMMENT '打卡时间',
  `status` tinyint NOT NULL COMMENT '打卡状态（1-正常，2-迟到，3-早退）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_worker_id`(`worker_id` ASC) USING BTREE,
  INDEX `idx_clock_time`(`clock_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '打卡记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of clock_in_record
-- ----------------------------
INSERT INTO `clock_in_record` VALUES (1, 19, 1, '2025-05-11 22:47:24', 2, 'do esse ipsum', 19, '2025-05-11 22:47:24', NULL, NULL, 0);
INSERT INTO `clock_in_record` VALUES (2, 19, 2, '2025-05-11 22:50:15', 1, 'do esse ipsum', 19, '2025-05-11 22:50:15', NULL, NULL, 0);

-- ----------------------------
-- Table structure for leave_record
-- ----------------------------
DROP TABLE IF EXISTS `leave_record`;
CREATE TABLE `leave_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `worker_id` bigint NOT NULL COMMENT '工人ID',
  `leave_type` tinyint NOT NULL COMMENT '请假类型（1-事假，2-病假，3-年假，4-其他）',
  `start_time` datetime NOT NULL COMMENT '请假开始时间',
  `end_time` datetime NOT NULL COMMENT '请假结束时间',
  `reason` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '请假原因',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除（0-否，1-是）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_worker_id`(`worker_id` ASC) USING BTREE,
  INDEX `idx_start_time`(`start_time` ASC) USING BTREE,
  INDEX `idx_end_time`(`end_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '请假记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leave_record
-- ----------------------------
INSERT INTO `leave_record` VALUES (1, 19, 1, '2025-05-12 14:24:20', '2025-05-12 19:18:53', 'ea cillum elit Lorem commodo', 19, '2025-05-11 22:28:49', NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父资源ID',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称',
  `type` tinyint(1) NOT NULL COMMENT '权限类型 1:目录，2:菜单，3:按钮',
  `sort` int NOT NULL COMMENT '显示排序',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `resource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单地址',
  `router_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端路径',
  `router_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端名称',
  `open_type` tinyint(1) NULL DEFAULT 0 COMMENT '打开方式 1-页签 2-新窗口',
  `delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除 0-未删除 1-删除',
  `creator` bigint NOT NULL DEFAULT 1 COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理端资源表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, 1, 'system:user', '用户管理', 2, 1, '', 'user/info', 'user/info', '', 0, 0, 0, '2023-12-03 18:47:48');
INSERT INTO `sys_permission` VALUES (2, 0, 1, 'system:role', '角色管理', 2, 2, '', 'role/list', 'role/list', '', 0, 0, 0, '2024-01-10 00:08:30');
INSERT INTO `sys_permission` VALUES (3, 0, 1, 'system:worker', '工人管理', 2, 3, '', 'worker/list', 'worker/list', '', 0, 0, 0, '2024-01-10 00:07:11');
INSERT INTO `sys_permission` VALUES (4, 0, 1, 'system:wage', '工种薪资', 2, 4, '', 'finance/wage', 'finance/wage', '', 0, 0, 0, '2024-01-10 00:03:48');
INSERT INTO `sys_permission` VALUES (5, 0, 1, 'system:salary', '工资记录', 2, 5, '', 'finance/salary', 'finance/salary', '', 0, 0, 0, '2023-12-03 18:47:56');
INSERT INTO `sys_permission` VALUES (6, 0, 1, 'system:attendance', '考勤管理', 2, 6, '', 'attendance/record', 'attendance/record', '', 0, 0, 0, '2023-12-03 18:47:56');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
  `delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除 0-未删除 1-删除',
  `creator` bigint NOT NULL DEFAULT 1 COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理端角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 1, 0, 1, '2025-05-10 00:01:36');
INSERT INTO `sys_role` VALUES (2, '管理员', 1, 0, 1, '2025-05-10 16:48:28');
INSERT INTO `sys_role` VALUES (3, '普工', 1, 0, 1, '2025-05-10 17:40:56');
INSERT INTO `sys_role` VALUES (4, '木工', 1, 0, 1, '2025-05-10 17:42:00');
INSERT INTO `sys_role` VALUES (5, '钢筋工', 1, 0, 1, '2025-05-10 17:42:24');
INSERT INTO `sys_role` VALUES (6, '电工', 1, 0, 1, '2025-05-10 17:42:31');
INSERT INTO `sys_role` VALUES (7, '瓦工', 1, 0, 1, '2025-05-10 17:43:20');
INSERT INTO `sys_role` VALUES (8, '焊工', 1, 0, 1, '2025-05-10 17:43:26');
INSERT INTO `sys_role` VALUES (9, '测试新增角色', 0, 0, 1, '2025-05-11 20:46:38');

-- ----------------------------
-- Table structure for sys_role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission_relation`;
CREATE TABLE `sys_role_permission_relation`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_role_id` bigint NOT NULL COMMENT '管理端角色 ID',
  `admin_resource_id` bigint NOT NULL COMMENT '管理端用户 ID',
  `creator` bigint NOT NULL DEFAULT 1 COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_role_permission`(`admin_role_id` ASC, `admin_resource_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理端角色资源关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission_relation
-- ----------------------------
INSERT INTO `sys_role_permission_relation` VALUES (52, 9, 2, 1, '2025-05-11 20:46:38');
INSERT INTO `sys_role_permission_relation` VALUES (53, 9, 3, 1, '2025-05-11 20:46:38');
INSERT INTO `sys_role_permission_relation` VALUES (54, 9, 4, 1, '2025-05-11 21:53:48');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:启用',
  `phone_num` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号名称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '真实姓名',
  `delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除 0-未删除 1-删除',
  `creator` bigint NOT NULL DEFAULT 1 COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_phone_num`(`phone_num` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理端用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 1, '16666688888', '123456', 'admin', 0, 0, '2025-05-08 15:21:46');
INSERT INTO `sys_user` VALUES (2, 1, '19988886668', '123', '员工1', 0, 0, '2025-05-08 15:21:46');
INSERT INTO `sys_user` VALUES (3, 1, '16666688889', '123456', '工人', 0, 0, '2025-05-10 21:18:35');
INSERT INTO `sys_user` VALUES (8, 1, '1871518761543', '123456', '123', 0, 1, '2025-05-10 23:34:39');
INSERT INTO `sys_user` VALUES (9, 0, '19999999999', '123456', '工人甲', 0, 1, '2025-05-11 17:29:53');
INSERT INTO `sys_user` VALUES (25, 1, '18717341712', '123456', '工人112', 0, 1, '2025-05-11 17:41:39');
INSERT INTO `sys_user` VALUES (26, 1, '86456842889', '123456', '工人2', 1, 1, '2025-05-11 17:53:19');
INSERT INTO `sys_user` VALUES (27, 0, '18714242435', '123456', '工人1', 0, 1, '2025-05-11 20:41:39');
INSERT INTO `sys_user` VALUES (28, 1, '17644986436', '123456', '奉静1', 1, 1, '2025-05-11 21:57:46');

-- ----------------------------
-- Table structure for sys_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_relation`;
CREATE TABLE `sys_user_role_relation`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `admin_user_id` bigint NOT NULL COMMENT '管理端用户 ID',
  `admin_role_id` bigint NOT NULL COMMENT '管理端角色 ID',
  `creator` bigint NOT NULL DEFAULT 1 COMMENT '创建人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_role`(`admin_user_id` ASC, `admin_role_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理端用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role_relation
-- ----------------------------
INSERT INTO `sys_user_role_relation` VALUES (1, 1, 1, 1, '2024-03-24 15:59:52');
INSERT INTO `sys_user_role_relation` VALUES (2, 2, 2, 1, '2024-03-30 16:48:47');
INSERT INTO `sys_user_role_relation` VALUES (7, 3, 3, 1, '2025-05-10 21:18:53');
INSERT INTO `sys_user_role_relation` VALUES (8, 8, 2, 1, '2025-05-10 23:34:39');
INSERT INTO `sys_user_role_relation` VALUES (11, 25, 3, 1, '2025-05-11 17:41:39');
INSERT INTO `sys_user_role_relation` VALUES (12, 9, 3, 1, '2025-05-11 17:43:01');
INSERT INTO `sys_user_role_relation` VALUES (13, 26, 3, 1, '2025-05-11 17:53:19');
INSERT INTO `sys_user_role_relation` VALUES (14, 27, 3, 1, '2025-05-11 20:41:39');
INSERT INTO `sys_user_role_relation` VALUES (19, 28, 5, 1, '2025-05-11 22:12:02');

-- ----------------------------
-- Table structure for worker_info
-- ----------------------------
DROP TABLE IF EXISTS `worker_info`;
CREATE TABLE `worker_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID（主键自增）',
  `sys_user_id` bigint NOT NULL COMMENT '账号ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `worker_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工号',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工种',
  `gender` tinyint NOT NULL COMMENT '性别 0-女 1-男',
  `phone_num` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '联系方式',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证号',
  `emergency_contact` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '紧急联系人',
  `status` tinyint NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
  `delete` tinyint NOT NULL DEFAULT 0 COMMENT '删除 0-未删除 1-删除',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `created_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工人信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of worker_info
-- ----------------------------
INSERT INTO `worker_info` VALUES (1, 9, '工人甲', '202505111729538885', 3, '普工', 1, '19999999999', '12010020090819460X', '19999999999', 1, 0, 1, '2025-05-11 19:34:22');
INSERT INTO `worker_info` VALUES (17, 25, '工人112', '202505111741397991', 3, '普工', 1, '18717341712', '431224199981828432', '', 0, 0, 1, '2025-05-11 19:34:25');
INSERT INTO `worker_info` VALUES (18, 26, '工人2', '202505111753184106', 3, '普工', 0, '86456842889', '440103196803149276', '12345678911', 1, 0, 1, '2025-05-11 22:17:49');
INSERT INTO `worker_info` VALUES (19, 28, '奉静1', '202505112157462202', 5, '钢筋工', 0, '17644986436', '130100202008011418', '17644986436', 1, 0, 1, '2025-05-11 22:17:50');

SET FOREIGN_KEY_CHECKS = 1;
