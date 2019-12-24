package com.paulo.tarefas.api.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paulo.tarefas.api.dtos.TarefaDto;
import com.paulo.tarefas.api.entity.StatusTarefa;
import com.paulo.tarefas.api.entity.Tarefa;
import com.paulo.tarefas.api.entity.Usuario;
import com.paulo.tarefas.api.exception.ResourceNotFoundException;
import com.paulo.tarefas.api.repository.TarefaRepository;
import com.paulo.tarefas.api.service.TarefaService;
import com.paulo.tarefas.api.service.UsuarioService;

import javassist.NotFoundException;

@Service
public class TarefaServiceImpl implements TarefaService {

	@Autowired
	private TarefaRepository tarefaRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	@Transactional(readOnly = false)
	public Tarefa persistirTarefa(String email, TarefaDto tarefaDto) {
		Tarefa tarefa = this.converterDtoParaTarefa(email, tarefaDto);
		return tarefaRepository.save(tarefa);
	}

	@Override
	@Transactional(readOnly = false)
	public void removerTarefa(String email,Long id) throws NotFoundException {
		Tarefa tarefa = this.buscarPorId(email, id);
		tarefaRepository.deleteById(tarefa.getId());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Tarefa> paginarTarefasPorUsuarioId(String email, Pageable pageable) {
		return tarefaRepository.findByUsuarioEmail(email, pageable);
	}

	@Transactional(readOnly = true)
	public Tarefa buscarPorId(String email, Long id) {
		Tarefa tarefa = tarefaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tarefa nao encontrada."));
		return verificaDonoDaTarefa(tarefa, email);
	}

	private Tarefa converterDtoParaTarefa(String email, TarefaDto tarefaDto) {

		Tarefa tarefa = new Tarefa();
		if (tarefaDto.getId().isPresent()) {
			tarefa = this.buscarPorId(email, tarefaDto.getId().get());
		} else {
			Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);
			if (!usuario.isPresent()) {
				throw new ResourceNotFoundException("Usuario nao encontrado.");
			} else {
				tarefa.setUsuario(usuario.get());
			}
		}

		tarefa.setDescricao(tarefaDto.getDescricao());
		this.verificaStatus(tarefa, tarefaDto.getStatus());
		return verificaDonoDaTarefa(tarefa, email);
	}

	private void verificaStatus(Tarefa tarefa, String status) {
		if (EnumUtils.isValidEnum(StatusTarefa.class, status)) {
			tarefa.setStatus(StatusTarefa.valueOf(status));
		} else {
			throw new ResourceNotFoundException("Status invalido.");
		}
	}

	private Tarefa verificaDonoDaTarefa(Tarefa tarefa, String email) {
		if (!tarefa.getUsuario().getEmail().equals(email)) {
			throw new AccessDeniedException("Voce nao possui acesso a essa tarefa.");
		}
		return tarefa;
	}

	@Override
	public Page<Tarefa> paginarTarefasPorUsuarioIdEStatus(String email, Pageable pageable, String status) throws NotFoundException {
		Tarefa tarefa = new Tarefa();
		this.verificaStatus(tarefa, status.toUpperCase());
		return tarefaRepository.findByUsuarioEmailAndStatus(email, pageable,tarefa.getStatus());
	}

}
