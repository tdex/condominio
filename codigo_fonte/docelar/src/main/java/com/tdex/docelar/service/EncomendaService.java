package com.tdex.docelar.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

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

@Service
public class EncomendaService {

	@Autowired
	private EncomendaRepository encomendaRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	public List<Encomenda> listAll() {
		return encomendaRepository.findAll();
	}

	@Transactional
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

	public Encomenda saida(Integer id, EncomendaRecebimentoDTO recebedor) {
		return encomendaRepository.findById(id).map(encomenda -> {
			encomenda.setHoraSaida(LocalDateTime.now());
			encomenda.setRecebedor(recebedor.getRecebedor());
			return encomendaRepository.save(encomenda);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Encomenda não encontrada."));
	}

	public List<Encomenda> listarEncomendasAbertasPorApartamento(Integer idApartamento) {
		return apartamentoRepository.findById(idApartamento).map(ap -> {
			return encomendaRepository.findByDestinatarioAndHoraSaidaIsNull(ap);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));
	}

	public List<Encomenda> listarTodasEncomendasPorApartamento(Integer idApartamento) {
		return apartamentoRepository.findById(idApartamento).map(ap -> {
			return encomendaRepository.findByDestinatario(ap);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));
	}


}
