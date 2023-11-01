package com.kap.controleusuario.services;

import java.util.List;

import com.kap.controleusuario.entities.Tarefa;

public interface TarefaService {
	
	
	List<Tarefa> listarTarefasPorUsuarioId(Long id);
	
	List<Tarefa> listarTarefasPorUsuarioMatricula(Long matricula);
	
	Tarefa salvarTarefa(Tarefa tarefa);

}
