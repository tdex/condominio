package com.tdex.docelar.rest.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.domain.entity.Encomenda;
import com.tdex.docelar.domain.repository.ApartamentoRepository;
import com.tdex.docelar.domain.repository.EncomendaRepository;
import com.tdex.docelar.rest.dto.EncomendaDTO;
import com.tdex.docelar.rest.dto.EncomendaRecebimentoDTO;
import com.tdex.docelar.rest.service.EncomendaService;

@Service
public class EncomendaServiceImpl implements EncomendaService {

	@Autowired
	private EncomendaRepository encomendaRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@Override
	public List<Encomenda> listAll() {
		return encomendaRepository.findAll();
	}

	@Override
	public Encomenda save(EncomendaDTO dto) {
		Apartamento apartamento = apartamentoRepository.findById(dto.getDestinatario())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destinatário não encontrado."));

		Encomenda encomenda = Encomenda.builder()
					.descricao(dto.getDescricao())
					.horaEntrada(LocalDateTime.now())
					.destinatario(apartamento)
					.build();

		return encomendaRepository.save(encomenda);
	}

	@Override
	public Encomenda saida(Integer id, EncomendaRecebimentoDTO recebedor) {
		return encomendaRepository.findById(id).map(encomenda -> {
			encomenda.setHoraSaida(LocalDateTime.now());
			encomenda.setRecebedor(recebedor.getRecebedor());
			return encomendaRepository.save(encomenda);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Encomenda não encontrada."));
	}

	@Override
	public List<Encomenda> listarEncomendasAbertasPorApartamento(Integer idApartamento) {
		return apartamentoRepository.findById(idApartamento).map(ap -> {
			return encomendaRepository.findByDestinatarioAndHoraSaidaIsNull(ap);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));
	}

	@Override
	public List<Encomenda> listarTodasEncomendasPorApartamento(Integer idApartamento) {
		return apartamentoRepository.findById(idApartamento).map(ap -> {
			return encomendaRepository.findByDestinatario(ap);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));
	}

}
