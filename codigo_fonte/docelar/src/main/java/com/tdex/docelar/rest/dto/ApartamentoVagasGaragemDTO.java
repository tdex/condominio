package com.tdex.docelar.rest.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApartamentoVagasGaragemDTO {

	@NotNull
	public Integer vagas;
	
}
