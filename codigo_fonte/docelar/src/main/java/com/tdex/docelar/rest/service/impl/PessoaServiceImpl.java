package com.tdex.docelar.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.config.UsuarioServiceConfig;
import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.domain.entity.Pessoa;
import com.tdex.docelar.domain.entity.Telefone;
import com.tdex.docelar.domain.entity.Usuario;
import com.tdex.docelar.domain.repository.ApartamentoRepository;
import com.tdex.docelar.domain.repository.PessoaRepository;
import com.tdex.docelar.domain.repository.TelefoneRepository;
import com.tdex.docelar.rest.dto.PessoaDTO;
import com.tdex.docelar.rest.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@Autowired
	private UsuarioServiceConfig usuarioService;

	@Override
	public Pessoa salvarPessoa(PessoaDTO dto) {
		List<Telefone> telefones = new ArrayList<>();

		Usuario usuario = Usuario.builder()
									.email(dto.getEmail())
									.senha(dto.getSenha())
									.build();

		Apartamento apartamento = apartamentoRepository.findById(dto.getEndereco())
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));

		Pessoa pessoa = Pessoa.builder()
								.nome(dto.getNome())
								.endereco(apartamento)
								.usuario(usuario)
								.build();

		if (dto.getTelefone() != null && !dto.getTelefone().isEmpty()) {
			dto.getTelefone().forEach(tel -> {
				Telefone telefone = Telefone.builder()
						.ddd(tel.getDdd())
						.numero(tel.getNumero())
						.pessoa(pessoa)
						.build();

				telefones.add(telefone);
			});
		}

		pessoa.setTelefone(telefones);

		Pessoa savedPessoa = pessoaRepository.save(pessoa);
		telefoneRepository.saveAll(telefones);
		usuarioService.salvar(usuario);

		return savedPessoa;
	}

	@Override
	public void deletePessoa(Integer id) {
		pessoaRepository.findById(id).map(pessoa -> {
			pessoaRepository.deleteById(pessoa.getId());
			return pessoa;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada."));
	}

	@Override
	public List<Pessoa> find(Pessoa filtro) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

		Example<Pessoa> example = Example.of(filtro, matcher);

		return pessoaRepository.findAll(example);
	}

	@Override
	public Pessoa getPessoa(Integer id) {
		return pessoaRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada."));
	}

}
