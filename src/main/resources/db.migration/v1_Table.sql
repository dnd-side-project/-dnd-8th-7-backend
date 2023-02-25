CREATE TABLE IF NOT EXISTS users (
    email VARCHAR(45) PRIMARY KEY,
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
    user_email VARCHAR(45) REFERENCES users ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS task (
    id SERIAL PRIMARY KEY,
    contents VARCHAR(100),
    status BOOLEAN,
    block_id INTEGER REFERENCES block ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS review (
    id SERIAL PRIMARY KEY,
    datetime DATE,
    retrospection VARCHAR(100),
    retrospection_lock BOOLEAN,
    emotion VARCHAR(45),
    user_email VARCHAR(45) REFERENCES users ON DELETE CASCADE
);
