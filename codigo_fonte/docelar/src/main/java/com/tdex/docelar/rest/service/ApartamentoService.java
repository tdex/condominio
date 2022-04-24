package com.tdex.docelar.rest.service;

import java.util.List;

import com.tdex.docelar.domain.entity.Apartamento;
import com.tdex.docelar.rest.dto.ApartamentoDTO;

public interface ApartamentoService {

	List<Apartamento> listarTodosApartamentos();

	Apartamento salvar(ApartamentoDTO dto);

	Apartamento buscarApartamento(Integer id);

	void deleteApartamento(Integer id);

	void atualizarVagasGaragem(Integer idAp, Integer nrVagas);

}
