package com.example.demo.services;

import com.example.demo.model.Pelicula;
import com.example.demo.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

	@Autowired
	private PeliculaRepository peliculaRepository;

	public List<Pelicula> findAll() {
		return peliculaRepository.findAll();
	}

	public Optional<Pelicula> findById(Long id) {
		return peliculaRepository.findById(id);
	}

	public Pelicula save(Pelicula p) {
		return peliculaRepository.save(p);
	}

	public void deleteById(Long id) {
		peliculaRepository.deleteById(id);
	}

	public List<Pelicula> findFavoritas() {
		return peliculaRepository.findByFavoritaTrue();
	}

	public Page<Pelicula> findAll(Boolean vista, Pageable p) {
		if (vista != null) {
			return peliculaRepository.findByVista(vista, p);
		}
		return peliculaRepository.findAll(p);
	}

	public Page<Pelicula> findByTitulo(String t, Boolean vista, Pageable p) {
		if (vista != null) {
			return peliculaRepository.findByVistaAndTituloContainingIgnoreCase(vista, t, p);
		}
		return peliculaRepository.findByTituloContainingIgnoreCase(t, p);
	}

	public Page<Pelicula> findByGenero(Long id, Boolean vista, Pageable p) {
		if (vista != null) {
			return peliculaRepository.findByVistaAndGeneroId(vista, id, p);
		}
		return peliculaRepository.findByGeneroId(id, p);
	}

	public Page<Pelicula> findByTituloAndGenero(String t, Long id, Boolean vista, Pageable p) {
		if (vista != null) {
			return peliculaRepository.findByVistaAndTituloContainingIgnoreCaseAndGeneroId(vista, t, id, p);
		}
		return peliculaRepository.findByTituloContainingIgnoreCaseAndGeneroId(t, id, p);
	}

	public boolean toggleVista(Long id) {
		Pelicula p = peliculaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Película no encontrada: " + id));
		p.setVista(!p.getVista());
		peliculaRepository.save(p);
		return p.getVista();
	}

	public boolean toggleFavorita(Long id) {
		Pelicula p = peliculaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Película no encontrada: " + id));
		p.setFavorita(!p.getFavorita());
		peliculaRepository.save(p);
		return p.getFavorita();
	}
}