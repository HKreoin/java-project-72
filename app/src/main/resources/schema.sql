DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    created_at TIMESTAMP
);

CREATE TABLE url_checks (
    id SERIAL PRIMARY KEY,
    status_code INTEGER,
    title VARCHAR(256),
    h1 VARCHAR(256),
    description TEXT,
    url_id BIGINT REFERENCES urls (id),
    created_at TIMESTAMP
);

