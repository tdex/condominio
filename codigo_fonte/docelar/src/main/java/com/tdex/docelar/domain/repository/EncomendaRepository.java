package com.tdex.docelar.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.domain.entity.Encomenda;

public interface EncomendaRepository extends JpaRepository<Encomenda, Integer> {

	List<Encomenda> findByDestinatarioAndHoraSaidaIsNull(Apartamento apartamento);

	List<Encomenda> findByDestinatario(Apartamento apartamento);

}
