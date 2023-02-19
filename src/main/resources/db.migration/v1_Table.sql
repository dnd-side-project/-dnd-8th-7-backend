CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(45),
    nickname VARCHAR(45),
    introduction VARCHAR(45),
    user_lock BOOLEAN,
    roles VARCHAR(10),
    img_path VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS block (
    id SERIAL PRIMARY KEY,
    block_lock BOOLEAN,
    save BOOLEAN,
    title VARCHAR(45),
    block_color VARCHAR(10),
    datetime DATE,
    emotion VARCHAR(45),
    user_id INTEGER REFERENCES users
);

CREATE TABLE IF NOT EXISTS task (
    id SERIAL PRIMARY KEY,
    contents VARCHAR(100),
    status BOOLEAN,
    block_id INTEGER REFERENCES block
);
