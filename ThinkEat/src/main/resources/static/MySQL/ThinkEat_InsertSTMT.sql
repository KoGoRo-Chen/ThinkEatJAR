-- Inserting data into `Comment` table
INSERT INTO `thinkeat`.`Comment` (`cmtContext`) VALUES ('Great place, loved the food!');
INSERT INTO `thinkeat`.`Comment` (`cmtContext`) VALUES ('The service was a bit slow.');

-- Inserting data into `restdata` table
INSERT INTO `thinkeat`.`restdata` (`resname`, `resaddress`) VALUES ('Sunshine Cafe', '123 Sunny Street');
INSERT INTO `thinkeat`.`restdata` (`resname`, `resaddress`) VALUES ('Moonlight Diner', '456 Moon Ave');

-- Inserting data into `price` table
INSERT INTO `thinkeat`.`price` (`pricename`) VALUES ('Economical');
INSERT INTO `thinkeat`.`price` (`pricename`) VALUES ('Expensive');

-- Inserting data into `eatrepo` table
INSERT INTO `thinkeat`.`eatrepo` (`resId`, `eatTitle`, `eatdate`, `priceId`, `eatrepo`) VALUES (1, 'Lunch at Sunshine', '2024-01-15', 1, 'Delicious and affordable lunch options');
INSERT INTO `thinkeat`.`eatrepo` (`resId`, `eatTitle`, `eatdate`, `priceId`, `eatrepo`) VALUES (2, 'Dinner at Moonlight', '2024-01-16', 2, 'Fine dining experience with a romantic atmosphere');

-- Inserting data into `eatrepo_ref_cmt` table
INSERT INTO `thinkeat`.`eatrepo_ref_cmt` (`eatRepoId`, `cmtId`) VALUES (1, 1);
INSERT INTO `thinkeat`.`eatrepo_ref_cmt` (`eatRepoId`, `cmtId`) VALUES (2, 2);

-- Inserting data into `pic` table
INSERT INTO `thinkeat`.`pic` (`picFilePath`) VALUES ('/images/sunshine_lunch.jpg');
INSERT INTO `thinkeat`.`pic` (`picFilePath`) VALUES ('/images/moonlight_dinner.jpg');

-- Inserting data into `eatrepo_ref_pic` table
INSERT INTO `thinkeat`.`eatrepo_ref_pic` (`eatRepoId`, `picId`) VALUES (1, 1);
INSERT INTO `thinkeat`.`eatrepo_ref_pic` (`eatRepoId`, `picId`) VALUES (2, 2);

-- Inserting data into `tag` table
INSERT INTO `thinkeat`.`tag` (`tagName`) VALUES ('Cozy');
INSERT INTO `thinkeat`.`tag` (`tagName`) VALUES ('Romantic');

-- Inserting data into `eatrepo_ref_tag` table
INSERT INTO `thinkeat`.`eatrepo_ref_tag` (`eatRepoId`, `tagId`) VALUES (1, 1);
INSERT INTO `thinkeat`.`eatrepo_ref_tag` (`eatRepoId`, `tagId`) VALUES (2, 2);

-- Inserting data into `favlist` table
INSERT INTO `thinkeat`.`favlist` (`favListName`) VALUES ('My Favorite Restaurants');
INSERT INTO `thinkeat`.`favlist` (`favListName`) VALUES ('Weekend Dine-Outs');

-- Inserting data into `favlist_ref_eatrepo` table
INSERT INTO `thinkeat`.`favlist_ref_eatrepo` (`favListId`, `eatRepoId`) VALUES (1, 1);
INSERT INTO `thinkeat`.`favlist_ref_eatrepo` (`favListId`, `eatRepoId`) VALUES (2, 2);

-- Inserting data into `level` table
INSERT INTO `thinkeat`.`level` (`levelName`) VALUES ('Beginner');
INSERT INTO `thinkeat`.`level` (`levelName`) VALUES ('Expert');

-- Inserting data into `service` table
INSERT INTO `thinkeat`.`service` (`serviceName`) VALUES ('Delivery');
INSERT INTO `thinkeat`.`service` (`serviceName`) VALUES ('Reservation');

-- Inserting data into `level_ref_service` table
INSERT INTO `thinkeat`.`level_ref_service` (`levelId`, `serviceId`) VALUES (1, 1);
INSERT INTO `thinkeat`.`level_ref_service` (`levelId`, `serviceId`) VALUES (2, 2);

-- Inserting data into `user` table
INSERT INTO `thinkeat`.`user` (`levelId`, `nickName`, `useraccount`, `password`) VALUES (1, 'Foodie123', 'user1@example.com', 'password123');
INSERT INTO `thinkeat`.`user` (`levelId`, `nickName`, `useraccount`, `password`) VALUES (2, 'GourmetExpert', 'user2@example.com', 'password456');

-- Inserting data into `user_ref_eatrepo` table
INSERT INTO `thinkeat`.`user_ref_eatrepo` (`userId`, `eatRepoId`) VALUES (1, 1);
INSERT INTO `thinkeat`.`user_ref_eatrepo` (`userId`, `eatRepoId`) VALUES (2, 2);

-- Inserting data into `user_ref_favlist` table
INSERT INTO `thinkeat`.`user_ref_favlist` (`userId`, `favListId`) VALUES (1, 1);
INSERT INTO `thinkeat`.`user_ref_favlist` (`userId`, `favListId`) VALUES (2, 2);
