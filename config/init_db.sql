CREATE TABLE resume
(
    uuid      VARCHAR PRIMARY KEY NOT NULL,
    full_name TEXT                 NOT NULL
);

CREATE TABLE contact
(
    id          SERIAL,
    resume_uuid VARCHAR NOT NULL REFERENCES resume (uuid) ON DELETE CASCADE,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL
);
CREATE UNIQUE INDEX contact_uuid_type_index
    ON contact (resume_uuid, type);

create table text
(
    id          serial  not null,
    resume_uuid varchar not null
        constraint text_resume_uuid_fkey
            references resume
            on delete cascade,
    type        text    not null,
    value       text    not null
);
create unique index text_uuid_type_index
    on text (resume_uuid, type);
