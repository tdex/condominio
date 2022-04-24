package com.tdex.docelar.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.rest.dto.ApartamentoDTO;
import com.tdex.docelar.rest.dto.ApartamentoVagasGaragemDTO;
import com.tdex.docelar.rest.service.ApartamentoService;

@RestController
@RequestMapping("/api/apartamento")
public class ApartamentoController {

	@Autowired
	private ApartamentoService service;

	@GetMapping
	public List<Apartamento> allApartamentos() {
		return service.listarTodosApartamentos();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Apartamento save(@RequestBody @Valid ApartamentoDTO ap) {
		return service.salvar(ap);
	}

	@GetMapping("{id}")
	public Apartamento getApartamentoById(@PathVariable Integer id) {
		return service.buscarApartamento(id);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteApartamento(@PathVariable Integer id) {
		service.deleteApartamento(id);
	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaVagasGaragem(@PathVariable Integer id, @RequestBody @Valid ApartamentoVagasGaragemDTO vagas) {
		service.atualizarVagasGaragem(id, vagas.getVagas());
	}

}
