package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long>{
	
	List<Pelicula> findByFavoritaTrue();
	
	
	Page<Pelicula> findByVista(Boolean vista, Pageable p);
	Page<Pelicula> findByVistaAndTituloContainingIgnoreCase(Boolean vista, String titulo, Pageable p);
	Page<Pelicula> findByVistaAndGeneroId(Boolean vista, Long id, Pageable p);
	Page<Pelicula> findByVistaAndTituloContainingIgnoreCaseAndGeneroId(Boolean vista, String titulo, Long id, Pageable p);
	Page<Pelicula> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);     
	Page<Pelicula> findByGeneroId(Long idGenero, Pageable pageable);     
	Page<Pelicula> findByTituloContainingIgnoreCaseAndGeneroId(String titulo, Long idGenero, Pageable pageable); 
	
	long countByVistaFalse();

	long countByResenaPersonalIsNotNull();

	long countByFavoritaTrue();

	@Query("SELECT COALESCE(SUM(p.duracion), 0) FROM Pelicula p WHERE p.vista = true")
	long sumDuracionVistaTrue();

	@Query(value = "SELECT g.nombre FROM peliculas p JOIN generos g ON p.id_genero = g.id GROUP BY g.nombre ORDER BY COUNT(p.id) DESC LIMIT 1", nativeQuery = true)
	String findGeneroFavorito();
	
	
}
