DROP TABLE IF EXISTS injection.users CASCADE;

CREATE TABLE injection.users
(
    id bigint NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default",
    surname character varying COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE injection.users
    OWNER to admin;

DROP TABLE IF EXISTS injection.user_roles CASCADE;

CREATE TABLE injection.user_roles
(
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    role character varying COLLATE pg_catalog."default",
    CONSTRAINT user_roles_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE injection.user_roles
    OWNER to admin;

ALTER TABLE injection.user_roles
    ADD CONSTRAINT "FK_USER_ID" FOREIGN KEY (user_id)
    REFERENCES injection.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;
    
DROP TABLE IF EXISTS injection.user_details CASCADE;

CREATE TABLE injection.user_details
(
    id UUID NOT NULL,
    user_id bigint NOT NULL,
    address character varying COLLATE pg_catalog."default" NOT NULL,
    credit_card_no character varying COLLATE pg_catalog."default",
    comment character varying COLLATE pg_catalog."default",
    CONSTRAINT user_detail_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE injection.user_details
    OWNER to admin;

ALTER TABLE injection.user_details
    ADD CONSTRAINT "FK_USERNAME" FOREIGN KEY (user_id)
    REFERENCES injection.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID;    