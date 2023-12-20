package com.kap.controleusuario.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.kap.controleusuario.enums.TipoStatus.UsuarioStatus;
import com.kap.controleusuario.enums.UserRoles;

public class UsuarioDto {

	private Long id;

	private String nome;

	private String email;

	private String senha;

	private UsuarioStatus status;
	
	private String cpf;
	
	private String dataNascimento;
	
	private Long matricula;
	
	private UserRoles roles;

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

	@NotEmpty(message="CPF não pode ser vazio.")
	@CPF(message="CPF inválido.")
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@NotEmpty(message = "Data de nascimento não pode ser vazia.")
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String DataNascimento) {
		this.dataNascimento = DataNascimento;
	}

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public UserRoles getRoles() {
		return roles;
	}

	public void setRoles(UserRoles roles) {
		this.roles = roles;
	}
	
	
}
