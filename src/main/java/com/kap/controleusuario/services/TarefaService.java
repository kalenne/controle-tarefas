package com.kap.controleusuario.services;

import java.util.List;

import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.exception.NotFoundException;

public interface TarefaService {

	List<Tarefa> listarTarefasPorUsuarioId(Long id) throws NotFoundException;
	
	List<Tarefa> listarTarefasPorUsuarioMatricula(Long matricula) throws NotFoundException;
	
	Tarefa salvarTarefa(Tarefa tarefa);

}
