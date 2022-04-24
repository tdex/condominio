package com.tdex.docelar.rest.service;

import java.util.List;

import com.tdex.docelar.domain.entity.Encomenda;
import com.tdex.docelar.rest.dto.EncomendaDTO;
import com.tdex.docelar.rest.dto.EncomendaRecebimentoDTO;

public interface EncomendaService {

	List<Encomenda> listAll();

	Encomenda save(EncomendaDTO dto);

	Encomenda saida(Integer id, EncomendaRecebimentoDTO recebedor);

	List<Encomenda> listarEncomendasAbertasPorApartamento(Integer idApartamento);

	List<Encomenda> listarTodasEncomendasPorApartamento(Integer idApartamento);

}
