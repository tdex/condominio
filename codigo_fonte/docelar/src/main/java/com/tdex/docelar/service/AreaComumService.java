package com.tdex.docelar.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.aspectj.apache.bcel.generic.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.AreaComum;
import com.tdex.docelar.domain.repository.AgendamentoAreaComumRepository;
import com.tdex.docelar.domain.repository.AreaComumRepository;

@Service
public class AreaComumService {

	@Autowired
	private AreaComumRepository areaRepository;

	@Autowired
	private AgendamentoAreaComumRepository agendamentoRepository;

	public AreaComum salvarArea(AreaComum area) {
		return areaRepository.save(area);
	}

	public AreaComum getArea(Integer id) {
		return areaRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

	public void deleteArea(Integer id) {
		areaRepository.findById(id).map(area -> {
			areaRepository.deleteById(area.getId());
			return Type.VOID;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

	public List<AreaComum> listAreaComum() {
		return areaRepository.findAll();
	}

	@Transactional
	public AreaComum updateArea(Integer id, @Valid AreaComum areaAtualizada) {
		return areaRepository.findById(id).map(area -> {
			areaAtualizada.setId(area.getId());
			return areaRepository.save(areaAtualizada);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Área comum não encontrada."));
	}

}
