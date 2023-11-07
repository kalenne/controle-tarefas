package com.kap.controleusuario.services;

import java.util.Optional;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.utils.TipoStatus.UsuarioStatus;

public interface UsuarioService {
	
	Optional<Usuario> buscarPorMatricula(Long matricula) throws NotFoundException;
	
	Optional<Usuario> buscarPorEmail (String email)throws NotFoundException;
	
	Usuario salvarUsuario (Usuario usuario);
	
	Optional<Usuario> editarUsuarioPorEmail(Usuario usuario) throws NotFoundException;
	
	Optional<Usuario> editarUsuarioPorMatricula(Long matricula, Usuario usuario) throws NotFoundException;
	
	Optional<Usuario> exclusaoUsuario (Long matricula) throws NotFoundException;
	
	UsuarioStatus[] status();

}
