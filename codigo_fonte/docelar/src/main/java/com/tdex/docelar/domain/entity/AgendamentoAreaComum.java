package com.tdex.docelar.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.tdex.docelar.domain.enums.StatusAgendamentoEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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
