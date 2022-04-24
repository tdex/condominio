package com.tdex.docelar.rest.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Veiculo;
import com.tdex.docelar.domain.repository.ApartamentoRepository;
import com.tdex.docelar.domain.repository.VeiculoRepository;
import com.tdex.docelar.rest.dto.VeiculoDTO;
import com.tdex.docelar.rest.service.VeiculoService;

@Service
public class VeiculoServiceImpl implements VeiculoService {

	@Autowired
	private VeiculoRepository veiculoRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

	@Override
	public Veiculo salvarVeiculo(VeiculoDTO dto) {
		return apartamentoRepository.findById(dto.getApartamento()).map(ap -> {
			Optional<List<Veiculo>> veiculosNoApartamento = buscarVeiculoPorApartamento(ap.getId());

			if (buscarVeiculoPorPlaca(dto.getPlaca()).isPresent()) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Veículo já está cadastrado.");
			}

			veiculosNoApartamento.ifPresent(list -> {
				if(list.size() >= ap.getVagasGaragem()) {
					throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apartamento não possui mais vagas de garagem.");
				}
			});

			Veiculo veiculo = Veiculo.builder()
										.apartamento(ap)
										.placa(dto.getPlaca().toUpperCase())
										.build();

			return veiculoRepository.save(veiculo);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado. Não foi possível inserir o veículo."));
	}

	@Override
	public Optional<List<Veiculo>> buscarVeiculoPorApartamento(Integer id) {
		return apartamentoRepository.findById(id).map(veiculoRepository::findByApartamento);
	}

	@Override
	public Optional<Veiculo> buscarVeiculoPorPlaca(String placa) {
		return veiculoRepository.findByPlacaIgnoreCase(placa);
	}

	@Override
	public void deleteVeiculo(Integer id) {
		veiculoRepository.findById(id).map(veiculo -> {
			veiculoRepository.deleteById(id);
			return id;
		}).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado."));
	}

}
