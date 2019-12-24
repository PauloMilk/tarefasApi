package com.paulo.tarefas.api.dtos;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class TarefaDto {

	private Optional<Long> id = Optional.empty();

	@NotEmpty(message = "A descricao nao pode estar vazia.")
	@Length(min = 3, max = 200, message = "A descricao deve conter entre 3 a 200 caracteres.")
	private String descricao;

	private String dataCriacao;

	private String dataAtualizacao;

	@NotEmpty(message = "Status nao pode estar vazio.")
	private String status;

	private String usuario;

	public TarefaDto() {
	}

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(String dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(String dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
