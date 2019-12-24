package com.paulo.tarefas.api.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.paulo.tarefas.api.entity.StatusTarefa;
import com.paulo.tarefas.api.entity.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{

	@Query("SELECT t FROM Tarefa t where t.usuario.email = :email")
	Page<Tarefa> findByUsuarioEmail(String email, Pageable pageable);

	@Query("SELECT t FROM Tarefa t where t.usuario.email = :email AND t.status = :status")
	Page<Tarefa> findByUsuarioEmailAndStatus(String email, Pageable pageable, StatusTarefa status);

}
