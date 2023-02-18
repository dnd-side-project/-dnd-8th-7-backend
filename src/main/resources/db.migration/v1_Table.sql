CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(45),
    nickname VARCHAR(45),
    introduction VARCHAR(45),
    user_lock BOOLEAN
);

CREATE TABLE IF NOT EXISTS block (
    id SERIAL PRIMARY KEY,
    block_lock BOOLEAN,
    save BOOLEAN,
    title VARCHAR(45),
    block_color VARCHAR(10),
    date DATE,
    emotion VARCHAR(45),
    user_id INTEGER REFERENCES "user"
);

CREATE TABLE IF NOT EXISTS task (
    id SERIAL PRIMARY KEY,
    task VARCHAR(100),
    status BOOLEAN,
    block_id INTEGER REFERENCES block

);
