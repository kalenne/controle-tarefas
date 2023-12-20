package com.kap.controleusuario.controllers;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kap.controleusuario.dtos.TarefaDto;
import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.enums.Prioridade;
import com.kap.controleusuario.enums.TipoStatus;
import com.kap.controleusuario.enums.TipoStatus.UsuarioStatus;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.response.Response;
import com.kap.controleusuario.services.TarefaService;
import com.kap.controleusuario.utils.FormatLocalDateTime;
import com.kap.controleusuario.validacao.ValidacaoUsuario;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;

	@Autowired
	private ValidacaoUsuario validacao;
	
	@Autowired
	private FormatLocalDateTime fldt;
	
	private static final Logger log = LoggerFactory.getLogger(TarefaController.class);
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity salvarTarefa(@RequestBody TarefaDto cadastrarTarefaDto) throws ParseException {

		Tarefa tarefa = this.converterDtoparaTarefa(cadastrarTarefaDto);
		this.tarefaService.salvarTarefa(tarefa);
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping(value = "/{matricula}/ativo")
	public ResponseEntity<Response<List<TarefaDto>>> listarTarefasAtivasUsuario(@PathVariable Long matricula)
			throws NotFoundException {
		
		Response<List<TarefaDto>> response = new Response<>();
		
		List<Tarefa> tarefa = this.tarefaService.listarTarefasAtivasUsuario(matricula);
		List<TarefaDto> dto = converterTarefaParaDto(tarefa);
		
		response.setData(dto);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/{matricula}")
	public ResponseEntity<Response<List<TarefaDto>>> listaTarefasPorMatricula(@PathVariable Long matricula)
			throws NotFoundException {
		Response<List<TarefaDto>> response = new Response<>();
		
		List<Tarefa> tarefa = this.tarefaService.listarTarefasPorUsuarioMatricula(matricula);
		List<TarefaDto> dto = converterTarefaParaDto(tarefa);
		
		response.setData(dto);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping("/{codigo}")
	public ResponseEntity<TarefaDto> editarTarefaPorCodigo(@PathVariable String codigo,
			@RequestBody TarefaDto cadastrarTarefaDto) throws ParseException {

		Tarefa tarefa = this.converterDtoparaTarefa(cadastrarTarefaDto);
		this.tarefaService.editarTarefaPorCodigo(codigo, tarefa);

		return new ResponseEntity(HttpStatus.OK);
	}

	private Tarefa converterDtoparaTarefa(TarefaDto cadastrarTarefaDto) throws ParseException {
		Tarefa tarefa = new Tarefa();
		tarefa.setDescricao(cadastrarTarefaDto.getDescricao());
		tarefa.setUsuario(this.validacao.usuarioPorMatricula(cadastrarTarefaDto.getMatricula()));
		tarefa.setStatus(cadastrarTarefaDto.getStatus());
		tarefa.setAutor(this.validacao.usuarioPorMatricula(cadastrarTarefaDto.getAutor()));
		tarefa.setPrioridade(cadastrarTarefaDto.getPrioridade());
		tarefa.setTitulo(cadastrarTarefaDto.getTitulo());
		tarefa.setDataInicio(fldt.userToDb(cadastrarTarefaDto.getDataInicio()));
		tarefa.setDataFinal(fldt.userToDb(cadastrarTarefaDto.getDataFinal()));
		return tarefa;
	}

	private List<TarefaDto> converterTarefaParaDto(List<Tarefa> tarefa) {

		return tarefa.stream().map(dados -> {
			TarefaDto cadastrarTarefaDto = new TarefaDto();
			cadastrarTarefaDto.setCodigo(dados.getCodigo());
			cadastrarTarefaDto.setMatricula(dados.getUsuario().getMatricula());
			cadastrarTarefaDto.setStatus(dados.getStatus());
			cadastrarTarefaDto.setDescricao(dados.getDescricao());
			cadastrarTarefaDto.setNomeUsuario(dados.getUsuario().getNome());
			cadastrarTarefaDto.setAutor(dados.getAutor().getMatricula());
			cadastrarTarefaDto.setDataInicio(fldt.dbToUser(dados.getDataInicio()));
			cadastrarTarefaDto.setDataFinal(fldt.dbToUser(dados.getDataFinal()));
			cadastrarTarefaDto.setPrioridade(dados.getPrioridade());
			cadastrarTarefaDto.setTitulo(dados.getTitulo());
			
			return cadastrarTarefaDto;
		}).collect(Collectors.toList());
	}
}
