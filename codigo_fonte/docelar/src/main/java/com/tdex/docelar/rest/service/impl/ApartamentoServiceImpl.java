package com.tdex.docelar.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.domain.entity.Predio;
import com.tdex.docelar.domain.repository.ApartamentoRepository;
import com.tdex.docelar.domain.repository.PredioRepository;
import com.tdex.docelar.rest.dto.ApartamentoDTO;
import com.tdex.docelar.rest.service.ApartamentoService;

@Service
public class ApartamentoServiceImpl implements ApartamentoService {

	@Autowired
	private ApartamentoRepository repository;

	@Autowired
	private PredioRepository predioRepository;

	@Override
	public List<Apartamento> listarTodosApartamentos() {
		return repository.findAll();
	}

	@Override
	public Apartamento salvar(ApartamentoDTO ap) {
		Predio predio = predioRepository.findById(ap.getPredio())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						"Não foi possível inserir apartamento no prédio indicado."));

		Apartamento apartamento = Apartamento.builder()
												.predio(predio)
												.numero(ap.getNumero())
												.andar(ap.getAndar())
												.vagasGaragem(ap.getVagas())
												.complemento(ap.getComplemento())
												.build();

		return repository.save(apartamento);
	}

	@Override
	public Apartamento buscarApartamento(Integer id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));
	}

	@Override
	public void deleteApartamento(Integer id) {
		repository.findById(id).map(ap -> {
			repository.deleteById(ap.getId());
			return id;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Não foi possível excluir apartamento. Código inválido!"));
	}

	@Override
	public void atualizarVagasGaragem(Integer idAp, Integer nrVagas) {
		repository.findById(idAp).map(ap -> {
			ap.setVagasGaragem(nrVagas);
			return repository.save(ap);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Apartamento não encontrado."));
	}

}
