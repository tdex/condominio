package com.tdex.docelar.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tdex.docelar.domain.entity.Pessoa;
import com.tdex.docelar.rest.dto.PessoaDTO;
import com.tdex.docelar.rest.service.PessoaService;

@RestController
@RequestMapping("/api/pessoa")
public class PessoaController {

	@Autowired
	private PessoaService service;

	@GetMapping("{id}")
	public Pessoa getPessoa(@PathVariable Integer id) {
		return service.getPessoa(id);
	}

	@GetMapping
	public List<Pessoa> find(@RequestBody Pessoa filtro) {
		return service.find(filtro);
	}

	@PostMapping
	public Pessoa save(@RequestBody PessoaDTO dto) {
		return service.salvarPessoa(dto);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePessoa(@PathVariable Integer id) {
		service.deletePessoa(id);
	}

}
