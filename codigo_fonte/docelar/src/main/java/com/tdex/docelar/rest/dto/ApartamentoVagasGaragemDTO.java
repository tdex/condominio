package com.tdex.docelar.rest.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartamentoVagasGaragemDTO {

	@NotNull
	public Integer vagas;
	
}
