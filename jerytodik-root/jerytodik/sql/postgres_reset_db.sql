
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

CREATE SEQUENCE seq_jt_history;
CREATE TABLE jt_history
(
  jt_history_id bigint NOT NULL DEFAULT nextval('seq_jt_history'::regclass),
  jt_history_content character varying(100) NOT NULL,
  jt_history_date DATE,
  js_history_source_id bigint 
  CONSTRAINT jt_history_pkey PRIMARY KEY (jt_history_id)
)
WITH (
  OIDS=FALSE
);
alter table jt_history 
	add foreign key(js_history_source_id) references jt_source(jt_source_id);