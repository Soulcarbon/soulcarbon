CREATE TABLE sys_role
(
  id bigserial NOT NULL,
  role_name character varying(255),
  class_name character varying(255),
  CONSTRAINT sys_role_pkey PRIMARY KEY (id)
);

CREATE TABLE sys_user
(
  id bigserial NOT NULL,
  enabled boolean NOT NULL,
  full_name character varying(255),
  login character varying(255),
  hash_pwd character varying(500),
  role_id bigint,
  CONSTRAINT sys_user_pkey PRIMARY KEY (id),
  CONSTRAINT fk_3xmp6q1rp7q1r7baucrlbf5l FOREIGN KEY (role_id)
      REFERENCES sys_role (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);