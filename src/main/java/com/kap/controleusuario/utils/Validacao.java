package com.kap.controleusuario.utils;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.AlreadyExistsException;
import com.kap.controleusuario.repositories.UsuarioRepository;

@Component
public class Validacao {
	
	private static final Logger log = LoggerFactory.getLogger(Validacao.class);

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario usuarioValidaMatriculaEmail(Usuario usuario) {
		
		return usuario;
	}
	
	public long gerarMatriculaUsuarioValidacao() {
		Random valor = new Random();
		long matricula = (long) valor.nextInt(900_000) + 100_000;
		
		Usuario usuario = this.usuarioRepository.findByMatricula(matricula);
		
		if(usuario != null) {
			gerarMatriculaUsuarioValidacao();
		}	
		
		return matricula;		
	}
	
	public String usuarioValidaEmail(String email) throws AlreadyExistsException {
		
		Usuario usuario = this.usuarioRepository.findByEmail(email);
		
		if (usuario != null) {
			throw new AlreadyExistsException("E-mail ja existente.");
		}
		
		return email;
	}
	
	public Long usuarioPorMatricula(Long matricula) {
		
		Usuario usuario = this.usuarioRepository.findByMatricula(matricula);
		
		return usuario.getId();
	}
	
	

}
