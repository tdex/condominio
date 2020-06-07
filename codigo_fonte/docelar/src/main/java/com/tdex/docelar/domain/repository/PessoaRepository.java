package com.tdex.docelar.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdex.docelar.domain.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
