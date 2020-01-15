CREATE TABLE participant
(
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    rfid          BIGINT    UNIQUE          NOT NULL ,
    start_number  BIGINT    UNIQUE          NOT NULL,
    first_name    TEXT                      NOT NULL,
    last_name     TEXT                      NOT NULL
);

CREATE TABLE event
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    participant_id  BIGINT                NOT NULL REFERENCES participant,
    reader_id       BIGINT                NOT NULL,
    time            TIMESTAMP             NOT NULL,
    UNIQUE (participant_id, reader_id)
);
