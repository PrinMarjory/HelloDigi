
package fr.diginamic.HelloDigi.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
	
	@ExceptionHandler({FunctionalException.class})
	protected ResponseEntity<String> traiterErreurs(FunctionalException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

}
