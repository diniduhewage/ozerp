-- add columns to User table
ALTER TABLE User
ADD COLUMN phone VARCHAR(30),
ADD COLUMN address VARCHAR(255),
ADD COLUMN dateOfBirth VARCHAR(20),
ADD COLUMN gender VARCHAR(10),
ADD COLUMN nationality VARCHAR(20),
ADD COLUMN userType VARCHAR(20),
ADD COLUMN username VARCHAR(50),
ADD COLUMN status VARCHAR(20),
ADD COLUMN lastLoginDate BIGINT,
ADD COLUMN lastLogoutDate BIGINT,
ADD COLUMN accountCreationDate BIGINT,
ADD COLUMN accountExpirationDate BIGINT,
ADD COLUMN profilePicture BLOB,
ADD COLUMN securityQuestion1 VARCHAR(255),
ADD COLUMN securityAnswer1 VARCHAR(255),
ADD COLUMN securityQuestion2 VARCHAR(255),
ADD COLUMN securityAnswer2 VARCHAR(255),
ADD COLUMN twoFactorAuthentication BOOLEAN default false,
ADD COLUMN lastTwoFactorAuthenticationDate BIGINT,
ADD COLUMN preferredLanguage VARCHAR(20),
ADD COLUMN timezone VARCHAR(20),
ADD COLUMN notifications BOOLEAN default false,
ADD COLUMN notificationSettings VARCHAR(255);
