package com.tdex.docelar.domain.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Apartamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "predio_id")
	@NotNull
	private Predio predio;

	@NotNull
	private Integer numero;

	private String complemento;

	@NotNull
	private Integer andar;

	@OneToMany(mappedBy = "endereco")
	@JsonIgnore
	private List<Pessoa> residente;
	
	@OneToMany(mappedBy = "apartamento")
	@JsonIgnore
	private List<Veiculo> veiculo;
	
	@NotNull
	private Integer vagasGaragem;

}
