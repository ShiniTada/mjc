CREATE TABLE `gift_certificate`
(
    `id`               BIGINT         NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(50)    NOT NULL,
    `description`      VARCHAR(250)   NOT NULL,
    `price`            DECIMAL(10, 0) NOT NULL,
    `duration`         INT            NOT NULL,
    `create_date`      TIMESTAMP NULL,
    `last_update_date` TIMESTAMP NULL,
    PRIMARY KEY (`id`)
)
;


-- -----------------------------------------------------
-- Table  `tag`
-- -----------------------------------------------------
CREATE TABLE `tag`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`)
)
;


-- -----------------------------------------------------
-- Table `tag_certificate`
-- -----------------------------------------------------
CREATE TABLE `tag_certificate`
(
    `id_tag_certificate` BIGINT NOT NULL AUTO_INCREMENT,
    `id_certificate`     BIGINT NOT NULL,
    `id_tag`             BIGINT NOT NULL,
    PRIMARY KEY (`id_tag_certificate`),

    CONSTRAINT `gift_certificate`
        FOREIGN KEY (`id_certificate`)
            REFERENCES `gift_certificate` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `tag`
        FOREIGN KEY (`id_tag`)
            REFERENCES `tag` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
;
-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
create table user
(
    id       bigint auto_increment
        primary key,
    email    varchar(70) not null,
    password varchar(20) not null,
    role     enum ('USER', 'ADMIN') default 'USER' not null,
    constraint email
        unique (email)
);
-- -----------------------------------------------------
-- Table `order_user`
-- -----------------------------------------------------
create table order_user
(
    id             bigint auto_increment primary key,
    price          decimal   not null,
    order_time     timestamp not null,
    id_user        bigint    not null,
    id_certificate bigint    not null,
    foreign key (id_user) references user (id),
    foreign key (id_certificate) references gift_certificate (id)
);