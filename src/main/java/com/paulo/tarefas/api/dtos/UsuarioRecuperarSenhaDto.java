package com.paulo.tarefas.api.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UsuarioRecuperarSenhaDto {

	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String senha;
	
	@NotEmpty
	private String codigo;

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
