package com.kap.controleusuario.utils;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.AlreadyExistsException;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.repositories.TarefaRepository;
import com.kap.controleusuario.repositories.UsuarioRepository;

@Component
public class Validacao {
	
	private static final Logger log = LoggerFactory.getLogger(Validacao.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TarefaRepository tarefaRepository;
		
	public long gerarMatriculaUsuarioValidacao() {
		Random valor = new Random();
		long matricula = (long) valor.nextInt(900_000) + 100_000;
		
		Optional<Usuario> usuario = this.usuarioRepository.findByMatricula(matricula);
		
		if(usuario.isPresent()) {
			gerarMatriculaUsuarioValidacao();
		}	
		
		return matricula;		
	}
	
	public String usuarioValidaEmail(String email) throws AlreadyExistsException {
		
		Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email);
		
		if(usuario.isPresent()) {
			throw new AlreadyExistsException("E-mail ja cadastrado");
		}
				
		return email;
	}
	
	public Usuario usuarioPorMatricula(Long matricula) throws NotFoundException {
		
		Optional<Usuario> usuario = this.usuarioRepository.findByMatricula(matricula);
		
		if(!usuario.isPresent()) {
			throw new NotFoundException("Usuario inexistente");
		}
		
		return usuario.get();
	}
	
	public String gerarCodigo() {
		Random random = new Random();
		String codigo = "X" + (10000 + random.nextInt(90000));
		
		Optional<Tarefa> tarefa = this.tarefaRepository.findByCodigo(codigo);
		
		if(tarefa.isPresent()) {
			gerarCodigo();
		}
		return codigo;
	}
	

}
