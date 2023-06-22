CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       login VARCHAR(50) NOT NULL,
                       password VARCHAR(50) NOT NULL
);

CREATE TABLE chatrooms (
                           chatroom_id SERIAL PRIMARY KEY,
                           chatroom_name VARCHAR(100) NOT NULL,
                           chatroom_owner INTEGER NOT NULL REFERENCES users(user_id)
);

CREATE TABLE messages (
                          message_id SERIAL PRIMARY KEY,
                          message_author INTEGER NOT NULL REFERENCES users(user_id),
                          message_room INTEGER NOT NULL REFERENCES chatrooms(chatroom_id),
                          message_text TEXT NOT NULL,
                          message_datetime TIMESTAMP NOT NULL
);