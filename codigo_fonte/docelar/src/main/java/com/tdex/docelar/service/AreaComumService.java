package com.tdex.docelar.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.aspectj.apache.bcel.generic.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.AgendamentoAreaComum;
import com.tdex.docelar.domain.entity.AreaComum;
import com.tdex.docelar.domain.entity.Pessoa;
import com.tdex.docelar.domain.enums.StatusAgendamentoEnum;
import com.tdex.docelar.domain.repository.AgendamentoAreaComumRepository;
import com.tdex.docelar.domain.repository.AreaComumRepository;
import com.tdex.docelar.domain.repository.PessoaRepository;
import com.tdex.docelar.rest.dto.AgendamentoDTO;

@Service
public class AreaComumService {

	@Autowired
	private AreaComumRepository areaRepository;

	@Autowired
	private AgendamentoAreaComumRepository agendamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	public AreaComum salvarArea(AreaComum area) {
		return areaRepository.save(area);
	}

	public AreaComum getArea(Integer id) {
		return areaRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

	public void deleteArea(Integer id) {
		areaRepository.findById(id).map(area -> {
			areaRepository.deleteById(area.getId());
			return Type.VOID;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

	public List<AreaComum> listAreaComum() {
		return areaRepository.findAll();
	}

	@Transactional
	public AreaComum updateArea(Integer id, @Valid AreaComum areaAtualizada) {
		return areaRepository.findById(id).map(area -> {
			areaAtualizada.setId(area.getId());
			return areaRepository.save(areaAtualizada);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

	@Transactional
	public AgendamentoAreaComum agendarArea(@Valid AgendamentoDTO agendamento) {

		Pessoa pessoa = pessoaRepository.findById(agendamento.getPessoa())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada."));

		AreaComum area = areaRepository.findById(agendamento.getArea())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área não encontrada."));

		LocalDateTime horaInicial;
		LocalDateTime horaFinal;

		try {
			horaInicial = LocalDateTime.parse(agendamento.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
			horaFinal = horaInicial.plusMinutes(area.getPeriodo() * agendamento.getQtdPeriodo());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data inválida. Favor tentar novamente no formato: DD/MM/AAAA HH:MM");
		}

		AgendamentoAreaComum registro = AgendamentoAreaComum.builder()
							.pessoa(pessoa)
							.area(area)
							.horaInicio(horaInicial)
							.horaFim(horaFinal)
							.status(StatusAgendamentoEnum.PENDENTE)
							.ultimaAtualizacao(LocalDateTime.now())
							.build();

		return agendamentoRepository.save(registro);
	}

	public List<AgendamentoAreaComum> listAllAgendamentos() {
		return agendamentoRepository.findAll();
	}

	@Transactional
	public void updateStatusPedido(Integer id, StatusAgendamentoEnum novoStaus) {
		agendamentoRepository.findById(id).map(agendamento -> {
			agendamento.setStatus(novoStaus);
			agendamento.setUltimaAtualizacao(LocalDateTime.now());
			agendamentoRepository.save(agendamento);
			return Type.VOID;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado."));

	}


}
