package com.tdex.docelar.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaComum implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Nome da área é obrigatório.")
	private String nome;

	private BigDecimal preco;

	@NotNull(message = "Defina o periodo do agendamento em minutos.")
	private Integer periodo;

	@OneToMany(mappedBy = "area")
	@JsonIgnore
	private List<AgendamentoAreaComum> agendamento;
}
