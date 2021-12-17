package edu.djharo.minsait.mscursos.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.djharo.minsait.mscomun.model.entity.Alumno;
import edu.djharo.minsait.mscomun.model.entity.Curso;
import edu.djharo.minsait.mscursos.repository.CursoRepository;
import edu.djharo.minsait.mscursos.service.CursoService;

@Service
public class CursoServiceImp implements CursoService {

	@Autowired
	private CursoRepository cursoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Curso> findAll() {
		return this.cursoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Curso> findById(Long id) {
		return this.cursoRepository.findById(id);
	}

	@Override
	@Transactional
	public Curso save(Curso curso) {
		return this.cursoRepository.save(curso);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		this.cursoRepository.deleteById(id);

	}

	@Override
	@Transactional
	public Curso update(Curso curso, Long id) {
		
		Curso cursoLeido = null;
		Curso cursoActualizado = null;
			
		Optional<Curso> opa = this.cursoRepository.findById(id);
			if (opa.isPresent())
			{
				//el alumno que queremos modificar, existe
				cursoLeido = opa.get();
				
				cursoLeido.setName(curso.getName());

				
				cursoActualizado = this.cursoRepository.save(cursoLeido);
				
			} //else //no existe el alumno que quiere modifcarse
			
		return cursoActualizado;
	}

	@Override
	public Optional<Curso> asignarAlumnos(List<Alumno> alumnos, Long id) {
		Optional<Curso> opc = Optional.empty();
		Curso curso_leido;
		Curso curso_actulizado = null;;
		
				opc = this.cursoRepository.findById(id);
				if (opc.isPresent())
				{
					//asignar los almunos
					curso_leido = opc.get();
					alumnos.forEach(a -> curso_leido.addAlumno(a));
					
					curso_actulizado = this.cursoRepository.save(curso_leido);
					opc = Optional.of(curso_actulizado);
					
				} //else no hago nada 
			
		
		return opc;
	}

	@Override
	public Optional<Curso> eliminarAlumno(Alumno alumno, Long id) {
		Optional<Curso> opc = Optional.empty();
		Curso curso_leido;
		Curso curso_actulizado = null;
		
				opc = this.cursoRepository.findById(id);
				if (opc.isPresent())
				{
					//asignar los almunos
					curso_leido = opc.get();
					curso_leido.removeAlumno(alumno);
					
					
					curso_actulizado = this.cursoRepository.save(curso_leido);
					opc = Optional.of(curso_actulizado);
					
				} //else no hago nada 
			
		
		return opc;
	}
	
	@Override
	public Optional<Curso> obtenerCursoAlumnoQueryNativa(Long id_alumno) {
		return this.cursoRepository.obtenerCursoAlumnoQueryNativa(id_alumno); 
	}

	@Override
	public Optional<Curso> obtenerCursoAlumnoJPQL(Long id_alumno) {
		return this.cursoRepository.obtenerCursoAlumnoJPQL(id_alumno); 
	}
}
