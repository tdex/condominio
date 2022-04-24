package com.tdex.docelar.rest.service;

import java.util.List;
import java.util.Optional;

import com.tdex.docelar.domain.entity.Veiculo;
import com.tdex.docelar.rest.dto.VeiculoDTO;

public interface VeiculoService {

	Veiculo salvarVeiculo(VeiculoDTO dto);

	Optional<List<Veiculo>> buscarVeiculoPorApartamento(Integer id);

	Optional<Veiculo> buscarVeiculoPorPlaca(String placa);

	void deleteVeiculo(Integer id);

}
