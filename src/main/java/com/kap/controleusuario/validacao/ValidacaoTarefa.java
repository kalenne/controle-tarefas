package com.kap.controleusuario.validacao;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.repositories.TarefaRepository;
import com.kap.controleusuario.repositories.UsuarioRepository;

@Component
public class ValidacaoTarefa {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public String gerarCodigoTarefa() {
		Random random = new Random();
		String codigo = "X" + (10000 + random.nextInt(90000));
		
		Optional<Tarefa> tarefa = this.tarefaRepository.findByCodigo(codigo);
		
		if(tarefa.isPresent()) {
			gerarCodigoTarefa();
		}
		return codigo;
	}
	
	public Long usuarioPorMatricula(Long matricula) {
		
		Optional<Usuario> usuario = this.usuarioRepository.findByMatricula(matricula);
		
		return usuario.get().getId();
	}
	
}
