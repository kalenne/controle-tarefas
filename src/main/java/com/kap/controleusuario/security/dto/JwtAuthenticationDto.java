package com.kap.controleusuario.security.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {
	
	private String email;
	private String senha;
	
	public JwtAuthenticationDto() {
		
	}
	@NotEmpty(message = "E-mail não pode ser vazio")
	@Email(message="E-mail inválido")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@NotEmpty(message="Senha não pode ser vazia.")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationDto [email=" + email + ", senha=" + senha + "]";
	}
	
}
