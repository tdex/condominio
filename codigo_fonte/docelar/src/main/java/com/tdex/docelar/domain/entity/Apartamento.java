package com.tdex.docelar.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Apartamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "predio_id")
	@NotNull(message = "Necessário definir o prédio em que se encontra o apartamento.")
	private Predio predio;

	@NotNull(message = "Numero do apartamento é obrigatório.")
	private Integer numero;

	private String complemento;

	@NotNull(message = "Andar do apartamento é obrigatório.")
	private Integer andar;

	@OneToMany(mappedBy = "endereco", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Pessoa> residente;

	@OneToMany(mappedBy = "apartamento", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Veiculo> veiculo;

	@NotNull(message = "Quantidade de vagas de garagem é obrigatório.")
	private Integer vagasGaragem;

	@OneToMany(mappedBy = "destinatario", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Encomenda> encomendas;

}
