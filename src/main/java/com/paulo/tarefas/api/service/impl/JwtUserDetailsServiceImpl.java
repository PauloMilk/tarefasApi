package com.paulo.tarefas.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paulo.tarefas.api.entity.Usuario;
import com.paulo.tarefas.api.security.config.JwtUserFactory;
import com.paulo.tarefas.api.service.UsuarioService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioService.buscarPorEmail(username);
		if(usuario.isPresent()) {
			if(usuario.get().isAtivo()) {
				return JwtUserFactory.create(usuario.get());
			}
			throw new BadCredentialsException("Usuario nao esta ativo. Confirme seu email.");
		}else {
			throw new UsernameNotFoundException("Usuario ou senha incorretos.");
		}
		
	}

}
