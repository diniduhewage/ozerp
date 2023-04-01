DROP TABLE IF EXISTS `action_tab`;
CREATE TABLE `action_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `created_date` bigint DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `component_tab`;
CREATE TABLE `component_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `created_date` bigint DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `permission_tab`;
CREATE TABLE `permission_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `created_date` bigint DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `action_id` bigint NOT NULL,
  `component_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ACTION` (`action_id`),
  KEY `FK_COMPONENT` (`component_id`),
  CONSTRAINT `FK_COMPONENT` FOREIGN KEY (`component_id`) REFERENCES `component_tab` (`id`),
  CONSTRAINT `FK_ACTION` FOREIGN KEY (`action_id`) REFERENCES `action_tab` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `role_tab`;
CREATE TABLE `role_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `created_date` bigint DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `role_permission_tab`;
CREATE TABLE `role_permission_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` bigint DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `permission_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PERMISSION_ROLE_PERMISSION` (`permission_id`),
  KEY `FK_ROLE_ROLE_PERMISSION` (`role_id`),
  CONSTRAINT `FK_ROLE_ROLE_PERMISSION` FOREIGN KEY (`role_id`) REFERENCES `role_tab` (`id`),
  CONSTRAINT `FK_PERMISSION_ROLE_PERMISSION` FOREIGN KEY (`permission_id`) REFERENCES `permission_tab` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `app_user_tab`;
CREATE TABLE `app_user_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` bigint DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `active_user` boolean DEFAULT false,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `user_role_tab`;
CREATE TABLE `user_role_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` bigint DEFAULT NULL,
  `modified_date` bigint DEFAULT NULL,
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ROLE_USER_ROLE` (`role_id`),
  KEY `FK_USER_USER_ROLE` (`user_id`),
  CONSTRAINT `FK_ROLE_USER_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role_tab` (`id`),
  CONSTRAINT `FK_USER_USER_ROLE` FOREIGN KEY (`user_id`) REFERENCES `app_user_tab` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `verification_token_tab`;
CREATE TABLE `verification_token_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration_time` bigint DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_VERIFY_TOKEN` (`user_id`),
  CONSTRAINT `FK_USER_VERIFY_TOKEN` FOREIGN KEY (`user_id`) REFERENCES `app_user_tab` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



DROP TABLE IF EXISTS `password_reset_token_tab`;
CREATE TABLE `password_reset_token_tab` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expiration_time` bigint DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_PASSWORD_RESET_TOKEN` (`user_id`),
  CONSTRAINT `FK_USER_PASSWORD_RESET_TOKEN` FOREIGN KEY (`user_id`) REFERENCES `app_user_tab` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
