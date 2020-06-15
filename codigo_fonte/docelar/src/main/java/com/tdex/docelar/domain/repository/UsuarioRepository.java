package com.tdex.docelar.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdex.docelar.domain.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

}
