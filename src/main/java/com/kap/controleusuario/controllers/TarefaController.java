package com.kap.controleusuario.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kap.controleusuario.dtos.TarefaDto;
import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.services.TarefaService;
import com.kap.controleusuario.utils.Validacao;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;

	@Autowired
	private Validacao validacao;

	@PostMapping
	public ResponseEntity salvarTarefa(@RequestBody TarefaDto cadastrarTarefaDto) {

		Tarefa tarefa = this.converterDtoparaTarefa(cadastrarTarefaDto);
		this.tarefaService.salvarTarefa(tarefa);

		return new ResponseEntity(HttpStatus.CREATED);
	}

	@GetMapping(value = "/{matricula}/ativo")
	public ResponseEntity<List<TarefaDto>> listarTarefasAtivasUsuario(@PathVariable Long matricula)
			throws NotFoundException {

		List<Tarefa> tarefa = this.tarefaService.listarTarefasAtivasUsuario(matricula);
		List<TarefaDto> cadastrarTarefaDtos = converterTarefaParaDto(tarefa);

		return new ResponseEntity<List<TarefaDto>>(cadastrarTarefaDtos, HttpStatus.OK);
	}

	@GetMapping(value = "/{matricula}")
	public ResponseEntity<List<TarefaDto>> listaTarefasPorMatricula(@PathVariable Long matricula)
			throws NotFoundException {

		List<Tarefa> tarefa = this.tarefaService.listarTarefasPorUsuarioMatricula(matricula);
		List<TarefaDto> cadastrarTarefaDtos = converterTarefaParaDto(tarefa);

		return new ResponseEntity<List<TarefaDto>>(cadastrarTarefaDtos, HttpStatus.OK);

	}

	@PutMapping("/{codigo}")
	public ResponseEntity<TarefaDto> editarTarefaPorCodigo(@PathVariable String codigo,
			@RequestBody TarefaDto cadastrarTarefaDto) {

		Tarefa tarefa = this.converterDtoparaTarefa(cadastrarTarefaDto);
		this.tarefaService.editarTarefaPorCodigo(codigo, tarefa);

		return new ResponseEntity(HttpStatus.CREATED);
	}

	private Tarefa converterDtoparaTarefa(TarefaDto cadastrarTarefaDto) {

		Tarefa tarefa = new Tarefa();
		tarefa.setDescricao(cadastrarTarefaDto.getDescricao());
		tarefa.setUsuario(this.validacao.usuarioPorMatricula(cadastrarTarefaDto.getMatricula()));
		tarefa.setStatus(cadastrarTarefaDto.getStatus());
		return tarefa;
	}

	private List<TarefaDto> converterTarefaParaDto(List<Tarefa> tarefa) {

		List<TarefaDto> listTarefaDto = tarefa.stream().map(dados -> {
			TarefaDto cadastrarTarefaDto = new TarefaDto();
			cadastrarTarefaDto.setCodigo(dados.getCodigo());
			cadastrarTarefaDto.setMatricula(dados.getUsuario().getMatricula());
			cadastrarTarefaDto.setStatus(dados.getStatus());
			cadastrarTarefaDto.setDescricao(dados.getDescricao());
			cadastrarTarefaDto.setNome_usuario(dados.getUsuario().getNome());
			return cadastrarTarefaDto;
		}).collect(Collectors.toList());

		return listTarefaDto;
	}
}
