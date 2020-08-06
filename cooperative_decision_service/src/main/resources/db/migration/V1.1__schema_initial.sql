 CREATE TABLE IF NOT EXISTS `meeting` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `title` varchar(100) NOT NULL,
   `description` varchar(255) DEFAULT NULL,
   `event_date` DATETIME NOT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 CREATE TABLE IF NOT EXISTS `schedule` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `title` varchar(100) NOT NULL,
   `description` varchar(255) DEFAULT NULL,
   `meeting_id` bigint NOT NULL,
   PRIMARY KEY (`id`),
   KEY `FK_schedule_meeting` (`meeting_id`),
   CONSTRAINT `FK_schedule_meeting` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  CREATE TABLE IF NOT EXISTS `session` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `start_date_time` DATETIME NOT NULL,
   `end_date_time` DATETIME NOT NULL,
   `schedule_id` bigint NOT NULL,
   PRIMARY KEY (`id`),
   CONSTRAINT `FK_session_schedule` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 CREATE TABLE IF NOT EXISTS `associate` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `document` varchar(14) NOT NULL,
   `document_type` varchar(4) NOT NULL,
   `name` varchar(100) NOT NULL,
   `birth_date` DATE DEFAULT NULL,
   `enabled` bit NOT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  CREATE TABLE IF NOT EXISTS `meeting_has_associate` (
   `associate_id` bigint NOT NULL,
   `meeting_id` bigint NOT NULL,
   `moderator` bit(1) NULL,
   PRIMARY KEY (`associate_id`, `meeting_id`),
   KEY `FK_meeting_has_associate_associate` (`associate_id`),
   KEY `FK_meeting_has_associate_meeting` (`meeting_id`),
   CONSTRAINT `FK_meeting_has_associate_associate` FOREIGN KEY (`associate_id`) REFERENCES `associate` (`id`),
   CONSTRAINT `FK_meeting_has_associate_meeting` FOREIGN KEY (`meeting_id`) REFERENCES `meeting` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 CREATE TABLE IF NOT EXISTS `vote` (
   `id` bigint NOT NULL AUTO_INCREMENT,
   `decision` varchar(3) NOT NULL,
   `seem` varchar(255) DEFAULT NULL,
   `associate_id` bigint NOT NULL,
   `session_id` bigint NOT NULL,
   PRIMARY KEY (`id`),
   KEY `FK_vote_associate` (`associate_id`),
   KEY `FK_vote_session` (`session_id`),
   CONSTRAINT `FK_vote_associate` FOREIGN KEY (`associate_id`) REFERENCES `associate` (`id`),
   CONSTRAINT `FK_vote_session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;