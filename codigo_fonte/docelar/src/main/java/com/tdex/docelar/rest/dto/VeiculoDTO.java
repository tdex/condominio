package com.tdex.docelar.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VeiculoDTO {

	@NotEmpty(message = "Placa do veículo é obrigatório.")
	private String placa;

	@NotNull(message = "Necessário indicar o código do apartamento.")
	private Integer apartamento;
}
