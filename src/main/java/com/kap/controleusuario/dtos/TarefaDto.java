package com.kap.controleusuario.dtos;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.kap.controleusuario.enums.Prioridade;
import com.kap.controleusuario.enums.TipoStatus;

public class TarefaDto {

	private Long id;
	private String descricao;
	private Long matricula;
	private String nomeUsuario;
	private String titulo;
	private String status;
	private String codigo;
	private LocalDateTime dataInicio;
	private LocalDateTime dataFinal;
	private String prioridade;
	private Long autor;
	
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
	public String getStatus() {
		return status.toString();
	}

	public void setStatus(String status) {
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
	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	
	@NotEmpty(message = "Titulo não pode ser vazio")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@NotEmpty(message = "Data de Inicio não pode ser vazio")
	public LocalDateTime getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDateTime dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	@NotEmpty(message = "Data Final não pode ser vazio")
	public LocalDateTime getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	@NotEmpty(message = "Prioridades não pode ser vazio")
	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}
	
	@NotEmpty(message = "Autor não pode ser vazio")
	public Long getAutor() {
		return autor;
	}

	public void setAutor(Long autor) {
		this.autor = autor;
	}

	

}
