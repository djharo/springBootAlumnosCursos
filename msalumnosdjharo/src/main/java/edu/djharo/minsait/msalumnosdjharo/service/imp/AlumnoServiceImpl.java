package edu.djharo.minsait.msalumnosdjharo.service.imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.djharo.minsait.msalumnosdjharo.cliente.CursoFeignClient;
import edu.djharo.minsait.msalumnosdjharo.externservice.FraseChuckNorris;
import edu.djharo.minsait.msalumnosdjharo.repository.AlumnoRepository;
import edu.djharo.minsait.msalumnosdjharo.service.AlumnoService;
import edu.djharo.minsait.mscomun.model.entity.Alumno;
import edu.djharo.minsait.mscomun.model.entity.Curso;

@Service
public class AlumnoServiceImpl implements AlumnoService {
	
	@Autowired
	private AlumnoRepository alumnoRepository;
	
	@Autowired
	private CursoFeignClient cursoFeignClient;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAll() {
		return this.alumnoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Alumno> findById(Long id) {
		return this.alumnoRepository.findById(id);
	}

	@Override
	@Transactional
	public Alumno save(Alumno alumno) {
		return this.alumnoRepository.save(alumno);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.alumnoRepository.deleteById(id);

	}

	@Override
	@Transactional
	public Alumno update(Alumno alumno, Long id) {
		
		Alumno alumnoLeido = null;
		Alumno alumnoActualizado = null;
			
		Optional<Alumno> opa = this.alumnoRepository.findById(id);
			if (opa.isPresent())
			{
				//el alumno que queremos modificar, existe
				alumnoLeido = opa.get();
				
				alumnoLeido.setApellido(alumno.getApellido());
				alumnoLeido.setNombre(alumno.getNombre());
				alumnoLeido.setEmail(alumno.getEmail());
				alumnoLeido.setEdad(alumno.getEdad());
				
				alumnoActualizado = this.alumnoRepository.save(alumnoLeido);
				
			} //else //no existe el alumno que quiere modifcarse
			
		return alumnoActualizado;
	}
	

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findByEdadBetween(int edad_inicial, int edad_final) {
		return this.alumnoRepository.findByEdadBetween(edad_inicial, edad_final);
	}

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findByNombreLike(String nombre) {
		return this.alumnoRepository.findByNombreLike(nombre);
	}

	@Override
	@Transactional (readOnly = true)
	public Iterable<Alumno> findByNombre(String nombre) {
		return this.alumnoRepository.findByNombre(nombre);
	}

	@Override
	public Iterable<Alumno> busquedaPorNombreOApellido(String nombre) {
		return this.alumnoRepository.busquedaPorNombreOApellido(nombre);
	}

	@Override
	public Optional<FraseChuckNorris> obtenerFraseChuckNorris() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		FraseChuckNorris fraseChuckNorris = restTemplate.getForObject("https://api.chucknorris.io/jokes/random", FraseChuckNorris.class);
		
		return Optional.of(fraseChuckNorris);
	}

	@Override
	public Optional<Curso> obtenerCursoNativa(Long idalumno) {
		return this.cursoFeignClient.obtenerCursoNativa(idalumno);
	}
}
