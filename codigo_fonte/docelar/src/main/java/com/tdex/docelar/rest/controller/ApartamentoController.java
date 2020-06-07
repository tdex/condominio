package com.tdex.docelar.rest.controller;

import javax.validation.Valid;

import org.aspectj.apache.bcel.generic.Type;
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
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.domain.entity.Predio;
import com.tdex.docelar.domain.repository.ApartamentoRepository;
import com.tdex.docelar.domain.repository.PredioRepository;
import com.tdex.docelar.rest.dto.ApartamentoDTO;
import com.tdex.docelar.rest.dto.ApartamentoVagasGaragemDTO;

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
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Não foi possível inserir apartamento no prédio indicado."));

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

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteApartamento(@PathVariable Integer id) {
		repository.findById(id).map(ap -> {
			repository.deleteById(ap.getId());
			return Type.VOID;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Não foi possível excluir apartamento. Código inválido!"));
	}
	

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizaVagasGaragem(@PathVariable Integer id, @RequestBody @Valid ApartamentoVagasGaragemDTO vagas) {
		repository.findById(id).map(ap -> {
			ap.setVagasGaragem(vagas.getVagas());
			return repository.save(ap);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));
	}
}
