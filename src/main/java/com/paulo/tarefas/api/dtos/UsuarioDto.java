package com.paulo.tarefas.api.dtos;

import java.util.Optional;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UsuarioDto {

	private Optional<Long> id = Optional.empty();
	
	@NotEmpty(message = "Nome nao pode estar vazio.")
	private String nome;
	
	@NotEmpty(message = "Email nao pode estar vazio.")
	@Email(message = "Email invalido.")
	private String email;
	
	@NotEmpty(message = "Senha nao pode estar vazia.")
	private String senha;

	public Optional<Long> getId() {
		return id;
	}

	public void setId(Optional<Long> id) {
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

}
