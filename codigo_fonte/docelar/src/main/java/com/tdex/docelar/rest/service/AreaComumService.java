package com.tdex.docelar.rest.service;

import java.util.List;

import com.tdex.docelar.domain.entity.AgendamentoAreaComum;
import com.tdex.docelar.domain.entity.AreaComum;
import com.tdex.docelar.domain.enums.StatusAgendamentoEnum;
import com.tdex.docelar.rest.dto.AgendamentoDTO;

public interface AreaComumService {

	 AreaComum salvarArea(AreaComum area);

	 AreaComum getArea(Integer id);

	 void deleteArea(Integer id);

	 List<AreaComum> listAreaComum();

	 AreaComum updateArea(Integer id, AreaComum areaAtualizada);

	 AgendamentoAreaComum agendarArea(AgendamentoDTO agendamento);

	 List<AgendamentoAreaComum> listAllAgendamentos();

	 void updateStatusPedido(Integer id, StatusAgendamentoEnum novoStaus);

}
