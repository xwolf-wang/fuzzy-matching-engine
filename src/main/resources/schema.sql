DROP TABLE IF EXISTS properties;
-- DROP TABLE IF EXISTS rule_tbl;
CREATE TABLE properties
(
  application  VARCHAR(50),
  profile      VARCHAR(50),
  label      VARCHAR(50),
  key       VARCHAR(50),
  value     VARCHAR(500)
);

-- create table rule_tbl (
--    id integer not null,
--     left_field varchar(255),
--     left_source_type varchar(255),
--     right_field varchar(255),
--     right_source_type varchar(255),
--     source_system varchar(255),
--     primary key (id)
-- )
