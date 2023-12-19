package com.kap.controleusuario.services;

import java.util.List;
import java.util.Optional;

import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.enums.Prioridade;
import com.kap.controleusuario.enums.TipoStatus;
import com.kap.controleusuario.exception.NotFoundException;

public interface TarefaService {
	
	List<Tarefa> listarTarefasPorUsuarioMatricula(Long matricula) throws NotFoundException;
	
	Tarefa salvarTarefa(Tarefa tarefa);
	
	Optional<Tarefa> editarTarefaPorCodigo(String codigo, Tarefa tarefa) throws NotFoundException;
	
	List<Tarefa> listarTarefasAtivasUsuario(Long matricula) throws NotFoundException;
	
	TipoStatus[] status();
	
	Prioridade[] prioridade();
	
}
