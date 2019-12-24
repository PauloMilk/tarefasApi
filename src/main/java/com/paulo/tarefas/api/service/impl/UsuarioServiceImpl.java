package com.paulo.tarefas.api.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.paulo.tarefas.api.entity.PerfilTipo;
import com.paulo.tarefas.api.entity.Usuario;
import com.paulo.tarefas.api.repository.UsuarioRepository;
import com.paulo.tarefas.api.service.UsuarioService;
import com.paulo.tarefas.api.utils.PasswordUtils;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	
	@Autowired
	ActiveMQConsumer mq;

	public Optional<Usuario> buscarPorEmail(String email) {
		return Optional.ofNullable(usuarioRepository.findByEmail(email));
	}

	public Usuario buscarPorEmailEAtivo(String email) {
		return usuarioRepository.findByEmailAndAtivo(email, true)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));
	}

	@Override
	public void salvarUsuario(Usuario usuario){
		String pass = PasswordUtils.gerarBCrypt(usuario.getSenha());
		usuario.setSenha(pass);
		usuario.setPerfil(PerfilTipo.ROLE_USUARIO);
		usuario.setAtivo(false);
		try {
			usuarioRepository.save(usuario);
			this.emailDeConfirmacaoDeCadastro(usuario.getEmail());
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("Email ja cadastrado!");
		}
	}

	private void emailDeConfirmacaoDeCadastro(String email) {
		mq.sendMessage(new String[] {email, "enviarEmailConfirmacao"});
//		String codigo = Base64Utils.encodeToString(email.getBytes());
//		emailService.enviarPedidoDeConfirmacaoDeCadastro(email, codigo);
	}

	public void ativarCadastro(String codigo) {
		String email = new String(Base64Utils.decodeFromString(codigo));
		Optional<Usuario> usuario = buscarPorEmail(email);
		if (usuario.isPresent() && !usuario.get().isAtivo()) {
			usuario.get().setAtivo(true);
			usuarioRepository.save(usuario.get());
		}else {
			throw new UsernameNotFoundException("Codigo de ativacao incorreto.");
		}

	}

	@Override
	public void pedidoRedefinirSenha(String email) {
		Usuario usuario = buscarPorEmailEAtivo(email);
		String verificador = RandomStringUtils.randomAlphanumeric(6);
		usuario.setCodigoSeguranca(verificador);
		usuarioRepository.save(usuario);
		mq.sendMessage(new String[] {email, "enviarEmailRecuperacao", verificador});
		//emailService.enviarPedidoDeRedefinicaoDeSenha(email, verificador);
	}

	@Override
	public void recuperarSenha(String email, String senha, String codigoSeguranca) {
		Usuario usuario = buscarPorEmailEAtivo(email);
		if(usuario.getCodigoSeguranca().equals(codigoSeguranca)) {
			usuario.setSenha(PasswordUtils.gerarBCrypt(senha));
			usuario.setCodigoSeguranca(null);
			usuarioRepository.save(usuario);	
		}else {
			throw new AccessDeniedException("Codigo de Seguranca incorreto.");
		}
		
	}

}
