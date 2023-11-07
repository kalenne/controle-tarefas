package com.kap.controleusuario.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.repositories.UsuarioRepository;
import com.kap.controleusuario.services.UsuarioService;
import com.kap.controleusuario.utils.Validacao;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private Validacao validacao;

	@Override
	public Optional<Usuario> buscarPorMatricula(Long matricula) throws NotFoundException {
		
		Usuario usuario = this.usuarioRepository.findByMatricula(matricula);
		
		if(usuario == null) {
			throw new NotFoundException("Usuario inexistente");
		}
		
		return Optional.ofNullable(usuario) ;
	}

	@Override
	public Optional<Usuario> buscarPorEmail(String email) throws NotFoundException{
		
		Usuario usuario = this.usuarioRepository.findByEmail(email);
		
		if(usuario == null) {
			throw new NotFoundException("Usuario inexistente");
		}
		
		return Optional.ofNullable(usuario);
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {		
		usuario.setMatricula(validacao.gerarMatriculaUsuarioValidacao());
		usuario.setEmail(validacao.usuarioValidaEmail(usuario.getEmail()));
				
		return this.usuarioRepository.save(usuario);
	}
	
	
}
