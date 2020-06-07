package com.tdex.docelar.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Pessoa;
import com.tdex.docelar.domain.repository.PessoaRepository;
import com.tdex.docelar.rest.dto.PessoaDTO;
import com.tdex.docelar.service.PessoaService;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private PessoaService service;
	
	@GetMapping("{id}")
	public Pessoa getPessoa(@PathVariable Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada."));
	}
	
	@PostMapping
	public Pessoa save(@RequestBody PessoaDTO dto) {
		return service.salvarPessoa(dto);
	}

}
