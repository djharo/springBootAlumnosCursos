package edu.djharo.minsait.msalumnosdjharo.service;

import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;

import edu.djharo.minsait.msalumnosdjharo.externservice.FraseChuckNorris;
import edu.djharo.minsait.mscomun.model.entity.Alumno;
import edu.djharo.minsait.mscomun.model.entity.Curso;


public interface AlumnoService {
	
	public Iterable<Alumno> findAll();
	
	public Optional<Alumno> findById(Long id);
	
	public Alumno save (Alumno alumno);
	
	public void deleteById(Long id);
	
	public Alumno update (Alumno alumno, Long id);
	
	public Iterable<Alumno> findByEdadBetween (int edad_inicial, int edad_final);
	
	public Iterable<Alumno> findByNombreLike (String nombre);
	
	public Iterable<Alumno> findByNombre (String nombre);
	
	public Iterable<Alumno> busquedaPorNombreOApellido (String nombre);
	
	public Optional<FraseChuckNorris> obtenerFraseChuckNorris();
	
	//Feign client hacia el microservicis cursos
	public Optional<Curso> obtenerCursoNativa(@PathVariable Long idalumno);
}
