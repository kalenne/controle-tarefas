package com.kap.controleusuario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kap.controleusuario.entities.Usuario;

@Transactional(readOnly = true)
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	//@Query("SELECT u.id, u.nome, u.matricula FROM usuario AS u WHERE u.matricula = :matricula ")
	Usuario findByMatricula(@Param("matricula") Long Matricula);
	
	Usuario findByEmail(String email);
	
	
	
	
	
	
}
