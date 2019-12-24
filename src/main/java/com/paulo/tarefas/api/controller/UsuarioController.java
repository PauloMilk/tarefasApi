package com.paulo.tarefas.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paulo.tarefas.api.dtos.UsuarioDto;
import com.paulo.tarefas.api.dtos.UsuarioRecuperarSenhaDto;
import com.paulo.tarefas.api.entity.PerfilTipo;
import com.paulo.tarefas.api.entity.Usuario;
import com.paulo.tarefas.api.service.UsuarioService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	@ApiOperation(value = "Cadastra um novo usuario no sistema.")
	public ResponseEntity<?> cadastrar(@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result) {
		Usuario usuario = this.converterDtoParaUsuario(usuarioDto, result);
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(errors);
		}
		usuarioService.salvarUsuario(usuario);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/confirmacao")
	@ApiOperation(value = "Realiza a confirmacao de email do usuario.")
	public ResponseEntity<?> respostaConfirmacaoCadastroPaciente(@RequestParam("codigo") String codigo) {
		usuarioService.ativarCadastro(codigo);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/recuperacao")
	@ApiOperation(value = "Realiza a solicitacao de recuperacao de senha por email.")
	public ResponseEntity<?> solicitarRedefirnirSenha(@RequestParam String email){
		usuarioService.pedidoRedefinirSenha(email);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/recuperacao")
	@ApiOperation(value = "Realiza a recuperacao de senha por email.")
	public ResponseEntity<?> redefirnirSenha(@Valid @RequestBody UsuarioRecuperarSenhaDto usuarioDto,
			BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<String>();
			result.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(errors);
		}
		usuarioService.recuperarSenha(usuarioDto.getEmail(), usuarioDto.getSenha(), usuarioDto.getCodigo());
		return ResponseEntity.ok().build();
	}
	

	private Usuario converterDtoParaUsuario(UsuarioDto usuarioDto, BindingResult result) {
		Usuario usuario = new Usuario();
		if (usuarioDto.getId().isPresent()) {
			Optional<Usuario> user = this.usuarioService.buscarPorEmail(usuarioDto.getEmail());
			if (user.isPresent()) {
				usuario = user.get();
			} else {
				result.addError(new ObjectError("usuario", "Usuario nao encontrado."));
			}
		} else {
			usuario.setPerfil(PerfilTipo.ROLE_USUARIO);
		}
		usuario.setEmail(usuarioDto.getEmail());
		usuario.setNome(usuarioDto.getNome());
		usuario.setSenha(usuarioDto.getSenha());

		return usuario;
	}

}
