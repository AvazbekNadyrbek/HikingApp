-- Sequences (match @SequenceGenerator in User and Location entities)
CREATE SEQUENCE IF NOT EXISTS user_sequence
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS location_sequence
    START WITH 1
    INCREMENT BY 1;

-- Users table (name = "users" to avoid conflict with reserved word in PostgreSQL)
CREATE TABLE IF NOT EXISTS users (
    id       BIGINT       NOT NULL DEFAULT nextval('user_sequence') PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL
        CONSTRAINT users_role_check CHECK (role IN ('ROLE_ADMIN', 'ROLE_USER'))
);

-- Location table
CREATE TABLE IF NOT EXISTS location (
    id               BIGINT           NOT NULL DEFAULT nextval('location_sequence') PRIMARY KEY,
    name             VARCHAR(255)     NOT NULL,
    description      TEXT,
    latitude         DOUBLE PRECISION NOT NULL,
    longitude        DOUBLE PRECISION NOT NULL,
    altitude_meters  INTEGER,
    category         VARCHAR(50)      NOT NULL
        CONSTRAINT location_category_check CHECK (category IN ('HIKING', 'VIEWPOINT', 'BENCH', 'LAKE', 'WATERFALL', 'OTHER')),
    difficulty       VARCHAR(50)
        CONSTRAINT location_difficulty_check CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD')),
    distance_km      DOUBLE PRECISION,
    duration_minutes INTEGER,
    best_season      VARCHAR(50)
        CONSTRAINT location_season_check CHECK (best_season IN ('SPRING', 'SUMMER', 'AUTUMN', 'WINTER', 'ALL_YEAR')),
    is_active        BOOLEAN          NOT NULL DEFAULT TRUE,
    is_featured      BOOLEAN                   DEFAULT FALSE,
    tip_text         VARCHAR(300)
);
