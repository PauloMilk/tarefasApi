package com.paulo.tarefas.api.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "cd_usuario")
	private Long id;
	
	@Column(name = "nm_usuario")
	private String nome;

	@Column(name = "cd_email", unique = true)
	private String email;
	
	@Column(name = "cd_senha")
	private String senha;
	
	@Column(name = "cd_seguranca")
	private String codigoSeguranca;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Tarefa> tarefas;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "nm_perfil", nullable = false)
	private PerfilTipo perfil;
	
	@Column(name = "ic_ativo", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean ativo;
	
	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	@JsonIgnore
	@JsonProperty(value = "tarefas")
	public List<Tarefa> getTarefas() {
		return tarefas;
	}
	

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public PerfilTipo getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilTipo perfil) {
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}
	
	
}
