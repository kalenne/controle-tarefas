package com.kap.controleusuario.enums;

public enum UsuarioStatus {
	ATIVO("Ativo"), INATIVO("Inativo");
	
	private final String texto;

	UsuarioStatus (String texto) {
		this.texto = texto;
	}
	
	@Override
    public String toString() {
        return texto;
    }
}