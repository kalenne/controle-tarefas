package com.kap.controleusuario.exception;


public class ResponseException {
	
	private String horario;
	private String texto;
	private String detalhes;
	
	public ResponseException(String horario, String texto, String detalhes) {
		this.horario = horario;
		this.texto = texto;
		this.detalhes = detalhes;
	}

	public String getHorario() {
		return horario;
	}

	public String getTexto() {
		return texto;
	}

	public String getDetalhes() {
		return detalhes;
	}
	
	

}
