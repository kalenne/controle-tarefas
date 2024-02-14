package com.kap.controleusuario.enums;

public enum TipoStatus {
	
	CRIADO("Criado"), EM_ANDAMENTO("Em Andamento"), FINALIZADO("Finalizado"), CANCELADO("Cancelado"), VENCIDO("Vencido");
	
	
	
	private final String texto;

	TipoStatus(String texto) {
		this.texto = texto;
	}
	
	@Override
    public String toString() {
        return texto;
    }
}



