CREATE TABLE usuario (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    matricula bigint(20) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    roles VARCHAR(255)NOT NULL,
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE tarefa(
  id bigint(20) PRIMARY KEY AUTO_INCREMENT,
  usuario_id bigint(20) NOT NULL,
  descricao varchar(255) NOT NULL,
  data_criacao datetime NOT NULL
);

ALTER TABLE tarefa
  ADD CONSTRAINT fk_tarefa_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario (id);



  