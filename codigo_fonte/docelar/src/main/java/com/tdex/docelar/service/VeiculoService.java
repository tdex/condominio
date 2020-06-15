package com.tdex.docelar.service;

import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.generic.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Veiculo;
import com.tdex.docelar.domain.repository.ApartamentoRepository;
import com.tdex.docelar.domain.repository.VeiculoRepository;
import com.tdex.docelar.rest.dto.VeiculoDTO;

@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepository veiculoRepository;

	@Autowired
	private ApartamentoRepository apartamentoRepository;

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

	public Optional<List<Veiculo>> buscarVeiculoPorApartamento(Integer id) {
		return apartamentoRepository.findById(id).map(veiculoRepository::findByApartamento);
	}

	public Optional<Veiculo> buscarVeiculoPorPlaca(String placa) {
		return veiculoRepository.findByPlacaIgnoreCase(placa);
	}

	public void deleteVeiculo(Integer id) {
		veiculoRepository.findById(id).map(veiculo -> {
			veiculoRepository.deleteById(id);
			return Type.VOID;
		}).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado."));
	}
}
