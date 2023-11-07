package com.kap.controleusuario.services;

import java.util.Optional;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.NotFoundException;

public interface UsuarioService {
	
	Optional<Usuario> buscarPorMatricula(Long matricula) throws NotFoundException;
	
	Optional<Usuario> buscarPorEmail (String email)throws NotFoundException;
	
	Usuario salvarUsuario (Usuario usuario);

}
