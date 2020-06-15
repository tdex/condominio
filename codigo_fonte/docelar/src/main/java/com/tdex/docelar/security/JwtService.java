package com.tdex.docelar.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tdex.docelar.domain.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expiracao}")
	private String expiracao;

	@Value("${security.jwt.chave-assinatura}")
	private String chaveAssinatura;

	public String gerarToken(Usuario usuario) {
		Long expString = Long.valueOf(expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);

		return Jwts.builder()
					.setSubject(usuario.getEmail())
					.setExpiration(data)
					.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
					.compact();
	}

	private Claims obterClaims(String token) throws ExpiredJwtException {
		return Jwts.parser()
					.setSigningKey(chaveAssinatura)
					.parseClaimsJws(token)
					.getBody();
	}

	public boolean tokenValido(String token) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime localDateTime = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			return !LocalDateTime.now().isAfter(localDateTime);
		} catch (Exception e) {
			return false;
		}
	}

	public String obterLoginUsuario(String token) throws ExpiredJwtException {
		return obterClaims(token).getSubject();
	}
}
