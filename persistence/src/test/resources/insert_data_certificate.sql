INSERT INTO gift_certificate (name,description,price,duration,create_date,last_update_date,is_active) VALUES ('Id Incorporated','nec ante. Maecenas mi felis, adipiscing fringilla,','0.04',17,'2021-03-21T02:07:05-07:00','2021-09-14T12:00:10-07:00',true),('Tellus Lorem Industries','orci. Donec nibh. Quisque nonummy ipsum','29.24',44,'2021-03-12T08:38:50-08:00','2020-10-20T00:49:43-07:00',false);
INSERT INTO tag(name) VALUES('IT');
INSERT INTO tag(name) VALUES('Hello');
INSERT INTO tag_has_gift_certificate(tag_id,gift_certificate_id) VALUES(1,1);
INSERT INTO tag_has_gift_certificate(tag_id,gift_certificate_id) VALUES(2,2);

