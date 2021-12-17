package edu.djharo.minsait.mscursos.service;

import java.util.List;
import java.util.Optional;

import edu.djharo.minsait.mscomun.model.entity.Alumno;
import edu.djharo.minsait.mscomun.model.entity.Curso;

public interface CursoService {

	public Iterable<Curso> findAll();
	
	public Optional<Curso> findById(Long id);
	
	public Curso save (Curso alumno);
	
	public void deleteById(Long id);
	
	public Curso update (Curso alumno, Long id);
	
	//"no crud" -EXTRAS
	
	public Optional<Curso> asignarAlumnos (List<Alumno> alumnos, Long id);
	
	public Optional<Curso> eliminarAlumno (Alumno alumno, Long id);
	
	public Optional<Curso> obtenerCursoAlumnoQueryNativa (Long id_alumno);
	
	public Optional<Curso> obtenerCursoAlumnoJPQL (Long id_alumno);
}
