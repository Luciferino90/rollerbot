use rollerbot;


CREATE TABLE IF NOT EXISTS `telegram_user` (
  `id` bigint(20) NOT NULL,
  `tg_name` varchar(45),
  `tg_surname` varchar(45),
  `tg_username` varchar(45),
  `register_date` datetime NOT NULL,
  `tg_id` bigint(20) DEFAULT NULL,
  `email` varchar(45),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `pathfinder_pg` (
  `id` bigint(20) NOT NULL,
  `name` varchar(45),
  `telegram_user_id` bigint(20),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`telegram_user_id`) REFERENCES `telegram_user`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `levels` (
  `id` bigint(20),
  `pathfinder_pg_id` bigint(20),
  `level_number` bigint(20),
  `st_fortitude` bigint(20),
  `st_will` bigint(20),
  `st_reflex` bigint(20),
  `lvl_strength` bigint(20),
  `lvl_dexterity` bigint(20),
  `lvl_constitution` bigint(20),
  `lvl_intelligence` bigint(20),
  `lvl_wisdom` bigint(20),
  `lvl_charisma` bigint(20),
  `lf` bigint(20),
  `custom` text,
  PRIMARY KEY(`id`),
  FOREIGN KEY (`pathfinder_pg_id`) REFERENCES `pathfinder_pg`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `stats` (
  `id` bigint(20),
  `pathfinder_pg_id` bigint(20),
  `lvl_strength` bigint(20),
  `lvl_dexterity` bigint(20),
  `lvl_constitution` bigint(20),
  `lvl_intelligence` bigint(20),
  `lvl_wisdom` bigint(20),
  `lvl_charisma` bigint(20),
  FOREIGN KEY (`pathfinder_pg_id`) REFERENCES `pathfinder_pg`(`id`),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `custom_throws` (
  `id` bigint(20),
  `pathfinder_pg_id` bigint(20),
  `telegram_user_id` bigint(20),
  `custom_name` varchar(45),
  `custom_command` text,
  FOREIGN KEY (`telegram_user_id`) REFERENCES `telegram_user`(`id`),
  FOREIGN KEY (`pathfinder_pg_id`) REFERENCES `pathfinder_pg`(`id`),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `default_throws` (
  `id` bigint(20),
  `name` varchar(45),
  `command` text,
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `custom_variables` (
  `id` bigint(20),
  `pathfinder_pg_id` bigint(20),
  `telegram_user_id` bigint(20),
  `custom_name` varchar(45),
  `custom_value` varchar(45),
  FOREIGN KEY (`telegram_user_id`) REFERENCES `telegram_user`(`id`),
  FOREIGN KEY (`pathfinder_pg_id`) REFERENCES `pathfinder_pg`(`id`),
  PRIMARY KEY(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

