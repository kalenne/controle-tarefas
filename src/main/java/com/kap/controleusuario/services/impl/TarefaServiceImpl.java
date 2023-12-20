package com.kap.controleusuario.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kap.controleusuario.entities.Tarefa;
import com.kap.controleusuario.enums.Prioridade;
import com.kap.controleusuario.enums.TipoStatus;
import com.kap.controleusuario.exception.NotFoundException;
import com.kap.controleusuario.repositories.TarefaRepository;
import com.kap.controleusuario.services.TarefaService;
import com.kap.controleusuario.utils.FormatLocalDateTime;
import com.kap.controleusuario.validacao.ValidacaoTarefa;
import com.kap.controleusuario.validacao.ValidacaoUsuario;

@Service
public class TarefaServiceImpl implements TarefaService {

	private static final Logger log = LoggerFactory.getLogger(TarefaServiceImpl.class);

	@Autowired
	private TarefaRepository tarefaRepository;

	@Autowired
	private ValidacaoTarefa validacao;
	
	public Tarefa salvarTarefa(Tarefa tarefa) {
		
		if(validacao.validarDataInicioFinal(tarefa.getDataInicio(), tarefa.getDataFinal())) {
			tarefa.setCodigo(validacao.gerarCodigoTarefa());
			tarefa.setStatus(TipoStatus.CRIADO);
			
			String titulo = tarefa.getCodigo() + " - " + tarefa.getTitulo();
			tarefa.setTitulo(titulo);
			
			return this.tarefaRepository.save(tarefa);
		}
		
		return null;
		
	}

	@Override
	public List<Tarefa> listarTarefasPorUsuarioMatricula(Long matricula) throws NotFoundException {

		List<Tarefa> tarefa = this.tarefaRepository.findByUsuarioId(validacao.usuarioPorMatricula(matricula));

		if (tarefa == null) {
			throw new NotFoundException("Não possui tarefa atrelada ao Usuario");
		}

		return tarefa;
	}

	@Override
	public Optional<Tarefa> editarTarefaPorCodigo(String codigo, Tarefa tarefa) throws NotFoundException {

		Optional<Tarefa> taref = this.tarefaRepository.findByCodigo(codigo).map(dados -> {
			dados.setDescricao(tarefa.getDescricao());
			dados.setStatus(tarefa.getStatus());
			dados.setUsuario(tarefa.getUsuario());
			dados.setDataInicio(tarefa.getDataInicio());
			dados.setDataInicio(tarefa.getDataFinal());
			String titulo = dados.getCodigo() + " - " + tarefa.getTitulo();
			dados.setTitulo(titulo);
			dados.setPrioridade(tarefa.getPrioridade());
			return this.tarefaRepository.save(dados);
		});

		if (!taref.isPresent()) {
			throw new NotFoundException("Tarefa inválida!");
		}

		return taref;
	}

	@Override
	public List<Tarefa> listarTarefasAtivasUsuario(Long matricula) throws NotFoundException {

		List<Tarefa> tarefa = this.tarefaRepository.findByUsuarioId(validacao.usuarioPorMatricula(matricula)).stream()
				.filter(dados -> !(TipoStatus.CANCELADO.equals(dados.getStatus())
						|| TipoStatus.FINALIZADO.equals(dados.getStatus())))
				.collect(Collectors.toList());

		if (tarefa.isEmpty()) {
			throw new NotFoundException("Não há tarefa ativa vinculada ao Usuario");
		}

		return tarefa;
	}

}
