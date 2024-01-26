INSERT INTO thinkeat.price (id, name) VALUES ('1', '100元以內');
INSERT INTO thinkeat.price (id, name) VALUES ('2', '100~200元');
INSERT INTO thinkeat.price (id, name) VALUES ('3', '200~300元');
INSERT INTO thinkeat.price (id, name) VALUES ('4', '300~400元');
INSERT INTO thinkeat.price (id, name) VALUES ('5', '400~500元');

INSERT INTO thinkeat.tag (id, name) VALUES ('1', '台式');
INSERT INTO thinkeat.tag (id, name) VALUES ('2', '手搖杯');
INSERT INTO thinkeat.tag (id, name) VALUES ('3', '會辣');
INSERT INTO thinkeat.tag (id, name) VALUES ('4', '早餐');
INSERT INTO thinkeat.tag (id, name) VALUES ('5', '消夜');
INSERT INTO thinkeat.tag (id, name) VALUES ('6', '簡餐');
INSERT INTO thinkeat.tag (id, name) VALUES ('7', '下午茶');
INSERT INTO thinkeat.tag (id, name) VALUES ('8', '日式');

INSERT INTO `thinkeat`.`authority` (`id`, `description`, `name`) VALUES ('1', '一般會員', 'standard_user');
INSERT INTO `thinkeat`.`authority` (`id`, `description`, `name`) VALUES ('2', '管理員', 'admin');
INSERT INTO `thinkeat`.`authority` (`id`, `description`, `name`) VALUES ('3', '創辦人', 'founder');

INSERT INTO `thinkeat`.`user` (`id`, `enabled`, `nickname`, `password`, `token_expired`, `username`) VALUES ('1', '1', '一般會員', '$2y$10$oTWA1mrN67z2niF7d8HOduSaQ70cBtUhcqfoywhTnFkF5cZ9VozRG', '0', 'testuser');
INSERT INTO `thinkeat`.`user` (`id`, `enabled`, `nickname`, `password`, `token_expired`, `username`) VALUES ('2', '1', '管理員', '$2y$10$jPJux4x4yu9ZJ8Bepb0jSeAlPaYjq.vbWxsOFh4VzrZGOmu4HkvqS', '0', 'admin');
INSERT INTO `thinkeat`.`user` (`id`, `enabled`, `nickname`, `password`, `token_expired`, `username`) VALUES ('3', '1', '創辦者', '$2y$10$DHUH5Ay5OdgFMwLIhcT.4u3XpQsbiDwFwlY.AKuh2ZLAKNWsif7su', '0', 'founder');

INSERT INTO `thinkeat`.`user_authority` (`id`, `authority_id`, `user_id`) VALUES ('1', '1', '1');
INSERT INTO `thinkeat`.`user_authority` (`id`, `authority_id`, `user_id`) VALUES ('2', '2', '2');
INSERT INTO `thinkeat`.`user_authority` (`id`, `authority_id`, `user_id`) VALUES ('3', '3', '3');