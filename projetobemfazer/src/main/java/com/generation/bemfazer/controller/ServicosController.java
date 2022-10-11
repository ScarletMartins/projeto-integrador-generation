package com.generation.bemfazer.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.bemfazer.model.Servicos;
import com.generation.bemfazer.repository.CategoriasRepository;
import com.generation.bemfazer.repository.ServicosRepository;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ServicosController {

	@Autowired
	private ServicosRepository servicosRepository;
	
	@Autowired
	private CategoriasRepository categoriasRepository;

	@GetMapping
	public ResponseEntity<List<Servicos>> getAll() {
		return ResponseEntity.ok(servicosRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Servicos> getById(@PathVariable Long id) {
		return servicosRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Servicos>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(servicosRepository.findAllByTituloContainingIgnoreCase(titulo));

	}

	@PostMapping
	public ResponseEntity<Servicos> post(@Valid @RequestBody Servicos servicos) {
		if (categoriasRepository.existsById(servicos.getCategorias().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(servicosRepository.save(servicos));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	}

	@PutMapping
	public ResponseEntity<Servicos> put(@Valid @RequestBody Servicos servicos) {
		if (servicosRepository.existsById(servicos.getId())) {
			
			if (categoriasRepository.existsById(servicos.getCategorias().getId()))
				return ResponseEntity.status(HttpStatus.OK)
					.body(servicosRepository.save(servicos));
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Servicos> servicos = servicosRepository.findById(id);

		if (servicos.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		servicosRepository.deleteById(id);

	}
}
