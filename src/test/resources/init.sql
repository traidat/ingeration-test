CREATE TABLE IF NOT EXISTS account (
    id             bigserial
    constraint account_pk
    primary key,
    username text,
    name text
);
INSERT INTO account (username, name) VALUES ('truongvq', 'truong');
