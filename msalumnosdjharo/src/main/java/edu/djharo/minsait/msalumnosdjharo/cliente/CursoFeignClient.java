package edu.djharo.minsait.msalumnosdjharo.cliente;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity; Probar
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.djharo.minsait.mscomun.model.entity.Curso;

@FeignClient(name = "mscursos")
public interface CursoFeignClient {
	
	@GetMapping("/cursos/obtenercursonativo/{idalumno}")
	public Optional<Curso> obtenerCursoNativa(@PathVariable Long idalumno);
}
