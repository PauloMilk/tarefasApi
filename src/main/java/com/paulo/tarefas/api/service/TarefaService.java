package com.paulo.tarefas.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.paulo.tarefas.api.dtos.TarefaDto;
import com.paulo.tarefas.api.entity.Tarefa;

import javassist.NotFoundException;

public interface TarefaService {

	Page<Tarefa> paginarTarefasPorUsuarioId(String email, Pageable pageable);
	
	Page<Tarefa> paginarTarefasPorUsuarioIdEStatus(String email, Pageable pageable, String status) throws NotFoundException;

	Tarefa persistirTarefa(String email, TarefaDto tarefaDto) throws NotFoundException;

	Tarefa buscarPorId(String email, Long id) throws NotFoundException;

	void removerTarefa(String email, Long id) throws NotFoundException;
	
	
}
