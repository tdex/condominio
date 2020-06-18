package com.tdex.docelar.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty(message = "E-mail é obrigatório.")
	private String email;

	private String senha;

	@Column(nullable = false, columnDefinition = "TINYINT", length = 1)
	private boolean admin;
}
