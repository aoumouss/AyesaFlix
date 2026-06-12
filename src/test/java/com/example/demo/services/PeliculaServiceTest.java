package com.example.demo.services;

import com.example.demo.model.Pelicula;
import com.example.demo.repository.PeliculaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests unitarios de PeliculaService usando Mockito (sin levantar Spring ni base de datos).
 */
@ExtendWith(MockitoExtension.class)
class PeliculaServiceTest {

    @Mock
    private PeliculaRepository repo;

    @InjectMocks
    private PeliculaService service;

    @Test
    void toggleVista_invierteElEstadoYGuarda() {
        Pelicula p = new Pelicula();
        p.setId(1L);
        p.setVista(true);
        when(repo.findById(1L)).thenReturn(Optional.of(p));
        when(repo.save(any(Pelicula.class))).thenAnswer(inv -> inv.getArgument(0));

        boolean nuevoEstado = service.toggleVista(1L);

        assertThat(nuevoEstado).isFalse();
        assertThat(p.getVista()).isFalse();
        verify(repo).save(p);
    }

    @Test
    void toggleFavorita_invierteElEstado() {
        Pelicula p = new Pelicula();
        p.setId(5L);
        p.setFavorita(false);
        when(repo.findById(5L)).thenReturn(Optional.of(p));
        when(repo.save(any(Pelicula.class))).thenAnswer(inv -> inv.getArgument(0));

        boolean nuevoEstado = service.toggleFavorita(5L);

        assertThat(nuevoEstado).isTrue();
        assertThat(p.getFavorita()).isTrue();
    }

    @Test
    void toggleVista_idInexistente_lanzaExcepcionYNoGuarda() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.toggleVista(99L));
        verify(repo, never()).save(any(Pelicula.class));
    }

    @Test
    void findFavoritas_delegaEnElRepositorio() {
        when(repo.findByFavoritaTrue()).thenReturn(Collections.singletonList(new Pelicula()));

        assertThat(service.findFavoritas()).hasSize(1);
        verify(repo).findByFavoritaTrue();
    }
}
