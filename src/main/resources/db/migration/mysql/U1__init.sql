ALTER TABLE tarefa DROP CONSTRAINT fk_tarefa_usuario_id;

DROP TABLE tarefa;

DROP TABLE usuario;


DELETE FROM flyway_schema_history WHERE version=1