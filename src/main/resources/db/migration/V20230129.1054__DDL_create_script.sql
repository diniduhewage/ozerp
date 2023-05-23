DROP TABLE IF EXISTS `Action`;
CREATE TABLE `Action` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createdDate` bigint DEFAULT NULL,
  `modifiedDate` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Component`;
CREATE TABLE `Component` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createdDate` bigint DEFAULT NULL,
  `modifiedDate` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Permission`;
CREATE TABLE `Permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createdDate` bigint DEFAULT NULL,
  `modifiedDate` bigint DEFAULT NULL,
  `actionId` bigint NOT NULL,
  `componentId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Action` (`actionId`),
  KEY `FK_Component` (`componentId`),
  CONSTRAINT `FK_Component` FOREIGN KEY (`componentId`) REFERENCES `Component` (`id`),
  CONSTRAINT `FK_Action` FOREIGN KEY (`actionId`) REFERENCES `Action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createdDate` bigint DEFAULT NULL,
  `modifiedDate` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `RolePermission`;
CREATE TABLE `RolePermission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdDate` bigint DEFAULT NULL,
  `modifiedDate` bigint DEFAULT NULL,
  `permissionId` bigint NOT NULL,
  `roleId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Permission` (`permissionId`),
  KEY `FK_Role` (`roleId`),
  CONSTRAINT `FK_Role` FOREIGN KEY (`roleId`) REFERENCES `Role` (`id`),
  CONSTRAINT `FK_Permission` FOREIGN KEY (`permissionId`) REFERENCES `Permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `createdDate` bigint DEFAULT NULL,
  `modifiedDate` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `UserRole`;
CREATE TABLE `UserRole` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `createdDate` bigint DEFAULT NULL,
  `modifiedDate` bigint DEFAULT NULL,
  `roleId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ROLE_2` (`roleId`),
  KEY `FK_USER` (`userId`),
  CONSTRAINT `FK_USER` FOREIGN KEY (`userId`) REFERENCES `User` (`id`),
  CONSTRAINT `FK_ROLE_2` FOREIGN KEY (`roleId`) REFERENCES `Role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `VerificationToken`;
CREATE TABLE `VerificationToken` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expirationTime` bigint DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `userId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_VERIFY_TOKEN` (`userId`),
  CONSTRAINT `FK_USER_VERIFY_TOKEN` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `PasswordResetToken`;
CREATE TABLE `PasswordResetToken` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `expirationTime` bigint DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `userId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_PASSWORD_RESET_TOKEN` (`userId`),
  CONSTRAINT `FK_USER_PASSWORD_RESET_TOKEN` FOREIGN KEY (`userId`) REFERENCES `User` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Merchant`;
CREATE TABLE `Merchant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `createdDate` bigint DEFAULT NULL,
  `modifiedDate` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

