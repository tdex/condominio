package com.tdex.docelar.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.tdex.docelar.domain.entity.AreaComum;
import com.tdex.docelar.rest.service.AreaComumService;

@RestController
@RequestMapping("/api/area")
public class AreaComumController {

	@Autowired
	private AreaComumService service;

	@PostMapping
	public AreaComum salvar(@RequestBody @Valid AreaComum area) {
		return service.salvarArea(area);
	}

	@GetMapping
	public List<AreaComum> listarTodasAreasComuns() {
		List<AreaComum> areas = service.listAreaComum();

		if (areas.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma área comum cadastrada.");
		}

		return areas;
	}

	@GetMapping("{id}")
	public AreaComum getArea(@PathVariable Integer id) {
		return service.getArea(id);
	}

	@PutMapping("{id}")
	public AreaComum updateArea(@PathVariable Integer id, @RequestBody @Valid AreaComum area) {
		return service.updateArea(id, area);
	}
}
