package com.paulo.tarefas.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulo.tarefas.api.dtos.TarefaDto;
import com.paulo.tarefas.api.entity.Tarefa;
import com.paulo.tarefas.api.service.TarefaService;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;

	@GetMapping()
	@PreAuthorize("hasAnyRole('USUARIO')")
	@ApiOperation(value = "Retorna uma lista paginada de tarefas do usuario autenticado.")
	public ResponseEntity<Page<TarefaDto>> paginarTarefasUsuario(Authentication authentication,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pageable) {
		Page<Tarefa> tarefas = tarefaService.paginarTarefasPorUsuarioId(authentication.getName(), pageable);
		return ResponseEntity.ok(tarefas.map(task -> this.converterTarefaParaDto(task)));
	}
	
	@GetMapping("/status/{status}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	@ApiOperation(value = "Retorna uma lista paginada de tarefas do usuario autenticado.")
	public ResponseEntity<Page<TarefaDto>> paginarTarefasUsuarioPorStatus(Authentication authentication,
			@PageableDefault(sort = "id", direction = Direction.DESC, page = 0, size = 10) Pageable pageable, @PathVariable String status) throws NotFoundException {
		Page<Tarefa> tarefas = tarefaService.paginarTarefasPorUsuarioIdEStatus(authentication.getName(), pageable, status);
		return ResponseEntity.ok(tarefas.map(task -> this.converterTarefaParaDto(task)));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('USUARIO')")
	@ApiOperation(value = "Cadastra uma tarefa para o usuario autenticado.")
	public ResponseEntity<?> cadastrar(@Valid @RequestBody TarefaDto tarefaDto, BindingResult result,
			Authentication authentication) throws NotFoundException {
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(errors);
		}	
		return ResponseEntity.ok(this.converterTarefaParaDto(tarefaService.persistirTarefa(authentication.getName(), tarefaDto)));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	@ApiOperation(value = "Altera os dados de uma tarefa do usuario autenticado.")
	public ResponseEntity<?> editar(@PathVariable("id") Long id,
			@Valid @RequestBody TarefaDto tarefaDto, BindingResult result, Authentication authentication) throws NotFoundException {
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(errors);
		}
		tarefaDto.setId(Optional.of(id));
		return ResponseEntity.ok(this.converterTarefaParaDto(tarefaService.persistirTarefa(authentication.getName(), tarefaDto)));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	@ApiOperation(value = "Retorna uma tarefa do usuario autenticado por id.")
	public ResponseEntity<TarefaDto> obterPorIdTarefa(@PathVariable("id") Long id,
			Authentication authentication) throws NotFoundException {
		return ResponseEntity.ok(this.converterTarefaParaDto(this.tarefaService.buscarPorId(authentication.getName(),id)));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('USUARIO')")
	@ApiOperation(value = "Remove uma tarefa do usuario autenticado pelo id.")
	public ResponseEntity<?> delete(@PathVariable("id") Long id, Authentication authentication) throws NotFoundException {
		this.tarefaService.removerTarefa(authentication.getName(),id);
		return ResponseEntity.ok().build();
	}

	private TarefaDto converterTarefaParaDto(Tarefa tarefa) {
		TarefaDto dto = new TarefaDto();
		dto.setId(Optional.of(tarefa.getId()));
		dto.setDescricao(tarefa.getDescricao());
		dto.setStatus(tarefa.getStatus().toString());
		dto.setDataCriacao(tarefa.getDataCriacao().toString());
		dto.setDataAtualizacao(tarefa.getDataAtualizacao().toString());
		dto.setUsuario(tarefa.getUsuario().getNome());
		return dto;
	}

}
