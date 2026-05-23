CREATE TABLE IF NOT EXISTS users (
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(50)  UNIQUE,
    phone         VARCHAR(20)  UNIQUE,
    email         VARCHAR(100) UNIQUE,
    nickname      VARCHAR(50),
    password_hash VARCHAR(255),
    avatar        VARCHAR(255) DEFAULT '',
    is_vip        BOOLEAN      DEFAULT FALSE,
    vip_plan      VARCHAR(20),
    vip_expire_date TIMESTAMP,
    role          VARCHAR(20)  DEFAULT 'USER',
    created_at    TIMESTAMP    DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS manuals (
    id           BIGSERIAL PRIMARY KEY,
    title        VARCHAR(200) NOT NULL,
    dynasty      VARCHAR(20),
    author       VARCHAR(50),
    category     VARCHAR(20),
    category_name VARCHAR(50),
    difficulty   INT          DEFAULT 1,
    is_premium   BOOLEAN      DEFAULT FALSE,
    description  VARCHAR(500),
    content      TEXT         DEFAULT '[]',
    total_moves  INT          DEFAULT 0,
    view_count   INT          DEFAULT 0,
    created_at   TIMESTAMP    DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS study_records (
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT    NOT NULL,
    manual_id  BIGINT    NOT NULL,
    progress   INT       DEFAULT 0,
    studied_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS favorites (
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT    NOT NULL,
    manual_id  BIGINT    NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    UNIQUE (user_id, manual_id)
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT         NOT NULL,
    plan        VARCHAR(20),
    start_date  TIMESTAMP,
    expire_date TIMESTAMP,
    price       DECIMAL(10, 2),
    status      VARCHAR(20) DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS tournaments (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(200) NOT NULL,
    year       VARCHAR(10),
    location   VARCHAR(100),
    champion   VARCHAR(100),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS tournament_games (
    id            BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL,
    round         VARCHAR(50),
    red_player    VARCHAR(100),
    black_player  VARCHAR(100),
    result        VARCHAR(20),
    opening       VARCHAR(100),
    date          VARCHAR(20),
    moves         TEXT DEFAULT '[]'
);

CREATE TABLE IF NOT EXISTS players (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    title      VARCHAR(100),
    era        VARCHAR(100),
    era_class  VARCHAR(20),
    birth_year VARCHAR(50),
    bio        TEXT,
    manuals    TEXT DEFAULT '[]'
);

CREATE TABLE IF NOT EXISTS news (
    id       BIGSERIAL PRIMARY KEY,
    category VARCHAR(20),
    title    VARCHAR(300) NOT NULL,
    summary  TEXT,
    source   VARCHAR(100),
    date     VARCHAR(20),
    content  TEXT
);

CREATE TABLE IF NOT EXISTS my_manuals (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    title       VARCHAR(200) NOT NULL,
    type        VARCHAR(20) DEFAULT 'custom',
    difficulty  INT DEFAULT 3,
    red_player  VARCHAR(100),
    black_player VARCHAR(100),
    game_date   VARCHAR(20),
    moves       TEXT,
    remark      TEXT,
    created_at  TIMESTAMP DEFAULT NOW()
);
