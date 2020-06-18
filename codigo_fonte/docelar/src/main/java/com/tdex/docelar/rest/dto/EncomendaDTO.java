package com.tdex.docelar.rest.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncomendaDTO {

	@NotBlank(message = "Descrição é obrigatório.")
	private String descricao;

	private String horaEntrada;

	private String horaSaida;

	@NotNull(message = "Destinatário é obrigatório.")
	private Integer destinatario;
}
