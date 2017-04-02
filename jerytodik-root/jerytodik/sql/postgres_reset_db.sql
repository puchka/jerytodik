
CREATE SEQUENCE seq_jt_source;
CREATE TABLE jt_source
(
  jt_source_id bigint NOT NULL DEFAULT nextval('seq_jt_source'::regclass),
  jt_source_name character varying(255) NOT NULL,
  jt_source_description character varying(2000),
  jt_source_url character varying(255) NOT NULL,
  jt_source_activated BOOLEAN DEFAULT FALSE,
  jt_source_start_date DATE,
  CONSTRAINT jt_source_pkey PRIMARY KEY (jt_source_id)
)
WITH (
  OIDS=FALSE
);