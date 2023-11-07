package com.kap.controleusuario.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.kap.controleusuario.utils.TipoStatus;

public class TarefaDto {

	private Long id;
	private String descricao;
	private Long matricula;
	private String nome_usuario;
	private TipoStatus status;
	private String codigo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Descricao não pode ser vazio")
	@Length(min = 5, max = 200, message = "Descricao deve conter entre 5 e 200 caracteres.")
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

	@NotEmpty(message = "Status não pode ser vazio")
	public TipoStatus getStatus() {
		return status;
	}

	public void setStatus(TipoStatus status) {
		this.status = status;
	}

	@NotEmpty(message = "Codigo não pode ser vazio")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@NotEmpty(message = "Nome não pode ser vazio")
	public String getNome_usuario() {
		return nome_usuario;
	}

	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}

}
