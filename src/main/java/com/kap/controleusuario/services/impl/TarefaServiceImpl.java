package com.kap.controleusuario.services.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.repositories.TarefaRepository;
import com.kap.controleusuario.services.TarefaService;
import com.kap.controleusuario.utils.Validacao;

@Service
public class TarefaServiceImpl implements TarefaService {
	
	private static final Logger log = LoggerFactory.getLogger(TarefaServiceImpl.class);
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private Validacao validacao;
	
	@Override
	public List<Tarefa> listarTarefasPorUsuarioId(Long id) throws NotFoundException {
		
		List<Tarefa> tarefa = this.tarefaRepository.findByUsuarioId(id);
		
		if (tarefa.isEmpty()) {
			throw new NotFoundException("Não possui tarefa atrelada ao Usuario");
		}
		
		return tarefa;
	}
	
	public Tarefa salvarTarefa(Tarefa tarefa) {
		
		return this.tarefaRepository.save(tarefa);
	}

	@Override
	public List<Tarefa> listarTarefasPorUsuarioMatricula(Long matricula) throws NotFoundException {
		
		Long usuario_id = this.validacao.usuarioPorMatricula(matricula);
		
		log.debug(usuario_id.toString());
		
		if(usuario_id.toString().isEmpty()) {
			throw new NotFoundException("Usuario inexistente");
		}
		
		List<Tarefa> tarefa = this.tarefaRepository.findByUsuarioId(usuario_id);
		
		if (tarefa.isEmpty()) {
			throw new NotFoundException("Não possui tarefa atrelada ao Usuario");
		}
		
		return tarefa;
	}
	
	

}
