package com.kap.controleusuario.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.enums.UserRoles;

public class JwtUserFactory {
	
	private JwtUserFactory() {
	}
	
	public static JwtUser create(Usuario usuario) {
		return new JwtUser(
				usuario.getId(), 
				usuario.getEmail(), 
				usuario.getSenha(),
				mapToGrantedAuthorities(usuario.getRoles()));
				
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities (UserRoles roles){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority(roles.toString()));
		return authorities;


	}
}
