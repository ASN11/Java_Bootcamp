INSERT INTO users (login, password) VALUES ('alice', 'password123');
INSERT INTO users (login, password) VALUES ('bob', 'foobar');
INSERT INTO users (login, password) VALUES ('charlie', '123456');
INSERT INTO users (login, password) VALUES ('dave', 'pass123');
INSERT INTO users (login, password) VALUES ('eve', 'qwerty');

INSERT INTO chatrooms (chatroom_name, chatroom_owner) VALUES ('General', 1);
INSERT INTO chatrooms (chatroom_name, chatroom_owner) VALUES ('Project discussion', 1);
INSERT INTO chatrooms (chatroom_name, chatroom_owner) VALUES ('Off-topic', 2);
INSERT INTO chatrooms (chatroom_name, chatroom_owner) VALUES ('Day', 3);
INSERT INTO chatrooms (chatroom_name, chatroom_owner) VALUES ('Learning', 4);
INSERT INTO chatrooms (chatroom_name, chatroom_owner) VALUES ('Global', 5);

INSERT INTO messages (message_author, message_room, message_text, message_datetime) VALUES (1, 1, 'Hi', now());
INSERT INTO messages (message_author, message_room, message_text, message_datetime) VALUES (2, 2, 'Hey', now());
INSERT INTO messages (message_author, message_room, message_text, message_datetime) VALUES (3, 3, 'Aboba', now());
INSERT INTO messages (message_author, message_room, message_text, message_datetime) VALUES (4, 4, 'Mya', now());
INSERT INTO messages (message_author, message_room, message_text, message_datetime) VALUES (5, 5, 'Crya', now());
