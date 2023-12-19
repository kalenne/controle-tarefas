ALTER TABLE usuario
  DROP COLUMN titulo,
  DROP COLUMN criador_matricula,
  DROP COLUMN data_inicio,
  DROP COLUMN data_final,
  DROP COLUMN prioridade,
  DROP CONSTRAINT fk_autor_usuario_id;
  

DELETE FROM flyway_schema_history WHERE version=5