CREATE TABLE IF NOT EXISTS users (
    email TEXT PRIMARY KEY,
    nickname TEXT,
    roles TEXT,
    introduction TEXT,
    user_lock BOOLEAN,
    img_path TEXT
);

CREATE TABLE IF NOT EXISTS block (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    block_lock BOOLEAN,
    title TEXT,
    block_color TEXT,
    datetime DATE,
    emotion TEXT,
    user_email TEXT REFERENCES users(email)
);

CREATE TABLE IF NOT EXISTS "keep" (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_email TEXT REFERENCES users(email),
    block_id BIGINT REFERENCES block(id),
    title TEXT,
    block_color TEXT,
    emotion TEXT,
    sum_of_task INTEGER
);

CREATE TABLE IF NOT EXISTS review (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_email TEXT REFERENCES users(email),
    datetime DATE,
    retrospection TEXT,
    retrospection_lock BOOLEAN,
    emotion TEXT
);

CREATE TABLE IF NOT EXISTS task (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    contents TEXT,
    status BOOLEAN,
    block_id BIGINT REFERENCES block(id)
);

