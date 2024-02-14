package com.kap.controleusuario.enums;

public enum UsuarioPerfil {
	ROLE_ADMIN("ADMIN"),
	ROLE_USER("USER");
	
	private final String texto;

	UsuarioPerfil(String texto) {
		this.texto = texto;
	}
	
	@Override
    public String toString() {
        return texto;
    }
}
