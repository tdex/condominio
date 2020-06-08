package com.tdex.docelar.rest.dto;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApartamentoDTO {

	@NotNull(message = "Defina o prédio que esse apartamento pertence.")
	private Integer predio;

	@NotNull(message = "Defina o numero do apartamento.")
	private Integer numero;
	
	private String complemento;

	@NotNull(message = "Defina o andar do apartamento.")
	private Integer andar;

	@NotNull(message = "Defina a quantidade de vagas na garagem esse apartamento possui.")
	private Integer vagas;
}
