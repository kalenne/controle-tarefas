package com.kap.controleusuario.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.security.JwtUsuario;
import com.kap.controleusuario.services.UsuarioService;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioService.buscarPorEmail(username);
		if (!usuario.isPresent()) {
			throw new UsernameNotFoundException("Usuario [" + username + "] nao encontrado" );
		}
		
		return new JwtUsuario(usuario);
	}
}