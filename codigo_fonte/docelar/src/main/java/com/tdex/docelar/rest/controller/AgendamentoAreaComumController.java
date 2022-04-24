package com.tdex.docelar.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.AgendamentoAreaComum;
import com.tdex.docelar.domain.enums.StatusAgendamentoEnum;
import com.tdex.docelar.rest.dto.AgendamentoDTO;
import com.tdex.docelar.rest.service.AreaComumService;

@RestController
@RequestMapping("/api/agendamentoarea")
public class AgendamentoAreaComumController {

	@Autowired
	private AreaComumService service;

	@PostMapping
	public AgendamentoAreaComum agendar(@RequestBody @Valid AgendamentoDTO agendamento) {
		return service.agendarArea(agendamento);
	}

	@GetMapping
	public List<AgendamentoAreaComum> todosAgendamentos() {
		List<AgendamentoAreaComum> agendamentos = service.listAllAgendamentos();

		if (agendamentos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nenhum agendamento encontrado.");
		}

		return agendamentos;
	}

	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarStatusAgendamento(@PathVariable Integer id, @RequestParam StatusAgendamentoEnum status) {
		service.updateStatusPedido(id, status);
	}
}
