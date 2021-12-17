package edu.djharo.minsait.mscursos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.djharo.minsait.mscomun.model.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
	
	//¿A qué curso está asignado un alumno?
	
	//@query nativa
	@Query(value = "select * from minsaitdb.cursos where id = "
	+ "(select curso_id from cursos_alumnos where alumnos_id=?1)", nativeQuery = true)
    public Optional<Curso> obtenerCursoAlumnoQueryNativa (Long idAlumno);
	
	//@query JQPL
	@Query("select c from Curso c JOIN c.alumnos l where l.id= ?1")
	public Optional<Curso> obtenerCursoAlumnoJPQL (Long idAlumno);
}
