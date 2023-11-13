ALTER TABLE usuario
  DROP COLUMN cpf,
  DROP COLUMN data_nascimento;

DELETE FROM flyway_schema_history WHERE version=4