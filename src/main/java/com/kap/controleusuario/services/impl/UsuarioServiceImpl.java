package com.kap.controleusuario.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.enums.TipoStatus.UsuarioStatus;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.repositories.UsuarioRepository;
import com.kap.controleusuario.services.UsuarioService;
import com.kap.controleusuario.utils.FormatDate;
import com.kap.controleusuario.validacao.ValidacaoUsuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ValidacaoUsuario validacao;

	@Override
	public Optional<Usuario> buscarPorMatricula(Long matricula) throws NotFoundException {

		Optional<Usuario> usuario = this.usuarioRepository.findByMatricula(matricula);

		if (!usuario.isPresent()) {
			throw new NotFoundException("Usuario inexistente");
		}

		return Optional.ofNullable(usuario.get());
	}

	@Override
	public Optional<Usuario> buscarPorEmail(String email) throws NotFoundException {

		Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email);

		if (!usuario.isPresent()) {
			throw new NotFoundException("Usuario inexistente");
		}

		return Optional.ofNullable(usuario.get());
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
				
		usuario.setMatricula(validacao.gerarMatriculaUsuarioValidacao());
		usuario.setEmail(validacao.usuarioValidaEmail(usuario.getEmail()));
		usuario.setStatus(UsuarioStatus.ATIVO);
		return this.usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> editarUsuarioPorEmail(Usuario usuario) throws NotFoundException {

		Optional<Usuario> user = this.usuarioRepository.findByEmail(usuario.getEmail()).map(dados -> {
			
			return this.usuarioRepository.save(setUsuarioEditado(dados, usuario));
		});

		if (!user.isPresent()) {
			throw new NotFoundException("Usuário inválido");
		}

		return user;
	}

	@Override
	public Optional<Usuario> editarUsuarioPorMatricula(Long matricula, Usuario usuario) throws NotFoundException {

		Optional<Usuario> user = this.usuarioRepository.findByMatricula(matricula).map(dados -> {
			
			return this.usuarioRepository.save(setUsuarioEditado(dados, usuario));
		});

		if (!user.isPresent()) {
			throw new NotFoundException("Usuário inválido");
		}

		return user;
	}


	@Override
	public UsuarioStatus[] status() {
		return UsuarioStatus.values();
	}
	
	private Usuario setUsuarioEditado(Usuario usuario, Usuario UsuarioEditado) {
		usuario.setEmail(UsuarioEditado.getEmail());
		usuario.setNome(UsuarioEditado.getNome());
		usuario.setSenha(UsuarioEditado.getSenha());
		if (UsuarioEditado.getRoles() != null) {
			usuario.setRoles(UsuarioEditado.getRoles());
		}
		if(UsuarioEditado.getStatus() != null) {
			usuario.setStatus(UsuarioEditado.getStatus());
		}
		
		usuario.setDataNascimento(UsuarioEditado.getDataNascimento());
		usuario.setCpf(UsuarioEditado.getCpf());
		return usuario;
	}

}
