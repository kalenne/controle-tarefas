package com.kap.controleusuario.utils;

import org.springframework.stereotype.Component;

import com.kap.controleusuario.enums.Prioridade;
import com.kap.controleusuario.enums.TipoStatus;
import com.kap.controleusuario.enums.UsuarioPerfil;
import com.kap.controleusuario.enums.UsuarioStatus;

@Component
public class ConverterEnum {

	public TipoStatus converterStringTipoStatus(String texto) {
		for (TipoStatus status : TipoStatus.values()) {

			if (status.toString().equals(texto)) {
				return status;
			}
		}
		return null;
	}
	
	public UsuarioStatus converterStringUsuarioStatus(String texto) {
		for (UsuarioStatus status : UsuarioStatus.values()) {

			if (status.toString().equals(texto)) {
				return status;
			}
		}
		return null;
	}
	
	public Prioridade converterStringPrioridade(String texto) {
		for (Prioridade prioridade : Prioridade.values()) {

			if (prioridade.toString().equals(texto)) {
				return prioridade;
			}
		}
		return null;
	}
	
	public UsuarioPerfil converterStringUsuarioPerfil(String texto) {
		for (UsuarioPerfil perfil : UsuarioPerfil.values()) {

			if (perfil.toString().equals(texto)) {
				return perfil;
			}
		}
		return null;
	}

}
