package com.tdex.docelar.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.domain.entity.Pessoa;
import com.tdex.docelar.domain.entity.Telefone;
import com.tdex.docelar.domain.entity.Usuario;
import com.tdex.docelar.domain.repository.ApartamentoRepository;
import com.tdex.docelar.domain.repository.PessoaRepository;
import com.tdex.docelar.domain.repository.TelefoneRepository;
import com.tdex.docelar.rest.dto.PessoaDTO;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private TelefoneRepository telefoneRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@Autowired
	private UsuarioServiceImpl usuarioService;

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

		dto.getTelefone().forEach(tel -> {
			Telefone telefone = Telefone.builder()
					.ddd(tel.getDdd())
					.numero(tel.getNumero())
					.pessoa(pessoa)
					.build();

			telefones.add(telefone);
		});

		pessoa.setTelefone(telefones);

		Pessoa savedPessoa = pessoaRepository.save(pessoa);
		telefoneRepository.saveAll(telefones);
		usuarioService.salvar(usuario);

		return savedPessoa;
	}
}
