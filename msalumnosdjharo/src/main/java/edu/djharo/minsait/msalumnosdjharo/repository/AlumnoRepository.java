package edu.djharo.minsait.msalumnosdjharo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.djharo.minsait.mscomun.model.entity.Alumno;

//Al extender de JpaRepository no hace falta poner @Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long>{

	//"key word" querys
	//BUSCAR ALUMNOS EN UN RANGO DE EDAD
	public Iterable<Alumno> findByEdadBetween (int edad_inicial, int edad_final);
	
	//BUSCAR ALUMNOS POR EL NOMBRE like
	public Iterable<Alumno> findByNombreLike (String nombre);
	
	//BUSCAR ALUMNOS POR EL NOMBRE like
	public Iterable<Alumno> findByNombre (String nombre);
	
	//@Query
	@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
	public Iterable<Alumno> busquedaPorNombreOApellido(@Param("nombre") String nombre);
}
