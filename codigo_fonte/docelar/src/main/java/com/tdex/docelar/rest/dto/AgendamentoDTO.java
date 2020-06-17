package com.tdex.docelar.rest.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoDTO {

	@NotNull
	private Integer area;

	@NotNull
	private String data;

	@NotNull
	private Integer qtdPeriodo;

	@NotNull
	private Integer pessoa;

}
