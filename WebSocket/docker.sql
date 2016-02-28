/*
Navicat MySQL Data Transfer

Source Server         : 123
Source Server Version : 60011
Source Host           : localhost:3306
Source Database       : docker

Target Server Type    : MYSQL
Target Server Version : 60011
File Encoding         : 65001

Date: 2016-01-18 09:47:24
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `common`
-- ----------------------------
DROP TABLE IF EXISTS `common`;
CREATE TABLE `common` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  `value` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of common
-- ----------------------------
INSERT INTO common VALUES ('1', 'port', '30000');

-- ----------------------------
-- Table structure for `service_setting`
-- ----------------------------
DROP TABLE IF EXISTS `service_setting`;
CREATE TABLE `service_setting` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `service_name` varchar(255) NOT NULL,
  `service_desc` longtext,
  `service_git_id` bigint(20) NOT NULL,
  `service_git_url` longtext NOT NULL,
  `service_git_branch` varchar(255) NOT NULL,
  `service_docker_num` int(11) NOT NULL,
  `service_docker_port` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of service_setting
-- ----------------------------
INSERT INTO service_setting VALUES ('1', '2', 'AutoDeploy', '', '2', 'http://159.226.57.11/fly/AutoDeploy.git', 'master', '10', '30000');
INSERT INTO service_setting VALUES ('2', '2', 'AutoDeploy', '', '2', 'http://159.226.57.11/fly/AutoDeploy.git', 'master', '20', '30000');
INSERT INTO service_setting VALUES ('3', '2', 'AutoDeploy', '', '2', 'http://159.226.57.11/fly/AutoDeploy.git', 'master', '20', '30000');
INSERT INTO service_setting VALUES ('4', '2', 'AutoDeploy', '', '2', 'http://159.226.57.11/fly/AutoDeploy.git', 'master', '20', '30000');
INSERT INTO service_setting VALUES ('5', '2', 'AutoDeploy', '', '2', 'http://159.226.57.11/fly/AutoDeploy.git', 'master', '20', '30000');
INSERT INTO service_setting VALUES ('6', '2', 'AutoDeploy', '', '2', 'http://159.226.57.11/fly/AutoDeploy.git', 'master', '10', '30000');
INSERT INTO service_setting VALUES ('7', '2', 'AutoDeploy', '', '2', 'http://159.226.57.11/fly/AutoDeploy.git', 'master', '20', '30000');

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) NOT NULL,
  `user_psw` varchar(30) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO userinfo VALUES ('1', 'mlk840403', '95588520');
INSERT INTO userinfo VALUES ('2', 'mlk840404', '95588520');
INSERT INTO userinfo VALUES ('3', 'mlk840405', '95588520');
INSERT INTO userinfo VALUES ('4', 'mlk840406', '95588520');
INSERT INTO userinfo VALUES ('5', 'mlk840407', '95588520');
INSERT INTO userinfo VALUES ('6', 'mlk840408', '95588520');
INSERT INTO userinfo VALUES ('7', 'mlk840409', '95588520');
INSERT INTO userinfo VALUES ('8', 'mlk840409啊', '95588520');
