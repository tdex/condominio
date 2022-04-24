package com.tdex.docelar.rest.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import com.tdex.docelar.rest.service.AreaComumService;

@Service
public class AreaComumServiceImpl implements AreaComumService {

	@Autowired
	private AreaComumRepository areaRepository;

	@Autowired
	private AgendamentoAreaComumRepository agendamentoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Override
	public AreaComum salvarArea(AreaComum area) {
		return areaRepository.save(area);
	}

	@Override
	public AreaComum getArea(Integer id) {
		return areaRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

	@Override
	public void deleteArea(Integer id) {
		areaRepository.findById(id).map(area -> {
			areaRepository.deleteById(area.getId());
			return area;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

	@Override
	public List<AreaComum> listAreaComum() {
		return areaRepository.findAll();
	}

	@Override
	public AreaComum updateArea(Integer id, AreaComum areaAtualizada) {
		return areaRepository.findById(id).map(area -> {
			areaAtualizada.setId(area.getId());
			return areaRepository.save(areaAtualizada);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

	@Override
	public AgendamentoAreaComum agendarArea(AgendamentoDTO agendamento) {
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

	@Override
	public List<AgendamentoAreaComum> listAllAgendamentos() {
		return agendamentoRepository.findAll();
	}

	@Override
	public void updateStatusPedido(Integer id, StatusAgendamentoEnum novoStaus) {
		agendamentoRepository.findById(id).map(agendamento -> {
			agendamento.setStatus(novoStaus);
			agendamento.setUltimaAtualizacao(LocalDateTime.now());
			agendamentoRepository.save(agendamento);
			return agendamento;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agendamento não encontrado."));
	}

}
