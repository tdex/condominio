package com.tdex.docelar.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Veiculo;
import com.tdex.docelar.rest.dto.VeiculoDTO;
import com.tdex.docelar.rest.service.VeiculoService;

@RestController
@RequestMapping("/api/veiculo")
public class VeiculoController {

	@Autowired
	private VeiculoService service;

	@PostMapping
	public Veiculo add(@RequestBody @Valid VeiculoDTO dto) {
		return service.salvarVeiculo(dto);
	}

	@GetMapping("{ap}")
	public List<Veiculo> buscarVeiculosPorApartamento(@PathVariable Integer ap) {
		return service.buscarVeiculoPorApartamento(ap)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum veículo encontrado para o apartamento.") );
	}

	@GetMapping
	public Veiculo buscarPlaca(@RequestParam String placa) {
		return service.buscarVeiculoPorPlaca(placa).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Placa não encontrada."));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerVeiculo(@PathVariable Integer id) {
		service.deleteVeiculo(id);
	}
}
