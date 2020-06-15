package com.tdex.docelar.rest.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PessoaDTO {

	private String nome;

	private Integer endereco;

	private List<TelefoneDTO> telefone;

	private String email;

	private String senha;

}
