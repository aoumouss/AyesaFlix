package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pelicula;
import com.example.demo.services.PeliculaService;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {

	private static final int PAGE_SIZE = 4; // Películas por página

	@Autowired
	private PeliculaService peliculaService;

	// ------- VISTA PRINCIPAL -------

	// GET /api/peliculas --> todas paginadas
	// GET /api/peliculas?buscar=matrix --> filtrar por título
	// GET /api/peliculas?generoId=2 --> filtrar por género
	// GET /api/peliculas?buscar=matrix&generoId=2 --> filtrar por ambos
	// GET /api/peliculas?page=1 --> segunda página
	@GetMapping
	public Page<Pelicula> listar(@RequestParam(required = false) String buscar,
			@RequestParam(required = false) Long generoId, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "true") Boolean vista) {

		Pageable pageable = PageRequest.of(page, PAGE_SIZE);

		if (buscar != null && generoId != null) {
			return peliculaService.findByTituloAndGenero(buscar, generoId, vista, pageable);
		} else if (buscar != null) {
			return peliculaService.findByTitulo(buscar, vista, pageable);
		} else if (generoId != null) {
			return peliculaService.findByGenero(generoId, vista, pageable);
		} else {
			return peliculaService.findAll(vista, pageable);
		}
	}

	// ------- SECCIÓN PELÍCULAS FAVORITAS -------

	// GET /api/peliculas/favoritas --> lista de películas marcadas como favoritas
	@GetMapping("/favoritas")
	public List<Pelicula> favoritas() {
		return peliculaService.findFavoritas();
	}

	// ------- MODEL CARD -------

	// GET /api/peliculas/3 --> película con id=3, o 404 si no existe
	@GetMapping("/{id}")
	public ResponseEntity<Pelicula> buscarPorId(@PathVariable Long id) {
		return peliculaService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	// ------- ACTUALIZA VALORACIÓN (ESTRELLAS) -------

	// PATCH /api/peliculas/3/valoracion?valoracion=4
	// Actualiza solo la valoración, sin tocar los demás campos
	@PatchMapping("/{id}/valoracion")
	public ResponseEntity<Pelicula> actualizarValoracion(@PathVariable Long id, @RequestParam Integer valoracion) {
		return peliculaService.findById(id).map(p -> {
			p.setValoracion(valoracion);
			return ResponseEntity.ok(peliculaService.save(p));
		}).orElse(ResponseEntity.notFound().build());
	}

	// ------- AÑADIR PELÍCULA A FAVORITOS -------

	// POST /api/peliculas/3/favorita → si era favorita la quita, si no la añade
	// Devuelve true o false
	@PostMapping("/{id}/favorita")
	public ResponseEntity<Boolean> toggleFavorita(@PathVariable Long id) {
		boolean nuevoEstado = peliculaService.toggleFavorita(id);
		return ResponseEntity.ok(nuevoEstado);
	}

	// ------- WATCHLIST: MARCAR VISTA / PENDIENTE -------

	// PATCH /api/peliculas/3/vista → alterna vista (vista ↔ pendiente)
	// Devuelve el nuevo estado: true = vista, false = pendiente
	@PatchMapping("/{id}/vista")
	public ResponseEntity<Boolean> toggleVista(@PathVariable Long id) {
		boolean nuevoEstado = peliculaService.toggleVista(id);
		return ResponseEntity.ok(nuevoEstado);
	}

	// ------- INSERTAR PELICULA -------

	// POST /api/peliculas --> crea una película nueva
	// Recibe JSON en el cuerpo, valida con @Valid, devuelve 201 + película con ID
	// asignado
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pelicula crear(@Valid @RequestBody Pelicula pelicula) {
		return peliculaService.save(pelicula);
	}

	// ------- UPDATE PELICULA -------

	// PUT /api/peliculas/3 --> reemplaza TODOS los datos de la película con id=3
	// Debes enviar el JSON completo con todos los campos
	@PutMapping("/{id}")
	public ResponseEntity<Pelicula> actualizar(@PathVariable Long id, @Valid @RequestBody Pelicula datos) {
		return peliculaService.findById(id).map(p -> {
			// Actualizamos cada campo con los datos recibidos
			p.setTitulo(datos.getTitulo());
			p.setDuracion(datos.getDuracion());
			p.setAnio(datos.getAnio());
			p.setSinopsis(datos.getSinopsis());
			p.setActores(datos.getActores());
			p.setUrlPortada(datos.getUrlPortada());
			p.setUrlTrailer(datos.getUrlTrailer());
			p.setValoracion(datos.getValoracion());
			p.setVista(datos.getVista());
			p.setGenero(datos.getGenero());
			return ResponseEntity.ok(peliculaService.save(p));
		}).orElse(ResponseEntity.notFound().build());
	}

	// ------- BORRAR PELICULA -------

	// DELETE /api/peliculas/3 --> borra la película con id=3
	// Devuelve 204 No Content si se borró, 404 si no existe
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> borrar(@PathVariable Long id) {
		if (peliculaService.findById(id).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		peliculaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/resena")
	public ResponseEntity<Pelicula> actualizarResena(@PathVariable Long id, @RequestBody Map<String, String> body) {
		return peliculaService.findById(id).map(p -> {
			p.setResenaPersonal(body.get("resenaPersonal"));
			return ResponseEntity.ok(peliculaService.save(p));
		}).orElse(ResponseEntity.notFound().build());
	}

}