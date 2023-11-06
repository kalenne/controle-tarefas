package com.kap.controleusuario.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kap.controleusuario.dtos.CadastrarTarefaDto;
import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.entities.Usuario;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.services.TarefaService;
import com.kap.controleusuario.services.UsuarioService;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	public ResponseEntity salvarTarefa ( @RequestBody CadastrarTarefaDto cadastrarTarefaDto) {
		
		Tarefa tarefa = this.converterDtoparaTarefa(cadastrarTarefaDto);
		
		this.tarefaService.salvarTarefa(tarefa);
		
		return new ResponseEntity(HttpStatus.CREATED);
		
	}
	
	@GetMapping(value = "/{matricula}")
	public ResponseEntity<List<Tarefa>> listaTarefasPorMatricula (@PathVariable Long matricula) throws NotFoundException {
		Long id = usuario(matricula).getId();
		
		List<Tarefa> tarefa = this.tarefaService.listarTarefasPorUsuarioId(id);
				
		return new ResponseEntity<List<Tarefa>>(tarefa, HttpStatus.OK) ;
				
	}


	private Tarefa converterDtoparaTarefa(CadastrarTarefaDto cadastrarTarefaDto) {

		Tarefa tarefa = new Tarefa();
		
		tarefa.setDescricao(cadastrarTarefaDto.getDescricao());	
		tarefa.setUsuario(usuario(cadastrarTarefaDto.getMatricula()));
		return tarefa;
	}
	
	private Usuario usuario (Long matricula) {
		Usuario usuario = new Usuario();
		this.usuarioService.buscarPorMatricula(matricula).ifPresent(usu -> usuario.setId(usu.getId()));
		return usuario;
	}
}
