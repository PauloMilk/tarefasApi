package com.paulo.tarefas.api.service;

import java.util.Optional;

import com.paulo.tarefas.api.entity.Usuario;

public interface UsuarioService {

	Optional<Usuario> buscarPorEmail(String email);
	
	void salvarUsuario(Usuario usuario);
	
	void ativarCadastro(String codigo);
	
	void pedidoRedefinirSenha(String email);

	void recuperarSenha(String email, String senha, String codigoSeguranca);
}
