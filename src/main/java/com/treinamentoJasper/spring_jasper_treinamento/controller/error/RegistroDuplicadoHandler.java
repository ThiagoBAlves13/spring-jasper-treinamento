package com.treinamentoJasper.spring_jasper_treinamento.controller.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.treinamentoJasper.spring_jasper_treinamento.exception.RegistroDuplicadoException;

@RestControllerAdvice
public class RegistroDuplicadoHandler {
	private Logger logger = LoggerFactory.getLogger(RegistroDuplicadoHandler.class);

	/**
	 * Método interceptador global da exception {@link RegistroDuplicadoException}.
	 *
	 * @param exception uma {@link RegistroDuplicadoException}
	 * @return resposta HTTP relacionada
	 */
	@ExceptionHandler(RegistroDuplicadoException.class)
	public ResponseEntity<String> handle(RegistroDuplicadoException exception) {
		logger.error("Motivo da falha: {}", exception);
		
		String msg ="";

		if (exception.getMessage() != null)
			msg = "\""+exception.getMessage()+"\"";
		
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(msg);
	}	

}
