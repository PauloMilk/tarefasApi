package com.paulo.tarefas.api.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

	
	/**
	 * Gera um hash utilizando o BCrypt
	 * 
	 * @param senha
	 * @return String
	 */
	public static String gerarBCrypt(String senha) {
		if(senha == null) 
			return senha;
		BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder();
		return bEncoder.encode(senha);
	}
	
	/**
	 * Verifica se a senha e valida
	 * 
	 * @param senha
	 * @param senhaEnconded
	 * @return
	 */
	public boolean verificarSenha(String senha, String senhaEnconded) {
		return new BCryptPasswordEncoder().matches(senha, senhaEnconded);
	}
	
	
	
}
