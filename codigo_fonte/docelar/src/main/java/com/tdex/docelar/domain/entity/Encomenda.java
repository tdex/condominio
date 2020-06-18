package com.tdex.docelar.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Encomenda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Descrição é obrigatório.")
	private String descricao;

	@NotNull(message = "Necessário indicar a hora da entrada da encomenda.")
	private LocalDateTime horaEntrada;

	private LocalDateTime horaSaida;

	@OneToOne
	@JoinColumn(name = "apartamento_id")
	@NotNull(message = "Necessário indicar a qual apartamento a encomenda pertence.")
	private Apartamento destinatario;

	private String recebedor;

}
