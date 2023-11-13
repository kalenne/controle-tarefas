ALTER TABLE tarefa
  DROP COLUMN status,
  DROP COLUMN codigo;
  
ALTER TABLE usuario
  DROP COLUMN status;

DELETE FROM flyway_schema_history WHERE version=2