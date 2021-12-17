package edu.djharo.minsait.msalumnosdjharo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//para la gestión centralizada de expceciones
//con esta anotación consigo que esta clase "escuche los fallos" de el paquete indicado
@RestControllerAdvice (basePackages = { "edu.djharo.minsait.msalumnosdjharo" })
public class GestionExcepciones {

	//a nivel de método, consigo asignar un tipo de fallo a un método concreto
	//gracias al uso de ExeceptionHander ))
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<?> errorDeBorrado (Exception ex)
	{
		ResponseEntity<?> responseEntity = null;
		String excepcion = null;
			excepcion = ex.getMessage() + " aaaaalgo de mi";//ex.toString(); muestra org.spring... con getMessage solo el mensaje de la excepcion.
			responseEntity = ResponseEntity.internalServerError().body(excepcion);

		return responseEntity;
	}
}