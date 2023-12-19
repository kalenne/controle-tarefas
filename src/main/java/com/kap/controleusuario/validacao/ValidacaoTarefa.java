package com.kap.controleusuario.validacao;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.BadRequestException;
import com.kap.controleusuario.exception.NotFoundException;
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
		
		if(!usuario.isPresent()) {
			throw new NotFoundException("Usuario inexistente na base de dados.");
		}
		
		return usuario.get().getId();
	}
	
	public boolean validarDataInicioFinal(LocalDateTime inicio, LocalDateTime fim) {
		
		if(!fim.isAfter(inicio)) {
			throw new BadRequestException("A data de fim não pode ser menor ou igual à data de início.");
		}
		
		return true;
	}
	
	 
	
}
