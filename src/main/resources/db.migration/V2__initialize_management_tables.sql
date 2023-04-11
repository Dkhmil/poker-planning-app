CREATE TABLE poker_planning_sessions
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title     VARCHAR(255)                            NOT NULL,
    deck_type VARCHAR(255)                            NOT NULL,
    link      VARCHAR(255),
    CONSTRAINT pk_poker_planning_sessions PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name       VARCHAR(255)                            NOT NULL,
    session_id BIGINT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_SESSION FOREIGN KEY (session_id) REFERENCES poker_planning_sessions (id);

CREATE TABLE user_stories
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description VARCHAR(255)                            NOT NULL,
    status      VARCHAR(255)                            NOT NULL,
    session_id  BIGINT,
    CONSTRAINT pk_user_stories PRIMARY KEY (id)
);

ALTER TABLE user_stories
    ADD CONSTRAINT FK_USER_STORIES_ON_SESSION FOREIGN KEY (session_id) REFERENCES poker_planning_sessions (id);

CREATE TABLE votes
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    vote_option   INT                                     NOT NULL,
    user_id       BIGINT,
    user_story_id BIGINT,
    CONSTRAINT pk_votes PRIMARY KEY (id)
);

ALTER TABLE votes
    ADD CONSTRAINT FK_VOTES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE votes
    ADD CONSTRAINT FK_VOTES_ON_USER_STORY FOREIGN KEY (user_story_id) REFERENCES user_stories (id);

insert
into poker_planning_sessions
    (id, deck_type, link, title)
values (4, 'STANDARD', null, 'some title');

insert
into users
    (name)
values ('Dmytro');