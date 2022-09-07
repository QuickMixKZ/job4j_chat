CREATE TABLE roles(
   id SERIAL PRIMARY KEY,
   name VARCHAR(100) UNIQUE
);
CREATE TABLE users(
   id SERIAL PRIMARY KEY,
   username VARCHAR(100) UNIQUE NOT NULL,
   password VARCHAR(100)
);
CREATE TABLE users_roles(
   user_id INT REFERENCES users(id),
   role_id INT REFERENCES roles(id)
);

create table rooms(
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE,
    created TIMESTAMP not null default now(),
    creator_id INT REFERENCES users(id)
);

CREATE TABLE messages(
   id SERIAL PRIMARY KEY,
   text VARCHAR(1000),
   created TIMESTAMP DEFAULT now(),
   room_id INT REFERENCES rooms(id),
   author_id INT REFERENCES users(id)
);

create table rooms_messages(
   room_id INT REFERENCES rooms(id),
   message_id INT REFERENCES messages(id)
);