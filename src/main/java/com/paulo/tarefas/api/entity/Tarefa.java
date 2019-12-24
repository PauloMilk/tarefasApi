package com.paulo.tarefas.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_tarefa")
public class Tarefa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cd_tarefa")
	private Long id;
	
	@Column(name = "ds_tarefa")
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao")
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_atualizacao")
	private Date dataAtualizacao;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ic_status", nullable = false)
	private StatusTarefa status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cd_usuario")
	private Usuario usuario;
	
	
	
	public StatusTarefa getStatus() {
		return status;
	}

	public void setStatus(StatusTarefa status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tarefa() {
	
	}

	@PrePersist
	public void dataCriacao() {
		Date data = new Date();
		dataCriacao = data;
		dataAtualizacao = data;
	}
	
	@PreUpdate
	public void dataAtualizacao() {
		dataAtualizacao = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
	
	
}
