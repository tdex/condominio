package com.tdex.docelar.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErros {

	@Getter
	public List<String> erros;

	public ApiErros(String mensagem) {
		this.erros = Arrays.asList(mensagem);
	}

	public ApiErros(List<String> erros) {
		this.erros = erros;
	}

}
