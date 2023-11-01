package com.kap.controleusuario.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.repositories.TarefaRepository;
import com.kap.controleusuario.repositories.UsuarioRepository;
import com.kap.controleusuario.services.TarefaService;
import com.kap.controleusuario.services.UsuarioService;

@Service
public class TarefaServiceImpl implements TarefaService {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public List<Tarefa> listarTarefasPorUsuarioId(Long id) {
		
		List<Tarefa> tarefa = this.tarefaRepository.findByUsuarioId(id);
		
		if (tarefa.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		
		return tarefa;
	}
	
	public Tarefa salvarTarefa(Tarefa tarefa) {
		return this.tarefaRepository.save(tarefa);
	}

	@Override
	public List<Tarefa> listarTarefasPorUsuarioMatricula(Long matricula) {
		
		Usuario usuario = this.usuarioRepository.findByMatricula(matricula);
		
		System.out.println(usuario);
		
		List<Tarefa> tarefa = this.tarefaRepository.findByUsuarioId(usuario.getId());
		
		if (tarefa.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		
		return tarefa;
	}
	
	

}
