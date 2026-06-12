package com.example.demo.controller;

import com.example.demo.model.Pelicula;
import com.example.demo.services.PeliculaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests del controlador REST con MockMvc (capa web aislada, servicio simulado).
 */
@WebMvcTest(PeliculaController.class)
class PeliculaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PeliculaService service;

    @Test
    void getPorId_existente_devuelve200YJson() throws Exception {
        Pelicula p = new Pelicula();
        p.setId(1L);
        p.setTitulo("Matrix");
        when(service.findById(1L)).thenReturn(Optional.of(p));

        mvc.perform(get("/api/peliculas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Matrix"));
    }

    @Test
    void getPorId_inexistente_devuelve404() throws Exception {
        when(service.findById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/peliculas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void toggleVista_devuelveElNuevoEstado() throws Exception {
        when(service.toggleVista(1L)).thenReturn(false);

        mvc.perform(patch("/api/peliculas/1/vista"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void borrar_inexistente_devuelve404YNoBorra() throws Exception {
        when(service.findById(7L)).thenReturn(Optional.empty());

        mvc.perform(delete("/api/peliculas/7"))
                .andExpect(status().isNotFound());
        verify(service, never()).deleteById(anyLong());
    }
}
