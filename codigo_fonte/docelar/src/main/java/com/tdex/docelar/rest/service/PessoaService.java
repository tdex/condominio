package com.tdex.docelar.rest.service;

import java.util.List;

import com.tdex.docelar.domain.entity.Pessoa;
import com.tdex.docelar.rest.dto.PessoaDTO;

public interface PessoaService {

	 Pessoa salvarPessoa(PessoaDTO dto);

	 void deletePessoa(Integer id);

	 List<Pessoa> find(Pessoa filtro);

	 Pessoa getPessoa(Integer id);

}
