package com.tdex.docelar.rest.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApartamentoDTO {

	@NotNull
	private Integer predio;

	@NotNull
	private Integer numero;

	@NotNull
	private Integer andar;

	@NotNull
	private Integer vagas;
}
