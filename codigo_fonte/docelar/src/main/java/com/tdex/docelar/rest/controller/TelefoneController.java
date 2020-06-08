package com.tdex.docelar.rest.controller;

import javax.validation.Valid;

import org.aspectj.apache.bcel.generic.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Telefone;
import com.tdex.docelar.domain.repository.PessoaRepository;
import com.tdex.docelar.domain.repository.TelefoneRepository;

@RestController
@RequestMapping("/api/telefone")
public class TelefoneController {

	@Autowired
	private TelefoneRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		repository.findById(id).map(telefone -> {
			repository.deleteById(telefone.getId());
			return Type.VOID;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Telefone não encontrado."));
	}
	
	@PutMapping("{id}")
	public Telefone update(@PathVariable Integer id, @RequestBody @Valid Telefone telefone) {
		return repository.findById(id).map(tel -> {
			Telefone atualizado = Telefone.builder()
										.id(tel.getId())
										.ddd(telefone.getDdd())
										.numero(telefone.getNumero())
										.pessoa(tel.getPessoa())
										.build();
			return repository.save(atualizado);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Telefone não encontrado."));
	}
	
	@PostMapping("{idPessoa}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Telefone addNumeroContatoExistente(@PathVariable Integer idPessoa, @RequestBody Telefone telefone) {
		return pessoaRepository.findById(idPessoa).map(pessoa -> {
			telefone.setPessoa(pessoa);
			return repository.save(telefone);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada."));
	}
}
