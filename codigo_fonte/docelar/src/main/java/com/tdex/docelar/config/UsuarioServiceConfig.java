package com.tdex.docelar.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Usuario;
import com.tdex.docelar.domain.repository.UsuarioRepository;
import com.tdex.docelar.exception.SenhaInvalidaException;
import com.tdex.docelar.rest.dto.UsuarioDTO;

@Service
public class UsuarioServiceConfig implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario criptografarSenha(Usuario usuario) {
		String senhaCriptografada = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);

		return usuario;
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(criptografarSenha(usuario));
	}

	public UserDetails autenticar(Usuario usuario) {
		UserDetails user = loadUserByUsername(usuario.getEmail());
		boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());

		if (senhasBatem) {
			return user;
		}

		throw new SenhaInvalidaException();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository
							.findById(username)
							.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

		String[] roles = usuario.isAdmin() ?
							new String[] {"ADMIN", "USER"} :
							new String[] {"USER"};
		return User.builder()
					.username(usuario.getEmail())
					.password(usuario.getSenha())
					.roles(roles)
					.build();
	}

	public Usuario alterarSenha(UsuarioDTO dto) {
		UserDetails user = loadUserByUsername(dto.getEmail());
		boolean senhasBatem = encoder.matches(dto.getSenhaAntiga(), user.getPassword());

		if (senhasBatem) {
			Usuario usuario = Usuario.builder().email(dto.getEmail()).senha(dto.getSenhaNova()).build();
			return usuarioRepository.save(criptografarSenha(usuario));
		}

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não foi possível alterar a senha.");
	}

}
