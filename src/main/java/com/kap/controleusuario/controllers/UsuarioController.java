package com.kap.controleusuario.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kap.controleusuario.dtos.CadastroUsuarioDto;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.services.UsuarioService;
import com.kap.controleusuario.utils.UserRoles;
import com.kap.controleusuario.utils.Validacao;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity salvarUsuario (@RequestBody CadastroUsuarioDto cadastroUsuarioDto) {
		
		Usuario usuario = this.converterDtoparaUsuario(cadastroUsuarioDto);
				
		this.usuarioService.salvarUsuario(usuario);
		
		return new ResponseEntity(HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{matricula}")
	public ResponseEntity<Optional<Usuario>> listarUsuarioPorMatricula(@PathVariable Long matricula) throws NotFoundException {
		Optional<Usuario> usuario = this.usuarioService.buscarPorMatricula(matricula);
		
		return new ResponseEntity<Optional<Usuario>>(usuario, HttpStatus.ACCEPTED);
	}

	private Usuario converterDtoparaUsuario(CadastroUsuarioDto cadastroUsuarioDto) {
				
		Usuario usuario = new Usuario();
		usuario.setNome(cadastroUsuarioDto.getNome());
		usuario.setEmail(cadastroUsuarioDto.getEmail());
		usuario.setSenha(cadastroUsuarioDto.getSenha());
		usuario.setRoles(UserRoles.ROLE_USER);
		return usuario;
	}

}
