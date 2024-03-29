package com.kap.controleusuario.enums;

public enum Prioridade {
	
	BAIXO("Baixo"),
	MEDIO("Médio"),
	ALTO("Alto"),
	CRITICO("Crítico");
	
	private final String texto;

	Prioridade(String texto) {
		this.texto = texto;
	}
	
	@Override
    public String toString() {
        return texto;
    }

}
