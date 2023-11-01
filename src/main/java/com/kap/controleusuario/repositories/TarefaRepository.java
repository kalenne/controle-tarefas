package com.kap.controleusuario.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kap.controleusuario.entities.Tarefa;

@Transactional(readOnly = true)
@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
	
	//@Query("SELECT t.descricao, t.data_criacao FROM tarefa as t "
			//+ "WHERE t.usuario_id = :id")
	List<Tarefa> findByUsuarioId (Long id);
	

}
