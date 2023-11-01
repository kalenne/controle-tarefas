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
	public ResponseEntity<CadastrarTarefaDto> salvar ( @RequestBody CadastrarTarefaDto cadastrarTarefaDto) {
		
		Tarefa tarefa = this.converterDtoparaTarefa(cadastrarTarefaDto);
		
		this.tarefaService.salvarTarefa(tarefa);
		
		return new ResponseEntity<CadastrarTarefaDto>(HttpStatus.CREATED);
		
	}
	
	@GetMapping(value = "/{matricula}")
	public ResponseEntity<List<Tarefa>> listaTarefasPorMatricula (@PathVariable Long matricula) {
		
		List<Tarefa> tarefa = this.tarefaService.listarTarefasPorUsuarioMatricula(matricula);
				
		return new ResponseEntity<List<Tarefa>>(tarefa, HttpStatus.OK) ;
		
	}


	private Tarefa converterDtoparaTarefa(CadastrarTarefaDto cadastrarTarefaDto) {

		Tarefa tarefa = new Tarefa();	
		tarefa.setDescricao(cadastrarTarefaDto.getDescricao());	

		return tarefa;
	}
	
	
}
