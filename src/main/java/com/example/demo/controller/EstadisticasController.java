package com.example.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.repository.PeliculaRepository;


@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {
    @Autowired
    private PeliculaRepository repo;

    @GetMapping
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> stats = new LinkedHashMap<>();
        stats.put("totalRegistradas", repo.count());
        stats.put("watchlistPendientes", repo.countByVistaFalse());
        stats.put("tiempoTotalMinutos", repo.sumDuracionVistaTrue());
        stats.put("totalResenas", repo.countByResenaPersonalIsNotNull());
        stats.put("totalFavoritas", repo.countByFavoritaTrue());
        stats.put("generoFavorito", repo.findGeneroFavorito());
        return stats;
    }
}