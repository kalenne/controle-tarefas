package com.kap.controleusuario.services.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.repositories.UsuarioRepository;
import com.kap.controleusuario.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public Optional<Usuario> buscarPorMatricula(Long matricula) {
		
		return Optional.ofNullable(this.usuarioRepository.findByMatricula(matricula)) ;
	}

	@Override
	public Optional<Usuario> buscarPorEmail(String email) {
		
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		
		Random matricula = new Random();
		
		usuario.setMatricula((long) matricula.nextInt(900_000) + 100_000);
		
		return this.usuarioRepository.save(usuario);
	}
		
}
