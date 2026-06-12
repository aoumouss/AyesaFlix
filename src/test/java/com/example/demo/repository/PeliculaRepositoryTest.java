package com.example.demo.repository;

import com.example.demo.model.Genero;
import com.example.demo.model.Pelicula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests de integración del repositorio sobre H2 embebido.
 * Se desactiva data.sql para partir de una base limpia y controlar los datos del test.
 */
@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
class PeliculaRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private PeliculaRepository repo;

    private Pelicula nuevaPelicula(String titulo, Genero genero, boolean vista, boolean favorita,
                                   Integer duracion, String resena) {
        Pelicula p = new Pelicula();
        p.setTitulo(titulo);
        p.setGenero(genero);
        p.setVista(vista);
        p.setFavorita(favorita);
        p.setDuracion(duracion);
        p.setAnio(2000);
        p.setResenaPersonal(resena);
        return p;
    }

    @Test
    void cuentaPendientesYResenas_ySumaLaDuracionDeLasVistas() {
        Genero accion = em.persist(new Genero("Acción"));
        em.persist(nuevaPelicula("A", accion, true, true, 120, "buena"));
        em.persist(nuevaPelicula("B", accion, true, false, 100, null));
        em.persist(nuevaPelicula("C", accion, false, true, 90, null)); // pendiente
        em.flush();

        assertThat(repo.countByVistaFalse()).isEqualTo(1);
        assertThat(repo.countByResenaPersonalIsNotNull()).isEqualTo(1);
        assertThat(repo.sumDuracionVistaTrue()).isEqualTo(220); // solo 120 + 100 (las vistas)
        assertThat(repo.findByFavoritaTrue()).hasSize(2);
    }

    @Test
    void generoFavorito_devuelveElGeneroConMasPeliculas() {
        Genero accion = em.persist(new Genero("Acción"));
        Genero drama = em.persist(new Genero("Drama"));
        em.persist(nuevaPelicula("A", accion, true, true, 120, null));
        em.persist(nuevaPelicula("B", accion, true, true, 100, null));
        em.persist(nuevaPelicula("C", drama, true, true, 90, null));
        em.flush();

        assertThat(repo.findGeneroFavorito()).isEqualTo("Acción");
    }
}
