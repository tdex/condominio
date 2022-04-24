package com.tdex.docelar.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.config.UsuarioServiceConfig;
import com.tdex.docelar.domain.entity.Usuario;
import com.tdex.docelar.exception.SenhaInvalidaException;
import com.tdex.docelar.rest.dto.TokenDTO;
import com.tdex.docelar.rest.dto.UsuarioDTO;
import com.tdex.docelar.security.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioServiceConfig usuarioService;
	private final JwtService jwtService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario salvar(@RequestBody @Valid Usuario usuario) {
		return usuarioService.salvar(usuario);
	}

	@PostMapping("/auth")
	public TokenDTO autenticar(@RequestBody Usuario credenciais) {
		try {
			usuarioService.autenticar(credenciais);

			String token = jwtService.gerarToken(credenciais);

			return new TokenDTO(credenciais.getEmail(), token);
		} catch (UsernameNotFoundException | SenhaInvalidaException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

	@PatchMapping
	public Usuario updateSenha(@RequestBody UsuarioDTO dto) {
		return usuarioService.alterarSenha(dto);
	}

}
