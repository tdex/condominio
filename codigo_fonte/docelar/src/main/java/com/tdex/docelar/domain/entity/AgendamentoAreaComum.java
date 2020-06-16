package com.tdex.docelar.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
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
public class AgendamentoAreaComum {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "pessoa_id")
	@NotNull(message = "Pessoa é obrigatório.")
	private Pessoa pessoa;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "area_comum_id")
	@NotNull(message = "Área é obrigatório.")
	private AreaComum area;

	@NotBlank(message = "Informe o horário do agendamento")
	private LocalDateTime horario;

	@NotNull(message = "Periodo é obrigatório.")
	private Integer qtdPeriodo;
}
