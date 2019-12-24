package com.paulo.tarefas.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paulo.tarefas.api.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);

	Optional<Usuario> findByEmailAndAtivo(String email, boolean ativo);
}
