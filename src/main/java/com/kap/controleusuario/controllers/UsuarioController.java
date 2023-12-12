package com.kap.controleusuario.controllers;

import java.text.ParseException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kap.controleusuario.dtos.UsuarioDto;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.enums.UserRoles;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.response.Response;
import com.kap.controleusuario.services.UsuarioService;
import com.kap.controleusuario.utils.FormatDate;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;

	private FormatDate fd = new FormatDate();

	@PostMapping("/salvar")
	public ResponseEntity salvarUsuario(@Valid @RequestBody UsuarioDto cadastroUsuarioDto) {
		Usuario usuario = this.converterDtoparaUsuario(cadastroUsuarioDto);
		this.usuarioService.salvarUsuario(usuario);

		return new ResponseEntity(HttpStatus.CREATED);

	}

	@GetMapping("/{matricula}")
	public ResponseEntity<Response<UsuarioDto>> listarUsuarioPorMatricula(@PathVariable Long matricula)
			throws NotFoundException {
		Response<UsuarioDto> response = new Response<>();
		UsuarioDto dto = new UsuarioDto();

		Optional<Usuario> usuario = this.usuarioService.buscarPorMatricula(matricula);
		if (usuario.isPresent()) {
			dto = converterUsuarioParaDto(usuario.get());
		}
		
		response.setData(dto);

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@GetMapping("/e/{email}")
	public ResponseEntity<Response<UsuarioDto>> listarUsuarioPorEmail(@PathVariable String email)
			throws NotFoundException {
		Response<UsuarioDto> response = new Response<>();
		UsuarioDto dto = new UsuarioDto();
		
		Optional<Usuario> usuario = this.usuarioService.buscarPorEmail(email);
		if (usuario.isPresent()) {
			dto = converterUsuarioParaDto(usuario.get());
		}
		
		response.setData(dto);

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}

	@PutMapping("/editar")
	public ResponseEntity editarUsuarioPorEmail(@Valid @RequestBody UsuarioDto cadastroUsuarioDto) {

		Usuario usuario = this.converterDtoparaUsuario(cadastroUsuarioDto);
		this.usuarioService.editarUsuarioPorEmail(usuario);

		return new ResponseEntity(HttpStatus.OK);
	}

	@PutMapping("/editar/{matricula}")
	public ResponseEntity editarUsuarioPorMatricula(@PathVariable Long matricula,
			@Valid @RequestBody UsuarioDto cadastroUsuarioDto) {

		Usuario usuario = this.converterDtoparaUsuario(cadastroUsuarioDto);
		this.usuarioService.editarUsuarioPorMatricula(matricula, usuario);

		return new ResponseEntity(HttpStatus.OK);
	}

	private Usuario converterDtoparaUsuario(UsuarioDto cadastroUsuarioDto) {
		Usuario usuario = new Usuario();

		try {
			usuario.setNome(cadastroUsuarioDto.getNome());
			usuario.setEmail(cadastroUsuarioDto.getEmail());
			usuario.setSenha(cadastroUsuarioDto.getSenha());
			usuario.setStatus(cadastroUsuarioDto.getStatus());
			usuario.setCpf(cadastroUsuarioDto.getCpf());
			usuario.setDataNascimento(fd.userToDb(cadastroUsuarioDto.getDataNascimento()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return usuario;
	}

	private UsuarioDto converterUsuarioParaDto(Usuario usuario) {
		UsuarioDto dto = new UsuarioDto();
		dto.setEmail(usuario.getEmail());
		dto.setNome(usuario.getNome());
		dto.setStatus(usuario.getStatus());
		dto.setCpf(usuario.getCpf());
		dto.setDataNascimento(fd.dbToUser(usuario.getDataNascimento()));
		dto.setMatricula(usuario.getMatricula());
		return dto;
	}

}
