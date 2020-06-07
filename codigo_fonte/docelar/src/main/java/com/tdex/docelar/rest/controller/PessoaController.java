package com.tdex.docelar.rest.controller;

import java.util.List;

import org.aspectj.apache.bcel.generic.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@GetMapping
	public List<Pessoa> find(Pessoa filtro) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

		Example<Pessoa> example = Example.of(filtro, matcher);

		return repository.findAll(example);
	}

	@PostMapping
	public Pessoa save(@RequestBody PessoaDTO dto) {
		return service.salvarPessoa(dto);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePessoa(@PathVariable Integer id) {
		repository.findById(id).map(pessoa -> {
			repository.deleteById(pessoa.getId());
			return Type.VOID;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada."));
	}

}
