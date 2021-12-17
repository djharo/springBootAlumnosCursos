package edu.djharo.minsait.msalumnosdjharo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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

import edu.djharo.minsait.msalumnosdjharo.externservice.FraseChuckNorris;
import edu.djharo.minsait.msalumnosdjharo.service.AlumnoService;
import edu.djharo.minsait.mscomun.model.entity.Alumno;
import edu.djharo.minsait.mscomun.model.entity.Curso;
import io.swagger.v3.oas.annotations.Operation;

/**
 * aqui vamos a recibir las peticiones web / http para alta/baja/modificacion/borrado de alumnos
 * @author djharo
 *
 */
//localhost:65110/swagger-ui/index.html //Cambiar puerto
//definir un filtro para permitir peticiones cruzadas de otros origenes
//"http://127.0.0.1:5500" servidor donde esta la pagina methos se indica a que metodos podra tener acceso el cliente
@CrossOrigin(originPatterns = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping(path = "/alumnos")
public class AlumnoController {
	
	@Autowired
	private AlumnoService alumnoService;
	
	@Value("${instancia}")
	String nombre_instancia;
	
	@Autowired
	Environment environment;
	
	private Logger log = LoggerFactory.getLogger(AlumnoController.class);
	
	@Operation(summary = "Lista los Alumnos", description = "Lista todos los alumnos del sistema.")
	@GetMapping(value = "/listarAlumnos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Iterable<Alumno>> listarAlumnos(){
		
		ResponseEntity<Iterable<Alumno>> responseEntity = null; //representa el HTTP de vuelta
		Iterable<Alumno> ita = null;
		
		log.debug("Entrando en listarAlumnos()");
		
		log.debug("Atendido por el MS " + this.nombre_instancia + " puerto " + this.environment.getProperty("local.server.port"));
		
		ita = this.alumnoService.findAll();
		log.debug("Lista recuperada = " + ita);	
		responseEntity = ResponseEntity.ok(ita);
		
		log.debug("Saliendo de listarAlumnos()");
		
		return responseEntity;
	}
	
	@GetMapping("/listarAlumnoPorId/{id}")
	public ResponseEntity<Alumno> listarAlumnoPorId(@PathVariable Long id){
		
		ResponseEntity<Alumno> responseEntity = null; //representa el HTTP de vuelta
		
		Optional<Alumno> opa =  this.alumnoService.findById(id);
			 if (opa.isPresent())
			 {
				 Alumno alumnoLeido = opa.get();
				 responseEntity = ResponseEntity.ok(alumnoLeido);
			 } else {
				 responseEntity = ResponseEntity.noContent().build();
			 }
		
		return responseEntity;
	}
	
	@PostMapping("/insertarAlumno")
	public ResponseEntity<?> insertarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Alumno alumno_creado = null;

		if (br.hasErrors()) {
			log.error("Errorrrrr en el Alumno");
			responseEntity = obtenerErrores(br);
		} else {
			log.debug("Sin errores en la entrada");
			alumno_creado = this.alumnoService.save(alumno);
			responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(alumno_creado);
		}

		return responseEntity;

	}
	
	@PutMapping("/modificarAlumno/{id}")
	public ResponseEntity<?> modificarAlumno(@Valid @RequestBody Alumno alumno, BindingResult br,
			@PathVariable Long id) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Alumno alumno_actualizado = null;

		if (br.hasErrors()) {
			log.error("Errorrrrr en el Alumno");
			responseEntity = obtenerErrores(br);
		} else {

			alumno_actualizado = this.alumnoService.update(alumno, id);
			if (alumno_actualizado != null) {
				responseEntity = ResponseEntity.ok(alumno_actualizado);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}
		}

		return responseEntity;

	}
	
	@DeleteMapping("/borrarAlumno/{id}")
	public ResponseEntity<Alumno> borrarAlumno(@PathVariable Long id){
		ResponseEntity<Alumno> responseEntity = null; //representa el HTTP de vuelta
		
		this.alumnoService.deleteById(id);
		responseEntity = ResponseEntity.ok().build();
		//TODO mejorar la respuesta ante un ID que no exista
		//la DOC no concuerda pero nos lanza un 
		//org.springframework.dao.EmptyResultDataAccessException: No class edu.val.minsait.msalumnos.model.entity.Alumno entity with id 20 exists!
	
	return responseEntity;
	}
	
	@GetMapping("/obtenerAlumnoTest")
	public Alumno obtenerAlumnoTest(){
		Alumno alumnoPrueba = null;//Representa el HTTP de vuelta
		alumnoPrueba = new Alumno();
		alumnoPrueba.setNombre("Juan");
		alumnoPrueba.setApellido("Perez");
		alumnoPrueba.setEdad(32);
		alumnoPrueba.setEmail("jperez@minsait.com");
		return alumnoPrueba;
	}
	
	@GetMapping("/buscarpornombre/{nombre}")
	public ResponseEntity<?> obtenerAlumnosByNombre(@PathVariable String nombre)
	{
		ResponseEntity<?> responseEntity = null; //representa el HTTP de vuelta
		Iterable<Alumno> ita = null;
		
			log.debug("Entrando en obtenerAlumnosByNombre()" );
			ita = this.alumnoService.findByNombre(nombre);
			log.debug("Lista recuperada = "+ita);
			responseEntity = ResponseEntity.ok(ita);
			log.debug("Saliendo de obtenerAlumnosByNombre()" );
		
		return responseEntity;
		
	}
	
	@GetMapping("/buscarpornombrelike/{nombre}")
	public ResponseEntity<?> obtenerAlumnosByNombreLike(@PathVariable String nombre)
	{
		ResponseEntity<?> responseEntity = null; //representa el HTTP de vuelta
		Iterable<Alumno> ita = null;
		
			log.debug("Entrando en obtenerAlumnosByNombre() " + nombre );
			ita = this.alumnoService.findByNombreLike("%"+nombre+"%");
			log.debug("Lista recuperada = "+ita);
			responseEntity = ResponseEntity.ok(ita);
			log.debug("Saliendo de obtenerAlumnosByNombre()" );
		
		return responseEntity;
		
	}
	
	@GetMapping("/busquedaPorNombreOApellido/{nombre}")
	public ResponseEntity<?> busquedaPorNombreOApellido(@PathVariable String nombre)
	{
		ResponseEntity<?> responseEntity = null; //representa el HTTP de vuelta
		Iterable<Alumno> ita = null;
		
			log.debug("Entrando en busquedaPorNombreOApellido() " + nombre );
			ita = this.alumnoService.busquedaPorNombreOApellido(nombre);
			log.debug("Lista recuperada = "+ita);
			int naAlumnos=((Collection<?>) ita).size();
			if(naAlumnos ==0) {
				responseEntity = ResponseEntity.noContent().build();
			}else {
				responseEntity = ResponseEntity.ok(ita);
			}
			
			log.debug("Saliendo de busquedaPorNombreOApellido()" );
		
		return responseEntity;
		
	}
	
	@GetMapping("/buscarporrangoedad/{edad1}/{edad2}")
	public ResponseEntity<?> obtenerAlumnosByRangoDeEdad(@PathVariable int edad1, @PathVariable int edad2) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Iterable<Alumno> ita = null;

		log.debug("Entrando en obtenerAlumnosByRangoDeEdad() " + edad1 + " " + edad2);
		ita = this.alumnoService.findByEdadBetween(edad1, edad2);
		log.debug("Lista recuperada = " + ita);
		responseEntity = ResponseEntity.ok(ita);
		log.debug("Saliendo de obtenerAlumnosByRangoDeEdad()");

		return responseEntity;

	}
	
	private ResponseEntity<?> obtenerErrores(BindingResult br) {
		ResponseEntity<?> responseEntity = null;
		List<ObjectError> lista_errores = null;

		lista_errores = br.getAllErrors();
		lista_errores.forEach(obj_error -> {
			log.error(obj_error.toString());
		});

		responseEntity = ResponseEntity.badRequest().body(lista_errores);

		return responseEntity;// mensaje con la descirición de los errores 400

	}
	
	@Operation(summary = "Frases ChuckNorris", description = "Frases aleatorias de Chuck Norris")
	@GetMapping(value = "/frase-chuck", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FraseChuckNorris> obtenerFrasesChuck(){
		
		ResponseEntity<FraseChuckNorris> responseEntity = null; //representa el HTTP de vuelta
		Optional<FraseChuckNorris> frase = null;
		
			log.debug("Entrando en obtenerFrasesChuck()");
			
			frase = this.alumnoService.obtenerFraseChuckNorris();
			
			if(frase.isEmpty()) {
				log.debug("Frase recuperada = " + "sin frase");
				responseEntity = ResponseEntity.noContent().build();
			}else {
				responseEntity = ResponseEntity.ok(frase.get());
				log.debug("Frase recuperada = " + frase.get().getValue());
			}
			
			log.debug("Saliendo de obtenerFrasesChuck()" );
		
		return responseEntity;
	}
	
	@GetMapping("/dameCursoAlumnoViaMs/{idalumno}")
	public ResponseEntity<?> obtenerCursoAlumnoFeign(@PathVariable Long idalumno) {
		ResponseEntity<?> responseEntity = null; // representa el HTTP de vuelta
		Optional<Curso> opc = null;
		
		Curso curso = null;
		
		opc = this.alumnoService.obtenerCursoNativa(idalumno);
		if (opc.isPresent()) {
			curso = opc.get();
			responseEntity = ResponseEntity.ok(curso);
		} else {
			responseEntity = ResponseEntity.noContent().build();
		}

		return responseEntity;
	}
}
