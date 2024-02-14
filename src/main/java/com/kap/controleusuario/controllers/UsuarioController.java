package com.kap.controleusuario.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.kap.controleusuario.response.Response;
import com.kap.controleusuario.services.UsuarioService;
import com.kap.controleusuario.utils.ConverterEnum;
import com.kap.controleusuario.utils.FormatLocalDate;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	private static final Logger log = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ConverterEnum converter;

	private FormatLocalDate fd = new FormatLocalDate();

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

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping("/admin/usuarios")
	public ResponseEntity<Response<List<UsuarioDto>>> listarUsuarios() throws NotFoundException {

		Response<List<UsuarioDto>> response = new Response<>();

		List<Usuario> usuarios = this.usuarioService.buscarTodosUsuarios();
		List<UsuarioDto> dto = converterUsuariosParaDto(usuarios);
		
		response.setData(dto);
		
		return new ResponseEntity<>(response, HttpStatus.OK);

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

		usuario.setNome(cadastroUsuarioDto.getNome());
		usuario.setEmail(cadastroUsuarioDto.getEmail());
		usuario.setSenha(cadastroUsuarioDto.getSenha());
		usuario.setStatus(converter.converterStringUsuarioStatus(cadastroUsuarioDto.getStatus()));
		usuario.setCpf(cadastroUsuarioDto.getCpf());
		usuario.setDataNascimento(cadastroUsuarioDto.getDataNascimento());
		usuario.setRoles(converter.converterStringUsuarioPerfil(cadastroUsuarioDto.getRoles()));
		return usuario;
	}

	private UsuarioDto converterUsuarioParaDto(Usuario usuario) {
		UsuarioDto dto = new UsuarioDto();
		dto.setEmail(usuario.getEmail());
		dto.setNome(usuario.getNome());
		dto.setStatus(usuario.getStatus().toString());
		dto.setCpf(usuario.getCpf());
		dto.setDataNascimento(usuario.getDataNascimento());
		dto.setMatricula(usuario.getMatricula());
		dto.setRoles(usuario.getRoles().toString());
		return dto;
	}

	private List<UsuarioDto> converterUsuariosParaDto(List<Usuario> usuarios) {

		return usuarios.stream().map(usuario -> {

			UsuarioDto dto = new UsuarioDto();
			dto.setEmail(usuario.getEmail());
			dto.setNome(usuario.getNome());
			dto.setStatus(usuario.getStatus().toString());
			dto.setCpf(usuario.getCpf());
			dto.setDataNascimento(usuario.getDataNascimento());
			dto.setMatricula(usuario.getMatricula());
			dto.setRoles(usuario.getRoles().toString());
			return dto;

		}).collect(Collectors.toList());

	}

}
