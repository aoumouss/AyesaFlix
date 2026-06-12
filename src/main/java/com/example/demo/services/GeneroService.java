package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Genero;
import com.example.demo.repository.GeneroRepository;

@Service
public class GeneroService {

	@Autowired
	private GeneroRepository generoRepository;

	public List<Genero> findAll() {
		return generoRepository.findAll();
	}

	public Optional<Genero> findById(Long id) {
		return generoRepository.findById(id);
	}

	public Genero save(Genero genero) {
		return generoRepository.save(genero);
	}

	public void deleteById(Long id) {
		generoRepository.deleteById(id);
	}
}
