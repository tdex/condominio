package com.tdex.docelar.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Veiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String placa;

	@OneToOne
	@JoinColumn(name = "apartamento_id")
	private Apartamento apartamento;

}
