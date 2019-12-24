package com.paulo.tarefas.api.controller;

import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulo.tarefas.api.dtos.JwtAuthenticationDto;
import com.paulo.tarefas.api.dtos.TokenDto;
import com.paulo.tarefas.api.response.Response;
import com.paulo.tarefas.api.utils.JwtTokenUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	private static final String TOKEN_HEADER = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping
	@ApiOperation(value = "Retorna o token da autenticacao da api.")
	public ResponseEntity<TokenDto> gerarTokenJwt(@Valid @RequestBody JwtAuthenticationDto authenticationDto
			) throws AuthenticationException {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationDto.getEmail(), authenticationDto.getSenha()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDto.getEmail());
		String token = jwtTokenUtil.getToken(userDetails);

		return ResponseEntity.ok(new TokenDto(token));
	}

	@PostMapping("/refresh")
	@ApiOperation(value = "Retorna um novo token de autenticacao da api.")
	public ResponseEntity<?> gerarRefreshTokenJwt(HttpServletRequest request) {
		Response<TokenDto> response = new Response<TokenDto>();
		Optional<String> token = Optional.ofNullable(request.getHeader(TOKEN_HEADER));

		if (token.isPresent() && token.get().startsWith(BEARER_PREFIX)) {
			token = Optional.of(token.get().substring(7));
		}

		if (!token.isPresent()) {
			response.getErrors().add("Token invalida ou expirado.");
		} else if (!jwtTokenUtil.tokenValid(token.get())) {
			response.getErrors().add("Token invalido ou expirado.");
		}

		if (!response.getErrors().isEmpty()) {
			return ResponseEntity.badRequest().body(response.getErrors());
		}

		String refreshToken = jwtTokenUtil.refreshToken(token.get());

		return ResponseEntity.ok(new TokenDto(refreshToken));
	}
}
