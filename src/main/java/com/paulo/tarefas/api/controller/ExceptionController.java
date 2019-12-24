package com.paulo.tarefas.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.paulo.tarefas.api.entity.ApiError;
import com.paulo.tarefas.api.exception.ResourceNotFoundException;


@RestControllerAdvice
public class ExceptionController {

	
	@ExceptionHandler(Exception.class) 
	public final ResponseEntity<ApiError> handleException(final Exception exception, final HttpServletRequest request) {
		return new ResponseEntity<ApiError>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Exception para not found
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ResponseEntity<ApiError> handleResourceNotFound(final ResourceNotFoundException exception,
			final HttpServletRequest request) {
		return new ResponseEntity<ApiError>(new ApiError(HttpStatus.NOT_FOUND, exception.getMessage()), HttpStatus.NOT_FOUND);
	}
	
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public @ResponseBody ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, HttpServletResponse response){
        System.out.println(ex.getMessage());
    	return new ResponseEntity<ApiError>(new ApiError(HttpStatus.FORBIDDEN, ex.getMessage()), HttpStatus.FORBIDDEN);
    }
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	public @ResponseBody ResponseEntity<ApiError> handleAccessDenied(final AccessDeniedException exception,
			final HttpServletRequest request) {
		return new ResponseEntity<ApiError>(new ApiError(HttpStatus.FORBIDDEN, exception.getMessage()), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody ResponseEntity<ApiError> handleUsernameNotFound(final UsernameNotFoundException exception,
			final HttpServletRequest request) {
		 return new ResponseEntity<ApiError>(new ApiError(HttpStatus.UNAUTHORIZED, exception.getMessage()), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public @ResponseBody ResponseEntity<ApiError> handleBadCredencials(final BadCredentialsException exception,
			final HttpServletRequest request) {

		 return new ResponseEntity<ApiError>(new ApiError(HttpStatus.UNAUTHORIZED, exception.getMessage().equals("Bad credentials") ? "Usuario ou Senha incorretos." : exception.getMessage()), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ResponseEntity<ApiError> handleResourceBadRequest(final DataIntegrityViolationException exception,
			final HttpServletRequest request) {
		return new ResponseEntity<ApiError>(new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	
	

	
}
