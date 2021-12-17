package edu.djharo.minsait.mscomun.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "cursos")
public class Curso extends RepresentationModel<Curso>{
	
	public Curso(List<Alumno> alumnos) {
		this.alumnos = new ArrayList<Alumno>();
	}
	
	public Curso() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//autoincrementado en mysql
	private Long id;
	
	private String name;
	
	@Column(name = "creado_en")
	@Temporal(TemporalType.TIMESTAMP)//detalle en ms
	private Date creadoEn;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Alumno> alumnos;
	
	@PrePersist//(solo para el insert(save) la primera vez)el método anotado asi, se ejecuta automaticamente antes de guardar en la base de datos el alumno.
	private void generarFecha() {
		this.creadoEn = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(Date creadoEn) {
		this.creadoEn = creadoEn;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	public void addAlumno (Alumno alumno)
	{
		this.alumnos.add(alumno);
	}
	
	public void removeAlumno (Alumno alumno)
	{
		this.alumnos.remove(alumno);
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", name=" + name + ", creadoEn=" + creadoEn + ", alumnos=" + alumnos + "]";
	}
	
	
}
