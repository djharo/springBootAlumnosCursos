package edu.djharo.minsait.mscursos.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.djharo.minsait.mscomun.model.entity.Alumno;
import edu.djharo.minsait.mscomun.model.entity.Curso;
import edu.djharo.minsait.mscursos.service.CursoService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


//definir un filtro para permitir peticiones cruzadas de otros origenes
//"http://127.0.0.1:5500" servidor donde esta la pagina methos se indica a que metodos podra tener acceso el cliente
@CrossOrigin(originPatterns = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping(path = "/cursos")
public class CursoController {
	
	@Autowired
	private CursoService cursoService;
	
	@Value("${instancia}")
	String nombre_instancia;
	
	@Autowired
	Environment environment;
	
	private Logger log = LoggerFactory.getLogger(CursoController.class);
	
	@GetMapping(value = "/listarCursos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Curso>> listarCursos(){
		
		ResponseEntity<Iterable<Curso>> responseEntity = null; //representa el HTTP de vuelta
		Iterable<Curso> ita = null;
		
		log.debug("Entrando en listarCursos()");
		
		log.debug("Atendido por el MS " + this.nombre_instancia + " puerto " + this.environment.getProperty("local.server.port"));
		
		ita = this.cursoService.findAll();
		log.debug("Lista recuperada = " + ita);	
		responseEntity = ResponseEntity.ok(ita);
		
		log.debug("Saliendo de listarCursos()");
		
		return responseEntity;
	}
	
	@GetMapping("/listarCursoPorId/{id}")
	public ResponseEntity<Curso> listarCursoPorId(@PathVariable Long id){
		
		ResponseEntity<Curso> responseEntity = null; //representa el HTTP de vuelta
		
		Optional<Curso> opa =  this.cursoService.findById(id);
			 if (opa.isPresent())
			 {
				 Curso CursoLeido = opa.get();
				 responseEntity = ResponseEntity.ok(CursoLeido);
			 } else {
				 responseEntity = ResponseEntity.noContent().build();
			 }
		
		return responseEntity;
	}
	
	@PostMapping("/insertarCurso")
	public ResponseEntity<?> insertarCurso(@RequestBody Curso Curso) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Curso Curso_creado = null;

			Curso_creado = this.cursoService.save(Curso);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(Curso_creado);

		return responseEntity;
	}
	
	@PutMapping("/modificarCurso/{id}")
	public ResponseEntity<?> modificarCurso(@RequestBody Curso Curso, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Curso Curso_actualizado = null;

			Curso_actualizado = this.cursoService.update(Curso, id);
			if (Curso_actualizado != null) {
				responseEntity = ResponseEntity.ok(Curso_actualizado);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}

		return responseEntity;
	}
	
	@DeleteMapping("/borrarCurso/{id}")
	public ResponseEntity<Curso> borrarCurso(@PathVariable Long id){
		ResponseEntity<Curso> responseEntity = null; //representa el HTTP de vuelta
		
		this.cursoService.deleteById(id);
		responseEntity = ResponseEntity.ok().build();
		//TODO mejorar la respuesta ante un ID que no exista
		//la DOC no concuerda pero nos lanza un 
		//org.springframework.dao.EmptyResultDataAccessException: No class edu.val.minsait.msCursos.model.entity.Curso entity with id 20 exists!
	
	return responseEntity;
	}
	
	@GetMapping("/obtenerCursoTest")
	public Curso obtenerCursoTest(){
		
		Curso CursoPrueba = null;//Representa el HTTP de vuelta
		CursoPrueba = new Curso();
		CursoPrueba.setName("De la biblioeconomia al Java");

		return CursoPrueba;
	}
	

	// http://localhost:8090/api/cursos/saludo
	@GetMapping("/saludo")
	public String saludo() {
		return "HOLA DESDE MSCURSOS";
	}

	@PutMapping("/asignar-alumnos/{id}")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Optional<Curso> opc = null;
		Curso curso_actualizado = null;

		log.debug("Entrando en asignarAlumnos");
		opc = this.cursoService.asignarAlumnos(alumnos, id);

		if (opc.isPresent()) {
			curso_actualizado = opc.get();
			responseEntity = ResponseEntity.ok(curso_actualizado);
			log.debug("Curso actualizado = " + curso_actualizado);
		} else {
			responseEntity = ResponseEntity.notFound().build();
			log.debug("No se ha podido actualizar el curso");
		}
		log.debug("Saliendo de asignarAlumnos");

		return responseEntity;
	}

	@PutMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> eliminarAlumno(Alumno alumno, @PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Optional<Curso> opc = null;
		Curso curso_actualizado = null;

			log.debug("Entrando en eliminarAlumno");
			opc = this.cursoService.eliminarAlumno(alumno, id);
	
			if (opc.isPresent()) {
				curso_actualizado = opc.get();
				responseEntity = ResponseEntity.ok(curso_actualizado);
				log.debug("Curso actualizado = " + curso_actualizado);
			} else {
				responseEntity = ResponseEntity.notFound().build();
				log.debug("No se ha podido actualizar el curso");
			}
			log.debug("Saliendo de eliminarAlumno");

		return responseEntity;
	}
	
	@GetMapping("/obtenercursonativo/{idalumno}")
	public ResponseEntity<?> obtenerCursoNativa(@PathVariable Long idalumno) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Optional<Curso> opa = null;

		opa = this.cursoService.obtenerCursoAlumnoQueryNativa(idalumno);
		if (opa.isPresent()) {
			Curso Curso_leido = opa.get();
			responseEntity = ResponseEntity.ok(Curso_leido);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}

		return responseEntity;

	}
	
	@GetMapping("/obtenercursojpql/{idalumno}")
	public ResponseEntity<?> obtenerCursoJPQL(@PathVariable Long idalumno) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Optional<Curso> opa = null;

		opa = this.cursoService.obtenerCursoAlumnoJPQL(idalumno);
		if (opa.isPresent()) {
			Curso Curso_leido = opa.get();
			responseEntity = ResponseEntity.ok(Curso_leido);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}

		return responseEntity;
	}
	
	@GetMapping(value = "/listarCursosHateoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Curso>> listarCursosHateoas(){
		
		ResponseEntity<Iterable<Curso>> responseEntity = null; //representa el HTTP de vuelta
		Iterable<Curso> ita = null;
		
		log.debug("Entrando en listarCursos()");
		
		log.debug("Atendido por el MS " + this.nombre_instancia + " puerto " + this.environment.getProperty("local.server.port"));
		
		ita = this.cursoService.findAll();
		log.debug("Lista recuperada = " + ita);	
		
		for (Curso c : ita)
		{
			c.add(linkTo(methodOn(CursoController.class).listarCursoPorId(c.getId())).withSelfRel());
		}
		
		responseEntity = ResponseEntity.ok(ita);
		
		log.debug("Saliendo de listarCursos()");
		
		return responseEntity;
	}
}
