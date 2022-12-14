package com.generation.bemfazer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.bemfazer.model.Servicos;

@Repository
public interface ServicosRepository extends JpaRepository<Servicos, Long> {

	public List<Servicos> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);

}
