package com.tdex.docelar.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.domain.entity.Predio;
import com.tdex.docelar.domain.repository.ApartamentoRepository;
import com.tdex.docelar.domain.repository.PredioRepository;
import com.tdex.docelar.rest.dto.ApartamentoDTO;

@RestController
@RequestMapping("/api/apartamento")
public class ApartamentoController {

	@Autowired
	private ApartamentoRepository repository;
	
	@Autowired
	private PredioRepository predioRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Apartamento save(@RequestBody @Valid ApartamentoDTO ap) {
		Predio predio = predioRepository.findById(ap.getPredio())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível inserir apartamento no prédio indicado."));
		
		Apartamento apartamento = Apartamento.builder()
												.predio(predio)
												.numero(ap.getNumero())
												.andar(ap.getAndar())
												.vagasGaragem(ap.getVagas())
												.build();
		
		return repository.save(apartamento);
	}

	@GetMapping("{id}")
	public Apartamento getApartamentoById(@PathVariable Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));
	}
}
