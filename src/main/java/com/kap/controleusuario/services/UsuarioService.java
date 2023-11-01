package com.kap.controleusuario.services;

import java.util.Optional;

import com.kap.controleusuario.entities.Usuario;

public interface UsuarioService {
	
	Optional<Usuario> buscarPorMatricula(Long matricula);
	
	Optional<Usuario> buscarPorEmail (String email);
	
	Usuario salvarUsuario (Usuario usuario);

}
