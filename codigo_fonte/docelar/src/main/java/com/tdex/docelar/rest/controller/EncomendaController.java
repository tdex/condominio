package com.tdex.docelar.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Encomenda;
import com.tdex.docelar.rest.dto.EncomendaDTO;
import com.tdex.docelar.rest.dto.EncomendaRecebimentoDTO;
import com.tdex.docelar.rest.service.EncomendaService;

@RestController
@RequestMapping("/api/encomenda")
public class EncomendaController {

	@Autowired
	private EncomendaService service;

	@GetMapping
	public List<Encomenda> listarEncomendas() {
		List<Encomenda> encomendas = service.listAll();

		if (encomendas.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhuma encomenda encontrada.");
		}

		return encomendas;
	}

	@PostMapping
	public Encomenda salvar(@RequestBody @Valid EncomendaDTO encomenda) {
		return service.save(encomenda);
	}

	@PatchMapping("{id}")
	public Encomenda entregarEncomenda(@PathVariable Integer id, @RequestBody EncomendaRecebimentoDTO recebedor) {
		return service.saida(id, recebedor);
	}

	@GetMapping("{idApartamento}")
	public List<Encomenda> listarEncomendasAbertasPorApartamento(@PathVariable Integer idApartamento) {
		return service.listarEncomendasAbertasPorApartamento(idApartamento);
	}

	@GetMapping("{idApartamento}/all")
	public List<Encomenda> listarEncomendasPorApartamento(@PathVariable Integer idApartamento) {
		return service.listarTodasEncomendasPorApartamento(idApartamento);
	}

}
