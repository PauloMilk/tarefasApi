package com.paulo.tarefas.api.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.paulo.tarefas.api.entity.PerfilTipo;
import com.paulo.tarefas.api.entity.Usuario;

public class JwtUserFactory  {

	public static JwtUser create(Usuario usuario) {
		return new JwtUser(usuario.getId(), usuario.getEmail(), usuario.getSenha(), mapToGrantedAuthorities(usuario.getPerfil()));
	}

	private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(PerfilTipo perfil) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(perfil.toString()));
		return authorities;
	}
}
