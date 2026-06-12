package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Genero;
import com.example.demo.services.GeneroService;

@RestController
@RequestMapping("/api/generos")
public class GeneroController {

	@Autowired
	private GeneroService generoService;

	// GET /api/generos -> devuelve la lista completa en JSON

	@GetMapping
	public List<Genero> listar() {
		return generoService.findAll();
	}

	// GET /api/generos/3 -> devuelve el género con id=3, o 404 si no existe
	@GetMapping("/{id}")
	public ResponseEntity<Genero> buscarPorId(@PathVariable Long id) {
		return generoService.findById(id).map(ResponseEntity::ok) // Si existe devolverá 200 + JSON
				.orElse(ResponseEntity.notFound().build()); // Si no, devuelve 404
	}

	// POST /api/generos -> crea un nuevo género a partir del JSON recibido
	// Devuelve 201 Created con el género ya guardado (incluye el ID generado)
	@PostMapping
	@ResponseStatus
	public Genero crear(@Valid @RequestBody Genero genero) {
		return generoService.save(genero);
	}

	// PUT /api/generos/3 -> reemplaza los datos del género con id=3
	@PutMapping("/{id}")
	public ResponseEntity<Genero> actualizar(@PathVariable Long id, @Valid @RequestBody Genero datos) {
		return generoService.findById(id).map(genero -> {
			genero.setNombre(datos.getNombre()); // Actualizamos el campo
			return ResponseEntity.ok(generoService.save(genero));
		}).orElse(ResponseEntity.notFound().build());
	}

	// DELETE /api/generos/3 -> borra el género con id=3
	// Devuelve 204 No Content en caso de borrar, 404 si no existía
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrar(@PathVariable Long id) {
		if (generoService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		generoService.deleteById(id);
		return ResponseEntity.noContent().build(); // 204 -> éxito, sin cuerpo
	}

}
