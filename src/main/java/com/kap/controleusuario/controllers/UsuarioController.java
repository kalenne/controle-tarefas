package com.kap.controleusuario.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kap.controleusuario.dtos.UsuarioDto;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.services.UsuarioService;
import com.kap.controleusuario.utils.UserRoles;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity salvarUsuario(@RequestBody UsuarioDto cadastroUsuarioDto) {

		Usuario usuario = this.converterDtoparaUsuario(cadastroUsuarioDto);
		this.usuarioService.salvarUsuario(usuario);

		return new ResponseEntity(HttpStatus.CREATED);

	}

	@GetMapping("/{matricula}")
	public ResponseEntity<UsuarioDto> listarUsuarioPorMatricula(@PathVariable Long matricula) throws NotFoundException {
		Optional<Usuario> usuario = this.usuarioService.buscarPorMatricula(matricula);

		UsuarioDto dto = converterUsuarioParaDto(usuario.get());

		return new ResponseEntity<UsuarioDto>(dto, HttpStatus.ACCEPTED);
	}

	@PutMapping("/editar")
	public ResponseEntity editarUsuarioPorEmail(@RequestBody UsuarioDto cadastroUsuarioDto) {

		Usuario usuario = this.converterDtoparaUsuario(cadastroUsuarioDto);
		this.usuarioService.editarUsuarioPorEmail(usuario);

		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping("/editar/{matricula}")
	public ResponseEntity editarUsuarioPorMatricula(@PathVariable Long matricula,
			@RequestBody UsuarioDto cadastroUsuarioDto) {

		Usuario usuario = this.converterDtoparaUsuario(cadastroUsuarioDto);
		this.usuarioService.editarUsuarioPorMatricula(matricula, usuario);

		return new ResponseEntity(HttpStatus.CREATED);
	}

	@PutMapping("/del/{matricula}")
	public ResponseEntity exclusaoUsuario(@PathVariable Long matricula) throws NotFoundException {
		this.usuarioService.exclusaoUsuario(matricula);

		return new ResponseEntity(HttpStatus.ACCEPTED);
	}

	private Usuario converterDtoparaUsuario(UsuarioDto cadastroUsuarioDto) {

		Usuario usuario = new Usuario();
		usuario.setNome(cadastroUsuarioDto.getNome());
		usuario.setEmail(cadastroUsuarioDto.getEmail());
		usuario.setSenha(cadastroUsuarioDto.getSenha());
		usuario.setRoles(UserRoles.ROLE_USER);
		usuario.setStatus(cadastroUsuarioDto.getStatus());
		return usuario;
	}

	private UsuarioDto converterUsuarioParaDto(Usuario usuario) {

		UsuarioDto dto = new UsuarioDto();
		dto.setEmail(usuario.getEmail());
		dto.setNome(usuario.getNome());
		dto.setStatus(usuario.getStatus());
		return dto;
	}

}
