CREATE TABLE IF NOT EXISTS member (
    member_id SERIAL NOT NULL,
    email VARCHAR(45) NULL,
    password VARCHAR(100) NULL,
    PRIMARY KEY(member_id)
);