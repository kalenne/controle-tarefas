package com.kap.controleusuario.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.repositories.UsuarioRepository;
import com.kap.controleusuario.services.UsuarioService;
import com.kap.controleusuario.utils.TipoStatus.UsuarioStatus;
import com.kap.controleusuario.utils.Validacao;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private Validacao validacao;

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
			dados.setEmail(usuario.getEmail());
			dados.setNome(usuario.getNome());
			dados.setSenha(usuario.getSenha());
			if (usuario.getRoles() != null) {
				dados.setRoles(usuario.getRoles());
			}
			if (usuario.getStatus() == UsuarioStatus.ATIVO) {
				dados.setStatus(UsuarioStatus.ATIVO);
			}
			return this.usuarioRepository.save(dados);
		});

		if (!user.isPresent()) {
			throw new NotFoundException("Usuário inválido");
		}

		return user;
	}

	@Override
	public Optional<Usuario> editarUsuarioPorMatricula(Long matricula, Usuario usuario) throws NotFoundException {

		Optional<Usuario> user = this.usuarioRepository.findByMatricula(matricula).map(dados -> {
			dados.setEmail(usuario.getEmail());
			dados.setNome(usuario.getNome());
			dados.setSenha(usuario.getSenha());
			if (usuario.getRoles() != null) {
				dados.setRoles(usuario.getRoles());
			}
			if (usuario.getStatus() == UsuarioStatus.ATIVO) {
				dados.setStatus(UsuarioStatus.ATIVO);
			}
			return this.usuarioRepository.save(dados);
		});

		if (!user.isPresent()) {
			throw new NotFoundException("Usuário inválido");
		}

		return user;
	}

	@Override
	public Optional<Usuario> exclusaoUsuario(Long matricula) throws NotFoundException {

		Optional<Usuario> user = this.usuarioRepository.findByMatricula(matricula).map(dados -> {
			dados.setStatus(UsuarioStatus.INATIVO);
			return this.usuarioRepository.save(dados);
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

}
