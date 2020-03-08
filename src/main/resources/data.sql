INSERT INTO `person` (`dtype`, `id`, `name`, `cnpj`, `cpf`) VALUES ('Physical', 1, 'Pedro alfredo', NULL, '321.123.123-53');

INSERT INTO `account` (`id`, `balance`, `close_date`, `number`, `open_date`, `password`, `person_id`) VALUES (1, 0, NULL, 22222223, '2020-02-01 22:08:54', '472c782e2ea863cf9c98efe3984f908a', 1);

INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-01 22:08:54', 1, 10, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-02 22:08:54', 2, 5, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-03 22:08:54', 1, 20, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-04 22:08:54', 2, 10, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-05 22:08:54', 2, 5, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-06 22:08:54', 1, 10, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-07 22:08:54', 1, 10, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-08 22:08:54', 2, 5, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-09 22:08:54', 1, 25, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-10 22:08:54', 2, 5, 1);
INSERT INTO `movement` (`date`, `type`, `value`, `account_id`) VALUES ('2020-02-11 22:08:54', 2, 5, 1);

UPDATE `account` SET `balance` = (SELECT SUM( IF(type = 1 , `value` , - `value`) ) FROM `movement` WHERE `account_id` = 1) WHERE `id` = 1;