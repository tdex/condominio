package com.tdex.docelar.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private String email;
	private String senhaAntiga;
	private String senhaNova;

}
