package com.kap.controleusuario.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.kap.controleusuario.utils.TipoStatus.UsuarioStatus;

public class UsuarioDto {

	private Long id;

	private String nome;

	private String email;

	private String senha;

	private UsuarioStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome nao pode ser vazio.")
	@Length(min = 5, max = 200, message = "Nome deve conter entre 5 e 200 caracteres.")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message = "Email nao pode ser vazio.")
	@Email(message = "Email invalido.")
	@Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Senha nao pode ser vazio.")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsuarioStatus getStatus() {
		return status;
	}

	public void setStatus(UsuarioStatus status) {
		this.status = status;
	}

}
