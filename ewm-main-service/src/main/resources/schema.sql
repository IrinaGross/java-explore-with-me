CREATE TABLE IF NOT EXISTS users(
    user_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    user_name VARCHAR(250) NOT NULL,
    user_email VARCHAR(254) NOT NULL,
    CONSTRAINT PK_USER PRIMARY KEY (user_id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (user_email)
);

CREATE TABLE IF NOT EXISTS categories(
    category_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    category_name VARCHAR(50) NOT NULL,
    CONSTRAINT PK_CATEGORIES PRIMARY KEY (category_id),
    CONSTRAINT UQ_CATEGORIES_NAME UNIQUE (category_name)
);

CREATE TYPE IF NOT EXISTS event_state_enum AS ENUM ('PENDING', 'CANCELED', 'PUBLISHED');

CREATE TABLE IF NOT EXISTS events(
    event_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    event_title VARCHAR(120) NOT NULL,
    event_annotation VARCHAR(2000) NOT NULL,
    event_description VARCHAR(7000) NOT NULL,
    event_created_date TIMESTAMP NOT NULL,
    event_date TIMESTAMP NOT NULL,
    event_latitude FLOAT NOT NULL,
    event_longitude FLOAT NOT NULL,
    event_limit INT NOT NULL,
    event_paid BOOLEAN NOT NULL,
    event_need_moderation_requests BOOLEAN NOT NULL,
    event_state event_state_enum NOT NULL,
    event_published_date TIMESTAMP,
    event_initiator_id BIGINT NOT NULL,
    event_category_id BIGINT NOT NULL,
    CONSTRAINT PK_EVENTS PRIMARY KEY (event_id),
    CONSTRAINT FK_EVENT_INITIATOR FOREIGN KEY (event_initiator_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT FK_EVENT_CATEGORY FOREIGN KEY (event_category_id)
        REFERENCES categories(category_id)
        ON DELETE RESTRICT
);

CREATE TYPE IF NOT EXISTS request_status_enum AS ENUM ('PENDING', 'CONFIRMED', 'REJECTED', 'CANCELED');

CREATE TABLE IF NOT EXISTS requests(
    request_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    request_created_date TIMESTAMP NOT NULL,
    request_event_id BIGINT NOT NULL,
    request_user_id BIGINT NOT NULL,
    request_status request_status_enum NOT NULL,
    CONSTRAINT PK_REQUESTS PRIMARY KEY (request_id),
    CONSTRAINT FK_REQUEST_USER FOREIGN KEY (request_user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT FK_REQUEST_EVENT FOREIGN KEY (request_event_id)
        REFERENCES events(event_id)
        ON DELETE CASCADE
);