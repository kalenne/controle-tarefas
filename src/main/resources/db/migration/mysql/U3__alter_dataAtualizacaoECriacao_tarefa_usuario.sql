ALTER TABLE tarefa
  DROP COLUMN data_atualizacao;
  
ALTER TABLE usuario
  DROP COLUMN data_criacao,
  DROP COLUMN data_atualizacao ;

DELETE FROM flyway_schema_history WHERE version=3