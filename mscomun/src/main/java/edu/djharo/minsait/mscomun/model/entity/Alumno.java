package edu.djharo.minsait.mscomun.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "alumnos")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//autoincrementado en mysql
	private Long id;
	
	@Size(min = 3, max = 15)
	private String nombre;//sin anotacion va a crear un campo con el mismo nombre
	
	@NotEmpty//ni nula y > o
	private String apellido;
	
	@Email
	private String email;
	
	@Min(0)
	@Max(130)
	private Integer edad;
	
	@Column(name = "creado_en")
	@Temporal(TemporalType.TIMESTAMP)//detalle en ms
	private Date creadoEn;
	
	@PrePersist//(solo para el insert(save) la primera vez)el método anotado asi, se ejecuta automaticamente antes de guardar en la base de datos el alumno.
	private void generarFecha() {
		this.creadoEn = new Date();
	}
}
