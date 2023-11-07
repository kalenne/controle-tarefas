package com.kap.controleusuario.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kap.controleusuario.entities.Tarefa;

@Transactional(readOnly = true)
@Repository

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

	List<Tarefa> findByUsuarioId(Long id);

	Optional<Tarefa> findByCodigo(String codigo);

}
