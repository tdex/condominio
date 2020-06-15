package com.tdex.docelar.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.domain.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {

	public List<Veiculo> findByApartamento(Apartamento ap);

	public Optional<Veiculo> findByPlacaIgnoreCase(String placa);
}
