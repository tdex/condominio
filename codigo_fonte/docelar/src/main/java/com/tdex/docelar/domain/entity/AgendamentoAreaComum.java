package com.tdex.docelar.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.tdex.docelar.domain.enums.StatusAgendamentoEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoAreaComum implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@NotNull(message = "Informe o horário de inicio.")
	private LocalDateTime horaInicio;

	@NotNull(message = "Informe o horário de inicio.")
	private LocalDateTime horaFim;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private StatusAgendamentoEnum status;

	private LocalDateTime ultimaAtualizacao;
}
