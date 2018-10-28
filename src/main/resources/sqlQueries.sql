CREATE TABLE `custom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pathfinder_pg_id` bigint(20) DEFAULT NULL,
  `telegram_user_id` bigint(20) DEFAULT NULL,
  `custom_name` varchar(45) DEFAULT NULL,
  `custom_command` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

CREATE TABLE `default_vars` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `command` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `pathfinder_pg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `telegram_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

CREATE TABLE `stats` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pathfinder_pg_id` bigint(20) DEFAULT NULL,
  `hp` bigint(20) DEFAULT NULL,
  `as_strength` bigint(20) DEFAULT NULL,
  `as_dexterity` bigint(20) DEFAULT NULL,
  `as_constitution` bigint(20) DEFAULT NULL,
  `as_intelligence` bigint(20) DEFAULT NULL,
  `as_wisdom` bigint(20) DEFAULT NULL,
  `as_charisma` bigint(20) DEFAULT NULL,
  `ts_fortitude` bigint(20) DEFAULT NULL,
  `ts_will` bigint(20) DEFAULT NULL,
  `ts_reflex` bigint(20) DEFAULT NULL,
  `base_attack_bonus` bigint(20) DEFAULT NULL,
  `level` bigint(20) DEFAULT NULL,
  `init` bigint(20) DEFAULT NULL,
  `armor_class` bigint(20) DEFAULT NULL,
  `surprise_armor_class` bigint(20) DEFAULT NULL,
  `contact_armor_class` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pathfinder_pg_id_UNIQUE` (`pathfinder_pg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

CREATE TABLE `telegram_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tg_name` varchar(45) DEFAULT NULL,
  `tg_surname` varchar(45) DEFAULT NULL,
  `tg_username` varchar(45) DEFAULT NULL,
  `register_date` datetime NOT NULL,
  `tg_id` bigint(20) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `default_pathfinder_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `default_pathfinder_id_UNIQUE` (`default_pathfinder_id`),
  UNIQUE KEY `tg_id_UNIQUE` (`tg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
