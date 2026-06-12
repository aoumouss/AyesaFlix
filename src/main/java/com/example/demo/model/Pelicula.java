package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "peliculas")
public class Pelicula {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El título es obligatorio")
	private String titulo;

	@Min(value = 1, message = "La duración mínima es 1 minuto")
	private Integer duracion;

	@Min(value = 1888, message = "Año no válido")
	private Integer anio;

	@Column(columnDefinition = "TEXT")
	private String sinopsis;

	private String actores;

	@Column(name = "url_portada")
	private String urlPortada;

	@Column(name = "url_trailer")
	private String urlTrailer;

	@Min(value = 1)
	@Max(value = 5)
	private Integer valoracion;

	@Column(nullable = false)
	private Boolean favorita = true;

	@Column(nullable = false)
	private Boolean vista = true;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_genero", nullable = false)
	private Genero genero;

	@Column(columnDefinition = "TEXT")
	private String resenaPersonal;

	public String getResenaPersonal() {
		return resenaPersonal;
	}

	public void setResenaPersonal(String r) {
		this.resenaPersonal = r;
	}

	public Boolean getVista() {
		return vista;
	}

	public void setVista(Boolean vista) {
		this.vista = vista;
	}

	public Pelicula() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public String getActores() {
		return actores;
	}

	public void setActores(String actores) {
		this.actores = actores;
	}

	public String getUrlPortada() {
		return urlPortada;
	}

	public void setUrlPortada(String urlPortada) {
		this.urlPortada = urlPortada;
	}

	public String getUrlTrailer() {
		return urlTrailer;
	}

	public void setUrlTrailer(String urlTrailer) {
		this.urlTrailer = urlTrailer;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Boolean getFavorita() {
		return favorita;
	}

	public void setFavorita(Boolean favorita) {
		this.favorita = favorita;
	}
}
