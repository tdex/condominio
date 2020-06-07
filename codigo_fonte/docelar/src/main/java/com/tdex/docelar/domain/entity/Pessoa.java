package com.tdex.docelar.domain.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "apartamento_id")
	private Apartamento endereco; 
	
	@OneToMany(mappedBy = "pessoa")
	private List<Telefone> telefone;
	
	private String email;
	
	private String senha;
}
