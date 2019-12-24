package com.paulo.tarefas.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDto {

	@NotEmpty(message = "Email nao pode ser vazio")
	@Email(message = "Email invalido.")
	private String email;
	
	@NotEmpty(message = "Senha nao pode ser vazia.")
	private String senha;
	
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
