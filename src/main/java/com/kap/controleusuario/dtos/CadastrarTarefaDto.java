package com.kap.controleusuario.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class CadastrarTarefaDto {
	
	private Long id;
	private String descricao;
	private Long matricula;
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@NotEmpty(message = "Descricao não pode ser vazio")
	@Length(min=5, max=200, message = "Descricao deve conter entre 5 e 200 caracteres.")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@NotEmpty(message = "Matricula não pode ser vazio")
	public Long getMatricula() {
		return matricula;
	}
	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}
	
}
