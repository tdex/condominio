package com.tdex.docelar.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Predio;
import com.tdex.docelar.domain.repository.PredioRepository;
import com.tdex.docelar.rest.dto.PredioNomeDTO;

@RestController
@RequestMapping("/api/predio")
public class PredioController {

	@Autowired 
	private PredioRepository repository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Predio save(@RequestBody @Valid Predio predio) {
		return repository.save(predio);
	}
	
	@GetMapping("{id}")
	public Predio getPretdioById(@PathVariable Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prédio não encontrado."));
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarNomePredio(@PathVariable Integer id, @RequestBody PredioNomeDTO dto) {
		repository.findById(id).map(predio -> {
			predio.setNome(dto.getNome());
			return repository.save(predio);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prédio não encontrado."));
	}
}
