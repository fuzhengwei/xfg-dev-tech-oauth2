# ************************************************************
# Sequel Ace SQL dump
# 版本号： 20050
#
# https://sequel-ace.com/
# https://github.com/Sequel-Ace/Sequel-Ace
#
# 主机: 192.168.1.108 (MySQL 8.0.32)
# 数据库: xfg-dev-tech-oauth2
# 生成时间: 2025-01-12 02:53:08 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
SET NAMES utf8mb4;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE='NO_AUTO_VALUE_ON_ZERO', SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE database if NOT EXISTS `xfg-dev-tech-oauth2` default character set utf8mb4 collate utf8mb4_0900_ai_ci;
use `xfg-dev-tech-oauth2`;

# 转储表 oauth_access_token
# ------------------------------------------------------------

DROP TABLE IF EXISTS `oauth_access_token`;

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# 转储表 oauth_account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `oauth_account`;

CREATE TABLE `oauth_account` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '账号ID',
  `client_id` varchar(50) NOT NULL COMMENT '客户端ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(200) DEFAULT NULL COMMENT '密码',
  `mobile` varchar(13) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '账号可用',
  `account_non_expired` tinyint(1) DEFAULT '1' COMMENT '账号未过期',
  `credentials_non_expired` tinyint(1) DEFAULT '1' COMMENT '密码未过期',
  `account_non_locked` tinyint(1) DEFAULT '1' COMMENT '账号未锁定',
  `account_non_deleted` tinyint(1) DEFAULT '1' COMMENT '账号未删除',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `user_idx` (`client_id`,`username`,`password`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='自定义认证中心账号表';

LOCK TABLES `oauth_account` WRITE;
/*!40000 ALTER TABLE `oauth_account` DISABLE KEYS */;

INSERT INTO `oauth_account` (`id`, `client_id`, `username`, `password`, `mobile`, `email`, `enabled`, `account_non_expired`, `credentials_non_expired`, `account_non_locked`, `account_non_deleted`, `created_time`, `updated_time`)
VALUES
	(1,'xfg-studio','liergou','$2a$10$EZbplKhID6StHwBIoNnsLOPPj4/zyyFNywvZ1H.uemC1gEScZnmmy','13500001111','184172133@qq.com',1,1,1,1,1,'2025-01-09 13:16:57','2025-01-12 10:02:18'),
	(2,'xfg-studio','xiaofuge','$2a$10$EZbplKhID6StHwBIoNnsLOPPj4/zyyFNywvZ1H.uemC1gEScZnmmy','13500002222','523088136@qq.com',1,1,1,1,1,'2025-01-09 13:16:57','2025-01-12 10:02:11');

/*!40000 ALTER TABLE `oauth_account` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 oauth_approvals
# ------------------------------------------------------------

DROP TABLE IF EXISTS `oauth_approvals`;

CREATE TABLE `oauth_approvals` (
  `userId` varchar(255) DEFAULT NULL,
  `clientId` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# 转储表 oauth_client_details
# ------------------------------------------------------------

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uri` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int DEFAULT NULL,
  `refresh_token_validity` int DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;

INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`)
VALUES
	('xfg-studio','big-market-app','$2a$10$EZbplKhID6StHwBIoNnsLOPPj4/zyyFNywvZ1H.uemC1gEScZnmmy','read,write','client_credentials,authorization_code,password,refresh_token,implicit','http://bugstack.cn','user',7199,2592000,NULL,'true');

/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;


# 转储表 oauth_client_token
# ------------------------------------------------------------

DROP TABLE IF EXISTS `oauth_client_token`;

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(255) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# 转储表 oauth_code
# ------------------------------------------------------------

DROP TABLE IF EXISTS `oauth_code`;

CREATE TABLE `oauth_code` (
  `code` varchar(255) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



# 转储表 oauth_refresh_token
# ------------------------------------------------------------

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
