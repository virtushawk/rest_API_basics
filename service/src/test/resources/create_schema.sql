CREATE TABLE IF NOT EXISTS `gift_certificate` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(150) NOT NULL,
    `description` TEXT NULL DEFAULT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `duration` INT NOT NULL,
    `create_date` DATETIME NOT NULL,
    `last_update_date` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)

CREATE TABLE IF NOT EXISTS `tag` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(150) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
    UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)

CREATE TABLE IF NOT EXISTS `tag_has_gift_certificate` (
    `tag_id` INT NOT NULL,
    `gift_certificate_id` INT NOT NULL,
     PRIMARY KEY (`tag_id`, `gift_certificate_id`),
    INDEX `fk_tag_has_gift_certificate_gift_certificate1_idx` (`gift_certificate_id` ASC) VISIBLE,
    INDEX `fk_tag_has_gift_certificate_tag_idx` (`tag_id` ASC) VISIBLE,
    CONSTRAINT `fk_tag_has_gift_certificate_gift_certificate1`
    FOREIGN KEY (`gift_certificate_id`)
    REFERENCES `gift_certificate` (`id`),
    CONSTRAINT `fk_tag_has_gift_certificate_tag`
    FOREIGN KEY (`tag_id`)
    REFERENCES `tag` (`id`))